package onemessagecompany.onemessage.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 52Solution on 16/06/2017.
 */

public class UserWhoReadMessageResponse {


    public List<UsersWhoReadMessage> getUsers() {
        return Users;
    }

    public void setUsers(List<UsersWhoReadMessage> users) {
        Users = users;
    }

    @SerializedName("Users")
    private List<UsersWhoReadMessage> Users;
}
