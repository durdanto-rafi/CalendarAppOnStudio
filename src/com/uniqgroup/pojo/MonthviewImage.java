package com.uniqgroup.pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class MonthviewImage implements Parcelable {
	
	int _id;
	String  _week;
	String  _day;
	String _image;
	int _user_id;
	
	public MonthviewImage() {

	}
	
	public MonthviewImage(int id, String  week, String  day, String image, int user_id) {
		this._id = id;
		this._week = week;
		this._day = day;
		this._image = image;
		this._user_id = user_id;
	}
	
	public MonthviewImage(String  week, String  day, String image, int user_id) {
		this._week = week;
		this._day = day;
		this._image = image;
		this._user_id = user_id;
	}

	/**
	 * @return the _id
	 */
	public int get_id() {
		return _id;
	}

	/**
	 * @param _id the _id to set
	 */
	public void set_id(int _id) {
		this._id = _id;
	}

	/**
	 * @return the _week
	 */
	public String get_week() {
		return _week;
	}

	/**
	 * @param _week the _week to set
	 */
	public void set_week(String _week) {
		this._week = _week;
	}

	/**
	 * @return the _day
	 */
	public String get_day() {
		return _day;
	}

	/**
	 * @param _day the _day to set
	 */
	public void set_day(String _day) {
		this._day = _day;
	}

	/**
	 * @return the _image
	 */
	public String get_image() {
		return _image;
	}

	/**
	 * @param _image the _image to set
	 */
	public void set_image(String _image) {
		this._image = _image;
	}

	/**
	 * @return the _user_id
	 */
	public int get_user_id() {
		return _user_id;
	}

	/**
	 * @param _user_id the _user_id to set
	 */
	public void set_user_id(int _user_id) {
		this._user_id = _user_id;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(_id);
		dest.writeString(_week);
		dest.writeString(_day);
		dest.writeString(_image);
		dest.writeInt(_user_id);
	}
	
	 
	private MonthviewImage(Parcel in) {
        _id = in.readInt();
        _week = in.readString();
        _day = in.readString();
        _image = in.readString();
        _user_id = in.readInt();
   }
	
	public static final Parcelable.Creator<MonthviewImage> CREATOR = new Parcelable.Creator<MonthviewImage>() 
	{
		public MonthviewImage createFromParcel(Parcel in) 
		{
			return new MonthviewImage(in);
		}
	
		public MonthviewImage[] newArray(int size) 
		{
			return new MonthviewImage[size];
		}
	};
	
}
