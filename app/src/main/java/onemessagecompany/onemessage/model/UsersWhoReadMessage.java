package onemessagecompany.onemessage.model;

/**
 * Created by 52Solution on 16/06/2017.
 */

public class UsersWhoReadMessage {


    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getSeenDate() {
        return SeenDate;
    }

    public void setSeenDate(String seenDate) {
        SeenDate = seenDate;
    }

    private String UserName;

    private String FirstName;
    private String Id;
    private String LastName;
    private String SeenDate;


}
