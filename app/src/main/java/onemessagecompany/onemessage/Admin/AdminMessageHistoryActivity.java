package onemessagecompany.onemessage.Admin;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import onemessagecompany.onemessage.Adapters.AdminMessagsAdapter;
import onemessagecompany.onemessage.AdminMainActivity;
import onemessagecompany.onemessage.BaseActivity;
import onemessagecompany.onemessage.CustomAlert;
import onemessagecompany.onemessage.LoginActivity;
import onemessagecompany.onemessage.Public.SendMessageActivity;
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

public class AdminMessageHistoryActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView mRecyclerView;
    private AdminMessagsAdapter mAdminMessagsAdapter;
    public Message message;
    public ImageButton imageButtonDeleteMessage;
    public Dialog dialog;
    public TextView description;
    public TextView header;
    public int deletingPosition;
    private CustomAlert customAlert;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);

        setContentView(R.layout.activity_admin_message_history);

        initializeNavigation();
        initializeRecycler();
        getMessages();

    }

    public void initializeNavigation()
    {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.admin_chat_history_activity_drawer_layout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(AdminMessageHistoryActivity.this);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        int itemId = item.getItemId();

        switch (itemId) {
            /*
             * When you click the reset menu item, we want to start all over
             * and display the pretty gradient again. There are a few similar
             * ways of doing this, with this one being the simplest of those
             * ways. (in our humble opinion)
             */
            case R.id.action_send_message:
                Intent intentSendMessage = new Intent(this, SendMessageActivity.class);
                startActivity(intentSendMessage);
                finish();
                return true;
            case R.id.action_logout:
                sharedData.setAccessToken(getApplicationContext(), null);
                Intent intentLogin = new Intent(this, LoginActivity.class);
                intentLogin.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(intentLogin);
                finish();
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
                Intent intentAddUser = new Intent(AdminMessageHistoryActivity.this, AddUser.class);
                startActivity(intentAddUser);
                break;
            case R.id.generate_key:
                Intent intentGenerateKey = new Intent(this, GenerateKey.class);
                startActivity(intentGenerateKey);
                break;
            case R.id.config:
                Intent intentConfig = new Intent(this, ConfigActivity.class);
                startActivity(intentConfig);
                break;
            case R.id.message:
                Intent intentSendMessage = new Intent(this, SendMessageActivity.class);
                startActivity(intentSendMessage);
                break;
            case R.id.logout:
                sharedData.setAccessToken(getApplicationContext(), null);
                Intent intentLogin = new Intent(this, LoginActivity.class);
                startActivity(intentLogin);
                finish();
                break;
            case R.id.contact_list:
                Intent intentAdminMain = new Intent(this, AdminMainActivity.class);
                startActivity(intentAdminMain);
                break;
            case R.id.notifications:
                Intent intentForgetPassword = new Intent(this, ForgetPasswordListActivity.class);
                startActivity(intentForgetPassword);
                break;

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.admin_chat_history_activity_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void initializeRecycler() {

        mRecyclerView = (RecyclerView) findViewById(R.id.messages_recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setHasFixedSize(true);

    }

    public void getMessages() {
        SendMessageApi apiService = ApiClient.getAuthorizedClient().create(SendMessageApi.class);
        Call<MessageResponse> call = apiService.GetMessages();
        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                int statusCode = response.code();
                if (statusCode != 200) {
                    Intent intentLogin = new Intent(AdminMessageHistoryActivity.this, LoginActivity.class);
                    startActivity(intentLogin);
                    finish();
                    Toast.makeText(getApplicationContext(), "Session Expired Please Login Again", Toast.LENGTH_LONG).show();

                } else {
                    List<Message> messages = response.body().getMessages();
                    mAdminMessagsAdapter = new AdminMessagsAdapter(messages, R.layout.list_item_admin_message, MyApplication.getContext(),
                            new AdminMessagsAdapter.AdminMessagsAdapterOnClickHandler() {
                                @Override
                                public void onClick(View v, int position) {
                                    onMessageClick(mAdminMessagsAdapter.getSelectedMessage(position));
                                }

                                @Override
                                public void onRemoveMessageClick(View v, int position) {
                                    deletingPosition = position;
                                    customAlert = new CustomAlert(AdminMessageHistoryActivity.this,
                                            "Delete Message",
                                            "Are you sure you want to delete ?",
                                            new CustomAlert.MyDialogListener() {
                                                @Override
                                                public void userSelectedAValue() {
                                                    mAdminMessagsAdapter.deleteMessage(deletingPosition);
                                                }
                                            }
                                    );
                                    customAlert.show();
                                }
                            }
                    );
                    mRecyclerView.setAdapter(mAdminMessagsAdapter);
                }
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {

            }
        });
    }

    public void onMessageClick(Message message) {
        Intent intentReplies = new Intent(AdminMessageHistoryActivity.this, RepliesActivity.class);
        intentReplies.putExtra("message", message);
        startActivity(intentReplies);
    }

    public void onRemoveClick(Message message) {

    }
}
