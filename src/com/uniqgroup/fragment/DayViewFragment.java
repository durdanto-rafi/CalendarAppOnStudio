package com.uniqgroup.fragment;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.uniqgroup.adapter.DayEventAfternoonListAdapter;
import com.uniqgroup.adapter.DayEventEveningListAdapter;
import com.uniqgroup.adapter.DayEventMorningListAdapter;
import com.uniqgroup.application.R;
import com.uniqgroup.application.UserEventListActivity;
import com.uniqgroup.database.DatabaseHandler;
import com.uniqgroup.pojo.AfterNoonEvent;
import com.uniqgroup.pojo.EveningEvent;
import com.uniqgroup.pojo.MorningEvent;

@SuppressLint("NewApi")
public class DayViewFragment extends Fragment {

	ArrayList<MorningEvent> morni_list;
	ArrayList<AfterNoonEvent> afterni_list;
	ArrayList<EveningEvent> eveningi_list;

	DatabaseHandler db;
	ListView list_view_morning, list_view_afternoon, list_view_evening;
	DayEventMorningListAdapter day_event_morn_adapter;
	DayEventAfternoonListAdapter day_event_after_adapter;
	DayEventEveningListAdapter day_event_evening_adapter;
	public ImageView ivCurrentSelected, ivPreviousSelected = null;

	public final static String MORNING_LIST = "morning_list";
	public final static String EVENING_LIST = "evenng_list";
	public final static String AFTERNOON_LIST = "afternoon_list";

	public final static String EVENT_LIST_ACTIVITY = "event_list_activity";

	UserEventListActivity event_list_activity_ref;

	public static DayViewFragment getInstance(
			UserEventListActivity event_list_activity_ref,
			ArrayList<MorningEvent> morning_list,
			ArrayList<AfterNoonEvent> aftern_list,
			ArrayList<EveningEvent> evening_list) {

		DayViewFragment fragment = new DayViewFragment();
		Bundle bd = new Bundle();
		// bd.putSerializable(EVENT_LIST_ACTIVITY, event_list_activity_ref);
		bd.putParcelableArrayList(MORNING_LIST, morning_list);
		bd.putParcelableArrayList(AFTERNOON_LIST, aftern_list);
		bd.putParcelableArrayList(EVENING_LIST, evening_list);
		fragment.setArguments(bd);
		return fragment;

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle bd = getArguments();

		event_list_activity_ref = (UserEventListActivity) getActivity();

		morni_list = bd.getParcelableArrayList(MORNING_LIST);
		afterni_list = bd.getParcelableArrayList(AFTERNOON_LIST);
		eveningi_list = bd.getParcelableArrayList(EVENING_LIST);

		day_event_morn_adapter = new DayEventMorningListAdapter(getActivity(),
				morni_list);
		day_event_after_adapter = new DayEventAfternoonListAdapter(
				getActivity(), afterni_list);
		day_event_evening_adapter = new DayEventEveningListAdapter(
				getActivity(), eveningi_list);

		db = new DatabaseHandler(getActivity());
	}

	public void callNotifyAfternoon(ArrayList<AfterNoonEvent> aftern_list){
		day_event_after_adapter.changeData(aftern_list);
		day_event_after_adapter.notifyDataSetChanged();
	}
	
	public void callNotifyEvening(ArrayList<EveningEvent> evening_list){
		day_event_evening_adapter.changeData(evening_list);
		day_event_evening_adapter.notifyDataSetChanged();
	}
	
	public void callNotifyMorning(ArrayList<MorningEvent> morning_list){
		day_event_morn_adapter.changeData(morning_list);
		day_event_morn_adapter.notifyDataSetChanged();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.dayview_frag, null);

		list_view_morning = (ListView) v.findViewById(R.id.morning_dayview_lv);
		list_view_afternoon = (ListView) v
				.findViewById(R.id.afternoon_dayview_lv);
		list_view_evening = (ListView) v.findViewById(R.id.evening_dayview_lv);

		System.out.println("Sizes: " + morni_list.size() + ","
				+ eveningi_list.size() + "," + afterni_list.size() + ",");

		list_view_morning.setAdapter(day_event_morn_adapter);

		list_view_afternoon.setAdapter(day_event_after_adapter);

