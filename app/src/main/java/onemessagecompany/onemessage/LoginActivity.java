package onemessagecompany.onemessage;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.List;

import onemessagecompany.onemessage.Admin.AdminMessageHistoryActivity;
import onemessagecompany.onemessage.Admin.ChangeAdminPasswordActivity;
import onemessagecompany.onemessage.Public.ChangeUserActivityPassword;
import onemessagecompany.onemessage.Public.ForgetPasswordActivity;
import onemessagecompany.onemessage.Public.PublicMainActivity;
import onemessagecompany.onemessage.data.MyApplication;
import onemessagecompany.onemessage.data.sharedData;
import onemessagecompany.onemessage.model.BoolResponse;
import onemessagecompany.onemessage.model.DeviceIdRequest;
import onemessagecompany.onemessage.model.TokenResponse;
import onemessagecompany.onemessage.rest.ApiClient;
import onemessagecompany.onemessage.rest.CheckFirstTimeLoginApi;
import onemessagecompany.onemessage.rest.LoginApi;
import onemessagecompany.onemessage.rest.SendDeviceIdApi;
import onemessagecompany.onemessage.rest.SendMessageApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.READ_CONTACTS;
import static onemessagecompany.onemessage.R.id.email_login_form;
import static onemessagecompany.onemessage.R.id.txt_password_login;
import static onemessagecompany.onemessage.R.id.txt_user_name_login;


public class LoginActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    private static final int REQUEST_READ_CONTACTS = 0;
    private LoginApi loginApi;


    private EditText mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private TextView mForgot_password;
    boolean isFirstLogin = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);

        setContentView(R.layout.activity_login);
        String dsf = sharedData.getAccessToken(getApplicationContext());

        // hide keyboard when touch outside
        findViewById(email_login_form).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                return true;

            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText mNameLogin = (EditText) findViewById(txt_user_name_login);
                EditText mPasswordLogin = (EditText) findViewById(txt_password_login);
                String username = mNameLogin.getText().toString();
                String password = mPasswordLogin.getText().toString();

                LoginPost(username, password);
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        mForgot_password = (TextView) findViewById(R.id.forgot_password);

        mForgot_password.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent publicForgetPassword = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                startActivity(publicForgetPassword);
            }
        });


    }


    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(mEmailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }


    private void LoginPost(String username, String password) {
        showProgress(true);

        loginApi = ApiClient.getClient().create(LoginApi.class);
        loginApi.getTokenAccess(username, password, "password").enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                int statusCode = response.code();
                if (statusCode == 200) {
                    TokenResponse tokenResponse = response.body();
                    setTokenAndRole(tokenResponse);
                    if (sharedData.getRole(MyApplication.getContext()).equals("Administrator"))
                        navigateToMain();
                    else
                        checkFirstLoginChangePassword();
                } else
                    Toast.makeText(getApplicationContext(), "Invalid Login username or password", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Invalid Login username or password", Toast.LENGTH_LONG).show();
                showProgress(false);
            }
        });

    }

    public void checkFirstLoginChangePassword() {

        CheckFirstTimeLoginApi checkFirstTimeLoginApi = ApiClient.getAuthorizedClient().create(CheckFirstTimeLoginApi.class);
        checkFirstTimeLoginApi.CheckFirstTimePasswordChanged().enqueue(new Callback<BoolResponse>() {
            @Override
            public void onResponse(Call<BoolResponse> call, Response<BoolResponse> response) {
                int statusCode = response.code();
                if (statusCode == 200) {
                    isFirstLogin = response.body().isResponse();
                    if (isFirstLogin) {
                        Intent changeUserPassIntent = new Intent(LoginActivity.this, ChangeUserActivityPassword.class);
                        startActivity(changeUserPassIntent);
                        finish();
                    } else
                        navigateToMain();
                }
            }

            @Override
            public void onFailure(Call<BoolResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Invalid Login username or password", Toast.LENGTH_LONG).show();
                showProgress(false);
            }
        });
    }

    public void setTokenAndRole(TokenResponse tokenResponse) {
        sharedData.setAccessToken(getApplicationContext(), tokenResponse.getAccess_token());
        sharedData.setRole(MyApplication.getContext(), tokenResponse.getRole());
    }

    private void navigateToMain() {
        if (!sharedData.getAccessToken(getApplicationContext()).isEmpty() && sharedData.getAccessToken(getApplicationContext()) != " ") {
            SendDeviceId();
            switch (sharedData.getRole(this)) {
                case "Administrator":
                    Intent adminMainIntent = new Intent(LoginActivity.this, AdminMessageHistoryActivity.class);
                    startActivity(adminMainIntent);
                    finish();
                    break;
                default:
                    Intent publicMainPublic = new Intent(LoginActivity.this, PublicMainActivity.class);
                    startActivity(publicMainPublic);
                    finish();
            }
        } else
            Toast.makeText(getApplicationContext(), "Invalid Login username or password", Toast.LENGTH_LONG).show();
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    public void SendDeviceId() {
        String token = FirebaseInstanceId.getInstance().getToken();

        SendDeviceIdApi sendMessageApi = ApiClient.getAuthorizedClient().create(SendDeviceIdApi.class);
        DeviceIdRequest deviceIdRequest = new DeviceIdRequest();

        deviceIdRequest.setDeviceId(token);

        sendMessageApi.SendDeviceId(deviceIdRequest).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                int statusCode = response.code();
                if (statusCode == 200) {

                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    @Override
    public void onBackPressed() {
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("com.package.ACTION_LOGOUT");
        sendBroadcast(broadcastIntent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

}

