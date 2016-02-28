package com.uniqgroup.customdialog;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

import com.uniqgroup.application.EventManageActivity;
import com.uniqgroup.application.R;
import com.uniqgroup.application.UserCreateActivity;
import com.uniqgroup.utility.ImageProcessing;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

@SuppressLint("SetJavaScriptEnabled")
public class ImageSearchCustomDialog extends Dialog implements
		android.view.View.OnClickListener {

	public Activity c;
	EventManageActivity event_act;
	EventCreateCustomDialog event_dialog;
	UserCreateCustomDialog userDialog;
	UserCreateActivity user_act;

	ImageProcessing imgProc;
	public Dialog dialog;
	private WebView webView;
	private ImageButton ibtnTake, ibtnGoBack;
	private String appImagePath = null;
	// private File mFileTemp;
	public static final int REQUEST_CODE_CROP_IMAGE = 0x3;
	public static final String TEMP_PHOTO_FILE_NAME = "temp_photo.jpg";

	public ImageSearchCustomDialog(Activity a, EventManageActivity eca) {
		super(a, R.style.CustomAlertDialog);
		this.c = a;
		this.event_act = eca;
	}

	public ImageSearchCustomDialog(Activity a, UserCreateActivity user_act) {
		super(a, R.style.CustomAlertDialog);
		this.c = a;
		this.user_act = user_act;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		DisplayMetrics metrics = c.getResources().getDisplayMetrics();
		int screenWidth = (int) (metrics.widthPixels * 0.80);
		int screenHeight = (int) (metrics.heightPixels * 0.80);
		FullScreencall();
		getWindow().setLayout(screenWidth, screenHeight);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		setContentView(R.layout.image_search_costom_dialog);
		setCancelable(true);
		
		
		webView = (WebView) findViewById(R.id.wvImageSearch);
		ibtnTake = (ImageButton) findViewById(R.id.ibtnTake);
		ibtnGoBack = (ImageButton) findViewById(R.id.ibtnGoBack);
		LoadImageSearchData();
		ibtnTake.setOnClickListener(this);
		ibtnGoBack.setOnClickListener(this);
		imgProc = new ImageProcessing(c);
		//appImagePath = "/Android/Data/"+ c.getPackageName()+"/Images/";
		appImagePath = imgProc.getImageDir();
		
		this.setOnDismissListener(new OnDismissListener() {
			
			@Override
			public void onDismiss(DialogInterface dialog) {
				if (event_act != null) 
				{
					event_act.isImageSearchDialogShow = false;
				}
				else
				{
					user_act.isImageSearchDialogShow = false;
				}
			}
		});
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

	private void LoadImageSearchData() {
		webView.getSettings().setJavaScriptEnabled(true);
		webView.setWebChromeClient(new WebChromeClient());
		webView.setWebViewClient(new WebViewClient() {

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
		});
		webView.loadUrl("https://www.google.com/search?tbm=isch&q=image..");
	}

	private void takeScreenshot() {
		Date now = new Date();
		android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

		try {
			View v = webView;
			v.setDrawingCacheEnabled(true);
			Bitmap bitmap = Bitmap.createBitmap(v.getDrawingCache());
			v.setDrawingCacheEnabled(false);

			if (event_act != null) 
			{
				//appImagePath = "/Android/Data/"+ event_act.getPackageName()+"/Images/";
				String state = Environment.getExternalStorageState();
				if (Environment.MEDIA_MOUNTED.equals(state)) {
					event_act.mFileTemp = new File(
							Environment.getExternalStorageDirectory() + appImagePath,
							TEMP_PHOTO_FILE_NAME);
				} else {
					event_act.mFileTemp = new File(c.getFilesDir(),
							TEMP_PHOTO_FILE_NAME);
				}

				FileOutputStream outputStream = new FileOutputStream(
						event_act.mFileTemp);
				int quality = 100;
				bitmap.compress(Bitmap.CompressFormat.JPEG, quality,
						outputStream);
				outputStream.flush();
				outputStream.close();

				event_act.startCropImage(event_act.mFileTemp);

			} 
			else
			{
				//appImagePath = "/Android/Data/"+ user_act.getPackageName()+"/Images/";
				String stateUser = Environment.getExternalStorageState();
				if (Environment.MEDIA_MOUNTED.equals(stateUser)) {
					user_act.mFileTemp = new File(
							Environment.getExternalStorageDirectory() + appImagePath,
							TEMP_PHOTO_FILE_NAME);
				} else {
					user_act.mFileTemp = new File(c.getFilesDir(),
							TEMP_PHOTO_FILE_NAME);
				}

				FileOutputStream outputStream = new FileOutputStream(
						user_act.mFileTemp);
				int quality = 100;
				bitmap.compress(Bitmap.CompressFormat.JPEG, quality,
						outputStream);
				outputStream.flush();
				outputStream.close();

				//Toast.makeText(c, user_act.mFileTemp.getAbsolutePath(),Toast.LENGTH_SHORT).show();
				user_act.startCropImageUser(user_act.mFileTemp);
			}

		} catch (Throwable e) {
			// Several error may come out with file handling or OOM
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {

		if (v.getId() == R.id.ibtnTake) {
			takeScreenshot();
		}
		else if (v.getId() == R.id.ibtnGoBack) {
			webView.goBack();
		}

	}

}