		list_view_evening.setAdapter(day_event_evening_adapter);
		//list_view_morning();
		return v;

	}

	public void list_view_morning() {

		final Context ctx = getActivity();

		list_view_morning.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				TextView tx1 = (TextView) view.findViewById(R.id.tv_1);
				final int event_id = morni_list.get(position).get_event_id();
				String time = morni_list.get(position).get_time();

				// mir
				UserEventListActivity activity = (UserEventListActivity) getActivity();
				activity.speakOut(morni_list.get(position).get_tts());

				final ImageView iv_tck = (ImageView) view
						.findViewById(R.id.iv_2);

				

				iv_tck.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View regProfile) {
						int state = 0;

						if (iv_tck.getDrawable().getConstantState() != ctx
								.getResources()
								.getDrawable(R.drawable.ic_cross)
								.getConstantState()) {
							state = 1;
						}

						// update state
						new DatabaseHandler(ctx).updateEventByStatus(event_id,
								String.valueOf(state));

					}
				});

				/*Intent intent = null;
				if (event_list_activity_ref.isEditMode()) {
					intent = new Intent(ctx, EventManageActivity.class);
				} else {
					if (db.checkEventSeqStatus(event_id)) {
						intent = new Intent(ctx, EventSeqActivity.class);
					} else {
						
						event_list_activity_ref.show_FeedBack_AlertDialog();
						
//						Toast.makeText(ctx, "Actions completed !",
//								Toast.LENGTH_SHORT).show();
						
						return;
					}
				}

				UserEventListActivity uevl = (UserEventListActivity) getActivity();
				intent.putExtra("event_id", event_id); 
				intent.putExtra("user_id", uevl.getCurrentUserId());
				intent.putExtra("flag", "Update");
				intent.putExtra("returnPath", "com.uniqgroup.application.UserEventListActivity");
				intent.putExtra("returnView", CalendarViews.dayView);
				startActivity(intent);*/
			}
		});

		// list_view_morning.setLongClickable(longClickable);

		list_view_afternoon.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				TextView tx1 = (TextView) view.findViewById(R.id.tv_1);
				int event_id = afterni_list.get(position).get_event_id();
				String time = afterni_list.get(position).get_time();

				// mir
				UserEventListActivity activity = (UserEventListActivity) getActivity();
				activity.speakOut(afterni_list.get(position).get_tts());
				// Toast.makeText(getActivity(), String.valueOf(event_id) +
				// " :: "+ time, Toast.LENGTH_LONG).show();

				

				/*Intent intent = null;
				if (event_list_activity_ref.isEditMode()) {
					intent = new Intent(ctx, EventManageActivity.class);
				} else {
					if (db.checkEventSeqStatus(event_id)) {
						intent = new Intent(ctx, EventSeqActivity.class);
					} else {
						
						event_list_activity_ref.show_FeedBack_AlertDialog();
						
//						Toast.makeText(ctx, "Actions completed !",
//								Toast.LENGTH_SHORT).show();
						return;
					}
				}

				UserEventListActivity uevl = (UserEventListActivity) getActivity();
				intent.putExtra("event_id", event_id); // Optional parameters
				intent.putExtra("user_id", uevl.getCurrentUserId());
				intent.putExtra("flag", "Update");
				intent.putExtra("returnPath","com.uniqgroup.application.UserEventListActivity");
				intent.putExtra("returnView", CalendarViews.dayView);
				startActivity(intent);*/
			}
		});

		list_view_evening.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				TextView tx1 = (TextView) view.findViewById(R.id.tv_1);
				int event_id = eveningi_list.get(position).get_event_id();
				String time = eveningi_list.get(position).get_time();

				// Toast.makeText(getActivity(), String.valueOf(event_id) +
				// " :: "+ time, Toast.LENGTH_LONG).show();
				UserEventListActivity activity = (UserEventListActivity) getActivity();
				activity.speakOut(eveningi_list.get(position).get_tts());

				/*Intent intent = null;
				if (event_list_activity_ref.isEditMode()) {
					intent = new Intent(ctx, EventManageActivity.class);
				} else {
					if (db.checkEventSeqStatus(event_id)) {
						intent = new Intent(ctx, EventSeqActivity.class);
					} else {
						
						
						event_list_activity_ref.show_FeedBack_AlertDialog();
						
//						Toast.makeText(ctx, "Actions completed !",
//								Toast.LENGTH_SHORT).show();
						return;
					}
				}*/

				/*UserEventListActivity uevl = (UserEventListActivity) getActivity();
				intent.putExtra("event_id", event_id);
				intent.putExtra("user_id", uevl.getCurrentUserId());
				intent.putExtra("flag", "Update");
				intent.putExtra("returnPath", "com.uniqgroup.application.UserEventListActivity");
				intent.putExtra("returnView", "D");
				ctx.startActivity(intent);
				System.out.println("asdasdasdasd");*/
			}
		});

	}

	// write database related codes here
	public void fetchAlltheData(String user_id) {

	}

	public void checkSelectedActivity(ImageView currentSelected) {
		if (ivCurrentSelected != currentSelected) {
			currentSelected
					.setBackgroundResource(R.drawable.round_corner_purple);
			if (ivPreviousSelected != null) {
				ivPreviousSelected
						.setBackgroundResource(R.drawable.round_corner_ash);
			}
			ivPreviousSelected = currentSelected;
			ivCurrentSelected = currentSelected;
		}
	}

	// Calling async task to fetch data
	public class DatabaseAsyncCall extends AsyncTask<String, Void, Void> {
		ProgressDialog pd;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pd = new ProgressDialog(getActivity());
			pd.setMessage("Please wait");
			pd.setCancelable(false);
			pd.show();
		}

		@Override
		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub
			String userID = params[0];
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			pd.show();

		}
	}

}
