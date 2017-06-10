package onemessagecompany.onemessage.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 52Solution on 3/06/2017.
 */


public class RegisterResponse {


    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public List<String> getModelState() {
        return ModelState;
    }

    public void setModelState(List<String> modelState) {
        ModelState = modelState;
    }

    private String Message ;
    @SerializedName("ModelState ")
    private List<String> ModelState ;



}
