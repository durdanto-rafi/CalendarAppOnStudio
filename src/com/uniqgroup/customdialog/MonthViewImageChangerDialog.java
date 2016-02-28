package com.uniqgroup.customdialog;

import java.util.ArrayList;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.uniqgroup.adapter.MonthImageChangerAdapter;
import com.uniqgroup.application.R;
import com.uniqgroup.application.UserEventListActivity;
import com.uniqgroup.database.DatabaseHandler;
import com.uniqgroup.fragment.MonthViewFragment;
import com.uniqgroup.pojo.MonthviewImage;
import com.uniqgroup.pojo.WeekEvent;

public class MonthViewImageChangerDialog extends Dialog implements
		android.view.View.OnClickListener {

	ArrayList<WeekEvent> lstMonthImage;

	DatabaseHandler db;

	UserEventListActivity uela;
	MonthViewFragment monthView;
	Context ctx;
	GridView gvMonthImageChanger;
	ImageView ivMonthOk, ivMonthClose;
	int userId;
	String day;
	String weekNo;
	MonthviewImage monthViewImage;
	WeekEvent event = new WeekEvent();

	public MonthViewImageChangerDialog(UserEventListActivity act,
			MonthViewFragment monthViewFragment, int userId, String day,
			String weekNo) {
		super(act, R.style.CustomAlertDialog);
		uela = act;
		this.monthView = monthViewFragment;
		this.userId = userId;
		this.day = day;
		this.weekNo = weekNo;
		this.ctx = act;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		DisplayMetrics metrics = uela.getResources().getDisplayMetrics();
		int screenWidth = (int) (metrics.widthPixels * 0.20);
		int screenHeight = (int) (metrics.heightPixels * 0.20);
		getWindow().setLayout(screenWidth, screenHeight);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		setContentView(R.layout.month_view_image_changer_dialog);
		setCancelable(false);

		db = new DatabaseHandler(ctx);
		gvMonthImageChanger = (GridView) findViewById(R.id.gvMonthImageChanger);
		ivMonthOk = (ImageView) findViewById(R.id.ivMonthOk);
		ivMonthClose = (ImageView) findViewById(R.id.ivMonthClose);
		ivMonthOk.setOnClickListener(this);
		ivMonthClose.setOnClickListener(this);

		lstMonthImage = new ArrayList<WeekEvent>();
		lstMonthImage = db.weekEventList(day, weekNo, userId);

		if (lstMonthImage != null) {
			MonthImageChangerAdapter adapter = new MonthImageChangerAdapter(
					ctx, lstMonthImage);
			gvMonthImageChanger.setAdapter(adapter);
		}

		gvMonthImageChanger.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				event = lstMonthImage.get(position);
				// Toast.makeText(ctx,
				// String.valueOf(position)+" "+event.getEventLogo()
				// ,Toast.LENGTH_SHORT).show();

				monthViewImage = new MonthviewImage();
				monthViewImage.set_week(weekNo);
				monthViewImage.set_day(day);
				monthViewImage.set_image(event.getEventLogo());
				monthViewImage.set_user_id(userId);
			}
		});
		FullScreencall();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ivMonthOk:

			// Toast.makeText(ctx,
			// String.valueOf(db.addUpdateMonthView(monthViewImage))
			// ,Toast.LENGTH_SHORT).show();
			if (event.getEventLogo() != null) {
				uela.FullScreencall();
				db.addUpdateMonthView(monthViewImage);
				uela.reloadMonthView();
				uela.isMVICDialog = false;
				dismiss();
			} else {
				Toast.makeText(ctx, "Please make a selection first !",
						Toast.LENGTH_SHORT).show();
			}

			break;
		case R.id.ivMonthClose:
			uela.FullScreencall();
			uela.isMVICDialog = false;
			dismiss();
			break;
		default:
			break;
		}

	}

	public void FullScreencall() {
		if (Build.VERSION.SDK_INT < 19) {
			View v = this.getWindow().getDecorView();
			v.setSystemUiVisibility(View.GONE);
		} else {
			// for lower api versions.
			View decorView = getWindow().getDecorView();
			int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
					| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
			decorView.setSystemUiVisibility(uiOptions);
		}
	}

}
