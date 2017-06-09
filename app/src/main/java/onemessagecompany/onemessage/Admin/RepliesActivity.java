package onemessagecompany.onemessage.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.List;

import onemessagecompany.onemessage.Adapters.AdminMessageRepliesAdapter;
import onemessagecompany.onemessage.Adapters.AdminMessagsAdapter;
import onemessagecompany.onemessage.LoginActivity;
import onemessagecompany.onemessage.R;
import onemessagecompany.onemessage.data.MyApplication;
import onemessagecompany.onemessage.model.AdminRepliesResponse;
import onemessagecompany.onemessage.model.AdminReply;
import onemessagecompany.onemessage.model.Message;
import onemessagecompany.onemessage.model.MessageResponse;
import onemessagecompany.onemessage.rest.ApiClient;
import onemessagecompany.onemessage.rest.RepliesApi;
import onemessagecompany.onemessage.rest.RepliesApi;
import onemessagecompany.onemessage.rest.SendMessageApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RepliesActivity extends AppCompatActivity implements AdminMessageRepliesAdapter.AdminMessageRepliesOnClickHandler {


  private Message message;
  private RecyclerView mRecyclerView;
  private AdminMessageRepliesAdapter madminMessageRepliesAdapter;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
      WindowManager.LayoutParams.FLAG_SECURE);

    setContentView(R.layout.activity_replies);
    message = (Message) getIntent().getSerializableExtra("message");

    TextView admin_msg=(TextView)findViewById(R.id.admin_msg);
    admin_msg.setText(message.getBody());
    //Set Home Page User List Recycle View

    mRecyclerView = (RecyclerView) findViewById(R.id.replies_recycler_view);

    LinearLayoutManager layoutManager= new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

    mRecyclerView.setLayoutManager(layoutManager);

    mRecyclerView.setHasFixedSize(true);

    getReplies();
  }

  public  void getReplies()
  {
    RepliesApi apiService = ApiClient.getAuthorizedClient().create(RepliesApi.class);
    Call<AdminRepliesResponse> call=apiService.GetReplies(String.valueOf(message.getID()));
    call.enqueue(new Callback<AdminRepliesResponse>() {
      @Override
      public void onResponse(Call<AdminRepliesResponse> call, Response<AdminRepliesResponse> response) {
        int statusCode=response.code();
        if (statusCode == 401) {
          Intent intentLogin = new Intent(RepliesActivity.this, LoginActivity.class);
          startActivity(intentLogin);
          finish();
        } else {
          AdminRepliesResponse adminRepliesResponse=response.body();
          List<AdminReply> adminReplies=adminRepliesResponse.getResponse();
          mRecyclerView.setAdapter(new AdminMessageRepliesAdapter(adminReplies,R.layout.sender_chat_list_item, MyApplication.getContext(),RepliesActivity.this));
        }
      }

      @Override
      public void onFailure(Call<AdminRepliesResponse> call, Throwable t) {

      }
    });
  }

  @Override
  public void onClick(AdminReply adminReply) {

  }
}
