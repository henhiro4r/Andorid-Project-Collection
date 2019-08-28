package com.example.movies.reminder;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import android.util.Log;

import com.example.movies.DetailActivity;
import com.example.movies.MainActivity;
import com.example.movies.R;
import com.example.movies.model.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import cz.msebera.android.httpclient.Header;

import static com.example.movies.db.DatabaseContract.CONTENT_MOVIE_URI;

public class MovieReleaseReminder extends BroadcastReceiver {

    private final int NOTIFICATION_ID = 222;
    private static final String API_KEY = "68eff651539ae197e48884a6d31d2059";
    private ArrayList<Movie> movies = new ArrayList<>();

    @Override
    public void onReceive(Context context, Intent intent) {
        getReleasedMovie(context);
    }

    private void getReleasedMovie(Context context) {
        Date date = Calendar.getInstance().getTime();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String today = format.format(date);

        AsyncHttpClient client = new AsyncHttpClient();
        String url = "https://api.themoviedb.org/3/discover/movie?api_key="+ API_KEY +"&language=en-US&primary_release_date.gte=" + today + "&primary_release_date.lte=" + today;
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject movie = list.getJSONObject(i);
                        if (today.equals(movie.getString("release_date"))) {
                            Movie m = new Movie();
                            String id = String.valueOf(movie.getInt("id"));
                            m.setId_movie(id);
                            m.setTitle(movie.getString("title"));
                            m.setPopularity(String.valueOf(movie.getDouble("popularity")));
                            m.setDescription(movie.getString("overview"));
                            m.setPoster("https://image.tmdb.org/t/p/original" + movie.getString("poster_path"));
                            m.setCover("https://image.tmdb.org/t/p/original" + movie.getString("backdrop_path"));
                            m.setReleaseDate(movie.getString("release_date").substring(0, 4));
                            movies.add(m);
                        }
                    }
                    showNotification(context);
                } catch (Exception e) {
                    Log.d("onFailureReleaseMovie", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailureToConnect", error.getMessage());
            }
        });

    }

    private void showNotification(Context context) {
        int REQUEST_CODE = 101;
        String CHANNEL_ID = "channel_02";
        String CHANNEL_NAME = "Reminder movie release";
        String title = context.getString(R.string.release_title);
        String message;
        Intent intent;
        Bitmap icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_notifications_black_24dp);
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID);

        if (movies.size() > 0) {
            intent = new Intent(context, DetailActivity.class);
            for (int i = 0; i < movies.size(); i++) {
                Uri uri = Uri.parse(CONTENT_MOVIE_URI + "/" + movies.get(i).getId_movie());
                intent.setData(uri);
                intent.putExtra(DetailActivity.EXTRA_MOVIE, movies.get(i));
                PendingIntent pendingIntent = TaskStackBuilder.create(context).addNextIntent(intent).getPendingIntent(i, PendingIntent.FLAG_UPDATE_CURRENT);
                message = movies.get(i).getTitle() + " " + context.getString(R.string.release_message);
                builder.setSmallIcon(R.drawable.ic_notifications_black_24dp)
                        .setLargeIcon(icon)
                        .setContentTitle(title)
                        .setContentText(message)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true);
                if (manager != null) {
                    manager.notify(i, builder.build());
                }
            }
        } else {
            intent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = TaskStackBuilder.create(context).addNextIntent(intent).getPendingIntent(REQUEST_CODE, PendingIntent.FLAG_UPDATE_CURRENT);
            message = context.getString(R.string.no_release_message);
            builder.setSmallIcon(R.drawable.ic_notifications_black_24dp)
                    .setLargeIcon(icon)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);
            if (manager != null) {
                manager.notify(NOTIFICATION_ID, builder.build());
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            builder.setChannelId(CHANNEL_ID);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }
    }

    public void activateReleaseReminder(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, MovieReleaseReminder.class);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, NOTIFICATION_ID, intent, 0);
        if (alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }
    }

    public void deactivateReleaseReminder(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, MovieReleaseReminder.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, NOTIFICATION_ID, intent, 0);
        pendingIntent.cancel();
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
    }
}
