package onemessagecompany.onemessage.Admin;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import onemessagecompany.onemessage.R;
import onemessagecompany.onemessage.data.sharedData;
import onemessagecompany.onemessage.model.RegisterRequest;
import onemessagecompany.onemessage.model.RegisterResponse;
import onemessagecompany.onemessage.rest.ApiClient;
import onemessagecompany.onemessage.rest.RegisterApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddUser extends AppCompatActivity {

  private TextView mTextMessage;

  private RegisterApi registerApi;
  private EditText mFirstName;
  private EditText mLastName;
  private EditText mRegisterPassword;
  private EditText mConfirmPassword;
  private CheckBox mRegisterActivateCheckbox;
  private EditText mRegister_UserName;
  private EditText mRegisterEmail;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
      WindowManager.LayoutParams.FLAG_SECURE);

    setContentView(R.layout.activity_add_user);

    mFirstName = (EditText) findViewById(R.id.register_FirstName);
    mLastName = (EditText) findViewById(R.id.register_LastName);
    mRegisterPassword = (EditText) findViewById(R.id.register_Password);
    mConfirmPassword = (EditText) findViewById(R.id.register_ConfirmPassword);
    mRegisterActivateCheckbox = (CheckBox) findViewById(R.id.register_checkBox);
    mRegister_UserName = (EditText) findViewById(R.id.register_UserName);
    mRegisterEmail = (EditText) findViewById(R.id.register_Email);


    Button mRegisterButton = (Button) findViewById(R.id.register_button);
    mRegisterButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        RegisterPost();
      }
    });

  }

  private void RegisterPost() {
    String firstName = mFirstName.getText().toString();
    String lastName = mLastName.getText().toString();
    String password = mRegisterPassword.getText().toString();
    String confirmPassword = mConfirmPassword.getText().toString();
    boolean registerEnabled = mRegisterActivateCheckbox.isChecked();
    String userName = mRegister_UserName.getText().toString();
    String email = mRegisterEmail.getText().toString();

    registerApi = ApiClient.getAuthorizedClient().create(RegisterApi.class);
    RegisterRequest registerRequest = new RegisterRequest();

    registerRequest.setEmail(email);
    registerRequest.setFirstName(firstName);
    registerRequest.setLastName(lastName);
    registerRequest.setPassword(password);
    registerRequest.setConfirmPassword(confirmPassword);
    registerRequest.setUserName(userName);
    registerRequest.setIsEnabled(registerEnabled);


    registerApi.Register(registerRequest).enqueue(new Callback<Void>() {
      @Override
      public void onResponse(Call<Void> call, Response<Void> response) {
        int statusCode = response.code();
        if (statusCode == 200)
          Toast.makeText(getApplicationContext(), "Create Success", Toast.LENGTH_LONG).show();
      }

      @Override
      public void onFailure(Call<Void> call, Throwable t) {
        Toast.makeText(getApplicationContext(), "Invalid Login username or password", Toast.LENGTH_LONG).show();
      }
    });

  }


}
