package com.media.beta.fragment_menu;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.media.beta.R;
import com.media.beta.adapter.PolitikAdapter;
import com.media.beta.data.Politik;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Jobs extends Fragment {

    private ProgressBar pDialog;
    private List<Politik> listPolitikC;
    private List<Politik> listSub;
    private String urlpolitik = "https://raw.githubusercontent.com/rosyiddwisetyawan/api.json/master/politiknew";
    private String urlJSON = "https://newsapi.org/v2/top-headlines?country=id&apiKey=47c3a6bce7a74fee8b56c58b2e5484cc";
    PolitikAdapter politikAdapterC;
    Boolean isScroll = false;
    int currentItem, totalItem, scrollItem;
    SwipeRefreshLayout swipeRefreshLayout;
    EditText edt;
    String search;
    ImageButton btnsearch;

    public static Jobs newInstance() {
        Jobs fragment = new Jobs();
        return fragment;
    }

    public Jobs() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_jobs, container, false);

        edt = (EditText)view.findViewById(R.id.edtSearch);


        btnsearch = (ImageButton) view.findViewById(R.id.btnSearch);
        btnsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search = edt.getText().toString();
                Log.i("search",search.toString());
                if(search!=null){
                    filter(search);
                }else{
                    getDataPolitikTop();
                }
            }
        });
        pDialog = (ProgressBar)view.findViewById(R.id.idprogressJobs);
//        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.idswipeRefreshJobs);
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                Log.i("ini","mulai swipe");
//                getDataPolitikTop();
//                swipeRefreshLayout.setRefreshing(false);
//            }
//        });

        listPolitikC = new ArrayList<>();
        listSub = new ArrayList<>();

        politikAdapterC = new PolitikAdapter(getContext(), listPolitikC);

        getDataPolitikTop();

        RecyclerView recyclerView3 = (RecyclerView) view.findViewById(R.id.recyclerv_frag_politik_utama3);
        recyclerView3.setAdapter(politikAdapterC);
        recyclerView3.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView3.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState== AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                    isScroll=true;
                    fetchData();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItem = new LinearLayoutManager(getActivity()).getChildCount();
                totalItem = new LinearLayoutManager(getActivity()).getItemCount();
                scrollItem = new LinearLayoutManager(getActivity()).findFirstVisibleItemPosition();

                if(isScroll && (currentItem + scrollItem == totalItem)){
                    isScroll = false;
                }
            }
        });

        return view;
    }

    private void fetchData(){
        pDialog.setVisibility(View.VISIBLE);
        System.out.print("berapa "+listSub.size());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(listSub.size()>=2) {
                    for (int i = 0; i <= 2; i++) {
                        if(i == 2){
                            break; // A unlabeled break is enough. You don't need a labeled break here.
                        }
                        listPolitikC.add(listSub.get(i));
                    }
                    listSub.subList(0,2).clear();
                    pDialog.setVisibility(View.GONE);
                    politikAdapterC.notifyDataSetChanged();
                }
                pDialog.setVisibility(View.GONE);
            }
        },1000);
    }

    private void filter(String text) {
        //new array list that will hold the filtered data
        List<Politik> temp = new ArrayList();
        for(Politik p: listPolitikC){
            if(p.getTitle().toLowerCase().contains(text.toLowerCase())){
                temp.add(p);
            }
        }
        //update recyclerview
        politikAdapterC.updateList(temp);
    }

    private void getDataPolitikTop() {
        listPolitikC.clear();
        listSub.clear();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlJSON,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);
                        try {
                            JSONArray jsonArray = response.getJSONArray("articles");
                            for (int p=0; p<jsonArray.length(); p++){
                                JSONObject jsonObject = jsonArray.getJSONObject(p);
                                Politik politik = new Politik();
                                politik.setDescription(jsonObject.getString("content"));
                                politik.setImage(jsonObject.getString("urlToImage"));
                                politik.setTitle(jsonObject.getString("title"));
                                politik.setLink(jsonObject.getString("url"));
                                JSONObject name = jsonObject.getJSONObject("source");
                                politik.setSumber(name.getString("name"));
                                Log.i("masuk aja","oke");
                                if(p<5) {
                                    listPolitikC.add(politik);
                                    Log.i("ini polc", String.valueOf(listPolitikC.size()));
                                }else {
                                    listSub.add(politik);
                                    Log.i("ini sub", String.valueOf(listSub.size()));
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        politikAdapterC.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.toString());
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(jsonObjectRequest);
    }
}
