package onemessagecompany.onemessage.model;

import java.util.ArrayList;

/**
 * Created by 52Solution on 16/06/2017.
 */

public class SendMessageToSpecUsers {

    private String Message;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public ArrayList<String> getUsersId() {
        return UsersId;
    }

    public void setUsersId(ArrayList<String> usersId) {
        UsersId = usersId;
    }

    private ArrayList<String> UsersId;
}
