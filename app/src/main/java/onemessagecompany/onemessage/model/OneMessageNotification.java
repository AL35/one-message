package onemessagecompany.onemessage.model;

/**
 * Created by 52Solution on 11/06/2017.
 */

public class OneMessageNotification {

    public String getTitle() {
        return title;
    }

    public void setTitle(String to) {
        this.title = to;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    private String title;
    private String body;

}
