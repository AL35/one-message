package onemessagecompany.onemessage.Admin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import onemessagecompany.onemessage.Adapters.ForgetPasswordAdapter;
import onemessagecompany.onemessage.Adapters.UsersAdapter;
import onemessagecompany.onemessage.R;
import onemessagecompany.onemessage.model.Notification;
import onemessagecompany.onemessage.model.NotificationResponse;
import onemessagecompany.onemessage.model.User;
import onemessagecompany.onemessage.model.UsersResponse;
import onemessagecompany.onemessage.rest.ApiClient;
import onemessagecompany.onemessage.rest.ForgetPasswordApi;
import onemessagecompany.onemessage.rest.UsersApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetPasswordListActivity extends AppCompatActivity  implements ForgetPasswordAdapter.ForgetPasswordAdapterOnClickHandler {

  private RecyclerView mRecyclerView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_forget_password_list);


    mRecyclerView = (RecyclerView) findViewById(R.id.forget_password_recycler_view);

    LinearLayoutManager layoutManager= new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

    mRecyclerView.setLayoutManager(layoutManager);

    mRecyclerView.setHasFixedSize(true);



    getNotifications();
  }


  public  void getNotifications()
  {
    ForgetPasswordApi apiService =
      ApiClient.getAuthorizedClient().create(ForgetPasswordApi.class);

    Call<NotificationResponse> call = apiService.GetNotifications();
    call.enqueue(new Callback<NotificationResponse>() {
      @Override
      public void onResponse(Call<NotificationResponse> call, Response<NotificationResponse> response) {
        int statusCode = response.code();
        if (statusCode == 200) {
          NotificationResponse notificationList = response.body();
          mRecyclerView.setAdapter(new ForgetPasswordAdapter(notificationList.getNotifications(), R.layout.list_item_forget_password, getApplicationContext(), ForgetPasswordListActivity.this));
        }
      }

      @Override
      public void onFailure(Call<NotificationResponse> call, Throwable t) {

      }
    });
  }


  @Override
  public void onClick(Notification notification) {
    getUsers(notification.getKey());
  }



  public  void getUsers(String Id)
  {
    UsersApi apiService =
      ApiClient.getAuthorizedClient().create(UsersApi.class);

    Call<UsersResponse> call = apiService.GetUserById(Id,"user");
    call.enqueue(new Callback<UsersResponse>() {
      @Override
      public void onResponse(Call<UsersResponse> call, Response<UsersResponse> response) {
        int statusCode = response.code();
        if (statusCode == 200) {
          UsersResponse user = response.body();
          Context context = ForgetPasswordListActivity.this;

          Intent userDetails = new Intent(context, UserDetailsActivity.class);
          userDetails.putExtra("userDetails", user.getUser());
         startActivity(userDetails);
        }
      }

      @Override
      public void onFailure(Call<UsersResponse> call, Throwable t) {

      }
    });
  }
}
