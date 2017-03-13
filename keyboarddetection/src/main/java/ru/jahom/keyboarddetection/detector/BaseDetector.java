package ru.jahom.keyboarddetection.detector;

import android.support.annotation.Nullable;

import ru.jahom.keyboarddetection.SoftKeyboard;

/**
 * Created by Anton Knyazev on 08.03.2017.
 */

public abstract class BaseDetector implements SoftKeyboard.Detector {
  @Nullable
  private SoftKeyboard.Listener mListener;

  @Override
  public void setListener(@Nullable SoftKeyboard.Listener listener) {
    mListener = listener;
  }

  protected void onKeyboardChange(boolean visible) {
    if (mListener != null) {
      mListener.onKeyboardChange(visible);
    }
  }

}
