package onemessagecompany.onemessage.Public;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.reactivestreams.Subscription;

import java.util.List;

import onemessagecompany.onemessage.Adapters.AdminMessagsAdapter;
import onemessagecompany.onemessage.Adapters.UserMessageAdapter;
import onemessagecompany.onemessage.Admin.AddUser;
import onemessagecompany.onemessage.Admin.AdminMessageHistoryActivity;
import onemessagecompany.onemessage.Admin.ConfigActivity;
import onemessagecompany.onemessage.Admin.GenerateKey;
import onemessagecompany.onemessage.AdminMainActivity;
import onemessagecompany.onemessage.LoginActivity;
import onemessagecompany.onemessage.NetworkStateReceiver;
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

public class PublicMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, UserMessageAdapter.UserMessageAdapterOnClickHandler, NetworkStateReceiver.NetworkStateReceiverListener {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private RecyclerView mRecyclerView;
    private Context context = MyApplication.getContext();
    public LinearLayout noMessages;
    private ProgressBar mLoadingIndicator;
    private NetworkStateReceiver networkStateReceiver;
    public LinearLayout noConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);

        setContentView(R.layout.activity_public_main);

        initializeMenu();

        noMessages = (LinearLayout) findViewById(R.id.public_messages);
        noConnection = (LinearLayout) findViewById(R.id.public_messages_no_connection);
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);


        registerInNetworkReciver();

        initializeRecycler();
        getMessages();



    }

    public void registerInNetworkReciver() {
        networkStateReceiver = new NetworkStateReceiver();
        networkStateReceiver.addListener(this);
        this.registerReceiver(networkStateReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));
    }


    public void initializeMenu() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.public_main_activity_drawer_layout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void initializeRecycler() {

        mRecyclerView = (RecyclerView) findViewById(R.id.public_messages_recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setHasFixedSize(true);
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }

    public void getMessages() {
        mLoadingIndicator.setVisibility(View.VISIBLE);
        noMessages.setVisibility(View.GONE);
        mRecyclerView.setAdapter(null);

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
                } else if (statusCode == 200) {
                    mLoadingIndicator.setVisibility(View.INVISIBLE);

                    List<Message> messages = response.body().getMessages();
                    if (messages.size() > 0) {
                        noMessages.setVisibility(View.GONE);
                        mRecyclerView.setAdapter(new UserMessageAdapter(messages, R.layout.list_item_message, MyApplication.getContext(), PublicMainActivity.this));
                    } else
                        noMessages.setVisibility(View.VISIBLE);
                }
                mLoadingIndicator.setVisibility(View.GONE);
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


    //register your activity onResume()
    @Override
    public void onResume() {
        super.onResume();
        context.registerReceiver(mMessageReceiver, new IntentFilter("Send"));
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getMessages();
    }

    //Must unregister onPause()
    @Override
    protected void onPause() {
        super.onPause();
        context.unregisterReceiver(mMessageReceiver);
    }


    //This is the handler that will manager to process the broadcast intent
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();
            if (action.equals("Send")) {
                getMessages();
            }
        }
    };

    @Override
    public void networkAvailable() {
        noConnection.setVisibility(View.GONE);
        getMessages();
    }

    @Override
    public void networkUnavailable() {
        noConnection.setVisibility(View.VISIBLE);
        noMessages.setVisibility(View.GONE);
        mRecyclerView.setAdapter(null);
    }

}
