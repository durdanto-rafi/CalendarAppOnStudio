package com.uniqgroup.pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class AfterNoonEvent implements Parcelable{
	
	int _event_id;
	String _status;
	String _logo;
	String _time;
	String tts_text;
	int _select_mode;
	
	public AfterNoonEvent() {

	}

	public AfterNoonEvent(int id, String status, String logo, String time,
			String text, int selectMode) {

		this._event_id = id;
		this._logo = logo;
		this._status = status;
		this._time = time;
		this.tts_text = text;
		this._select_mode = selectMode;
	}

	public int get_event_id() {
		return _event_id;
	}

	public String get_status() {
		return _status;
	}

	public String get_logo() {
		return _logo;
	}

	public void set_logo(String _logo) {
		this._logo = _logo;
	}

	public String get_time() {
		return _time;
	}
	public String get_tts() {
		return tts_text;
	}
	public int get_select_mode() {
		return _select_mode;
	}

	// ///********************************************//////////////////////
	public void set_time(String _time) {
		this._time = _time;
	}

	public void set_tts(String tts) {
		this.tts_text = tts;
	}

	public void set_status(String _status) {
		this._status = _status;
	}

	public void set_event_id(int _event_id) {
		this._event_id = _event_id;
	}

	public void set_select_mode(int _select_mode) {
		this._select_mode = _select_mode;
	}
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeInt(_event_id);
		dest.writeString(_status);
		dest.writeString(_logo);
		dest.writeString(_time);
		dest.writeString(tts_text);
		dest.writeInt(_select_mode);
	}

	public static final Parcelable.Creator<AfterNoonEvent> CREATOR = new Parcelable.Creator<AfterNoonEvent>() {
		public AfterNoonEvent createFromParcel(Parcel in) {
			return new AfterNoonEvent(in);
		}

		public AfterNoonEvent[] newArray(int size) {
			return new AfterNoonEvent[size];
		}
	};

	private AfterNoonEvent(Parcel in) {
		_event_id = in.readInt();
		_status = in.readString();
		_logo = in.readString();
		_time = in.readString();
		tts_text = in.readString();
		_select_mode = in.readInt();
	}
}
