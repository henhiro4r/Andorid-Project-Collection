package com.example.movies.fragment.favorite;

import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.movies.DetailActivity;
import com.example.movies.R;
import com.example.movies.adapter.TvShowAdapter;
import com.example.movies.clicksupport.ItemClickSupport;
import com.example.movies.helper.LoadCallback;
import com.example.movies.model.TvShow;

import org.jetbrains.annotations.NotNull;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static com.example.movies.db.DatabaseContract.CONTENT_SHOW_URI;
import static com.example.movies.helper.MappingHelper.mapCursorToArrayListShow;

public class FavTvShowFragment extends Fragment implements LoadCallback {

    private RecyclerView rvFavTv;
    private ProgressBar pbFavTv;
    private ArrayList<TvShow> tvShow = new ArrayList<>();
    private TvShowAdapter tvShowAdapter;
    private ImageView imgNoColl;
    private TextView tv_noColl;
    private static final String EXTRA_STATE = "EXTRA_STATE";

    public FavTvShowFragment() {

    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fav_tv_show, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imgNoColl = view.findViewById(R.id.no_collTv);
        tv_noColl = view.findViewById(R.id.tv_noCollTv);
        rvFavTv = view.findViewById(R.id.favRv_TvShow);
        pbFavTv = view.findViewById(R.id.pb_favTvShow);
        rvFavTv.setLayoutManager(new LinearLayoutManager(getActivity()));
        HandlerThread handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        DataObserver observer = new DataObserver(handler, getContext());
        if (getActivity() != null){
            getActivity().getContentResolver().registerContentObserver(CONTENT_SHOW_URI, true, observer);
        }
        tvShowAdapter = new TvShowAdapter(getActivity());
        rvFavTv.setAdapter(tvShowAdapter);

        if (savedInstanceState == null) {
            new LoadShowAsync(getContext(), this).execute();
        } else {
            ArrayList<TvShow> t = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (t != null){
                tvShowAdapter.setTvShows(t);
            }
        }
        clickSupport();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE, tvShowAdapter.getListShow());
    }

    @Override
    public void onResume() {
        super.onResume();
        new LoadShowAsync(getContext(), this).execute();
    }

    private void clickSupport() {
        ItemClickSupport.addTo(rvFavTv).setOnItemClickListener((recyclerView, i, v) -> {
            Uri uri = Uri.parse(CONTENT_SHOW_URI + "/" + tvShow.get(i).getId_show());
            Intent intent = new Intent(getActivity(), DetailActivity.class);
            intent.setData(uri);
            intent.putExtra(DetailActivity.EXTRA_SHOW, tvShow.get(i));
            startActivity(intent);
        });
    }

    @Override
    public void preExecute() {
        if (getActivity() != null){
            getActivity().runOnUiThread(()
                    -> pbFavTv.setVisibility(View.VISIBLE));
        }
    }

    @Override
    public void postExecute(Cursor cursor) {
        pbFavTv.setVisibility(View.GONE);
        tvShow = mapCursorToArrayListShow(cursor);
        if (tvShow.size() > 0){
            noCollections(false);
            tvShowAdapter.setTvShows(tvShow);
        } else {
            rvFavTv.setVisibility(View.INVISIBLE);
            noCollections(true);
        }
    }

    private void noCollections(boolean b) {
        if (b) {
            imgNoColl.setVisibility(View.VISIBLE);
            tv_noColl.setVisibility(View.VISIBLE);
        } else {
            imgNoColl.setVisibility(View.GONE);
            tv_noColl.setVisibility(View.GONE);
        }
    }

    private static class LoadShowAsync extends AsyncTask<Void, Void, Cursor> {
        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadCallback> weakCallback;

        private LoadShowAsync(Context context, LoadCallback callback) {
            weakContext = new WeakReference<>(context);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            Context context = weakContext.get();
            return context.getContentResolver().query(CONTENT_SHOW_URI, null, null,null,null);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            weakCallback.get().postExecute(cursor);
        }
    }

    public static class DataObserver extends ContentObserver {
        final Context context;

        public DataObserver(Handler handler, Context context) {
            super(handler);
            this.context = context;
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
        }
    }
}
