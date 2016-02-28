package com.uniqgroup.utility;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.uniqgroup.application.R;
import com.uniqgroup.pojo.EventSequence;

/**
 * @author Uniq
 *
 */

@SuppressLint("NewApi")
public class Custom_cell_Day_work extends RelativeLayout {

	ImageView tick_pic_IV;
	ImageView work_pic_IV;
	final Context ctx;
	Bitmap bitmapToSet;
	View main_view_container, stick_hanger_view;
	TextView roundHanger_tv, status_textV; // used for round shape
	EventSequence events;
	String tts_text;

	public Custom_cell_Day_work(Context context, Bitmap picture,
			EventSequence events, String tts) {
		super(context);
		ctx = context;
		bitmapToSet = picture;
		this.events = events;
		tts_text = tts;
		init(context);
		// TODO Auto-generated constructor stub
	}

	/** CALLED FROM ACTIVITY *******************/
	public void changeText_To_pic(int state) {
		/** changes the text with the state **/
		switch (state) {
		case 1:
			break;
		case 2:
			break;
		case 3:
			break;
		}
	}

	/** replace the data and create the cell again **/
	public void replaceAllData(Context context, Bitmap picture,
			EventSequence events, String tts) {
		bitmapToSet = picture;
		this.events = events;
		tts_text = tts;
		init(context);
	}

	public EventSequence getThe_datamodel_fromView() {
		return events;
	}

	/** DONE ***********************/

	public Custom_cell_Day_work(Context context, AttributeSet attrs) {
		super(context, attrs);
		// timerTime=Time;
		init(context);
		ctx = context;
		// TODO Auto-generated constructor stub
	}

	public Custom_cell_Day_work(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// timerTime=Time;
		init(context);
		ctx = context;
	}

	/** SETUP ALL THE UI RELATED WORKS *************************/
	public void init(final Context ctx) {
		LayoutInflater inflater = (LayoutInflater) ctx
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflater.inflate(R.layout.custom_cell_day_work, null);

		/** control the states with these layouts **/
		main_view_container = (LinearLayout) v.findViewById(R.id.container_linear_cell1);
		stick_hanger_view = (View) v.findViewById(R.id.stick_hanger_cell1);
		roundHanger_tv = (TextView) v.findViewById(R.id.round_hanger_cell1);

		status_textV = (TextView) v.findViewById(R.id.status_customcell_textV1);
		work_pic_IV = (ImageView) v
				.findViewById(R.id.workpic_customcellDaywork_IV1);
		if (bitmapToSet != null)
			work_pic_IV.setImageBitmap(bitmapToSet);

		tick_pic_IV = (ImageView) v
				.findViewById(R.id.tickpicc_customcellDaywork_IV1);

		//controlling the visibility of textview and action IV
		if (events != null) {

			if (events.getStatus().equals("0") || events.getStatus().equals("2") ||events.getStatus() == null) {
				status_textV.setVisibility(View.VISIBLE);
				if (tts_text != null)
					status_textV.setText(tts_text);
				else {
					status_textV.setText(tts_text);
				}
			} else if (events.getStatus().equals("1")
					) {
				status_textV.setVisibility(View.INVISIBLE);
			}

		}
		// tick_pic_IV.setImageResource(R.drawable.);

		addView(v);
	}

	/** STATE CONTROLLER *****************************/
	// normal state 0=purple, 1=green,2=red
	public void setState(int state) {
		switch (state) {
		case 1:
			main_view_container
					.setBackgroundResource(R.drawable.round_corner_green);
			stick_hanger_view.setBackgroundResource(R.color.green);
			roundHanger_tv.setBackgroundResource(R.drawable.round_shape_green);

			// controlling bottom part
			/*tick_pic_IV.setImageResource(R.drawable.img_green_happy_face);
			tick_pic_IV.setVisibility(View.VISIBLE);
			status_textV.setVisibility(View.INVISIBLE);*/
			break;
		case 2:
			main_view_container
					.setBackgroundResource(R.drawable.round_corner_red);
			stick_hanger_view.setBackgroundResource(R.color.red);
			roundHanger_tv.setBackgroundResource(R.drawable.round_shape_red);

			/*tick_pic_IV.setImageResource(R.drawable.img_red_sad_face);
			tick_pic_IV.setVisibility(View.VISIBLE);
			status_textV.setVisibility(View.INVISIBLE);*/
			break;

		default:
			break;
		}
	}

}
