package com.uniqgroup.utility;

import android.view.MotionEvent;
import android.view.View;

public class Custom_Touch_Event {
	private static final int NONE = 0;
	private static final int SWIPE = 1;
	private int mode = NONE;
	private float startX;
	private float stopX;
	// We will only detect a swipe if the difference is at least 100 pixels
	// Change this value to your needs
	private static final int TRESHOLD = 100;
	
	OnFling_right_left listener;
	public Custom_Touch_Event(OnFling_right_left listener){
		this.listener=listener;
	}
	
	public void onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_POINTER_DOWN:
			// This happens when you touch the screen with two fingers
			mode = SWIPE;
			// You can also use event.getY(1) or the average of the two
			startX = event.getX(0);
			break;

		case MotionEvent.ACTION_POINTER_UP:
			// This happens when you release the second finger
			mode = NONE;
			if (Math.abs(startX - stopX) > TRESHOLD) {
				if (startX > stopX) {
					// Swipe right-left
					listener.flingRL_happened(false);
					System.out.println("Swipe up");
				} else {
					// Swipe left-right
					listener.flingRL_happened(true);
					System.out.println("Swipe down");
				}
			}
			this.mode = NONE;
			break;

		case MotionEvent.ACTION_MOVE:
			if (mode == SWIPE) {
				stopX = event.getX(0);
			}
			break;
		}
	}
	
	public interface OnFling_right_left{
		public void flingRL_happened(boolean editOn);
		
	}
	
}
