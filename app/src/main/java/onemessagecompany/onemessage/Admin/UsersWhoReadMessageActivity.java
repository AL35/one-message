package onemessagecompany.onemessage.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;

import onemessagecompany.onemessage.Adapters.UserWhoReadMessageAdapter;
import onemessagecompany.onemessage.Adapters.UsersAdapter;
import onemessagecompany.onemessage.AdminMainActivity;
import onemessagecompany.onemessage.LoginActivity;
import onemessagecompany.onemessage.Public.SendMessageActivity;
import onemessagecompany.onemessage.R;
import onemessagecompany.onemessage.model.Message;
import onemessagecompany.onemessage.model.UserWhoReadMessageResponse;
import onemessagecompany.onemessage.model.UsersResponse;
import onemessagecompany.onemessage.model.UsersWhoReadMessage;
import onemessagecompany.onemessage.rest.ApiClient;
import onemessagecompany.onemessage.rest.UsersApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsersWhoReadMessageActivity extends AppCompatActivity {

    public int msgId = 0;
    public Message message;
    public List<UsersWhoReadMessage> users;
    private RecyclerView mRecyclerView;
    private UserWhoReadMessageAdapter mUsersAdapter;
    public LinearLayout noOneSeen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_who_read_message);

        message = (Message) getIntent().getSerializableExtra("msg");
        if (message != null)
            msgId = message.getID();

        initializeRecycler();

        noOneSeen=(LinearLayout)findViewById(R.id.no_one_seen);


        if (msgId > 0)
            getUsers();
        else
            Toast.makeText(getApplicationContext(), "No Message Selected", Toast.LENGTH_LONG).show();

    }

    public void initializeRecycler() {

        mRecyclerView = (RecyclerView) findViewById(R.id.users_who_read_message_recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setHasFixedSize(true);
    }

    public void getUsers() {
        UsersApi apiService =
                ApiClient.getAuthorizedClient().create(UsersApi.class);

        Call<UserWhoReadMessageResponse> call = apiService.GetUserWhoReadMessage(msgId);
        call.enqueue(new Callback<UserWhoReadMessageResponse>() {
            @Override
            public void onResponse(Call<UserWhoReadMessageResponse> call, Response<UserWhoReadMessageResponse> response) {
                int statusCode = response.code();
                if (statusCode == 401) {
                    Intent intentLogin = new Intent(UsersWhoReadMessageActivity.this, LoginActivity.class);
                    startActivity(intentLogin);
                    finish();
                } else {
                    users = response.body().getUsers();
                    if (users.size() > 0) {
                        mUsersAdapter = new UserWhoReadMessageAdapter(users, R.layout.list_item_user_who_read_message, getApplicationContext());
                        mRecyclerView.setAdapter(mUsersAdapter);
                    }
                    else
                        noOneSeen.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<UserWhoReadMessageResponse> call, Throwable t) {

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

    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("message", message);
        setResult(RESULT_OK, intent);
        finish();
    }
}
