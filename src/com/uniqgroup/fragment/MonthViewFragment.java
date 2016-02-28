package com.uniqgroup.fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.uniqgroup.application.R;
import com.uniqgroup.application.UserEventListActivity;
import com.uniqgroup.customdialog.MonthViewImageChangerDialog;
import com.uniqgroup.database.DatabaseHandler;
import com.uniqgroup.pojo.WeekEvent;
import com.uniqgroup.utility.CalenderDateList;
import com.uniqgroup.utility.ImageProcessing;

public class MonthViewFragment extends Fragment implements OnClickListener {

	// UserEventListActivity monthEventListActivityRef;
	String monthYear;
	int userId;
	MonthViewImageChangerDialog changerDialog;
	DatabaseHandler db;
	private ProgressDialog PdSummary;
	
	
	ArrayList<WeekEvent> monthImage = new ArrayList<WeekEvent>();
	ImageView ivPropicOne, ivPropicTwo, ivPropicThree, ivPropicFour,
			ivPropicFive, ivPropicSix, ivPropicSeven, ivPropicEight,
			ivPropicNine, ivPropicTen;
	ImageView ivPropicEleven, ivPropicTwelve, ivPropicThirteen,
			ivPropicFourteen, ivPropicFifteen, ivPropicSixteen,
			ivPropicSeventeen, ivPropicEighteen;
	ImageView ivPropicNineteen, ivPropicTwenty, ivPropicTwentyOne,
			ivPropicTwentyTwo, ivPropicTwentyThree, ivPropicTwentyFour,
			ivPropicTwentyFive;
	ImageView ivPropicTwentySix, ivPropicTwentySeven, ivPropicTwentyEight,
			ivPropicTwentyNine, ivPropicThirty, ivPropicThirtyOne,
			ivPropicThirtyTwo, ivPropicThirtyThree, ivPropicThirtyFour,
			ivPropicThirtyFive, ivPropicThirtySix, ivPropicThirtySeven,
			ivPropicThirtyEight, ivPropicThirtyNine, ivPropicFourty,
			ivPropicFourtyOne, ivPropicFourtyTwo;

	TextView tvOne, tvTwo, tvThree, tvFour, tvFive, tvSix, tvSeven, tvEight,
			tvNine, tvTen, tvEleven, tvTwelve, tvThirteen, tvFourteen,
			tvFifteen, tvSixteen, tvSevenTeen, tvEighteen, tvNinteen, tvTwenty,
			tvTwentyOne, tvTwentyTwo, tvTwentyThree, tvTwentyFour,
			tvTwentyFive, tvTwentySix, tvTwentySeven, tvTwentyEight,
			tvTwentyNine, tvThirty, tvThirtyOne, tvThirtyTwo, tvThirtyThree,
			tvThirtyFour, tvThirtyFive, tvThirtySix, tvThirtySeven,
			tvThirtyEight, tvThirtyNine, tvFourty, tvFourtyOne, tvFourtyTwo;
	TextView tvMonthFragHeader;

	UserEventListActivity uela;
	MonthViewImageChangerDialog imageChangerDialog;
	MonthViewFragment monthViewFragment = null;

	ArrayList<TextView> tvList = new ArrayList<TextView>();
	ArrayList<String> dateListOfMonth = new ArrayList<String>();
	ArrayList<String> imagePath = new ArrayList<String>();

	ArrayList<ImageView> dateList = new ArrayList<>();
	private ImageProcessing imageProcessingMonth;

