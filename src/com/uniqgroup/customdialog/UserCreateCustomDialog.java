package com.uniqgroup.customdialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.uniqgroup.application.R;
import com.uniqgroup.application.UserCreateActivity;
import com.uniqgroup.pojo.PasswordHash;
import com.uniqgroup.pojo.User;
import com.uniqgroup.utility.CalendarViews;
import com.uniqgroup.utility.ImageProcessing;
import com.uniqgroup.utility.PasswordStorage;

public class UserCreateCustomDialog extends Dialog implements
		android.view.View.OnClickListener {

	Context ctx;
	public Activity c;
	UserCreateActivity user_act;
	public Dialog d;
	public ImageButton ibtnGallary, ibtnCamera, ibtnFileChooser;

	ImageSearchCustomDialog imageSearchDialog = null;
	public EditText user_name;
	ImageView ivProfilePic, ivOk, ivClose, ivPasswordChanger, ivDefaultViewChanger;

	Boolean setImage = false;
	String user_password = PasswordStorage.getPassByIndex(0), 
			selectedView = CalendarViews.dayView;

	UserCreateCustomDialog dlgref;

	int curr_indx_pass = 0;
	int curr_indx_frag = 0;

	LinearLayout pass0, pass1, pass2, pass3;
	LinearLayout dayViewRow, weekViewRow, monthViewRow;
	User current_user;
	public ImageProcessing imgProc;

	public UserCreateCustomDialog(Activity a, UserCreateActivity uca,
			Context context) {
		super(a);
		this.c = a;
		this.user_act = uca;
		this.dlgref = this;
		this.ctx = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		DisplayMetrics metrics = c.getResources().getDisplayMetrics();
		int screenWidth = (int) (metrics.widthPixels * 0.80);
		int screenHeight = (int) (metrics.heightPixels * 0.80);

		setCancelable(false);
		FullScreencall();

		getWindow().setLayout(screenWidth, screenHeight);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		setContentView(R.layout.user_create_custom_dialog);

		imgProc = new ImageProcessing(ctx);
		ivOk = (ImageView) findViewById(R.id.btnOk);
		ivClose = (ImageView) findViewById(R.id.btnNo);
		ivPasswordChanger = (ImageView) findViewById(R.id.user_password);
		ivDefaultViewChanger = (ImageView) findViewById(R.id.ivFragmentChange);

		ivProfilePic = (ImageView) findViewById(R.id.profile_pic_imgv);

		ibtnGallary = (ImageButton) findViewById(R.id.gallery_image_bt);
		ibtnCamera = (ImageButton) findViewById(R.id.camera_image_bt);
		ibtnFileChooser = (ImageButton) findViewById(R.id.sdcard_image_bt);

		user_name = (EditText) findViewById(R.id.user_name_edtxt);

		ivOk.setOnClickListener(this);
		ivClose.setOnClickListener(this);

		ibtnGallary.setOnClickListener(this);
		ibtnCamera.setOnClickListener(this);
		ibtnFileChooser.setOnClickListener(this);
		ivPasswordChanger.setOnClickListener(this);
		ivDefaultViewChanger.setOnClickListener(this);

		current_user = user_act.getCurrentUser();
		if (current_user!=null) {
			setUserEditRes();
		}

		FullScreencall();

	}

	// Editing user name and password
	public void setUserEditRes() {
		
		user_name.setText(current_user.getName());
		user_password = getCurrentPassword();
		selectedView = current_user.get_default_view();
		imgProc.setImageWith_loader(ivProfilePic, current_user.getProfilePic());
		
		
		switch (selectedView) {
		case CalendarViews.dayView:
			ivDefaultViewChanger.setImageResource(R.drawable.ic_day_view);
			break;

		case CalendarViews.weekView:
			ivDefaultViewChanger.setImageResource(R.drawable.ic_week_view);
			break;

		case CalendarViews.monthView:
			ivDefaultViewChanger.setImageResource(R.drawable.ic_month_view);
			break;
		}

		setPasswordIcon();
		//imgProc.setImageWith_loader(ivProfilePic, current_user.getProfilePic());
		// imgvw.setImageBitmap(imgProc.getImage(current_user.getProfilePic()));
		setImage = true;
	}

	public void setFragmentIcon() {
		switch (curr_indx_frag) {
		case 0:
			ivDefaultViewChanger.setImageResource(R.drawable.ic_day_view);
			selectedView = CalendarViews.dayView;
			break;

		case 1:
			ivDefaultViewChanger.setImageResource(R.drawable.ic_day_view);
			selectedView = CalendarViews.dayView;
			break;

		case 2:
			ivDefaultViewChanger.setImageResource(R.drawable.ic_week_view);
			selectedView = CalendarViews.weekView;
			break;

		case 3:
			ivDefaultViewChanger.setImageResource(R.drawable.ic_month_view);
			selectedView = CalendarViews.monthView;
			break;
		}

	}

	// setting password icon based on password index 0,1,2,3
	public void setPasswordIcon() {
		switch (curr_indx_pass) {
		case 0:
			ivPasswordChanger.setImageResource(R.drawable.ic_password_off);
			break;
		case 1:
			ivPasswordChanger.setImageResource(R.drawable.ic_password_1);
			break;
		case 2:
			ivPasswordChanger.setImageResource(R.drawable.ic_password_2);
			break;
		case 3:
			ivPasswordChanger.setImageResource(R.drawable.ic_password_3);
			break;
		}
	}

	public String getCurrentPassword() {
		for (int i = 0; i < PasswordStorage.pass_list.length; i++) {
			try {
				if (PasswordHash.validatePassword(PasswordStorage.pass_list[i],
						user_act.getCurrentUser().getPassword())) {
					curr_indx_pass = i;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return PasswordStorage.pass_list[curr_indx_pass];
	}

	// selecting a password Row
	public void setPasswordRowVackGround() {

		switch (curr_indx_pass) {

		case 0:
			pass0.setBackgroundResource(R.drawable.round_top_shape_selected);
			break;
		case 1:
			pass1.setBackgroundResource(R.color.light_purple);
			break;
		case 2:
			pass2.setBackgroundResource(R.color.light_purple);
			break;
		case 3:
			pass3.setBackgroundResource(R.drawable.round_bottom_shape_selected);
			break;
		}
	}

	// selecting a Fragment Row By Rokan
	public void setFragmentRowBackGround() {
		switch (curr_indx_frag) {
		case 1:
			dayViewRow.setBackgroundResource(R.drawable.round_top_shape_selected);
			break;

		case 2:
			weekViewRow.setBackgroundResource(R.color.light_purple);
			break;

		case 3:
			monthViewRow.setBackgroundResource(R.drawable.round_bottom_shape_selected);
			break;

		}
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.sdcard_image_bt) {
			if(user_act.isImageSearchDialogShow==false)
			{
				user_act.imageSearchDialog = new ImageSearchCustomDialog(c,user_act);
				user_act.imageSearchDialog.show();
				user_act.isImageSearchDialogShow = true;
			}
			
		} else if (id == R.id.camera_image_bt) {
			user_act.loadImageCamera();
		} else if (id == R.id.gallery_image_bt) {
			user_act.loadImageGallery();
		} else if (id == R.id.btnOk) {
			if (validateUserCreateDialog()) {
				dismiss();
				user_act.FullScreencall();
				BitmapDrawable btmpDr = (BitmapDrawable) ivProfilePic.getDrawable();
				user_act.saveUser(user_name.getText().toString(), user_password, btmpDr, selectedView);
				user_act.isUserManageDialogShow = false;
			}
		} else if (id == R.id.btnNo) {
			dismiss();
			user_act.FullScreencall();
			user_act.isUserManageDialogShow = false;
		} else if (id == R.id.user_password) {
			showDialog(c);
		} else if (id == R.id.ivFragmentChange) {
			showFragmentNameChangeDialog(c);
		}
	}

	// this one will validate user create form
	public Boolean validateUserCreateDialog() {
		int user_name_length = user_name.getText().length();
		if (user_name_length == 0) {
			Toast.makeText(c, c.getResources().getString(R.string.userNameValidation), Toast.LENGTH_SHORT).show();
		}
		if (!setImage) {
			Toast.makeText(c, c.getResources().getString(R.string.userPricureValidation), Toast.LENGTH_SHORT).show();
		}

		if (user_name_length > 0 && setImage) {

			return true;
		}
		return false;
	}

	// display password dialog
	public void showDialog(Activity activity) {
		final Dialog dialog = new Dialog(activity, R.style.CustomAlertDialog);
		FullScreencall();
		dialog.setContentView(R.layout.password_custom_dialog);
		
		pass0 = (LinearLayout) dialog.findViewById(R.id.password_default_dialogue_tutorial_ln);
		
		pass1 = (LinearLayout) dialog.findViewById(R.id.password1_dialogue_tutorial_ln);
		
		
		pass2 = (LinearLayout) dialog
				.findViewById(R.id.password2_dialogue_tutorial_ln);
		pass3 = (LinearLayout) dialog
				.findViewById(R.id.password3_dialogue_tutorial_ln);

		pass0.setOnClickListener(new PasswordListener(dialog, this, 0));
		pass1.setOnClickListener(new PasswordListener(dialog, this, 1));
		pass2.setOnClickListener(new PasswordListener(dialog, this, 2));
		pass3.setOnClickListener(new PasswordListener(dialog, this, 3));

		setPasswordRowVackGround();
		dialog.show();

	}

	// display FragmentName dialog by Rokan
	public void showFragmentNameChangeDialog(Activity activity) {
		final Dialog dialog = new Dialog(activity, R.style.CustomAlertDialog);
		FullScreencall();
		dialog.setContentView(R.layout.fragment_name_changer_dialog);
		dayViewRow = (LinearLayout) dialog
				.findViewById(R.id.lnDayViewChangeDialog);
		weekViewRow = (LinearLayout) dialog
				.findViewById(R.id.lnWeekViewChangeDialog);
		monthViewRow = (LinearLayout) dialog
				.findViewById(R.id.lnMonthViewChangeDialog);

		dayViewRow.setOnClickListener(new FragmentListener(dialog, this, 1));
		weekViewRow.setOnClickListener(new FragmentListener(dialog, this, 2));
		monthViewRow.setOnClickListener(new FragmentListener(dialog, this, 3));

		setFragmentRowBackGround();
		dialog.show();

	}

	public class PasswordListener implements View.OnClickListener {
		UserCreateCustomDialog dlgref;
		int index;
		Dialog dlg;

		public PasswordListener(Dialog dlg, UserCreateCustomDialog dlgref1,
				int index1) {
			this.dlgref = dlgref1;
			this.index = index1;
			this.dlg = dlg;
		}

		@Override
		public void onClick(View arg1) {
			curr_indx_pass = index;
			setPasswordRowVackGround();
			dlgref.user_password = PasswordStorage.getPassByIndex(index);
			dlgref.setPasswordIcon();
			dlg.dismiss();
		}
	}

	// By Rokan
	public class FragmentListener implements View.OnClickListener {

		UserCreateCustomDialog userCreateCustomDialog;
		int fragIndex;
		Dialog dialog;

		public FragmentListener(Dialog dialog,
				UserCreateCustomDialog userCreateCustomDialog, int fragIndex) {
			this.dialog = dialog;
			this.fragIndex = fragIndex;
			userCreateCustomDialog = dlgref;
		}

		@Override
		public void onClick(View v) {
			curr_indx_frag = fragIndex;
			setFragmentRowBackGround();
			dlgref.setFragmentIcon();
			dialog.dismiss();

		}

	}

	// will set image profile
	public void setprofileImage(Drawable profileImage) {
		ivProfilePic.setImageDrawable(profileImage);
		setImage = true;
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