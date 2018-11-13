package com.media.beta.fragment_submenu;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.media.beta.R;


public class FilmToprateFragment extends Fragment {

    public FilmToprateFragment() {
        // Required empty public constructor
    }


    public static FilmToprateFragment newInstance() {
        FilmToprateFragment fragment = new FilmToprateFragment();
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
        final View view =  inflater.inflate(R.layout.fragment_film_toprate, container, false);
        return view;
    }


}
