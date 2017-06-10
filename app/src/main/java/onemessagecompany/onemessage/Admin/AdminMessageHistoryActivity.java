package onemessagecompany.onemessage.Admin;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import onemessagecompany.onemessage.Adapters.AdminMessagsAdapter;
import onemessagecompany.onemessage.AdminMainActivity;
import onemessagecompany.onemessage.CustomAlert;
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

public class AdminMessageHistoryActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private AdminMessagsAdapter mAdminMessagsAdapter;
    public Message message;
    public ImageButton imageButtonDeleteMessage;
    public Dialog dialog;
    public TextView description;
    public TextView header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);

        setContentView(R.layout.activity_admin_message_history);

        //Set Home Page User List Recycle View

        mRecyclerView = (RecyclerView) findViewById(R.id.messages_recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setHasFixedSize(true);

        getMessages();
//        initializeDialogAlert();

    }

    public void initializeDialogAlert() {
        dialog = new Dialog(this);


        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_alert_dialog);

        description = (TextView) findViewById(R.id.custom_alert_description);
        header = (TextView) findViewById(R.id.custom_alert_header);

        description.setText("Are you sure you want to delete ?");
        header.setText("Delete Message");
        dialog.findViewById(R.id.customDialogOk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.customDialogCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    public void getMessages() {
        SendMessageApi apiService = ApiClient.getAuthorizedClient().create(SendMessageApi.class);
        Call<MessageResponse> call = apiService.GetMessages();
        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                int statusCode = response.code();
                if (statusCode == 401) {
                    Intent intentLogin = new Intent(AdminMessageHistoryActivity.this, LoginActivity.class);
                    startActivity(intentLogin);
                    finish();
                } else {
                    List<Message> messages = response.body().getMessages();
                    mAdminMessagsAdapter = new AdminMessagsAdapter(messages, R.layout.list_item_admin_message, MyApplication.getContext(),
                            new AdminMessagsAdapter.AdminMessagsAdapterOnClickHandler() {
                                @Override
                                public void onClick(View v, int position) {
                                    onMessageClick(mAdminMessagsAdapter.getSelectedMessage(position));
                                }

                                @Override
                                public void onRemoveMessageClick(View v, int position) {
                                    mAdminMessagsAdapter.deleteMessage(position);
                                }
                            }
                    );
                    mRecyclerView.setAdapter(mAdminMessagsAdapter);
                }
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {

            }
        });
    }

    public void onMessageClick(Message message) {
        Intent intentReplies = new Intent(AdminMessageHistoryActivity.this, RepliesActivity.class);
        intentReplies.putExtra("message", message);
        startActivity(intentReplies);
    }

    public void onRemoveClick(Message message) {

    }
}
