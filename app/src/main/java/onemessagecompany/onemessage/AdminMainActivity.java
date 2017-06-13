package onemessagecompany.onemessage;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import java.util.List;

import onemessagecompany.onemessage.Adapters.UsersAdapter;
import onemessagecompany.onemessage.Admin.AddUser;
import onemessagecompany.onemessage.Admin.AdminMessageHistoryActivity;
import onemessagecompany.onemessage.Admin.ConfigActivity;
import onemessagecompany.onemessage.Admin.ForgetPasswordListActivity;
import onemessagecompany.onemessage.Admin.GenerateKey;
import onemessagecompany.onemessage.Admin.UserDetailsActivity;
import onemessagecompany.onemessage.Public.SendMessageActivity;
import onemessagecompany.onemessage.data.sharedData;
import onemessagecompany.onemessage.model.UsersResponse;
import onemessagecompany.onemessage.model.User;
import onemessagecompany.onemessage.rest.ApiClient;
import onemessagecompany.onemessage.rest.UsersApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminMainActivity extends AppCompatActivity implements UsersAdapter.UserAdapterOnClickHandler {



    private RecyclerView mRecyclerView;
    private UsersAdapter mUsersAdapter;
    private List<User> users ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);

        setContentView(R.layout.activity_admin_main);

//        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction("com.package.ACTION_LOGOUT");
//        registerReceiver(new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                //At this point you should start the login activity and finish this one
//                finish();
//            }
//        }, intentFilter);


        initializeRecycler();

        getUsersList();

        final EditText searchView = (EditText) findViewById(R.id.contact_search);
        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

//                mRecyclerView.setAdapter(mUsersAdapter.);
            }
        });

    }

public void initializeRecycler()
{

    mRecyclerView = (RecyclerView) findViewById(R.id.users_recycler_view);

    LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

    mRecyclerView.setLayoutManager(layoutManager);

    mRecyclerView.setHasFixedSize(true);
}

    public void getUsersList() {
        UsersApi apiService =
                ApiClient.getAuthorizedClient().create(UsersApi.class);

        Call<UsersResponse> call = apiService.GetUsersList();
        call.enqueue(new Callback<UsersResponse>() {
            @Override
            public void onResponse(Call<UsersResponse> call, Response<UsersResponse> response) {
                int statusCode = response.code();
                if (statusCode == 401) {
                    Intent intentLogin = new Intent(AdminMainActivity.this, LoginActivity.class);
                    startActivity(intentLogin);
                    finish();
                } else {
                    users = response.body().getResults();
                    mRecyclerView.setAdapter(new UsersAdapter(users, R.layout.list_item_user, getApplicationContext(), AdminMainActivity.this));
                }
            }

            @Override
            public void onFailure(Call<UsersResponse> call, Throwable t) {

            }
        });
    }


    @Override
    public void onClick(User user) {
        Context context = this;
        Intent userDetails = new Intent(context, UserDetailsActivity.class);
        userDetails.putExtra("userDetails", user);
        startActivity(userDetails);
    }
}
