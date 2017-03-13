package ru.jahom.keyboarddetection;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.util.ArraySet;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

import ru.jahom.keyboarddetection.detector.GlobalLayoutKeyboardDetector;

/**
 * Created by Anton Knyazev on 08.03.2017.
 */

public class SoftKeyboard  {

  private @Nullable Detector mDetector;
  private @Nullable InputMethodManager mInputMethodManager;
  private @Nullable Listener mListener;
  private @Nullable Activity mActivity;

  public SoftKeyboard() {
  }

  public void start(Activity activity) {
    mActivity = activity;
    mInputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
    View contentView = activity.findViewById(Window.ID_ANDROID_CONTENT);

    mDetector = new GlobalLayoutKeyboardDetector(contentView);
    mDetector.setListener(new Listener() {
      @Override
      public void onKeyboardChange(boolean visible) {
        runKeyboardChangeListener(visible);
      }
    });
    mDetector.start();
  }

  public void stop() {
    mActivity = null;
    mInputMethodManager = null;
    if (mDetector != null) {
      mDetector.stop();
      mDetector = null;
    }
  }

  public void show() {
    if (mActivity != null && mInputMethodManager != null) {
      if (mInputMethodManager.isActive()) {
        mInputMethodManager.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
      } else {
        mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
      }
    }
  }

  public void hide(){
    if (mInputMethodManager != null) {
      mInputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }
  }

  public void setListener(@Nullable Listener listener) {
    mListener = listener;
  }


  private void runKeyboardChangeListener(boolean visible) {
    if (mListener != null) {
      mListener.onKeyboardChange(visible);
    }
  }


  public interface Listener {
    void onKeyboardChange(boolean visible);
  }

  public interface Detector {
    void start();
    void stop();
    boolean isKeyboardShown();
    void setListener(@Nullable Listener listener);
  }

}
