package com.example.movies.fragment.favorite;

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
import com.example.movies.R;
import com.example.movies.adapter.TvShowAdapter;
import com.example.movies.clicksupport.ItemClickSupport;
import com.example.movies.model.TvShow;
import com.example.movies.viewmodel.FavoriteViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

public class FavTvShowFragment extends Fragment {

    private RecyclerView rvFavTv;
    private ProgressBar pbFavTv;
    private ArrayList<TvShow> tvShow = new ArrayList<>();
    private TvShowAdapter tvShowAdapter;

    public FavTvShowFragment() {

    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_fav_tv_show, container, false);
        rvFavTv = v.findViewById(R.id.favRv_TvShow);
        pbFavTv = v.findViewById(R.id.pb_favTvShow);
        showLoading(true);
        tvShowAdapter = new TvShowAdapter(getActivity());
        tvShowAdapter.notifyDataSetChanged();

        FavoriteViewModel favoriteViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(FavoriteViewModel.class);
        favoriteViewModel.setFavShows();
        favoriteViewModel.getFavShows().observe(getActivity(), loadShow);

        rvFavTv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvFavTv.setAdapter(tvShowAdapter);
        clickSupport();
        return v;
    }

    private Observer<ArrayList<TvShow>> loadShow = new Observer<ArrayList<TvShow>>() {
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
        ItemClickSupport.addTo(rvFavTv).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
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
            pbFavTv.setVisibility(View.VISIBLE);
        } else {
            pbFavTv.setVisibility(View.GONE);
        }
    }
}
