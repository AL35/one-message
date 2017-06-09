package onemessagecompany.onemessage.Public;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import onemessagecompany.onemessage.Adapters.ForgetPasswordAdapter;
import onemessagecompany.onemessage.R;
import onemessagecompany.onemessage.model.ForgetPasswordRequest;
import onemessagecompany.onemessage.model.ForgetPasswordResponse;
import onemessagecompany.onemessage.rest.ApiClient;
import onemessagecompany.onemessage.rest.ForgetPasswordApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetPasswordActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_forget_password);

    Button btnForgetPassword = (Button) findViewById(R.id.btnForgetPassword);
    btnForgetPassword.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        EditText mUsername = (EditText) findViewById(R.id.txt_forget_password_username);
        String username = mUsername.getText().toString();
        if (!username.isEmpty())
          sendForget(username);
      }
    });
  }

  public void sendForget(String username) {

    ForgetPasswordApi forgetPasswordApi = ApiClient.getClient().create(ForgetPasswordApi.class);
    ForgetPasswordRequest forgetPasswordRequest =new ForgetPasswordRequest();
    forgetPasswordRequest.setUserName(username);
    forgetPasswordApi.ForgetPassword(forgetPasswordRequest).enqueue(new Callback<ForgetPasswordResponse>() {
      @Override
      public void onResponse(Call<ForgetPasswordResponse> call, Response<ForgetPasswordResponse> response) {
        int statusCode = response.code();
        if (statusCode == 200) {
          Toast.makeText(getApplicationContext(), response.body().getResponse(), Toast.LENGTH_LONG).show();

        }

      }

      @Override
      public void onFailure(Call<ForgetPasswordResponse> call, Throwable t) {

      }
    });

  }
}
