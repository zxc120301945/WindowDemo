package com.window.demo.widget;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.window.demo.R;

/**
 * Created by hero on 2018/3/2.
 */

public class WindowView extends LinearLayout {

  private View mDialogView;
  private Context mContext;
  private onDissListener mListener;

  private TextView mViewMsg;
  private float mNoticeViewY;

  public WindowView(Context context) {
    super(context);
    mContext = context;
    initView();
  }

  private void initView() {
    setGravity(Gravity.CENTER);
    setBackgroundColor(Color.TRANSPARENT);
    ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT);
    setLayoutParams(params);

    mDialogView = LayoutInflater.from(mContext).inflate(R.layout.view_window, null);
    LinearLayout.LayoutParams viewLayoutParams = (LinearLayout.LayoutParams) mDialogView.findViewById(R.id.dialog_root_view).getLayoutParams();
    viewLayoutParams.width = 1080;
    mDialogView.findViewById(R.id.dialog_root_view).setLayoutParams(viewLayoutParams);
    mViewMsg = (TextView) mDialogView.findViewById(R.id.dialog_view_msg);

    addView(mDialogView);

  }

  public void setMsg(String msg) {
    mViewMsg.setText(msg);
  }

  private float startY;
  private float moveY = 0;

  @Override
  public boolean onTouchEvent(MotionEvent ev) {
    switch (ev.getAction()) {
      case MotionEvent.ACTION_DOWN:
        startY = ev.getY();
        break;
      case MotionEvent.ACTION_MOVE:
        moveY = startY - ev.getY();
        mDialogView.scrollBy(0, (int) moveY);
        startY = ev.getY();
        if (mDialogView.getScrollY() < 0) {
          mDialogView.scrollTo(0, 0);
        }
        break;
      case MotionEvent.ACTION_UP:
        if (mDialogView.getScrollY() > getHeight() / 4 && moveY > 0) {
          Log.e("up", "true");
          mNoticeViewY = mDialogView.getScrollY();
          animateOut();
//          mListener.onDis();
        } else {
          Log.e("up", "false");
          mDialogView.scrollTo(0, 0);
        }
        break;
    }

    return super.onTouchEvent(ev);
  }

  public interface onDissListener {
    void onDis();
  }

  public void setListener(onDissListener listener) {
    mListener = listener;
  }

  public onDissListener getListener() {
    return mListener;
  }

  /**
   * animate when show in
   */
  public void animateIn() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
      Log.e("mDialogView.getHeight()", mDialogView.getHeight() + "");
      ViewCompat.setTranslationY(mDialogView, -mDialogView.getHeight());
      ViewCompat.animate(mDialogView)
          .alpha(1.0f)
          .translationY(0)
          .setInterpolator(new FastOutSlowInInterpolator())
          .setDuration(300)
          .setListener(new ViewPropertyAnimatorListener() {
            @Override
            public void onAnimationStart(View view) {
              view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(View view) {
              Log.e("view.getY();", mDialogView.getY() + "");
            }

            @Override
            public void onAnimationCancel(View view) {

            }
          }).start();
    }
  }

  /**
   * animate when show out
   */
  public void animateOut() {

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
      Log.e("mDialogView.getHeight()", mDialogView.getHeight() + "");
      ViewCompat.animate(mDialogView)
          .translationY(-mDialogView.getHeight())
          .setInterpolator(new FastOutSlowInInterpolator())
          .setDuration(600)
          .setListener(new ViewPropertyAnimatorListener() {
            @Override
            public void onAnimationStart(View view) {
            }

            @Override
            public void onAnimationEnd(View view) {
              if (mListener != null) {
                mListener.onDis();
              }
              view.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationCancel(View view) {

            }
          }).start();
    }
  }
}
