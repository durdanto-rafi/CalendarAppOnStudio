package com.uniqgroup.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.uniqgroup.application.R;
import com.uniqgroup.application.UserEventListActivity;
import com.uniqgroup.database.DatabaseHandler;
import com.uniqgroup.pojo.EveningEvent;
import com.uniqgroup.utility.ImageProcessing;
import com.uniqgroup.utility.SelectModeStatic;

public class DayEventEveningListAdapter extends BaseAdapter {
	ArrayList<EveningEvent> DataList;
	Context ctx;
	LayoutInflater inflater;
	ImageProcessing imgProc;
	DatabaseHandler db;
	UserEventListActivity uela;
	public DayEventEveningListAdapter(Context context, ArrayList<EveningEvent> list) {
		
		DataList = list;
		ctx = context ;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		imgProc = new ImageProcessing(ctx);
		db = new DatabaseHandler(ctx);
		
		uela= (UserEventListActivity)ctx;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		System.out.println("getCount method");
		return DataList.size();
		
	}
	//not used here
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		System.out.println("getItem method");
		return null;
	}
//not used here
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		System.out.println("getItemId");
		return 0;
	}

	static class ViewHolder {
		public TextView time_tv;
		public ImageView action_IV;
		public ImageView tick_Iv;
	}
	
	public void changeData(ArrayList<EveningEvent> newList)
	{
		DataList=newList;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		//View v ;
		if (convertView == null) {
			holder=new ViewHolder();
			convertView = inflater.inflate(R.layout.day_view_row, null);
			holder.time_tv = (TextView) convertView.findViewById(R.id.tv_1);
			holder.tick_Iv = (ImageView) convertView.findViewById(R.id.iv_2);
			holder.action_IV = (ImageView) convertView.findViewById(R.id.iv_1);
			convertView.setTag(holder);

		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		
		
		holder.time_tv.setText(DataList.get(position).get_time());
		holder.time_tv.setTag(DataList.get(position).get_event_id());
		imgProc.setImageWith_loader(holder.action_IV, DataList.get(position).get_logo());

		int event_stat = Integer.parseInt(DataList.get(position).get_status());
		if(event_stat == 0){
			holder.tick_Iv.setImageResource(R.drawable.ic_tick_disbled);

		}else{
			holder.tick_Iv.setImageResource(R.drawable.ic_tick);

		}
		
		final int event_id = DataList.get(position).get_event_id();
		final String event_tts = DataList.get(position).get_tts();
		final int selectMode = DataList.get(position).get_select_mode();
		
		switch (selectMode) {
		case SelectModeStatic.notSelected:
			holder.action_IV.setBackgroundResource(R.drawable.round_corner_ash);
			break;
		case SelectModeStatic.singleSelected:
			holder.action_IV.setBackgroundResource(R.drawable.round_corner_purple);
			break;
		case SelectModeStatic.multiSelected:
			holder.action_IV.setBackgroundResource(R.drawable.round_corner_blue);
			break;
		}
//		holder.tick_Iv.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				
//				int state = 0;
//				
//				ImageView iv = (ImageView)v;
//				
//				if (iv.getDrawable().getConstantState() != 
//						ctx.getResources().getDrawable( R.drawable.ic_tick_disbled).getConstantState()) 
//			    {
//			      iv.setImageResource(R.drawable.ic_tick_disbled);
//			      
//			    } else{
//			    	state = 1;
//			    	iv.setImageResource(R.drawable.ic_tick);
//			    	
//			    }
//			  
//			 
//				//update state
//				db.updateEventByStatus(event_id,String.valueOf(state));
//				
//			}
//		});
		
		/*if(uela.isSelcted(position,3))
		{
			holder.action_IV.setBackgroundResource(R.drawable.round_corner_purple);
		}
		else
		{
			holder.action_IV.setBackgroundResource(R.drawable.round_corner_ash);
		}*/
		
		/*if(uela.checkEveningList(position))
		{
			uela.setImageViewBorder(holder.action_IV, true, 0);
		}
		else
		{
			uela.setImageViewBorder(holder.action_IV, false, 0);
		}*/
		
		holder.action_IV.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ImageView iv = (ImageView) v;
				//UserEventListActivity uela = (UserEventListActivity)ctx;
				uela.checkSelectedActivity(iv,event_id,event_tts,0,position,3);
			}
		});
		
		holder.action_IV.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				ImageView iv = (ImageView) v;
				//UserEventListActivity uela = (UserEventListActivity)ctx;
				uela.checkSelectedActivity(iv,event_id,event_tts,1,position,3);
				return true;
			}
		});
		
		System.out.println("getView method");
		return convertView;
	}

}
