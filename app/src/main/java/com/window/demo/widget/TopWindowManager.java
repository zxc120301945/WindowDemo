package com.window.demo.widget;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.WindowManager;

/**
 * Created by hero on 2018/3/2.
 */

public class TopWindowManager implements WindowView.onDissListener {

  private static WindowView mView;
  private static WindowManager mWindowManager;

  public void showWindow(Context context) {
    Context mCtx = context.getApplicationContext();
    mWindowManager = (WindowManager) mCtx.getSystemService(Context.WINDOW_SERVICE);
    mView = new WindowView(context);;
    WindowManager.LayoutParams mWindowParams = new WindowManager.LayoutParams();
    mWindowParams.type = WindowManager.LayoutParams.TYPE_TOAST;// 系统提示window
    mWindowParams.width = WindowManager.LayoutParams.MATCH_PARENT;
    mWindowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
    mWindowParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
    | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
    // 不设置这个弹出框的透明遮罩显示为黑色
    mWindowParams.format = PixelFormat.TRANSLUCENT;
    mWindowParams.gravity = Gravity.TOP;
    mWindowManager.addView(mView, mWindowParams);//添加窗口
    mView.setListener(this);
    mView.postDelayed(new Runnable() {
      @Override
      public void run() {
        mView.animateIn();
      }
    },500);
  }

  /**
   * 隐藏弹出框
   */
  public void hidePopupWindow() {
    mView.postDelayed(new Runnable() {
      @Override
      public void run() {
        mView.animateOut();
      }
    },500);

  }

  @Override
  public void onDis() {
    if(mWindowManager != null){
      mWindowManager.removeView(mView);
    }
  }
}
