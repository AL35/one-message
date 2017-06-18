package onemessagecompany.onemessage.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;

/**
 * Created by 52Solution on 28/05/2017.
 */

public final  class sharedData {
  private static SharedPreferences mySharedPreferences;

  public static void setFirstActivation(Context context, boolean activated) {

    final int mode = context.MODE_PRIVATE;
    mySharedPreferences = context.getSharedPreferences("sharedState", mode);
    SharedPreferences.Editor myEditor = mySharedPreferences.edit();
    myEditor.putBoolean("activated", activated);
    myEditor.commit();

  }

  public static boolean getFirstActivation(Context context) {
    final int mode = context.MODE_PRIVATE;
    mySharedPreferences = context.getSharedPreferences("sharedState", mode);
    return mySharedPreferences.getBoolean("activated", false);
  }

  public static String getVersionNumber(Context context) {
    final int mode = context.MODE_PRIVATE;
    mySharedPreferences = context.getSharedPreferences("sharedState", mode);
    return mySharedPreferences.getString("VersionNumber","1.0.0");
  }

  public static void setAccessToken(Context context, String accessToken) {

    final int mode = context.MODE_PRIVATE;
    Context context1=MyApplication.getContext();
    mySharedPreferences = context1.getSharedPreferences("sharedState", mode);
    SharedPreferences.Editor myEditor = mySharedPreferences.edit();
    myEditor.putString("accessToken", accessToken);
    myEditor.commit();

  }

  public static String getAccessToken(Context context) {
    final int mode = context.MODE_PRIVATE;
    Context context1=MyApplication.getContext();

    mySharedPreferences = context1.getSharedPreferences("sharedState", mode);
    return mySharedPreferences.getString("accessToken"," ");
  }


  public static void setPublicUserAccessToken(Context context, String accessToken) {
    final int mode = context.MODE_PRIVATE;
    Context context1=MyApplication.getContext();
    mySharedPreferences = context1.getSharedPreferences("sharedState", mode);
    SharedPreferences.Editor myEditor = mySharedPreferences.edit();
    myEditor.putString("publicUserAccessToken", accessToken);
    myEditor.commit();

  }

  public static String getPublicUserAccessToken(Context context) {
    final int mode = context.MODE_PRIVATE;
    Context context1=MyApplication.getContext();

    mySharedPreferences = context1.getSharedPreferences("sharedState", mode);
    return mySharedPreferences.getString("publicUserAccessToken"," ");
  }



  public static void setRole(Context context, String role) {

    final int mode = context.MODE_PRIVATE;
    mySharedPreferences = context.getSharedPreferences("sharedState", mode);
    SharedPreferences.Editor myEditor = mySharedPreferences.edit();
    myEditor.putString("role", role);
    myEditor.commit();

  }

  public static String getRole(Context context) {
    final int mode = context.MODE_PRIVATE;
    mySharedPreferences = context.getSharedPreferences("sharedState", mode);
    return mySharedPreferences.getString("role", " ");
  }

  public static void setFirstChangePassword(Context context, boolean passwordChanged) {

    final int mode = context.MODE_PRIVATE;
    mySharedPreferences = context.getSharedPreferences("sharedState", mode);
    SharedPreferences.Editor myEditor = mySharedPreferences.edit();
    myEditor.putBoolean("passwordChanged", passwordChanged);
    myEditor.commit();

  }

  public static boolean getFirstChangePassword(Context context) {
    final int mode = context.MODE_PRIVATE;
    mySharedPreferences = context.getSharedPreferences("sharedState", mode);
    return mySharedPreferences.getBoolean("passwordChanged", false);
  }



}
