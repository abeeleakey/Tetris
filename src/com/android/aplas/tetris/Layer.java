package com.android.aplas.tetris;

import android.graphics.Canvas;

public abstract class Layer {

	protected int mWidth, mHeight;
	protected int mCoordinateX, mCoordinateY;

	/**
	 * Layer's init function, because this is a game not a engine. so, set the
	 * position on init
	 * 
	 * @param x
	 *            up-left position
	 * @param y
	 *            up-top position
	 * @param width
	 * @param height
	 */
	public Layer(int x, int y, int width, int height) {
		this.mCoordinateX = x;
		this.mCoordinateY = y;
		this.mWidth = width;
		this.mHeight = height;
	}

	/**
	 * 
	 * @param dir
	 *            left:1 top:2 right:3 bottom:4
	 * @param increX
	 *            how far the layer move on time
	 * @param increY
	 */
	public synchronized void move(int dir, int increX, int increY) {
		mCoordinateX += increX;
		mCoordinateY += increY;
	}

	public int getX() {
		return mCoordinateX;
	}

	public int getY() {
		return mCoordinateY;
	}

	public int getWidth() {
		return mWidth;
	}

	public int getHeight() {
		return mHeight;
	}

	void setWidthImpl(int width) {
		if (width < 0) {
			throw new IllegalArgumentException();
		}
		this.mWidth = width;
	}

	void setHeightImpl(int height) {
		if (height < 0) {
			throw new IllegalArgumentException();
		}
		this.mHeight = height;
	}

	abstract void drawLayer(Canvas canvas);
}
