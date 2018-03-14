package com.window.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.window.demo.widget.TopWindowManager;
import com.window.demo.widget.WindowView;

public class MainActivity extends AppCompatActivity {

  private TopWindowManager mTopWindowManager;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    mTopWindowManager = new TopWindowManager();

    findViewById(R.id.button_show).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        mTopWindowManager.showWindow(MainActivity.this);
      }
    });

    findViewById(R.id.button_hide).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        mTopWindowManager.hidePopupWindow();
      }
    });
  }
}
