package com.uniqgroup.pojo;

/**
 * Created by hp 240 on 11/9/2015.
 */
public class EventOccur {


    int _id;
    String _date;
    String _time;
    int _event_id;
    String _status;////done/not done

    // Empty constructor
    public EventOccur(){

    }
    // constructor
    public EventOccur(int id, String date, String time,String status, int event_id){
        this._id = id;
        this._date = date;
        this._time = time;
        this._status = status;
        this._event_id = event_id;
        
   }

    // constructor
    public EventOccur(String date, String time,String status, int event_id){
        this._date = date;
        this._time = time;
        this._status = status;
        this._event_id = event_id;
    }

    // getting Id
    public int getID(){
        return this._id;
    }
    // setting Date
    public void setID(int id){
        this._id = id;
    }
    
    
    // getting Status
    public int getStatus(){
        return this._id;
    }
    // setting Date
    public void setStatus(String status){
        this._status = status;
    }

    // getting Date
    public String getDate(){
        return this._date;
    }
    // setting Date
    public void setDate(String date){
        this._date = date;
    }

    // getting Time
    public String getTime(){
        return this._time;
    }
    // setting Time
    public void setTime(String time){
        this._time = time;
    }

    // getting Event Id
    public int getEventId(){
        return this._event_id;
    }

    // setting Event Id
    public void setEventId(int event_id){
        this._event_id = event_id;
    }



}
