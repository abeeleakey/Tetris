package com.android.aplas.tetris;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

public class GameView extends View {

	Paint mPaint = new Paint();

	private int mBrickX = 50, mBrickY = 50;

	private final int INCREMENT_LENGTH = 30;

	public static final int DIRECTION_LEFT = 1;
	public static final int DIRECTION_TOP = 2;
	public static final int DIRECTION_RIGHT = 3;
	public static final int DIRECTION_BOTTOM = 4;

	public GameView(Context context) {
		super(context);
		mPaint.setTextSize(20);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);

		canvas.drawText("hello world", mBrickX, mBrickY, mPaint);
	}

	// the direction
	public synchronized void changeBrickPotion(int direction) {
		switch (direction) {
		// move left
		case DIRECTION_LEFT:
			mBrickX -= INCREMENT_LENGTH;
			break;
		// should be transform here
		case DIRECTION_TOP:
			break;
		// move right
		case DIRECTION_RIGHT:
			mBrickX += INCREMENT_LENGTH;
			break;
		// this should down quickly
		case DIRECTION_BOTTOM:
			break;
		}
		postInvalidate();
	}

}
