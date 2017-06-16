package onemessagecompany.onemessage.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import onemessagecompany.onemessage.R;
import onemessagecompany.onemessage.model.User;
import onemessagecompany.onemessage.model.UsersWhoReadMessage;

/**
 * Created by 52Solution on 16/06/2017.
 */

public class UserWhoReadMessageAdapter extends RecyclerView.Adapter<UserWhoReadMessageAdapter.UserWhoReadMessageAdapterViewHolder> {


    private Context context;
    private List<UsersWhoReadMessage> users;
    private int rowLayout;


    public UserWhoReadMessageAdapter(List<UsersWhoReadMessage> users, int rowLayout, Context context) {
        this.users = users;
        this.rowLayout = rowLayout;
        this.context = context;
    }


    public class UserWhoReadMessageAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView firstLastName;
        TextView userName;
        TextView seenDate;

        public UserWhoReadMessageAdapterViewHolder(View view) {
            super(view);
            firstLastName = (TextView) view.findViewById(R.id.item_list_fistlastname);
            userName = (TextView) view.findViewById(R.id.item_list_username);
            seenDate = (TextView) view.findViewById(R.id.item_list_seenDate);


        }
    }


    @Override
    public UserWhoReadMessageAdapter.UserWhoReadMessageAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new UserWhoReadMessageAdapter.UserWhoReadMessageAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserWhoReadMessageAdapter.UserWhoReadMessageAdapterViewHolder holder, final int position) {

        UsersWhoReadMessage user = users.get(position);
        holder.firstLastName.setText(user.getFirstName() + " " + user.getLastName());
        holder.userName.setText(user.getUserName());


        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date date = dateFormat.parse(user.getSeenDate());
            SimpleDateFormat dateFormatTime = new SimpleDateFormat("MMM dd,yyyy  hh:mm a");
            String dateTime = dateFormatTime.format(date);
            holder.seenDate.setText(dateTime);

        } catch (ParseException ex) {
        }
//
//
//        if(user.getIsEnabled() == false)
//        {
//            holder.userImg.setImageResource(R.drawable.om_disabled_user);
//        }

    }


    @Override
    public int getItemCount() {
        return users.size();
    }
}
