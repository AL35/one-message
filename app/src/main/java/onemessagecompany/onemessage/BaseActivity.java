package onemessagecompany.onemessage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import onemessagecompany.onemessage.Public.SendMessageActivity;
import onemessagecompany.onemessage.data.sharedData;

/**
 * Created by 52Solution on 11/06/2017.
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();

        switch (itemId) {
            /*
             * When you click the reset menu item, we want to start all over
             * and display the pretty gradient again. There are a few similar
             * ways of doing this, with this one being the simplest of those
             * ways. (in our humble opinion)
             */
            case R.id.action_send_message:
                Intent intentSendMessage = new Intent(getApplicationContext(), SendMessageActivity.class);
                startActivity(intentSendMessage);
                return true;
            case R.id.action_logout:
                sharedData.setAccessToken(getApplicationContext(), null);
                Intent intentLogin = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intentLogin);

                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
