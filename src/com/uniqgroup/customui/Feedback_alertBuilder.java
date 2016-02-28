package com.uniqgroup.customui;

import com.uniqgroup.application.EventSeqActivity;
import com.uniqgroup.application.R;
import com.uniqgroup.utility.ImageProcessing;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.media.MediaPlayer;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Feedback_alertBuilder {

	public static void makerDialog(Context ctx, final EventSeqActivity activity, String dailyPrizeImage) {

		ImageProcessing imgProc = new ImageProcessing(ctx);
		final Dialog dialog = new Dialog(ctx, R.style.CustomAlertDialog);
		// R.style.CustomAlertDialog);

		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.feed_back_layout);

		dialog.setCancelable(false);

		dialog.setCancelable(false);
		final Button no_bt = (Button) dialog.findViewById(R.id.dialog_no);
		final Button yes_bt = (Button) dialog.findViewById(R.id.dialog_yes);
		ImageView imageView = (ImageView) dialog.findViewById(R.id.dialogImageView);

		MediaPlayer mp = MediaPlayer.create(ctx, R.raw.positivebell_long);
		mp.start();

		if(dailyPrizeImage != null && dailyPrizeImage.length()>0)
		{
			imgProc.setImageWith_loader(imageView, dailyPrizeImage);
		}
		else
		{
			imageView.setImageResource(R.drawable.img_green_happy_face);
		}
		

		final EventSeqActivity eventSeqActivity = new EventSeqActivity();

		final LinearLayout fullLin = (LinearLayout) dialog
				.findViewById(R.id.full_dialog_lin);

		fullLin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//eventSeqActivity.FullScreencall();
				dialog.dismiss();
				activity.finishActivity();

			}
		});

		final Animation shake = AnimationUtils.loadAnimation(ctx,
				R.anim.rotation_anim);

		Animation zoomIn = AnimationUtils.loadAnimation(ctx, R.anim.zoom_in);
		final Animation zoom_out = AnimationUtils.loadAnimation(ctx,
				R.anim.zoom_out);

		zoomIn.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				fullLin.startAnimation(zoom_out);
			}
		});

		zoom_out.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				fullLin.setAnimation(shake);

			}
		});

		dialog.show();
		fullLin.setAnimation(shake);
	}

}
