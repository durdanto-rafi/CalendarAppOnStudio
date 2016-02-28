package com.uniqgroup.pojo;

public class EventAlarm {

	// private variables
	int _id;
	String _event_id;
	String _alarm_time;
	String _alarm_sat;
	String _alarm_sun;
	String _alarm_mon;
	String _alarm_tue;
	String _alarm_wed;
	String _alarm_thu;
	String _alarm_fri;
	String _alarm_week_num;
	String _alarm_week_repeat;

	public EventAlarm() {

	}

	public EventAlarm(int id, String event_id, String alarm_time,
			String alarm_sat, String alarm_sun, String alarm_mon,
			String alarm_tue, String alarm_wed, String alarm_thu,
			String alarm_fri, String alarm_week_num, String alarm_week_repeat) {
		this._id = id;
		this._event_id = event_id;
		this._alarm_time = alarm_time;
		this._alarm_sat = alarm_sat;
		this._alarm_sun = alarm_sun;
		this._alarm_mon = alarm_mon;
		this._alarm_tue = alarm_tue;
		this._alarm_wed = alarm_wed;
		this._alarm_thu = alarm_thu;
		this._alarm_fri = alarm_fri;
		this._alarm_week_num = alarm_week_num;
		this._alarm_week_repeat = alarm_week_repeat;
	}

	public EventAlarm(String event_id, String alarm_time, String alarm_sat,
			String alarm_sun, String alarm_mon, String alarm_tue,
			String alarm_wed, String alarm_thu, String alarm_fri,
			String alarm_week_num, String alarm_week_repeat) {
		this._event_id = event_id;
		this._alarm_time = alarm_time;
		this._alarm_sat = alarm_sat;
		this._alarm_sun = alarm_sun;
		this._alarm_mon = alarm_mon;
		this._alarm_tue = alarm_tue;
		this._alarm_wed = alarm_wed;
		this._alarm_thu = alarm_thu;
		this._alarm_fri = alarm_fri;
		this._alarm_week_num = alarm_week_num;
		this._alarm_week_repeat = alarm_week_repeat;
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public String get_event_id() {
		return _event_id;
	}

	public void set_event_id(String _event_id) {
		this._event_id = _event_id;
	}

	public String get_alarm_time() {
		return _alarm_time;
	}

	public void set_alarm_time(String _alarm_time) {
		this._alarm_time = _alarm_time;
	}

	public String get_alarm_sat() {
		return _alarm_sat;
	}

	public void set_alarm_sat(String _alarm_sat) {
		this._alarm_sat = _alarm_sat;
	}

	public String get_alarm_sun() {
		return _alarm_sun;
	}

	public void set_alarm_sun(String _alarm_sun) {
		this._alarm_sun = _alarm_sun;
	}

	public String get_alarm_mon() {
		return _alarm_mon;
	}

	public void set_alarm_mon(String _alarm_mon) {
		this._alarm_mon = _alarm_mon;
	}

	public String get_alarm_tue() {
		return _alarm_tue;
	}

	public void set_alarm_tue(String _alarm_tue) {
		this._alarm_tue = _alarm_tue;
	}

	public String get_alarm_wed() {
		return _alarm_wed;
	}

	public void set_alarm_wed(String _alarm_wed) {
		this._alarm_wed = _alarm_wed;
	}

	public String get_alarm_thu() {
		return _alarm_thu;
	}

	public void set_alarm_thu(String _alarm_thu) {
		this._alarm_thu = _alarm_thu;
	}

	public String get_alarm_fri() {
		return _alarm_fri;
	}

	public void set_alarm_fri(String _alarm_fri) {
		this._alarm_fri = _alarm_fri;
	}

	public String get_alarm_week_num() {
		return _alarm_week_num;
	}

	public void set_alarm_week_num(String _alarm_week_num) {
		this._alarm_week_num = _alarm_week_num;
	}

	public String get_alarm_week_repeat() {
		return _alarm_week_repeat;
	}

	public void set_alarm_week_repeat(String _alarm_week_repeat) {
		this._alarm_week_repeat = _alarm_week_repeat;
	}

}
