package com.uniqgroup.application;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.uniqgroup.customdialog.PasswordDialog;
import com.uniqgroup.customdialog.PasswordDialog.SwitchONEditMode;
import com.uniqgroup.customui.Animanation;
import com.uniqgroup.database.DatabaseHandler;
import com.uniqgroup.fragment.DayViewFragment;
import com.uniqgroup.fragment.MonthViewFragment;
import com.uniqgroup.fragment.PrizeViewFragment;
import com.uniqgroup.fragment.WeekViewFragment;
import com.uniqgroup.pojo.AfterNoonEvent;
import com.uniqgroup.pojo.EveningEvent;
import com.uniqgroup.pojo.EventSequence;
import com.uniqgroup.pojo.MorningEvent;
import com.uniqgroup.pojo.Prize;
import com.uniqgroup.pojo.User;
import com.uniqgroup.pojo.WeekEvent;
import com.uniqgroup.utility.CalendarViews;
import com.uniqgroup.utility.Custom_Touch_Event;
import com.uniqgroup.utility.Custom_Touch_Event.OnFling_right_left;
import com.uniqgroup.utility.EditModeSetting;
import com.uniqgroup.utility.ImageProcessing;
import com.uniqgroup.utility.PasswordStorage;
import com.uniqgroup.utility.SelectModeStatic;

