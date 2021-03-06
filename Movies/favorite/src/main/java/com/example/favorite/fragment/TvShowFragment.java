package com.example.favorite.fragment;


import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.favorite.DetailActivity;
import com.example.favorite.R;
import com.example.favorite.adapter.TvShowAdapter;
import com.example.favorite.model.TvShow;
import com.example.favorite.utils.ItemClickSupport;
import com.example.favorite.utils.LoadCallback;

import org.jetbrains.annotations.NotNull;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static com.example.favorite.db.DatabaseContract.CONTENT_SHOW_URI;
import static com.example.favorite.utils.MappingHelper.mapCursorToArrayListShow;

public class TvShowFragment extends Fragment implements LoadCallback {

    private RecyclerView rvShow;
    private ProgressBar pb_show;
    private ArrayList<TvShow> tvShow = new ArrayList<>();
    private TvShowAdapter tvShowAdapter;
    private ImageView imgNoColl;
    private TextView tv_noColl;
    private static final String EXTRA_STATE = "EXTRA_STATE";

    public TvShowFragment() {

    }


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tv_show, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imgNoColl = view.findViewById(R.id.noShow);
        tv_noColl = view.findViewById(R.id.tv_noCollTvs);
        rvShow = view.findViewById(R.id.rv_TvShow);
        pb_show = view.findViewById(R.id.pb_favTvShow);
        rvShow.setLayoutManager(new LinearLayoutManager(getActivity()));
        HandlerThread handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        DataObserver observer = new DataObserver(handler, getContext());
        if (getActivity() != null){
            getActivity().getContentResolver().registerContentObserver(CONTENT_SHOW_URI, true, observer);
        }
        tvShowAdapter = new TvShowAdapter(getActivity());
        rvShow.setAdapter(tvShowAdapter);

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
        ItemClickSupport.addTo(rvShow).setOnItemClickListener((recyclerView, i, v) -> {
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
                    -> pb_show.setVisibility(View.VISIBLE));
        }
    }

    @Override
    public void postExecute(Cursor cursor) {
        pb_show.setVisibility(View.GONE);
        tvShow = mapCursorToArrayListShow(cursor);
        if (tvShow.size() > 0){
            tvShowAdapter.setTvShows(tvShow);
            noCollections(false);
        } else {
            rvShow.setVisibility(View.INVISIBLE);
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

        DataObserver(Handler handler, Context context) {
            super(handler);
            this.context = context;
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
        }
    }
}
