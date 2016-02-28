package com.uniqgroup.customdialog;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.uniqgroup.application.AlarmActivity;
import com.uniqgroup.application.DailyAlarmReceiver;
import com.uniqgroup.application.EventSeqActivity;
import com.uniqgroup.application.R;
import com.uniqgroup.customui.Animanation;
import com.uniqgroup.database.DatabaseHandler;
import com.uniqgroup.pojo.User;
import com.uniqgroup.utility.CalendarViews;
import com.uniqgroup.utility.CircularImageView_Purple;
import com.uniqgroup.utility.EditModeSetting;
import com.uniqgroup.utility.ImageProcessing;

public class AlarmDialog extends Dialog implements
		android.view.View.OnClickListener {

	Context context;
	Activity activity;
	ImageView ivAlarmStop, ivAlarmDone, alarmRingRight, alarmRingLeft;
	CircularImageView_Purple ivAlarmPropic;
	AlarmActivity alarmActivity;
	public ImageProcessing imgProc;
	DatabaseHandler db;
	
	private static final String USER_ID = "user_id";
	private static final String EVENT_ID = "event_id";
	private static final String RETRN_VIEW = "returnView";
	private static final String IMAGE_PATH = "imagePath";

	User user;

	public AlarmDialog(Activity activity, AlarmActivity alarmActivity,
			Context context) {
		super(context, R.style.CustomAlertDialog);

		this.activity = activity;
		this.alarmActivity = alarmActivity;
		this.context = context;

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		DisplayMetrics metrics = activity.getResources().getDisplayMetrics();
		int screenWidth = (int) (metrics.widthPixels * 0.20);
		int screenHeight = (int) (metrics.heightPixels * 0.20);
		getWindow().setLayout(screenWidth, screenHeight);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		setContentView(R.layout.activity_alarm_dialog);
		setCancelable(false);

		ivAlarmStop = (ImageView) findViewById(R.id.ivAlarmStop);
		ivAlarmPropic = (CircularImageView_Purple) findViewById(R.id.alarm_propic);
		ivAlarmDone = (ImageView) findViewById(R.id.ivAlarmDone);
		
		alarmRingLeft = (ImageView) findViewById(R.id.alarm_ring_left);
		alarmRingRight = (ImageView) findViewById(R.id.alarm_ring_right);

		imgProc = new ImageProcessing(context);

		ivAlarmPropic.setImageResource(R.drawable.ic_launcher);
		// ivAlarmPropic.setBackgroundResource(R.color.white);

		imgProc.setImageWith_loader(ivAlarmPropic, alarmActivity.imagePath);
		ivAlarmPropic.setOnClickListener(this);
		ivAlarmStop.setOnClickListener(this);
		ivAlarmDone.setOnClickListener(this);
		Animanation.shakeAnimation2(alarmRingLeft);
		Animanation.shakeAnimation2(alarmRingRight);
		db = new DatabaseHandler(context);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.alarm_propic:
			if(db.checkEventSeqStatus(alarmActivity.current_event_id)){
				EditModeSetting.setEditMode(0);
				Intent intent = new Intent(context, EventSeqActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.putExtra("event_id", alarmActivity.current_event_id);
				intent.putExtra("user_id", alarmActivity.current_user_id);
				intent.putExtra("returnView", CalendarViews.dayView);
				intent.putExtra("flag", "Update");
				intent.putExtra("returnPath","com.uniqgroup.application.AlarmActivity");
				context.startActivity(intent);
				alarmActivity.beforFinish();
				alarmActivity.finish();
			}
			else{
				dismiss();
				alarmActivity.beforFinish();
				alarmActivity.finish();
			}
			break;

		case R.id.ivAlarmStop:
			dismiss();
			alarmActivity.beforFinish();
			alarmActivity.finish();
			break;

		case R.id.ivAlarmDone:
			Calendar cal = GregorianCalendar.getInstance(Locale.FRANCE);
			Intent in = new Intent(context, DailyAlarmReceiver.class);
			in.putExtra("eventId", alarmActivity.current_event_id);
			in.putExtra("userId", alarmActivity.current_user_id);
			in.putExtra("imagePath", alarmActivity.imagePath);
			PendingIntent pendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(), 0, in, 0);
			AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
			alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis()+1*60*1000,  pendingIntent);
			
			dismiss();
			alarmActivity.beforFinish();
			alarmActivity.finish();
			break;

		default:
			break;
		}

	}

}
