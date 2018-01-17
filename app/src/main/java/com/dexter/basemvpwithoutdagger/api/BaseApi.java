package com.dexter.basemvpwithoutdagger.api;

import android.content.Context;

import com.dexter.basemvpwithoutdagger.BuildConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Modifier;
import java.util.concurrent.TimeUnit;

import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class BaseApi {

    private static Retrofit retrofit = null;
    private static Dispatcher dispatcher;

    public static Dispatcher getDispatcher() {
        return dispatcher;
    }

    public static Retrofit getClient(final Context context) {
        if (retrofit == null) {

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.connectTimeout(120, TimeUnit.SECONDS);
            httpClient.readTimeout(120, TimeUnit.SECONDS);

            dispatcher = new Dispatcher();
            httpClient.dispatcher(dispatcher);

            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            if (BuildConfig.DEBUG) {
                //print the logs in this case
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            } else {
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
            }

            httpClient.addInterceptor(loggingInterceptor);

            OkHttpClient client = httpClient.build();

            Gson gson = new GsonBuilder()
                    .excludeFieldsWithModifiers(Modifier.TRANSIENT)
                    .setLenient()
                    .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static void removeClient() {
        retrofit = null;
    }
}
