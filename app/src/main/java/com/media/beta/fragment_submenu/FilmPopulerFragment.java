package com.media.beta.fragment_submenu;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
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
import com.media.beta.adapter.FilmPopulerAdapter;
import com.media.beta.adapter.HorizontalAdapter;
import com.media.beta.adapter.PolitikAdapter;
import com.media.beta.data.Film;
import com.media.beta.data.Politik;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class FilmPopulerFragment extends Fragment {

    private ProgressBar pDialog;
    private List<Film> listItem;
    private List<Film> listSub;
    private String urlJSON = "https://api.themoviedb.org/3/movie/popular?api_key=fd2bccf8d722c3ddb797baecd76d359b&language=en-US&page=1";
    FilmPopulerAdapter filmPopulerAdapter;
    Boolean isScroll = false;
    int currentItem, totalItem, scrollItem;
    private static final int NUM_COLUMNS = 2;

    public FilmPopulerFragment() {
        // Required empty public constructor
    }

    public static FilmPopulerFragment newInstance() {
        FilmPopulerFragment fragment = new FilmPopulerFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_film_populer, container, false);

        pDialog = (ProgressBar)view.findViewById(R.id.idprogress);

        listItem = new ArrayList<>();
        listSub = new ArrayList<>();

        filmPopulerAdapter = new FilmPopulerAdapter(getContext(), listItem);

        getDataPolitikTop();

        RecyclerView recyclerViewtop = (RecyclerView) view.findViewById(R.id.recycler_view);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(NUM_COLUMNS, LinearLayoutManager.VERTICAL);
        recyclerViewtop.setAdapter(filmPopulerAdapter);
        recyclerViewtop.setLayoutManager(staggeredGridLayoutManager);
        recyclerViewtop.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                        listItem.add(listSub.get(i));
                    }
                    listSub.subList(0,2).clear();
                    pDialog.setVisibility(View.GONE);
                    filmPopulerAdapter.notifyDataSetChanged();
                }
                pDialog.setVisibility(View.GONE);
            }
        },1000);
    }

    private void getDataPolitikTop() {
        listItem.clear();
        listSub.clear();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlJSON,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);
                        try {
                            JSONArray jsonArray = response.getJSONArray("results");
                            for (int p=0; p<jsonArray.length(); p++){
                                JSONObject jsonObject = jsonArray.getJSONObject(p);
                                Film film = new Film();
                                film.setDescription(jsonObject.getString("overview"));
                                film.setImage(jsonObject.getString("poster_path"));
                                film.setTitle(jsonObject.getString("title"));
                                film.setRelease(jsonObject.getString("release_date"));
                                film.setVote(jsonObject.getString("vote_average"));
                                film.setPopular(jsonObject.getString("popularity"));
                                if(p==0) {
                                    Log.i("Test","https://image.tmdb.org/t/p/w500/"+jsonObject.getString("poster_path"));
                                    listItem.add(film);
                                }else if(p>0 && p<12){
                                    Log.i("Test","https://image.tmdb.org/t/p/w500/"+jsonObject.getString("poster_path"));
                                    listItem.add(film);
                                }else{
                                    Log.i("Test","https://image.tmdb.org/t/p/w500/"+jsonObject.getString("poster_path"));
                                    listSub.add(film);
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        filmPopulerAdapter.notifyDataSetChanged();
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
