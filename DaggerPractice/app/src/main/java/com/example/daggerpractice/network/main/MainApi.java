package com.example.daggerpractice.network.main;

import com.example.daggerpractice.model.Post;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MainApi {

    @GET("posts")
    Flowable<List<Post>> getPostFromUser(@Query("userId") int id);
}
