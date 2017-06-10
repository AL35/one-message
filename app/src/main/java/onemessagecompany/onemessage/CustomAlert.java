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
    public TextView description;
    public TextView header;

    public CustomAlert(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_alert_dialog);

//        LinearLayout ll = (LinearLayout) findViewById(R.id.buttons_layout);
//        ll.setAlpha(0.4);

        yes = (ImageButton) findViewById(R.id.customDialogOk);
        no = (ImageButton) findViewById(R.id.customDialogCancel);
        description = (TextView) findViewById(R.id.custom_alert_description);
        header = (TextView) findViewById(R.id.custom_alert_header);

        description.setText("Are you sure you want to delete ?");
        header.setText("Delete Message");

        yes.setOnClickListener(this);
        no.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.customDialogOk:
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