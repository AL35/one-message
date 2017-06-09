package onemessagecompany.onemessage.data;

import android.app.Application;
import android.content.Context;

/**
 * Created by 52Solution on 7/06/2017.
 */

public class MyApplication extends  Application {

  private static Application sApplication;

  public static Application getApplication() {
    return sApplication;
  }

  public static Context getContext() {
    return getApplication().getApplicationContext();
  }

  @Override
  public void onCreate() {
    super.onCreate();
    sApplication = this;
  }
}
