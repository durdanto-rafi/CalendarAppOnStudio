package com.uniqgroup.application;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.uniqgroup.customdialog.PasswordDialog;
import com.uniqgroup.customdialog.PasswordDialog.SwitchONEditMode;
import com.uniqgroup.customui.Animanation;
import com.uniqgroup.database.DatabaseHandler;
import com.uniqgroup.pojo.Event;
import com.uniqgroup.pojo.EventComplete;
import com.uniqgroup.pojo.EventSequence;
import com.uniqgroup.pojo.User;
import com.uniqgroup.utility.CalenderDateList;
import com.uniqgroup.utility.Custom_Touch_Event;
import com.uniqgroup.utility.Custom_Touch_Event.OnFling_right_left;
import com.uniqgroup.utility.Custom_cell_Day_work;
import com.uniqgroup.utility.Custom_cell_daywork2;
import com.uniqgroup.utility.EditModeSetting;
import com.uniqgroup.utility.ImageProcessing;
import com.uniqgroup.utility.PasswordStorage;

public class EventSeqActivity extends Activity implements
		TextToSpeech.OnInitListener, OnFling_right_left, SwitchONEditMode {

	final String VIDEO_COMPLETE_BR = "video_finish";
	final String TIMER_COMPLETE_BR = "timer_finish";
	final String VIDEO_PATH_EXTRA = "video_path";

	final String KILL_ACTIVITY_BR = "kill_actvity";

	DatabaseHandler db;
	Context ref;
	String current_user_id, returnView;
	int current_event_id, current_frag_id;
	LinearLayout holder_linearLayout, linear_cell1, linear_cell2, linear_cell3,
			llBorder;
	private TextToSpeech tts;
	HashMap<Integer, Custom_cell_Day_work> all_cells_map = new HashMap<>();
	User current_user;
	boolean blocked_UI_flag = false, firsttime_cell_set = false;
	int workPosition = 1;
	EventSequence middle_cell, first_cell, last_cell;
	ImageButton ibtnBack, happyFace_IV, ibtnClearEditMode;
	int total_cells = 0; // the total number of cells
	int current_cell_number = 0; // holds the current cell number from hashmap
	boolean isOnly_one_cell = false;

	int middle_index = 0;
	List<EventSequence> event_sequnce_list;
	ImageProcessing image_processor;
	Animation left_out, left_out2, left_out3, left_in;

	boolean islast_cell_shouldbe_invisible = false;

	boolean actionFinish_showHappyface = false;
	Animation zoomIn, zoom_out;
	LinearLayout gesturelistener_ln;
	Custom_Touch_Event listener_customTouch;
	int isEditMode = 0;
	EventComplete eventComplete;
	Event event;

	UtteranceProgressListener tts_listener = new UtteranceProgressListener() {

		@Override
		public void onStart(String utteranceId) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onError(String utteranceId) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onDone(String utteranceId) {

		}
	};

	private void onTTSEnd() {
		if (middle_cell != null) {
			System.out.println("asdsad: " + middle_cell.getSeqTimer());

			if (middle_cell.getSeqPath().length() > 0) {
				Intent video_intent = new Intent(EventSeqActivity.this,
						VideoPlayer_Activity.class);
				video_intent.putExtra(VIDEO_PATH_EXTRA,
						middle_cell.getSeqPath());
				startActivity(video_intent);
			} else if (middle_cell.getSeqTimer() != 0) {
				Intent timer_intent = new Intent(EventSeqActivity.this,
						TimerActivity.class);
				/** this shall be changed error **/
				timer_intent.putExtra("img_path", middle_cell.getSeqImg());
				timer_intent.putExtra("timer", middle_cell.getSeqTimer());
				startActivity(timer_intent);
			}

			else if (middle_cell.getSeqPath().length() == 0
					&& middle_cell.getSeqTimer() == 0) {
				/** the feedback dialog shall be shown here **/
				runOnUiThread(new Runnable() {
					public void run() {
						happyFace_clickReady();
					}
				});
			}
		}
	}

	OnClickListener cell_click_listener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			/** do the action only if it is the current item **/
			if (isEditMode == 1) {
				Intent intent = null;
				intent = new Intent(EventSeqActivity.this,
						EventManageActivity.class);
				intent.putExtra("event_id", current_event_id);
				intent.putExtra("user_id", current_user_id);
				intent.putExtra("returnPath",
						"com.uniqgroup.application.EventSeqActivity");
				intent.putExtra("flag", "Update");
				intent.putExtra("returnView", returnView);
				startActivity(intent);
			} else {
				if (!blocked_UI_flag)
					if (tts != null && middle_cell != null) {
						if (middle_cell.getTts() != null) {
							// Toast.makeText(getApplicationContext(),
							// middle_cell.getTts(),Toast.LENGTH_LONG).show();
							speakOut(middle_cell.getTts());
							onTTSEnd();

						} else {
							happyFace_clickReady();
						}
					}
			}

		}

	};

	OnClickListener ibtnClickListner = new OnClickListener() {
		@Override
		public void onClick(View v) {
			/*
			 * Intent eventList = new Intent(EventSeqActivity.this,
			 * UserEventListActivity.class); eventList.putExtra("user_id",
			 * current_user_id); eventList.putExtra("returnView", returnView);
			 * 
			 * startActivity(eventList);
			 */
			finishActivity();
		}
	};

	OnCompletionListener completeListener = new OnCompletionListener() {

		@Override
		public void onCompletion(MediaPlayer mp) {
			// TODO Auto-generated method stub
			mp.release();
		}
	};

	public void playMusic(int play) {
		switch (play) {
		case 0:
			MediaPlayer mp = MediaPlayer.create(this, R.raw.sd_green_face);
			mp.setOnCompletionListener(completeListener);
			mp.start();
			break;
		case 1:
			MediaPlayer mp2 = MediaPlayer.create(this,
					R.raw.sd_open_dialog_faces);
			mp2.setOnCompletionListener(completeListener);
			mp2.start();
			break;
		case 2:
			MediaPlayer mp3 = MediaPlayer.create(this,
					R.raw.sd_sliding_action_after_green);
			mp3.setOnCompletionListener(completeListener);
			mp3.start();
			break;
		case 3:
			MediaPlayer mp4 = MediaPlayer.create(this, R.raw.sd_token_end);
			mp4.setOnCompletionListener(completeListener);
			mp4.start();
			break;
		case 4:
			MediaPlayer mp5 = MediaPlayer
					.create(this, R.raw.sd_white_face_zoom);
			mp5.setOnCompletionListener(completeListener);
			mp5.start();
			break;

		default:
			break;
		}
	}

	/** LISTENERS END HERE******************************* **/

	/**
	 * AFTER THE ACTION SHOWS EVERYTHNG AND IT IS DONE WE CALL THIS FUNC TO GET
	 * FEEDBACK AND START ANIMATION
	 **/
	public void happyFace_clickReady() {
		Log.e("error", "in happy");
		actionFinish_showHappyface = true;
		happyFace_IV.startAnimation(zoomIn);
		blocked_UI_flag = true;
		playMusic(4);
		FullScreencall();
		/** anim ready **/
	}

	public void happyFace_click() {
		if (actionFinish_showHappyface) {
			playMusic(1);
			show_FeedBack_AlertDialog();
			Animanation.clear(happyFace_IV);

			FullScreencall();
		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_sequnce);

		llBorder = (LinearLayout) findViewById(R.id.llBorder);
		gesturelistener_ln = (LinearLayout) findViewById(R.id.firsthalf_linear);
		gesturelistener_ln.setOnTouchListener(fling_listener);
		listener_customTouch = new Custom_Touch_Event(this);
		// holder_linearLayout = (LinearLayout)
		// findViewById(R.id.holder_eventAction_linear);
		linear_cell1 = (LinearLayout) findViewById(R.id.linear_1st_cell);
		linear_cell2 = (LinearLayout) findViewById(R.id.linear_2nd_cell);
		linear_cell3 = (LinearLayout) findViewById(R.id.linear_3rd_cell);
		ibtnBack = (ImageButton) findViewById(R.id.ibtnBack);
		ibtnClearEditMode = (ImageButton) findViewById(R.id.ibtnClearEditMode);

		zoomIn = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.zoom_in);
		zoom_out = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.zoom_out);

		zoomIn.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				happyFace_IV.startAnimation(zoom_out);
			}
		});

		zoom_out.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				// checktaskComplete();
				Animanation.shakeAnimation(happyFace_IV);
			}
		});

		happyFace_IV = (ImageButton) findViewById(R.id.happyFace_IV);
		Animanation.slideUP_to_Down(happyFace_IV);
		happyFace_IV.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				happyFace_click();
				FullScreencall();
			}
		});

		left_out = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.slide_left_out);
		left_out.setAnimationListener(lsitener1);
		/*
		 * rotation = AnimationUtils.loadAnimation(getApplicationContext(),
		 * R.anim.zoom_in);
		 */

		left_out2 = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.slide_left_out);
		left_out2.setAnimationListener(lsitener2);
		left_out3 = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.slide_left_out);
		left_out3.setAnimationListener(lsitener3);
		left_in = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.slide_left_in);
		left_in.setAnimationListener(lsitener4);

		image_processor = new ImageProcessing(EventSeqActivity.this);

		linear_cell2.setOnClickListener(cell_click_listener);

		ref = this;
		db = new DatabaseHandler(this);

		Intent intent = getIntent();
		current_user_id = intent.getStringExtra("user_id");
		current_event_id = intent.getIntExtra("event_id", -1);
		returnView = intent.getStringExtra("returnView");

		tts = new TextToSpeech(this, this);

		this.registerReceiver(video_finish_receiver, new IntentFilter(
				VIDEO_COMPLETE_BR));
		this.registerReceiver(timer_finish_receiver, new IntentFilter(
				TIMER_COMPLETE_BR));
		

