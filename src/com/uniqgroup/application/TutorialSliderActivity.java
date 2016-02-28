package com.uniqgroup.application;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.uniqgroup.adapter.TutorialAdapter;

public class TutorialSliderActivity extends Activity {

	int counter = 0, savePosition, lastPage, swipeFlag;
	ViewPager viewPager;
	PagerAdapter adapter;
	String[] tutorialText;
	int[] tutotialImages;
	ImageButton prev_IB, next_IB, ibtnBack;
	TextView tvTutorialText;
	TutorialSliderActivity ref;

	public void previous(int prev_page) {

		if (counter > 0) {
			counter--;
			viewPager.setCurrentItem(counter);

		} else {
			counter = 17;
			viewPager.setCurrentItem(counter);
		}
	}

	public void next(int next_page) {
		if (counter < 17) {
			counter++;
			viewPager.setCurrentItem(counter);

		} else {
			counter = 17;
			viewPager.setCurrentItem(counter);
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tutorial);
		ref = this;
		tutorialText = new String[] {
				this.getResources().getString(R.string.img_tuto_s01),
				this.getResources().getString(R.string.img_tuto_s02),
				this.getResources().getString(R.string.img_tuto_s03),
				this.getResources().getString(R.string.img_tuto_s04),
				this.getResources().getString(R.string.img_tuto_s05),
				this.getResources().getString(R.string.img_tuto_s06),
				this.getResources().getString(R.string.img_tuto_s07),
				this.getResources().getString(R.string.img_tuto_s08),
				this.getResources().getString(R.string.img_tuto_s09),
				this.getResources().getString(R.string.img_tuto_s010),
				this.getResources().getString(R.string.img_tuto_s011),
				this.getResources().getString(R.string.img_tuto_s012),
				this.getResources().getString(R.string.img_tuto_s013),
				this.getResources().getString(R.string.img_tuto_s014),
				this.getResources().getString(R.string.img_tuto_s015),
				this.getResources().getString(R.string.img_tuto_s016),
				this.getResources().getString(R.string.img_tuto_s017),
				this.getResources().getString(R.string.img_tuto_s018), };

		tutotialImages = new int[] { R.drawable.img_tuto_s01,
				R.drawable.img_tuto_s02, R.drawable.img_tuto_s03,
				R.drawable.img_tuto_s04, R.drawable.img_tuto_s05,
				R.drawable.img_tuto_s06, R.drawable.img_tuto_s07,
				R.drawable.img_tuto_s08, R.drawable.img_tuto_s09,
				R.drawable.img_tuto_s10, R.drawable.img_tuto_s11,
				R.drawable.img_tuto_s12, R.drawable.img_tuto_s13,
				R.drawable.img_tuto_s14, R.drawable.img_tuto_s15,
				R.drawable.img_tuto_s16, R.drawable.img_tuto_s17,
				R.drawable.img_tuto_s18 };

		ibtnBack = (ImageButton) findViewById(R.id.ibtnBack);

		prev_IB = (ImageButton) findViewById(R.id.prev_IB);
		prev_IB.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				swipeFlag = 1;
				previous(0);
			}
		});

		next_IB = (ImageButton) findViewById(R.id.next_IB);
		next_IB.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				swipeFlag = 1;
				next(0);
			}
		});

		prev_IB.setVisibility(View.INVISIBLE);

		tvTutorialText = (TextView) findViewById(R.id.tvTutorialText);
		viewPager = (ViewPager) findViewById(R.id.pager);
		adapter = new TutorialAdapter(TutorialSliderActivity.this, tutorialText, tutotialImages);
		viewPager.setCurrentItem(1);
		tvTutorialText.setText(tutorialText[0]);
		viewPager.setAdapter(adapter);

		viewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						if (lastPage > position) {
							if (swipeFlag != 1) {
								previous(0);
							}
						} else {
							if (swipeFlag != 1) {
								next(0);
							}
						}

						if (position == 0) {
							prev_IB.setVisibility(View.INVISIBLE);
						} else {
							prev_IB.setVisibility(View.VISIBLE);
						}
						if (position == 17) {
							next_IB.setVisibility(View.INVISIBLE);
						} else {
							next_IB.setVisibility(View.VISIBLE);
						}
						lastPage = position;
						tvTutorialText.setText(tutorialText[counter]);
						swipeFlag = 0;
					}
				});

		ibtnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent myIntent = new Intent(TutorialSliderActivity.this,
						TutorialActivity.class);
				TutorialSliderActivity.this.startActivity(myIntent);
				finish();
			}
		});

		FullScreencall();

	}

	public void FullScreencall() 
	{
		if (Build.VERSION.SDK_INT < 19) 
		{
			View v = this.getWindow().getDecorView();
			v.setSystemUiVisibility(View.GONE);
		} 
		else 
		{
			// for lower api versions.
			View decorView = getWindow().getDecorView();
			int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
			decorView.setSystemUiVisibility(uiOptions);
		}
	}
}
