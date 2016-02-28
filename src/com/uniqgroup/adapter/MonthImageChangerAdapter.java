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
import com.uniqgroup.pojo.WeekEvent;
import com.uniqgroup.utility.ImageProcessing;

public class MonthImageChangerAdapter extends BaseAdapter {

	private Context mContext;
	LayoutInflater inflater;
	private ImageProcessing imgProc;
	private DatabaseHandler db;
	private int[] imageid;

	UserEventListActivity activity;

	ArrayList<WeekEvent> lstMonthImage;

	public MonthImageChangerAdapter(Context context,
			ArrayList<WeekEvent> lstMonthImage) {
		mContext = context;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		imgProc = new ImageProcessing(mContext);

		this.lstMonthImage = lstMonthImage;
	}

	@Override
	public int getCount() {

		return lstMonthImage.size();
	}

	@Override
	public Object getItem(int arg0) {

		return null;
	}

	@Override
	public long getItemId(int arg0) {

		return 0;
	}

	static class ViewHolder {

		public ImageView ivMonthImage;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder = null;
		activity = new UserEventListActivity();
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.month_view_row, null);
			holder.ivMonthImage = (ImageView) convertView
					.findViewById(R.id.iv_month_pro_pic);
			convertView.setTag(holder);

		} else {

			holder = (ViewHolder) convertView.getTag();
		}

		WeekEvent weekEvent = new WeekEvent();
		weekEvent = lstMonthImage.get(position);
		imgProc.setImageWith_loader(holder.ivMonthImage,
				weekEvent.getEventLogo());

		return convertView;
	}

}
