package ru.jahom.keyboarddetection.detector;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * Created by Anton Knyazev on 08.03.2017.
 */

public class GlobalLayoutKeyboardDetector extends BaseDetector implements ViewTreeObserver.OnGlobalLayoutListener {

  private Activity mActivity;
  private View mContentView;
  private int mInitialValue;
  private boolean mHasSentInitialAction;
  private boolean mIsKeyboardShown;

  private Rect mRect = new Rect();

  public GlobalLayoutKeyboardDetector(Activity activity, View contentView) {
    mActivity = activity;
    mContentView = contentView;
  }

  public void start() {
    mContentView.getViewTreeObserver().addOnGlobalLayoutListener(this);
  }

  public void stop() {
    if (Build.VERSION.SDK_INT >= 16) {
      mContentView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
    } else {
      mContentView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
    }
  }

  @Override
  public void onGlobalLayout() {
    if (mInitialValue == 0) {
      mInitialValue = getValue();
    } else {
      int value = getValue();
      if (mInitialValue > value) {
        if (!mHasSentInitialAction || !mIsKeyboardShown) {
          mIsKeyboardShown = true;
          onKeyboardChange(true);
        }

      } else {
        if (!mHasSentInitialAction || mIsKeyboardShown) {
          mIsKeyboardShown = false;
          mContentView.post(new Runnable() {
            @Override
            public void run() {
              onKeyboardChange(false);
            }
          });
        }
      }
      mHasSentInitialAction = true;
    }
  }

  private int getValue() {
    mContentView.getWindowVisibleDisplayFrame(mRect);
    return mRect.height();
  }

  @Override
  public boolean isKeyboardShown() {
    return mIsKeyboardShown;
  }
}
