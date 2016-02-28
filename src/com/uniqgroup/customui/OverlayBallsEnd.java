package com.uniqgroup.customui;

import java.util.ArrayList;
import java.util.Random;

import com.uniqgroup.application.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.view.MotionEvent;



public class OverlayBallsEnd extends OverlayEnd {
	
	private static final int NUM_BALLS = 16;
	private static final int[] BALLS = new int[]{
		R.drawable.img_token_blue_face,
		R.drawable.img_token_green_face,
		R.drawable.img_token_grey_face,
		R.drawable.img_token_orange_face,
		R.drawable.img_token_pink_face,
		R.drawable.img_token_purple_face,
		R.drawable.img_token_red_face,
		R.drawable.img_token_yello_face
	};
	
	private Random mRandom = new Random();
	private ArrayList<Ball> mBalls = new ArrayList<OverlayBallsEnd.Ball>();

	public OverlayBallsEnd(Context context) {
		super(context);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// Update
		update();

		// Draw
		for ( Ball ball : mBalls ) ball.draw(canvas);
		
		// Invalidate
		invalidate();
	}
	
	protected void update() {
		// Remove finished
		for ( int i=0;i<mBalls.size();i++) {
			Ball ball = mBalls.get(i);
			if ( ball.isFinished(getWidth()) ) {
				ball.recycle();
				mBalls.remove(i);
			}
		}
		
		// Update
		for ( Ball ball : mBalls ) ball.update();
		
		// Add new
		if ( !isFinish() && mBalls.size() < NUM_BALLS ) {
			Ball ball = new Ball();
			ball.init(mRandom);
			ball.bitmap = BitmapFactory.decodeResource(getResources(), BALLS[mRandom.nextInt(BALLS.length)]);
			ball.offsetX = getWidth()/2;
			ball.offsetY = getHeight();
			
			mBalls.add(ball);
		}
	}
	
	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		for ( Ball ball : mBalls ) ball.recycle();
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if ( event.getAction() == MotionEvent.ACTION_DOWN ) {
			float x = event.getRawX();
			float y = event.getRawY();
	
			int index = -1;
			int count = mBalls.size();
			for ( int i=count-1;i>=0;i-- ) {
				Ball ball = mBalls.get(i);
				RectF rect = ball.getRectF();
				if ( rect.contains(x, y) ) {
					index = i;
					break;
				}
			}
			
			if ( index != -1 ) {
				mBalls.remove(index);
				return true;
			}
		}
		return super.onTouchEvent(event);
	}
	
	/** Gumball. */
	static class Ball {
		
		static final int MAX_K = 128;
		static final int MIN_K = 32;
		
		static final int MAX_SPEED = 8;
		static final int MIN_SPEED = 1;
		
		Bitmap bitmap;
		int speed;
		int direction;
		int x;
		int y;
		int k;
		int offsetX;
		int offsetY;
		
		boolean isFinished(int width) {
			return x+offsetX < -1*bitmap.getWidth() || x+offsetX > width;
		}
		
		void init(Random random) {
			x = 0;
			y = 0;
			k = random.nextInt(MAX_K-MIN_K) + MIN_K;
			direction = random.nextBoolean() ? 1 : -1;
			speed = random.nextInt(MAX_SPEED-MIN_SPEED) + MIN_SPEED;
		}
		
		void draw(Canvas canvas) {
			int x = this.offsetX + this.x;
			int y = this.offsetY - this.y;
			
			canvas.drawBitmap(bitmap, x,  y, null);
		}
		
		void update() {
			x = x + direction * speed;
			y = (int) (k * Math.log( (double) Math.abs(x)+1 ) );
		}
		
		void recycle() {
			if ( bitmap != null ) {
				bitmap.recycle();
				bitmap = null;
			}
		}
		
		RectF getRectF() {
			int x = this.offsetX + this.x;
			int y = this.offsetY - this.y;
			return new RectF(x, y, x+bitmap.getWidth(), y+bitmap.getHeight());
		}
	}

}
