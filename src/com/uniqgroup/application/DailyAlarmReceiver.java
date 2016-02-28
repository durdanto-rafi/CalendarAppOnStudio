package com.uniqgroup.application;

import com.uniqgroup.utility.CalendarViews;

import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.widget.Toast;

public class DailyAlarmReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) 
	{
		/*playRingtone(context);
		unlockScreen(context);
		powerUpDevice(context);*/
		//Toast.makeText(context, String.valueOf(intent.getIntExtra("userId",-1)),Toast.LENGTH_SHORT).show();
		
		Intent in = new Intent(context, AlarmActivity.class);
		in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		in.putExtra("event_id", intent.getIntExtra("eventId", -1));
		in.putExtra("user_id", intent.getIntExtra("userId", -1));
		
		in.putExtra("imagePath", intent.getExtras().getString("imagePath"));
		in.putExtra("returnView", CalendarViews.dayView);
		in.putExtra("flag", "Update");
		in.putExtra("returnPath", "com.uniqgroup.application.AlarmActivity");
		context.startActivity(in);
		
		/*i.setClassName("com.test", "com.test.MainActivity");
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);*/
	}
	
	private void playRingtone(Context context)
	{
		Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
		Ringtone r = RingtoneManager.getRingtone(context.getApplicationContext(), notification);
		r.play();
	}
	
	private void unlockScreen(Context context)
	{
		KeyguardManager km = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE); 
		final KeyguardManager.KeyguardLock kl = km .newKeyguardLock("MyKeyguardLock"); 
		kl.disableKeyguard(); 
	}
	
	private void powerUpDevice(Context context)
	{
		PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE); 
		WakeLock wakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK
		                                 | PowerManager.ACQUIRE_CAUSES_WAKEUP
		                                 | PowerManager.ON_AFTER_RELEASE, "MyWakeLock");
		wakeLock.acquire();
	}

}
