package com.uniqgroup.application;

import com.uniqgroup.application.AlarmMakerReceiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class BootReceiver extends BroadcastReceiver{

	public static final int ALARM_MAKER_ID   = 0x1;
	
	/*@Override
	public void onReceive(Context context, Intent intent) 
	{

		Intent in = new Intent(context, AlarmMakerReceiver.class);
		int i = 10;
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(), ALARM_MAKER_ID, in, 0);
		AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
		alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+ (i * 1000), pendingIntent);
		Toast.makeText(context, "Calendar App Alarm starting in " + i + " seconds",Toast.LENGTH_LONG).show();
		
	}*/
	
	@Override
	public void onReceive(Context context, Intent intent) 
	{
		if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED"))
        {
			//alarmMaker.SetAlarm(context);
			AlarmMakerReceiver ma = new AlarmMakerReceiver();
			ma.SetAlarm(context);
        }
	}
}

