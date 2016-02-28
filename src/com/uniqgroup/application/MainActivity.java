package com.uniqgroup.application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

import com.uniqgroup.database.DatabaseHandler;
import com.uniqgroup.utility.AlarmManage;

public class MainActivity extends Activity {

	ImageButton ibtnBack;
	public static final int ALARM_MAKER_ID   = 0x1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_screen);
		ibtnBack = (ImageButton) findViewById(R.id.ibtnBack);
		DatabaseHandler db = new DatabaseHandler(this);
		//db.resetDB();

		
		setAlarm();
		FullScreencall();
		
		
		ibtnBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						MainActivity.this);
				builder.setMessage("Do you want to exit Application")
						.setCancelable(false)
						.setPositiveButton("Yes",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										finish();
										System.exit(0);
									}
								})
						.setNegativeButton("No",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										dialog.cancel();
									}
								});
				AlertDialog alert = builder.create();
				alert.show();
			}
		});

	}
	// starting calendar app
	public void startuserlist(View v) {
		Intent user_create_activity = new Intent(this, UserCreateActivity.class);
		startActivity(user_create_activity);
		finish();
	}
	// starting
	public void starttutorial(View v) {

		Intent tuto_activity = new Intent(this, TutorialActivity.class);
		startActivity(tuto_activity);
		finish();
		// Intent user_create_activity = new Intent(this,
		// UserCreateActivity.class);
		// startActivity(user_create_activity);
		//

	}

	public void FullScreencall() {
		if (Build.VERSION.SDK_INT < 19) {
			View v = this.getWindow().getDecorView();
			v.setSystemUiVisibility(View.GONE);
		} else {
			
			View decorView = getWindow().getDecorView();
			int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION  | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
			decorView.setSystemUiVisibility(uiOptions);
		}
	}

	public void setAlarm()
	{
		Intent intent = new Intent(this, AlarmMakerReceiver.class);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), ALARM_MAKER_ID, intent, 0);
		AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		
		// every day at 12 am
        Calendar calendar = Calendar.getInstance();

        // if it's after or equal 9 am schedule for next day
        /*if (Calendar.getInstance().get(Calendar.HOUR_OF_DAY) >= 12) {
             // add, not set!
        }*/
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 12);
        calendar.set(Calendar.MINUTE, 00);
        calendar.set(Calendar.SECOND, 0);
		
		/*alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+ (i * 1000), (i * 1000), pendingIntent);*/
		alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
		Toast.makeText(this, "Processing alarm data for "+ String.valueOf(calendar.getTime()),Toast.LENGTH_LONG).show();
	}

	private void databaseBackup()
	{
		try {
			File sd = Environment.getExternalStorageDirectory();
			File data = Environment.getDataDirectory();

			if (sd.canWrite()) {
				String currentDBPath = "/Android/Data/" + getPackageName()
						+ "/databases/CalenderManager";
				String backupDBPath = "backupname";
				File currentDB = new File(currentDBPath);
				File backupDB = new File(sd, backupDBPath);

				if (currentDB.exists()) {
					FileChannel src = new FileInputStream(currentDB)
							.getChannel();
					FileChannel dst = new FileOutputStream(backupDB)
							.getChannel();
					dst.transferFrom(src, 0, src.size());
					src.close();
					dst.close();
				}
			}
		} catch (Exception e) {

		}
	}

}
