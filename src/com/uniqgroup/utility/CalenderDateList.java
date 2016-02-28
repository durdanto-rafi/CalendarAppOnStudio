package com.uniqgroup.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import com.uniqgroup.customui.WeekDayTextView;

import android.annotation.SuppressLint;
import android.util.Log;

@SuppressLint("SimpleDateFormat")
public class CalenderDateList {
	ArrayList<String> dayList = new ArrayList<String>();

	public ArrayList<String> getDateAni(String Day) {

		Calendar cal = Calendar.getInstance();
		String month = new SimpleDateFormat("MMM").format(cal.getTime());

		int year = cal.get(Calendar.YEAR);
		String yearInString = String.valueOf(year);

		return getDate(Day, month, yearInString);

	}

	public static String getCurrentWeekNumber() {

		Calendar cal = GregorianCalendar.getInstance(Locale.FRANCE);
		cal.setTime(new Date());
		int week = cal.get(Calendar.WEEK_OF_YEAR);

		return String.valueOf(week);
	}

	public static String getCurrentWeekDay() {

		Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();
		return new SimpleDateFormat("EE", Locale.ENGLISH)
				.format(date.getTime());
	}

	public static String getWeekNumber(String event_time) {

		SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
		// get current date time with Date()
		Date date = new Date();
		int week_number = 0;

		try {

			Date event_time_date = parser.parse(event_time);
			Date current_date = parser.parse(dateFormat.format(date));

			Log.d("Pass", current_date.toString());
			Log.d("Pass", event_time_date.toString());

			if (current_date.after(event_time_date)) {

				week_number = Calendar.getInstance().get(Calendar.WEEK_OF_YEAR) + 1;

				Log.d("Pass", "YES");
			}

			if (current_date.before(event_time_date)) {

				week_number = Calendar.getInstance().get(Calendar.WEEK_OF_YEAR);

				Log.d("Pass", "NO");
			}

			Log.d("Pass", String.valueOf(week_number));

		} catch (ParseException e) {
			// Invalid date was entered
		}

		return String.valueOf(week_number);
	}

