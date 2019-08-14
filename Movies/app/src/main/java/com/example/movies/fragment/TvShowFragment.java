package com.example.movies.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.movies.DetailActivity;
import com.example.movies.clicksupport.ItemClickSupport;
import com.example.movies.R;
import com.example.movies.adapter.TvShowAdapter;
import com.example.movies.model.TvShow;
import com.example.movies.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.Objects;

public class TvShowFragment extends Fragment {

    private RecyclerView rvTvShow;
    private ArrayList<TvShow> tvShow = new ArrayList<>();
    private ProgressBar progressBar;
    private TvShowAdapter tvShowAdapter;

    public TvShowFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v =  inflater.inflate(R.layout.fragment_tv_show, container, false);
        progressBar = v.findViewById(R.id.progressBarTv);
        rvTvShow = v.findViewById(R.id.rv_tvShow);
        showLoading(true);
        tvShowAdapter = new TvShowAdapter(getActivity());
        tvShowAdapter.notifyDataSetChanged();

        MainViewModel tvViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(MainViewModel.class);
        tvViewModel.setListTvShows();
        tvViewModel.getListTvShows().observe(getActivity(), loadshow);

        rvTvShow.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvTvShow.setAdapter(tvShowAdapter);
        clickSupport();
        return v;
    }

    private Observer<ArrayList<TvShow>> loadshow = new Observer<ArrayList<TvShow>>() {
        @Override
        public void onChanged(@Nullable ArrayList<TvShow> tvShows) {
            if (tvShows != null){
                tvShow.addAll(tvShows);
                tvShowAdapter.setTvShows(tvShows);
                showLoading(false);
            }
        }
    };

    private void clickSupport() {
        ItemClickSupport.addTo(rvTvShow).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int i, View v) {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra(DetailActivity.EXTRA_SHOW, tvShow.get(i));
                startActivity(intent);
            }
        });
    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}
