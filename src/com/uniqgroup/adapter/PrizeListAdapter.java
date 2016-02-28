package com.uniqgroup.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.uniqgroup.application.R;
import com.uniqgroup.application.UserEventListActivity;
import com.uniqgroup.database.DatabaseHandler;
import com.uniqgroup.pojo.Prize;
import com.uniqgroup.utility.CalenderDateList;
import com.uniqgroup.utility.ImageProcessing;

public class PrizeListAdapter extends BaseAdapter {

	ArrayList<Prize> prizeList;
	Context ctx;
	LayoutInflater inflater;

	DatabaseHandler db;
	ImageProcessing imgProc;
	UserEventListActivity uela;

	public PrizeListAdapter(Context context, ArrayList<Prize> prizeList) {
		this.prizeList = prizeList;
		this.ctx = context;
		this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.db = new DatabaseHandler(ctx);

		this.imgProc = new ImageProcessing(ctx);
		this.uela = (UserEventListActivity) ctx;
	}

	@Override
	public int getCount() {
		return prizeList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	static class ViewHolder {
		public ImageView ivActivity, ivMon, ivTue, ivWed, ivThu, ivFri, ivSat,
				ivSun, ivWeeklyPrize;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		// View v ;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.seven_days, null);

			holder.ivActivity = (ImageView) convertView
					.findViewById(R.id.iv_activity);
			holder.ivMon = (ImageView) convertView.findViewById(R.id.day_one);
			holder.ivTue = (ImageView) convertView.findViewById(R.id.day_two);
			holder.ivWed = (ImageView) convertView.findViewById(R.id.day_three);
			holder.ivThu = (ImageView) convertView.findViewById(R.id.day_four);
			holder.ivFri = (ImageView) convertView.findViewById(R.id.day_five);
			holder.ivSat = (ImageView) convertView.findViewById(R.id.day_six);
			holder.ivSun = (ImageView) convertView.findViewById(R.id.day_seven);
			holder.ivWeeklyPrize = (ImageView) convertView
					.findViewById(R.id.iv_prize);
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		imgProc.setImageWith_loader(holder.ivActivity, prizeList.get(position)
				.get_logo());
		holder.ivActivity.setBackgroundResource(R.drawable.round_corner_purple_prize);

		holder.ivMon.setImageResource(setInfo(prizeList.get(position)
				.get_Mon(), "Mon"));
		holder.ivTue.setImageResource(setInfo(prizeList.get(position)
				.get_Tue(), "Tue"));
		holder.ivWed.setImageResource(setInfo(prizeList.get(position)
				.get_Wed(), "Wed"));
		holder.ivThu.setImageResource(setInfo(prizeList.get(position)
				.get_Thu(), "Thu"));
		holder.ivFri.setImageResource(setInfo(prizeList.get(position)
				.get_Fri(), "Fri"));
		holder.ivSat.setImageResource(setInfo(prizeList.get(position)
				.get_Sat(), "Sat"));
		holder.ivSun.setImageResource(setInfo(prizeList.get(position)
				.get_Sun(), "Sun"));

		if (prizeList.get(position).get_weekly_prize() != null) 
		{
			imgProc.setImageWith_loader(holder.ivWeeklyPrize, prizeList.get(position).get_weekly_prize());
			holder.ivWeeklyPrize.setBackgroundResource(R.drawable.round_corner_purple_prize);
		}
		return convertView;
	}

	/** Setting Prize icons 0 -> Future/Past Activity, 1-> Activity Done, N -> No Activity of that day **/
	private int setInfo(String data, String day) {
		int value = 0;
		switch (data) {
		case "0":
			if (CalenderDateList.getDayValue(day) < CalenderDateList.getDayValue(CalenderDateList.getCurrentWeekDay())) 
			{
				value = R.drawable.img_red_sad_face;
			} 
			else 
			{
				value = R.drawable.ic_week_token_normal;
			}
			break;
		case "1":
			value = R.drawable.img_green_happy_face;
			break;
		case "N":
			value = R.drawable.bg_week_token_disabled;
			break;

		default:
			break;
		}

		return value;
	}

}
