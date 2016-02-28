package com.uniqgroup.pojo;

/**
 * Created by hp 240 on 11/9/2015.
 */
public class EventSequence {

	int _id;
	String _seq_image;
	String _seq_path;
	String _seq_type;// image/audio/video
	String _status;// done/notdone
	String _tts;
	int _seq_position = 0;
	int _seq_timer = 0;
	int _event_id;

	// Empty constructor
	public EventSequence() {

	}

	// constructor
	public EventSequence(int id, String seq_image, String seq_path,
			String seq_type, String status, String tts,int seq_timer, int event_id, int seq_position) {
		this._id = id;
		this._seq_image = seq_image;
		this._seq_path = seq_path;
		this._seq_type = seq_type;
		this._status = status;
		this._tts = tts;
		this._seq_timer = seq_timer;
		this._event_id = event_id;
		this._seq_position = seq_position;
	}

	// constructor
	public EventSequence(String seq_image, String seq_path, String seq_type,
			String status, String tts,int seq_timer, int event_id, int seq_position) {
		this._seq_image = seq_image;
		this._seq_path = seq_path;
		this._seq_type = seq_type;
		this._status = status;
		this._tts = tts;
		this._seq_timer = seq_timer;
		this._event_id = event_id;
		this._seq_position = seq_position;
	}

	
	// getting seq_timer
	public String getTts() {
		return this._tts;
	}

	// setting seq_timer
	public void setTts(String tts) {
		this._tts = tts;
	}

	

	// getting seq_timer
	public String getSeqType() {
		return this._seq_type;
	}

	// setting seq_timer
	public void setSeqType(String seq_type) {
		this._seq_type = seq_type;
	}

	// getting seq_timer
	public int getSeqTimer() {
		return this._seq_timer;
	}

	/**
	 * @return the _seq_position
	 */
	public int get_seq_position() {
		return _seq_position;
	}

	/**
	 * @param _seq_position the _seq_position to set
	 */
	public void set_seq_position(int _seq_position) {
		this._seq_position = _seq_position;
	}

	// setting seq_timer
	public void setSeqTimer(int seq_timer) {
		this._seq_timer = seq_timer;
	}

	// getting Id
	public int getID() {
		return this._id;
	}

	// setting seq_image
	public void setID(int id) {
		this._id = id;
	}

	// getting seq_image
	public String getSeqImg() {
		return this._seq_image;
	}

	// setting seq_image
	public void setSeqImg(String seq_image) {
		this._seq_image = seq_image;
	}

	// getting seq_title
	public String getSeqPath() {
		return this._seq_path;
	}

	// setting seq_title
	public void setSeqPath(String seq_path) {
		this._seq_path = seq_path;
	}

	// getting seq_title
	public String getStatus() {
		return this._status;
	}

	// setting seq_title
	public void setStatus(String status) {
		this._status = status;
	}

	// getting Event Id
	public int getEventId() {
		return this._event_id;
	}

	// setting Event Id
	public void setEventId(int event_id) {
		this._event_id = event_id;
	}
}
