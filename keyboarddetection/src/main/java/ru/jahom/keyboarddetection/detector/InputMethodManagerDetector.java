package ru.jahom.keyboarddetection.detector;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by Anton Knyazev on 08.03.2017.
 */

public class InputMethodManagerDetector extends BaseDetector {

  private Activity mActivity;
  private Handler _keyboardHandler = new Handler();
  private boolean _keyboardVisible;

  public InputMethodManagerDetector(Activity activity) {
    mActivity = activity;
  }

  @Override
  public void start() {
    View view = mActivity.getCurrentFocus();
    if (view != null)
    {
      InputMethodManager imm = (InputMethodManager) mActivity.getSystemService( Activity.INPUT_METHOD_SERVICE );
      imm.hideSoftInputFromWindow( view.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY, _keyboardResultReceiver );
      imm.showSoftInput( view, InputMethodManager.SHOW_IMPLICIT, _keyboardResultReceiver );
    }
    else // if (_keyboardVisible)
    {
      _keyboardVisible = false;
    }
  }

  @Override
  public void stop() {

  }

  @Override
  public boolean isKeyboardShown() {
    return false;
  }

  private ResultReceiver _keyboardResultReceiver = new ResultReceiver( _keyboardHandler )
  {
    @Override
    protected void onReceiveResult( int resultCode, Bundle resultData )
    {
      switch (resultCode)
      {
        case InputMethodManager.RESULT_SHOWN :
        case InputMethodManager.RESULT_UNCHANGED_SHOWN :
          // if (!_keyboardVisible)
        {
          _keyboardVisible = true;
          onKeyboardChange(true);
        }
        break;
        case InputMethodManager.RESULT_HIDDEN :
        case InputMethodManager.RESULT_UNCHANGED_HIDDEN :
          // if (_keyboardVisible)
        {
          _keyboardVisible = false;
          onKeyboardChange(false);
        }
        break;
      }
    }
  };

}
