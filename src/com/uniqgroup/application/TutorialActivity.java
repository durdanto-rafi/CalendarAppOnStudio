package com.uniqgroup.application;

import com.uniqgroup.customdialog.UserCreateCustomDialog.PasswordListener;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class TutorialActivity extends Activity {
	/** detect fling **/

	LinearLayout llTutorial, llPassword;
	ImageButton ibtnBack;
	LinearLayout pass0, pass1, pass2, pass3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tutorial);

		llTutorial = (LinearLayout) findViewById(R.id.llTutorial);
		llPassword = (LinearLayout) findViewById(R.id.llPassword);
		ibtnBack = (ImageButton) findViewById(R.id.ibtnBack);
		llTutorial.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent myIntent = new Intent(TutorialActivity.this, TutorialSliderActivity.class);
				TutorialActivity.this.startActivity(myIntent);
				finish();
			}
		});
		
		llPassword.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				showDialog(TutorialActivity.this);
			}
		});

		ibtnBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent myIntent = new Intent(TutorialActivity.this, MainActivity.class);
				TutorialActivity.this.startActivity(myIntent);
				finish();
			}
		});
		
		FullScreencall();
	}

	public void FullScreencall() {
		if (Build.VERSION.SDK_INT < 19) {
			View v = this.getWindow().getDecorView();
			v.setSystemUiVisibility(View.GONE);
		} else {
			// for lower api versions.
			View decorView = getWindow().getDecorView();
			int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
					| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
			decorView.setSystemUiVisibility(uiOptions);
		}
	}
	
	public void showDialog(Activity activity) {
		final Dialog dialog = new Dialog(activity, R.style.CustomAlertDialog);
		dialog.setContentView(R.layout.password_custom_dialog);
		pass1 = (LinearLayout) dialog
				.findViewById(R.id.password1_dialogue_tutorial_ln);
		pass2 = (LinearLayout) dialog
				.findViewById(R.id.password2_dialogue_tutorial_ln);
		pass3 = (LinearLayout) dialog
				.findViewById(R.id.password3_dialogue_tutorial_ln);
		dialog.show();
	}
}
