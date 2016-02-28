package com.uniqgroup.pojo;

/**
 * Created by hp 240 on 11/9/2015.
 */
public class EventAudVid {


    int _id;
    String _av_path;
    String _type;//audio/video
    int _event_id;


    
    // Empty constructor
    public EventAudVid(){

    }
    // constructor
    public EventAudVid(int id, String av_path, String type, int event_id){
        this._id = id;
        this._av_path = av_path;
        this._type = type;
        this._event_id = event_id;
    }

    // constructor
    public EventAudVid(String av_path, String type, int event_id){
        this._av_path = av_path;
        this._type = type;
        this._event_id = event_id;
    }

    // getting Id
    public int getID(){
        return this._id;
    }
    // setting av_path
    public void setID(int id){
        this._id = id;
    }

    // getting av_path
    public String getAvPath(){
        return this._av_path;
    }
    // setting av_path
    public void setAvPath(String av_path){
        this._av_path = av_path;
    }

    // getting type
    public String getType(){
        return this._type;
    }
    // setting type
    public void setType(String type){
        this._type = type;
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
