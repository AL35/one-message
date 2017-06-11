package onemessagecompany.onemessage.Public;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import org.reactivestreams.Subscription;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import onemessagecompany.onemessage.Adapters.AdminMessagsAdapter;
import onemessagecompany.onemessage.Adapters.UserMessageAdapter;
import onemessagecompany.onemessage.Admin.AddUser;
import onemessagecompany.onemessage.Admin.AdminMessageHistoryActivity;
import onemessagecompany.onemessage.Admin.ConfigActivity;
import onemessagecompany.onemessage.Admin.GenerateKey;
import onemessagecompany.onemessage.AdminMainActivity;
import onemessagecompany.onemessage.LoginActivity;
import onemessagecompany.onemessage.R;
import onemessagecompany.onemessage.data.MyApplication;
import onemessagecompany.onemessage.data.sharedData;
import onemessagecompany.onemessage.model.Message;
import onemessagecompany.onemessage.model.MessageResponse;
import onemessagecompany.onemessage.rest.ApiClient;
import onemessagecompany.onemessage.rest.SendMessageApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PublicMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, UserMessageAdapter.UserMessageAdapterOnClickHandler {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private RecyclerView mRecyclerView;
    private AdminMessagsAdapter mAdminMessagsAdapter;
    private Timer timer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);

        setContentView(R.layout.activity_public_main);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.public_main_activity_drawer_layout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //Set Home Page User List Recycle View

        mRecyclerView = (RecyclerView) findViewById(R.id.public_messages_recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setHasFixedSize(true);


        getMessages();

        timer = new Timer();
        TimerTask hourlyTask = new TimerTask() {
            @Override
            public void run() {
                getMessages();
            }
        };
        timer.schedule(hourlyTask, 0l, 10000);
    }


    @Override
    public void onDestroy() {
        if (timer != null)
            timer.cancel();
        super.onDestroy();
    }

    public void getMessages() {
        SendMessageApi apiService = ApiClient.getAuthorizedClient().create(SendMessageApi.class);
        Call<MessageResponse> call = apiService.GetMessages();
        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                int statusCode = response.code();
                if (statusCode == 401 || statusCode == 400) {
                    sharedData.setAccessToken(getApplicationContext(), null);
                    Intent intentLogin = new Intent(PublicMainActivity.this, LoginActivity.class);
                    startActivity(intentLogin);
                    finish();
                } else {
                    List<Message> messages = response.body().getMessages();
                    mRecyclerView.setAdapter(new UserMessageAdapter(messages, R.layout.list_item_message, MyApplication.getContext(), PublicMainActivity.this));
                }
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {

            }
        });
    }

    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.logout:
                if (timer != null)
                    timer.cancel();
                sharedData.setAccessToken(getApplicationContext(), null);
                Intent intentLogin = new Intent(PublicMainActivity.this, LoginActivity.class);
                startActivity(intentLogin);
                finish();
                return true;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.admin_main_activity_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(Message message) {
        Intent msgDetailsLogin = new Intent(PublicMainActivity.this, MessageDetailsActivity.class);
        msgDetailsLogin.putExtra("message", message);
        startActivity(msgDetailsLogin);
    }
}
