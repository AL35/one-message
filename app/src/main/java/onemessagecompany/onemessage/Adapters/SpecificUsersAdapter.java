package onemessagecompany.onemessage.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import onemessagecompany.onemessage.R;
import onemessagecompany.onemessage.model.User;

/**
 * Created by 52Solution on 15/06/2017.
 */

public class SpecificUsersAdapter extends RecyclerView.Adapter<SpecificUsersAdapter.SpecificUsersAdapterViewHolder> {


    private Context context;
    private List<User> users;
    private int rowLayout;
    public ArrayList<String> usersSelectedIds = new ArrayList<String>();

    public interface OnItemCheckListener {
        void onItemCheck(User user);

        void onItemUncheck(User user);
    }

    public OnItemCheckListener onItemCheckListener;


    public SpecificUsersAdapter(List<User> users,ArrayList<String> usersSelectedIds, int rowLayout, Context context, OnItemCheckListener onItemCheckListener) {
        this.users = users;
        this.rowLayout = rowLayout;
        this.context = context;
        this.onItemCheckListener = onItemCheckListener;
        this.usersSelectedIds=usersSelectedIds;

    }


    public class SpecificUsersAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView firstLastName;
        TextView userName;
        CheckBox checkbox;
        ImageView userImage;

        public SpecificUsersAdapterViewHolder(View view) {
            super(view);
            firstLastName = (TextView) view.findViewById(R.id.item_list_fistlastname);
            userName = (TextView) view.findViewById(R.id.item_list_username);
            userImage = (ImageView) view.findViewById(R.id.img_user);

            checkbox = (CheckBox) itemView.findViewById(R.id.select_user_checkbox);
            checkbox.setClickable(false);

        }

        public void setOnClickListener(View.OnClickListener onClickListener) {
            itemView.setOnClickListener(onClickListener);
        }

    }


    @Override
    public SpecificUsersAdapter.SpecificUsersAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new SpecificUsersAdapter.SpecificUsersAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SpecificUsersAdapter.SpecificUsersAdapterViewHolder holder, final int position) {

        final User user = users.get(position);
        if(user.getIsEnabled() == true){
            holder.firstLastName.setText(user.getFirstName() + " " + user.getLastName());
            holder.userName.setText(user.getUserName());

            if(user.getIsEnabled() == false)
                holder.userImage.setImageResource(R.drawable.om_disabled_user);

            if(usersSelectedIds.contains(user.getId()))
                holder.checkbox.setChecked(true);

            if (holder instanceof SpecificUsersAdapterViewHolder) {

                ((SpecificUsersAdapterViewHolder) holder).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((SpecificUsersAdapterViewHolder) holder).checkbox.setChecked(
                            !((SpecificUsersAdapterViewHolder) holder).checkbox.isChecked());
                    if (((SpecificUsersAdapterViewHolder) holder).checkbox.isChecked()) {
                        onItemCheckListener.onItemCheck(user);
                    } else {
                        onItemCheckListener.onItemUncheck(user);
                    }
                }
            });
        }

        }


    }


    @Override
    public int getItemCount() {
        return users.size();
    }


}