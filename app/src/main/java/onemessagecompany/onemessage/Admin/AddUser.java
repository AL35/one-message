package onemessagecompany.onemessage.Admin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import onemessagecompany.onemessage.AdminMainActivity;
import onemessagecompany.onemessage.BaseActivity;
import onemessagecompany.onemessage.R;
import onemessagecompany.onemessage.model.RegisterRequest;
import onemessagecompany.onemessage.rest.ApiClient;
import onemessagecompany.onemessage.rest.RegisterApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddUser extends BaseActivity {

    private TextView mTextMessage;

    private RegisterApi registerApi;
    private EditText mFirstName;
    private EditText mLastName;
    private EditText mRegisterPassword;
    private EditText mConfirmPassword;
    private Switch mRegisterActivateSwitch;
    private EditText mUserName;
    private Context context = AddUser.this;

    private String firstName;
    private String lastName;
    private String password;
    private String confirmPassword;
    private boolean registerEnabled;
    private String userName;
    private String email;

    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);

        setContentView(R.layout.activity_add_user);

        findViewById(R.id.ac_create_user).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                return true;
            }
        });

        findViewById(R.id.email_login_form).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                return true;
            }
        });

        mFirstName = (EditText) findViewById(R.id.register_FirstName);
        mLastName = (EditText) findViewById(R.id.register_LastName);
        mRegisterPassword = (EditText) findViewById(R.id.register_Password);
        mConfirmPassword = (EditText) findViewById(R.id.register_ConfirmPassword);
        mRegisterActivateSwitch = (Switch) findViewById(R.id.add_user_switch);
        mUserName = (EditText) findViewById(R.id.register_UserName);


        Button mRegisterButton = (Button) findViewById(R.id.register_button);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate())
                    RegisterPost();
                //else
                //    Toast.makeText(getApplicationContext(), "Creation failed", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void RegisterPost() {
        firstName = mFirstName.getText().toString();
        lastName = mLastName.getText().toString();
        password = mRegisterPassword.getText().toString();
        confirmPassword = mConfirmPassword.getText().toString();
        registerEnabled = mRegisterActivateSwitch.isChecked();
        userName = mUserName.getText().toString();
        email = "mail@domain.com";

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
                if (statusCode == 200) {
                    Toast.makeText(getApplicationContext(), "User created successfully", Toast.LENGTH_LONG).show();
                    Intent userMain = new Intent(context, AdminMainActivity.class);
                    startActivity(userMain);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Creation failed , Username is already taken", Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Creation failed", Toast.LENGTH_LONG).show();
            }
        });

    }


    public boolean validate() {
        initialize();
        boolean valid = true;
        if (firstName.isEmpty()) {
            mFirstName.setError("Required");
            valid = false;
        }
        if (lastName.isEmpty()) {
            mLastName.setError("Required");
            valid = false;
        }
        if (userName.isEmpty()) {
            mUserName.setError("Required");
            valid = false;
        }
        if (firstName.isEmpty()) {
            mFirstName.setError("Required");
            valid = false;
        }
        if (password.isEmpty() || password.length() < 6) {
            mRegisterPassword.setError("Required & Should not be less than 6 characters");
            valid = false;
        }
        if (confirmPassword.isEmpty() || confirmPassword.length() < 6) {
            mConfirmPassword.setError("Required & Should not be less than 6 characters");
            valid = false;
        }
        if (!confirmPassword.equals(password)) {
            mConfirmPassword.setError("Confirm Password & Password must be the same");
            valid = false;
        }
        return valid;
    }

    public void initialize() {
        firstName = mFirstName.getText().toString();
        lastName = mLastName.getText().toString();
        password = mRegisterPassword.getText().toString();
        confirmPassword = mConfirmPassword.getText().toString();
        registerEnabled = mRegisterActivateSwitch.isChecked();
        userName = mUserName.getText().toString();
    }


}
