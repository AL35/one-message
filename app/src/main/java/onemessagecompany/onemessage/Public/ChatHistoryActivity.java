package onemessagecompany.onemessage.Public;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import onemessagecompany.onemessage.Admin.UserDetailsActivity;
import onemessagecompany.onemessage.LoginActivity;
import onemessagecompany.onemessage.R;
import onemessagecompany.onemessage.data.sharedData;
import onemessagecompany.onemessage.model.SendMessageResponse;
import onemessagecompany.onemessage.model.User;
import onemessagecompany.onemessage.rest.ApiClient;
import onemessagecompany.onemessage.rest.SendMessageApi;
import onemessagecompany.onemessage.rest.UsersApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatHistoryActivity extends AppCompatActivity {
  private TextView tv1;
  private User user;
  private TextView txtMsg;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
      WindowManager.LayoutParams.FLAG_SECURE);

    setContentView(R.layout.activity_chat_history);
    user = (User) getIntent().getSerializableExtra("userDetails");


    Toolbar tb = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(tb);
    getSupportActionBar().setTitle(user.getFirstName());
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    tb.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Context context = ChatHistoryActivity.this;

        Intent userDetails = new Intent(context, UserDetailsActivity.class);
        userDetails.putExtra("userDetails", user);
        startActivity(userDetails);
      }
    });


    ImageView send = (ImageView) findViewById(R.id.btnSend);
    txtMsg = (TextView) findViewById(R.id.txtMsg);


    send.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String msg = txtMsg.getText().toString();


        SendMessageApi apiService =
          ApiClient.getAuthorizedClient().create(SendMessageApi.class);

        Map<String, String> params = new HashMap<String, String>();
        params.put("msg", msg);
        params.put("id", user.getId());

        Call<SendMessageResponse> call = apiService.SendMessage(user.getId(),msg);
        call.enqueue(new Callback<SendMessageResponse>() {
          @Override
          public void onResponse(Call<SendMessageResponse> call, Response<SendMessageResponse> response) {
            int statusCode = response.code();
            String responsemsg = response.body().getResponse();

            Toast.makeText(getApplicationContext(),responsemsg,Toast.LENGTH_LONG).show();
          }

          @Override
          public void onFailure(Call<SendMessageResponse> call, Throwable t) {

          }
        });


      }
    });


  }

  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == 1) {
      if (resultCode == RESULT_OK) {
        String strEditText = data.getStringExtra("editTextValue");
      }
    }
  }

}