this.registerReceiver(kill_activity_Receiver, new IntentFilter(
				KILL_ACTIVITY_BR));

		// displayImages();
		FullScreencall();
		setEditMode();

		ibtnBack.setOnClickListener(ibtnClickListner);

		ibtnClearEditMode.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				flingRL_happened(false);
			}
		});

		// flingRL_happened(false);
		new LoadCesll_async().execute();
		visibilitySetting();
		current_user = db.getUser(Integer.parseInt(current_user_id));
	}

	/***************************** CELL CONTROL METHODS **************************/
	/** sets the current cell which controls everything **/
	public void setCurrentCell(EventSequence setCell) {
		middle_cell = setCell;
	}

	public void moveCurrentCell() {
		if (current_cell_number + 1 < total_cells) {
			current_cell_number++;
			middle_cell = all_cells_map.get(current_cell_number)
					.getThe_datamodel_fromView();
			holder_linearLayout.setScrollX(current_cell_number);
		} else {
			/** finish all the sequence **/
		}
	}

	/** SETTING UP CELLS ******************/

	public Bitmap returnBitmap(String path) {

		return image_processor.getImage(path);
	}

	public int find_the_middleCell_index(List<EventSequence> event_sequnce_list) {
		int currentCell = -1;
		for (int i = 0; i < event_sequnce_list.size(); i++) {
			EventSequence sequece = event_sequnce_list.get(i);
			if (sequece.getStatus().equals("0")
					|| sequece.getStatus().equals("2")
					|| sequece.getStatus() == null) {
				currentCell = i;
				break;
			}
		}

		return currentCell;
	}

	public void displayImages() {
		event_sequnce_list = db.getAllEvtSeqList(current_event_id);
		int middleCell_index = -1;
		if (event_sequnce_list != null) {
			middleCell_index = find_the_middleCell_index(event_sequnce_list);
			Log.e("Error:", "size of event:" + event_sequnce_list.size()
					+ ", middle index:" + middle_index);
			middle_index = middleCell_index;
		}
		if (middleCell_index != -1) {
			/** if it is the last element **********************/
			if (middleCell_index == (event_sequnce_list.size() - 1)
					&& event_sequnce_list.size() != 1) {
				middle_cell = event_sequnce_list.get(middleCell_index);
				Custom_cell_Day_work middle_one = new Custom_cell_Day_work(
						this, returnBitmap(middle_cell.getSeqImg()),
						middle_cell, middle_cell.getTts());
				linear_cell2.addView(middle_one);

				// there can be only one cell (SPECIAL CASE)

				first_cell = event_sequnce_list.get(middleCell_index - 1);
				Custom_cell_daywork2 firstCell = new Custom_cell_daywork2(this,
						returnBitmap(first_cell.getSeqImg()), first_cell,
						first_cell.getTts());

			}
			/** if it is the first and only element **************************/
			else if (middleCell_index == 0 && event_sequnce_list.size() == 1) {
				middle_cell = event_sequnce_list.get(middleCell_index);
				Custom_cell_Day_work middle_one = new Custom_cell_Day_work(
						this, returnBitmap(middle_cell.getSeqImg()),
						middle_cell, middle_cell.getTts());
				linear_cell2.addView(middle_one);
				linear_cell1.setVisibility(View.INVISIBLE);
				linear_cell3.setVisibility(View.INVISIBLE);
				isOnly_one_cell = true;
			}
			/**
			 * if first cell is empty then this happens when u enter for the
			 * first time
			 ****************/
			else if (middleCell_index == 0 && event_sequnce_list.size() != 1) {
				middle_cell = event_sequnce_list.get(middleCell_index);
				Custom_cell_Day_work middle_one = new Custom_cell_Day_work(
						this, returnBitmap(middle_cell.getSeqImg()),
						middle_cell, middle_cell.getTts());
				linear_cell2.addView(middle_one);
				linear_cell1.setVisibility(View.INVISIBLE);

				last_cell = event_sequnce_list.get(middleCell_index + 1);
				Custom_cell_daywork2 lastCell = new Custom_cell_daywork2(this,
						returnBitmap(last_cell.getSeqImg()), last_cell,
						last_cell.getTts());
				linear_cell3.addView(lastCell);

			}

			/** for all the other cases ***************************************/
			else if (middleCell_index > 0
					&& middleCell_index < event_sequnce_list.size()) {
				middle_cell = event_sequnce_list.get(middleCell_index);
				Custom_cell_Day_work middle_one = new Custom_cell_Day_work(
						this, returnBitmap(middle_cell.getSeqImg()),
						middle_cell, middle_cell.getTts());
				linear_cell2.addView(middle_one);

				// there can be only one cell (SPECIAL CASE)
				first_cell = event_sequnce_list.get(middleCell_index - 1);
				Custom_cell_daywork2 firstCell = new Custom_cell_daywork2(this,
						returnBitmap(first_cell.getSeqImg()), first_cell,
						first_cell.getTts());
				linear_cell1.addView(firstCell);

				last_cell = event_sequnce_list.get(middleCell_index + 1);
				Custom_cell_daywork2 lastCell = new Custom_cell_daywork2(this,
						returnBitmap(last_cell.getSeqImg()), last_cell,
						last_cell.getTts());
				linear_cell3.addView(lastCell);

				linear_cell1.setVisibility(View.VISIBLE);
				linear_cell3.setVisibility(View.VISIBLE);
			}

		}

	}

	@Override
	public void onBackPressed() {

		Log.d("UserId", String.valueOf(current_user_id));

		/*
		 * Intent eventList = new Intent(this, UserEventListActivity.class);
		 * eventList.putExtra("user_id", String.valueOf(current_user_id)); //
		 * Optional // parameters startActivity(eventList);
		 */
		finishActivity();
	}

	/** SETTING UP Animation timer WORK3 ******************/
	
	private BroadcastReceiver kill_activity_Receiver = new BroadcastReceiver() {
		  @Override
		  public void onReceive(Context context, Intent intent) {
		  finishActivity();
		  }
		 };
	private BroadcastReceiver timer_finish_receiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			int message = intent.getIntExtra("finish", 0);
			System.out.println("timer");
			if (message == 0) {
				/**
				 * nothing will happen as the timer was killed, user shall try
				 * again
				 **/
			} else {
				/** the feedback dialog shall be shown here **/
				new Handler().postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						happyFace_clickReady();
					}
				}, 2000);

			}
		}
	};

	/** SETTING UP Video WORK2 ******************/
	private BroadcastReceiver video_finish_receiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			// String message = intent.getStringExtra("message");
			onVideoPlaying_finished();
		}
	};

	public void onVideoPlaying_finished() {
		if (middle_cell != null) {
			if (middle_cell.getSeqTimer() != 0) {
				Intent timer_intent = new Intent(EventSeqActivity.this,
						TimerActivity.class);
				timer_intent.putExtra("img_path", middle_cell.getSeqImg());
				timer_intent.putExtra("timer", middle_cell.getSeqTimer());
				startActivity(timer_intent);
			}

			else {
				/** the feedback dialog shall be shown here **/
				happyFace_clickReady();
			}
		}
	}

	/** TTS Related work WORK1 ************************************/

	private void speakOut(String speak) {
		System.out.println("speak out called:" + speak);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID,
				String.valueOf(middle_cell.getID()));
		if (tts != null)
			tts.speak(speak, TextToSpeech.QUEUE_FLUSH, map);
	}

	@Override
	public void onDestroy() {
		// Don't forget to shutdown tts!
		if (tts != null) {
			tts.stop();
			tts.shutdown();
		}
		LocalBroadcastManager.getInstance(this).unregisterReceiver(
				video_finish_receiver);
		LocalBroadcastManager.getInstance(this).unregisterReceiver(
				timer_finish_receiver);
		super.onDestroy();
	}

	@Override
	public void onInit(int status) {

		if (status == TextToSpeech.SUCCESS) {

			int result = tts.setLanguage(Locale.US);
			tts.setOnUtteranceProgressListener(tts_listener);
			if (result == TextToSpeech.LANG_MISSING_DATA
					|| result == TextToSpeech.LANG_NOT_SUPPORTED) {
				// Log.e("TTS", "This Language is not supported");
			} else {

			}

		} else {
			Log.e("TTS", "Initilization Failed!");
		}

	}

	/** THE FINAL CALL AFTER ANIMATION **/
	public void transition_finished() {
		blocked_UI_flag = false;
		// happyFace_IV.setVisibility(View.INVISIBLE);

		/*
		 * Animanation.slideDown_to_Down(happyFace_IV,
		 * R.drawable.img_white_face_pressed);
		 */
		// happyFace_IV.setImageResource(R.drawable.ic_launcher);
	}

	/** ********************** animation control ********************************************/
	AnimationListener lsitener1 = new AnimationListener() {

		@Override
		public void onAnimationStart(Animation arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onAnimationRepeat(Animation arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onAnimationEnd(Animation arg0) {

			linear_cell1.setVisibility(View.INVISIBLE);
			linear_cell2.startAnimation(left_out2);
			Animanation.slideDown_to_Down(happyFace_IV,
					R.drawable.img_white_face_pressed);
			playMusic(2);

		}
	};
	AnimationListener lsitener2 = new AnimationListener() {

		@Override
		public void onAnimationStart(Animation arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onAnimationRepeat(Animation arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onAnimationEnd(Animation arg0) {
			/** HERE UPDATE THE FIRST CELL AND MOVE ON **/
			linear_cell1.removeAllViews();
			Custom_cell_daywork2 first_cell_view = new Custom_cell_daywork2(
					EventSeqActivity.this,
					returnBitmap(first_cell.getSeqImg()), first_cell,
					first_cell.getTts());
			linear_cell1.addView(first_cell_view);

			linear_cell1.setVisibility(View.VISIBLE);
			linear_cell2.setVisibility(View.INVISIBLE);
			linear_cell3.startAnimation(left_out3);
			playMusic(2);

		}

	};
	AnimationListener lsitener3 = new AnimationListener() {

		@Override
		public void onAnimationStart(Animation arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onAnimationRepeat(Animation arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onAnimationEnd(Animation arg0) {
			linear_cell2.removeAllViews();
			Custom_cell_Day_work middle_cell_view = new Custom_cell_Day_work(
					EventSeqActivity.this,
					returnBitmap(middle_cell.getSeqImg()), middle_cell,
					middle_cell.getTts());
			linear_cell2.addView(middle_cell_view);

			linear_cell2.setVisibility(View.VISIBLE);
			linear_cell3.setVisibility(View.INVISIBLE);

			if (!islast_cell_shouldbe_invisible) {
				linear_cell3.removeAllViews();
				Custom_cell_daywork2 last_cell_view = new Custom_cell_daywork2(
						EventSeqActivity.this,
						returnBitmap(last_cell.getSeqImg()), last_cell,
						last_cell.getTts());
				linear_cell3.addView(last_cell_view);

				linear_cell3.startAnimation(left_in);
				playMusic(2);
			} else {
				linear_cell3.removeAllViews();
				// linear_cell3.setVisibility(View.INVISIBLE);
				transition_finished();
			}

		}

	};
	AnimationListener lsitener4 = new AnimationListener() {

		@Override
		public void onAnimationStart(Animation arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onAnimationRepeat(Animation arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onAnimationEnd(Animation arg0) {
			if (!islast_cell_shouldbe_invisible) {
				linear_cell3.setVisibility(View.VISIBLE);
			} else {
				linear_cell3.removeAllViews();
				linear_cell3.setVisibility(View.INVISIBLE);
			}
			// ln2.startAnimation(rotation);
			transition_finished();

		}

	};

	/** Shows the dialog to get feedback **************************/
	public void show_FeedBack_AlertDialog() {

		final Dialog dialog = new Dialog(this, R.style.CustomAlertDialog);
		// dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.feedback_eventdequence_dialog);

		dialog.getWindow().setTitleColor(getResources().getColor(R.color.blue));

		final ImageView happyFace = (ImageView) dialog
				.findViewById(R.id.happyFace_feedback);
		/*
		 * final ImageView action = (ImageView) dialog
		 * .findViewById(R.id.actionImage_feedback);
		 */

		final ImageView sadFace = (ImageView) dialog
				.findViewById(R.id.sad_feedback);
		// Bitmap action_bitmap = returnBitmap(middle_cell.getSeqImg());
		/*
		 * if (action_bitmap != null) action.setImageBitmap(action_bitmap);
		 */

		// final int event_id = DataList.get(position).get_event_id();

		happyFace.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				middle_cell.setStatus("1");
				db.updateEventSeqStatus(middle_cell);

				Custom_cell_Day_work view = (Custom_cell_Day_work) linear_cell2
						.getChildAt(0);
				view.setState(1);

				updateSequence();

				actionFinish_showHappyface = false;
				happyFace_IV.setImageResource(R.drawable.img_green_happy_face);
				// Toast.makeText(ref, "aaaaa", Toast.LENGTH_SHORT).show();
				/** rafy put any animations if needed **/

				// db.updateEventByStatus(current_event_id, "1");
				playMusic(0);
				dialog.dismiss();
			}
		});
		sadFace.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				middle_cell.setStatus("2");
				db.updateEventSeqStatus(middle_cell);
				Custom_cell_Day_work view = (Custom_cell_Day_work) linear_cell2
						.getChildAt(0);
				view.setState(2);
				// updateSequence();
				actionFinish_showHappyface = false;
				happyFace_IV.setImageResource(R.drawable.img_red_sad_face);

				blocked_UI_flag = false;
				FullScreencall();
				dialog.dismiss();
			}
		});

		dialog.show();
	}

	public void cellChanged_startTransaction() {
		blocked_UI_flag = true;
		linear_cell1.startAnimation(left_out); // starts the animation
	}

	/** UPDATE SEQUENCE AND START THE ANIMATION ***********************************/
	public void updateSequence() {
		if (event_sequnce_list != null) {

			Log.e("Error:", "size of event:" + event_sequnce_list.size()
					+ ", middle index:" + middle_index);
			/** things available in the right *********/
			if (event_sequnce_list.size() <= (middle_index + 1)) {
				// job done
				db.updateEventByStatus(current_event_id, "1");
				eventComplete = new EventComplete();
				eventComplete.set_week(CalenderDateList.getCurrentWeekNumber());
				eventComplete.set_day(CalenderDateList.getCurrentWeekDay());
				eventComplete.set_event_id(current_event_id);
				db.addUpdateEventComplete(eventComplete);
				/*Toast.makeText(
						getApplicationContext(),
						String.valueOf(db.addUpdateEventComplete(eventComplete)),
						Toast.LENGTH_SHORT).show();*/

				event = new Event();
				event = db.getEvent(current_event_id);

				startActivity(new Intent(this,Transparent_feedback_activity.class).putExtra("prize", event.get_daily_prize()));
				/*Feedback_alertBuilder.makerDialog(EventSeqActivity.this,
						EventSeqActivity.this, event.get_daily_prize());*/
			} else if (event_sequnce_list.size() - 1 == (middle_index + 1)) {
				islast_cell_shouldbe_invisible = true;

				first_cell = middle_cell;
				middle_cell = last_cell;
				middle_index = middle_index + 1;
				cellChanged_startTransaction();
			} else if (event_sequnce_list.size() - 1 > (middle_index + 1)) {
				first_cell = middle_cell;
				middle_cell = last_cell;
				middle_index = middle_index + 1;
				last_cell = event_sequnce_list.get(middle_index + 1);
				// starting transition we have got things in the right place
				cellChanged_startTransaction();
			}
			FullScreencall();
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

	OnTouchListener fling_listener = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if (listener_customTouch != null)
				listener_customTouch.onTouch(v, event);
			return true;
		}
	};

	@Override
	public void switchEdit_ON(boolean flag) {
		if (flag == true) {
			EditModeSetting.setEditMode(1);
		} else {
			EditModeSetting.setEditMode(0);
		}
		visibilitySetting();

	}

	@Override
	public void flingRL_happened(boolean editOn) {
		int curr_pass_index = PasswordStorage
				.getCurrentPasswordIndex(current_user.getPassword());
		if (editOn) {
			if (curr_pass_index == 0) {
				switchEdit_ON(true);
			} else {
				switchEdit_ON(false);
				new PasswordDialog(this, curr_pass_index, this).show();
			}
		} else {
			switchEdit_ON(false);
		}

	}

	public void visibilitySetting() {
		isEditMode = EditModeSetting.getEditMode();
		if (isEditMode()) {
			ibtnBack.setVisibility(View.VISIBLE);
			ibtnClearEditMode.setVisibility(View.VISIBLE);
			llBorder.setBackgroundResource(R.drawable.bg_transparente_edition);
		} else {
			ibtnBack.setVisibility(View.INVISIBLE);
			ibtnClearEditMode.setVisibility(View.INVISIBLE);
			llBorder.setBackground(null);
		}

	}

	public Boolean isEditMode() {

		return isEditMode == 1;
	}

	public void setEditMode() {

		if (EditModeSetting.getEditMode() == 1) {
			isEditMode = 1;
		} else {
			isEditMode = 0;
		}
	}

	public void finishActivity() {
		// Don't forget to shutdown tts!
		if (tts != null) {
			tts.stop();
			tts.shutdown();
		}
		LocalBroadcastManager.getInstance(this).unregisterReceiver(
				video_finish_receiver);
		LocalBroadcastManager.getInstance(this).unregisterReceiver(
				timer_finish_receiver);

		Intent eventList = new Intent(this, UserEventListActivity.class);
		eventList.putExtra("user_id", String.valueOf(current_user_id));
		eventList.putExtra("returnView", returnView);
		startActivity(eventList);

		finish();
	}

	class LoadCesll_async extends AsyncTask<Void, Void, Void> {
		ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pDialog = new ProgressDialog(EventSeqActivity.this);
			pDialog.setMessage("Loading please wait...");
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			runOnUiThread(new Runnable() {
				public void run() {
					displayImages();
				}
			});

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			pDialog.dismiss();
		}

	}

}