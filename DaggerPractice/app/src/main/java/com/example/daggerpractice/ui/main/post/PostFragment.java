package com.example.daggerpractice.ui.main.post;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.daggerpractice.R;
import com.example.daggerpractice.model.Post;
import com.example.daggerpractice.ui.main.Resource;
import com.example.daggerpractice.ui.main.profile.ProfileViewModel;
import com.example.daggerpractice.viewmodels.ViewModelProviderFactory;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class PostFragment extends DaggerFragment {

    private static final String TAG = "PostFragment";
    private PostViewModel viewModel;
    private RecyclerView recyclerView;

    @Inject
    PostAdapter adapter;

    @Inject
    ViewModelProviderFactory providerFactory;

    public PostFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_post, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this.getViewModelStore(), providerFactory).get(PostViewModel.class);
        recyclerView = view.findViewById(R.id.recycler_view);
        initRecyclerView();
        getPostData();
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }

    private void getPostData() {
        viewModel.observePost().removeObservers(getViewLifecycleOwner());
        viewModel.observePost().observe(getViewLifecycleOwner(), new Observer<Resource<List<Post>>>() {
            @Override
            public void onChanged(Resource<List<Post>> listResource) {
                if (listResource != null) {
                    switch (listResource.status) {
                        case LOADING:
                            Log.d(TAG, "onChanged: LOADING...");
                            break;
                        case SUCCESS:
                            Log.d(TAG, "onChanged: got posts...");
                            adapter.setPost(listResource.data);
                            break;
                        case ERROR:
                            Log.e(TAG, "onChanged: ERROR..." + listResource.message);
                            break;
                    }
                }
            }
        });
    }
}
