/*
 *	first, just calculate how many columns and rows here, because android
 *	phones a little hard to adapter with bitmap
 */
package com.android.aplas.tetris;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Map extends Layer {

	// rows:20 columns:10
	private int[][] mMap = null;

	public Map(int x, int y, int rows, int columns) {
		super(x, y, rows, columns);
		// TODO Auto-generated constructor stub
		mMap = new int[rows][columns];
	}

	@Override
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub

	}

}
