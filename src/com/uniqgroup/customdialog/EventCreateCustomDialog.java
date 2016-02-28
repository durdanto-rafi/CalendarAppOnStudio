package com.uniqgroup.customdialog;

import java.text.DecimalFormat;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.TimePicker;
import android.widget.Toast;

import com.uniqgroup.application.EventManageActivity;
import com.uniqgroup.application.R;
import com.uniqgroup.pojo.EventSequence;
import com.uniqgroup.utility.ImageProcessing;

public class EventCreateCustomDialog extends Dialog implements
		android.view.View.OnClickListener {
	public Activity c;
	EventManageActivity event_act;
	ImageSearchCustomDialog imageSearchDialog=null;
	
	
	
	ImageProcessing imgProc;
	//public Dialog dialog;
	public ImageButton yes, no, gallery_image_bt, camera_image_bt,
			sdcard_image_bt, video_add_imb, ibtnClear;

	public EditText minute_edit_text, second_edit_text, tts_edit_text;
	ImageView ivDone, ivClose, ivTime, ivProfile;

	TextView  tvVideo;
	int grid_box_pos, seqId;
	TextView tvDisplayTime ;
	ImageView ivCurrent;
	EventSequence eventSeq;

	EventManageActivity eventManageActivity = null;

	public EventCreateCustomDialog(Activity a, EventManageActivity eca,int box_pos, ImageView currentIvId, EventSequence eventSeq) 
	{
		super(a, R.style.CustomAlertDialog);
		this.c = a;
		this.event_act = eca;
		this.grid_box_pos = box_pos;
		this.ivCurrent = currentIvId;
		this.eventManageActivity = eca;
		this.eventSeq = eventSeq;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		DisplayMetrics metrics = c.getResources().getDisplayMetrics();
		int screenWidth = (int) (metrics.widthPixels * 0.80);
		int screenHeight = (int) (metrics.heightPixels * 0.80);
		FullScreencall();
		getWindow().setLayout(screenWidth, screenHeight);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		setContentView(R.layout.event_create_custom_dialog);
		setCancelable(false);
		
		imgProc = new ImageProcessing(c);
		ivDone = (ImageView) findViewById(R.id.ivDone);
		ivClose = (ImageView) findViewById(R.id.ivClose);
		ivTime = (ImageView) findViewById(R.id.ivTime);
		ivProfile = (ImageView) findViewById(R.id.ivProfile);
		tts_edit_text = (EditText) findViewById(R.id.edTTS);
		
		gallery_image_bt = (ImageButton) findViewById(R.id.gallery_image_bt);
		camera_image_bt = (ImageButton) findViewById(R.id.camera_image_bt);
		sdcard_image_bt = (ImageButton) findViewById(R.id.sdcard_image_bt);
		ibtnClear = (ImageButton) findViewById(R.id.ibtnClear);
		
		tts_edit_text = (EditText) findViewById(R.id.edTTS);
		tvVideo = (TextView) findViewById(R.id.tvVideo);
		tvDisplayTime = (TextView) findViewById(R.id.tvDisplayTime);
		
		tvVideo.setOnClickListener(this);
		ivDone.setOnClickListener(this);
		ivClose.setOnClickListener(this);
		ivTime.setOnClickListener(this);
		
		gallery_image_bt.setOnClickListener(this);
		camera_image_bt.setOnClickListener(this);
		sdcard_image_bt.setOnClickListener(this);
		ibtnClear.setOnClickListener(this);
		
		if(ivCurrent.getTag() != null)
		{
			eventSeq = new EventSequence();
			eventSeq = (EventSequence) ivCurrent.getTag();
			seqId = eventSeq.getID();
			ivProfile.setImageDrawable(ivCurrent.getDrawable());
			ivProfile.setImageMatrix(ivCurrent.getImageMatrix());
			ivProfile.setTag("Loaded");
			tts_edit_text.setText(eventSeq.getTts());
			tvVideo.setText(eventSeq.getSeqPath());
			if(eventSeq.getSeqPath().length()>0 && eventSeq.getSeqPath()!=null)
			{
				ibtnClear.setVisibility(View.VISIBLE);
			}
			
			int totalSecs = eventSeq.getSeqTimer();
			tvDisplayTime.setText(String.format("%02d:%02d", (totalSecs % 3600) / 60, totalSecs % 60));
		}
		
		tts_edit_text.setOnEditorActionListener(edTTSEditoractionListner);
		
		
	}

	public void setVideoPath(String path) {
		if(path.length()==0)
		{
			Toast.makeText(c, "Sorry, this video can't be set !", Toast.LENGTH_SHORT).show();
			return;
		}
		tvVideo.setText(path);
		ibtnClear.setVisibility(View.VISIBLE);
	}

	@Override
	public void onClick(View v) {

		int id = v.getId();
		if (id == R.id.ivDone) {
			if (validateUserCreateDialog()) {
				event_act.FullScreencall();
				dismiss();
				event_act.isActionManageDialogShow = false;
			}
		} else if (id == R.id.ivClose) {
			dismiss();
			event_act.FullScreencall();
			event_act.isActionManageDialogShow = false;
		} else if (id == R.id.tvVideo) {
			event_act.loadVideoFromDialog(grid_box_pos);
		} else if (id == R.id.ivTime) {
			TimePickerDialog mTimePicker;
			mTimePicker = new TimePickerDialog(c,
					new TimePickerDialog.OnTimeSetListener() {
						@Override
						public void onTimeSet(TimePicker timePicker,
								int selectedHour, int selectedMinute) {
							tvDisplayTime.setText(new DecimalFormat("00")
									.format(selectedHour)
									+ ":"
									+ new DecimalFormat("00")
											.format(selectedMinute));
						}
					}, Integer.parseInt(tvDisplayTime.getText().toString()
							.substring(0, 2)), Integer.parseInt(tvDisplayTime
							.getText().toString().substring(3, 5)), true);
			mTimePicker.setTitle("Select Minute and second");
			mTimePicker.show();
		}
		else if (id == R.id.sdcard_image_bt && event_act.isImageSearchDialogShow == false )
		{
			//event_act.loadImageStorage();
			
			event_act.imageSearchDialog =new ImageSearchCustomDialog(c, event_act);
			event_act.imageSearchDialog.show();
			event_act.isImageSearchDialogShow = true;
		} 
		
		else if (id == R.id.camera_image_bt) {
			event_act.loadImageCamera();
		} else if (id == R.id.gallery_image_bt) {
			event_act.loadImageGallery();
		}else if (id == R.id.ibtnClear) {
			tvVideo.setText(null);
			ibtnClear.setVisibility(View.INVISIBLE);
		}

	}

	public Boolean validateUserCreateDialog() {
		int timer_minute = Integer.parseInt(tvDisplayTime.getText().toString()
				.substring(0, 2));
		int timer_second = Integer.parseInt(tvDisplayTime.getText().toString()
				.substring(3, 5));
		String tts = tts_edit_text.getText().toString();

		int total_time = timer_minute * 60 + timer_second;

		if(ivProfile.getTag()==null){
			Toast.makeText(c, "Please provide action image !", Toast.LENGTH_SHORT)
			.show();
			return false;
		}
		if (tts.length() == 0) {
			Toast.makeText(c, "Please provide Name !", Toast.LENGTH_SHORT)
					.show();
			return false;
		}
		/*if (edVideo.getText().toString().length() == 0) {
			Toast.makeText(c, "Please provide a video file !",
					Toast.LENGTH_SHORT).show();
			return false;
		}
		if (total_time == 0) {
			Toast.makeText(c, "Please check the time !", Toast.LENGTH_SHORT)
					.show();
			return false;
		}*/

		eventSeq = new EventSequence();
		if(ivCurrent.getTag()!=null)
		{
			eventSeq = (EventSequence) ivCurrent.getTag();
		}
		eventSeq.setID(seqId);
		ivCurrent.setImageDrawable(ivProfile.getDrawable());
		ivCurrent.setImageMatrix(ivProfile.getImageMatrix());
		eventSeq.setSeqPath(tvVideo.getText().toString());
		eventSeq.setTts(tts);
		eventSeq.setSeqTimer(total_time);
		eventSeq.set_seq_position(grid_box_pos);
		eventSeq.setSeqType("Video");
		eventSeq.setStatus("Y");
		ivCurrent.setTag(eventSeq);
		return true;
	}
	
	public void FullScreencall() {
		if (Build.VERSION.SDK_INT < 19) {
			View v = this.getWindow().getDecorView();
			v.setSystemUiVisibility(View.GONE);
		} else {
			// for lower api versions.
			View decorView = getWindow().getDecorView();
			int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
			decorView.setSystemUiVisibility(uiOptions);
		}
	}
	
	OnEditorActionListener edTTSEditoractionListner = new OnEditorActionListener() 
	{
		@Override
		public boolean onEditorAction(TextView v, int actionId, KeyEvent event) 
		{
			if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) 
			{
				
			}    
			return false;
		}
	};
	
	public void setprofileImage(String imagePath) 
	{
		eventSeq = new EventSequence();
		if(ivCurrent.getTag()!=null)
		{
			eventSeq = (EventSequence) ivCurrent.getTag();
		}
		imgProc.setImageWith_loader2(ivProfile, imagePath);
		ivProfile.setTag("Loaded");
		eventSeq.setSeqImg("new");
		ivCurrent.setTag(eventSeq);
	}
}