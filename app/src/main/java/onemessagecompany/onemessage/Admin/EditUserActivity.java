package onemessagecompany.onemessage.Admin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import onemessagecompany.onemessage.AdminMainActivity;
import onemessagecompany.onemessage.BaseActivity;
import onemessagecompany.onemessage.LoginActivity;
import onemessagecompany.onemessage.Public.SendMessageActivity;
import onemessagecompany.onemessage.R;
import onemessagecompany.onemessage.data.sharedData;
import onemessagecompany.onemessage.model.EditUserRequest;
import onemessagecompany.onemessage.model.EditUserResponse;
import onemessagecompany.onemessage.model.User;
import onemessagecompany.onemessage.rest.ApiClient;
import onemessagecompany.onemessage.rest.UsersApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditUserActivity extends AppCompatActivity {

    private User user;
    private EditText mEditUserName;
    private EditText mFirstName;
    private EditText mLastName;
    private CheckBox mRegisterActivateCheckbox;
    private Context context = EditUserActivity.this;
    private String userName;
    private String firstName;
    private String lastName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);

        setContentView(R.layout.activity_edit_user);

        findViewById(R.id.login_form).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                return true;
            }
        });

        User user = (User) getIntent().getSerializableExtra("userDetails");


        mEditUserName = (EditText) findViewById(R.id.edit_UserName);
        mFirstName = (EditText) findViewById(R.id.edit_FirstName);
        mLastName = (EditText) findViewById(R.id.edit_LastName);
        mRegisterActivateCheckbox = (CheckBox) findViewById(R.id.edit_checkBox);

        mEditUserName.setText(user.getUserName());
        mEditUserName.setFocusable(false);
        mFirstName.setText(user.getFirstName());
        mLastName.setText(user.getLastName());
        mRegisterActivateCheckbox.setChecked(user.getIsEnabled());

        Button mRegisterButton = (Button) findViewById(R.id.edit_button);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate())
                    EditPost();
                else
                    Toast.makeText(getApplicationContext(), "Edit Failed", Toast.LENGTH_LONG).show();
            }
        });


    }

    private void EditPost() {

        userName = mEditUserName.getText().toString();
        firstName = mFirstName.getText().toString();
        lastName = mLastName.getText().toString();
        boolean registerEnabled = mRegisterActivateCheckbox.isChecked();


        UsersApi usersApi = ApiClient.getAuthorizedClient().create(UsersApi.class);
        EditUserRequest editUserRequest = new EditUserRequest();

        editUserRequest.setUserName(userName);
        editUserRequest.setFirstName(firstName);
        editUserRequest.setLastName(lastName);
        editUserRequest.setEnabled(registerEnabled);


        usersApi.EditUser(editUserRequest).enqueue(new Callback<EditUserResponse>() {
            @Override
            public void onResponse(Call<EditUserResponse> call, Response<EditUserResponse> response) {
                int statusCode = response.code();
                if (statusCode == 200 && response.body().isUserChanged()) {
                    Toast.makeText(getApplicationContext(), "Saved Success", Toast.LENGTH_LONG).show();
                    Intent userMain = new Intent(context, AdminMainActivity.class);
                    startActivity(userMain);
                }
            }

            @Override
            public void onFailure(Call<EditUserResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Invalid Login username or password", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_send_message:
                Intent intentSendMessage = new Intent(getApplicationContext(), SendMessageActivity.class);
                startActivity(intentSendMessage);
                return true;
            case R.id.action_logout:
                sharedData.setAccessToken(getApplicationContext(), null);
                Intent intentLogin = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intentLogin);
                finish();
                return true;
            default:
                return true;
        }
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("userDetails", user);
        setResult(RESULT_OK, intent);
        finish();
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
        return valid;
    }

    public void initialize() {
        firstName = mFirstName.getText().toString();
        lastName = mLastName.getText().toString();
    }


}
