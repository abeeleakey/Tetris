package com.android.aplas.tetris;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
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

	GamaePaintThread mGameThread = null;
	private SurfaceHolder mSurfaceHolder;

	private BlockLayer mBlockLayer = null;
	Bitmap mBlockBitmap;

	public ArrayList<Layer> mDrawableObjects = new ArrayList<Layer>();

	public GameView(Context context) {
		super(context);

		mBlockBitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.block);

		mSurfaceHolder = getHolder();
		mSurfaceHolder.addCallback(this);
		mGameThread = new GamaePaintThread(mSurfaceHolder);
		mBlockLayer = new BlockLayer(0, 50, mBlockBitmap,
				BlocksData.mBlockShapeInData[1]);
		mDrawableObjects.add(mBlockLayer);
		mGameThread.start();
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
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		Log.d(DEBUG_TAG, "surface create");

	}

	public void addDrawableObject(Layer layer) {
		mDrawableObjects.add(layer);
	}

	// thread not safe, have no idea at moment
	public void removeDrawableObject(Layer layer) {
		mDrawableObjects.remove(layer);
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		Log.d(DEBUG_TAG, "surface destroy");
	}

	public void onPause() {
		mGameThread.onPause();
	}

	public void onResume() {
		mGameThread.onResume();
	}

	class GamaePaintThread extends Thread {

		SurfaceHolder mHolder;
		Canvas mCanvas;
		boolean mRun = true;
		private boolean mPaused;

		public GamaePaintThread(SurfaceHolder sHolder) {
			super();

			mHolder = sHolder;
			mRequestRender = true;
		}

		public void onPause() {
			synchronized (mGameThreadManager) {
				mPaused = true;
				mGameThreadManager.notifyAll();
			}
		}

		public void onResume() {
			synchronized (mGameThreadManager) {
				mPaused = false;
				mRequestRender = true;
				mGameThreadManager.notifyAll();
			}
		}

		public void onSurfaceDestroy() {
			synchronized (mGameThreadManager) {
				mHasSurface = false;
				mGameThreadManager.notifyAll();
			}
			while ((!mWaitingForSurface) && (!mExited)) {
				try {
					mGameThreadManager.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					Thread.currentThread().interrupt();
				}
			}
		}

		public void onSurfaceChanged(int w, int h) {
			synchronized (mGameThreadManager) {
				mSizeChanged = true;
				mRequestRender = true;
				mRenderComplete = false;
				mGameThreadManager.notifyAll();
			}
			while (!mExited && !mPaused && !mRenderComplete) {
				try {
					mGameThreadManager.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					Thread.currentThread().interrupt();
				}
			}
		}

		public void requestExitAndWait() {
			synchronized (mGameThreadManager) {
				mShouldExist = true;
				mGameThreadManager.notifyAll();
				while (!mExited) {
					try {
						mGameThreadManager.wait();
					} catch (Exception e) {
						// TODO: handle exception
						Thread.currentThread().interrupt();
					}
				}
			}
		}

		public void onSurfaceCreate() {
			synchronized (mGameThreadManager) {
				mHasSurface = true;
				mGameThreadManager.notifyAll();
			}
		}

		private void guardedRun() {

		}

		@Override
		public void run() {
			while (true) {
				// mCanvas = null;
				synchronized (mGameThreadManager) {
					mCanvas = mHolder.lockCanvas();
					if (null != mCanvas) {
						for (Layer layer : mDrawableObjects) {
							if (null != layer) {
								layer.draw(mCanvas);
							}
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

	private boolean mExited;
	private boolean mHasSurface;
	private boolean mRequestRender;
	private boolean mRenderComplete;
	private boolean mWaitingForSurface;
	private boolean mSizeChanged = true;
	private boolean mShouldExist;
	private static final GemeViewThreadManager mGameThreadManager = new GemeViewThreadManager();

	private static class GemeViewThreadManager {

		public synchronized void threadExisting(GameView thread) {
			thread.mExited = true;
			if (thread == mThreadOwner) {
				mThreadOwner = null;
			}
			notifyAll();
		}

		public boolean tryAcquireSurfaceLocked(GameView thread) {
			if (mThreadOwner == thread || null == mThreadOwner) {
				mThreadOwner = thread;
				notifyAll();
				return true;
			}
			if (mMultipleContextsAllowed) {
				return true;
			}
			return false;
		}

		private GameView mThreadOwner;
		private boolean mMultipleContextsAllowed;
	}
}
