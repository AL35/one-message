package onemessagecompany.onemessage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import onemessagecompany.onemessage.Adapters.UsersAdapter;
import onemessagecompany.onemessage.Admin.UserDetailsActivity;
import onemessagecompany.onemessage.Public.SendMessageActivity;
import onemessagecompany.onemessage.model.User;
import onemessagecompany.onemessage.model.UsersResponse;
import onemessagecompany.onemessage.rest.ApiClient;
import onemessagecompany.onemessage.rest.UsersApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminMainActivity extends AppCompatActivity implements UsersAdapter.UserAdapterOnClickHandler {

    private RecyclerView mRecyclerView;
    private UsersAdapter mUsersAdapter;
    private List<User> users;
    private ProgressBar mLoadingIndicator;
    public LinearLayout noContacts;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        findViewById(R.id.ac_contacts).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                return true;
            }
        });
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();

        switch (itemId) {
            /*
             * When you click the reset menu item, we want to start all over
             * and display the pretty gradient again. There are a few similar
             * ways of doing this, with this one being the simplest of those
             * ways. (in our humble opinion)
             */
            case R.id.action_send_message:
                Intent intentSendMessage = new Intent(getApplicationContext(), SendMessageActivity.class);
                startActivity(intentSendMessage);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);

        setContentView(R.layout.activity_admin_main);

        initializeRecycler();

        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);
        noContacts = (LinearLayout) findViewById(R.id.no_contact_list);

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

                filter(editable.toString());
            }
        });

    }

    public void initializeRecycler() {

        mRecyclerView = (RecyclerView) findViewById(R.id.users_recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setHasFixedSize(true);
    }

    public void getUsersList() {
        mLoadingIndicator.setVisibility(View.VISIBLE);
        mRecyclerView.setAdapter(null);
        noContacts.setVisibility(View.GONE);

        UsersApi apiService =
                ApiClient.getAuthorizedClient().create(UsersApi.class);

        Call<UsersResponse> call = apiService.GetUsersList(false);
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
                    if (users.size() > 0) {
                        noContacts.setVisibility(View.GONE);
                        mUsersAdapter = new UsersAdapter(users, R.layout.list_item_user, getApplicationContext(), AdminMainActivity.this);
                        mRecyclerView.setAdapter(mUsersAdapter);

                    } else
                        noContacts.setVisibility(View.VISIBLE);

                }
                mLoadingIndicator.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<UsersResponse> call, Throwable t) {

            }
        });
    }

    void filter(String text) {
        List<User> temp = new ArrayList();
        for (User user : users) {
            if (
                    user.getFirstName().toString().toUpperCase().contains(text.toUpperCase())
                            || user.getLastName().toString().toUpperCase().contains(text.toUpperCase())
                            || user.getUserName().toString().toUpperCase().contains(text.toUpperCase())) {
                temp.add(user);
            }
        }
        if (temp.size() > 0) {
            mUsersAdapter.updateList(temp);
            noContacts.setVisibility(View.GONE);
        }else
            noContacts.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(User user) {
        Context context = this;
        Intent userDetails = new Intent(context, UserDetailsActivity.class);
        userDetails.putExtra("userDetails", user);
        startActivity(userDetails);
    }
}
