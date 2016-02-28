package com.uniqgroup.utility;

import java.util.ArrayList;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.uniqgroup.database.DatabaseHandler;
import com.uniqgroup.pojo.AlarmEvent;

public class AlarmManage extends BroadcastReceiver{
	public static final String alarmOff = "N";
	public static final String alarmOn = "Y";
	DatabaseHandler db;
	ArrayList<AlarmEvent> todayAlarmList;
	
	public void stopAlarm(){
		
	}
	
	
	public void jumptoAlarmAction(){
		
	}
	
	public void snoozeAlarm(){
		
	}


	@Override
	public void onReceive(Context context, Intent intent) 
	{
		//todayAlarmList = db.alarmEventList(CalenderDateList.getCurrentWeekDay(), CalenderDateList.getCurrentWeekNumber(), 1);
		Toast.makeText(context, "jhjhj",Toast.LENGTH_SHORT).show();
		
	}
	
	
	
}



