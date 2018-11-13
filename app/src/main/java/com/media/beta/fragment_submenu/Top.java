package com.media.beta.fragment_submenu;

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
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.media.beta.R;
import com.media.beta.adapter.PolitikAdapter;
import com.media.beta.adapter.TerbaruUtamaAdapter;
import com.media.beta.data.Politik;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class Top extends Fragment {

    private ProgressBar pDialog;
    private List<Politik> listTerbaruUtama;
    private List<Politik> listPolitikA;
    private List<Politik> listPolitikB;
    private List<Politik> listPolitikC;
    private List<Politik> listSub;
    private String urlpolitik = "https://raw.githubusercontent.com/rosyiddwisetyawan/api.json/master/politiknew";
    private String urlJSON = "https://newsapi.org/v2/top-headlines?country=id&apiKey=47c3a6bce7a74fee8b56c58b2e5484cc";
    TerbaruUtamaAdapter terbaruUtamaAdapter;
    PolitikAdapter politikAdapterA;
    PolitikAdapter politikAdapterB;
    PolitikAdapter politikAdapterC;
    Boolean isScroll = false;
    int currentItem, totalItem, scrollItem;

    public static Top newInstance() {
        Top fragment = new Top();
        return fragment;
    }

    public Top() {
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
        final View view = inflater.inflate(R.layout.fragment_top, container, false);

        pDialog = (ProgressBar)view.findViewById(R.id.idprogress);
//        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.idswipeRefresh);
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                getDataPolitikTop();
//                swipeRefreshLayout.setRefreshing(false);
//            }
//        });

        AdView mAdView = (AdView) view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        AdView mAdView2 = (AdView) view.findViewById(R.id.adView2);
        AdRequest adRequest2 = new AdRequest.Builder().build();
        mAdView2.loadAd(adRequest2);

        listTerbaruUtama = new ArrayList<>();
        listPolitikA = new ArrayList<>();
        listPolitikB = new ArrayList<>();
        listPolitikC = new ArrayList<>();
        listSub = new ArrayList<>();


        terbaruUtamaAdapter = new TerbaruUtamaAdapter(getContext(), listTerbaruUtama);
        politikAdapterA = new PolitikAdapter(getContext(), listPolitikA);
        politikAdapterB = new PolitikAdapter(getContext(), listPolitikB);
        politikAdapterC = new PolitikAdapter(getContext(), listPolitikC);

        getDataPolitikTop();

        RecyclerView recyclerViewtop = (RecyclerView) view.findViewById(R.id.recyclerv_frag_politik_utama_top);
        recyclerViewtop.setNestedScrollingEnabled(false);
        recyclerViewtop.setAdapter(terbaruUtamaAdapter);
        recyclerViewtop.setLayoutManager(new LinearLayoutManager(getActivity()));

        RecyclerView recyclerView1 = (RecyclerView) view.findViewById(R.id.recyclerv_frag_politik_utama1);
        recyclerView1.setNestedScrollingEnabled(false);
        recyclerView1.setAdapter(politikAdapterA);
        recyclerView1.setLayoutManager(new LinearLayoutManager(getActivity()));

        RecyclerView recyclerView2 = (RecyclerView) view.findViewById(R.id.recyclerv_frag_politik_utama2);
        recyclerView2.setNestedScrollingEnabled(false);
        recyclerView2.setAdapter(politikAdapterB);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getActivity()));

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
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                System.out.print("jumlah: "+listSub.size());
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

    private void getDataPolitikTop() {
        listTerbaruUtama.clear();
        listPolitikA.clear();
        listPolitikB.clear();
        listPolitikC.clear();
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
                                if(p==0) {
                                    listTerbaruUtama.add(politik);
                                }else if(p>0 && p<2){
                                    listPolitikA.add(politik);
                                }else if(p>1 && p<3){
                                    listPolitikB.add(politik);
                                }else if(p>3 && p<9){
                                    listPolitikC.add(politik);
                                }else{
                                    listSub.add(politik);
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        terbaruUtamaAdapter.notifyDataSetChanged();
                        politikAdapterA.notifyDataSetChanged();
                        politikAdapterB.notifyDataSetChanged();
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
