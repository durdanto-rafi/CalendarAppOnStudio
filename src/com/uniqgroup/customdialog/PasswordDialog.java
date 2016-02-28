package com.uniqgroup.customdialog;

import java.util.ArrayList;
import java.util.Arrays;

import com.uniqgroup.application.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class PasswordDialog extends Dialog implements
		android.view.View.OnClickListener {
	/**
	 * 1. magenta 2.green 3.Blue 4.yellow
	 **/
	int Correct_pattern;
	int[] pattern1 = { 1, 1, 1, 4 };
	int[] pattern2 = { 3, 2, 1, 2 };
	int[] pattern3 = { 4, 3, 2, 1 };

	int answer[] = {};

	int[][] pass_pattern = { { 1, 1, 1, 4 }, { 3, 2, 1, 2 }, { 4, 3, 2, 1 } };

	SwitchONEditMode listener;

	ImageButton greenButton, yellowButton, magentaButton, BlueButton;

	ArrayList<Integer> getPassword = new ArrayList<>();
	Context context;

	public PasswordDialog(Context context, int password_pattern,
			SwitchONEditMode listener) {
		super(context, R.style.CustomAlertDialog);
		setContentView(R.layout.password_enter_dialog);
		this.listener = listener;
		this.context = context;
		setCancelable(false);
		Correct_pattern = password_pattern;

		switch (Correct_pattern) {
		case 1:
			answer = pattern1;
		case 2:
			answer = pattern2;
		case 3:
			answer = pattern3;
			break;
		}

		greenButton = (ImageButton) findViewById(R.id.green_round_IB);
		greenButton.setOnClickListener(this);
		yellowButton = (ImageButton) findViewById(R.id.yellow_round_IB);
		yellowButton.setOnClickListener(this);
		magentaButton = (ImageButton) findViewById(R.id.magenta_round_IB);
		magentaButton.setOnClickListener(this);
		BlueButton = (ImageButton) findViewById(R.id.blue_round_IB);
		BlueButton.setOnClickListener(this);

		FullScreencall();
	}

	/** CHECK IF GREATER THAN 4 **/
	public void checkLimits() {
		if (getPassword.size() <= 3) {

		} else {
			checkAnswer();
		}
	}

	public void checkAnswer() {
		boolean isCorrect = true;

		// answer.toString());
		for (int i = 0; i < 4; i++) {
			if (getPassword.get(i) != pass_pattern[Correct_pattern - 1][i]) {

				Log.d("Pass", "pat::" + getPassword.get(i).toString());
				Log.d("Pass", "ans::" + String.valueOf(answer[i]));

				isCorrect = false;
			}
		}

		// listener.switchEdit_ON(isCorrect);
		// dismiss the dialog here
		if (isCorrect) {
			dismiss();
			listener.switchEdit_ON(isCorrect);
		} else {

			Toast.makeText(context, R.string.wrong_password_alert,
					Toast.LENGTH_SHORT).show();
		}
		getPassword.clear();
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.green_round_IB:
			getPassword.add(2);
			checkLimits();
			break;
		case R.id.yellow_round_IB:
			getPassword.add(4);
			checkLimits();
			break;
		case R.id.magenta_round_IB:
			getPassword.add(1);
			checkLimits();
			break;
		case R.id.blue_round_IB:
			getPassword.add(3);
			checkLimits();
			break;
		default:
			break;
		}
		FullScreencall();
	}

	public interface SwitchONEditMode {
		public void switchEdit_ON(boolean flag);
	}

	public void FullScreencall() {
		if (Build.VERSION.SDK_INT < 19) {
			View v = this.getWindow().getDecorView();
			v.setSystemUiVisibility(View.GONE);
		} else {
			// for lower api versions.
			View decorView = getWindow().getDecorView();
			int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
			decorView.setSystemUiVisibility(uiOptions);
		}
	}
}
