package ru.jahom.keyboarddetection;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.util.ArraySet;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import java.util.Set;

import ru.jahom.keyboarddetection.detector.GlobalLayoutKeyboardDetector;

/**
 * Created by Anton Knyazev on 08.03.2017.
 */

public class SoftKeyboard  {

  private static SoftKeyboard sInstance;

  private final Set<Listener> mListeners = new ArraySet<>();
  private Activity mActivity;
  private View mContentView;
  private InputMethodManager mInputMethodManager;

  private Detector mDetector;

  public static SoftKeyboard getInstance() {
    if (sInstance == null) {
      sInstance = new SoftKeyboard();
    }
    return sInstance;
  }

  public void start(Activity activity) {
    mActivity = activity;
    mInputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
    mContentView = activity.findViewById(Window.ID_ANDROID_CONTENT);

    mDetector = new GlobalLayoutKeyboardDetector(activity, mContentView);
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
    mContentView = null;
    mInputMethodManager = null;
    mDetector.stop();
    mDetector = null;
  }

  public void show() {
    if (mActivity.hasWindowFocus()) {
      mInputMethodManager.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
    } else {
      mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }
  }

  public void hide(){
    mInputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_IMPLICIT_ONLY);
  }

  public void registerListener(Listener listener) {
    mListeners.add(listener);
  }

  public void unregisterListener(Listener listener) {
    mListeners.remove(listener);
  }

  private void runKeyboardChangeListener(boolean visible) {
    for (Listener listener : mListeners) {
      listener.onKeyboardChange(visible);
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
