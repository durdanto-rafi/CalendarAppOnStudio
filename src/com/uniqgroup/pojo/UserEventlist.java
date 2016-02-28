package com.uniqgroup.pojo;

/**
 * Created by hp 240 on 11/9/2015.
 */
public class UserEventlist {
    User _user;
    Event _event;

    public UserEventlist(User user, Event event){
        _user = user;
        _event = event;
    }

    public User getUser(){
        return this._user;
    }

    public Event getEvent(){
        return this._event;
    }

}
