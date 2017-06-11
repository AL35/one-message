
package onemessagecompany.onemessage.Adapters;

  import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
  import java.util.TimeZone;

  import onemessagecompany.onemessage.R;
import onemessagecompany.onemessage.data.MyApplication;
import onemessagecompany.onemessage.model.Message;
import onemessagecompany.onemessage.rest.ApiClient;
import onemessagecompany.onemessage.rest.SendMessageApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 52Solution on 7/06/2017.
 */

public class UserMessageAdapter extends RecyclerView.Adapter<UserMessageAdapter.UserMessageAdapterViewHolder> {


  private final UserMessageAdapterOnClickHandler mClickHandler;

  private Context context;
  private List<Message> messages;
  private int rowLayout;

  public interface UserMessageAdapterOnClickHandler {
    void onClick(Message message);
  }

  public UserMessageAdapter(UserMessageAdapterOnClickHandler clickHandler) {
    mClickHandler = clickHandler;
  }


  public UserMessageAdapter(List<Message> messages, int rowLayout, Context context,UserMessageAdapterOnClickHandler clickHandler) {
    this.messages = messages;
    this.rowLayout = rowLayout;
    this.context = context;
    this.mClickHandler=clickHandler;
  }



  public class UserMessageAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView messageBody;
    TextView messageDate;
    ImageView removemsg;

    public UserMessageAdapterViewHolder(View view) {
      super(view);
      messageBody = (TextView) view.findViewById(R.id.item_list_message_body);
      messageDate = (TextView) view.findViewById(R.id.item_list_message_date);

      view.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
      int adapterPosition = getAdapterPosition();
      Message message = messages.get(adapterPosition);
      mClickHandler.onClick(message);
    }
  }


  @Override
  public UserMessageAdapter.UserMessageAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

//    Context context = parent.getContext();
//    int layoutIdForListItem = R.layout.list_item_user;
//    LayoutInflater inflater = LayoutInflater.from(context);
//    boolean shouldAttachToParentImmediately = false;
//
//    View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
//    return new UserAdapterViewHolder(view);



    View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
    return new UserMessageAdapterViewHolder(view);
  }

  @Override
  public void onBindViewHolder(UserMessageAdapterViewHolder holder, final int position) {

    Message message = messages.get(position);
    holder.messageBody.setText("Tab here to read this message...");




    try {
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
      dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

      Date date = dateFormat.parse(message.getRV());

      SimpleDateFormat dateFormatTime = new SimpleDateFormat("MMM dd,yyyy  hh:mm a");
      String dateTime = dateFormatTime.format(date);

      holder.messageDate.setText(dateTime);

    } catch (ParseException ex) {
    }

  }
  public void deleteMessage(Message  message) {
    SendMessageApi apiService = ApiClient.getAuthorizedClient().create(SendMessageApi.class);


    Call<Void> call = apiService.RemoveMessage(message.getID());
    call.enqueue(new Callback<Void>() {
      @Override
      public void onResponse(Call<Void> call, Response<Void> response) {
        int statusCode = response.code();
        if (statusCode == 200) {

        }
        else
        {
          Toast.makeText(MyApplication.getContext(), "Error", Toast.LENGTH_LONG).show();
        }
      }

      @Override
      public void onFailure(Call<Void> call, Throwable t) {

      }
    });
  }

  @Override
  public int getItemCount() {
    return messages.size();
  }

}
