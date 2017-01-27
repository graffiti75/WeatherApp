package com.example.rodrigo.weatherapp.presenter.di.module;

import android.content.Context;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * ApplicationModule.java.
 *
 * @author Rodrigo Cericatto
 * @since Jan 27, 2017
 */
@Module
public class ApplicationModule {
    private String mBaseUrl;
    private Context mContext;

    public ApplicationModule(Context context, String baseUrl) {
        mContext = context;
        mBaseUrl = baseUrl;
    }

    @Singleton
    @Provides
    GsonConverterFactory provideGsonConverterFactory() {
        return GsonConverterFactory.create();
    }

    @Singleton
    @Provides
    OkHttpClient provideOkHttpClient() {
        return new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .build();
    }

    @Singleton
    @Provides
    RxJavaCallAdapterFactory provideRxJavaCallAdapterFactory() {
        return RxJavaCallAdapterFactory.create();
    }

    @Singleton
    @Provides
    Retrofit provideRetrofit(OkHttpClient client, GsonConverterFactory converterFactory,
        RxJavaCallAdapterFactory adapterFactory) {
        return new Retrofit.Builder()
            .baseUrl(mBaseUrl)
            .addConverterFactory(converterFactory)
            .addCallAdapterFactory(adapterFactory)
            .client(client)
            .build();
    }

    @Provides
    @Singleton
    Context provideContext() {
        return mContext;
    }
}