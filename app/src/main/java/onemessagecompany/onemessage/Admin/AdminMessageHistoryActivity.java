package onemessagecompany.onemessage.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.WindowManager;

import java.util.List;

import onemessagecompany.onemessage.Adapters.AdminMessagsAdapter;
import onemessagecompany.onemessage.LoginActivity;
import onemessagecompany.onemessage.R;
import onemessagecompany.onemessage.data.MyApplication;
import onemessagecompany.onemessage.model.Message;
import onemessagecompany.onemessage.model.MessageResponse;
import onemessagecompany.onemessage.rest.ApiClient;
import onemessagecompany.onemessage.rest.SendMessageApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminMessageHistoryActivity extends AppCompatActivity implements AdminMessagsAdapter.AdminMessagsAdapterOnClickHandler {

  private RecyclerView mRecyclerView;
  private AdminMessagsAdapter mAdminMessagsAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
      WindowManager.LayoutParams.FLAG_SECURE);

    setContentView(R.layout.activity_admin_message_history);

    //Set Home Page User List Recycle View

    mRecyclerView = (RecyclerView) findViewById(R.id.messages_recycler_view);

    LinearLayoutManager layoutManager= new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

    mRecyclerView.setLayoutManager(layoutManager);

    mRecyclerView.setHasFixedSize(true);

    getMessages();

  }


  public  void getMessages()
  {
    SendMessageApi apiService = ApiClient.getAuthorizedClient().create(SendMessageApi.class);
    Call<MessageResponse> call=apiService.GetMessages();
    call.enqueue(new Callback<MessageResponse>() {
      @Override
      public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
        int statusCode=response.code();
        if (statusCode == 401) {
          Intent intentLogin = new Intent(AdminMessageHistoryActivity.this, LoginActivity.class);
          startActivity(intentLogin);
          finish();
        } else if(statusCode==200) {
          List<Message> messages = response.body().getMessages();
          mRecyclerView.setAdapter(new AdminMessagsAdapter(messages,R.layout.list_item_admin_message, MyApplication.getContext(),AdminMessageHistoryActivity.this));
        }
        else
        {

        }
      }

      @Override
      public void onFailure(Call<MessageResponse> call, Throwable t) {

      }
    });
  }

  @Override
  public void onClick(Message message) {
    Intent intentReplies = new Intent(AdminMessageHistoryActivity.this, RepliesActivity.class);
    intentReplies.putExtra("message", message);
    startActivity(intentReplies);
  }
}
