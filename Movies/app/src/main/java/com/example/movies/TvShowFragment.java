package com.example.movies;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.movies.adapter.TvShowAdapter;
import com.example.movies.model.TvShow;
import com.example.movies.model.TvShowData;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFragment extends Fragment {

    RecyclerView rvTvShow;
    ArrayList<TvShow> tvShows = new ArrayList<>();

    public TvShowFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v =  inflater.inflate(R.layout.fragment_tv_show, container, false);
        rvTvShow = v.findViewById(R.id.rv_tvShow);
        rvTvShow.setHasFixedSize(true);
        tvShows.addAll(TvShowData.getShowData());
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        loadData();
    }

    private void loadData() {
        rvTvShow.setLayoutManager(new LinearLayoutManager(getActivity()));
        TvShowAdapter tvShowAdapter = new TvShowAdapter(getActivity());
        tvShowAdapter.setTvShows(tvShows);
        rvTvShow.setAdapter(tvShowAdapter);

        ItemClickSupport.addTo(rvTvShow).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int i, View v) {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra(DetailActivity.EXTRA_SHOW, tvShows.get(i));
                startActivity(intent);
            }
        });
    }
}
