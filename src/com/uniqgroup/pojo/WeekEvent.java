package com.uniqgroup.pojo;


import android.os.Parcel;
import android.os.Parcelable;

public class WeekEvent implements Parcelable {

	int eventId;
	String eventLogo;
	String eventTTS;
	int select_mode;

	public WeekEvent() {

	}

	public WeekEvent(int eventId, String eventLogo, String eventTTS, int select_mode) {

		this.eventId = eventId;
		this.eventLogo = eventLogo;
		this.eventTTS = eventTTS;
		this.select_mode = select_mode;
	}

	public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

	public String getEventLogo() {
		return eventLogo;
	}

	public void setEventLogo(String eventLogo) {
		this.eventLogo = eventLogo;
	}

	public String getEventTTS() {
		return eventTTS;
	}

	public void setEventTTS(String eventTTS) {
		this.eventTTS = eventTTS;
	}

	@Override
	public int describeContents() {

		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {

		dest.writeInt(eventId);
		dest.writeString(eventLogo);
		dest.writeString(eventTTS);
		dest.writeInt(select_mode);

	}

	public static final Parcelable.Creator<WeekEvent> CREATOR = new Parcelable.Creator<WeekEvent>() {
		public WeekEvent createFromParcel(Parcel in) {
			return new WeekEvent(in);
		}

		public WeekEvent[] newArray(int size) {
			return new WeekEvent[size];
		}
	};

	private WeekEvent(Parcel in) {
		eventId = in.readInt();
		eventLogo = in.readString();
		eventTTS = in.readString();
		select_mode = in.readInt();

	}

	public int get_select_mode() {
		return select_mode;
	}

	public void set_select_mode(int _select_mode) {
		this.select_mode = _select_mode;
	}

}
