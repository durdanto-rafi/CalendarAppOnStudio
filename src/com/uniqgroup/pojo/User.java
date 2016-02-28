package com.uniqgroup.pojo;

/**
 * Created by hp 240 on 11/8/2015.
 */

public class User {

    //private variables
    int _id;
    String _name;
    String _password;
    String _profilepic;
    String _type;
    String _default_view;
    String _alarm;

    // Empty constructor
    public User(){

    }
    // constructor
    public User(int id, String name, String password, String profilepic, String type, String default_view, String alarm){
        this._id = id;
        this._name = name;
        this._password = password;
        this._profilepic = profilepic;
        this._type = type;
        this._default_view = default_view;
        this._alarm = alarm;
    }

    // constructor
    public User(String name, String password, String profilepic, String type, String default_view, String alarm){
        this._name = name;
        this._password = password;
        this._profilepic = profilepic;
        this._type = type;
        this._default_view = default_view;
        this._alarm = alarm;
    }



    // getting Type
    public String getType(){
        return this._type;
    }

    // setting type
    public void setType(String type){
        this._type = type;
    }


    // getting profilepic
    public String getProfilePic(){
        return this._profilepic;
    }


    // setting profile pic
    public void setProfilePic(String profilePic){
        this._profilepic = profilePic;
    }




    // getting ID
    public int getID(){
        return this._id;
    }

    // setting id
    public void setID(int id){
        this._id = id;
    }




    // getting name
    public String getName(){
        return this._name;
    }

    // setting name
    public void setName(String name){
        this._name = name;
    }

    // getting phone number
    public String getPassword(){
        return this._password;
    }

    // setting phone number
    public void setPassword(String password){
    	
    	String hash = null;
        try{
        	password=   PasswordHash.createHash( password );
            
        }catch (Exception e){ e.printStackTrace();}
        this._password = password;
    }
	/**
	 * @return the _default_view
	 */
	public String get_default_view() {
		return _default_view;
	}
	/**
	 * @param _default_view the _default_view to set
	 */
	public void set_default_view(String _default_view) {
		this._default_view = _default_view;
	}
	/**
	 * @return the _alarm
	 */
	public String get_alarm() {
		return _alarm;
	}
	/**
	 * @param _alarm the _alarm to set
	 */
	public void set_alarm(String _alarm) {
		this._alarm = _alarm;
	}
}