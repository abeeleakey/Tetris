package com.android.aplas.tetris;

import android.graphics.Canvas;
import android.graphics.RectF;

public class Block implements BaseObject {
	int mHeight;
	int mWidth;
	int mCoordX;
	int mCoordY;

	RectF rectf = null;

	public Block(int x, int y, int height, int width) {
		this.mCoordX = x;
		this.mCoordY = y;
		this.mHeight = height;
		this.mWidth = width;
		rectf = new RectF(x, y, x + width, y + height);
	}

	@Override
	public void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		canvas.drawRoundRect(rectf, mCoordX, mCoordY, null);
	}
}
