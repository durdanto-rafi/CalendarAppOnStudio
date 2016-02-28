package com.uniqgroup.application;

import com.uniqgroup.customui.CircleTimerView;
import com.uniqgroup.customui.TimerView.OnFinishListener;
import com.uniqgroup.utility.Circle;
import com.uniqgroup.utility.ImageProcessing;
import com.uniqgroup.utility.OnDoubleTapListener;
import com.uniqgroup.utility.ScaleImageView;
import com.uniqgroup.utility.OnDoubleTapListener.GestureListener_Custom;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class TimerActivity extends Activity implements GestureListener_Custom, OnFinishListener{

	final String TIMER_COMPLETE_BR = "timer_finish";
	CircleTimerView timer_circle;
	int time=0; /**in milisecond **/
	String imagepath;
	ScaleImageView image_IV;
	RelativeLayout whole_layout;
	
	boolean ispaused=true;
	ImageProcessing image_processor;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.timer_activity_layout);
		
		imagepath=getIntent().getExtras().getString("img_path");
		time=getIntent().getExtras().getInt("timer");
		
		image_processor=new ImageProcessing(this);
		
		System.out.println("time:"+ time+" imgPath:"+ imagepath);
		
		timer_circle=(CircleTimerView) findViewById(R.id.circle_timer_Iv);
		timer_circle.setTime(time*1000);
		timer_circle.setColor(Color.parseColor("#ff82358b"));
		timer_circle.setOnFinishListener(this);
		
		FullScreencall();
		
		
		image_IV=(ScaleImageView) findViewById(R.id.image_timer_Iv);
		image_processor.setImageWith_loader(image_IV,imagepath);
		//image_IV.setImageBitmap(image_processor.getImage(imagepath));
		
		whole_layout=(RelativeLayout) findViewById(R.id.timer_activity_layout);
		
		whole_layout.setOnTouchListener(new OnDoubleTapListener(this,this));
		
	}
	
	public void pauseTimer(){
		if(!ispaused){
			timer_circle.stop();
			ispaused=true;
		}
	}
	
	public void restartTimer(){
		if(ispaused){
			timer_circle.start();
			ispaused=false;
		}
	}
	
	public void controller_Timer(){
		if(ispaused)
			restartTimer();
		else {
			pauseTimer();
		}
	}
	
	@Override
	public void onDoubleTap(MotionEvent e, View v) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onSingleTapListener(MotionEvent e, View v) {
		// TODO Auto-generated method stub
		System.err.println("tap");
		controller_Timer();
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
		// TODO Auto-generated method stub
		finishthe_Activity(0);
	}

	/** It comes with timerView**/
	@Override
	public void onFinish() {
		// TODO Auto-generated method stub
		finishthe_Activity(1);
	}
	
	public void finishthe_Activity(int message){
		Intent intent = new Intent(TIMER_COMPLETE_BR);
		intent.putExtra("finish", message);
		sendBroadcast(intent);
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
