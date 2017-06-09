package onemessagecompany.onemessage;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;

/**
 * Created by 52Solution on 3/06/2017.
 */


public class NumberPickerCustom extends android.widget.NumberPicker {

  public NumberPickerCustom(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override
  public void addView(View child) {
    super.addView(child);
    updateView(child);
  }

  @Override
  public void addView(View child, int index, android.view.ViewGroup.LayoutParams params) {
    super.addView(child, index, params);
    updateView(child);
  }

  @Override
  public void addView(View child, android.view.ViewGroup.LayoutParams params) {
    super.addView(child, params);
    updateView(child);
  }

  private void updateView(View view) {
    if(view instanceof EditText){
      ((EditText) view).setTextSize(25);
      ((EditText) view).setTextColor(Color.parseColor("#333333"));
    }
  }

}
