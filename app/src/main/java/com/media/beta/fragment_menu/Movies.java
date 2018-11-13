package com.media.beta.fragment_menu;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.media.beta.R;
import com.media.beta.adapter.ViewPagerAdapter;
import com.media.beta.fragment_submenu.FilmPopulerFragment;
import com.media.beta.fragment_submenu.FilmToprateFragment;


public class Movies extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    public static Movies newInstance() {
        Movies fragment = new Movies();
        return fragment;
    }

    public Movies() {
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
        final View view = inflater.inflate(R.layout.fragment_movies, container, false);

        tabLayout = (TabLayout)view.findViewById(R.id.idtab2);
        viewPager = (ViewPager)view.findViewById(R.id.idview2);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.clearData();
        adapter.addFragment(new FilmPopulerFragment(),"Populer");
        adapter.addFragment(new FilmToprateFragment(),"Top Rate");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }


}
