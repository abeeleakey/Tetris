/*
 *	first, just calculate how many columns and rows here, because android
 *	phones a little hard to adapter with bitmap
 */
package com.android.aplas.tetris;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Map extends Layer {
	
	Paint mPaint = new Paint();

	public Map(int x, int y, int width, int height) {
		super(x, y, width, height);
		// TODO Auto-generated constructor stub
		mPaint.setColor(Color.RED);
	}

	@Override
	void drawLayer(Canvas canvas) {
		// TODO Auto-generated method stub
		canvas.drawText("MAP here", 100, 100, mPaint);
	}

}
