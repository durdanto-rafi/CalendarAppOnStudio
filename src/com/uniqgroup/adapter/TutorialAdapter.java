package com.uniqgroup.adapter;


import com.uniqgroup.application.R;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class TutorialAdapter extends PagerAdapter{

	Context context;
	String[] rank_dyn;
	int[] flag;
	LayoutInflater inflater;
 
	public TutorialAdapter(Context context, String[] rank, int[] flag) {
		this.context = context;
		this.rank_dyn = rank;
		this.flag = flag;
	}
 
	@Override
	public int getCount() {
		return rank_dyn.length;
	}
 
	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == ((LinearLayout) object);
	}
 
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
 
		ImageView imgflag;
 
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View itemView = inflater.inflate(R.layout.tutorial_items, container,false);		

		imgflag = (ImageView) itemView.findViewById(R.id.flag);
		imgflag.setImageResource(flag[position]);
 
		((ViewPager) container).addView(itemView);
 
		return itemView;
	}
 
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		((ViewPager) container).removeView((LinearLayout) object);
	}

}