	public static String getDayName(String date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		Date convertedDate = null;
		try {
			convertedDate = dateFormat.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String dayOfWeek = new SimpleDateFormat("EE", Locale.ENGLISH)
				.format(convertedDate);
		return dayOfWeek;
	}

	public ArrayList<String> getDate(String Day) {
		// Get Current Month-Year
		String monthYear = new SimpleDateFormat("-MMM-yyyy").format(new Date());

		// Calculate Last day of current Month
		int lastDayOfMonth = Calendar.getInstance().getActualMaximum(
				Calendar.DAY_OF_MONTH);

		for (int date = 1; date <= lastDayOfMonth; date++) {
			SimpleDateFormat inFormat = new SimpleDateFormat("dd-MMM-yyyy");
			Date providedDate = null;
			try {
				providedDate = inFormat.parse(date + monthYear);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			SimpleDateFormat outFormat = new SimpleDateFormat("EE");
			String dayName = outFormat.format(providedDate);

			if (dayName.equals(Day)) {
				dayList.add(new SimpleDateFormat("dd-MMM-yyyy")
						.format(providedDate));
			}
		}
		return dayList;
		// getDayName(dayList);
	}

	@SuppressLint("SimpleDateFormat")
	public ArrayList<String> getDate(String Day, String Month) {
		// Get Current Year
		String currentYear = new SimpleDateFormat("yyyy").format(new Date());

		// Calculate last date of provided Month
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		Date convertedDate = null;
		try {
			convertedDate = dateFormat.parse("01-" + Month + "-" + currentYear);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar c = Calendar.getInstance();
		c.setTime(convertedDate);
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		SimpleDateFormat monthFormat = new SimpleDateFormat("dd");
		int lastDayOfMonth = Integer.parseInt(monthFormat.format(c.getTime()));

		for (int date = 1; date <= lastDayOfMonth; date++) {
			SimpleDateFormat inFormat = new SimpleDateFormat("dd-MMM-yyyy");
			Date providedDate = null;
			try {
				providedDate = inFormat.parse(date + "-" + Month + "-"
						+ currentYear);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			SimpleDateFormat outFormat = new SimpleDateFormat("EE");
			String dayName = outFormat.format(providedDate);

			if (dayName.equals(Day)) {
				dayList.add(new SimpleDateFormat("dd-MMM-yyyy")
						.format(providedDate));
			}
		}
		return dayList;
	}

	@SuppressLint("SimpleDateFormat")
	public ArrayList<String> getDate(String Day, String Month, String Year) {
		// Calculate last date of provided Month
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		Date convertedDate = null;
		try {
			convertedDate = dateFormat.parse("01-" + Month + "-" + Year);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar c = Calendar.getInstance();
		c.setTime(convertedDate);
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		SimpleDateFormat monthFormat = new SimpleDateFormat("dd");
		int lastDayOfMonth = Integer.parseInt(monthFormat.format(c.getTime()));

		for (int date = 1; date <= lastDayOfMonth; date++) {
			SimpleDateFormat inFormat = new SimpleDateFormat("dd-MMM-yyyy");
			Date providedDate = null;
			try {
				providedDate = inFormat.parse(date + "-" + Month + "-" + Year);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			SimpleDateFormat outFormat = new SimpleDateFormat("EE");
			String dayName = outFormat.format(providedDate);

			if (dayName.equals(Day)) {
				dayList.add(new SimpleDateFormat("dd-MMM-yyyy")
						.format(providedDate));
			}
		}
		return dayList;
	}

	public static int getDayValue(String dayName) {
		ArrayList<String> daySequence = new ArrayList<String>();
		daySequence.add("Mon");
		daySequence.add("Tue");
		daySequence.add("Wed");
		daySequence.add("Thu");
		daySequence.add("Fri");
		daySequence.add("Sat");
		daySequence.add("Sun");

		return daySequence.indexOf(dayName);
	}

	private static int getCurrentYearMaxWeek() {
		Calendar cal1 = GregorianCalendar.getInstance(Locale.FRANCE);
		cal1.set(Calendar.YEAR, Integer.parseInt(new SimpleDateFormat("yyyy")
				.format(new Date())));
		cal1.set(Calendar.MONTH, Calendar.DECEMBER);
		cal1.set(Calendar.DAY_OF_MONTH, 31);

		int ordinalDay = cal1.get(Calendar.DAY_OF_YEAR);
		int weekDay = cal1.get(Calendar.DAY_OF_WEEK) - 1; // Sunday = 0
		int numberOfWeeks = (ordinalDay - weekDay + 10) / 7;
		return numberOfWeeks;
	}

	public static String getDayWiseWeekNum(String dayName, String time) {
		int currentWeek = Integer.parseInt(getCurrentWeekNumber());
		String currentDay = new SimpleDateFormat("EEE").format(new Date());
		Date currentTime = null;
		try {
			currentTime = new SimpleDateFormat("H:m")
					.parse(new SimpleDateFormat("H:m").format(new Date()));
		} catch (ParseException e1) {

			e1.printStackTrace();
		}

		if (getDayValue(dayName) == getDayValue(currentDay)) {
			try {
				if (new SimpleDateFormat("H:m").parse(time).after(currentTime)) {

				} else {
					if (getCurrentYearMaxWeek() != currentWeek) {
						currentWeek++;
					} else {
						currentWeek = 1;
					}

				}
			} catch (ParseException e) {

				e.printStackTrace();
			}
		}

		else if (getDayValue(dayName) < getDayValue(currentDay)) {
			currentWeek++;
		}

		else {

		}
		return String.valueOf(currentWeek);
	}

	public static String getHeaderDateForDayView() {
		return  " Week "+getCurrentWeekNumber()
				+ new SimpleDateFormat(" MMMM yyyy").format(new Date());
	}
	
	public static String getHeaderDateForWeekView() {
		return  new SimpleDateFormat("MMMM yyyy").format(new Date());
	}
	
	public static String getHeaderDateForMonthView() {
		return  " Year "+ new SimpleDateFormat("yyyy").format(new Date());
	}

	public static String getCurrentTime() {
		return new SimpleDateFormat("HH:mm").format(new Date());
	}

	public static String getWeekNoAndDay(String Date) {
		String format = "dd-MMM-yyyy";

		SimpleDateFormat df = new SimpleDateFormat(format);
		Date date = null;
		try {
			date = df.parse(Date);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		Calendar cal = GregorianCalendar.getInstance(Locale.FRANCE);
		cal.setTime(date);
		int week = cal.get(Calendar.WEEK_OF_YEAR);

		String dayOfWeek = new SimpleDateFormat("EE", Locale.ENGLISH)
				.format(date);
		return dayOfWeek + week;
	}

	public static String getWeekDayByDate(String Date) {
		String format = "dd-MMM-yyyy";

		SimpleDateFormat df = new SimpleDateFormat(format);
		Date date = null;
		try {
			date = df.parse(Date);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		String dayOfWeek = new SimpleDateFormat("EE", Locale.ENGLISH)
				.format(date);
		return dayOfWeek;
	}

	public static String getCurrentMonthYear() {
		return new SimpleDateFormat("-MMM-yyyy").format(new Date());

	}

	// By Rokan forMonthName
	public static String getCurrentMonthName() {
		return new SimpleDateFormat("MMMM").format(new Date());
	}

	public static int getDayCountOfMonthYear(String Date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		Date convertedDate = null;
		try {
			convertedDate = dateFormat.parse(Date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar c = Calendar.getInstance();
		c.setTime(convertedDate);
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		SimpleDateFormat monthFormat = new SimpleDateFormat("dd");
		int lastDayOfMonth = Integer.parseInt(monthFormat.format(c.getTime()));

		return lastDayOfMonth;
	}

	public static String getDateByWeekDay(int weekNo, String dayOfWeek)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
		Calendar cal = GregorianCalendar.getInstance(Locale.FRANCE);
		cal.set(Calendar.WEEK_OF_YEAR, weekNo);        
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return sdf.format(cal.getTime());
	}
	// public static String getFirstDay(){
	// Calendar c = new GregorianCalendar();
	// c.set(Calendar.DAY_OF_MONTH, 1);
	// return c.get(Calendar.DAY_OF_WEEK);
	//
	// }
	//

}
