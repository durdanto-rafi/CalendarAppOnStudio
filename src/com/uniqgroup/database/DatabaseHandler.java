package com.uniqgroup.database;

/**
 * Created by hp 240 on 11/8/2015.
 */
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.uniqgroup.pojo.AfterNoonEvent;
import com.uniqgroup.pojo.AlarmEvent;
import com.uniqgroup.pojo.EveningEvent;
import com.uniqgroup.pojo.Event;
import com.uniqgroup.pojo.EventAlarm;
import com.uniqgroup.pojo.EventAudVid;
import com.uniqgroup.pojo.EventComplete;
import com.uniqgroup.pojo.EventOccur;
import com.uniqgroup.pojo.EventSequence;
import com.uniqgroup.pojo.MonthviewImage;
import com.uniqgroup.pojo.MorningEvent;
import com.uniqgroup.pojo.PasswordHash;
import com.uniqgroup.pojo.Prize;
import com.uniqgroup.pojo.User;
import com.uniqgroup.pojo.WeekEvent;
import com.uniqgroup.utility.CalenderDateList;

public class DatabaseHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "CalenderManager";

	// Users table name
	private static final String TABLE_USERS = "Users";

	// EVENT table name
	private static final String TABLE_EVENT = "Events";

	// Event Occur table name
	private static final String TABLE_EVT_OC = "EventOccur";

	// Event Audio Video table name
	private static final String TABLE_EVT_AD_VD = "EventAudioVideo";

	// Event Audio Video table name
	private static final String TABLE_EVT_SEQ = "EventSequnces";

	// Event Alarm table name
	private static final String TABLE_EVT_ALARM = "EventAlarm";

	// Updated activity image for Monthview
	private static final String TABLE_MONTH_IMAGE = "MonthViewImage";
	
	// Updated activity image for Monthview
	private static final String TABLE_EVT_COMPLETE = "EventComplete";

	// Users Table Columns names
	private static final String KEY_USER_ID = "id";
	private static final String KEY_USER_NAME = "name";
	private static final String KEY_USER_PASS = "password";
	private static final String KEY_USER_PIC = "profilepic";
	private static final String KEY_USER_TYPE = "type";
	private static final String KEY_USER_DEFAULT_VIEW = "default_view";
	private static final String KEY_USER_ALARM = "alarm";

	// Event Table Column Names
	private static final String KEY_EVT_ID = "id";
	private static final String KEY_EVT_LOGO = "logo";
	private static final String KEY_EVT_DESC = "description";
	private static final String KEY_EVT_STAT = "status";
	private static final String KEY_EVT_USER_ID = "user_id";
	private static final String KEY_EVT_ALARM = "alarm";
	private static final String KEY_EVT_DAILY_PRIZE = "daily_prize";
	private static final String KEY_EVT_WEEKLY_PRIZE = "weekly_prize";

	// EventOccur Table Column Names
	private static final String KEY_EVT_OC_ID = "id";
	private static final String KEY_EVT_OC_DATE = "date";
	private static final String KEY_EVT_OC_TIME = "time";
	private static final String KEY_EVT_OC_EVT_STAT = "status";
	private static final String KEY_EVT_OC_EVT_ID = "event_id";

	// Event Audio Video Table Column Names
	private static final String KEY_EVT_AVD_ID = "id";
	private static final String KEY_EVT_AVD_PATH = "av_path";
	private static final String KEY_EVT_AVD_TYPE = "type";
	private static final String KEY_EVT_AVD_EVT_ID = "event_id";

	private static final String KEY_EVT_SEQ_ID = "id";
	private static final String KEY_EVT_SEQ_IMG = "seq_image";
	private static final String KEY_EVT_SEQ_PATH = "seq_path";
	private static final String KEY_EVT_SEQ_TYPE = "seq_type";
	private static final String KEY_EVT_SEQ_TTS = "seq_tts";
	private static final String KEY_EVT_SEQ_TIMER = "seq_timer";
	private static final String KEY_EVT_SEQ_STAT = "status";
	private static final String KEY_EVT_SEQ_EVT_ID = "event_id";
	private static final String KEY_EVT_SEQ_POSOTION = "event_position";

	private static final String KEY_EVT_ALR_ID = "id";
	private static final String KEY_EVT_ALR_EVENT_ID = "event_id";
	private static final String KEY_EVT_TIME = "alarm_time";
	private static final String KEY_EVT_SAT = "alarm_sat";
	private static final String KEY_EVT_SUN = "alarm_sun";
	private static final String KEY_EVT_MON = "alarm_mon";
	private static final String KEY_EVT_TUE = "alarm_tue";
	private static final String KEY_EVT_WED = "alarm_wed";
	private static final String KEY_EVT_THU = "alarm_thu";
	private static final String KEY_EVT_FRI = "alarm_fri";
	private static final String KEY_EVT_WEEK_NUM = "alarm_week_num";
	private static final String KEY_EVT_WEEK_REPEAT = "alarm_week_repeat";

	// Event Table Column Names
	private static final String KEY_MONTHVIEW_ID = "id";
	private static final String KEY_MONTHVIEW_WEEK = "week";
	private static final String KEY_MONTHVIEW_DAY = "day";
	private static final String KEY_MONTHVIEW_IMAGEPATH = "image";
	private static final String KEY_MONTHVIEW_USER_ID = "user_id";
	
	// Event Complete info Table Column Names
	private static final String KEY_EVT_COMP_ID = "id";
	private static final String KEY_EVT_COMP_EVT_ID = "event_id";
	private static final String KEY_EVT_COMP_DAY = "day";
	private static final String KEY_EVT_COMP_WEEK = "week";

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
				+ KEY_USER_ID + " INTEGER PRIMARY KEY," + KEY_USER_NAME
				+ " TEXT," + KEY_USER_PASS + " TEXT," + KEY_USER_PIC + " TEXT,"
				+ KEY_USER_TYPE + " TEXT," + KEY_USER_DEFAULT_VIEW + " TEXT,"
				+ KEY_USER_ALARM + " TEXT"
				+ ")";
		db.execSQL(CREATE_USERS_TABLE);

		String CREATE_EVENTS_TABLE = "CREATE TABLE " + TABLE_EVENT + "("
				+ KEY_EVT_ID + " INTEGER PRIMARY KEY," + KEY_EVT_LOGO
				+ " TEXT," + KEY_EVT_DESC + " TEXT," + KEY_EVT_STAT + " TEXT,"
				+ KEY_EVT_USER_ID + " TEXT ,"+ KEY_EVT_ALARM + " TEXT ," 
				+ KEY_EVT_DAILY_PRIZE + " TEXT ,"+ KEY_EVT_WEEKLY_PRIZE + " TEXT ," 
				+ " FOREIGN KEY("+ KEY_EVT_USER_ID+ ") REFERENCES Users(id)  ON DELETE CASCADE " + ")";

		db.execSQL(CREATE_EVENTS_TABLE);

		String CREATE_EVENTS_OC_TABLE = "CREATE TABLE " + TABLE_EVT_OC + "("
				+ KEY_EVT_OC_ID + " INTEGER PRIMARY KEY," + KEY_EVT_OC_DATE
				+ " TEXT," + KEY_EVT_OC_TIME + " TEXT," + KEY_EVT_OC_EVT_STAT
				+ " TEXT," + KEY_EVT_OC_EVT_ID + " TEXT ,"

				+ " FOREIGN KEY(" + KEY_EVT_OC_EVT_ID
				+ ") REFERENCES Events(id)  ON DELETE CASCADE " + ")";

		db.execSQL(CREATE_EVENTS_OC_TABLE);

		String CREATE_EVENTS_AVD = "CREATE TABLE " + TABLE_EVT_AD_VD + "("
				+ KEY_EVT_AVD_ID + " INTEGER PRIMARY KEY," + KEY_EVT_AVD_PATH
				+ " TEXT," + KEY_EVT_AVD_TYPE + " TEXT," + KEY_EVT_AVD_EVT_ID
				+ " TEXT ,"

				+ " FOREIGN KEY(" + KEY_EVT_AVD_EVT_ID
				+ ") REFERENCES Events(id)  ON DELETE CASCADE " + ")";

		db.execSQL(CREATE_EVENTS_AVD);

		String CREATE_EVENTS_SEQ = "CREATE TABLE " + TABLE_EVT_SEQ + "("
				+ KEY_EVT_SEQ_ID + " INTEGER PRIMARY KEY," + KEY_EVT_SEQ_IMG
				+ " TEXT," + KEY_EVT_SEQ_PATH + " TEXT," + KEY_EVT_SEQ_TYPE
				+ " TEXT ," + KEY_EVT_SEQ_STAT + " TEXT ," + KEY_EVT_SEQ_TTS
				+ " TEXT ," + KEY_EVT_SEQ_TIMER + " TEXT ,"
				+ KEY_EVT_SEQ_EVT_ID + " TEXT ," + KEY_EVT_SEQ_POSOTION
				+ " TEXT ," + " FOREIGN KEY(" + KEY_EVT_SEQ_EVT_ID
				+ ") REFERENCES Events(id)  ON DELETE CASCADE " + ")";

		db.execSQL(CREATE_EVENTS_SEQ);

		String CREATE_EVT_ALARM = "CREATE TABLE " + TABLE_EVT_ALARM + "("
				+ KEY_EVT_ALR_ID + " INTEGER PRIMARY KEY,"
				+ KEY_EVT_ALR_EVENT_ID + " TEXT," + KEY_EVT_TIME + " TEXT,"
				+ KEY_EVT_SAT + " TEXT ," + KEY_EVT_SUN + " TEXT ,"
				+ KEY_EVT_MON + " TEXT ," + KEY_EVT_TUE + " TEXT ,"
				+ KEY_EVT_WED + " TEXT ," + KEY_EVT_THU + " TEXT ,"
				+ KEY_EVT_FRI + " TEXT ," + KEY_EVT_WEEK_NUM + " TEXT ,"
				+ KEY_EVT_WEEK_REPEAT + " TEXT ," + " FOREIGN KEY("
				+ KEY_EVT_ALR_EVENT_ID
				+ ") REFERENCES Events(id)  ON DELETE CASCADE " + ")";

		db.execSQL(CREATE_EVT_ALARM);

		String CREATE_MONTHVIEW_IMAGE = "CREATE TABLE " + TABLE_MONTH_IMAGE
				+ "(" + KEY_MONTHVIEW_ID + " INTEGER PRIMARY KEY,"
				+ KEY_MONTHVIEW_WEEK + " TEXT," + KEY_MONTHVIEW_DAY + " TEXT,"
				+ KEY_MONTHVIEW_IMAGEPATH + " TEXT ," + KEY_MONTHVIEW_USER_ID
				+ " TEXT ," + " FOREIGN KEY(" + KEY_MONTHVIEW_USER_ID
				+ ") REFERENCES Users(id)  ON DELETE CASCADE " + ")";

		db.execSQL(CREATE_MONTHVIEW_IMAGE);
		
		String CREATE_EVENT_COMPLETE = "CREATE TABLE " + TABLE_EVT_COMPLETE
				+ "(" + KEY_EVT_COMP_ID + " INTEGER PRIMARY KEY,"
				+ KEY_EVT_COMP_EVT_ID + " INTEGER," + KEY_EVT_COMP_DAY + " TEXT,"
				+ KEY_EVT_COMP_WEEK + " TEXT ," 
				+ " FOREIGN KEY(" + KEY_EVT_COMP_EVT_ID + ") REFERENCES Events(id)  ON DELETE CASCADE " + ")";

		db.execSQL(CREATE_EVENT_COMPLETE);

	}

	@Override
	public void onOpen(SQLiteDatabase db) {
		super.onOpen(db);
		if (!db.isReadOnly()) {
			db.execSQL("PRAGMA foreign_keys=ON;");
		}
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENT);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVT_OC);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVT_AD_VD);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVT_SEQ);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVT_ALARM);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_MONTH_IMAGE);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVT_COMPLETE);

		// Create tables again
		onCreate(db);
	}

	public void resetDB() {

		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENT);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVT_OC);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVT_AD_VD);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVT_SEQ);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVT_ALARM);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_MONTH_IMAGE);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVT_COMPLETE);
		// Create tables again
		onCreate(db);
	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */

	// Adding new EVENT AVD
	public int addEventAlarm(EventAlarm Evt) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();

		values.put(KEY_EVT_ALR_EVENT_ID, Evt.get_event_id());
		values.put(KEY_EVT_TIME, Evt.get_alarm_time());
		values.put(KEY_EVT_SAT, Evt.get_alarm_sat());
		values.put(KEY_EVT_SUN, Evt.get_alarm_sun());
		values.put(KEY_EVT_MON, Evt.get_alarm_mon());
		values.put(KEY_EVT_TUE, Evt.get_alarm_tue());
		values.put(KEY_EVT_WED, Evt.get_alarm_wed());
		values.put(KEY_EVT_THU, Evt.get_alarm_thu());
		values.put(KEY_EVT_FRI, Evt.get_alarm_fri());
		values.put(KEY_EVT_WEEK_NUM, Evt.get_alarm_week_num());
		values.put(KEY_EVT_WEEK_REPEAT, Evt.get_alarm_week_repeat());

		// Inserting Row
		int last_id = (int) db.insert(TABLE_EVT_ALARM, null, values);
		db.close(); // Closing database connection

		return last_id;
	}

	// Updating single Event
	public int updateEventAlarm(EventAlarm Evt) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();

		values.put(KEY_EVT_ALR_ID, Evt.get_id());
		values.put(KEY_EVT_ALR_EVENT_ID, Evt.get_event_id());
		values.put(KEY_EVT_TIME, Evt.get_alarm_time());
		values.put(KEY_EVT_SAT, Evt.get_alarm_sat());
		values.put(KEY_EVT_SUN, Evt.get_alarm_sun());
		values.put(KEY_EVT_MON, Evt.get_alarm_mon());
		values.put(KEY_EVT_TUE, Evt.get_alarm_tue());
		values.put(KEY_EVT_WED, Evt.get_alarm_wed());
		values.put(KEY_EVT_THU, Evt.get_alarm_thu());
		values.put(KEY_EVT_FRI, Evt.get_alarm_fri());
		values.put(KEY_EVT_WEEK_NUM, Evt.get_alarm_week_num());
		values.put(KEY_EVT_WEEK_REPEAT, Evt.get_alarm_week_repeat());

		// updating row
		return db.update(TABLE_EVT_ALARM, values, KEY_EVT_ALR_ID + " = ?",
				new String[] { String.valueOf(Evt.get_id()) });
	}

	// Getting single EventOccur
	public EventAlarm getEventAlr(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_EVT_ALARM, new String[] {
				KEY_EVT_ALR_ID, KEY_EVT_ALR_EVENT_ID, KEY_EVT_TIME,
				KEY_EVT_SAT, KEY_EVT_SUN, KEY_EVT_MON, KEY_EVT_TUE,
				KEY_EVT_WED, KEY_EVT_THU, KEY_EVT_FRI, KEY_EVT_WEEK_NUM,
				KEY_EVT_WEEK_REPEAT }, KEY_EVT_ALR_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor.getCount() <= 0)
			return null;

		cursor.moveToFirst();

		EventAlarm Evt = new EventAlarm();

		Evt.set_id(Integer.parseInt(cursor.getString(0)));
		Evt.set_event_id(cursor.getString(1));
		Evt.set_alarm_time(cursor.getString(2));
		Evt.set_alarm_sat(cursor.getString(3));
		Evt.set_alarm_sun(cursor.getString(4));
		Evt.set_alarm_mon(cursor.getString(5));
		Evt.set_alarm_tue(cursor.getString(6));
		Evt.set_alarm_wed(cursor.getString(7));
		Evt.set_alarm_thu(cursor.getString(8));
		Evt.set_alarm_fri(cursor.getString(9));
		Evt.set_alarm_week_num(cursor.getString(10));
		Evt.set_alarm_week_repeat(cursor.getString(11));

		// return User
		return Evt;
	}

	// Getting Event's data by ID, Week Num,
	public List<EventAlarm> getAllEvtAlrList(int event_id) {
		List<EventAlarm> EvtocList = new ArrayList<EventAlarm>();
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = null;
		String selectQuery = null;

		selectQuery = "SELECT  * FROM " + TABLE_EVT_ALARM
				+ " WHERE event_id = " + event_id + " AND (alarm_week_num = "
				+ CalenderDateList.getCurrentWeekNumber()
				+ " OR alarm_week_repeat = 'Y')";

		cursor = db.rawQuery(selectQuery, null);
		if (cursor.getCount() <= 0)
			return null;

		if (cursor.moveToFirst()) {
			do {
				EventAlarm Evt = new EventAlarm();
				Evt.set_id(Integer.parseInt(cursor.getString(0)));
				Evt.set_event_id(cursor.getString(1));
				Evt.set_alarm_time(cursor.getString(2));
				Evt.set_alarm_sat(cursor.getString(3));
				Evt.set_alarm_sun(cursor.getString(4));
				Evt.set_alarm_mon(cursor.getString(5));
				Evt.set_alarm_tue(cursor.getString(6));
				Evt.set_alarm_wed(cursor.getString(7));
				Evt.set_alarm_thu(cursor.getString(8));
				Evt.set_alarm_fri(cursor.getString(9));
				Evt.set_alarm_week_num(cursor.getString(10));
				Evt.set_alarm_week_repeat(cursor.getString(11));

				EvtocList.add(Evt);
			} while (cursor.moveToNext());
		}

		return EvtocList;
	}

	// Deleting single Event AVD by Event Id
	public void deleteEventAlaram(EventAlarm Evt) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_EVT_ALARM, KEY_EVT_ALR_EVENT_ID + " = ?",
				new String[] { String.valueOf(Evt.get_event_id()) });
		db.close();
	}
	
	// Adding new EVENT AVD
	public int addEventSeq(EventSequence Evt) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_EVT_SEQ_IMG, Evt.getSeqImg());
		values.put(KEY_EVT_SEQ_PATH, Evt.getSeqPath());
		values.put(KEY_EVT_SEQ_TYPE, Evt.getSeqType());
		values.put(KEY_EVT_SEQ_STAT, Evt.getStatus());
		values.put(KEY_EVT_SEQ_TTS, Evt.getTts());
		values.put(KEY_EVT_SEQ_TIMER, Evt.getSeqTimer());
		values.put(KEY_EVT_SEQ_EVT_ID, Evt.getEventId());
		values.put(KEY_EVT_SEQ_POSOTION, Evt.get_seq_position());
		// Inserting Row
		int last_id = (int) db.insert(TABLE_EVT_SEQ, null, values);
		db.close(); // Closing database connection

		return last_id;
	}

	// Updating single Event
	public int updateEventSeq(EventSequence Evt) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_EVT_SEQ_IMG, Evt.getSeqImg());
		values.put(KEY_EVT_SEQ_PATH, Evt.getSeqPath());
		values.put(KEY_EVT_SEQ_TYPE, Evt.getSeqType());
		values.put(KEY_EVT_SEQ_STAT, Evt.getStatus());
		values.put(KEY_EVT_SEQ_TTS, Evt.getTts());
		values.put(KEY_EVT_SEQ_TIMER, Evt.getSeqTimer());
		values.put(KEY_EVT_SEQ_EVT_ID, Evt.getEventId());
		values.put(KEY_EVT_SEQ_POSOTION, Evt.get_seq_position());

		// updating row
		return db.update(TABLE_EVT_SEQ, values, KEY_EVT_SEQ_ID + " = ?",
				new String[] { String.valueOf(Evt.getID()) });
	}

	// Updating single Event
	public int updateEventSeqStatus(EventSequence Evt) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_EVT_SEQ_STAT, Evt.getStatus());

		return db.update(TABLE_EVT_SEQ, values, KEY_EVT_SEQ_ID + " = ?",
				new String[] { String.valueOf(Evt.getID()) });
	}

	// Getting single EventOccur
	public EventSequence getEventSeq(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_EVT_SEQ, new String[] { KEY_EVT_SEQ_ID,
				KEY_EVT_SEQ_IMG, KEY_EVT_SEQ_PATH, KEY_EVT_SEQ_TYPE,
				KEY_EVT_SEQ_STAT, KEY_EVT_SEQ_TTS, KEY_EVT_SEQ_TIMER,
				KEY_EVT_AVD_EVT_ID, KEY_EVT_SEQ_POSOTION }, KEY_EVT_SEQ_ID
				+ "=?", new String[] { String.valueOf(id) }, null, null, null,
				null);
		if (cursor.getCount() <= 0)
			return null;

		cursor.moveToFirst();
		EventSequence Evt = new EventSequence(Integer.parseInt(cursor
				.getString(0)), cursor.getString(1), cursor.getString(2),
				cursor.getString(3), cursor.getString(4), cursor.getString(5),
				Integer.parseInt(cursor.getString(6)), Integer.parseInt(cursor
						.getString(7)), Integer.parseInt(cursor.getString(8)));
		// return User
		return Evt;
	}

	// Getting All Events
	// if user-id = 0 then bring all else filter by user-id
	public List<EventSequence> getAllEvtSeqList(int event_id) {
		List<EventSequence> EvtocList = new ArrayList<EventSequence>();
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = null;
		String selectQuery = null;

		if (event_id == 0) {
			// Select All Query
			selectQuery = "SELECT  * FROM " + TABLE_EVT_SEQ;

		} else {
			selectQuery = "SELECT  * FROM " + TABLE_EVT_SEQ
					+ " WHERE event_id = " + event_id;
		}
		cursor = db.rawQuery(selectQuery, null);
		if (cursor.getCount() <= 0)
			return null;

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {

				EventSequence Evt = new EventSequence();
				Evt.setID(Integer.parseInt(cursor.getString(0)));
				Evt.setSeqImg(cursor.getString(1));
				Evt.setSeqPath(cursor.getString(2));
				Evt.setSeqType(cursor.getString(3));
				Evt.setStatus(cursor.getString(4));
				Evt.setTts(cursor.getString(5));
				Evt.setSeqTimer(Integer.parseInt(cursor.getString(6)));
				Evt.setEventId(Integer.parseInt(cursor.getString(7)));
				Evt.set_seq_position(Integer.parseInt(cursor.getString(8)));
				// Adding User to list
				EvtocList.add(Evt);
			} while (cursor.moveToNext());
		}

		// return User list
		return EvtocList;
	}

	// Deleting single Event AVD
	public void deleteEventSeq(EventSequence Evt) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_EVT_SEQ, KEY_EVT_SEQ_ID + " = ?",
				new String[] { String.valueOf(Evt.getID()) });
		db.close();
	}

	// Deleting Event Seq by Event ID
	public void deleteEventSeqByEvntId(EventSequence Evt) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_EVT_SEQ, KEY_EVT_SEQ_EVT_ID + " = ?",
				new String[] { String.valueOf(Evt.getEventId()) });
		db.close();
	}

	// Adding new EVENT AVD
	public int addEventAVD(EventAudVid Evt) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_EVT_AVD_PATH, Evt.getAvPath());
		values.put(KEY_EVT_AVD_TYPE, Evt.getType());
		values.put(KEY_EVT_AVD_EVT_ID, Evt.getEventId());

		// Inserting Row
		int last_id = (int) db.insert(TABLE_EVT_AD_VD, null, values);
		db.close(); // Closing database connection

		return last_id;
	}

	// Updating single Event
	public int updateEventAVD(EventAudVid Evt) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_EVT_AVD_PATH, Evt.getAvPath());
		values.put(KEY_EVT_AVD_TYPE, Evt.getType());
		values.put(KEY_EVT_AVD_EVT_ID, Evt.getEventId());

		// updating row
		return db.update(TABLE_EVT_AD_VD, values, KEY_EVT_AVD_ID + " = ?",
				new String[] { String.valueOf(Evt.getID()) });
	}

	// Getting single EventOccur
	public EventAudVid getEventAVD(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_EVT_AD_VD, new String[] {
				KEY_EVT_AVD_ID, KEY_EVT_AVD_PATH, KEY_EVT_AVD_TYPE,
				KEY_EVT_AVD_EVT_ID }, KEY_EVT_AVD_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor.getCount() <= 0)
			return null;

		cursor.moveToFirst();
		EventAudVid Evt = new EventAudVid(
				Integer.parseInt(cursor.getString(0)), cursor.getString(1),
				cursor.getString(2), Integer.parseInt(cursor.getString(3)));
		// return User
		return Evt;
	}

	// Getting All Events
	// if user-id = 0 then bring all else filter by user-id
	public List<EventAudVid> getAllEvtAVDList(int event_id) {
		List<EventAudVid> EvtocList = new ArrayList<EventAudVid>();
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = null;
		String selectQuery = null;

		if (event_id == 0) {
			// Select All Query
			selectQuery = "SELECT  * FROM " + TABLE_EVT_AD_VD;

		} else {
			selectQuery = "SELECT  * FROM " + TABLE_EVT_AD_VD
					+ " WHERE event_id = " + event_id;
		}
		cursor = db.rawQuery(selectQuery, null);
		if (cursor.getCount() <= 0)
			return null;

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				EventAudVid Evt = new EventAudVid();
				Evt.setID(Integer.parseInt(cursor.getString(0)));

				Evt.setAvPath(cursor.getString(1));
				Evt.setType(cursor.getString(2));
				Evt.setEventId(Integer.parseInt(cursor.getString(3)));

				// Adding User to list
				EvtocList.add(Evt);
			} while (cursor.moveToNext());
		}

		// return User list
		return EvtocList;
	}

	// Deleting single Event AVD
	public void deleteEventAVD(EventAudVid Evt) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_EVT_AD_VD, KEY_EVT_AVD_EVT_ID + " = ?",
				new String[] { String.valueOf(Evt.getID()) });
		db.close();
	}

	// Adding new EVENT OC
	public int addEventOC(EventOccur Evt) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_EVT_OC_DATE, Evt.getDate());
		values.put(KEY_EVT_OC_TIME, Evt.getTime());
		values.put(KEY_EVT_OC_EVT_ID, Evt.getEventId());

		// Inserting Row
		int last_id = (int) db.insert(TABLE_EVT_OC, null, values);

		db.close(); // Closing database connection

		return last_id;
	}

	// Updating single Event
	public int updateEventOC(EventOccur Evt) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_EVT_OC_DATE, Evt.getDate());
		values.put(KEY_EVT_OC_TIME, Evt.getTime());
		values.put(KEY_EVT_OC_EVT_ID, Evt.getEventId());

		// updating row
		return db.update(TABLE_EVT_OC, values, KEY_EVT_OC_ID + " = ?",
				new String[] { String.valueOf(Evt.getID()) });
	}

	// Getting single EventOccur
	public EventOccur getEventOc(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_EVT_OC, new String[] { KEY_EVT_OC_ID,
				KEY_EVT_OC_DATE, KEY_EVT_OC_TIME, KEY_EVT_OC_EVT_STAT,
				KEY_EVT_OC_EVT_ID }, KEY_EVT_OC_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor.getCount() <= 0)
			return null;

		cursor.moveToFirst();
		EventOccur Evt = new EventOccur(Integer.parseInt(cursor.getString(0)),
				cursor.getString(1), cursor.getString(2), cursor.getString(3),
				Integer.parseInt(cursor.getString(4)));
		// return User
		return Evt;
	}

	// Getting All Events
	// if user-id = 0 then bring all else filter by user-id
	public List<EventOccur> getAllEvtOCList(int event_id) {
		List<EventOccur> EvtocList = new ArrayList<EventOccur>();
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = null;
		String selectQuery = null;

		if (event_id == 0) {

			// Select All Query
			selectQuery = "SELECT  * FROM " + TABLE_EVT_OC;

		} else {
			selectQuery = "SELECT  * FROM " + TABLE_EVT_OC
					+ " WHERE event_id = " + event_id;

		}
		cursor = db.rawQuery(selectQuery, null);
		if (cursor.getCount() <= 0)
			return null;

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				EventOccur Evt = new EventOccur();
				Evt.setID(Integer.parseInt(cursor.getString(0)));

				Evt.setDate(cursor.getString(1));
				Evt.setTime(cursor.getString(2));
				Evt.setStatus(cursor.getString(3));
				Evt.setEventId(Integer.parseInt(cursor.getString(4)));

				// Log.d("CHK: ", cursor.getString(3));

				// Adding User to list
				EvtocList.add(Evt);
			} while (cursor.moveToNext());
		}

		// return User list
		return EvtocList;
	}

	// Deleting single EventOc
	public void deleteEventOc(EventOccur Evt) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_EVT_OC, KEY_EVT_OC_ID + " = ?",
				new String[] { String.valueOf(Evt.getID()) });
		db.close();
	}

	// Deleting single EventOc
	public void deleteEventOcByEventId(int event_id) {
		SQLiteDatabase db = this.getWritableDatabase();
		String deleteSql = "DELETE FROM " + TABLE_EVT_OC + " WHERE event_id = "
				+ String.valueOf(event_id);
		db.rawQuery(deleteSql, null).moveToFirst();
		db.close();
	}

	// Adding new EVENT
	public int addEvent(Event Evt) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_EVT_LOGO, Evt.getLogo());
		values.put(KEY_EVT_DESC, Evt.getDescription());
		values.put(KEY_EVT_STAT, Evt.getStatus());
		values.put(KEY_EVT_USER_ID, Evt.getUserID());
		values.put(KEY_EVT_ALARM, Evt.get_alarm());
		values.put(KEY_EVT_DAILY_PRIZE, Evt.get_daily_prize());
		values.put(KEY_EVT_WEEKLY_PRIZE, Evt.get_weekly_prize());

		// Inserting Row
		int last_id = (int) db.insert(TABLE_EVENT, null, values);
		db.close(); // Closing database connection

		return last_id;
	}

	// Updating single Event
	public int updateEvent(Event Evt) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_EVT_LOGO, Evt.getLogo());
		values.put(KEY_EVT_DESC, Evt.getDescription());
		values.put(KEY_EVT_STAT, Evt.getStatus());
		values.put(KEY_EVT_USER_ID, Evt.getUserID());
		values.put(KEY_EVT_ALARM, Evt.get_alarm());
		values.put(KEY_EVT_DAILY_PRIZE, Evt.get_daily_prize());
		values.put(KEY_EVT_WEEKLY_PRIZE, Evt.get_weekly_prize());
		
		// updating row
		return db.update(TABLE_EVENT, values, KEY_EVT_ID + " = ?",
				new String[] { String.valueOf(Evt.getID()) });
	}

	public void updateEventByStatus(int event_id, String status) {

		Event result = getEvent(event_id);
		result.setStatus(status);
		updateEvent(result);

	}

	// Deleting single Event
	public void deleteEvent(Event Evt) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_EVENT, KEY_EVT_ID + " = ?",
				new String[] { String.valueOf(Evt.getID()) });
		db.close();
	}

	// Getting single Event
	public Event getEvent(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_EVENT, new String[] { KEY_EVT_ID,
				KEY_EVT_LOGO, KEY_EVT_DESC, KEY_EVT_STAT, KEY_EVT_USER_ID, KEY_EVT_ALARM, KEY_EVT_DAILY_PRIZE, KEY_EVT_WEEKLY_PRIZE},
				KEY_EVT_ID + "=?", new String[] { String.valueOf(id) }, null,
				null, null, null);
		if (cursor.getCount() <= 0)
			return null;

		cursor.moveToFirst();
		Event Evt = new Event(Integer.parseInt(cursor.getString(0)),
				cursor.getString(1), cursor.getString(2), cursor.getString(3),
				Integer.parseInt(cursor.getString(4)),cursor.getString(5),cursor.getString(6),cursor.getString(7));
		// return User
		return Evt;
	}

	// Getting All Events
	// if user-id = 0 then bring all else filter by user-id
	public List<Event> getAllEvts(int user_id) {
		List<Event> EvtList = new ArrayList<Event>();
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = null;
		String selectQuery = null;

		if (user_id == 0) {
			// Select All Query
			selectQuery = "SELECT  * FROM " + TABLE_EVENT;
		} else {
			selectQuery = "SELECT  * FROM " + TABLE_EVENT + " WHERE user_id = " + user_id;
		}
		cursor = db.rawQuery(selectQuery, null);
		if (cursor.getCount() <= 0)
			return null;

		if (cursor.moveToFirst()) {
			do {
				Event Evt = new Event();
				Evt.setID(Integer.parseInt(cursor.getString(0)));
				Evt.setLogo(cursor.getString(1));
				Evt.setDescription(cursor.getString(2));
				Evt.setDescription(cursor.getString(3));
				Evt.setUserID(Integer.parseInt(cursor.getString(4)));
				Evt.setDescription(cursor.getString(5));
				Evt.setDescription(cursor.getString(6));
				Evt.setDescription(cursor.getString(7));
				EvtList.add(Evt);
			} while (cursor.moveToNext());
		}
		return EvtList;
	}

	// Adding new User
	public int addUser(User User) {
		SQLiteDatabase db = this.getWritableDatabase();
		String hash = null;
		try {
			hash = PasswordHash.createHash(User.getPassword());

		} catch (Exception e) {
			e.printStackTrace();
		}

		ContentValues values = new ContentValues();
		values.put(KEY_USER_NAME, User.getName());
		values.put(KEY_USER_PASS, hash);
		values.put(KEY_USER_PIC, User.getProfilePic()); 
		values.put(KEY_USER_TYPE, User.getType());
		values.put(KEY_USER_DEFAULT_VIEW, User.get_default_view());
		values.put(KEY_USER_ALARM, User.get_alarm());
		// Inserting Row
		int last_id = (int) db.insert(TABLE_USERS, null, values);
		db.close(); // Closing database connection

		return last_id;
	}

	// Getting single User
	public User getUser(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_USERS, new String[] { KEY_USER_ID,
				KEY_USER_NAME, KEY_USER_PASS, KEY_USER_PIC, KEY_USER_TYPE, KEY_USER_DEFAULT_VIEW, KEY_USER_ALARM },
				KEY_USER_ID + "=?", new String[] { String.valueOf(id) }, null,
				null, null, null);
		if (cursor.getCount() <= 0)
			return null;

		cursor.moveToFirst();
		User User = new User(Integer.parseInt(cursor.getString(0)),
				cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6));
		// return User
		return User;
	}

	// Getting All Users
	public List<User> getAllUsers() {
		List<User> UserList = new ArrayList<User>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_USERS;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		if (cursor.getCount() <= 0)
			return null;

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				User User = new User();
				User.setID(Integer.parseInt(cursor.getString(0)));
				User.setName(cursor.getString(1));
				User.setPassword(cursor.getString(2));
				User.setProfilePic(cursor.getString(3));
				User.setType(cursor.getString(4));
				User.set_default_view(cursor.getString(5));
				User.set_alarm(cursor.getString(6));
				// Adding User to list
				UserList.add(User);
			} while (cursor.moveToNext());
		}

		// return User list
		return UserList;
	}

	// Updating single User
	public int updateUser(User User) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_USER_NAME, User.getName());
		values.put(KEY_USER_PASS, User.getPassword());
		values.put(KEY_USER_PIC, User.getProfilePic());
		values.put(KEY_USER_TYPE, User.getType());
		values.put(KEY_USER_DEFAULT_VIEW, User.get_default_view());
		values.put(KEY_USER_ALARM, User.get_alarm());
		// updating row
		return db.update(TABLE_USERS, values, KEY_USER_ID + " = ?",
				new String[] { String.valueOf(User.getID()) });
	}

	// Deleting single User
	public void deleteUser(User User) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_USERS, KEY_USER_ID + " = ?",
				new String[] { String.valueOf(User.getID()) });
		db.close();
	}

	// Getting Users Count
	public int getUsersCount() {
		String countQuery = "SELECT  * FROM " + TABLE_USERS;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.moveToFirst();
		cursor.close();

		// return count
		return cursor.getCount();
	}

	// Getting Morning Data
	public List<MorningEvent> morningEventList(String odayname,
			String week_number, int uid) {
		List<MorningEvent> eventList = new ArrayList<MorningEvent>();

		String selectQuery = "" + " SELECT " + " A.event_id, "
				+ " A.alarm_time, " + " B.logo, " + " B.status, "
				+ " B.description " + " FROM " + " EventAlarm A "
				+ " INNER JOIN Events B ON A.event_id = B.id "
				+ " WHERE "
				// + " (A.alarm_week_num = '"+ week_number+
				// "' OR A.alarm_week_repeat = 'Y')"
				+ " (A.alarm_week_num <> 'N' OR A.alarm_week_repeat = 'Y')"
				+ " AND B.user_id = '"
				+ String.valueOf(uid)
				+ "' "
				+ " AND strftime('%H:%M', A.alarm_time) BETWEEN '06:00' AND '11:59' "
				+ " AND CASE "
				+ " WHEN 'Sat' = '"
				+ odayname
				+ "' THEN "
				+ " alarm_sat = 'Y' "
				+ " WHEN 'Sun' = '"
				+ odayname
				+ "' THEN "
				+ " alarm_sun = 'Y' "
				+ " WHEN 'Mon' = '"
				+ odayname
				+ "' THEN "
				+ " alarm_mon = 'Y' "
				+ " WHEN 'Tue' = '"
				+ odayname
				+ "' THEN "
				+ " alarm_tue = 'Y' "
				+ " WHEN 'Wed' = '"
				+ odayname
				+ "' THEN "
				+ " alarm_wed = 'Y' "
				+ " WHEN 'Thu' = '"
				+ odayname
				+ "' THEN "
				+ " alarm_thu = 'Y' "
				+ " WHEN 'Fri' = '"
				+ odayname
				+ "' THEN "
				+ " alarm_fri = 'Y' " + " END " + " ORDER BY A.alarm_time ASC ";

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.getCount() <= 0)
			return null;

		if (cursor.moveToFirst()) {
			do {
				MorningEvent morn_event = new MorningEvent();
				morn_event.set_event_id(Integer.parseInt(cursor.getString(0)));
				morn_event.set_time(cursor.getString(1));
				morn_event.set_logo(cursor.getString(2));
				morn_event.set_status(cursor.getString(3));
				morn_event.set_tts(cursor.getString(4));

				eventList.add(morn_event);
			} while (cursor.moveToNext());
		}

		return eventList;
	}

	// Getting Afternoon Data
	public List<AfterNoonEvent> afterNoonEventList(String odayname,
			String week_number, int uid) {
		List<AfterNoonEvent> eventList = new ArrayList<AfterNoonEvent>();

		String selectQuery = "" + " SELECT " + " A.event_id, "
				+ " A.alarm_time, " + " B.logo, " + " B.status, "
				+ " B.description " + " FROM " + " EventAlarm A "
				+ " INNER JOIN Events B ON A.event_id = B.id "
				+ " WHERE "
				// + " (A.alarm_week_num = '"+ week_number+
				// "' OR A.alarm_week_repeat = 'Y')"
				+ " (A.alarm_week_num <> 'N' OR A.alarm_week_repeat = 'Y')"
				+ " AND B.user_id = '"
				+ String.valueOf(uid)
				+ "' "
				+ " AND strftime('%H:%M', A.alarm_time) BETWEEN '12:00' AND '17:59' "
				+ " AND CASE "
				+ " WHEN 'Sat' = '"
				+ odayname
				+ "' THEN "
				+ " alarm_sat = 'Y' "
				+ " WHEN 'Sun' = '"
				+ odayname
				+ "' THEN "
				+ " alarm_sun = 'Y' "
				+ " WHEN 'Mon' = '"
				+ odayname
				+ "' THEN "
				+ " alarm_mon = 'Y' "
				+ " WHEN 'Tue' = '"
				+ odayname
				+ "' THEN "
				+ " alarm_tue = 'Y' "
				+ " WHEN 'Wed' = '"
				+ odayname
				+ "' THEN "
				+ " alarm_wed = 'Y' "
				+ " WHEN 'Thu' = '"
				+ odayname
				+ "' THEN "
				+ " alarm_thu = 'Y' "
				+ " WHEN 'Fri' = '"
				+ odayname
				+ "' THEN "
				+ " alarm_fri = 'Y' " + " END "

				+ " ORDER BY A.alarm_time ASC ";

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		Log.d("Pass", String.valueOf(cursor.getCount()));

		if (cursor.getCount() <= 0)
			return null;

		if (cursor.moveToFirst()) {
			do {
				AfterNoonEvent after_event = new AfterNoonEvent();
				after_event.set_event_id(Integer.parseInt(cursor.getString(0)));
				after_event.set_time(cursor.getString(1));
				after_event.set_logo(cursor.getString(2));
				after_event.set_status(cursor.getString(3));
				after_event.set_tts(cursor.getString(4));

				eventList.add(after_event);
			} while (cursor.moveToNext());
		}

		return eventList;
	}

	// Getting Evening Data
	public List<EveningEvent> eveningEventList(String odayname,
			String week_number, int uid) {
		List<EveningEvent> eventList = new ArrayList<EveningEvent>();

		String selectQuery = "" + " SELECT " + " A.event_id, "
				+ " A.alarm_time, " + " B.logo, " + " B.status, "
				+ " B.description " + " FROM " + " EventAlarm A "
				+ " INNER JOIN Events B ON A.event_id = B.id "
				+ " WHERE "
				// + " (A.alarm_week_num = '"+ week_number+
				// "' OR A.alarm_week_repeat = 'Y')"
				+ " (A.alarm_week_num <> 'N' OR A.alarm_week_repeat = 'Y')"
				+ " AND B.user_id = '"+ String.valueOf(uid)+ "' "
				+ " AND (strftime('%H:%M', A.alarm_time) >= '18:00' OR strftime('%H:%M', A.alarm_time) <= '05:59') "
				+ " AND CASE "
				+ " WHEN 'Sat' = '"
				+ odayname
				+ "' THEN "
				+ " alarm_sat = 'Y' "
				+ " WHEN 'Sun' = '"
				+ odayname
				+ "' THEN "
				+ " alarm_sun = 'Y' "
				+ " WHEN 'Mon' = '"
				+ odayname
				+ "' THEN "
				+ " alarm_mon = 'Y' "
				+ " WHEN 'Tue' = '"
				+ odayname
				+ "' THEN "
				+ " alarm_tue = 'Y' "
				+ " WHEN 'Wed' = '"
				+ odayname
				+ "' THEN "
				+ " alarm_wed = 'Y' "
				+ " WHEN 'Thu' = '"
				+ odayname
				+ "' THEN "
				+ " alarm_thu = 'Y' "
				+ " WHEN 'Fri' = '"
				+ odayname
				+ "' THEN "
				+ " alarm_fri = 'Y' " + " END "

				+ " ORDER BY A.alarm_time ASC ";

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		Log.d("Pass", String.valueOf(cursor.getCount()));

		if (cursor.getCount() <= 0)
			return null;

		if (cursor.moveToFirst()) {
			do {
				EveningEvent evening_event = new EveningEvent();
				evening_event
						.set_event_id(Integer.parseInt(cursor.getString(0)));
				evening_event.set_time(cursor.getString(1));
				evening_event.set_logo(cursor.getString(2));
				evening_event.set_status(cursor.getString(3));

				evening_event.set_tts(cursor.getString(4));

				eventList.add(evening_event);
			} while (cursor.moveToNext());
		}

		return eventList;
	}

	// Checking Week Number
	// Created by RAFI
	public int checkWeekNumber(String weekNumber, String time) {
		String countQuery = "select * from EventAlarm A where A.alarm_week_num ='"
				+ weekNumber + "' and A.alarm_time='" + time + "'";
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);

		// return count
		return cursor.getCount();
	}

	// Updating single Event Alarm for Mon - Sun
	// Created by RAFI
	public int updateEventAlarmMon(EventAlarm Evt) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_EVT_MON, Evt.get_alarm_mon());

		return db.update(
				TABLE_EVT_ALARM,
				values,
				KEY_EVT_WEEK_NUM + " = ? AND " + KEY_EVT_TIME + " = ?",
				new String[] { String.valueOf(Evt.get_alarm_week_num()),
						String.valueOf(Evt.get_alarm_time()) });
	}

	public int updateEventAlarmTue(EventAlarm Evt) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_EVT_TUE, Evt.get_alarm_tue());

		return db.update(
				TABLE_EVT_ALARM,
				values,
				KEY_EVT_WEEK_NUM + " = ? AND " + KEY_EVT_TIME + " = ?",
				new String[] { String.valueOf(Evt.get_alarm_week_num()),
						String.valueOf(Evt.get_alarm_time()) });
	}

	public int updateEventAlarmWed(EventAlarm Evt) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_EVT_WED, Evt.get_alarm_wed());

		return db.update(
				TABLE_EVT_ALARM,
				values,
				KEY_EVT_WEEK_NUM + " = ? AND " + KEY_EVT_TIME + " = ?",
				new String[] { String.valueOf(Evt.get_alarm_week_num()),
						String.valueOf(Evt.get_alarm_time()) });
	}

	public int updateEventAlarmThu(EventAlarm Evt) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_EVT_THU, Evt.get_alarm_thu());

		return db.update(
				TABLE_EVT_ALARM,
				values,
				KEY_EVT_WEEK_NUM + " = ? AND " + KEY_EVT_TIME + " = ?",
				new String[] { String.valueOf(Evt.get_alarm_week_num()),
						String.valueOf(Evt.get_alarm_time()) });
	}

	public int updateEventAlarmFri(EventAlarm Evt) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_EVT_FRI, Evt.get_alarm_fri());

		return db.update(
				TABLE_EVT_ALARM,
				values,
				KEY_EVT_WEEK_NUM + " = ? AND " + KEY_EVT_TIME + " = ?",
				new String[] { String.valueOf(Evt.get_alarm_week_num()),
						String.valueOf(Evt.get_alarm_time()) });
	}

	public int updateEventAlarmSat(EventAlarm Evt) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_EVT_SAT, Evt.get_alarm_sat());

		return db.update(
				TABLE_EVT_ALARM,
				values,
				KEY_EVT_WEEK_NUM + " = ? AND " + KEY_EVT_TIME + " = ?",
				new String[] { String.valueOf(Evt.get_alarm_week_num()),
						String.valueOf(Evt.get_alarm_time()) });
	}

	public int updateEventAlarmSun(EventAlarm Evt) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_EVT_SUN, Evt.get_alarm_sun());

		return db.update(
				TABLE_EVT_ALARM,
				values,
				KEY_EVT_WEEK_NUM + " = ? AND " + KEY_EVT_TIME + " = ?",
				new String[] { String.valueOf(Evt.get_alarm_week_num()),
						String.valueOf(Evt.get_alarm_time()) });
	}

	// Getting Week Data
	public ArrayList<WeekEvent> weekEventList(String odayname,
			String week_number, int uid) {
		ArrayList<WeekEvent> weekEventList = new ArrayList<WeekEvent>();

		String selectQuery = "" + " SELECT " + " A.event_id, " + " B.logo, "
				+ " B.description " + " FROM " + " EventAlarm A "
				+ " INNER JOIN Events B ON A.event_id = B.id " 
				+ " WHERE "+ " (A.alarm_week_num = '" + week_number + "' OR A.alarm_week_repeat = 'Y')"
				+ " AND B.user_id = '" + String.valueOf(uid)+ "' "
				+ " AND CASE "
				+ " WHEN 'Sat' = '"
				+ odayname
				+ "' THEN "
				+ " alarm_sat = 'Y' "
				+ " WHEN 'Sun' = '"
				+ odayname
				+ "' THEN "
				+ " alarm_sun = 'Y' "
				+ " WHEN 'Mon' = '"
				+ odayname
				+ "' THEN "
				+ " alarm_mon = 'Y' "
				+ " WHEN 'Tue' = '"
				+ odayname
				+ "' THEN "
				+ " alarm_tue = 'Y' "
				+ " WHEN 'Wed' = '"
				+ odayname
				+ "' THEN "
				+ " alarm_wed = 'Y' "
				+ " WHEN 'Thu' = '"
				+ odayname
				+ "' THEN "
				+ " alarm_thu = 'Y' "
				+ " WHEN 'Fri' = '"
				+ odayname
				+ "' THEN "
				+ " alarm_fri = 'Y' " 
				+ " END " 
				+ " ORDER BY A.alarm_time ASC ";

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		Log.d("Pass", String.valueOf(cursor.getCount()));

		if (cursor.getCount() <= 0)
			return null;

		if (cursor.moveToFirst()) {
			do {
				WeekEvent weekEvent = new WeekEvent();
				weekEvent.setEventId(Integer.parseInt(cursor.getString(0)));
				weekEvent.setEventLogo(cursor.getString(1));
				weekEvent.setEventTTS(cursor.getString(2));

				weekEventList.add(weekEvent);
			} while (cursor.moveToNext());
		}

		return weekEventList;
	}

	// Getting Week Data
	public int monthEvent(String day) {
		List<WeekEvent> weekEventList = new ArrayList<WeekEvent>();

		String selectQuery = "" + "CREATE TEMP TABLE"
				+ " data"
				+ " AS"
				+ " SELECT "
				+ " event_id, "
				+ " alarm_week_num week, "
				+ " alarm_time alarm,"
				+ " CASE "
				+ "WHEN "
				+ " a.alarm_mon = 'Y' "
				+ " THEN "
				+ "  'Mon' "
				+ " WHEN a.alarm_tue = 'Y' "
				+ " THEN "
				+ "'Tue'"
				+

				" WHEN a.alarm_wed = 'Y'"
				+ " THEN"
				+ " 'Wed' "
				+

				"WHEN a.alarm_thu = 'Y' "
				+ " THEN "
				+ " 'Thu' "
				+ "WHEN a.alarm_fri = 'Y'"
				+ " THEN "
				+ " 'Fri' "
				+ " WHEN a.alarm_sat = 'Y' "
				+ " THEN "
				+ " 'Sat' "
				+ "WHEN a.alarm_sun = 'Y' "
				+ " THEN "
				+ " 'Sun' "
				+ " END"
				+ " dayName"
				+ " FROM"
				+ " EventAlarm a"
				// + "WHERE"
				// + " alarm_month_num = 1"
				+ ";" +

				" select " + "week," + " dayName," + " alarm," + "(SELECT "
				+ " x.seq_image " + "FROM " + " EventSequnces x "
				+ "INNER JOIN" + " EventAlarm y " + "ON"
				+ " x.event_id = y.event_id " + "WHERE" + " CASE"
				+ " WHEN 'Mon' = dayName" + " THEN" + " y.alarm_mon = 'Y' "
				+ "WHEN 'Tue' = dayName " + " THEN " + " y.alarm_tue = 'Y' "
				+ "WHEN 'Wed' = dayName " + " THEN " + "y.alarm_wed = 'Y' "
				+ "WHEN 'Thu' = dayName" + " THEN" + " y.alarm_thu = 'Y' "
				+ "WHEN 'Fri' = dayName" + " THEN" + " y.alarm_fri = 'Y' "
				+ "WHEN 'Sat' = dayName" + " THEN " + " y.alarm_sat = 'Y' "
				+ "WHEN 'Sun' = dayName " + " THEN " + " y.alarm_sun = 'Y' "
				+ " END" + " AND" + " y.alarm_week_num = week" + " ORDER BY "
				+ " alarm_time ASC " + "LIMIT 1) " + " logo" + " from"
				+ " data;" + " drop table data";

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		Log.d("Pass", String.valueOf(cursor.getCount()));

		/*
		 * if (cursor.getCount() <= 0) return 0;
		 */

		if (cursor.moveToFirst()) {
			do {
				String aa = cursor.getString(1);
				String bb = cursor.getString(1);
				String cc = cursor.getString(1);

			} while (cursor.moveToNext());
		}

		return cursor.getCount();
	}

	// Checking for Actions status
	public boolean checkEventSeqStatus(int event_id) {
		Boolean check = true;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = null;
		String selectQuery = null;

		selectQuery = "select id from " + TABLE_EVT_SEQ + " WHERE event_id = "
				+ event_id + " and (status = '2' or status = '0')";

		// select id from EventSequnces a where a.event_id = 1 and (a.status =
		// '2' or a.status = '0')

		cursor = db.rawQuery(selectQuery, null);
		if (cursor.getCount() <= 0)
			check = false;

		// looping through all rows and adding to list
		/*
		 * if (cursor.moveToFirst()) { do {
		 * 
		 * EventSequence Evt = new EventSequence();
		 * Evt.setID(Integer.parseInt(cursor.getString(0))); EvtocList.add(Evt);
		 * } while (cursor.moveToNext()); }
		 * 
		 * if(EvtocList.size()==0) check = false;
		 */

		// return User list
		return check;
	}

	public String monthEventList(String weekDay, String weekNumber,
			String userId) {
		String selectQuery = "SELECT " + " x.logo "+ ",(select image from MonthViewImage where week = "+weekNumber+" and day = '"+weekDay+"' and user_id = '"+userId+"') updated" 
				+ " FROM " + " Events x "
				+ "INNER JOIN" + " EventAlarm y " + "ON"
				+ " x.id = y.event_id " + " WHERE " + " (y.alarm_week_num = '"+ weekNumber+ "' OR y.alarm_week_repeat = 'Y')"
				+ " AND x.user_id = '"
				+ userId
				+ "' "
				+ " AND CASE"
				+ " WHEN 'Mon' = '"
				+ weekDay
				+ "' THEN"
				+ " y.alarm_mon = 'Y' "
				+ "WHEN 'Tue' = '"
				+ weekDay
				+ "' THEN "
				+ " y.alarm_tue = 'Y' "
				+ "WHEN 'Wed' = '"
				+ weekDay
				+ "' THEN "
				+ "y.alarm_wed = 'Y' "
				+ "WHEN 'Thu' = '"
				+ weekDay
				+ "' THEN"
				+ " y.alarm_thu = 'Y' "
				+ "WHEN 'Fri' = '"
				+ weekDay
				+ "' THEN"
				+ " y.alarm_fri = 'Y' "
				+ "WHEN 'Sat' = '"
				+ weekDay
				+ "' THEN "
				+ " y.alarm_sat = 'Y' "
				+ "WHEN 'Sun' = '"
				+ weekDay
				+ "' THEN "
				+ " y.alarm_sun = 'Y' " + " END"
				// + " AND" + " y.alarm_week_num = '" + weekNumber + "'"
				+ " ORDER BY " + " alarm_time ASC " + "LIMIT 1 ";

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.getCount() <= 0)
			return null;
		String imagePath = null;
		if (cursor.moveToFirst()) {
			do {
				if(cursor.getString(1) != null)
				{
					imagePath = cursor.getString(1);
				}
				else
				{
					imagePath = cursor.getString(0);
				}

			} while (cursor.moveToNext());
		}
		return imagePath;
	}

	// Deleting data from all tables for an User
	public void deleteAllUserData(int userId) {
		SQLiteDatabase db = this.getWritableDatabase();
		List<Event> data = new ArrayList<Event>();
		data = getAllEvts(userId);
		if (data != null) {
			for (Event event : data) {
				db.delete(TABLE_EVENT, KEY_EVT_ID + " = ?",
						new String[] { String.valueOf(event.getID()) });
				db.delete(TABLE_EVT_ALARM, KEY_EVT_ALR_EVENT_ID + " = ?",
						new String[] { String.valueOf(event.getID()) });
				db.delete(TABLE_EVT_SEQ, KEY_EVT_SEQ_EVT_ID + " = ?",
						new String[] { String.valueOf(event.getID()) });
				db.delete(TABLE_EVT_COMPLETE, KEY_EVT_COMP_EVT_ID + " = ?",
						new String[] { String.valueOf(event.getID()) });
			}
		}
		db.delete(TABLE_USERS, KEY_USER_ID + " = ?",
				new String[] { String.valueOf(userId) });
		db.delete(TABLE_MONTH_IMAGE, KEY_MONTHVIEW_USER_ID+ " = ?",
				new String[] { String.valueOf(userId) });
		db.close();
	}

	// Deleting data from all tables for single Event
	public void deleteAllEventData(int eventID) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_EVENT, KEY_EVT_ID + " = ?",
				new String[] { String.valueOf(eventID) });
		db.delete(TABLE_EVT_ALARM, KEY_EVT_ALR_EVENT_ID + " = ?",
				new String[] { String.valueOf(eventID) });
		db.delete(TABLE_EVT_SEQ, KEY_EVT_SEQ_EVT_ID + " = ?",
				new String[] { String.valueOf(eventID) });
		db.delete(TABLE_EVT_COMPLETE, KEY_EVT_COMP_EVT_ID + " = ?",
				new String[] { String.valueOf(eventID) });
		db.close();
	}

	// Update new Monthview Image
	public int addUpdateMonthView(MonthviewImage monthviewImageUpdate) {
		int last_id = 0;
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();

		String selectQuery = "select * from MonthViewImage where week = "
				+ monthviewImageUpdate.get_week() + " and day = '"
				+ monthviewImageUpdate.get_day() + "'";

		Cursor cursor = db.rawQuery(selectQuery, null);
		if (cursor.getCount() == 0) {
			values.put(KEY_MONTHVIEW_WEEK, monthviewImageUpdate.get_week());
			values.put(KEY_MONTHVIEW_DAY, monthviewImageUpdate.get_day());
			values.put(KEY_MONTHVIEW_IMAGEPATH,
					monthviewImageUpdate.get_image());
			values.put(KEY_MONTHVIEW_USER_ID,
					monthviewImageUpdate.get_user_id());

			// Inserting Row
			last_id = (int) db.insert(TABLE_MONTH_IMAGE, null, values);
		} else {
			// Updating Row
			values.put(KEY_MONTHVIEW_WEEK, monthviewImageUpdate.get_week());
			values.put(KEY_MONTHVIEW_DAY, monthviewImageUpdate.get_day());
			values.put(KEY_MONTHVIEW_IMAGEPATH,
					monthviewImageUpdate.get_image());
			values.put(KEY_MONTHVIEW_USER_ID,
					monthviewImageUpdate.get_user_id());

			db.update(TABLE_MONTH_IMAGE, values, KEY_MONTHVIEW_WEEK
					+ " = ? AND " + KEY_MONTHVIEW_DAY + " = ?",
					new String[] { monthviewImageUpdate.get_week(),
							monthviewImageUpdate.get_day() });
		}
		db.close(); // Closing database connection

		return last_id;
	}
	
	// Updating alarm of user
	public int updateAlarmOfUser(String userID, String alarmOn, String alarmOff) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		String updateQuery ="update Users set [alarm]= 'N'";
		Cursor c= db.rawQuery(updateQuery, null);

		c.moveToFirst();
		c.close();
		
		String updateQuery2 ="update Users set [alarm]= 'Y' where id = "+userID;
		Cursor c2= db.rawQuery(updateQuery2, null);

		c2.moveToFirst();
		c2.close();
		
		return 0;
	}
	
	// Getting Alarm Data
	public ArrayList<AlarmEvent> alarmEventList(String odayname,
			String week_number) {
		ArrayList<AlarmEvent> alarmEventList = new ArrayList<AlarmEvent>();

		String selectQuery = "" + " SELECT " + " A.event_id, " + " A.alarm_time, " + " B.user_id, " + " B.logo "
				+ " FROM " + " EventAlarm A "
				+ " INNER JOIN Events B ON A.event_id = B.id " 
				+ " WHERE "+ " (A.alarm_week_num = '" + week_number + "' OR A.alarm_week_repeat = 'Y')"
				+ " AND B.user_id = (SELECT id from Users WHERE alarm = 'Y') "
				+ " AND B.alarm = 'Y'"
				+ " AND CASE "
				+ " WHEN 'Sat' = '"
				+ odayname
				+ "' THEN "
				+ " alarm_sat = 'Y' "
				+ " WHEN 'Sun' = '"
				+ odayname
				+ "' THEN "
				+ " alarm_sun = 'Y' "
				+ " WHEN 'Mon' = '"
				+ odayname
				+ "' THEN "
				+ " alarm_mon = 'Y' "
				+ " WHEN 'Tue' = '"
				+ odayname
				+ "' THEN "
				+ " alarm_tue = 'Y' "
				+ " WHEN 'Wed' = '"
				+ odayname
				+ "' THEN "
				+ " alarm_wed = 'Y' "
				+ " WHEN 'Thu' = '"
				+ odayname
				+ "' THEN "
				+ " alarm_thu = 'Y' "
				+ " WHEN 'Fri' = '"
				+ odayname
				+ "' THEN "
				+ " alarm_fri = 'Y' " 
				+ " END " 
				+ " ORDER BY A.alarm_time ASC ";

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.getCount() <= 0)
			return null;

		if (cursor.moveToFirst()) {
			do {
				AlarmEvent alarmEvent = new AlarmEvent();
				alarmEvent.set_event_id((Integer.parseInt(cursor.getString(0))));
				alarmEvent.set_time((cursor.getString(1)));
				alarmEvent.set_user_id((Integer.parseInt(cursor.getString(2))));
				alarmEvent.set_image((cursor.getString(3)));
				alarmEventList.add(alarmEvent);
			} while (cursor.moveToNext());
		}

		return alarmEventList;
	}
	
	// Update new Monthview Image
	public int addUpdateEventComplete(EventComplete eventComplete) {
		int last_id = 0;
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();

		String selectQuery = "SELECT * FROM "+ TABLE_EVT_COMPLETE +" WHERE "+ KEY_EVT_COMP_WEEK +" = '" + eventComplete.get_week() 
								+ "' and " + KEY_EVT_COMP_DAY + " = '" + eventComplete.get_day() + "' and " + KEY_EVT_COMP_EVT_ID + " = '" + eventComplete.get_event_id()+ "'";

		Cursor cursor = db.rawQuery(selectQuery, null);
		if (cursor.getCount() == 0) {
			values.put(KEY_EVT_COMP_EVT_ID, eventComplete.get_event_id());
			values.put(KEY_EVT_COMP_DAY, eventComplete.get_day());
			values.put(KEY_EVT_COMP_WEEK, eventComplete.get_week());

			// Inserting Row
			last_id = (int) db.insert(TABLE_EVT_COMPLETE, null, values);
		} else {
			// Updating Row
			//values.put(KEY_EVT_COMP_ID, eventComplete.get_id());
			/*values.put(KEY_EVT_COMP_EVT_ID, eventComplete.get_event_id());
			values.put(KEY_EVT_COMP_DAY, eventComplete.get_day());
			values.put(KEY_EVT_COMP_WEEK, eventComplete.get_week());

			db.update(TABLE_EVT_COMPLETE, values, KEY_EVT_COMP_EVT_ID + " = ? AND " + KEY_EVT_COMP_WEEK + " = ? AND " + KEY_EVT_COMP_DAY + " = ?", 
					new String[] { eventComplete.get_week(), eventComplete.get_day() });*/
		}
		db.close(); // Closing database connection

		return last_id;
	}

	// Updating Event Seq Status by Event Id
	public int updateEventSeqStatusByEventId(EventSequence Evt) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_EVT_SEQ_STAT, Evt.getStatus());

		return db.update(TABLE_EVT_SEQ, values, KEY_EVT_SEQ_EVT_ID + " = ?",
				new String[] { String.valueOf(Evt.getEventId()) });
	}
	
	// Getting data for Prize View
	public List<Prize> getPrizeData(String week, int userId)
	{
		List<Prize> prizeList = new ArrayList<Prize>();

		String selectQuery = "SELECT   a.id, a.[alarm_time],c.[logo],"+
		      "CASE "+ 
		         "WHEN a.[alarm_mon] =  'Y' THEN (SELECT COUNT(b.id)  FROM [EventComplete] b WHERE b.[week]='"+ week+ "' AND b.[day] = 'Mon' AND b.[event_id]=a.id) "+
		         "ELSE 'N' "+
		      "END as Mon, "+  
		      "CASE "+ 
		           "WHEN a.[alarm_tue] =  'Y' THEN (SELECT COUNT(b.id) FROM [EventComplete] b WHERE b.[week]='"+ week+ "' AND b.[day] = 'Tue' AND b.[event_id]=a.id) "+
		           "ELSE 'N' "+
		      "END as Tue, "+      
		      "CASE "+
		           "WHEN a.[alarm_wed] =  'Y' THEN (SELECT COUNT(b.id)  FROM [EventComplete] b WHERE b.[week]='"+ week+ "' AND b.[day] = 'Wed' AND b.[event_id]=a.id) "+  
		           "ELSE 'N' "+
		      "END as Wed, "+
		      "CASE "+ 
		         "WHEN a.[alarm_thu] =  'Y' THEN (SELECT  COUNT(b.id)  FROM [EventComplete] b WHERE b.[week]='"+ week+ "' AND b.[day] = 'Thu' AND b.[event_id]=a.id ) "+ 
		         "ELSE 'N' "+
		      "END as Thu,  "+ 
		      "CASE "+
		           "WHEN a.[alarm_fri] =  'Y' THEN (SELECT COUNT(b.id)  FROM [EventComplete] b WHERE b.[week]='"+ week+ "' AND b.[day] = 'Fri' AND b.[event_id]=a.id) "+
		           "ELSE 'N' "+
		      "END as Fri, "+      
		      "CASE "+ 
		         "WHEN a.[alarm_sat] =  'Y' THEN (SELECT COUNT(b.id)  FROM [EventComplete] b WHERE b.[week]='"+ week+ "' AND b.[day] = 'Sat' AND b.[event_id]=a.id) "+
		         "ELSE 'N' "+
		      "END as Sat, "+  
		      "CASE "+ 
		         "WHEN a.[alarm_sun] =  'Y' THEN (SELECT COUNT(b.id)  FROM [EventComplete] b WHERE b.[week]='"+ week+ "' AND b.[day] = 'Sun' AND b.[event_id]=a.id) "+
		         "ELSE 'N' "+
		      "END as Sun, "+
		      "c.[weekly_prize] "+ 
			"from [EventAlarm] a INNER JOIN [Events] c ON a.[event_id] = c.id "+
			"where ([alarm_week_num] = '"+ week+ "' OR [alarm_week_repeat] = 'Y') AND c.[user_id]='"+ userId+ "' AND (LENGTH(c.[weekly_prize]) > 0 OR c.[weekly_prize] IS NOT NULL)  "+
			"ORDER BY a.[alarm_time] ASC ; ";
						

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.getCount() <= 0)
			return null;

		if (cursor.moveToFirst()) {
			do {
				Prize prize = new Prize();
				prize.set_id(Integer.parseInt(cursor.getString(0)));
				prize.set_time(cursor.getString(1));
				prize.set_logo(cursor.getString(2));
				prize.set_Mon(cursor.getString(3));
				prize.set_Tue(cursor.getString(4));
				prize.set_Wed(cursor.getString(5));
				prize.set_Thu(cursor.getString(6));
				prize.set_Fri(cursor.getString(7));
				prize.set_Sat(cursor.getString(8));
				prize.set_Sun(cursor.getString(9));
				prize.set_weekly_prize(cursor.getString(10));
				prizeList.add(prize);
				
			} while (cursor.moveToNext());
		}

		return prizeList;
	}

	//Delete Event Complete data by Event Id
	public void deleteEventCompByEventId(int eventId)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_EVT_COMPLETE, KEY_EVT_COMP_EVT_ID + " = ?",
				new String[] { String.valueOf(eventId) });
		db.close();
	}
}