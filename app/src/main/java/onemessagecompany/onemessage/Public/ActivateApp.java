package onemessagecompany.onemessage.Public;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import onemessagecompany.onemessage.AdminMainActivity;
import onemessagecompany.onemessage.LoginActivity;
import onemessagecompany.onemessage.R;
import onemessagecompany.onemessage.data.MyApplication;
import onemessagecompany.onemessage.data.sharedData;
import onemessagecompany.onemessage.model.ActivateRequest;
import onemessagecompany.onemessage.model.ActivateResponse;
import onemessagecompany.onemessage.rest.ActivationApi;
import onemessagecompany.onemessage.rest.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivateApp extends AppCompatActivity {

  private ActivationApi activationApi;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
      WindowManager.LayoutParams.FLAG_SECURE);

    setContentView(R.layout.activity_activate_app);

    if (sharedData.getFirstActivation(getApplicationContext())) {
      if (!sharedData.getAccessToken(getApplicationContext()).isEmpty()) {


        switch (sharedData.getRole(MyApplication.getContext())) {
          case "Administrator":
            Intent adminMainIntent = new Intent(ActivateApp.this, AdminMainActivity.class);
            startActivity(adminMainIntent);
            finish();
            break;
          default:
            Intent publicMainIntent = new Intent(ActivateApp.this, PublicMainActivity.class);
            startActivity(publicMainIntent);
            finish();
        }





      } else {
        Intent loginIntent = new Intent(ActivateApp.this, LoginActivity.class);
        startActivity(loginIntent);
        finish();
      }
    }


    Button mActivateButton = (Button) findViewById(R.id.activate_app);
    mActivateButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        activateApp();
      }
    });
  }

  private void activateApp() {
    activationApi = ApiClient.getClient().create(ActivationApi.class);
    ActivateRequest activateRequest = new ActivateRequest();
    EditText mActivationKey = (EditText) findViewById(R.id.txt_activatiobn_key);
    String key = mActivationKey.getText().toString();
    activateRequest.setKey(key);
    Call<ActivateResponse> tokenResponseCall = activationApi.checkActivationAccess(activateRequest);
    tokenResponseCall.enqueue(new Callback<ActivateResponse>() {
      @Override
      public void onResponse(Call<ActivateResponse> call, Response<ActivateResponse> response) {
        int statusCode = response.code();
        ActivateResponse activateResponse = response.body();
        navigateToLogin(activateResponse);
      }

      @Override
      public void onFailure(Call<ActivateResponse> call, Throwable t) {
        Toast.makeText(getApplicationContext(), "Invalid Key", Toast.LENGTH_LONG).show();
      }
    });
  }

  private void navigateToLogin(ActivateResponse activateResponse) {

    sharedData.setFirstActivation(getApplicationContext(), activateResponse.getVerified());
    if (sharedData.getFirstActivation(getApplicationContext())) {

      Intent loginIntent = new Intent(ActivateApp.this, LoginActivity.class);
      startActivity(loginIntent);
      finish();
    } else
      Toast.makeText(getApplicationContext(), "Invalid Key", Toast.LENGTH_LONG).show();
  }
}