package hassan.com.paydemo.ApiUtils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Hassan on 5/14/2018.
 */

public class RetrofitApi {

    private static Retrofit retrofit;
    private static Retrofit withHeadersRetrofit;

    private static String API_URL = "http://192.168.43.60/api/";
    private static Context context;

    public static Retrofit getRetrofit() {

        if (retrofit == null) {
            Retrofit.Builder retrofitBuilder = new Retrofit.Builder();
            retrofitBuilder.addConverterFactory(ScalarsConverterFactory.create());
            retrofitBuilder.baseUrl(API_URL);
            retrofitBuilder.client(new OkHttpClient.Builder().retryOnConnectionFailure(true).addInterceptor(requestsLoggerInterceptor()).build());
            retrofit = retrofitBuilder.build();
        }
        return retrofit;

    }

    public static Retrofit getRetrofitWithHeaders(Context context) {
        RetrofitApi.context = context;
        if (withHeadersRetrofit == null) {
            Retrofit.Builder retrofitBuilder = new Retrofit.Builder();
            retrofitBuilder.addConverterFactory(ScalarsConverterFactory.create());
            retrofitBuilder.baseUrl(API_URL);
            retrofitBuilder.client(getHttpClient());
            withHeadersRetrofit = retrofitBuilder.build();
        }
        return withHeadersRetrofit;
    }

    private static OkHttpClient getHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.retryOnConnectionFailure(true);
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
                Request request = chain.request().newBuilder().addHeader("Authorization", preferences.getString("token", "null")).build();
                return chain.proceed(request);
            }
        });


        return builder.build();
    }


    private static Interceptor requestsLoggerInterceptor() {
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                // logging headers
                for (int i = 0; i < chain.request().headers().size(); i++) {
                    Log.e("intercept: ", chain.request().headers().value(0) + "\r\n");
                }
                return chain.proceed(chain.request());
            }
        };

        return interceptor;
    }
}