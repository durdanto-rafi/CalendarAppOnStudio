package com.uniqgroup.customdialog;

import com.uniqgroup.application.R;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

public class FragmentNameChangeDialog extends Dialog {

	SwitchONEditMode listener;

	Context context;

	public FragmentNameChangeDialog(Context context, SwitchONEditMode listener) {
		super(context);
		this.context = context;
		this.listener = listener;

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setCancelable(false);
		setContentView(R.layout.fragment_name_changer_dialog);
		
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
			int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
					| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
			decorView.setSystemUiVisibility(uiOptions);
		}
	}

}
