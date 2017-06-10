package onemessagecompany.onemessage.Public;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import onemessagecompany.onemessage.R;
import onemessagecompany.onemessage.model.SendMessageRequest;
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

        findViewById(R.id.login_form).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                return true;
            }
        });
        Button btnSnd = (Button) findViewById(R.id.btnSendMessage);


        txtMsg = (EditText) findViewById(R.id.txt_sendMessage);


        btnSnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg=txtMsg.getText().toString();
                if (!msg.isEmpty())
                    sendMessage();
                else
                    Toast.makeText(getApplicationContext(), "Message Required", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void sendMessage() {
        String msg = txtMsg.getText().toString();

        SendMessageApi apiService =
                ApiClient.getAuthorizedClient().create(SendMessageApi.class);
        SendMessageRequest sendMessageRequest = new SendMessageRequest();
        sendMessageRequest.setMsg(msg);
        Call<SendMessageResponse> call = apiService.SendAdminMessage(sendMessageRequest);
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
