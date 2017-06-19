package onemessagecompany.onemessage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import onemessagecompany.onemessage.Admin.AdminMessageHistoryActivity;
import onemessagecompany.onemessage.Public.ActivateApp;
import onemessagecompany.onemessage.Public.ChangeUserActivityPassword;
import onemessagecompany.onemessage.Public.PublicMainActivity;
import onemessagecompany.onemessage.data.MyApplication;
import onemessagecompany.onemessage.data.sharedData;
import onemessagecompany.onemessage.model.BoolResponse;
import onemessagecompany.onemessage.model.VersionNumberResponse;
import onemessagecompany.onemessage.rest.ApiClient;
import onemessagecompany.onemessage.rest.CheckFirstTimeLoginApi;
import onemessagecompany.onemessage.rest.VersionNumberApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Splash extends AppCompatActivity {


    private final int SPLASH_DISPLAY_LENGTH = 2000;
    private TextView txtConflict;
    private TextView btnOk;
    private TextView btnExit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        txtConflict =(TextView) findViewById(R.id.txt_version_conflict) ;
        btnOk =(Button) findViewById(R.id.btnOk) ;
        btnExit =(Button) findViewById(R.id.btnExist) ;

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startApp();
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                checkVersionNumber();
//                startApp();
            }
        }, SPLASH_DISPLAY_LENGTH);


    }

    public void checkVersionNumber() {

        VersionNumberApi versionNumberApi = ApiClient.getClient().create(VersionNumberApi.class);
        versionNumberApi.GetVersionNumber().enqueue(new Callback<VersionNumberResponse>() {
            @Override
            public void onResponse(Call<VersionNumberResponse> call, Response<VersionNumberResponse> response) {
                int statusCode = response.code();

                if (statusCode == 200) {
                    String versionNumber = response.body().getVersion();
                    String[] versionNumberStrings = versionNumber.split("\\.");
                    int vNumber = Integer.parseInt(versionNumberStrings[0]);
                    int fNumber = Integer.parseInt(versionNumberStrings[1]);
                    int bNumber = Integer.parseInt(versionNumberStrings[2]);


                    int currentVersionNumber = Integer.parseInt(sharedData.getVersionNumber(MyApplication.getContext()).split("\\.")[0]);
                    int currentFeatureNumber = Integer.parseInt(sharedData.getVersionNumber(MyApplication.getContext()).split("\\.")[1]);
                    int currentBugFixNumber = Integer.parseInt(sharedData.getVersionNumber(MyApplication.getContext()).split("\\.")[2]);



                    if (vNumber != currentVersionNumber || fNumber != currentFeatureNumber) {
                        btnExit.setVisibility(View.VISIBLE);
                        txtConflict.setText("Current app version is no longer compatible with server version. please update your app");
                    }
                    else if(currentBugFixNumber !=bNumber){
                        txtConflict.setText("New version is available, please install it for new bug fixes");
                        btnOk.setVisibility(View.VISIBLE);
                    }
                    else
                        startApp();
                }
            }

            @Override
            public void onFailure(Call<VersionNumberResponse> call, Throwable t) {

            }
        });
    }


    public void startApp() {
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
        } else

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
                } else {
                    Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(loginIntent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<BoolResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Internet Connection Lost", Toast.LENGTH_LONG).show();
                Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(loginIntent);
                finish();
            }
        });
    }


}