	LinearLayout llFirstSevenDays, llSecondSevenDays, llThirdSevenDays,
			llFourthSevenDays, llFifthSevenDays, llSixSevenDays;
	LinearLayout dayOne, dayTwo, dayThree, dayFour, dayFive, daySix, daySeven;
	LinearLayout dayEight, dayNine, dayTen, dayEleven, dayTwelve, dayThirteen,
			dayFourteen;
	LinearLayout dayFifteen, daySixteen, daySeventeen, dayEighteen,
			dayNineteen, dayTwenty, dayTwentyOne;
	LinearLayout dayTwentyTwo, dayTwentyThree, dayTwentyFour, dayTwentyFive,
			dayTwentySix, dayTwentySeven, dayTwentyEight;
	LinearLayout dayTwentyNine, dayThirty, dayThirtyOne, dayThirtyTwo,
			dayThirtyThree, dayThirtyFour, dayThirtyFive, dayThirtySix,
			dayThirtySeven, dayThirtyEight, dayThirtyNine, dayFourty,
			dayFourtyOne, dayFourtyTwo;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.month_view_fragment);

		Bundle bundle = getArguments();

		monthViewFragment = new MonthViewFragment();
		db = new DatabaseHandler(getActivity());
		uela = (UserEventListActivity) getActivity();
		userId = Integer.parseInt(uela.getCurrentUserId());
		// imageChangerDialog = new MonthViewImageChangerDialog(activity);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.month_view_fragment, null);

		imageProcessingMonth = new ImageProcessing(getActivity());

		tvMonthFragHeader = (TextView) view
				.findViewById(R.id.tvMonthFragHeader);
		llFirstSevenDays = (LinearLayout) view
				.findViewById(R.id.first_seven_days);
		llSecondSevenDays = (LinearLayout) view
				.findViewById(R.id.second_seven_days);
		llThirdSevenDays = (LinearLayout) view
				.findViewById(R.id.third_seven_days);
		llFourthSevenDays = (LinearLayout) view
				.findViewById(R.id.fourth_seven_days);
		llFifthSevenDays = (LinearLayout) view
				.findViewById(R.id.fifth_seven_days);
		llSixSevenDays = (LinearLayout) view
				.findViewById(R.id.sixth_seven_days);

		dayOne = (LinearLayout) view.findViewById(R.id.day_one);
		dayTwo = (LinearLayout) view.findViewById(R.id.day_two);
		dayThree = (LinearLayout) view.findViewById(R.id.day_three);
		dayFour = (LinearLayout) view.findViewById(R.id.day_four);
		dayFive = (LinearLayout) view.findViewById(R.id.day_five);
		daySix = (LinearLayout) view.findViewById(R.id.day_six);
		daySeven = (LinearLayout) view.findViewById(R.id.day_seven);
		dayEight = (LinearLayout) view.findViewById(R.id.day_eight);
		dayNine = (LinearLayout) view.findViewById(R.id.day_nine);
		dayTen = (LinearLayout) view.findViewById(R.id.day_ten);
		dayEleven = (LinearLayout) view.findViewById(R.id.day_eleven);
		dayTwelve = (LinearLayout) view.findViewById(R.id.day_twelve);
		dayThirteen = (LinearLayout) view.findViewById(R.id.day_thirteen);
		dayFourteen = (LinearLayout) view.findViewById(R.id.day_fourteen);
		dayFifteen = (LinearLayout) view.findViewById(R.id.day_fifteen);

		daySixteen = (LinearLayout) view.findViewById(R.id.day_sixteen);
		daySeventeen = (LinearLayout) view.findViewById(R.id.day_seventeen);
		dayEighteen = (LinearLayout) view.findViewById(R.id.day_eighteen);
		dayNineteen = (LinearLayout) view.findViewById(R.id.day_nineteen);
		dayTwenty = (LinearLayout) view.findViewById(R.id.day_twenty);
		dayTwentyOne = (LinearLayout) view.findViewById(R.id.day_twenty_one);
		dayTwentyTwo = (LinearLayout) view.findViewById(R.id.day_twenty_two);
		dayTwentyThree = (LinearLayout) view
				.findViewById(R.id.day_twenty_three);
		dayTwentyFour = (LinearLayout) view.findViewById(R.id.day_twenty_four);
		dayTwentyFive = (LinearLayout) view.findViewById(R.id.day_twenty_five);
		dayTwentySix = (LinearLayout) view.findViewById(R.id.day_twenty_six);
		dayTwentySeven = (LinearLayout) view
				.findViewById(R.id.day_twenty_seven);
		dayTwentyEight = (LinearLayout) view
				.findViewById(R.id.day_twenty_eight);
		dayTwentyNine = (LinearLayout) view.findViewById(R.id.day_twenty_nine);
		dayThirty = (LinearLayout) view.findViewById(R.id.day_thirty);

		dayThirtyOne = (LinearLayout) view.findViewById(R.id.day_thirty_one);
		dayThirtyTwo = (LinearLayout) view.findViewById(R.id.day_thirty_two);
		dayThirtyThree = (LinearLayout) view
				.findViewById(R.id.day_thirty_three);
		dayThirtyFour = (LinearLayout) view.findViewById(R.id.day_thirty_four);
		dayThirtyFive = (LinearLayout) view.findViewById(R.id.day_thirty_five);
		dayThirtySix = (LinearLayout) view.findViewById(R.id.day_thirty_six);
		dayThirtySeven = (LinearLayout) view
				.findViewById(R.id.day_thirty_seven);
		dayThirtyEight = (LinearLayout) view
				.findViewById(R.id.day_thirty_eight);
		dayThirtyNine = (LinearLayout) view.findViewById(R.id.day_thirty_nine);
		dayFourty = (LinearLayout) view.findViewById(R.id.day_fourty);
		dayFourtyOne = (LinearLayout) view.findViewById(R.id.day_fourty_one);
		dayFourtyTwo = (LinearLayout) view.findViewById(R.id.day_fourty_two);

		tvOne = (TextView) dayOne.findViewById(R.id.tvHeader);
		tvTwo = (TextView) dayTwo.findViewById(R.id.tvHeader);
		tvThree = (TextView) dayThree.findViewById(R.id.tvHeader);
		tvFour = (TextView) dayFour.findViewById(R.id.tvHeader);
		tvFive = (TextView) dayFive.findViewById(R.id.tvHeader);
		tvSix = (TextView) daySix.findViewById(R.id.tvHeader);
		tvSeven = (TextView) daySeven.findViewById(R.id.tvHeader);
		tvEight = (TextView) dayEight.findViewById(R.id.tvHeader);
		tvNine = (TextView) dayNine.findViewById(R.id.tvHeader);
		tvTen = (TextView) dayTen.findViewById(R.id.tvHeader);

		tvEleven = (TextView) dayEleven.findViewById(R.id.tvHeader);
		tvTwelve = (TextView) dayTwelve.findViewById(R.id.tvHeader);
		tvThirteen = (TextView) dayThirteen.findViewById(R.id.tvHeader);
		tvFourteen = (TextView) dayFourteen.findViewById(R.id.tvHeader);
		tvFifteen = (TextView) dayFifteen.findViewById(R.id.tvHeader);
		tvSixteen = (TextView) daySixteen.findViewById(R.id.tvHeader);
		tvSevenTeen = (TextView) daySeventeen.findViewById(R.id.tvHeader);
		tvEighteen = (TextView) dayEighteen.findViewById(R.id.tvHeader);
		tvNinteen = (TextView) dayNineteen.findViewById(R.id.tvHeader);
		tvTwenty = (TextView) dayTwenty.findViewById(R.id.tvHeader);
		tvTwentyOne = (TextView) dayTwentyOne.findViewById(R.id.tvHeader);
		tvTwentyTwo = (TextView) dayTwentyTwo.findViewById(R.id.tvHeader);
		tvTwentyThree = (TextView) dayTwentyThree.findViewById(R.id.tvHeader);
		tvTwentyFour = (TextView) dayTwentyFour.findViewById(R.id.tvHeader);
		tvTwentyFive = (TextView) dayTwentyFive.findViewById(R.id.tvHeader);
		tvTwentySix = (TextView) dayTwentySix.findViewById(R.id.tvHeader);
		tvTwentySeven = (TextView) dayTwentySeven.findViewById(R.id.tvHeader);
		tvTwentyEight = (TextView) dayTwentyEight.findViewById(R.id.tvHeader);
		tvTwentyNine = (TextView) dayTwentyNine.findViewById(R.id.tvHeader);
		tvThirty = (TextView) dayThirty.findViewById(R.id.tvHeader);
		tvThirtyOne = (TextView) dayThirtyOne.findViewById(R.id.tvHeader);
		tvThirtyTwo = (TextView) dayThirtyTwo.findViewById(R.id.tvHeader);
		tvThirtyThree = (TextView) dayThirtyThree.findViewById(R.id.tvHeader);
		tvThirtyFour = (TextView) dayThirtyFour.findViewById(R.id.tvHeader);
		tvThirtyFive = (TextView) dayThirtyFive.findViewById(R.id.tvHeader);
		tvThirtySix = (TextView) dayThirtySix.findViewById(R.id.tvHeader);
		tvThirtySeven = (TextView) dayThirtySeven.findViewById(R.id.tvHeader);
		tvThirtyEight = (TextView) dayThirtyEight.findViewById(R.id.tvHeader);
		tvThirtyNine = (TextView) dayThirtyNine.findViewById(R.id.tvHeader);
		tvFourty = (TextView) dayFourty.findViewById(R.id.tvHeader);
		tvFourtyOne = (TextView) dayFourtyOne.findViewById(R.id.tvHeader);
		tvFourtyTwo = (TextView) dayFourtyTwo.findViewById(R.id.tvHeader);

		ivPropicOne = (ImageView) dayOne.findViewById(R.id.iv_month_pro_pic);
		ivPropicTwo = (ImageView) dayTwo.findViewById(R.id.iv_month_pro_pic);
		ivPropicThree = (ImageView) dayThree
				.findViewById(R.id.iv_month_pro_pic);
		ivPropicFour = (ImageView) dayFour.findViewById(R.id.iv_month_pro_pic);
		ivPropicFive = (ImageView) dayFive.findViewById(R.id.iv_month_pro_pic);
		ivPropicSix = (ImageView) daySix.findViewById(R.id.iv_month_pro_pic);
		ivPropicSeven = (ImageView) daySeven
				.findViewById(R.id.iv_month_pro_pic);
		ivPropicEight = (ImageView) dayEight
				.findViewById(R.id.iv_month_pro_pic);
		ivPropicNine = (ImageView) dayNine.findViewById(R.id.iv_month_pro_pic);
		ivPropicTen = (ImageView) dayTen.findViewById(R.id.iv_month_pro_pic);

		ivPropicEleven = (ImageView) dayEleven
				.findViewById(R.id.iv_month_pro_pic);
		ivPropicTwelve = (ImageView) dayTwelve
				.findViewById(R.id.iv_month_pro_pic);
		ivPropicThirteen = (ImageView) dayThirteen
				.findViewById(R.id.iv_month_pro_pic);
		ivPropicFourteen = (ImageView) dayFourteen
				.findViewById(R.id.iv_month_pro_pic);
		ivPropicFifteen = (ImageView) dayFifteen
				.findViewById(R.id.iv_month_pro_pic);
		ivPropicSixteen = (ImageView) daySixteen
				.findViewById(R.id.iv_month_pro_pic);
		ivPropicSeventeen = (ImageView) daySeventeen
				.findViewById(R.id.iv_month_pro_pic);
		ivPropicEighteen = (ImageView) dayEighteen
				.findViewById(R.id.iv_month_pro_pic);
		ivPropicNineteen = (ImageView) dayNineteen
				.findViewById(R.id.iv_month_pro_pic);
		ivPropicTwenty = (ImageView) dayTwenty
				.findViewById(R.id.iv_month_pro_pic);
		ivPropicTwentyOne = (ImageView) dayTwentyOne
				.findViewById(R.id.iv_month_pro_pic);
		ivPropicTwentyTwo = (ImageView) dayTwentyTwo
				.findViewById(R.id.iv_month_pro_pic);
		ivPropicTwentyThree = (ImageView) dayTwentyThree
				.findViewById(R.id.iv_month_pro_pic);
		ivPropicTwentyFour = (ImageView) dayTwentyFour
				.findViewById(R.id.iv_month_pro_pic);
		ivPropicTwentyFive = (ImageView) dayTwentyFive
				.findViewById(R.id.iv_month_pro_pic);
		ivPropicTwentySix = (ImageView) dayTwentySix
				.findViewById(R.id.iv_month_pro_pic);
		ivPropicTwentySeven = (ImageView) dayTwentySeven
				.findViewById(R.id.iv_month_pro_pic);
		ivPropicTwentyEight = (ImageView) dayTwentyEight
				.findViewById(R.id.iv_month_pro_pic);
		ivPropicTwentyNine = (ImageView) dayTwentyNine
				.findViewById(R.id.iv_month_pro_pic);
		ivPropicThirty = (ImageView) dayThirty
				.findViewById(R.id.iv_month_pro_pic);

		ivPropicThirtyOne = (ImageView) dayThirtyOne
				.findViewById(R.id.iv_month_pro_pic);

		ivPropicThirtyTwo = (ImageView) dayThirtyTwo
				.findViewById(R.id.iv_month_pro_pic);
		ivPropicThirtyThree = (ImageView) dayThirtyThree
				.findViewById(R.id.iv_month_pro_pic);
		ivPropicThirtyFour = (ImageView) dayThirtyFour
				.findViewById(R.id.iv_month_pro_pic);
		ivPropicThirtyFive = (ImageView) dayThirtyFive
				.findViewById(R.id.iv_month_pro_pic);
		ivPropicThirtySix = (ImageView) dayThirtySix
				.findViewById(R.id.iv_month_pro_pic);
		ivPropicThirtySeven = (ImageView) dayThirtySeven
				.findViewById(R.id.iv_month_pro_pic);
		ivPropicThirtyEight = (ImageView) dayThirtyEight
				.findViewById(R.id.iv_month_pro_pic);
		ivPropicThirtyNine = (ImageView) dayThirtyNine
				.findViewById(R.id.iv_month_pro_pic);
		ivPropicFourty = (ImageView) dayFourty
				.findViewById(R.id.iv_month_pro_pic);
		ivPropicFourtyOne = (ImageView) dayFourtyOne
				.findViewById(R.id.iv_month_pro_pic);
		ivPropicFourtyTwo = (ImageView) dayFourtyTwo
				.findViewById(R.id.iv_month_pro_pic);

		dateList.add(ivPropicOne);
		dateList.add(ivPropicTwo);
		dateList.add(ivPropicThree);
		dateList.add(ivPropicFour);
		dateList.add(ivPropicFive);
		dateList.add(ivPropicSix);
		dateList.add(ivPropicSeven);
		dateList.add(ivPropicEight);
		dateList.add(ivPropicNine);
		dateList.add(ivPropicTen);
		dateList.add(ivPropicEleven);
		dateList.add(ivPropicTwelve);
		dateList.add(ivPropicThirteen);
		dateList.add(ivPropicFourteen);
		dateList.add(ivPropicFifteen);
		dateList.add(ivPropicSixteen);
		dateList.add(ivPropicSeventeen);
		dateList.add(ivPropicEighteen);
		dateList.add(ivPropicNineteen);
		dateList.add(ivPropicTwenty);
		dateList.add(ivPropicTwentyOne);
		dateList.add(ivPropicTwentyTwo);
		dateList.add(ivPropicTwentyThree);
		dateList.add(ivPropicTwentyFour);
		dateList.add(ivPropicTwentyFive);
		dateList.add(ivPropicTwentySix);
		dateList.add(ivPropicTwentySeven);
		dateList.add(ivPropicTwentyEight);
		dateList.add(ivPropicTwentyNine);
		dateList.add(ivPropicThirty);
		dateList.add(ivPropicThirtyOne);
		dateList.add(ivPropicThirtyTwo);
		dateList.add(ivPropicThirtyThree);
		dateList.add(ivPropicThirtyFour);
		dateList.add(ivPropicThirtyFive);
		dateList.add(ivPropicThirtySix);
		dateList.add(ivPropicThirtySeven);
		dateList.add(ivPropicThirtyEight);
		dateList.add(ivPropicThirtyNine);
		dateList.add(ivPropicFourty);
		dateList.add(ivPropicFourtyOne);
		dateList.add(ivPropicFourtyTwo);

		tvList.add(tvOne);
		tvList.add(tvTwo);
		tvList.add(tvThree);
		tvList.add(tvFour);
		tvList.add(tvFive);
		tvList.add(tvSix);
		tvList.add(tvSeven);
		tvList.add(tvEight);
		tvList.add(tvNine);
		tvList.add(tvTen);
		tvList.add(tvEleven);
		tvList.add(tvTwelve);
		tvList.add(tvThirteen);
		tvList.add(tvFourteen);
		tvList.add(tvFifteen);
		tvList.add(tvSixteen);
		tvList.add(tvSevenTeen);
		tvList.add(tvEighteen);
		tvList.add(tvNinteen);
		tvList.add(tvTwenty);
		tvList.add(tvTwentyOne);
		tvList.add(tvTwentyTwo);
		tvList.add(tvTwentyThree);
		tvList.add(tvTwentyFour);
		tvList.add(tvTwentyFive);
		tvList.add(tvTwentySix);
		tvList.add(tvTwentySeven);
		tvList.add(tvTwentyEight);
		tvList.add(tvTwentyNine);
		tvList.add(tvThirty);
		tvList.add(tvThirtyOne);
		tvList.add(tvThirtyTwo);
		tvList.add(tvThirtyThree);
		tvList.add(tvThirtyFour);
		tvList.add(tvThirtyFive);
		tvList.add(tvThirtySix);
		tvList.add(tvThirtySeven);
		tvList.add(tvThirtyEight);
		tvList.add(tvThirtyNine);
		tvList.add(tvFourty);
		tvList.add(tvFourtyOne);
		tvList.add(tvFourtyTwo);

		//bindData();
		AsycLoadMonthView asycDayView = new AsycLoadMonthView();
		asycDayView.execute();

		dayOne.setOnClickListener(this);
		dayTwo.setOnClickListener(this);
		dayThree.setOnClickListener(this);
		dayFour.setOnClickListener(this);
		dayFive.setOnClickListener(this);
		daySix.setOnClickListener(this);
		daySeven.setOnClickListener(this);
		dayEight.setOnClickListener(this);
		dayNine.setOnClickListener(this);
		dayTen.setOnClickListener(this);
		dayEleven.setOnClickListener(this);
		dayTwelve.setOnClickListener(this);
		dayThirteen.setOnClickListener(this);
		dayFourteen.setOnClickListener(this);
		dayFifteen.setOnClickListener(this);
		daySixteen.setOnClickListener(this);
		daySeventeen.setOnClickListener(this);
		dayEighteen.setOnClickListener(this);
		dayNineteen.setOnClickListener(this);
		dayTwenty.setOnClickListener(this);
		dayTwentyOne.setOnClickListener(this);
		dayTwentyTwo.setOnClickListener(this);
		dayTwentyThree.setOnClickListener(this);
		dayTwentyFour.setOnClickListener(this);
		dayTwentyFive.setOnClickListener(this);
		dayTwentySix.setOnClickListener(this);
		dayTwentySeven.setOnClickListener(this);
		dayTwentyEight.setOnClickListener(this);
		dayTwentyNine.setOnClickListener(this);
		dayThirty.setOnClickListener(this);
		dayThirtyOne.setOnClickListener(this);
		dayThirtyTwo.setOnClickListener(this);
		dayThirtyThree.setOnClickListener(this);
		dayThirtyFour.setOnClickListener(this);
		dayThirtyFive.setOnClickListener(this);
		dayThirtySix.setOnClickListener(this);
		dayThirtySeven.setOnClickListener(this);
		dayThirtyEight.setOnClickListener(this);
		dayThirtyNine.setOnClickListener(this);
		dayFourty.setOnClickListener(this);
		dayFourtyOne.setOnClickListener(this);
		dayFourtyTwo.setOnClickListener(this);

		return view;

	}

	private void setContentView(int monthViewFragment) {

	}

	@Override
	public void onClick(View v) {

		String dayAndWeekNum = null;
		String currentDate = null;
		switch (v.getId()) {
		case R.id.day_one:
			currentDate = tvOne.getText().toString();
			if (currentDate.length() > 0) {
				dayAndWeekNum = CalenderDateList.getWeekNoAndDay(currentDate
						+ monthYear);
			}
			break;

		case R.id.day_two:
			currentDate = tvTwo.getText().toString();
			if (currentDate.length() > 0) {
				dayAndWeekNum = CalenderDateList.getWeekNoAndDay(currentDate
						+ monthYear);
			}
			break;

		case R.id.day_three:
			currentDate = tvThree.getText().toString();
			if (currentDate.length() > 0) {
				dayAndWeekNum = CalenderDateList.getWeekNoAndDay(currentDate
						+ monthYear);
			}
			break;

		case R.id.day_four:
			currentDate = tvFour.getText().toString();
			if (currentDate.length() > 0) {
				dayAndWeekNum = CalenderDateList.getWeekNoAndDay(currentDate
						+ monthYear);
			}
			break;

		case R.id.day_five:
			currentDate = tvFive.getText().toString();
			if (currentDate.length() > 0) {
				dayAndWeekNum = CalenderDateList.getWeekNoAndDay(currentDate
						+ monthYear);
			}
			break;

		case R.id.day_six:

			currentDate = tvSix.getText().toString();
			if (currentDate.length() > 0) {
				dayAndWeekNum = CalenderDateList.getWeekNoAndDay(currentDate
						+ monthYear);
			}

			break;

		case R.id.day_seven:
			currentDate = tvSeven.getText().toString();
			if (currentDate.length() > 0) {
				dayAndWeekNum = CalenderDateList.getWeekNoAndDay(currentDate
						+ monthYear);
			}
			break;

		case R.id.day_eight:
			currentDate = tvEight.getText().toString();
			if (currentDate.length() > 0) {
				dayAndWeekNum = CalenderDateList.getWeekNoAndDay(currentDate
						+ monthYear);
			}
			break;

		case R.id.day_nine:
			currentDate = tvNine.getText().toString();
			if (currentDate.length() > 0) {
				dayAndWeekNum = CalenderDateList.getWeekNoAndDay(currentDate
						+ monthYear);
			}
			break;

		case R.id.day_ten:
			currentDate = tvTen.getText().toString();
			if (currentDate.length() > 0) {
				dayAndWeekNum = CalenderDateList.getWeekNoAndDay(currentDate
						+ monthYear);
			}
			break;

		case R.id.day_eleven:
			currentDate = tvEleven.getText().toString();
			if (currentDate.length() > 0) {
				dayAndWeekNum = CalenderDateList.getWeekNoAndDay(currentDate
						+ monthYear);
			}
			break;

		case R.id.day_twelve:
			currentDate = tvTwelve.getText().toString();
			if (currentDate.length() > 0) {
				dayAndWeekNum = CalenderDateList.getWeekNoAndDay(currentDate
						+ monthYear);
			}
			break;

		case R.id.day_thirteen:
			currentDate = tvThirteen.getText().toString();
			if (currentDate.length() > 0) {
				dayAndWeekNum = CalenderDateList.getWeekNoAndDay(currentDate
						+ monthYear);
			}
			break;

		case R.id.day_fourteen:
			currentDate = tvFourteen.getText().toString();
			if (currentDate.length() > 0) {
				dayAndWeekNum = CalenderDateList.getWeekNoAndDay(currentDate
						+ monthYear);
			}
			break;

		case R.id.day_fifteen:
			currentDate = tvFifteen.getText().toString();
			if (currentDate.length() > 0) {
				dayAndWeekNum = CalenderDateList.getWeekNoAndDay(currentDate
						+ monthYear);
			}
			break;

		case R.id.day_sixteen:
			currentDate = tvSixteen.getText().toString();
			if (currentDate.length() > 0) {
				dayAndWeekNum = CalenderDateList.getWeekNoAndDay(currentDate
						+ monthYear);
			}
			break;

		case R.id.day_seventeen:
			currentDate = tvSevenTeen.getText().toString();
			if (currentDate.length() > 0) {
				dayAndWeekNum = CalenderDateList.getWeekNoAndDay(currentDate
						+ monthYear);
			}
			break;

		case R.id.day_eighteen:
			currentDate = tvEighteen.getText().toString();
			if (currentDate.length() > 0) {
				dayAndWeekNum = CalenderDateList.getWeekNoAndDay(currentDate
						+ monthYear);
			}
			break;

		case R.id.day_nineteen:
			currentDate = tvNinteen.getText().toString();
			if (currentDate.length() > 0) {
				dayAndWeekNum = CalenderDateList.getWeekNoAndDay(currentDate
						+ monthYear);
			}
			break;

		case R.id.day_twenty:
			currentDate = tvTwenty.getText().toString();
			if (currentDate.length() > 0) {
				dayAndWeekNum = CalenderDateList.getWeekNoAndDay(currentDate
						+ monthYear);
			}
			break;

		case R.id.day_twenty_one:
			currentDate = tvTwentyOne.getText().toString();
			if (currentDate.length() > 0) {
				dayAndWeekNum = CalenderDateList.getWeekNoAndDay(currentDate
						+ monthYear);
			}
			break;

		case R.id.day_twenty_two:
			currentDate = tvTwentyTwo.getText().toString();
			if (currentDate.length() > 0) {
				dayAndWeekNum = CalenderDateList.getWeekNoAndDay(currentDate
						+ monthYear);
			}
			break;

		case R.id.day_twenty_three:
			currentDate = tvTwentyThree.getText().toString();
			if (currentDate.length() > 0) {
				dayAndWeekNum = CalenderDateList.getWeekNoAndDay(currentDate
						+ monthYear);
			}
			break;

		case R.id.day_twenty_four:
			currentDate = tvTwentyFour.getText().toString();
			if (currentDate.length() > 0) {
				dayAndWeekNum = CalenderDateList.getWeekNoAndDay(currentDate
						+ monthYear);
			}
			break;

		case R.id.day_twenty_five:
			currentDate = tvTwentyFive.getText().toString();
			if (currentDate.length() > 0) {
				dayAndWeekNum = CalenderDateList.getWeekNoAndDay(currentDate
						+ monthYear);
			}
			break;

		case R.id.day_twenty_six:
			currentDate = tvTwentySix.getText().toString();
			if (currentDate.length() > 0) {
				dayAndWeekNum = CalenderDateList.getWeekNoAndDay(currentDate
						+ monthYear);
			}
			break;

		case R.id.day_twenty_seven:
			currentDate = tvTwentySeven.getText().toString();
			if (currentDate.length() > 0) {
				dayAndWeekNum = CalenderDateList.getWeekNoAndDay(currentDate
						+ monthYear);
			}
			break;

		case R.id.day_twenty_eight:
			currentDate = tvTwentyEight.getText().toString();
			if (currentDate.length() > 0) {
				dayAndWeekNum = CalenderDateList.getWeekNoAndDay(currentDate
						+ monthYear);
			}
			break;

		case R.id.day_twenty_nine:
			currentDate = tvTwentyNine.getText().toString();
			if (currentDate.length() > 0) {
				dayAndWeekNum = CalenderDateList.getWeekNoAndDay(currentDate
						+ monthYear);
			}
			break;

		case R.id.day_thirty:
			currentDate = tvThirty.getText().toString();
			if (currentDate.length() > 0) {
				dayAndWeekNum = CalenderDateList.getWeekNoAndDay(currentDate
						+ monthYear);
			}
			break;

		case R.id.day_thirty_one:
			currentDate = tvThirtyOne.getText().toString();
			if (currentDate.length() > 0) {
				dayAndWeekNum = CalenderDateList.getWeekNoAndDay(currentDate
						+ monthYear);
			}
			break;

		case R.id.day_thirty_two:
			currentDate = tvThirtyTwo.getText().toString();
			if (currentDate.length() > 0) {
				dayAndWeekNum = CalenderDateList.getWeekNoAndDay(currentDate
						+ monthYear);
			}
			break;

		case R.id.day_thirty_three:
			currentDate = tvThirtyThree.getText().toString();
			if (currentDate.length() > 0) {
				dayAndWeekNum = CalenderDateList.getWeekNoAndDay(currentDate
						+ monthYear);
			}

			break;

		case R.id.day_thirty_four:
			currentDate = tvThirtyFour.getText().toString();
			if (currentDate.length() > 0) {
				dayAndWeekNum = CalenderDateList.getWeekNoAndDay(currentDate
						+ monthYear);
			}
			break;

		case R.id.day_thirty_five:
			currentDate = tvThirtyFive.getText().toString();
			if (currentDate.length() > 0) {
				dayAndWeekNum = CalenderDateList.getWeekNoAndDay(currentDate
						+ monthYear);
			}
			break;

		case R.id.day_thirty_six:
			currentDate = tvThirtySix.getText().toString();
			if (currentDate.length() > 0) {
				dayAndWeekNum = CalenderDateList.getWeekNoAndDay(currentDate
						+ monthYear);
			}
			break;

		case R.id.day_thirty_seven:
			currentDate = tvThirtySeven.getText().toString();
			if (currentDate.length() > 0) {
				dayAndWeekNum = CalenderDateList.getWeekNoAndDay(currentDate
						+ monthYear);
			}
			break;

		case R.id.day_thirty_eight:
			currentDate = tvThirtyEight.getText().toString();
			if (currentDate.length() > 0) {
				dayAndWeekNum = CalenderDateList.getWeekNoAndDay(currentDate
						+ monthYear);
			}
			break;

		case R.id.day_thirty_nine:
			currentDate = tvThirtyNine.getText().toString();
			if (currentDate.length() > 0) {
				dayAndWeekNum = CalenderDateList.getWeekNoAndDay(currentDate
						+ monthYear);
			}
			break;

		case R.id.day_fourty:
			currentDate = tvFourty.getText().toString();
			if (currentDate.length() > 0) {
				dayAndWeekNum = CalenderDateList.getWeekNoAndDay(currentDate
						+ monthYear);
			}
			break;

		case R.id.day_fourty_one:
			currentDate = tvFourtyOne.getText().toString();
			if (currentDate.length() > 0) {
				dayAndWeekNum = CalenderDateList.getWeekNoAndDay(currentDate
						+ monthYear);
			}
			break;

		case R.id.day_fourty_two:
			currentDate = tvFourtyTwo.getText().toString();
			if (currentDate.length() > 0) {
				dayAndWeekNum = CalenderDateList.getWeekNoAndDay(currentDate
						+ monthYear);
			}
			break;

		default:
			break;

		}

		if (dayAndWeekNum != null) {
			// Toast.makeText(getActivity(), dayAndWeekNum + " " + currentDate,
			// Toast.LENGTH_SHORT).show();
			
			Log.e("ABC", String.valueOf(uela.isMVICDialog));
			if (uela.isEditMode() && uela.isMVICDialog == false) 
			{
				changerDialog = new MonthViewImageChangerDialog(uela,
						monthViewFragment, userId,
						dayAndWeekNum.substring(0, 3),
						dayAndWeekNum.substring(3));
				changerDialog.show();
				uela.isMVICDialog = true;
				
			} else {
				//Toast.makeText(getActivity(), dayAndWeekNum + " " + currentDate, Toast.LENGTH_SHORT).show();
				uela.centralDate.set(Calendar.DAY_OF_MONTH, Integer.parseInt(currentDate));
				uela.accessMonthViewToDayView();
			}
		}

	}

	private void bindData() {
		monthYear = CalenderDateList.getCurrentMonthYear();
		int skipCell = CalenderDateList.getDayValue(CalenderDateList.getWeekDayByDate("01" + monthYear));
		int lastDayOfMonth = CalenderDateList.getDayCountOfMonthYear("01" + monthYear);

		boolean hasData = false;
		for (int i = skipCell; i < lastDayOfMonth + skipCell; i++){
			tvList.get(i).setText(String.valueOf(i + 1 - skipCell));
			String date = CalenderDateList.getWeekNoAndDay(i + 1 + monthYear);
			String monthImagePath = db.monthEventList(date.substring(0, 3), date.substring(3), uela.getCurrentUserId());
			if (monthImagePath != null) {
				imageProcessingMonth.setImageWith_loader(dateList.get(i), monthImagePath);
				hasData = true;
			}
		}

		if (hasData) {
			uela.stopAnimation();
		} else {
			uela.startFingerAnimation();
		}
	}
	
	class AsycLoadMonthView extends AsyncTask<String, String, String>
	{	
		int skipCell;
		int lastDayOfMonth;
		
		@SuppressLint("SimpleDateFormat")
		@Override
		protected void onPreExecute() 
		{
			PdSummary = new ProgressDialog(uela);
			PdSummary.setMessage("Loading Monthview...");
			PdSummary.setIndeterminate(false);
			PdSummary.setCancelable(false);
			PdSummary.show();
			
			//monthYear = CalenderDateList.getCurrentMonthYear();
			monthYear = new SimpleDateFormat("-MMM-yyyy").format(uela.centralDate.getTime());
			skipCell = CalenderDateList.getDayValue(CalenderDateList.getWeekDayByDate("01" + monthYear));
			lastDayOfMonth = CalenderDateList.getDayCountOfMonthYear("01" + monthYear);
			
			for (int i = skipCell; i < lastDayOfMonth + skipCell; i++){
				tvList.get(i).setText(String.valueOf(i + 1 - skipCell));
				dateListOfMonth.add(CalenderDateList.getWeekNoAndDay(i + 1 + monthYear));
			}
		}
		
		@Override
		protected String doInBackground(String... params) 
		{
			for(String date : dateListOfMonth)
			{
				imagePath.add(db.monthEventList(date.substring(0, 3), date.substring(3), uela.getCurrentUserId()));
			}
			return "ok";
		}
		
		@Override
		protected void onPostExecute(String result) 
		{
			if(result.equals("ok"))
			{
				boolean hasData = false;
				for (int i = 0; i < imagePath.size(); i++)
				{
					if (imagePath.get(i) != null) {
						imageProcessingMonth.setImageWith_loader(dateList.get(i+skipCell), imagePath.get(i));
						hasData = true;
					}
				}
				if (hasData) {
					uela.stopAnimation();
				} else {
					uela.startFingerAnimation();
				}
				PdSummary.dismiss();
				
			}
			else
			{
				PdSummary.dismiss();
				//Toast.makeText(ref, "No New Data", Toast.LENGTH_SHORT).show();
			}
		}
	}
	
	

}
