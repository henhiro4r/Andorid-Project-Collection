package com.example.favorite.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import android.util.Log;

import com.example.favorite.model.Cast;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Cast>> listCast = new MutableLiveData<>();
    private MutableLiveData<ArrayList<String>> genres = new MutableLiveData<>();

    public void setGenre(String url) {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<String> listItems = new ArrayList<>();
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("genres");
                    for (int i = 0; i < list.length(); i++){
                        JSONObject g = list.getJSONObject(i);
                        listItems.add(g.getString("name"));
                    }
                    genres.postValue(listItems);
                }catch (Exception e){
                    Log.d("exceptionGenre", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailureGenre", error.getMessage());
            }
        });
    }
    public LiveData<ArrayList<String>> getGenre() {
        return genres;
    }

    public void setCast(String url){
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<Cast> listItems = new ArrayList<>();
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("cast");
                    for (int i = 0; i < list.length(); i++){
                        JSONObject c = list.getJSONObject(i);
                        Cast cast = new Cast();
                        cast.setImg_url("https://image.tmdb.org/t/p/original" + c.getString("profile_path"));
                        cast.setRole(c.getString("character"));
                        cast.setName(c.getString("name"));
                        listItems.add(cast);
                    }
                    listCast.postValue(listItems);
                }catch (Exception e){
                    Log.d("exceptionCast", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailureCast", error.getMessage());
            }
        });
    }

    public LiveData<ArrayList<Cast>> getCast(){
        return listCast;
    }
}
