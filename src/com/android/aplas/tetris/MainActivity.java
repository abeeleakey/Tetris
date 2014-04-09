package com.android.aplas.russiabrick;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;

public class MainActivity extends Activity implements
		android.view.GestureDetector.OnGestureListener, OnTouchListener {

	private final static String TAG = "main activity --->";

	final int FLING_MINI_DISTANCE = 25;

	private GestureDetector mGestureDector;
	private GameView mGameView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_main);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);

		mGestureDector = new GestureDetector(this, this);
		mGameView = new GameView(this);
		setContentView((View) mGameView);
		mGameView.setOnTouchListener(this);
		mGameView.setLongClickable(true);
	}

	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		Log.d(TAG, "on Down");
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
		Log.d(TAG, "on show press");
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		Log.d(TAG, "on single tap up");
		return false;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		Log.d(TAG, "on scroll");
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
		Log.d(TAG, "on long press");
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
		Log.d(TAG, "on fling");
		float e1x = e1.getX();
		float e2x = e2.getX();
		float e1Y = e1.getY();
		float e2Y = e2.getY();
		if (Math.abs(e1x - e2x) > Math.abs(e2Y - e1Y)) { // means fling on x
			mGameView.changeBrickPotion(mGameView.DIRECTION_RIGHT);
		}

		return false;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		// return false;
		return mGestureDector.onTouchEvent(event);
	}

}
