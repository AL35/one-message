package onemessagecompany.onemessage.Public;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

import onemessagecompany.onemessage.R;
import onemessagecompany.onemessage.data.sharedData;
import onemessagecompany.onemessage.model.SendMessageResponse;
import onemessagecompany.onemessage.rest.ApiClient;
import onemessagecompany.onemessage.rest.SendMessageApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SendMessageActivity extends AppCompatActivity {
  private EditText txtMsg;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
      WindowManager.LayoutParams.FLAG_SECURE);

    setContentView(R.layout.activity_send_message);


    Button btnSnd = (Button) findViewById(R.id.btnSendMessage);


    txtMsg = (EditText) findViewById(R.id.txt_sendMessage);


    btnSnd.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        sendMessage();
      }
    });
  }

  public void sendMessage() {
    String msg = txtMsg.getText().toString();

    SendMessageApi apiService =
      ApiClient.getAuthorizedClient().create(SendMessageApi.class);


    Call<SendMessageResponse> call = apiService.SendAdminMessage(msg);
    call.enqueue(new Callback<SendMessageResponse>() {
      @Override
      public void onResponse(Call<SendMessageResponse> call, Response<SendMessageResponse> response) {
        int statusCode = response.code();
        String responsemsg = response.body().getResponse();

        Toast.makeText(getApplicationContext(), responsemsg, Toast.LENGTH_LONG).show();
        finish();
      }

      @Override
      public void onFailure(Call<SendMessageResponse> call, Throwable t) {

      }
    });
  }
}
