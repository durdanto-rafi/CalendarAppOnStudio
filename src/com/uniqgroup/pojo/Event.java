package com.uniqgroup.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by hp 240 on 11/8/2015.
 */

public class Event implements Parcelable{

    //private variables
    int _id;
    String _logo;
    String _description;
    int _user_id;
    String _status;
    String _alarm;
    String _daily_prize;
    String _weekly_prize;


    // Empty constructor
    public Event(){

    }
    // constructor
    public Event(int id, String logo, String description, String status, int user_id, 
    		String alarm,String daily_prize,String weekly_prize){
        this._id = id;
        this._logo = logo;
        this._description = description;
        this._status = status;
        this._user_id = user_id;
        this._alarm = alarm;
        this._daily_prize = daily_prize;
        this._weekly_prize = weekly_prize;
    }


    // constructor
    public Event( String logo, String description, String status, int user_id, String alarm, String daily_prize,String weekly_prize){
        this._logo = logo;
        this._description = description;
        this._status = status;
        this._user_id = user_id;
        this._alarm = alarm;
        this._daily_prize = daily_prize;
        this._weekly_prize = weekly_prize;
    }


    // getting Type
    public String getStatus(){
        return this._status;
    }

    // setting type
    public void setStatus(String status){
        this._status = status;
    }

    // getting Type
    public String getLogo(){
        return this._logo;
    }

    // setting type
    public void setLogo(String logo){
        this._logo = logo;
    }

    

    // getting profilepic
    public String getDescription(){
        return this._description;
    }


    // setting profile pic
    public void setDescription(String description){
        this._description = description;
    }

    // getting ID
    public int getUserID(){
        return this._user_id;
    }

    // setting id
    public void setUserID(int user_id){
        this._user_id = user_id;
    }

    // getting name
    public int getID(){
        return this._id;
    }

    // setting name
    public void setID(int id){
        this._id = id;
    }
    
    public String get_alarm() {
		return _alarm;
	}
	public void set_alarm(String _alarm) {
		this._alarm = _alarm;
	}
	
	public String get_daily_prize() {
		return _daily_prize;
	}
	public void set_daily_prize(String _daily_prize) {
		this._daily_prize = _daily_prize;
	}
	public String get_weekly_prize() {
		return _weekly_prize;
	}
	public void set_weekly_prize(String _weekly_prize) {
		this._weekly_prize = _weekly_prize;
	}
    
    @Override
    public int describeContents() {
        return 0;
    }
  
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(_id);
        dest.writeString(_logo);
        dest.writeString(_description);
        dest.writeInt(_user_id);
        dest.writeString(_status);
        dest.writeString(_alarm);
        dest.writeString(_daily_prize);
        dest.writeString(_weekly_prize);
    }
    
    
    public static final Parcelable.Creator<Event> CREATOR
    	= new Parcelable.Creator<Event>() {
    	public Event createFromParcel(Parcel in) {
    		return new Event(in);
    	}

    	public Event[] newArray(int size) {
			return new Event[size];
    	}
    };
   
    
    private Event(Parcel in) {
         _id = in.readInt();
         _logo = in.readString();
         _description = in.readString();
         _user_id = in.readInt();
         _status = in.readString();
         _alarm = in.readString();
         _daily_prize = in.readString();
         _weekly_prize = in.readString();
    }
    
}