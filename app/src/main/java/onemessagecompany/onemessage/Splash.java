package onemessagecompany.onemessage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import onemessagecompany.onemessage.Admin.AdminMessageHistoryActivity;
import onemessagecompany.onemessage.Public.ActivateApp;
import onemessagecompany.onemessage.Public.ChangeUserActivityPassword;
import onemessagecompany.onemessage.Public.PublicMainActivity;
import onemessagecompany.onemessage.data.MyApplication;
import onemessagecompany.onemessage.data.sharedData;
import onemessagecompany.onemessage.model.BoolResponse;
import onemessagecompany.onemessage.rest.ApiClient;
import onemessagecompany.onemessage.rest.CheckFirstTimeLoginApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Splash extends AppCompatActivity {


    private final int SPLASH_DISPLAY_LENGTH = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                startApp();
            }
        }, SPLASH_DISPLAY_LENGTH);




    }

    public void startApp()
    {
        if (sharedData.getFirstActivation(getApplicationContext())) {
            if (!sharedData.getAccessToken(getApplicationContext()).isEmpty() && sharedData.getAccessToken(getApplicationContext()) != " ") {
                if (sharedData.getRole(MyApplication.getContext()).equals("Administrator")) {
                    Intent adminMainIntent = new Intent(getApplicationContext(), AdminMessageHistoryActivity.class);
                    startActivity(adminMainIntent);
                    finish();
                } else {
                    checkFirstLoginChangePassword();
                }
            } else {
                Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(loginIntent);
                finish();
            }
        }else

        {
            Intent activateIntent = new Intent(getApplicationContext(), ActivateApp.class);
            startActivity(activateIntent);
            finish();
        }
    }
    public void checkFirstLoginChangePassword() {

        CheckFirstTimeLoginApi checkFirstTimeLoginApi = ApiClient.getAuthorizedClient().create(CheckFirstTimeLoginApi.class);
        checkFirstTimeLoginApi.CheckFirstTimePasswordChanged().enqueue(new Callback<BoolResponse>() {
            @Override
            public void onResponse(Call<BoolResponse> call, Response<BoolResponse> response) {
                int statusCode = response.code();
                if (statusCode == 200) {
                    boolean isFirstLogin = response.body().isResponse();
                    if (isFirstLogin) {
                        Intent changeUserPassIntent = new Intent(Splash.this, ChangeUserActivityPassword.class);
                        startActivity(changeUserPassIntent);
                        finish();
                    } else {
                        Intent publicMainIntetn = new Intent(Splash.this, PublicMainActivity.class);
                        startActivity(publicMainIntetn);
                        finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<BoolResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Invalid Login username or password", Toast.LENGTH_LONG).show();
            }
        });
    }


}
