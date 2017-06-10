package onemessagecompany.onemessage.Admin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import onemessagecompany.onemessage.AdminMainActivity;
import onemessagecompany.onemessage.CustomAlert;
import onemessagecompany.onemessage.R;
import onemessagecompany.onemessage.data.sharedData;
import onemessagecompany.onemessage.model.DeleteAccountRequest;
import onemessagecompany.onemessage.model.User;
import onemessagecompany.onemessage.rest.ApiClient;
import onemessagecompany.onemessage.rest.UsersApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserDetailsActivity extends AppCompatActivity {
    private User user;
    private CustomAlert customAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);

        setContentView(R.layout.activity_user_details);
        user = (User) getIntent().getSerializableExtra("userDetails");


        Toolbar tb = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        getSupportActionBar().setTitle(user.getFirstName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button editUser = (Button) findViewById(R.id.btnEditUser);
        editUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = UserDetailsActivity.this;

                Intent userDetails = new Intent(context, EditUserActivity.class);
                userDetails.putExtra("userDetails", user);
                startActivity(userDetails);
            }
        });

        customAlert = new CustomAlert(UserDetailsActivity.this,
                "Delete User",
                "Are you sure you want to delete ?",
                new CustomAlert.MyDialogListener() {
            @Override
            public void userSelectedAValue() {
                deleteUserPost();
            }
        }) ;

        final Button changePassword = (Button) findViewById(R.id.btnEditPassword);
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = UserDetailsActivity.this;

                Intent userDetails = new Intent(context, ChangePasswordActivity.class);
                userDetails.putExtra("userDetails", user);
                startActivity(userDetails);
            }
        });



        final Button deleteUser = (Button) findViewById(R.id.btnDeleteUser);
        deleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customAlert.show();
            }
        });
    }

    private void deleteUserPost() {


        UsersApi usersApi = ApiClient.getAuthorizedClient().create(UsersApi.class);
        DeleteAccountRequest deleteAccountRequest = new DeleteAccountRequest();

        deleteAccountRequest.setUserName(user.getUserName());
        usersApi.DeleteAccount(deleteAccountRequest).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                int statusCode = response.code();
                if (statusCode == 200) {
                    Toast.makeText(getApplicationContext(), "Deleted Success", Toast.LENGTH_LONG).show();
                    Intent MainActivity = new Intent(UserDetailsActivity.this, AdminMainActivity.class);
                    startActivity(MainActivity);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                user = (User) getIntent().getSerializableExtra("userDetails");
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

}
