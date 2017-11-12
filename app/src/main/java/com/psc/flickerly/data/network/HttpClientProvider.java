package com.psc.flickerly.data.network;

import com.psc.flickerly.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
public class HttpClientProvider {
    private static final int HTTP_READ_TIMEOUT = 10000;
    private static final int HTTP_CONNECT_TIMEOUT = 6000;

    public OkHttpClient create() {
        return new OkHttpClient().newBuilder()
                                 .connectTimeout(HTTP_CONNECT_TIMEOUT, TimeUnit.SECONDS)
                                 .readTimeout(HTTP_READ_TIMEOUT, TimeUnit.SECONDS)
                                 .addInterceptor(createLoggingInterceptor())
                                 .build();
    }

    private static HttpLoggingInterceptor createLoggingInterceptor() {
        return new HttpLoggingInterceptor()
                .setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
    }
}
