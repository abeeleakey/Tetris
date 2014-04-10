package com.android.aplas.tetris;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

	private final static String DEBUG_TAG = "game view --->";

	public static final int DIRECTION_LEFT = 1;
	public static final int DIRECTION_TOP = 2;
	public static final int DIRECTION_RIGHT = 3;
	public static final int DIRECTION_BOTTOM = 4;

	private int mBrickX = 50, mBrickY = 50;

	private final int INCREMENT_LENGTH = 30;

	private Paint mPaint = new Paint();
	GamaePaintThread mGameThread = null;
	private SurfaceHolder mSurfaceHolder;

	private Map mMap = null;

	ArrayList<Layer> mDrawableObjects = new ArrayList<Layer>();

	public GameView(Context context) {
		super(context);
		mPaint.setTextSize(20);
		mSurfaceHolder = getHolder();
		mSurfaceHolder.addCallback(this);
		mGameThread = new GamaePaintThread(mSurfaceHolder);
		mMap = new Map(100, 100, 20, 20);
		mDrawableObjects.add(mMap);
	}

	// @Override
	// protected void onDraw(Canvas canvas) {
	// // TODO Auto-generated method stub
	// super.onDraw(canvas);
	//
	// canvas.drawText("hello world", mBrickX, mBrickY, mPaint);
	// }

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
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		Log.d(DEBUG_TAG, "surface create");
		mGameThread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		Log.d(DEBUG_TAG, "surface destroy");
		mGameThread.stopThread();
	}

	class GamaePaintThread extends Thread {
		SurfaceHolder mHolder;
		Canvas mCanvas;
		boolean mRun = true;

		public GamaePaintThread(SurfaceHolder sHolder) {
			mHolder = sHolder;
		}

		public void stopThread() {
			mRun = false;
		}

		@Override
		public void run() {
			while (mRun) {
				// mCanvas = null;
				synchronized (mHolder) {
					mCanvas = mHolder.lockCanvas();
					if (null != mCanvas) {
						for (Layer layer : mDrawableObjects) {
							layer.drawLayer(mCanvas);
						}
					}
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} finally {
						if (null != mHolder) {
							mHolder.unlockCanvasAndPost(mCanvas);
						}
					}
				}
			}
		}
	}
}
