package com.uniqgroup.application;

import com.uniqgroup.utility.OnDoubleTapListener;
import com.uniqgroup.utility.OnDoubleTapListener.GestureListener_Custom;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.VideoView;

//Implement SurfaceHolder interface to Play video
//Implement this interface to receive information about changes to the surface
public class VideoPlayer_Activity extends Activity implements
		SurfaceHolder.Callback, OnCompletionListener, GestureListener_Custom {

	final String VIDEO_PATH_EXTRA = "video_path";
	final String VIDEO_COMPLETE_BR = "video_finish";

	MediaPlayer mediaPlayer;
	SurfaceView surfaceView;
	SurfaceHolder surfaceHolder;
	boolean pausing = false;
	VideoView mVideoView;
	LinearLayout full_layout;
	ImageButton ibtnPlay;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.video_player_ativity);

		getWindow().setFormat(PixelFormat.UNKNOWN);

		// Displays a video file.
		mVideoView = (VideoView) findViewById(R.id.videoplayer_videov);
		full_layout = (LinearLayout) findViewById(R.id.linear_videoplayer);
		ibtnPlay = (ImageButton) findViewById(R.id.ibtnPlay);
		full_layout.setOnTouchListener(new OnDoubleTapListener(this, this));

		String uriPath = getIntent().getExtras()
				.getString(VIDEO_PATH_EXTRA, "");
		System.out.println(uriPath);

		ibtnPlay.setOnClickListener(ibtnPlayOnclickLintner);
		try {
			Uri uri = Uri.parse(uriPath);
			mVideoView.setVideoURI(uri);
			mVideoView.requestFocus();
			mVideoView.setOnCompletionListener(this);
		} catch (Exception e) {
			e.printStackTrace();
			finish_and_Notify();
		}

		FullScreencall();
		
		
	}

	OnClickListener ibtnPlayOnclickLintner = new OnClickListener() {
		@Override
		public void onClick(View v) {
			mVideoView.start();
			ibtnPlay.setVisibility(View.INVISIBLE);
			mVideoView.setBackgroundResource(android.R.color.transparent);
		}
	};

	/** used for killing the activity and notify action screen **/
	public void finish_and_Notify() {
		Intent intent = new Intent(VIDEO_COMPLETE_BR);
		sendBroadcast(intent);
		finish();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onCompletion(MediaPlayer arg0) {
		// TODO Auto-generated method stub
		finish_and_Notify();
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		finish_and_Notify();
		// super.onBackPressed();
	}

	@Override
	public void onDoubleTap(MotionEvent e, View v) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSingleTapListener(MotionEvent e, View v) {
		if (mVideoView.isPlaying()) {
			mVideoView.pause();
			ibtnPlay.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void onLongClick(MotionEvent e, View v) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDown(MotionEvent e, View v) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFling_to_Close() {
		finish();
	}
	
	
	public void FullScreencall() {
		if (Build.VERSION.SDK_INT < 19) {
			View v = this.getWindow().getDecorView();
			v.setSystemUiVisibility(View.GONE);
		} else {
			// for lower api versions.
			View decorView = getWindow().getDecorView();
			int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
					| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
			decorView.setSystemUiVisibility(uiOptions);
		}
	}
	
	
}