package com.uniqgroup.fragment;



import com.uniqgroup.customui.OverlayBallsEnd;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TransparentFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		final OverlayBallsEnd view = new OverlayBallsEnd(getActivity());
		return view;
	}
	
}
