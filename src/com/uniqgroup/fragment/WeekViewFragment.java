package com.uniqgroup.fragment;

import java.util.ArrayList;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.uniqgroup.adapter.FridayListAdapter;
import com.uniqgroup.adapter.MondayListAdapter;
import com.uniqgroup.adapter.SaturdayListAdapter;
import com.uniqgroup.adapter.SundayListAdapter;
import com.uniqgroup.adapter.ThursdayListAdapter;
import com.uniqgroup.adapter.TuesdayListAdapter;
import com.uniqgroup.adapter.WednesdayListAdapter;
import com.uniqgroup.application.R;
import com.uniqgroup.application.UserEventListActivity;
import com.uniqgroup.database.DatabaseHandler;
import com.uniqgroup.pojo.WeekEvent;

public class WeekViewFragment extends Fragment {

	ArrayList<WeekEvent> monday_list;
	ArrayList<WeekEvent> tuesday_list;
	ArrayList<WeekEvent> wednesday_list;
	ArrayList<WeekEvent> thursday_list;
	ArrayList<WeekEvent> friday_list;
	ArrayList<WeekEvent> saturday_list;
	ArrayList<WeekEvent> sunday_list;

	DatabaseHandler db;

	ListView lstMonday, lstTuesday, lstWednesday, lstThursday, lstFriday,
			lstSaturday, lstSunday;

	MondayListAdapter mondayAdapter;
	TuesdayListAdapter tuesdayAdapter;
	WednesdayListAdapter wednesdayAdapter;
	ThursdayListAdapter thursdayAdapter;
	FridayListAdapter fridayAdapter;
	SaturdayListAdapter saturdayAdapter;
	SundayListAdapter sundayAdapter;

	public final static String MONDAY_LIST = "monday_list";
	public final static String TUESDAY_LIST = "tuesday_list";
	public final static String WEDNESDAY_LIST = "wednesday_list";
	public final static String THURSDAY_LIST = "thursday_list";
	public final static String FRIDAY_LIST = "friday_list";
	public final static String SATURDAY_LIST = "saturday_list";
	public final static String SUNDAY_LIST = "sunday_list";

	public final static String WEEK_EVENT_LIST_ACTIVITY = "event_list_activity";

	UserEventListActivity week_event_list_activity_ref;

	public static WeekViewFragment getInstance(
			UserEventListActivity week_event_list_activity_ref,
			ArrayList<WeekEvent> monday_list,
			ArrayList<WeekEvent> tuesday_list,
			ArrayList<WeekEvent> wednesday_list,
			ArrayList<WeekEvent> thursday_list,
			ArrayList<WeekEvent> friday_list,
			ArrayList<WeekEvent> saturday_list, ArrayList<WeekEvent> sunday_list

	) {

		WeekViewFragment fragmentWeek = new WeekViewFragment();
		Bundle bd = new Bundle();
		// bd.putSerializable(EVENT_LIST_ACTIVITY, event_list_activity_ref);

		bd.putParcelableArrayList(MONDAY_LIST, monday_list);
		bd.putParcelableArrayList(TUESDAY_LIST, tuesday_list);
		bd.putParcelableArrayList(WEDNESDAY_LIST, wednesday_list);
		bd.putParcelableArrayList(THURSDAY_LIST, thursday_list);
		bd.putParcelableArrayList(FRIDAY_LIST, friday_list);
		bd.putParcelableArrayList(SATURDAY_LIST, saturday_list);
		bd.putParcelableArrayList(SUNDAY_LIST, sunday_list);

		fragmentWeek.setArguments(bd);
		return fragmentWeek;

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.week_view_fragment);

		Bundle bundle = getArguments();
		week_event_list_activity_ref = (UserEventListActivity) getActivity();

		monday_list = bundle.getParcelableArrayList(MONDAY_LIST);
		tuesday_list = bundle.getParcelableArrayList(TUESDAY_LIST);
		wednesday_list = bundle.getParcelableArrayList(WEDNESDAY_LIST);
		thursday_list = bundle.getParcelableArrayList(THURSDAY_LIST);
		friday_list = bundle.getParcelableArrayList(FRIDAY_LIST);
		saturday_list = bundle.getParcelableArrayList(SATURDAY_LIST);
		sunday_list = bundle.getParcelableArrayList(SUNDAY_LIST);

		mondayAdapter = new MondayListAdapter(getActivity(), monday_list);
		tuesdayAdapter = new TuesdayListAdapter(getActivity(), tuesday_list);

		wednesdayAdapter = new WednesdayListAdapter(getActivity(),
				wednesday_list);
		thursdayAdapter = new ThursdayListAdapter(getActivity(), thursday_list);
		fridayAdapter = new FridayListAdapter(getActivity(), friday_list);
		saturdayAdapter = new SaturdayListAdapter(getActivity(), saturday_list);
		sundayAdapter = new SundayListAdapter(getActivity(), sunday_list);

