package onemessagecompany.onemessage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.WindowManager;
import android.widget.EditText;

import java.util.List;

import onemessagecompany.onemessage.Adapters.UsersAdapter;
import onemessagecompany.onemessage.Admin.UserDetailsActivity;
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
    private List<User> users ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);

        setContentView(R.layout.activity_admin_main);

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
