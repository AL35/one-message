package onemessagecompany.onemessage.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import onemessagecompany.onemessage.R;
import onemessagecompany.onemessage.model.User;

/**
 * Created by 52Solution on 4/06/2017.
 */

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserAdapterViewHolder> {


  private final UserAdapterOnClickHandler mClickHandler;

  private Context context;
  private List<User> users;
  private int rowLayout;

  public interface UserAdapterOnClickHandler {
    void onClick(User user);
  }

  public UsersAdapter(UserAdapterOnClickHandler clickHandler) {
    mClickHandler = clickHandler;
  }


  public UsersAdapter(List<User> users, int rowLayout, Context context,UserAdapterOnClickHandler clickHandler) {
    this.users = users;
    this.rowLayout = rowLayout;
    this.context = context;
    this.mClickHandler=clickHandler;
  }



  public class UserAdapterViewHolder extends RecyclerView.ViewHolder implements OnClickListener{
    TextView firstLastName;
    TextView userName;

    public UserAdapterViewHolder(View view) {
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
  public UsersAdapter.UserAdapterViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {

//    Context context = parent.getContext();
//    int layoutIdForListItem = R.layout.list_item_user;
//    LayoutInflater inflater = LayoutInflater.from(context);
//    boolean shouldAttachToParentImmediately = false;
//
//    View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
//    return new UserAdapterViewHolder(view);



    View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
    return new UserAdapterViewHolder(view);
  }

  @Override
  public void onBindViewHolder(UserAdapterViewHolder holder, final int position) {

    User user = users.get(position);
    holder.firstLastName.setText(user.getFirstName() + " " + user.getLastName());
    holder.userName.setText(user.getUserName());

  }


  @Override
  public int getItemCount() {
    return users.size();
  }

}
