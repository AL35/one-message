package onemessagecompany.onemessage.rest;

import android.content.Context;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import onemessagecompany.onemessage.data.MyApplication;
import onemessagecompany.onemessage.data.sharedData;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {

    public static final String BASE_URL = "http://ahmedlotfy-001-site1.itempurl.com/";
    public static final String BASE_Firebase_URL = "http://fcm.googleapis.com/";

    private static Retrofit retrofit = null;
    private static Retrofit retrofitAuthorized = null;
    private static Retrofit retrofitFirebase = null;

    private static final String fireBaseServerKey = "Key=AAAAIT9IWe4:APA91bHvb7evTtA6Vx16AD3gfLN_4kW9md8l77A1r0R3mBrDyhoVgRDYw_2l6GGWQe8butBV2_htDTssBoZsUiI3T_O0TNlRrzOOmIefUYtvFeGNp7mEexeJzaKXWeBrL1Mn7YrRhn3q";
    private static String Token;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static Retrofit getAuthorizedClient() {
        Context context = MyApplication.getContext();
        Token = "Bearer " + sharedData.getAccessToken(context);

        OkHttpClient.Builder okBuilder = new OkHttpClient.Builder();
        okBuilder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();

                Request.Builder newRquest = request.newBuilder().header("Authorization", Token);

                return chain.proceed(newRquest.build());
            }
        });
        if (retrofitAuthorized == null) {
            retrofitAuthorized = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okBuilder.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofitAuthorized;
    }

    public static Retrofit getFirebaseClient() {
        OkHttpClient.Builder okBuilder = new OkHttpClient.Builder();
        okBuilder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();

                Request.Builder newRquest = request.newBuilder().header("Authorization", fireBaseServerKey);
                newRquest.header("Content-Type", "application/json");

                return chain.proceed(newRquest.build());
            }
        });
        if (retrofitFirebase == null) {
            retrofitFirebase = new Retrofit.Builder()
                    .baseUrl(BASE_Firebase_URL)
                    .client(okBuilder.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofitFirebase;
    }


}
