package onemessagecompany.onemessage.Public;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import onemessagecompany.onemessage.R;
import onemessagecompany.onemessage.model.Message;
import onemessagecompany.onemessage.model.ReplyRequest;
import onemessagecompany.onemessage.rest.ApiClient;
import onemessagecompany.onemessage.rest.RepliesApi;
import onemessagecompany.onemessage.rest.SendMessageApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageDetailsActivity extends AppCompatActivity {

  private Message message;
  private EditText txtReply;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
      WindowManager.LayoutParams.FLAG_SECURE);

    setContentView(R.layout.activity_message_details);

    findViewById(R.id.message_details_form).setOnTouchListener(new View.OnTouchListener() {
      @Override
      public boolean onTouch(View v, MotionEvent event) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
      }
    });

    message = (Message) getIntent().getSerializableExtra("message");

    TextView txtMsg = (TextView) findViewById(R.id.txt_current_Message);
    txtReply = (EditText) findViewById(R.id.txt_sendMessage);

    txtMsg.setText(message.getBody());

    seenMessage();

    ImageButton btnSendReply = (ImageButton) findViewById(R.id.btnSendReply);
    Button btnDelete = (Button) findViewById(R.id.btnDeleteMsg);
    Button btnDismiss = (Button) findViewById(R.id.btnDismiss);

    btnDismiss.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });


    btnDelete.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        deleteMessage();
      }
    });


    btnSendReply.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String reply = txtReply.getText().toString();
        sendReply(reply);
      }
    });

  }

  public void deleteMessage() {
    SendMessageApi apiService = ApiClient.getAuthorizedClient().create(SendMessageApi.class);


    Call<Void> call = apiService.RemoveMessage(message.getID());
    call.enqueue(new Callback<Void>() {
      @Override
      public void onResponse(Call<Void> call, Response<Void> response) {
        int statusCode = response.code();
        if (statusCode == 200) {
          Toast.makeText(getApplicationContext(), "Message Deleted", Toast.LENGTH_LONG).show();
          Intent publicMainLogin = new Intent(MessageDetailsActivity.this, PublicMainActivity.class);
          startActivity(publicMainLogin);
        }
        else
        {
          Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
        }
      }

      @Override
      public void onFailure(Call<Void> call, Throwable t) {

      }
    });
  }

  public void seenMessage() {
    SendMessageApi apiService = ApiClient.getAuthorizedClient().create(SendMessageApi.class);
    ArrayList<Integer> ids = new ArrayList<Integer>();
    ids.add(message.getID());

    Call<Void> call = apiService.SeenMessages(ids);
    call.enqueue(new Callback<Void>() {
      @Override
      public void onResponse(Call<Void> call, Response<Void> response) {
        int statusCode = response.code();
        if (statusCode == 200) {

        }
        else
        {
        }
      }

      @Override
      public void onFailure(Call<Void> call, Throwable t) {

      }
    });

  }

  public void sendReply(String reply) {
    RepliesApi apiService = ApiClient.getAuthorizedClient().create(RepliesApi.class);
    ReplyRequest replyRequest = new ReplyRequest();

    replyRequest.setParentMessageId(message.getID());
    replyRequest.setReplyMessage(reply);

    Call<Void> call = apiService.sendReply(replyRequest);
    call.enqueue(new Callback<Void>() {
      @Override
      public void onResponse(Call<Void> call, Response<Void> response) {
        int statusCode = response.code();
        if (statusCode == 200) {
          Intent publicMainLogin = new Intent(MessageDetailsActivity.this, PublicMainActivity.class);
          startActivity(publicMainLogin);
        } else {
          Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
        }
      }

      @Override
      public void onFailure(Call<Void> call, Throwable t) {

      }
    });
  }

}
