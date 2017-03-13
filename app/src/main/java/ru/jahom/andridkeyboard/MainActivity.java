package ru.jahom.andridkeyboard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import ru.jahom.keyboarddetection.SoftKeyboard;

public class MainActivity extends AppCompatActivity implements SoftKeyboard.Listener {

  private SoftKeyboard mSoftKeyboard;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    mSoftKeyboard = new SoftKeyboard();
    mSoftKeyboard.start(this);
    mSoftKeyboard.setListener(this);
    mSoftKeyboard.show();
  }

  @Override
  public void onKeyboardChange(boolean visible) {
    Toast.makeText(this, "visible = "  + visible, Toast.LENGTH_LONG).show();
  }
}
