package onemessagecompany.onemessage.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.WindowManager;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import onemessagecompany.onemessage.Adapters.AdminMessageRepliesAdapter;
import onemessagecompany.onemessage.BaseActivity;
import onemessagecompany.onemessage.LoginActivity;
import onemessagecompany.onemessage.R;
import onemessagecompany.onemessage.data.MyApplication;
import onemessagecompany.onemessage.model.AdminRepliesResponse;
import onemessagecompany.onemessage.model.AdminReply;
import onemessagecompany.onemessage.model.Message;
import onemessagecompany.onemessage.model.ReplyRequest;
import onemessagecompany.onemessage.rest.ApiClient;
import onemessagecompany.onemessage.rest.RepliesApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RepliesActivity extends BaseActivity implements AdminMessageRepliesAdapter.AdminMessageRepliesOnClickHandler {


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

    TextView tvUsername = (TextView)findViewById(R.id.v1_username);

    try {
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
      dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
      Date date = dateFormat.parse(message.getRV());
      SimpleDateFormat dateFormatTime = new SimpleDateFormat("MMM dd HH:mm");
      String dateTime = dateFormatTime.format(date);

      tvUsername.setText("admin, " + dateTime);


    } catch (ParseException ex) {
    }


    //Set Home Page User List Recycle View

    mRecyclerView = (RecyclerView) findViewById(R.id.replies_recycler_view);

    LinearLayoutManager layoutManager= new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

    mRecyclerView.setLayoutManager(layoutManager);

    mRecyclerView.setHasFixedSize(true);
    try{
      markMessageAsHasNoReplies(message.getID());
    }
    catch(Exception ex){}


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

  public void markMessageAsHasNoReplies(int msgId){
      RepliesApi apiService = ApiClient.getAuthorizedClient().create(RepliesApi.class);

      ReplyRequest replyRequest = new ReplyRequest();
      replyRequest.setParentMessageId(msgId);
      replyRequest.setReplyMessage("nothig");

      Call<Void> call = apiService.MarkMessageAsHasNoReply(replyRequest);
      call.enqueue(new Callback<Void>() {
        @Override
        public void onResponse(Call<Void> call, Response<Void> response) {
          if(response.code() == 200){
            //nothing to say just a mark.
          }
          else{

          }
        }

        @Override
        public void onFailure(Call<Void> call, Throwable t) {

        }
      });
  }

  @Override
  public void onClick(AdminReply adminReply) {

  }
}
