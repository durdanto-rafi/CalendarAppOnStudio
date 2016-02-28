package com.uniqgroup.application;

import com.uniqgroup.customdialog.AlarmDialog;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.Vibrator;
import android.os.PowerManager.WakeLock;
import android.view.Window;
import android.widget.Toast;

public class AlarmActivity extends Activity {
	public String current_user_id;
	String returnView;
	public String imagePath;
	public int current_event_id;
	int current_frag_id;
	AlarmActivity myActivity;
	MediaPlayer mediaPlayer;
	private Vibrator vibrator;
	WakeLock wakeLock;

	private static final String USER_ID = "user_id";
	private static final String EVENT_ID = "event_id";
	private static final String RETRN_VIEW = "returnView";
	private static final String IMAGE_PATH = "imagePath";
	
	
	
	private static final String LOG_TAG = "SMSReceiver";
	public static final int NOTIFICATION_ID_RECEIVED = 0x1221;
	static final String ACTION = "android.provider.Telephony.SMS_RECEIVED";
	Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		// setContentView(R.layout.activity_alarm);

		myActivity = this;

		Intent intent = getIntent();
		current_user_id = String.valueOf(intent.getIntExtra(USER_ID,-1));
		current_event_id = intent.getIntExtra(EVENT_ID, 0);
		returnView = intent.getStringExtra(RETRN_VIEW);
		imagePath = intent.getExtras().getString(IMAGE_PATH);
		
		//Toast.makeText(context, current_user_id, Toast.LENGTH_LONG).show();
		startAlarm();
		unlockScreen(this);
		powerUpDevice(this);

		AlarmDialog alarmDialog = new AlarmDialog(AlarmActivity.this, myActivity, AlarmActivity.this);
		alarmDialog.show();

	}

	private void startAlarm() {
		mediaPlayer = new MediaPlayer();
		vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
		long[] pattern = { 1000, 200, 200, 200 };
		vibrator.vibrate(pattern, 0);

		try {
			mediaPlayer.setVolume(1.0f, 1.0f);
			mediaPlayer.setDataSource(this,
					RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM));
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
			mediaPlayer.setLooping(true);
			mediaPlayer.prepare();
			mediaPlayer.start();

		} catch (Exception e) {
			mediaPlayer.release();
		}

	}

	private void unlockScreen(Context context) {
		KeyguardManager km = (KeyguardManager) context
				.getSystemService(Context.KEYGUARD_SERVICE);
		final KeyguardManager.KeyguardLock kl = km
				.newKeyguardLock("MyKeyguardLock");
		kl.disableKeyguard();
	}

	private void powerUpDevice(Context context) {
		PowerManager pm = (PowerManager) context
				.getSystemService(Context.POWER_SERVICE);
		wakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK
				| PowerManager.ACQUIRE_CAUSES_WAKEUP
				| PowerManager.ON_AFTER_RELEASE, "MyWakeLock");
		wakeLock.acquire();
	}

	public void beforFinish() {
		try {
			if (vibrator != null)
				vibrator.cancel();
		} catch (Exception e) {

		}
		try {
			mediaPlayer.stop();
		} catch (Exception e) {

		}
		try {
			mediaPlayer.release();
		} catch (Exception e) {

		}
		try {
			wakeLock.release();
		} catch (Exception e) {

		}
	}

	@Override
	public void onDestroy() {
		try {
			if (vibrator != null)
				vibrator.cancel();
		} catch (Exception e) {

		}
		try {
			mediaPlayer.stop();
		} catch (Exception e) {

		}
		try {
			mediaPlayer.release();
		} catch (Exception e) {

		}
		try {
			wakeLock.release();
		} catch (Exception e) {

		}
		super.onDestroy();
	}

	/*
	 * @Override public void onPause() {
	 * 
	 * }
	 */
}
