package com.uniqgroup.application;

import com.uniqgroup.customui.OverlayBallsEnd;
import com.uniqgroup.fragment.PrizeViewFragment;
import com.uniqgroup.fragment.TransparentFragment;
import com.uniqgroup.utility.ImageProcessing;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class Transparent_feedback_activity extends Activity {

	final String KILL_ACTIVITY_BR = "kill_actvity";
	Button btnDialog;
	int i = 0;
	RelativeLayout relative;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final OverlayBallsEnd view = new OverlayBallsEnd(
				Transparent_feedback_activity.this);
		setContentView(R.layout.transparent_backgound_feedback);
		String imagepath = getIntent().getExtras().getString("prize");

		final Dialog customDialog = new Dialog(
				Transparent_feedback_activity.this, R.style.CustomAlertDialog);
		customDialog.setContentView(R.layout.feed_back_layout);
		ImageView dialogImageView = (ImageView) customDialog
				.findViewById(R.id.dialogImageView);
		ImageProcessing mproc = new ImageProcessing(this);
		if (imagepath != null && imagepath.length() > 0) {

			mproc.setImageWith_loader(dialogImageView, imagepath);
		} else {
			dialogImageView.setImageResource(R.drawable.img_green_happy_face);
		}

		dialogImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (i == 0) {

					Fragment fragment = new TransparentFragment();
					FragmentManager managerMonth = getFragmentManager();
					FragmentTransaction transactionMonth = managerMonth
							.beginTransaction();
					transactionMonth.addToBackStack(null);
					transactionMonth.replace(R.id.baloons_rl, fragment,
							"Fragment").commit();

					new Handler().postDelayed(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							MediaPlayer mp = MediaPlayer.create(
									Transparent_feedback_activity.this,
									R.raw.sd_token_end);
							mp.start();
						}
					}, 1000);

					i++;

				} else {
					customDialog.dismiss();
					finish_and_Notify();
					finish();
				}
			}
		});
		customDialog.show();

		/*
		 * //btnDialog = (Button) findViewById(R.id.btnDialog);
		 * //btnDialog.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { Dialog customDialog = new
		 * Dialog(MainActivity.this);
		 * 
		 * 
		 * //ViewGroup group = (ViewGroup)
		 * customDialog.getWindow().getDecorView(); //group.addView(view);
		 * //view.invalidate();
		 * 
		 * 
		 * customDialog.setContentView(view); customDialog.show();
		 * 
		 * } });
		 */
	}

	public void finish_and_Notify() {
		Intent intent = new Intent(KILL_ACTIVITY_BR);
		sendBroadcast(intent);
		finish();
	}

}