@SuppressLint("SimpleDateFormat")
public class UserEventListActivity extends Activity implements
		OnFling_right_left, SwitchONEditMode, TextToSpeech.OnInitListener,
		OnClickListener {

	DatabaseHandler db;
	int selected_tab = 0;
	int selected_weekTab = 1;
	int selected_monthTab = 2, touchCount,
			previousSelectedAdapter = -1, previousSelectedPosition = -1;
	User current_user;
	public String dayOnly;
	public String weekNumberOnly;
	public String monthOnly;
	private ProgressDialog PdSummary;

	public boolean isMVICDialog = false;

	Calendar calendar = Calendar.getInstance();
	SimpleDateFormat monthNameFormate = new SimpleDateFormat("MMMM");
	SimpleDateFormat dayNameFormate = new SimpleDateFormat("EEEE");

	ImageButton addEventButton = null, ibtnBack;
	ArrayList<MorningEvent> morn_list;
	ArrayList<AfterNoonEvent> aftern_list;
	ArrayList<EveningEvent> even_list;

	ArrayList<Integer> activityIdForDelete = new ArrayList<Integer>();
	
	ArrayList<Integer> morningSelectedPositions = new ArrayList<Integer>();
	ArrayList<Integer> afternoonSelectedPositions = new ArrayList<Integer>();
	ArrayList<Integer> eveningSelectedPositions = new ArrayList<Integer>();
	
	ArrayList<Integer> mondaySelectedPositions = new ArrayList<Integer>();
	ArrayList<Integer> tuesdaySelectedPositions = new ArrayList<Integer>();
	ArrayList<Integer> wednesdaySelectedPositions = new ArrayList<Integer>();
	ArrayList<Integer> thrusdaySelectedPositions = new ArrayList<Integer>();
	ArrayList<Integer> fridaySelectedPositions = new ArrayList<Integer>();
	ArrayList<Integer> saturdaySelectedPositions = new ArrayList<Integer>();
	ArrayList<Integer> sundaySelectedPositions = new ArrayList<Integer>();

	ArrayList<WeekEvent> monday_list;
	ArrayList<WeekEvent> tuesday_list;
	ArrayList<WeekEvent> wednesday_list;
	ArrayList<WeekEvent> thursday_list;
	ArrayList<WeekEvent> friday_list;
	ArrayList<WeekEvent> saturday_list;
	ArrayList<WeekEvent> sunday_list;
	
	ArrayList<Prize> prizeList;

	public Calendar centralDate = GregorianCalendar.getInstance(Locale.FRANCE);
	Fragment fragment = null;

	private TextToSpeech tts;
	int edit_mode = 0, multiSelectionMode = 0;
	RelativeLayout gesturelistener_ln, rlEventFinger;
	LinearLayout llBorder;
	Custom_Touch_Event listener_customTouch;
	public ImageProcessing imgProc;
	TextView tvDateMain, tvAvaterName;
	UserEventListActivity ref;

	TextView tvDateSwitcher;
	ImageButton ibDay, ibWeek, ibMonth, ibPrev, ibNext, ibPrize,
			ibtnClearEditMode, ibtnDelete;
	ImageView add_event_IV, ivEventFinger, ivPreviousSelected;

	String current_user_id, currentView;

	boolean isEmpty = false;

	OnTouchListener fling_listener = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if (listener_customTouch != null)
				listener_customTouch.onTouch(v, event);
			return true;
		}
	};

	@Override
	public void onBackPressed() {
		Intent eventList = new Intent(this, UserCreateActivity.class);
		eventList.putExtra("user_id", current_user_id); // Optional parameters
		startActivity(eventList);
		finish();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_event_list);
		ref = this;
		Intent intent = getIntent();
		current_user_id = intent.getStringExtra("user_id");
		currentView = intent.getStringExtra("returnView");
		// eventId = intent.getScheme();

		imgProc = new ImageProcessing(this);

		tvDateMain = (TextView) findViewById(R.id.tvUserMonthName);

		addEventButton = (ImageButton) findViewById(R.id.add_event_imbt);
		ibtnClearEditMode = (ImageButton) findViewById(R.id.ibtnClearEditMode);
		llBorder = (LinearLayout) findViewById(R.id.llBorder);
		ibtnBack = (ImageButton) findViewById(R.id.ibtnBack);
		ibtnDelete = (ImageButton) findViewById(R.id.ibtnDelete);
		ibDay = (ImageButton) findViewById(R.id.ib_day);
		ibWeek = (ImageButton) findViewById(R.id.ib_week);
		ibMonth = (ImageButton) findViewById(R.id.ib_month);
		ibPrev = (ImageButton) findViewById(R.id.ibPrev);
		ibNext = (ImageButton) findViewById(R.id.ibNext);
		ibPrize = (ImageButton) findViewById(R.id.ibPrize);

		tvDateSwitcher = (TextView) findViewById(R.id.tvMiddle);

		visibilitySetting();
		tts = new TextToSpeech(this, this);

		gesturelistener_ln = (RelativeLayout) findViewById(R.id.mainLinear_Dayview);
		gesturelistener_ln.setOnTouchListener(fling_listener);
		listener_customTouch = new Custom_Touch_Event(this);

		rlEventFinger = (RelativeLayout) findViewById(R.id.ll_animation_finger_eventcreate);
		ivEventFinger = (ImageView) findViewById(R.id.event_list_animation_finger);

		// addEventButton = (ImageButton)findViewById(R.id.add_event_imbt);
		db = new DatabaseHandler(this);
		displayAvatarAndName();
		userWiseView();

		ibDay.setOnClickListener(this);
		ibWeek.setOnClickListener(this);
		ibMonth.setOnClickListener(this);

		ibPrev.setOnClickListener(this);
		ibNext.setOnClickListener(this);
		ibPrize.setOnClickListener(this);

		ibtnBack.setOnClickListener(this);
		ibtnClearEditMode.setOnClickListener(this);

		ibtnDelete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) 
			{
				if (activityIdForDelete.size() == 0) 
				{
					return;
				}
				
				for (int i : activityIdForDelete) 
				{
					db.deleteAllEventData(i);
				}
				
				if (currentView.equals(CalendarViews.dayView)) 
				{
					AsycLoadDayView asycDayView = new AsycLoadDayView();
					asycDayView.execute();
				}
				else if (currentView.equals(CalendarViews.weekView)) 
				{
					AsycLoadWeekView asycWeekView = new AsycLoadWeekView();
					asycWeekView.execute();
				}
				Toast.makeText(ref, "Selected activities deleted successfully !", Toast.LENGTH_SHORT).show();
			}
		});

		llBorder.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				
				if (currentView.equals(CalendarViews.dayView))
				{
					AsycLoadDayView asycDayView = new AsycLoadDayView();
					asycDayView.execute();
				}
				if (currentView.equals(CalendarViews.weekView))
				{
					AsycLoadWeekView asycWeekView = new AsycLoadWeekView();
					asycWeekView.execute();
				}
			}
		});

		// loading day fragment

		FullScreencall();

	}

	public Boolean isEditMode() {
		return edit_mode == 1;
	}

	public String getCurrentUserId() {

		return current_user_id;
	}

	// loading different fragment
	// day / week / Month View
	public boolean loadFragment(String view) {

		boolean flag = false;

		String centralWeekNum = String.valueOf(centralDate
				.get(Calendar.WEEK_OF_YEAR));
		String centralWeekDay = new SimpleDateFormat("EE", Locale.ENGLISH)
				.format(centralDate.getTime());

		if (view.equals(CalendarViews.dayView)) {
			morn_list = (ArrayList<MorningEvent>) db.morningEventList(
					centralWeekDay, centralWeekNum, current_user.getID());
			aftern_list = (ArrayList<AfterNoonEvent>) db.afterNoonEventList(
					centralWeekDay, centralWeekNum, current_user.getID());
			even_list = (ArrayList<EveningEvent>) db.eveningEventList(
					centralWeekDay, centralWeekNum, current_user.getID());
			flag = true;
		}

		else if (view.equals(CalendarViews.weekView)) {
			monday_list = (ArrayList<WeekEvent>) db.weekEventList("Mon",
					centralWeekNum, current_user.getID());
			tuesday_list = (ArrayList<WeekEvent>) db.weekEventList("Tue",
					centralWeekNum, current_user.getID());
			wednesday_list = (ArrayList<WeekEvent>) db.weekEventList("Wed",
					centralWeekNum, current_user.getID());
			thursday_list = (ArrayList<WeekEvent>) db.weekEventList("Thu",
					centralWeekNum, current_user.getID());
			friday_list = (ArrayList<WeekEvent>) db.weekEventList("Fri",
					centralWeekNum, current_user.getID());
			saturday_list = (ArrayList<WeekEvent>) db.weekEventList("Sat",
					centralWeekNum, current_user.getID());
			sunday_list = (ArrayList<WeekEvent>) db.weekEventList("Sun",
					centralWeekNum, current_user.getID());
			flag = true;
		}

		else if (view.equals(CalendarViews.monthView)) {
			fragment = new MonthViewFragment();
			selected_monthTab = 2;
			FragmentManager managerMonth = getFragmentManager();
			FragmentTransaction transactionMonth = managerMonth.beginTransaction();
			transactionMonth.addToBackStack(null);
			transactionMonth.replace(R.id.event_list_container, fragment,
					"MonthViewFragment").commit();
			flag = true;
		}
		else if (view.equals(CalendarViews.prizeView)) {
			
			//prizeList= (ArrayList<Prize>) db.getPrizeData(CalenderDateList.getCurrentWeekNumber(), current_user.getID());
			
			fragment = new PrizeViewFragment();
			FragmentManager managerMonth = getFragmentManager();
			FragmentTransaction transactionMonth = managerMonth.beginTransaction();
			transactionMonth.addToBackStack(null);
			transactionMonth.replace(R.id.event_list_container, fragment, "PrizeViewFragment").commit();
			flag = true;
		}
		return flag;

	}

	public void loadMonthViewInfo() {
		if (monday_list == null && tuesday_list == null
				&& wednesday_list == null && thursday_list == null
				&& friday_list == null && saturday_list == null
				&& sunday_list == null) {
			startFingerAnimation();
		} else {
			stopAnimation();
		}

		if (monday_list == null) {
			monday_list = new ArrayList<WeekEvent>();
		}
		if (tuesday_list == null) {
			tuesday_list = new ArrayList<WeekEvent>();
		}
		if (wednesday_list == null) {
			wednesday_list = new ArrayList<WeekEvent>();
		}
		if (thursday_list == null) {
			thursday_list = new ArrayList<WeekEvent>();
		}
		if (friday_list == null) {
			friday_list = new ArrayList<WeekEvent>();
		}
		if (saturday_list == null) {
			saturday_list = new ArrayList<WeekEvent>();
		}
		if (sunday_list == null) {
			sunday_list = new ArrayList<WeekEvent>();
		}

		fragment = WeekViewFragment.getInstance(this, monday_list,
				tuesday_list, wednesday_list, thursday_list, friday_list,
				saturday_list, sunday_list);
		selected_weekTab = 1;

		FragmentManager managerWeek = getFragmentManager();
		FragmentTransaction transactionWeek = managerWeek.beginTransaction();
		transactionWeek.addToBackStack(null);
		transactionWeek.replace(R.id.event_list_container, fragment,
				"WeekViewFragment").commit();
	}

	public void loadDayViewInfo() {
		if (morn_list == null && even_list == null && aftern_list == null) {
			startFingerAnimation();
		} else {
			stopAnimation();
		}

		if (morn_list == null) {
			morn_list = new ArrayList<MorningEvent>();
		}

		if (even_list == null) {
			even_list = new ArrayList<EveningEvent>();
		}

		if (aftern_list == null) {
			aftern_list = new ArrayList<AfterNoonEvent>();
		}

		fragment = DayViewFragment.getInstance(this, morn_list, aftern_list,
				even_list);
		selected_tab = 0;

		FragmentManager managerDay = getFragmentManager();
		FragmentTransaction transactionDay = managerDay.beginTransaction();
		transactionDay.addToBackStack(null);
		transactionDay.replace(R.id.event_list_container, fragment,
				"DayViewFragment").commit();
	}

	public void displayAvatarAndName() {
		Intent intent = getIntent();
		String user_id = intent.getStringExtra("user_id");

		current_user = db.getUser(Integer.parseInt(user_id));
		TextView avatar_name_text = (TextView) findViewById(R.id.user_avatar_name);
		avatar_name_text.setText(current_user.getName());
		ImageView avatar_view = (ImageView) findViewById(R.id.user_avatar);
		imgProc.setImageWith_loader(avatar_view, current_user.getProfilePic());

	}

	public void addNewEvent(View v) {
		Intent intent = new Intent(this, EventManageActivity.class);
		intent.putExtra("user_id", String.valueOf(current_user.getID()));
		intent.putExtra("flag", "New");
		intent.putExtra("returnPath",
				"com.uniqgroup.application.UserEventListActivity");
		intent.putExtra("returnView", currentView);
		startActivity(intent);
		finish();

	}

	public void switchEdit_ON(boolean flag) {
		if (flag == true) {
			EditModeSetting.setEditMode(1);
		} else {
			EditModeSetting.setEditMode(0);
		}
		visibilitySetting();

	}

	public void visibilitySetting() {
		edit_mode = EditModeSetting.getEditMode();
		if (isEditMode()) {
			addEventButton.setVisibility(View.VISIBLE);
			tvDateMain.setVisibility(View.VISIBLE);
			ibtnBack.setVisibility(View.VISIBLE);
			ibtnClearEditMode.setVisibility(View.VISIBLE);
			llBorder.setBackgroundResource(R.drawable.bg_transparente_edition);
			tvDateSwitcher.setVisibility(View.VISIBLE);
			ibNext.setVisibility(View.VISIBLE);
			ibPrev.setVisibility(View.VISIBLE);
			ibDay.setVisibility(View.VISIBLE);
			ibMonth.setVisibility(View.VISIBLE);
			ibWeek.setVisibility(View.VISIBLE);
			ibPrize.setVisibility(View.VISIBLE);
		} else {

			tvDateMain.setVisibility(View.INVISIBLE);
			addEventButton.setVisibility(View.INVISIBLE);
			ibtnBack.setVisibility(View.INVISIBLE);
			ibtnClearEditMode.setVisibility(View.INVISIBLE);
			llBorder.setBackground(null);
			tvDateSwitcher.setVisibility(View.INVISIBLE);
			ibNext.setVisibility(View.INVISIBLE);
			ibPrev.setVisibility(View.INVISIBLE);
			ibDay.setVisibility(View.INVISIBLE);
			ibMonth.setVisibility(View.INVISIBLE);
			ibWeek.setVisibility(View.INVISIBLE);
			ibPrize.setVisibility(View.INVISIBLE);
		}
	}

	@Override
	public void flingRL_happened(boolean editOn) {
		int curr_pass_index = PasswordStorage
				.getCurrentPasswordIndex(current_user.getPassword());
		if (editOn) {
			ivEventFinger.setVisibility(View.GONE);
			rlEventFinger.setVisibility(View.GONE);
			ivEventFinger.clearAnimation();

			if (curr_pass_index == 0) {
				switchEdit_ON(true);
			} else {
				switchEdit_ON(false);
				new PasswordDialog(this, curr_pass_index, this).show();
			}
		} else {
			Animanation.clear(addEventButton);
			switchEdit_ON(false);
		}
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

	// ///used for tts mir***************//////////////////////
	public void speakOut(String speak) {
		/*
		 * System.out.println("speak out called:" + speak); HashMap<String,
		 * String> map = new HashMap<String, String>();
		 * map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID,
		 * String.valueOf(middle_cell.getID()));
		 */
		if (tts != null)
			tts.speak(speak, TextToSpeech.QUEUE_FLUSH, null);// , map);
	}

	@Override
	public void onDestroy() {
		// Don't forget to shutdown tts!
		if (tts != null) {
			tts.stop();
			tts.shutdown();
		}
		super.onDestroy();
	}

	@Override
	public void onInit(int status) {

		if (status == TextToSpeech.SUCCESS) {

			int result = tts.setLanguage(Locale.US);
			if (result == TextToSpeech.LANG_MISSING_DATA
					|| result == TextToSpeech.LANG_NOT_SUPPORTED) {
				// Log.e("TTS", "This Language is not supported");
			} else {

			}

		} else {
			Log.e("TTS", "Initilization Failed!");
		}

	}

	@Override
	public void onClick(View v) {

		AsycLoadDayView asycDayView = new AsycLoadDayView();
		AsycLoadWeekView asycWeekView = new AsycLoadWeekView();
		AsycLoadMonthView asycMonthView = new AsycLoadMonthView();

		switch (v.getId()) {
		case R.id.ib_day:
			asycDayView.execute();
			//currentView = CalendarViews.dayView;
			//changeViewChangerIcon();
			break;

		case R.id.ib_week:
			asycWeekView.execute();
			//currentView = CalendarViews.weekView;
			//changeViewChangerIcon();
			break;

		case R.id.ib_month:
			asycMonthView.execute();
			//currentView = CalendarViews.monthView;
			//changeViewChangerIcon();
			break;
		
		case R.id.ibPrize:
			loadFragment(CalendarViews.prizeView);
			currentView = CalendarViews.prizeView;
			changeViewChangerIcon();
			break;
		
		case R.id.ibPrev:
			switch (currentView) {
			case CalendarViews.dayView:
				centralDate.add(Calendar.DATE, -1);
				asycDayView.execute();
				break;

			case CalendarViews.weekView:
				centralDate.add(Calendar.WEEK_OF_YEAR, -1);
				asycWeekView.execute();
				break;

			case CalendarViews.monthView:
				centralDate.add(Calendar.MONTH, -1);
				asycMonthView.execute();
				break;

			default:
				break;
			}
			/*touchCount = 0;
			previousSelectedPosition = -1;
			previousSelectedAdapter = -1;*/
			break;

		case R.id.ibNext:
			switch (currentView) {
			case CalendarViews.dayView:
				centralDate.add(Calendar.DATE, 1);
				asycDayView.execute();
				break;

			case CalendarViews.weekView:
				centralDate.add(Calendar.WEEK_OF_YEAR, 1);
				asycWeekView.execute();
				break;

			case CalendarViews.monthView:
				centralDate.add(Calendar.MONTH, 1);
				asycMonthView.execute();
				break;

			default:
				break;
			}
			/*touchCount = 0;
			previousSelectedPosition = -1;
			previousSelectedAdapter = -1;*/
			break;

		case R.id.ibtnBack:
			Intent intent = new Intent(UserEventListActivity.this, UserCreateActivity.class);
			startActivity(intent);
			finish();
			break;

		case R.id.ibtnClearEditMode:
			flingRL_happened(false);
			ibtnDelete.setVisibility(View.INVISIBLE);
			if (currentView.equals(CalendarViews.dayView)) {
				AsycLoadDayView asycDayView2 = new AsycLoadDayView();
				asycDayView2.execute();
			}
			if (currentView.equals(CalendarViews.weekView)) {
				AsycLoadWeekView asycWeekView2 = new AsycLoadWeekView();
				asycWeekView2.execute();
			}
			break;

		default:
			break;
		}

	}

	public void checkSelectedActivity(ImageView currentSelected, int eventId, String tts, int deleteMode, int pos, int adapter) 
	{
		if (multiSelectionMode == 0 && deleteMode == 0) 
		{
			if (touchCount == 1 && previousSelectedPosition == pos && previousSelectedAdapter == adapter) 
			{
				Intent intent = null;
				if (isEditMode()) 
				{
					intent = new Intent(ref, EventManageActivity.class);
				} 
				else 
				{
					if (db.checkEventSeqStatus(eventId)) 
					{
						speakOut(tts);
						intent = new Intent(ref, EventSeqActivity.class);
					} 
					else 
					{
						showRestartActiviyAlertDialog(eventId);
						return;
					}
				}
				intent.putExtra("event_id", eventId);
				intent.putExtra("user_id", getCurrentUserId());
				intent.putExtra("flag", "Update");
				intent.putExtra("returnPath", "com.uniqgroup.application.UserEventListActivity");
				intent.putExtra("returnView", CalendarViews.dayView);
				startActivity(intent);
				finishActivity();
			} 
			else 
			{
				if (previousSelectedAdapter != -1 && previousSelectedPosition != -1) 
				{
					if (previousSelectedAdapter == 1) 
					{
						MorningEvent event = morn_list.get(previousSelectedPosition);
						event.set_select_mode(SelectModeStatic.notSelected);
						morn_list.set(previousSelectedPosition, event);
					} 
					else if (previousSelectedAdapter == 2) 
					{
						AfterNoonEvent event = aftern_list.get(previousSelectedPosition);
						event.set_select_mode(SelectModeStatic.notSelected);
						aftern_list.set(previousSelectedPosition, event);
					} 
					else if (previousSelectedAdapter == 3) 
					{
						EveningEvent eveningEvent = even_list.get(previousSelectedPosition);
						eveningEvent.set_select_mode(SelectModeStatic.notSelected);
						even_list.set(previousSelectedPosition, eveningEvent);
					}
					activityIdForDelete.clear();
				}

				if (adapter == 1) 
				{
					MorningEvent event = morn_list.get(pos);
					event.set_select_mode(SelectModeStatic.singleSelected);
					morn_list.set(pos, event);
					previousSelectedPosition = pos;
					previousSelectedAdapter = adapter;
					touchCount = 1;
				} 
				else if (adapter == 2) 
				{
					AfterNoonEvent event = aftern_list.get(pos);
					event.set_select_mode(SelectModeStatic.singleSelected);
					aftern_list.set(pos, event);
					previousSelectedPosition = pos;
					previousSelectedAdapter = adapter;
					touchCount = 1;
				} 
				else if (adapter == 3) 
				{
					EveningEvent eveningEvent = even_list.get(pos);
					eveningEvent.set_select_mode(SelectModeStatic.singleSelected);
					even_list.set(pos, eveningEvent);
					previousSelectedPosition = pos;
					previousSelectedAdapter = adapter;
					touchCount = 1;
				}

				if (isEditMode()) 
				{
					ibtnDelete.setVisibility(View.VISIBLE);
					activityIdForDelete.add(eventId);
				}
			}
		} 
		else 
		{
			if (isEditMode()) 
			{
				multiSelectionMode = 1;
				if (previousSelectedPosition != -1) 
				{
					if (previousSelectedAdapter == 1) 
					{
						MorningEvent event = morn_list.get(previousSelectedPosition);
						event.set_select_mode(SelectModeStatic.notSelected);
						morn_list.set(previousSelectedPosition, event);
					} 
					else if (previousSelectedAdapter == 2) 
					{
						AfterNoonEvent event = aftern_list.get(previousSelectedPosition);
						event.set_select_mode(SelectModeStatic.notSelected);
						aftern_list.set(previousSelectedPosition, event);
					} 
					else if (previousSelectedAdapter == 3) 
					{
						EveningEvent eveningEvent = even_list.get(previousSelectedPosition);
						eveningEvent.set_select_mode(SelectModeStatic.notSelected);
						even_list.set(previousSelectedPosition, eveningEvent);
					}
				}
				if (!activityIdForDelete.contains(eventId)) 
				{
					activityIdForDelete.add(eventId);
					if (adapter == 1) 
					{
						morningSelectedPositions.add(pos);
						MorningEvent event = morn_list.get(pos);
						event.set_select_mode(SelectModeStatic.multiSelected);
						morn_list.set(pos, event);
					}

					else if (adapter == 2) 
					{
						afternoonSelectedPositions.add(pos);
						AfterNoonEvent event = aftern_list.get(pos);
						event.set_select_mode(SelectModeStatic.multiSelected);
						aftern_list.set(pos, event);
					} 
					else if (adapter == 3) 
					{
						eveningSelectedPositions.add(pos);
						EveningEvent event = even_list.get(pos);
						event.set_select_mode(SelectModeStatic.multiSelected);
						even_list.set(pos, event);
					}
				} 
				else 
				{
					activityIdForDelete.remove((Integer) eventId);
					if (adapter == 1) 
					{
						morningSelectedPositions.remove((Integer) pos);
						MorningEvent event = morn_list.get(pos);
						event.set_select_mode(SelectModeStatic.notSelected);
					}

					else if (adapter == 2) 
					{
						afternoonSelectedPositions.remove((Integer) pos);
						AfterNoonEvent event = aftern_list.get(pos);
						event.set_select_mode(SelectModeStatic.notSelected);
					} 
					else if (adapter == 3) 
					{
						eveningSelectedPositions.remove((Integer) pos);
						EveningEvent event = even_list.get(pos);
						event.set_select_mode(SelectModeStatic.notSelected);
					}
				}
				ibtnDelete.setVisibility(View.VISIBLE);
			}
		}
		if (currentView.equals(CalendarViews.dayView)) 
		{
			notifyDatasetDayview();
		}

	}

	private void finishActivity() {
		if (tts != null) {
			tts.stop();
			tts.shutdown();
		}
		finish();
	}

	private void notifyDatasetDayview() {
		DayViewFragment fragment = (DayViewFragment) getFragmentManager().findFragmentByTag("DayViewFragment");
		fragment.callNotifyMorning(morn_list);
		fragment.callNotifyAfternoon(aftern_list);
		fragment.callNotifyEvening(even_list);
	}
	
	private void notifyDatasetWeekview() {
		WeekViewFragment fragment = (WeekViewFragment) getFragmentManager().findFragmentByTag("WeekViewFragment");
		fragment.callNotifyMonday(monday_list);
		fragment.callNotifyTuesday(tuesday_list);
		fragment.callNotifyWednesday(wednesday_list);
		fragment.callNotifyThrusday(thursday_list);
		fragment.callNotifyFriday(friday_list);
		fragment.callNotifySaturday(saturday_list);
		fragment.callNotifySunday(sunday_list);
	}

	public void reloadMonthView() {
		// loadFragment(CalendarViews.monthView);
		AsycLoadMonthView asycMonthView = new AsycLoadMonthView();
		asycMonthView.execute();
	}

	private void userWiseView() {
		if (currentView.length() > 0) {
			switch (currentView) {
			case CalendarViews.dayView:
				AsycLoadDayView asycDayView = new AsycLoadDayView();
				asycDayView.execute();
				
				break;

			case CalendarViews.weekView:
				AsycLoadWeekView asyWeekView = new AsycLoadWeekView();
				asyWeekView.execute();
				

				break;
			case CalendarViews.monthView:
				AsycLoadMonthView asyMonthView = new AsycLoadMonthView();
				asyMonthView.execute();

				break;

			default:
				break;
			}
		} else {
			switch (current_user.get_default_view()) {
			case CalendarViews.dayView:
				AsycLoadDayView asycDayView = new AsycLoadDayView();
				asycDayView.execute();
				//currentView = current_user.get_default_view();
				break;

			case CalendarViews.weekView:
				AsycLoadWeekView asyWeekView = new AsycLoadWeekView();
				asyWeekView.execute();
				//currentView = current_user.get_default_view();
				break;
			case CalendarViews.monthView:
				AsycLoadMonthView asyMonthView = new AsycLoadMonthView();
				asyMonthView.execute();
				//currentView = current_user.get_default_view();
				break;

			default:
				break;
			}
		}
	}

	public void startFingerAnimation() {
		TranslateAnimation animation = new TranslateAnimation(-600.0f, 0.0f,
				0.0f, 0.0f);
		animation.setDuration(3000); // animation duration
		animation.setRepeatCount(Animation.INFINITE); // animation repeat //
														// count
		animation.setRepeatMode(1);
		ivEventFinger.setVisibility(View.VISIBLE);
		ivEventFinger.startAnimation(animation);

		Animanation.blink(addEventButton);
		ibtnClearEditMode.setEnabled(false);
		ibtnBack.setEnabled(false);

		isEmpty = true;
		rlEventFinger.setVisibility(View.VISIBLE);
	}

	public void stopAnimation() {
		Animanation.clear(addEventButton);
		ibtnClearEditMode.setEnabled(true);
		ibtnBack.setEnabled(true);
		rlEventFinger.setVisibility(View.INVISIBLE);
	}

	class AsycLoadDayView extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
			disableFragmentSwitcher();
			PdSummary = new ProgressDialog(ref);
			PdSummary.setMessage("Loading Dayview...");
			PdSummary.setIndeterminate(false);
			PdSummary.setCancelable(false);
			PdSummary.show();
			FullScreencall();
		}

		@Override
		protected String doInBackground(String... params) {
			if (loadFragment(CalendarViews.dayView) == true) {
				return "ok";
			} else {
				return "err";
			}
		}

		@Override
		protected void onPostExecute(String result) {
			if (result.equals("ok")) {
				tvDateSwitcher.setText(new SimpleDateFormat("EEEE")
						.format(centralDate.getTime()));
				tvDateMain.setText("Week "
						+ centralDate.get(Calendar.WEEK_OF_YEAR)
						+ new SimpleDateFormat(" MMMM yyyy").format(centralDate
								.getTime()));
				enableFragmentSwitcher();
				loadDayViewInfo();
				resetAllValues();
				currentView = CalendarViews.dayView;
				changeViewChangerIcon();
				PdSummary.dismiss();
				FullScreencall();

			} else {
				PdSummary.dismiss();
				Toast.makeText(ref, "No New Data", Toast.LENGTH_SHORT).show();
			}
		}
	}

	class AsycLoadWeekView extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
			disableFragmentSwitcher();

			PdSummary = new ProgressDialog(ref);
			PdSummary.setMessage("Loading Weekview...");
			PdSummary.setIndeterminate(false);
			PdSummary.setCancelable(false);
			PdSummary.show();
			FullScreencall();
		}

		@Override
		protected String doInBackground(String... params) {
			if (loadFragment(CalendarViews.weekView) == true) {
				return "ok";
			} else {
				return "err";
			}
		}

		@Override
		protected void onPostExecute(String result) {
			if (result.equals("ok")) {
				tvDateSwitcher.setText("Week "
						+ centralDate.get(Calendar.WEEK_OF_YEAR));
				tvDateMain.setText(new SimpleDateFormat(" MMMM yyyy")
						.format(centralDate.getTime()));
				enableFragmentSwitcher();
				loadMonthViewInfo();
				resetAllValues();
				currentView = CalendarViews.weekView;
				changeViewChangerIcon();
				PdSummary.dismiss();
				FullScreencall();
			} else {
				PdSummary.dismiss();
				Toast.makeText(ref, "No New Data", Toast.LENGTH_SHORT).show();
			}
		}
	}

	class AsycLoadMonthView extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
			PdSummary = new ProgressDialog(ref);
			PdSummary.setMessage("Loading Monthview...");
			PdSummary.setIndeterminate(false);
			PdSummary.setCancelable(false);
			PdSummary.show();
			disableFragmentSwitcher();
			FullScreencall();
		}

		@Override
		protected String doInBackground(String... params) {
			if (loadFragment(CalendarViews.monthView) == true) {
				return "ok";
			} else {
				return "err";
			}
		}

		@Override
		protected void onPostExecute(String result) {
			if (result.equals("ok")) {
				tvDateSwitcher.setText(new SimpleDateFormat("MMMM")
						.format(centralDate.getTime()));
				tvDateMain.setText("Year "
						+ String.valueOf(centralDate.get(Calendar.YEAR)));
				enableFragmentSwitcher();
				currentView = CalendarViews.monthView;
				changeViewChangerIcon();
				PdSummary.dismiss();
				FullScreencall();
			} else {
				PdSummary.dismiss();
				Toast.makeText(ref, "No New Data", Toast.LENGTH_SHORT).show();
			}
		}
	}

	private void disableFragmentSwitcher() {
		ibDay.setEnabled(false);
		ibMonth.setEnabled(false);
		ibMonth.setEnabled(false);
	}

	private void enableFragmentSwitcher() {
		ibDay.setEnabled(true);
		ibMonth.setEnabled(true);
		ibMonth.setEnabled(true);
	}

	public void accessMonthViewToDayView() {

		AsycLoadDayView asycDayView = new AsycLoadDayView();
		asycDayView.execute();
	}

	/** Shows the dialog to get feedback **/
	public void showRestartActiviyAlertDialog(final int eventId) {

		final Dialog dialog = new Dialog(this, R.style.CustomAlertDialog);
		dialog.setContentView(R.layout.feedback_relod_dialog);
		dialog.getWindow().setTitleColor(getResources().getColor(R.color.blue));
		final ImageView happyFace = (ImageView) dialog.findViewById(R.id.happyFace_feedback_reload);
		final ImageView sadFace = (ImageView) dialog.findViewById(R.id.sad_feedback_reload);

		happyFace.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				
				EventSequence evtSeq = new EventSequence();
				evtSeq.setEventId(eventId);
				evtSeq.setStatus("0");
				db.updateEventSeqStatusByEventId(evtSeq);
				
				Intent intent = new Intent(ref, EventSeqActivity.class);
				intent.putExtra("event_id", eventId);
				intent.putExtra("user_id", ref.getCurrentUserId());
				intent.putExtra("flag", "Update");
				intent.putExtra("returnPath", "com.uniqgroup.application.UserEventListActivity");
				intent.putExtra("returnView", CalendarViews.weekView);
				startActivity(intent);
				finishActivity();
			}
		});
		sadFace.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				FullScreencall();
				dialog.dismiss();
			}
		});

		dialog.show();
	}

	public void selectionProcessWeekView(int eventId, String tts, int multiSelectMode, int pos, int adapter) 
	{
		if (multiSelectionMode == 0 && multiSelectMode == 0) 
		{
			if (touchCount == 1 && previousSelectedPosition == pos && previousSelectedAdapter == adapter) 
			{
				Intent intent = null;
				if (isEditMode()) 
				{
					intent = new Intent(ref, EventManageActivity.class);
				} 
				else 
				{
					if (db.checkEventSeqStatus(eventId)) 
					{
						speakOut(tts);
						intent = new Intent(ref, EventSeqActivity.class);
					} 
					else 
					{
						showRestartActiviyAlertDialog(eventId);
						return;
					}
				}
				intent.putExtra("event_id", eventId);
				intent.putExtra("user_id", getCurrentUserId());
				intent.putExtra("flag", "Update");
				intent.putExtra("returnPath", "com.uniqgroup.application.UserEventListActivity");
				intent.putExtra("returnView", CalendarViews.weekView);
				startActivity(intent);
				finishActivity();
			} 
			else 
			{
				if (previousSelectedAdapter != -1 && previousSelectedPosition != -1) 
				{
					if (previousSelectedAdapter == 1) 
					{
						WeekEvent event = monday_list.get(previousSelectedPosition);
						event.set_select_mode(SelectModeStatic.notSelected);
						monday_list.set(previousSelectedPosition, event);
					} 
					else if (previousSelectedAdapter == 2) 
					{
						WeekEvent event = tuesday_list.get(previousSelectedPosition);
						event.set_select_mode(SelectModeStatic.notSelected);
						tuesday_list.set(previousSelectedPosition, event);
					} 
					else if (previousSelectedAdapter == 3) 
					{
						WeekEvent event = wednesday_list.get(previousSelectedPosition);
						event.set_select_mode(SelectModeStatic.notSelected);
						wednesday_list.set(previousSelectedPosition, event);
					}
					else if (previousSelectedAdapter == 4) 
					{
						WeekEvent event = thursday_list.get(previousSelectedPosition);
						event.set_select_mode(SelectModeStatic.notSelected);
						thursday_list.set(previousSelectedPosition, event);
					} 
					else if (previousSelectedAdapter == 5) 
					{
						WeekEvent event = friday_list.get(previousSelectedPosition);
						event.set_select_mode(SelectModeStatic.notSelected);
						friday_list.set(previousSelectedPosition, event);
					} 
					else if (previousSelectedAdapter == 6) 
					{
						WeekEvent event = saturday_list.get(previousSelectedPosition);
						event.set_select_mode(SelectModeStatic.notSelected);
						saturday_list.set(previousSelectedPosition, event);
					} 
					else if (previousSelectedAdapter == 7) 
					{
						WeekEvent event = sunday_list.get(previousSelectedPosition);
						event.set_select_mode(SelectModeStatic.notSelected);
						sunday_list.set(previousSelectedPosition, event);
					}
					activityIdForDelete.clear();
				}

				if (adapter == 1) 
				{
					WeekEvent event = monday_list.get(pos);
					event.set_select_mode(SelectModeStatic.singleSelected);
					monday_list.set(pos, event);
					previousSelectedPosition = pos;
					previousSelectedAdapter = adapter;
					touchCount = 1;
				} 
				else if (adapter == 2) 
				{
					WeekEvent event = tuesday_list.get(pos);
					event.set_select_mode(SelectModeStatic.singleSelected);
					tuesday_list.set(pos, event);
					previousSelectedPosition = pos;
					previousSelectedAdapter = adapter;
					touchCount = 1;
				} 
				else if (adapter == 3) 
				{
					WeekEvent event = wednesday_list.get(pos);
					event.set_select_mode(SelectModeStatic.singleSelected);
					wednesday_list.set(pos, event);
					previousSelectedPosition = pos;
					previousSelectedAdapter = adapter;
					touchCount = 1;
				}
				else if (adapter == 4) 
				{
					WeekEvent event = thursday_list.get(pos);
					event.set_select_mode(SelectModeStatic.singleSelected);
					thursday_list.set(pos, event);
					previousSelectedPosition = pos;
					previousSelectedAdapter = adapter;
					touchCount = 1;
				}
				else if (adapter == 5) 
				{
					WeekEvent event = friday_list.get(pos);
					event.set_select_mode(SelectModeStatic.singleSelected);
					friday_list.set(pos, event);
					previousSelectedPosition = pos;
					previousSelectedAdapter = adapter;
					touchCount = 1;
				}
				else if (adapter == 6) 
				{
					WeekEvent event = saturday_list.get(pos);
					event.set_select_mode(SelectModeStatic.singleSelected);
					saturday_list.set(pos, event);
					previousSelectedPosition = pos;
					previousSelectedAdapter = adapter;
					touchCount = 1;
				}
				else if (adapter == 7) 
				{
					WeekEvent event = sunday_list.get(pos);
					event.set_select_mode(SelectModeStatic.singleSelected);
					sunday_list.set(pos, event);
					previousSelectedPosition = pos;
					previousSelectedAdapter = adapter;
					touchCount = 1;
				}

				if (isEditMode()) 
				{
					ibtnDelete.setVisibility(View.VISIBLE);
					activityIdForDelete.add(eventId);
				}
			}
		} 
		else 
		{
			if (isEditMode()) 
			{
				multiSelectionMode = 1;
				if (previousSelectedPosition != -1) 
				{
					if (previousSelectedAdapter == 1) 
					{
						WeekEvent event = monday_list.get(previousSelectedPosition);
						event.set_select_mode(SelectModeStatic.notSelected);
						monday_list.set(previousSelectedPosition, event);
					} 
					else if (previousSelectedAdapter == 2) 
					{
						WeekEvent event = tuesday_list.get(previousSelectedPosition);
						event.set_select_mode(SelectModeStatic.notSelected);
						tuesday_list.set(previousSelectedPosition, event);
					} 
					else if (previousSelectedAdapter == 3) 
					{
						WeekEvent event = wednesday_list.get(previousSelectedPosition);
						event.set_select_mode(SelectModeStatic.notSelected);
						wednesday_list.set(previousSelectedPosition, event);
					} 
					else if (previousSelectedAdapter == 4) 
					{
						WeekEvent event = thursday_list.get(previousSelectedPosition);
						event.set_select_mode(SelectModeStatic.notSelected);
						thursday_list.set(previousSelectedPosition, event);
					} 
					else if (previousSelectedAdapter == 5) 
					{
						WeekEvent event = friday_list.get(previousSelectedPosition);
						event.set_select_mode(SelectModeStatic.notSelected);
						friday_list.set(previousSelectedPosition, event);
					} 
					else if (previousSelectedAdapter == 6) 
					{
						WeekEvent event = saturday_list.get(previousSelectedPosition);
						event.set_select_mode(SelectModeStatic.notSelected);
						saturday_list.set(previousSelectedPosition, event);
					} 
					else if (previousSelectedAdapter == 7) 
					{
						WeekEvent event = sunday_list.get(previousSelectedPosition);
						event.set_select_mode(SelectModeStatic.notSelected);
						sunday_list.set(previousSelectedPosition, event);
					} 
				}
				
				if (!activityIdForDelete.contains(eventId)) 
				{
					activityIdForDelete.add(eventId);
					if (adapter == 1) 
					{
						mondaySelectedPositions.add(pos);
						WeekEvent event = monday_list.get(pos);
						event.set_select_mode(SelectModeStatic.multiSelected);
						monday_list.set(pos, event);
					}

					else if (adapter == 2) 
					{
						tuesdaySelectedPositions.add(pos);
						WeekEvent event = tuesday_list.get(pos);
						event.set_select_mode(SelectModeStatic.multiSelected);
						tuesday_list.set(pos, event);
					} 
					else if (adapter == 3) 
					{
						wednesdaySelectedPositions.add(pos);
						WeekEvent event = wednesday_list.get(pos);
						event.set_select_mode(SelectModeStatic.multiSelected);
						wednesday_list.set(pos, event);
					} 
					else if (adapter == 4) 
					{
						thrusdaySelectedPositions.add(pos);
						WeekEvent event = thursday_list.get(pos);
						event.set_select_mode(SelectModeStatic.multiSelected);
						thursday_list.set(pos, event);
					} 
					else if (adapter == 5) 
					{
						fridaySelectedPositions.add(pos);
						WeekEvent event = friday_list.get(pos);
						event.set_select_mode(SelectModeStatic.multiSelected);
						friday_list.set(pos, event);
					} 
					else if (adapter == 6) 
					{
						saturdaySelectedPositions.add(pos);
						WeekEvent event = saturday_list.get(pos);
						event.set_select_mode(SelectModeStatic.multiSelected);
						saturday_list.set(pos, event);
					} 
					else if (adapter == 7) 
					{
						tuesdaySelectedPositions.add(pos);
						WeekEvent event = sunday_list.get(pos);
						event.set_select_mode(SelectModeStatic.multiSelected);
						sunday_list.set(pos, event);
					} 
				} 
				else 
				{
					//activityIdForDelete.remove((Integer) eventId);
					if (adapter == 1) 
					{
						mondaySelectedPositions.remove((Integer) pos);
						WeekEvent event = monday_list.get(pos);
						if(event.get_select_mode()!=SelectModeStatic.multiSelected)
						{
							Toast.makeText(ref, "This activity is already selected", Toast.LENGTH_SHORT).show();
							return;
						}
						activityIdForDelete.remove((Integer) eventId);
						event.set_select_mode(SelectModeStatic.notSelected);
					}
					else if (adapter == 2) 
					{
						tuesdaySelectedPositions.remove((Integer) pos);
						WeekEvent event = tuesday_list.get(pos);
						if(event.get_select_mode()!=SelectModeStatic.multiSelected)
						{
							Toast.makeText(ref, "This activity is already selected", Toast.LENGTH_SHORT).show();
							return;
						}
						event.set_select_mode(SelectModeStatic.notSelected);
						activityIdForDelete.remove((Integer) eventId);
					} 
					else if (adapter == 3) 
					{
						wednesdaySelectedPositions.remove((Integer) pos);
						WeekEvent event = wednesday_list.get(pos);
						if(event.get_select_mode()!=SelectModeStatic.multiSelected)
						{
							Toast.makeText(ref, "This activity is already selected", Toast.LENGTH_SHORT).show();
							return;
						}
						activityIdForDelete.remove((Integer) eventId);
						event.set_select_mode(SelectModeStatic.notSelected);
					} 
					else if (adapter == 4) 
					{
						thrusdaySelectedPositions.remove((Integer) pos);
						WeekEvent event = thursday_list.get(pos);
						if(event.get_select_mode()!=SelectModeStatic.multiSelected)
						{
							Toast.makeText(ref, "This activity is already selected", Toast.LENGTH_SHORT).show();
							return;
						}
						activityIdForDelete.remove((Integer) eventId);
						event.set_select_mode(SelectModeStatic.notSelected);
					} 
					else if (adapter == 5) 
					{
						fridaySelectedPositions.remove((Integer) pos);
						WeekEvent event = friday_list.get(pos);
						if(event.get_select_mode()!=SelectModeStatic.multiSelected)
						{
							Toast.makeText(ref, "This activity is already selected", Toast.LENGTH_SHORT).show();
							return;
						}
						activityIdForDelete.remove((Integer) eventId);
						event.set_select_mode(SelectModeStatic.notSelected);
					} 
					else if (adapter == 6) 
					{
						saturdaySelectedPositions.remove((Integer) pos);
						WeekEvent event = saturday_list.get(pos);
						if(event.get_select_mode()!=SelectModeStatic.multiSelected)
						{
							Toast.makeText(ref, "This activity is already selected", Toast.LENGTH_SHORT).show();
							return;
						}
						activityIdForDelete.remove((Integer) eventId);
						event.set_select_mode(SelectModeStatic.notSelected);
					} 
					else if (adapter == 7) 
					{
						tuesdaySelectedPositions.remove((Integer) pos);
						WeekEvent event = sunday_list.get(pos);
						if(event.get_select_mode()!=SelectModeStatic.multiSelected)
						{
							Toast.makeText(ref, "This activity is already selected", Toast.LENGTH_SHORT).show();
							return;
						}
						activityIdForDelete.remove((Integer) eventId);
						event.set_select_mode(SelectModeStatic.notSelected);
					} 
				}
				ibtnDelete.setVisibility(View.VISIBLE);
			}
		}
		if (currentView.equals(CalendarViews.weekView)) 
		{
			notifyDatasetWeekview();
		}

	}

	private void resetAllValues()
	{
		activityIdForDelete.clear();
		
		morningSelectedPositions.clear();
		afternoonSelectedPositions.clear();
		eveningSelectedPositions.clear();
		
		mondaySelectedPositions.clear();
		tuesdaySelectedPositions.clear();
		wednesdaySelectedPositions.clear();
		thrusdaySelectedPositions.clear();
		fridaySelectedPositions.clear();
		saturdaySelectedPositions.clear();
		sundaySelectedPositions.clear();
	
		ibtnDelete.setVisibility(View.INVISIBLE);
		
		multiSelectionMode = 0;
		touchCount = 0;
		previousSelectedPosition = -1;
		previousSelectedAdapter = -1;
	}

	/** Show the current state of View Changer **/
	private void changeViewChangerIcon()
	{
		switch(currentView){
		case CalendarViews.dayView:
			ibDay.setBackgroundResource(R.drawable.ic_navigation_view_selected);
			ibWeek.setBackgroundResource(R.drawable.ic_week_view_selector);
			ibMonth.setBackgroundResource(R.drawable.ic_month_view_selector);
			ibPrize.setBackgroundResource(R.drawable.ic_navigation_token);
			break;
		case CalendarViews.weekView:
			ibDay.setBackgroundResource(R.drawable.ic_day_view_selector);
			ibWeek.setBackgroundResource(R.drawable.ic_navigation_view_selected);
			ibMonth.setBackgroundResource(R.drawable.ic_month_view_selector);
			ibPrize.setBackgroundResource(R.drawable.ic_navigation_token);
			break;
		case CalendarViews.monthView:
			ibDay.setBackgroundResource(R.drawable.ic_day_view_selector);
			ibWeek.setBackgroundResource(R.drawable.ic_week_view_selector);
			ibMonth.setBackgroundResource(R.drawable.ic_navigation_view_selected);
			ibPrize.setBackgroundResource(R.drawable.ic_navigation_token);
			break;
		case CalendarViews.prizeView:
			ibDay.setBackgroundResource(R.drawable.ic_day_view_selector);
			ibWeek.setBackgroundResource(R.drawable.ic_week_view_selector);
			ibMonth.setBackgroundResource(R.drawable.ic_month_view_selector);
			ibPrize.setBackgroundResource(R.drawable.ic_navigation_token_selected);
			break;
		}
		
	}
}
