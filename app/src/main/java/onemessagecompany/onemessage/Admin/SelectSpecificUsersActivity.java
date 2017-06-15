package onemessagecompany.onemessage.Admin;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import onemessagecompany.onemessage.Adapters.SpecificUsersAdapter;
import onemessagecompany.onemessage.Adapters.UsersAdapter;
import onemessagecompany.onemessage.AdminMainActivity;
import onemessagecompany.onemessage.LoginActivity;
import onemessagecompany.onemessage.Public.SendMessageActivity;
import onemessagecompany.onemessage.R;
import onemessagecompany.onemessage.model.User;
import onemessagecompany.onemessage.model.UsersResponse;
import onemessagecompany.onemessage.rest.ApiClient;
import onemessagecompany.onemessage.rest.UsersApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectSpecificUsersActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private SpecificUsersAdapter mSpecificUsersAdapter;
    private List<User> users;
    public ArrayList<String> currentSelectedItems = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_specific_users);

        if ((ArrayList<String>) getIntent().getSerializableExtra("usersIdsList") != null)
            currentSelectedItems = (ArrayList<String>) getIntent().getSerializableExtra("usersIdsList");

        initializeRecycler();
        getUsersList();

        Button btnDone = (Button) findViewById(R.id.btnSelectedDone);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent resultSendMessage = new Intent(SelectSpecificUsersActivity.this, SendMessageActivity.class);
                resultSendMessage.putExtra("usersList", currentSelectedItems);
                setResult(RESULT_OK, resultSendMessage);
                finish();
            }
        });
    }

    public void initializeRecycler() {

        mRecyclerView = (RecyclerView) findViewById(R.id.select_specific_user_recycler_view);

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
                    Intent intentLogin = new Intent(SelectSpecificUsersActivity.this, LoginActivity.class);
                    startActivity(intentLogin);
                    finish();
                } else {
                    users = response.body().getResults();

                    SpecificUsersAdapter myAdapter = new SpecificUsersAdapter(users, currentSelectedItems, R.layout.list_item_specific_user, getApplicationContext(), new SpecificUsersAdapter.OnItemCheckListener() {
                        @Override
                        public void onItemCheck(User item) {
                            currentSelectedItems.add(item.getId());
                        }

                        @Override
                        public void onItemUncheck(User item) {
                            currentSelectedItems.remove(item.getId());
                        }
                    });


                    mRecyclerView.setAdapter(myAdapter);
                }
            }

            @Override
            public void onFailure(Call<UsersResponse> call, Throwable t) {

            }
        });
    }

}
