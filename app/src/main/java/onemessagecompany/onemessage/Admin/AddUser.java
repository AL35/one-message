package onemessagecompany.onemessage.Admin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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
    private CheckBox mRegisterActivateCheckbox;
    private EditText mUserName;
    private EditText mRegisterEmail;
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

        findViewById(R.id.login_form).setOnTouchListener(new View.OnTouchListener() {
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
        mRegisterActivateCheckbox = (CheckBox) findViewById(R.id.register_checkBox);
        mUserName = (EditText) findViewById(R.id.register_UserName);
        mRegisterEmail = (EditText) findViewById(R.id.register_Email);


        mRegisterEmail .addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                String registerEmail = mRegisterEmail.getText().toString().trim();

                if (registerEmail.matches(emailPattern) && s.length() > 0)
                {
//                    Toast.makeText(getApplicationContext(),"valid email address",Toast.LENGTH_SHORT).show();
//                    // or
//                    mRegisterEmail.setText("valid email");
                }
                else
                {
                    //or
                    mRegisterEmail.setError("invalid email");
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // other stuffs
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // other stuffs
            }
        });

        Button mRegisterButton = (Button) findViewById(R.id.register_button);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate())
                    RegisterPost();
                else
                    Toast.makeText(getApplicationContext(), "Create Failed", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void RegisterPost() {
        firstName = mFirstName.getText().toString();
        lastName = mLastName.getText().toString();
        password = mRegisterPassword.getText().toString();
        confirmPassword = mConfirmPassword.getText().toString();
        registerEnabled = mRegisterActivateCheckbox.isChecked();
        userName = mUserName.getText().toString();
        email = mRegisterEmail.getText().toString();

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
                    Toast.makeText(getApplicationContext(), "Create Success", Toast.LENGTH_LONG).show();
                    Intent userMain = new Intent(context, AdminMainActivity.class);
                    startActivity(userMain);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Create Failed , User Name or Email Already Taken", Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Create Failed", Toast.LENGTH_LONG).show();
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
        if (email.isEmpty()) {
            mRegisterEmail.setError("Required");
        }
        if(!email.matches(emailPattern))
        {
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
            mConfirmPassword.setError("Confirm & Password Should Be Similar");
            valid = false;
        }
        return valid;
    }

    public void initialize() {
        firstName = mFirstName.getText().toString();
        lastName = mLastName.getText().toString();
        password = mRegisterPassword.getText().toString();
        confirmPassword = mConfirmPassword.getText().toString();
        registerEnabled = mRegisterActivateCheckbox.isChecked();
        userName = mUserName.getText().toString();
        email = mRegisterEmail.getText().toString();
    }


}
