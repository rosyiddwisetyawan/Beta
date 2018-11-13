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
import com.media.beta.fragment_submenu.Populer;
import com.media.beta.fragment_submenu.Terkini;
import com.media.beta.fragment_submenu.Top;

public class Beranda extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    public static Beranda newInstance() {
        Beranda fragment = new Beranda();
        return fragment;
    }

    public Beranda() {
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
        final View view = inflater.inflate(R.layout.fragment_beranda, container, false);

        tabLayout = (TabLayout)view.findViewById(R.id.idtab);
        viewPager = (ViewPager)view.findViewById(R.id.idview);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.clearData();
        adapter.addFragment(new Top(),"Top");
        adapter.addFragment(new Terkini(),"Terkini");
        adapter.addFragment(new Populer(),"Populer");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }

}
