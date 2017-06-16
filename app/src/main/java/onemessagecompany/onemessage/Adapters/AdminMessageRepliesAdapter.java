package onemessagecompany.onemessage.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import onemessagecompany.onemessage.R;
import onemessagecompany.onemessage.model.AdminReply;

/**
 * Created by 52Solution on 7/06/2017.
 */


public class AdminMessageRepliesAdapter extends RecyclerView.Adapter<AdminMessageRepliesAdapter.AdminMessageRepliesViewHolder> {


  private final AdminMessageRepliesOnClickHandler mClickHandler;

  private Context context;
  private List<AdminReply> adminReplies;
  private int rowLayout;

  public interface AdminMessageRepliesOnClickHandler {
    void onClick(AdminReply adminReply);
  }

  public AdminMessageRepliesAdapter(AdminMessageRepliesOnClickHandler clickHandler) {
    mClickHandler = clickHandler;
  }


  public AdminMessageRepliesAdapter(List<AdminReply> adminReplies, int rowLayout, Context context, AdminMessageRepliesOnClickHandler clickHandler) {
    this.adminReplies = adminReplies;
    this.rowLayout = rowLayout;
    this.context = context;
    this.mClickHandler=clickHandler;
  }



  public class AdminMessageRepliesViewHolder extends RecyclerView.ViewHolder  {
    TextView v1_msg;
    TextView v1_username;

    public AdminMessageRepliesViewHolder(View view) {
      super(view);
      v1_msg = (TextView) view.findViewById(R.id.v1_msg);
      v1_username = (TextView) view.findViewById(R.id.v1_username);

//      view.setOnClickListener(this);

    }
//
//    @Override
//    public void onClick(View v) {
//      int adapterPosition = getAdapterPosition();
//      AdminReply adminReply = adminReplies.get(adapterPosition);
//      mClickHandler.onClick(adminReply);
//    }
  }


  @Override
  public AdminMessageRepliesAdapter.AdminMessageRepliesViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {

//    Context context = parent.getContext();
//    int layoutIdForListItem = R.layout.list_item_user;
//    LayoutInflater inflater = LayoutInflater.from(context);
//    boolean shouldAttachToParentImmediately = false;
//
//    View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
//    return new UserAdapterViewHolder(view);



    View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
    return new AdminMessageRepliesViewHolder(view);
  }

  @Override
  public void onBindViewHolder(AdminMessageRepliesViewHolder holder, final int position) {

    AdminReply adminReply = adminReplies.get(position);
    holder.v1_msg.setText(adminReply.getBody());

    try {
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
      dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
      Date date = dateFormat.parse(adminReply.getRV());
      SimpleDateFormat dateFormatTime = new SimpleDateFormat("MMM dd HH:mm");
      String dateTime = dateFormatTime.format(date);
      holder.v1_username.setText(adminReply.getUserName() + ", " + dateTime);

    } catch (ParseException ex) {
    }

  }


  @Override
  public int getItemCount() {
    return adminReplies.size();
  }

}
