package com.android.aplas.tetris;

import junit.framework.Assert;
import android.graphics.Bitmap;
import android.graphics.Canvas;

public class BlockLayer extends Layer implements BaseObject {

	private int mCoordX, mCoordY;
	private final int mRows, mCloums;
	private Bitmap mBlock;
	private int[][] mShape;
	private Object layerLock = new Object();
	private int mBlockUnitLength;

	private boolean mAllowSwitchState;

	public BlockLayer(int x, int y, Bitmap bitmap, int[][] shape) {
		// TODO Auto-generated constructor stub
		super(x, y);
		Assert.assertNotNull(shape);
		this.mCoordX = x;
		this.mCoordY = y;
		this.mRows = shape.length;
		this.mCloums = shape[0].length;
		mBlock = bitmap;
		mShape = shape;
		mBlockUnitLength = mBlock.getWidth();
	}

	public int[][] getShape() {
		synchronized (layerLock) {
			return mShape;
		}
	}

	public void switchShapeState(int[][] newShape) {
		synchronized (layerLock) {
			if (mAllowSwitchState) {
				mShape = newShape;
			}
		}
	}

	public void allowSwitch(boolean allow) {
		this.mAllowSwitchState = allow;
	}

	@Override
	public void draw(Canvas canvas) {
		synchronized (layerLock) {
			for (int i = 0; i < mRows; i++) {
				for (int j = 0; j < mCloums; j++) {
					if (mShape[i][j] > 0) {
						canvas.drawBitmap(mBlock, (mCoordX + i
								* mBlockUnitLength), (mCoordY + j
								* mBlockUnitLength), null);
					}
				}
			}
		}
	}
}
