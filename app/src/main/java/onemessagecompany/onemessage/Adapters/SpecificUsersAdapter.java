package onemessagecompany.onemessage.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import onemessagecompany.onemessage.R;
import onemessagecompany.onemessage.model.User;

/**
 * Created by 52Solution on 15/06/2017.
 */

public class SpecificUsersAdapter extends RecyclerView.Adapter<SpecificUsersAdapter.SpecificUsersAdapterViewHolder> {

    private final SpecificUsersAdapter.SpecificUsersAdapterOnClickHandler mClickHandler;

    private Context context;
    private List<User> users;
    private int rowLayout;

    public interface SpecificUsersAdapterOnClickHandler {
        void onClick(User user);
    }

    public SpecificUsersAdapter(SpecificUsersAdapter.SpecificUsersAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }


    public SpecificUsersAdapter(List<User> users, int rowLayout, Context context,SpecificUsersAdapter.SpecificUsersAdapterOnClickHandler clickHandler) {
        this.users = users;
        this.rowLayout = rowLayout;
        this.context = context;
        this.mClickHandler=clickHandler;
    }



    public class SpecificUsersAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView firstLastName;
        TextView userName;

        public SpecificUsersAdapterViewHolder(View view) {
            super(view);
            firstLastName = (TextView) view.findViewById(R.id.item_list_fistlastname);
            userName = (TextView) view.findViewById(R.id.item_list_username);

            view.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            User user = users.get(adapterPosition);
            mClickHandler.onClick(user);
        }
    }


    @Override
    public SpecificUsersAdapter.SpecificUsersAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

//    Context context = parent.getContext();
//    int layoutIdForListItem = R.layout.list_item_user;
//    LayoutInflater inflater = LayoutInflater.from(context);
//    boolean shouldAttachToParentImmediately = false;
//
//    View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
//    return new UserAdapterViewHolder(view);



        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new SpecificUsersAdapter.SpecificUsersAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SpecificUsersAdapter.SpecificUsersAdapterViewHolder holder, final int position) {

        User user = users.get(position);
        holder.firstLastName.setText(user.getFirstName() + " " + user.getLastName());
        holder.userName.setText(user.getUserName());

    }


    @Override
    public int getItemCount() {
        return users.size();
    }

}