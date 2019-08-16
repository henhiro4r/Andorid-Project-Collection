package com.example.movies.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.movies.R;
import com.example.movies.adapter.ViewPagerAdapter;
import com.example.movies.fragment.favorite.FavMovieFragment;
import com.example.movies.fragment.favorite.FavTvShowFragment;

import java.util.Objects;

public class FavoriteFragment extends Fragment {

    public FavoriteFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_favorite, container, false);
        TabLayout favTabLayout = v.findViewById(R.id.tab_fav);
        ViewPager favViewPager = v.findViewById(R.id.viewPager_fav);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new FavMovieFragment(), getString(R.string.title_movie));
        adapter.addFragment(new FavTvShowFragment(), getString(R.string.title_tv_show));
        favViewPager.setAdapter(adapter);
        favTabLayout.setupWithViewPager(favViewPager);
        return v;
    }

}
