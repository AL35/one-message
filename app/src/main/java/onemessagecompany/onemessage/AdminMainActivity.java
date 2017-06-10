package onemessagecompany.onemessage;

import android.content.Context;
import android.content.Intent;
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
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;

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

public class AdminMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, UsersAdapter.UserAdapterOnClickHandler {


    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private RecyclerView mRecyclerView;
    private UsersAdapter mUsersAdapter;
    private List<User> users ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);

        setContentView(R.layout.activity_admin_main);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.admin_main_activity_drawer_layout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Set Home Page User List Recycle View

        mRecyclerView = (RecyclerView) findViewById(R.id.users_recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setHasFixedSize(true);

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
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.add_users:
                Intent intentAddUser = new Intent(AdminMainActivity.this, AddUser.class);
                startActivity(intentAddUser);
                break;
            case R.id.generate_key:
                Intent intentGenerateKey = new Intent(AdminMainActivity.this, GenerateKey.class);
                startActivity(intentGenerateKey);
                break;
            case R.id.config:
                Intent intentConfig = new Intent(AdminMainActivity.this, ConfigActivity.class);
                startActivity(intentConfig);
                break;
            case R.id.message:
                Intent intentSendMessage = new Intent(AdminMainActivity.this, SendMessageActivity.class);
                startActivity(intentSendMessage);
                break;
            case R.id.logout:
                sharedData.setAccessToken(getApplicationContext(), " ");
                Intent intentLogin = new Intent(AdminMainActivity.this, LoginActivity.class);
                startActivity(intentLogin);
                break;
            case R.id.chat_history:
                Intent intentAdminMessages = new Intent(AdminMainActivity.this, AdminMessageHistoryActivity.class);
                startActivity(intentAdminMessages);
                break;
            case R.id.notifications:
                Intent intentForgetPassword = new Intent(AdminMainActivity.this, ForgetPasswordListActivity.class);
                startActivity(intentForgetPassword);
                break;

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.admin_main_activity_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(User user) {
        Context context = this;
        Intent userDetails = new Intent(context, UserDetailsActivity.class);
        userDetails.putExtra("userDetails", user);
        startActivity(userDetails);
    }
}
