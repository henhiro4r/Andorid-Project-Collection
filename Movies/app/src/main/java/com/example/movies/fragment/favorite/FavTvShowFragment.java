package com.example.movies.fragment.favorite;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.movies.R;

public class FavTvShowFragment extends Fragment {

    private RecyclerView rvFavTv;
    private ProgressBar pbFavTv;

    public FavTvShowFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_fav_tv_show, container, false);
        rvFavTv = v.findViewById(R.id.favRv_TvShow);
        pbFavTv = v.findViewById(R.id.pb_favTvShow);
        showLoading(true);
        return v;
    }

    private void showLoading(Boolean state) {
        if (state) {
            pbFavTv.setVisibility(View.VISIBLE);
        } else {
            pbFavTv.setVisibility(View.GONE);
        }
    }
}
