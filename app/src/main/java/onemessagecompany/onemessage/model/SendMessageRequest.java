package onemessagecompany.onemessage.model;

/**
 * Created by 52Solution on 11/06/2017.
 */

public class SendMessageRequest {

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    private String msg;

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    private String to;
}
