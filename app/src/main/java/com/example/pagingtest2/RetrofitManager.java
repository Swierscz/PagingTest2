package com.example.pagingtest2;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.security.cert.CertificateException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {


    private static final String URL = "https://reqres.in/api/";
    // Create Logger

    // Create OkHttp Client
    private static OkHttpClient.Builder okHttp = new OkHttpClient.Builder();
    // Create Retrofit Builder
    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttp.build());
    // Create Retrofit Instance
    private static Retrofit retrofit = builder.build();
    public static <T> T buildService(Class<T> type) {
        return retrofit.create(type);
    }

}
