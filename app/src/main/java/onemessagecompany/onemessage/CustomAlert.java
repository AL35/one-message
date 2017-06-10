package onemessagecompany.onemessage;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by 52Solution on 10/06/2017.
 */

public class CustomAlert extends Dialog implements
        android.view.View.OnClickListener {


    public Activity c;
    public Dialog d;
    public ImageButton yes, no;
    public String descriptionTxt;
    public String headerTxt;

    public MyDialogListener myDialogListener;

    public interface MyDialogListener {
        public void userSelectedAValue();
    }


    public CustomAlert(Activity a, String headerTxt, String descriptionTxt, MyDialogListener myDialogListener) {
        super(a);
        this.c = a;
        this.myDialogListener = myDialogListener;


        this.headerTxt = headerTxt;
        this.descriptionTxt=descriptionTxt;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_alert_dialog);

        TextView description = (TextView) findViewById(R.id.custom_alert_description);
        TextView header = (TextView) findViewById(R.id.custom_alert_header);

        description.setText(descriptionTxt);
        header.setText(headerTxt);

        yes = (ImageButton) findViewById(R.id.customDialogOk);
        no = (ImageButton) findViewById(R.id.customDialogCancel);


        yes.setOnClickListener(this);
        no.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.customDialogOk:
                myDialogListener.userSelectedAValue();
                c.finish();
                break;
            case R.id.customDialogCancel:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }
}