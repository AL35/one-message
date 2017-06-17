package onemessagecompany.onemessage.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import onemessagecompany.onemessage.BaseActivity;
import onemessagecompany.onemessage.LoginActivity;
import onemessagecompany.onemessage.R;
import onemessagecompany.onemessage.model.GenerateKeyResponse;
import onemessagecompany.onemessage.rest.ApiClient;
import onemessagecompany.onemessage.rest.GenerateActivationKeyApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GenerateKey extends BaseActivity {

  private GenerateActivationKeyApi generateActivationKeyApi;
  private TextView mGeneratedActivationKey;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
      WindowManager.LayoutParams.FLAG_SECURE);

    setContentView(R.layout.activity_generate_key);

    mGeneratedActivationKey = (TextView) findViewById(R.id.generated_activation_key);
    Button mRegisterButton = (Button) findViewById(R.id.generate_activation_button);
    mRegisterButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        GenerateGet();
      }
    });
  }


  private void GenerateGet() {


    generateActivationKeyApi = ApiClient.getAuthorizedClient().create(GenerateActivationKeyApi.class);
    generateActivationKeyApi.GenerateActivationKey().enqueue(new Callback<GenerateKeyResponse>() {
      @Override
      public void onResponse(Call<GenerateKeyResponse> call, Response<GenerateKeyResponse> response) {
        int statusCode = response.code();
        if (statusCode == 200 && response.body() != null) {
          GenerateKeyResponse generateKeyResponse = response.body();
          mGeneratedActivationKey.setText(generateKeyResponse.getKey());
        }
        else
        {
          Intent intentConfig = new Intent(GenerateKey.this, LoginActivity.class);
          startActivity(intentConfig);
        }
      }

      @Override
      public void onFailure(Call<GenerateKeyResponse> call, Throwable t) {
        Toast.makeText(getApplicationContext(), "Invalid Login username or password", Toast.LENGTH_LONG).show();
      }
    });
  }
}