		db = new DatabaseHandler(getActivity());

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.week_view_fragment, null);

		lstMonday = (ListView) view.findViewById(R.id.lstMonDay);
		lstTuesday = (ListView) view.findViewById(R.id.lstTuesDay);
		lstWednesday = (ListView) view.findViewById(R.id.lstWednesDay);
		lstThursday = (ListView) view.findViewById(R.id.lstThursDay);
		lstFriday = (ListView) view.findViewById(R.id.lstFriDay);
		lstSaturday = (ListView) view.findViewById(R.id.lstSaturDay);
		lstSunday = (ListView) view.findViewById(R.id.lstSunDay);

		lstMonday.setAdapter(mondayAdapter);
		lstTuesday.setAdapter(tuesdayAdapter);
		lstWednesday.setAdapter(wednesdayAdapter);
		lstThursday.setAdapter(thursdayAdapter);
		lstFriday.setAdapter(fridayAdapter);
		lstSaturday.setAdapter(saturdayAdapter);
		lstSunday.setAdapter(sundayAdapter);

		listViewMonday();
		listViewTuesday();
		listViewWednesday();
		listViewThursday();
		listViewFriday();
		listViewSaturday();
		listViewSunday();

		return view;

	}

	public void listViewMonday() {

		final Context ctx = getActivity();

		/*lstMonday.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				final int eventId = monday_list.get(position).getEventId();

				Intent intent;
				if (week_event_list_activity_ref.isEditMode()) {
					intent = new Intent(ctx, EventManageActivity.class);
				} else {

					if (db.checkEventSeqStatus(eventId)) {
						intent = new Intent(ctx, EventSeqActivity.class);
					} else {

						week_event_list_activity_ref
								.showRestartActiviyAlertDialog(eventId);

						return;
					}

				}

				UserEventListActivity uevl = (UserEventListActivity) getActivity();
				uevl.speakOut(monday_list.get(position).getEventTTS());
				intent.putExtra("event_id", eventId);
				intent.putExtra("user_id", uevl.getCurrentUserId());
				intent.putExtra("flag", "Update");
				intent.putExtra("returnPath",
						"com.uniqgroup.application.UserEventListActivity");
				intent.putExtra("returnView", CalendarViews.weekView);
				startActivity(intent);
			}
		});*/

	}

	public void listViewTuesday() {

		final Context ctx = getActivity();

		/*lstTuesday.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				final int eventId = tuesday_list.get(position).getEventId();
				Intent intent;
				if (week_event_list_activity_ref.isEditMode()) {
					intent = new Intent(ctx, EventManageActivity.class);
				} else {

					if (db.checkEventSeqStatus(eventId)) {
						intent = new Intent(ctx, EventSeqActivity.class);
					} else {

						week_event_list_activity_ref
								.showRestartActiviyAlertDialog(eventId);

						return;
					}

				}

				UserEventListActivity uevl = (UserEventListActivity) getActivity();
				uevl.speakOut(tuesday_list.get(position).getEventTTS());
				intent.putExtra("event_id", eventId);
				intent.putExtra("user_id", uevl.getCurrentUserId());
				intent.putExtra("flag", "Update");
				intent.putExtra("returnPath",
						"com.uniqgroup.application.UserEventListActivity");
				startActivity(intent);
			}
		});*/

	}

	public void listViewWednesday() {

		final Context ctx = getActivity();

		/*lstWednesday.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				final int eventId = wednesday_list.get(position).getEventId();
				Intent intent;
				if (week_event_list_activity_ref.isEditMode()) {
					intent = new Intent(ctx, EventManageActivity.class);
				} else {

					if (db.checkEventSeqStatus(eventId)) {
						intent = new Intent(ctx, EventSeqActivity.class);
					} else {

						week_event_list_activity_ref
								.showRestartActiviyAlertDialog(eventId);

						return;
					}

				}

				UserEventListActivity uevl = (UserEventListActivity) getActivity();
				uevl.speakOut(wednesday_list.get(position).getEventTTS());
				intent.putExtra("event_id", eventId);
				intent.putExtra("user_id", uevl.getCurrentUserId());
				intent.putExtra("flag", "Update");
				intent.putExtra("returnPath",
						"com.uniqgroup.application.UserEventListActivity");
				startActivity(intent);
			}
		});*/

	}

	public void listViewThursday() {
/*
		final Context ctx = getActivity();

		lstThursday.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				final int eventId = thursday_list.get(position).getEventId();
				Intent intent;
				if (week_event_list_activity_ref.isEditMode()) {
					intent = new Intent(ctx, EventManageActivity.class);
				} else {

					if (db.checkEventSeqStatus(eventId)) {
						intent = new Intent(ctx, EventSeqActivity.class);
					} else {

						week_event_list_activity_ref
								.showRestartActiviyAlertDialog(eventId);

						return;
					}

				}

				UserEventListActivity uevl = (UserEventListActivity) getActivity();
				uevl.speakOut(thursday_list.get(position).getEventTTS());
				intent.putExtra("event_id", eventId);
				intent.putExtra("user_id", uevl.getCurrentUserId());
				intent.putExtra("flag", "Update");
				intent.putExtra("returnPath",
						"com.uniqgroup.application.UserEventListActivity");
				intent.putExtra("returnView", CalendarViews.weekView);
				startActivity(intent);

			}
		});
*/
	}

	public void listViewFriday() {

		/*final Context ctx = getActivity();

		lstFriday.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				final int eventId = friday_list.get(position).getEventId();
				Intent intent;
				if (week_event_list_activity_ref.isEditMode()) {
					intent = new Intent(ctx, EventManageActivity.class);
				} else {
					if (db.checkEventSeqStatus(eventId)) {
						intent = new Intent(ctx, EventSeqActivity.class);
					} else {

						week_event_list_activity_ref
								.showRestartActiviyAlertDialog(eventId);

						return;
					}

				}

				UserEventListActivity uevl = (UserEventListActivity) getActivity();
				uevl.speakOut(friday_list.get(position).getEventTTS());
				intent.putExtra("event_id", eventId);
				intent.putExtra("user_id", uevl.getCurrentUserId());
				intent.putExtra("flag", "Update");
				intent.putExtra("returnPath",
						"com.uniqgroup.application.UserEventListActivity");
				intent.putExtra("returnView", CalendarViews.weekView);
				startActivity(intent);
			}
		});
*/
	}

	public void listViewSaturday() {

		/*final Context ctx = getActivity();

		lstSaturday.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				final int eventId = saturday_list.get(position).getEventId();
				Intent intent;
				if (week_event_list_activity_ref.isEditMode()) {
					intent = new Intent(ctx, EventManageActivity.class);
				} else {

					if (db.checkEventSeqStatus(eventId)) {
						intent = new Intent(ctx, EventSeqActivity.class);
					} else {

						week_event_list_activity_ref
								.showRestartActiviyAlertDialog(eventId);

						return;
					}

				}

				UserEventListActivity uevl = (UserEventListActivity) getActivity();
				uevl.speakOut(saturday_list.get(position).getEventTTS());
				intent.putExtra("event_id", eventId);
				intent.putExtra("user_id", uevl.getCurrentUserId());
				intent.putExtra("flag", "Update");
				intent.putExtra("returnPath",
						"com.uniqgroup.application.UserEventListActivity");
				intent.putExtra("returnView", CalendarViews.weekView);
				startActivity(intent);
			}
		});
*/
	}

	public void listViewSunday() {
/*
		final Context ctx = getActivity();

		lstSunday.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				final int eventId = sunday_list.get(position).getEventId();
				Intent intent;
				if (week_event_list_activity_ref.isEditMode()) {
					intent = new Intent(ctx, EventManageActivity.class);
				} else {

					if (db.checkEventSeqStatus(eventId)) {
						intent = new Intent(ctx, EventSeqActivity.class);
					} else {

						week_event_list_activity_ref
								.showRestartActiviyAlertDialog(eventId);

						return;
					}

				}

				UserEventListActivity uevl = (UserEventListActivity) getActivity();
				uevl.speakOut(sunday_list.get(position).getEventTTS());
				intent.putExtra("event_id", eventId);
				intent.putExtra("user_id", uevl.getCurrentUserId());
				intent.putExtra("flag", "Update");
				intent.putExtra("returnPath",
						"com.uniqgroup.application.UserEventListActivity");
				intent.putExtra("returnView", CalendarViews.weekView);
				startActivity(intent);
			}
		});
*/
	}

	public void fetchAlltheData(String user_id) {

	}

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

	private void setContentView(int activityWeekViewFragment) {
		// TODO Auto-generated method stub

	}

	public void callNotifyMonday(ArrayList<WeekEvent> monday_list){
		mondayAdapter.changeData(monday_list);
		mondayAdapter.notifyDataSetChanged();
	}
	
	public void callNotifyTuesday(ArrayList<WeekEvent> tuesday_list){
		tuesdayAdapter.changeData(tuesday_list);
		tuesdayAdapter.notifyDataSetChanged();
	}
	
	public void callNotifyWednesday(ArrayList<WeekEvent> wednesday_list){
		wednesdayAdapter.changeData(wednesday_list);
		wednesdayAdapter.notifyDataSetChanged();
	}
	
	public void callNotifyThrusday(ArrayList<WeekEvent> thursday_list){
		thursdayAdapter.changeData(thursday_list);
		thursdayAdapter.notifyDataSetChanged();
	}
	
	public void callNotifyFriday(ArrayList<WeekEvent> friday_list){
		fridayAdapter.changeData(friday_list);
		fridayAdapter.notifyDataSetChanged();
	}
	
	public void callNotifySaturday(ArrayList<WeekEvent> saturday_list){
		saturdayAdapter.changeData(saturday_list);
		saturdayAdapter.notifyDataSetChanged();
	}
	
	public void callNotifySunday(ArrayList<WeekEvent> sunday_list){
		sundayAdapter.changeData(sunday_list);
		sundayAdapter.notifyDataSetChanged();
	}
}
