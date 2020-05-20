package com.example.daggerpractice.ui.main.post;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.daggerpractice.SessionManager;
import com.example.daggerpractice.model.Post;
import com.example.daggerpractice.network.main.MainApi;
import com.example.daggerpractice.ui.main.Resource;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class PostViewModel extends ViewModel {

    private static final String TAG = "PostViewModel";

    private final MainApi mainApi;
    private final SessionManager sessionManager;

    private MediatorLiveData<Resource<List<Post>>> posts;

    @Inject
    public PostViewModel(MainApi api, SessionManager sessionManager) {
        this.mainApi = api;
        this.sessionManager = sessionManager;
        Log.d(TAG, "PostViewModel: working");
    }

    public LiveData<Resource<List<Post>>> observePost() {
        if (posts == null) {
            posts = new MediatorLiveData<>();
            posts.setValue(Resource.loading((List<Post>) null));
            final LiveData<Resource<List<Post>>> source = LiveDataReactiveStreams.fromPublisher(mainApi.getPostFromUser(sessionManager.getUser().getValue().data.getId())
                    .onErrorReturn(new Function<Throwable, List<Post>>() {
                        @Override
                        public List<Post> apply(Throwable throwable) throws Exception {
                            Log.e(TAG, "apply: ", throwable);
                            Post p = new Post();
                            p.setId(-1);
                            ArrayList<Post> post = new ArrayList<>();
                            post.add(p);
                            return post;
                        }
                    })
                    .map(new Function<List<Post>, Resource<List<Post>>>() {
                        @Override
                        public Resource<List<Post>> apply(List<Post> posts) throws Exception {
                            if (posts.size() > 0) {
                                if (posts.get(0).getId() == -1){
                                    return Resource.error("Something went wrong", null);
                                }
                            }
                            return Resource.success(posts);
                        }
                    })
                    .subscribeOn(Schedulers.io())
            );
            posts.addSource(source, new Observer<Resource<List<Post>>>() {
                @Override
                public void onChanged(Resource<List<Post>> listResource) {
                    posts.setValue(listResource);
                    posts.removeSource(source);
                }
            });
        }
        return posts;
    }
}

// .map(Resource.success())
// .doOnError(new Consumer<Throwable>() {
//     @Override
//     public void accept(Throwable throwable) throws Exception {
//         Log.d(TAG, throwable.getMessage());
//     }
// })
// .onErrorReturnItem(Resource.error("Something went wrong", null))
