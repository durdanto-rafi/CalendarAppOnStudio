package com.uniqgroup.pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class AlarmEvent implements Parcelable {

	int _event_id;
	int _user_id;
	String _time;
	String _image;
	
	public AlarmEvent(){
		
	}
	

	public int get_event_id() {
		return _event_id;
	}

	public void set_event_id(int _event_id) {
		this._event_id = _event_id;
	}

	public int get_user_id() {
		return _user_id;
	}


	public void set_user_id(int _user_id) {
		this._user_id = _user_id;
	}
	
	public String get_time() {
		return _time;
	}

	public void set_time(String _time) {
		this._time = _time;
	}
	
	public String get_image() {
		return _image;
	}


	public void set_image(String _image) {
		this._image = _image;
	}
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(_event_id);
		dest.writeInt(_user_id);
		dest.writeString(_time);
		dest.writeString(_image);
		
	}
	
	public static final Parcelable.Creator<AlarmEvent> CREATOR = new Parcelable.Creator<AlarmEvent>() {
		public AlarmEvent createFromParcel(Parcel in) {
			return new AlarmEvent(in);
		}

		public AlarmEvent[] newArray(int size) {
			return new AlarmEvent[size];
		}
	};

	private AlarmEvent(Parcel in) {
		_event_id = in.readInt();
		_user_id = in.readInt();
		_time = in.readString();
		_image = in.readString();
	}


	


	

}
