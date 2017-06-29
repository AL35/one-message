package onemessagecompany.onemessage.Public;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import onemessagecompany.onemessage.R;
import onemessagecompany.onemessage.data.MyApplication;
import onemessagecompany.onemessage.data.sharedData;
import onemessagecompany.onemessage.model.ChangeAdminPasswordRequest;
import onemessagecompany.onemessage.rest.ApiClient;
import onemessagecompany.onemessage.rest.CheckFirstTimeLoginApi;
import onemessagecompany.onemessage.rest.UsersApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangeUserActivityPassword extends AppCompatActivity {

    private TextInputLayout mOldPassword;
    private TextInputLayout mPassword;
    private TextInputLayout mConfirmPassword;


    private String oldPassword;
    private String password;
    private String confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_user_password);

        findViewById(R.id.ac_change_user_password_form).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                return true;
            }
        });

        mOldPassword = (TextInputLayout) findViewById(R.id.old_user_password);
        mPassword = (TextInputLayout) findViewById(R.id.edit_user_password);
        mConfirmPassword = (TextInputLayout) findViewById(R.id.edit_user_confirmPassword);



        Button mRegisterButton = (Button) findViewById(R.id.change_user_password_button);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate()){

                    oldPassword = mOldPassword.getEditText().getText().toString();
                    password = mPassword.getEditText().getText().toString();
                    confirmPassword = mConfirmPassword.getEditText().getText().toString();

                    ChangeAdminPasswordPOST(oldPassword,password,confirmPassword);
                }

            }
        });
    }

    public void ChangeAdminPasswordPOST(String strOldPassword, String strNewPassword, String strConfirmPassword)
    {
        UsersApi usersApi = ApiClient.getAuthorizedClient().create(UsersApi.class);

        ChangeAdminPasswordRequest changeAdminPasswordRequest = new ChangeAdminPasswordRequest();

        changeAdminPasswordRequest.setOldPassword(strOldPassword);
        changeAdminPasswordRequest.setNewPassword(strNewPassword);
        changeAdminPasswordRequest.setConfirmPassword(strConfirmPassword);

        usersApi.ChangeAdminPassword(changeAdminPasswordRequest).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                int statusCode = response.code();
                if (statusCode == 200) {
                    Toast.makeText(getApplicationContext(), "Password Changed Successfully", Toast.LENGTH_LONG).show();
                    setFirstLogin();
                    //sharedData.setFirstChangePassword(MyApplication.getContext(),true);
                    //Intent intentPublicMain = new Intent(getApplicationContext(), PublicMainActivity.class);
                    //startActivity(intentPublicMain);
                    //finish();

                }
                else{
                    Toast.makeText(getApplicationContext(), "Incorrect password", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Unexpected error occurred, may be network related", Toast.LENGTH_LONG).show();
            }
        });

    }//end ChangeAdminPasswordPOST

    public void  setFirstLogin()
    {
        CheckFirstTimeLoginApi checkFirstTimeLoginApi = ApiClient.getAuthorizedClient().create(CheckFirstTimeLoginApi.class);
        checkFirstTimeLoginApi.SetFirstTimePasswordChanged().enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                int statusCode = response.code();
                if (statusCode == 200) {
                    sharedData.setFirstChangePassword(MyApplication.getContext(),true);
                    Intent intentPublicMain = new Intent(getApplicationContext(), PublicMainActivity.class);
                    startActivity(intentPublicMain);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
            }
        });


    }

    public boolean validate() {
        initialize();
        boolean valid = true;

        if (oldPassword.isEmpty()) {
            mOldPassword.setError("Required");
            valid = false;
        }
        else {
            mOldPassword.setError(null);
        }
        if (password.isEmpty() || password.length() < 6) {
            mPassword.setError("Required & Should not be less than 6 characters");
            valid = false;
        }
        else {
            if(password.equals(oldPassword)){
                mPassword.setError("Old Password cannot be the same like New Password");
                valid = false;
            }
            else
                mPassword.setError(null);
        }
        if (confirmPassword.isEmpty() || confirmPassword.length() < 6) {
            mConfirmPassword.setError("Required & Should not be less than 6 characters");
            valid = false;
        }
        else{
            mConfirmPassword.setError(null);
        }
        if (!confirmPassword.equals(password)) {
            mConfirmPassword.setError("Confirm & Password must be the same");
            valid = false;
        }
        return valid;
    }

    public void initialize() {

        oldPassword = mOldPassword.getEditText().getText().toString();
        password = mPassword.getEditText().getText().toString();
        confirmPassword = mConfirmPassword.getEditText().getText().toString();
    }
}
