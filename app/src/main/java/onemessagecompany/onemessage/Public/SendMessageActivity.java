package onemessagecompany.onemessage.Public;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;

import onemessagecompany.onemessage.Admin.SelectSpecificUsersActivity;
import onemessagecompany.onemessage.R;
import onemessagecompany.onemessage.model.SendMessageRequest;
import onemessagecompany.onemessage.model.SendMessageResponse;
import onemessagecompany.onemessage.model.SendMessageToSpecUsers;
import onemessagecompany.onemessage.rest.ApiClient;
import onemessagecompany.onemessage.rest.SendMessageApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SendMessageActivity extends AppCompatActivity {
    private EditText txtMsg;
    int count = 0;
    private ArrayList<String> usersIdsList =new ArrayList<String>();

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

        findViewById(R.id.v1_admin).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.showSoftInput(getCurrentFocus().findFocus(), InputMethodManager.SHOW_IMPLICIT);
                return true;
            }
        });

        Button btnSnd = (Button) findViewById(R.id.btnSendMessage);
        Button btnSndSelected = (Button) findViewById(R.id.btnSendSpecific);


        txtMsg = (EditText) findViewById(R.id.txt_sendMessage);


        btnSnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String msg = txtMsg.getText().toString();
                if (!msg.isEmpty())
                    sendMessage();
                else
                    Toast.makeText(getApplicationContext(), "Please type a message first", Toast.LENGTH_LONG).show();

            }
        });

        btnSndSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String msg = txtMsg.getText().toString();
                if (!msg.isEmpty())
                    if (usersIdsList.size() < 1)
                        Toast.makeText(getApplicationContext(), "Please select at least one user", Toast.LENGTH_LONG).show();
                    else
                        sendSelectedUsersMessage();
                else
                    Toast.makeText(getApplicationContext(), "Please type a message first", Toast.LENGTH_LONG).show();

            }
        });


        Button btnSelectUsers = (Button) findViewById(R.id.btnSelectSpecificUser);
        btnSelectUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentSelectUsers = new Intent(getApplicationContext(), SelectSpecificUsersActivity.class);
                intentSelectUsers.putExtra("usersIdsList", usersIdsList);
                startActivityForResult(intentSelectUsers, 1);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
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


    public void sendMessage() {
        String msg = txtMsg.getText().toString();

        SendMessageApi apiService =
                ApiClient.getAuthorizedClient().create(SendMessageApi.class);
        SendMessageRequest sendMessageRequest = new SendMessageRequest();
        sendMessageRequest.setMsg(msg);
        sendMessageRequest.setTo("Key=" + FirebaseInstanceId.getInstance().getToken());


        Call<SendMessageResponse> call = apiService.SendAdminMessage(sendMessageRequest);
        call.enqueue(new Callback<SendMessageResponse>() {
            @Override
            public void onResponse(Call<SendMessageResponse> call, Response<SendMessageResponse> response) {
                int statusCode = response.code();
                if (statusCode == 200) {
                    String responsemsg = response.body().getResponse();
                    Toast.makeText(getApplicationContext(), responsemsg, Toast.LENGTH_LONG).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<SendMessageResponse> call, Throwable t) {

            }
        });
    }


    public void sendSelectedUsersMessage() {
        String msg = txtMsg.getText().toString();

        SendMessageApi apiService =
                ApiClient.getAuthorizedClient().create(SendMessageApi.class);
        SendMessageToSpecUsers sendMessageToSpecUsers = new SendMessageToSpecUsers();
        sendMessageToSpecUsers.setMessage(msg);
        sendMessageToSpecUsers.setUsersId(usersIdsList);


        Call<Void> call = apiService.SendMessageToSpecificUsers(sendMessageToSpecUsers);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                int statusCode = response.code();
                if (statusCode == 200) {
                    Toast.makeText(getApplicationContext(), "Message Sent", Toast.LENGTH_LONG).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                usersIdsList = (ArrayList<String>) data.getSerializableExtra("usersList");
            }
        }
    }
}
