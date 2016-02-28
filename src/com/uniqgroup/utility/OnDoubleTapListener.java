package com.uniqgroup.utility;

import android.content.Context;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.webkit.WebChromeClient.CustomViewCallback;

/*

 Usage:

 myView.setOnTouchListener(new OnDoubleTapListener(this) {
 @Override
 public void onDoubleTap(MotionEvent e) {
 Toast.makeText(MainActivity.this, "Double Tap", Toast.LENGTH_SHORT).show();
 }
 });

 */
public class OnDoubleTapListener implements OnTouchListener {
	private static final int NONE = 0;
	private static final int SWIPE = 1;
	private int mode = NONE;
	private float startX;
	private float stopX;
	// We will only detect a swipe if the difference is at least 100 pixels
	// Change this value to your needs
	private static final int TRESHOLD = 100;

	private GestureDetector gestureDetector;
	GestureListener_Custom listener_Custom;

	View view_touched;

	public OnDoubleTapListener(Context c, GestureListener_Custom listener) {
		gestureDetector = new GestureDetector(c, new GestureListener());
		listener_Custom = listener;
	}

	public boolean onTouch(final View view, final MotionEvent motionEvent) {
		view_touched = view;

		onTouch_Fling(view, motionEvent);
		gestureDetector.onTouchEvent(motionEvent);
		return true;
	}

	private final class GestureListener extends SimpleOnGestureListener {

		@Override
		public boolean onDown(MotionEvent e) {
			OnDoubleTapListener.this.onDown(e, view_touched);
			return super.onDown(e);
		}

		@Override
		public boolean onDoubleTap(MotionEvent e) {
			OnDoubleTapListener.this.onDoubleTap(e, view_touched);
			return super.onDoubleTap(e);
		}

		@Override
		public boolean onSingleTapConfirmed(MotionEvent e) {

			OnDoubleTapListener.this.onSingleTapConfirmed(e, view_touched);
			return super.onSingleTapConfirmed(e);
		}

	}

	/** if any modification needed before calling activity it can be done here **/
	public void onDown(MotionEvent e, View v) {
		listener_Custom.onDown(e, v);
	}

	public void onSingleTapConfirmed(MotionEvent e, View v) {
		listener_Custom.onSingleTapListener(e, v);
	}

	public void onDoubleTap(MotionEvent e, View v) {
		// To be overridden when implementing listener
		listener_Custom.onDoubleTap(e, v);
	}

	public void onTouch_Fling(View v, MotionEvent event) {
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
					listener_Custom.onFling_to_Close();
					System.out.println("Swipe down");
				} else {
					// Swipe left-right
					
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

	/** this part is used for calling activity **/
	public interface GestureListener_Custom {
		public void onDoubleTap(MotionEvent e, View v);

		public void onSingleTapListener(MotionEvent e, View v);

		public void onLongClick(MotionEvent e, View v);

		public void onDown(MotionEvent e, View v);

		public void onFling_to_Close();
	}

}