package com.uniqgroup.fragment;

import java.util.ArrayList;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.uniqgroup.adapter.PrizeListAdapter;
import com.uniqgroup.application.R;
import com.uniqgroup.application.UserEventListActivity;
import com.uniqgroup.database.DatabaseHandler;
import com.uniqgroup.pojo.Prize;
import com.uniqgroup.utility.CalenderDateList;

public class PrizeViewFragment extends Fragment{
	
	DatabaseHandler db;
	ArrayList<Prize> prizeList;
	UserEventListActivity uela;
	PrizeListAdapter prizeAdapter;
	ListView lstPrize;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		uela = (UserEventListActivity) getActivity();
		db = new DatabaseHandler(getActivity());
		prizeList= (ArrayList<Prize>) db.getPrizeData(CalenderDateList.getCurrentWeekNumber(), Integer.parseInt(uela.getCurrentUserId()));
		if(prizeList != null)
		{
			prizeAdapter = new PrizeListAdapter(getActivity(), prizeList);
		}
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
		View v = inflater.inflate(R.layout.prize_frag, null);
		lstPrize = (ListView) v.findViewById(R.id.lstPrize);
		lstPrize.setAdapter(prizeAdapter);
		return v;
	}

}
