package com.uniqgroup.application;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.uniqgroup.database.DatabaseHandler;
import com.uniqgroup.pojo.AlarmEvent;
import com.uniqgroup.utility.CalenderDateList;

public class AlarmMakerReceiver extends BroadcastReceiver{

	DatabaseHandler db;
	ArrayList<AlarmEvent> todayAlarmList;
	boolean alarmRecieverRunning = false;
	public static final int ALARM_MAKER_ID   = 0x1;
	
	@Override
	public void onReceive(Context context, Intent intent) 
	{
		db = new DatabaseHandler(context);
		todayAlarmList = new ArrayList<AlarmEvent>();
		todayAlarmList = db.alarmEventList(CalenderDateList.getCurrentWeekDay(), CalenderDateList.getCurrentWeekNumber());
		
		
		if(todayAlarmList!= null)
		{
			int count = 0;
			for(AlarmEvent alarmEvent : todayAlarmList)
			{
				Calendar cal = GregorianCalendar.getInstance(Locale.FRANCE);
				cal.set(Calendar.WEEK_OF_YEAR,  Integer.parseInt(CalenderDateList.getCurrentWeekNumber()));
				cal.set(Calendar.DAY_OF_WEEK, CalenderDateList.getDayValue(CalenderDateList.getCurrentWeekDay())+2);
				cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(alarmEvent.get_time().substring(0, 2)));
				cal.set(Calendar.MINUTE, Integer.parseInt(alarmEvent.get_time().substring(3, 5)));
				cal.set(Calendar.SECOND, 00);
				

				if(cal.getTimeInMillis() > System.currentTimeMillis())
				{
					Intent in = new Intent(context, DailyAlarmReceiver.class);
					in.putExtra("eventId", alarmEvent.get_event_id());
					in.putExtra("userId", alarmEvent.get_user_id());
					in.putExtra("imagePath", alarmEvent.get_image());
					PendingIntent pendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(), alarmEvent.get_event_id(), in, 0);
					AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
					alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),  pendingIntent);
					count++;
				}
			}
			
			Toast.makeText(context, "Total Alarm today  "+todayAlarmList.size()+", Pending "+count,Toast.LENGTH_SHORT).show();
		}
		else
		{
			Toast.makeText(context, "Total Alarm today  0",Toast.LENGTH_SHORT).show();
		}
	}
	
	public void SetAlarm(Context context)
    {
		Intent intent = new Intent(context, AlarmMakerReceiver.class);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ALARM_MAKER_ID, intent, 0);
		AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
		alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),  pendingIntent);
		Toast.makeText(context, "Rescheduling Alarm Data",Toast.LENGTH_LONG).show();
    }

    public void CancelAlarm(Context context)
    {
        Intent intent = new Intent(context, AlarmMakerReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }
	
}
