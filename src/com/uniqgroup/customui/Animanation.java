package com.uniqgroup.customui;

import com.uniqgroup.application.R;

import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageButton;
import android.widget.ImageView;

/** Animation nation. */
public class Animanation {

	/**
	 * Clear all animations.
	 * 
	 * @param view
	 *            The view to clear animation.
	 */
	public static void clear(View view) {
		view.setAnimation(null);
	}

	public static void shakeAnimation(View v) {
		/*ObjectAnimator oAniamtor = ObjectAnimator.ofFloat(v, "translationX", 0,
				25, -25, 25, -25, 15, -15, 6, -6, 0);
		oAniamtor.setDuration(100);
		oAniamtor.start();*/
		RotateAnimation rotate = new RotateAnimation(-30, 60,
		        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
		        0.5f);

		rotate.setDuration(1000);
		rotate.setRepeatMode(2);
		rotate.setRepeatCount(Animation.INFINITE);
		v.setAnimation(rotate);
	}
	
	
	public static void shakeAnimation2(View v) {
		/*ObjectAnimator oAniamtor = ObjectAnimator.ofFloat(v, "translationX", 0,
				25, -25, 25, -25, 15, -15, 6, -6, 0);
		oAniamtor.setDuration(100);
		oAniamtor.start();*/
		RotateAnimation rotate = new RotateAnimation(-20, 20,
		        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
		        0.5f);

		rotate.setDuration(200);
		rotate.setRepeatMode(2);
		rotate.setRepeatCount(Animation.INFINITE);
		v.setAnimation(rotate);
	}
	

	/**
	 * A classic blink animation.
	 * 
	 * @param view
	 *            The view to animate.
	 */
	public static void blink(final View view) {
		final AlphaAnimation toAlpha = new AlphaAnimation(1, 0);
		final AlphaAnimation fromAlpha = new AlphaAnimation(0, 1);

		toAlpha.setDuration(1000);
		toAlpha.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				view.startAnimation(fromAlpha);
			}
		});

		fromAlpha.setDuration(1000);
		fromAlpha.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				view.startAnimation(toAlpha);
			}
		});

		view.startAnimation(toAlpha);
	}

	/** ANIMATION TO SLIDE up to DOWN **/
	public static void slideUP_to_Down(View v) {
		TranslateAnimation animation = new TranslateAnimation(0.0f, 0.0f,
				-400.0f, 0.0f);
		animation.setDuration(1000); // animation duration

		v.setVisibility(View.VISIBLE);
		v.startAnimation(animation);
	}

	/** ANIMATION TO SLIDE down to DOWN only for eventSeq **/
	public static void slideDown_to_Down(final View v, int id) {
		TranslateAnimation animation = new TranslateAnimation(0.0f, 0.0f, 0.0f,
				400.0f);
		animation.setDuration(1000); // animation duration
		animation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				v.setVisibility(View.INVISIBLE);
				ImageButton iv = (ImageButton) v;
				iv.setImageResource(R.drawable.img_white_face_pressed);
				slideUP_to_Down(v);
			}
		});

		v.startAnimation(animation);
	}

	/**
	 * Alpha animation to set a view visibility {@link View#VISIBLE}.
	 * 
	 * @param view
	 *            The view to show.
	 */
	public static void toVisible(final View view) {
		if (view.getVisibility() == View.VISIBLE)
			return;

		AlphaAnimation animation = new AlphaAnimation(0, 1);
		animation.setDuration(250);
		animation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				view.setVisibility(View.VISIBLE);
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
			}
		});
		view.startAnimation(animation);
	}

	/**
	 * Alpha animation to set a view visibility to {@link View#INVISIBLE}.
	 * 
	 * @param view
	 *            The view to hide.
	 */
	public static void toInvisible(final View view) {
		if (view.getVisibility() == View.INVISIBLE)
			return;

		AlphaAnimation animation = new AlphaAnimation(1, 0);
		animation.setDuration(250);
		animation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				view.setVisibility(View.INVISIBLE);
			}
		});
		view.startAnimation(animation);
	}

	/**
	 * Alpha animation to set a view visibility to {@link View#GONE}.
	 * 
	 * @param view
	 *            The view to hide.
	 */
	public static void toGone(final View view) {
		if (view.getVisibility() == View.GONE)
			return;

		AlphaAnimation animation = new AlphaAnimation(1, 0);
		animation.setDuration(250);
		animation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				view.setVisibility(View.GONE);
			}
		});
		view.startAnimation(animation);
	}

}
