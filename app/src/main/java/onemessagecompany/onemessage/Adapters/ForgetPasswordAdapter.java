package onemessagecompany.onemessage.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import onemessagecompany.onemessage.R;
import onemessagecompany.onemessage.model.Notification;
import onemessagecompany.onemessage.model.User;

/**
 * Created by 52Solution on 8/06/2017.
 */

public class ForgetPasswordAdapter  extends RecyclerView.Adapter<ForgetPasswordAdapter.ForgetPasswordAdapterViewHolder> {


    private final ForgetPasswordAdapterOnClickHandler mClickHandler;

    private Context context;
    private List<Notification> notifications;
    private int rowLayout;

    public interface ForgetPasswordAdapterOnClickHandler {
      void onClick(Notification notification);
    }

    public ForgetPasswordAdapter(ForgetPasswordAdapterOnClickHandler clickHandler) {
      mClickHandler = clickHandler;
    }


    public ForgetPasswordAdapter(List<Notification> notifications, int rowLayout, Context context, ForgetPasswordAdapterOnClickHandler clickHandler) {
      this.notifications = notifications;
      this.rowLayout = rowLayout;
      this.context = context;
      this.mClickHandler=clickHandler;
    }



    public class ForgetPasswordAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
      TextView userName;

      public ForgetPasswordAdapterViewHolder(View view) {
        super(view);
        userName = (TextView) view.findViewById(R.id.item_list_forget_password_username);

        view.setOnClickListener(this);

      }

      @Override
      public void onClick(View v) {
        int adapterPosition = getAdapterPosition();
        Notification notification = notifications.get(adapterPosition);
        mClickHandler.onClick(notification);
      }
    }


    @Override
    public ForgetPasswordAdapter.ForgetPasswordAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

//    Context context = parent.getContext();
//    int layoutIdForListItem = R.layout.list_item_user;
//    LayoutInflater inflater = LayoutInflater.from(context);
//    boolean shouldAttachToParentImmediately = false;
//
//    View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
//    return new UserAdapterViewHolder(view);



      View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
      return new ForgetPasswordAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ForgetPasswordAdapterViewHolder holder, final int position) {

      Notification notification = notifications.get(position);
      holder.userName.setText(notification.getValue()+" Forget Password");

    }


    @Override
    public int getItemCount() {
      return notifications.size();
    }


}
