package com.uniqgroup.application;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import net.londatiga.android.ActionItem;
import net.londatiga.android.QuickAction;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import br.com.thinkti.android.filechooser.FileChooser;

import com.uniqgroup.customdialog.EventCreateCustomDialog;
import com.uniqgroup.customdialog.ImageSearchCustomDialog;
import com.uniqgroup.database.DatabaseHandler;
import com.uniqgroup.pojo.Event;
import com.uniqgroup.pojo.EventAlarm;
import com.uniqgroup.pojo.EventSequence;
import com.uniqgroup.pojo.User;
import com.uniqgroup.utility.CalenderDateList;
import com.uniqgroup.utility.GridClickManupulator_EventCreate;
import com.uniqgroup.utility.ImageProcessing;
import com.uniqgroup.utility.InternalStorageContentProvider;

import eu.janmuller.android.simplecropimage.CropImage;

/**
 * Created by RAFI on 06/01/2016.
 */

public class EventManageActivity extends Activity implements OnClickListener {

    // Initializing UI Elements
    CheckBox chkFri, chkSat, chkSun, chkMon, chkTue, chkWed, chkThu, chkAll;
    ImageButton ibtnBack, ibtnGallary, ibtnCamera, ibtnImageSearch, ibtnDayPrize, ibtnWeekPrize;
    EditText edActivityName;
    TextView tvDisplayTime, avatarName;
    LinearLayout llMain;
    ImageView avatarPic, currentGridBox, ivSave, ivEditAction, ivDeleteAction,
            event_cancel_imgv, main_grid_img, btnChangeTime, ivActivity,
            ivAction1, ivAction2, ivAction3, ivAction4, ivAction5, ivAction6,
            ivAction7, ivAction8, ivAction9, ivAction10, ivAction11,
            ivAction12, ivCheckSingleTap, currentActionIvForDelete,
            ivDailyPrize, ivWeeklyPrize;
    ToggleButton tglALarmOnOff;

    // Initializing References
    EventCreateCustomDialog actionEditDialog = null;
    public ImageSearchCustomDialog imageSearchDialog = null;
    DatabaseHandler db;
    User currentUser;
    EventManageActivity ref;
    ImageProcessing imgProc;
    Event event;
    EventSequence eventSeq;
    // GridClickManupulator_EventCreate mode_checker;

    // Initializing Variables
    ArrayList<Integer> dayList = new ArrayList<Integer>();
    HashMap<Integer, EventSequence> event_seq_list;
    ArrayList<ImageView> eventActions;
    private static final int SELECT_PICTURE = 1, CAMERA_REQUEST = 1,
            FILE_CHOOSER = 1, TIME_DIALOG_ID = 999, ID_UP = 1, ID_DOWN = 2,
            ID_CAMERA = 3, ID_GALLERY = 4, ID_SDCARD = 5, ID_CHROME = 6,
            CLOSE = 9;

    int intent_source = 0, current_grid_pos = 0, hour, minute,
            load_video_from_dialog, eventId = 0, eventAlarmId = 0,
            currentActionIdForDelete = 0;

    public static final int REQUEST_CODE_CROP_IMAGE = 0x7;
    public static final int REQUEST_CODE_TAKE_PICTURE = 0x8;

    String selectedImagePath, currentUserId, returnView;
    Class<?> returnIntentPath;
    Bitmap preview_bitmap;
    QuickAction quickAction;
    Boolean hsMainGridImage = false;
    Uri mCapturedImageURI;
    public File mFileTemp;
    public static final String TEMP_PHOTO_FILE_NAME = "temp_photo.jpg";
    private String appImagePath = null;
    // public boolean isDialogShow = false;
    public boolean isImageSearchDialogShow = false;
    public boolean isActionManageDialogShow = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FullScreencall();
        setContentView(R.layout.activity_event_create);

        ref = this;
        db = new DatabaseHandler(this);

        avatarName = (TextView) findViewById(R.id.user_avatar_name_event_list_imgv);
        avatarPic = (ImageView) findViewById(R.id.user_avatar_event_list_imgv);

        chkFri = (CheckBox) findViewById(R.id.chkFri);
        chkSat = (CheckBox) findViewById(R.id.chkSat);
        chkSun = (CheckBox) findViewById(R.id.chkSun);
        chkMon = (CheckBox) findViewById(R.id.chkMon);
        chkTue = (CheckBox) findViewById(R.id.chkTue);
        chkWed = (CheckBox) findViewById(R.id.chkWed);
        chkThu = (CheckBox) findViewById(R.id.chkThu);
        chkAll = (CheckBox) findViewById(R.id.chkAll);

        tglALarmOnOff = (ToggleButton) findViewById(R.id.tglALarmOnOff);

        // all the grids
        ivActivity = (ImageView) findViewById(R.id.event_main_image);
        ivAction1 = (ImageView) findViewById(R.id.event_seq1);
        ivAction2 = (ImageView) findViewById(R.id.event_seq2);
        ivAction3 = (ImageView) findViewById(R.id.event_seq3);
        ivAction4 = (ImageView) findViewById(R.id.event_seq4);
        ivAction5 = (ImageView) findViewById(R.id.event_seq5);
        ivAction6 = (ImageView) findViewById(R.id.event_seq6);
        ivAction7 = (ImageView) findViewById(R.id.event_seq7);
        ivAction8 = (ImageView) findViewById(R.id.event_seq8);
        ivAction9 = (ImageView) findViewById(R.id.event_seq9);
        ivAction10 = (ImageView) findViewById(R.id.event_seq10);
        ivAction11 = (ImageView) findViewById(R.id.event_seq11);
        ivAction12 = (ImageView) findViewById(R.id.event_seq12);

        edActivityName = (EditText) findViewById(R.id.event_description);

        ivDailyPrize = (ImageView) findViewById(R.id.ivDailyPrize);
        ivWeeklyPrize = (ImageView) findViewById(R.id.ivWeeklyPrize);

        ibtnGallary = (ImageButton) findViewById(R.id.gallery_image_bt);
        ibtnCamera = (ImageButton) findViewById(R.id.camera_image_bt);
        ibtnImageSearch = (ImageButton) findViewById(R.id.sdcard_image_bt);
        ivSave = (ImageView) findViewById(R.id.event_add_imgv);
        ivEditAction = (ImageView) findViewById(R.id.event_edit_imgv);
        ivDeleteAction = (ImageView) findViewById(R.id.event_delete_imgv);
        tvDisplayTime = (TextView) findViewById(R.id.tvTime);
        btnChangeTime = (ImageView) findViewById(R.id.btnChangeTime);

        ibtnBack = (ImageButton) findViewById(R.id.ibtnBack);
        llMain = (LinearLayout) findViewById(R.id.llMain);
        imgProc = new ImageProcessing(this);

        eventActions = new ArrayList<ImageView>();
        eventActions.add(ivAction1);
        eventActions.add(ivAction2);
        eventActions.add(ivAction3);
        eventActions.add(ivAction4);
        eventActions.add(ivAction5);
        eventActions.add(ivAction6);
        eventActions.add(ivAction7);
        eventActions.add(ivAction8);
        eventActions.add(ivAction9);
        eventActions.add(ivAction10);
        eventActions.add(ivAction11);
        eventActions.add(ivAction12);

        setCurrentTime();
        setOnClickListenToTime();
        imageSelectorOption();
        setOnTouchListnerToImageViews();

        Intent intent = getIntent();
        currentUserId = intent.getStringExtra("user_id");
        returnView = intent.getStringExtra("returnView");
        try {
            returnIntentPath = Class.forName(intent
                    .getStringExtra("returnPath"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (intent.getStringExtra("flag").equals("Update")) {
            eventId = intent.getIntExtra("event_id", 0);
            getData();
        } else {
            allWeek();
        }
        displayAvatarInfo();

        edActivityName.setOnEditorActionListener(edActivitynameEditorListner);
        ibtnBack.setOnClickListener(ibtnBackOnClickListner);
        llMain.setOnTouchListener(llMainOnTouchListner);
        ivEditAction.setOnClickListener(ivEditOnClickListner);
        ivDeleteAction.setOnClickListener(ivDeleteOnClickListner);
        ibtnGallary.setOnClickListener(galleryOnClickListner);
        ibtnCamera.setOnClickListener(cameraOnClickListner);
        ibtnImageSearch.setOnClickListener(sdcardOnClickListner);
        // ibtnDayPrize.setOnClickListener(this);
        ivDailyPrize.setOnClickListener(this);
        ivWeeklyPrize.setOnClickListener(this);

        addButtonlistener();
        appImagePath = imgProc.getImageDir();

    }

    GridClickManupulator_EventCreate touchModeChecker = new GridClickManupulator_EventCreate();

    private void displayAvatarInfo() {
        currentUser = db.getUser(Integer.parseInt(currentUserId));
        avatarName.setText(currentUser.getName());
        imgProc.setImageWith_loader(avatarPic, currentUser.getProfilePic());
    }

    private void setCurrentTime() {
        tvDisplayTime.setText(CalenderDateList.getCurrentTime());
    }

    private void setOnClickListenToTime() {
        btnChangeTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(ref,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker,
                                                  int selectedHour, int selectedMinute) {
                                tvDisplayTime.setText(new DecimalFormat("00")
                                        .format(selectedHour)
                                        + ":"
                                        + new DecimalFormat("00")
                                        .format(selectedMinute));
                                FullScreencall();
                            }
                        }, Integer.parseInt(tvDisplayTime.getText().toString()
                        .substring(0, 2)), Integer
                        .parseInt(tvDisplayTime.getText().toString()
                                .substring(3, 5)), true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });
    }

    private void imageSelectorOption() {
        ActionItem cameraItem = new ActionItem(ID_CAMERA, "", getResources()
                .getDrawable(R.drawable.ic_photo));
        ActionItem galleryItem = new ActionItem(ID_GALLERY, "", getResources()
                .getDrawable(R.drawable.ic_picture));
        /*
		 * ActionItem sdcardItem = new ActionItem(ID_SDCARD, "", getResources()
		 * .getDrawable(R.drawable.ic_folder));
		 */
        ActionItem chromeItem = new ActionItem(ID_CHROME, "", getResources()
                .getDrawable(R.drawable.ic_imagefrominternet));
        ActionItem close = new ActionItem(CLOSE, "", getResources()
                .getDrawable(R.drawable.ic_cross));

        quickAction = new QuickAction(this, QuickAction.HORIZONTAL);

        quickAction.addActionItem(cameraItem);
        quickAction.addActionItem(galleryItem);
		/* quickAction.addActionItem(sdcardItem); */
        quickAction.addActionItem(chromeItem);
        quickAction.addActionItem(close);

        quickAction
                .setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {
                    @Override
                    public void onItemClick(QuickAction source, int pos,
                                            int actionId) {
                        ActionItem actionItem = quickAction.getActionItem(pos);
                        quickActionOptions(actionItem.getActionId());
                    }
                });
    }

    public void quickActionOptions(int actionId) {

        switch (actionId) {
            case ID_CAMERA:
                loadImageCamera();
                break;
            case ID_GALLERY:
                loadImageGallery();
                break;
            case ID_SDCARD:
                loadImageStorage();
                break;
            case ID_CHROME:
                if (isImageSearchDialogShow == false) {
                    imageSearchDialog = new ImageSearchCustomDialog(ref, ref);
                    imageSearchDialog.show();
                    isImageSearchDialogShow = true;
                }
                break;
            case CLOSE:
                if (currentGridBox == ivDailyPrize) {
                    currentGridBox.setImageResource(R.drawable.day_prize_selector);
                    currentGridBox.setBackground(null);

                    event = new Event();
                    if (ivActivity.getTag() != null) {
                        event = (Event) ivActivity.getTag();
                        event.set_daily_prize(null);
                        ivActivity.setTag(event);
                    }
                } else if (currentGridBox == ivWeeklyPrize) {
                    currentGridBox.setImageResource(R.drawable.week_prize_selector);
                    currentGridBox.setBackground(null);

                    if (ivActivity.getTag() != null) {
                        event = (Event) ivActivity.getTag();
                        event.set_weekly_prize(null);
                        ivActivity.setTag(event);
                    }
                }
                break;
            default:
                break;
        }
    }

    public void loadImageGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),
                SELECT_PICTURE);
        intent_source = 1;
    }

    public void loadImageCamera() {
		/*
		 * Intent cameraIntent = new
		 * Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		 * startActivityForResult(cameraIntent, CAMERA_REQUEST); intent_source =
		 * 2;
		 */

        String state1 = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state1)) {
            mFileTemp = new File(Environment.getExternalStorageDirectory()
                    + appImagePath, TEMP_PHOTO_FILE_NAME);
        } else {
            mFileTemp = new File(getFilesDir() + appImagePath,
                    TEMP_PHOTO_FILE_NAME);
        }

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        try {
            Uri mImageCaptureUri = null;
            String state2 = Environment.getExternalStorageState();
            if (Environment.MEDIA_MOUNTED.equals(state2)) {
                mImageCaptureUri = Uri.fromFile(mFileTemp);
            } else {

                mImageCaptureUri = InternalStorageContentProvider.CONTENT_URI;
            }
            intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT,
                    mImageCaptureUri);
            intent.putExtra("return-data", true);
            startActivityForResult(intent, REQUEST_CODE_TAKE_PICTURE);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(ref, "Hey", Toast.LENGTH_SHORT).show();
        }
    }

    public void loadImageStorage() {
        Intent intent = new Intent(this, FileChooser.class);
        ArrayList<String> extensions = new ArrayList<String>();
        extensions.add(".png");
        extensions.add(".jpg");
        extensions.add(".mp3");
        extensions.add(".flac");
        extensions.add(".wav");
        intent.putStringArrayListExtra("filterFileExtension", extensions);
        startActivityForResult(intent, FILE_CHOOSER);
        intent_source = 3;
    }

    private void setOnTouchListnerToImageViews() {
        ivActivity.setOnTouchListener(onTouchImageViews);

        ivAction1.setOnTouchListener(onTouchImageViews);
        ivAction2.setOnTouchListener(onTouchImageViews);
        ivAction3.setOnTouchListener(onTouchImageViews);
        ivAction4.setOnTouchListener(onTouchImageViews);
        ivAction5.setOnTouchListener(onTouchImageViews);
        ivAction6.setOnTouchListener(onTouchImageViews);
        ivAction7.setOnTouchListener(onTouchImageViews);
        ivAction8.setOnTouchListener(onTouchImageViews);
        ivAction9.setOnTouchListener(onTouchImageViews);
        ivAction10.setOnTouchListener(onTouchImageViews);

        ivAction11.setOnTouchListener(onTouchImageViews);
        ivAction12.setOnTouchListener(onTouchImageViews);
    }

    OnTouchListener onTouchImageViews = new OnTouchListener() {
        int id = 0;
        View v = null;
        View previousForSingleTapView = null;
        GestureDetector gestureDetector = new GestureDetector(getApplication(),
                new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public boolean onDoubleTap(MotionEvent e) {
                        doubleTapedImageViewListner(v, id);
                        touchModeChecker.setMode(1);
                        handleSingleTap(v, previousForSingleTapView, id);
                        previousForSingleTapView = v;
                        return super.onDoubleTap(e);
                    }

                    @Override
                    public boolean onSingleTapConfirmed(MotionEvent e) {
                        if (touchModeChecker.getMode() != 2) {
                            touchModeChecker.setMode(1);
                            handleSingleTap(v, previousForSingleTapView, id);
                            previousForSingleTapView = v;
                        } else {
                            handleSingleTapAfterLongTap(v, id);
                        }
                        return true;
                    }

                    @Override
                    public void onLongPress(MotionEvent e) {
                        ImageView iv = (ImageView) v;
                        int i = iv.getId();
                        if (i != R.id.event_main_image) {
                            touchModeChecker.setMode(2);
                            handleLongTap(v, previousForSingleTapView, id);
                        }
                    }
                });

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            id = v.getId();
            this.v = v;
            gestureDetector.onTouchEvent(event);
            return true;
        }
    };

    public void doubleTapedImageViewListner(View v, int id) {

        currentGridBox = (ImageView) v;

        switch (v.getId()) {
            case R.id.event_seq1:
                current_grid_pos = 1;
                break;
            case R.id.event_seq2:
                current_grid_pos = 2;
                break;
            case R.id.event_seq3:
                current_grid_pos = 3;
                break;
            case R.id.event_seq4:
                current_grid_pos = 4;
                break;
            case R.id.event_seq5:
                current_grid_pos = 5;
                break;
            case R.id.event_seq6:
                current_grid_pos = 6;
                break;
            case R.id.event_seq7:
                current_grid_pos = 7;
                break;
            case R.id.event_seq8:
                current_grid_pos = 8;
                break;
            case R.id.event_seq9:
                current_grid_pos = 9;
                break;
            case R.id.event_seq10:
                current_grid_pos = 10;
                break;
            case R.id.event_seq11:
                current_grid_pos = 11;
                break;
            case R.id.event_seq12:
                current_grid_pos = 12;
                break;
            case R.id.event_main_image:
                current_grid_pos = 13;
                // quickAction.show(v);
                break;
            default:
                break;
        }
    }

    public void handleSingleTap(View v, View prev_v, int selectedImageViewId) {
        ImageView ivCurrent = (ImageView) v;
        if (ivCurrent.getId() == ivActivity.getId()) {
            return;
        }
        if (ivCheckSingleTap != null) {
            if (ivCheckSingleTap.getId() == ivCurrent.getId()
                    && isActionManageDialogShow == false) {
                ImageView ivAction = ivCheckSingleTap;
                actionEditDialog = new EventCreateCustomDialog(ref, ref,
                        touchModeChecker.getClickedNumber(touchModeChecker
                                .getGrid_selected_ID()), ivAction, eventSeq);
                actionEditDialog.show();
                isActionManageDialogShow = true;
                return;
            }
        }

        if (prev_v != null) {
            ImageView iv_prev = (ImageView) prev_v;
            iv_prev.setBackgroundResource(R.drawable.shadow);
            touchModeChecker.getDeleteable_list().clear();
        }
        ivCheckSingleTap = ivCurrent;
        ivCurrent.setBackgroundResource(R.drawable.round_corner_purple);
        touchModeChecker.setGrid_selected_ID(selectedImageViewId);
        if (ivCurrent.getTag() != null) {
            eventSeq = new EventSequence();
            eventSeq = (EventSequence) ivCurrent.getTag();
            ivDeleteAction.setVisibility(View.VISIBLE);
            currentActionIvForDelete = ivCurrent;
            touchModeChecker.addTo_List(selectedImageViewId);
        } else {
            ivDeleteAction.setVisibility(View.INVISIBLE);
        }
    }

    public void handleSingleTapAfterLongTap(View v, int selectedImageViewId) {
        ImageView ivCurrent = (ImageView) v;
        if (touchModeChecker.getDeleteable_list().contains(selectedImageViewId)) {
            ivCurrent.setBackgroundResource(R.drawable.shadow);
            touchModeChecker.getDeleteable_list().remove(
                    (Integer) selectedImageViewId);
        } else {
            if (ivCurrent.getTag() != null) {
                ivCurrent.setBackgroundResource(R.drawable.round_corner_blue);
                touchModeChecker.addTo_List(selectedImageViewId);
            }
        }
    }

    public void handleLongTap(View v, View prev_v, int current_grid_id) {
        ImageView ivCurrent = (ImageView) v;

        if (ivCurrent.getTag() != null) {
            if (ivCheckSingleTap != null)
                ivCheckSingleTap.setBackgroundResource(R.drawable.shadow);
            ivCurrent.setBackgroundResource(R.drawable.round_corner_blue);
            touchModeChecker.addTo_List(current_grid_id);
            eventSeq = new EventSequence();
            eventSeq = (EventSequence) ivCurrent.getTag();
            ivDeleteAction.setVisibility(View.VISIBLE);
        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        String fileSelected = null;

        if (resultCode == RESULT_OK) {
            if ((requestCode == FILE_CHOOSER) && intent_source == 3) {
                fileSelected = data.getStringExtra("fileSelected");

                String[] splitted_path = fileSelected.split("/");
                String media_format_path = splitted_path[splitted_path.length - 1];
                String media_format = media_format_path.split("\\.")[1];

                if (media_format.compareTo("png") == 0
                        || media_format.compareTo("jpg") == 0) {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                    setImagetoImaageView(
                            BitmapFactory.decodeFile(fileSelected, options),
                            current_grid_pos, (ImageView) currentGridBox,
                            fileSelected);
                } else {
                    preview_bitmap = ThumbnailUtils.createVideoThumbnail(
                            fileSelected,
                            MediaStore.Images.Thumbnails.MINI_KIND);

                    if (preview_bitmap == null) {
                        // event_seq_list.get(current_grid_pos).setSeqPath(fileSelected);
                        if (actionEditDialog != null) {
                            actionEditDialog.setVideoPath(fileSelected);
                        }
                    } else {
                        // event_seq_list.get(current_grid_pos).setSeqPath(fileSelected);
                        if (actionEditDialog != null) {
                            actionEditDialog.setVideoPath(fileSelected);
                        }
                    }
                }
            }

            // Get Image from Gallery
            if (requestCode == SELECT_PICTURE && intent_source == 1) {
				/*
				 * try { Uri selectedImageUri = data.getData(); boolean
				 * fromDrive = false; try { selectedImagePath =
				 * getPath(selectedImageUri); } catch (IllegalArgumentException
				 * e) { e.printStackTrace(); fromDrive = true; }
				 * 
				 * if (!fromDrive) { fileSelected = selectedImagePath;
				 * InputStream is;
				 * 
				 * is = this.getContentResolver().openInputStream(
				 * selectedImageUri); BitmapFactory.Options options = new
				 * BitmapFactory.Options(); options.inSampleSize = 1;
				 * setImagetoImaageView( BitmapFactory.decodeStream(is, null,
				 * options), current_grid_pos, (ImageView) currentGridBox,
				 * fileSelected); } else {
				 */
                try {
                    InputStream inputStream = getContentResolver()
                            .openInputStream(data.getData());
                    Bitmap yourSelectedImage = BitmapFactory
                            .decodeStream(inputStream);
                    // ivActivity.setImageBitmap(yourSelectedImage);

                    try {
                        String state = Environment.getExternalStorageState();
                        if (Environment.MEDIA_MOUNTED.equals(state)) {
                            mFileTemp = new File(Environment.getExternalStorageDirectory() + appImagePath, TEMP_PHOTO_FILE_NAME);
                        } else {
                            mFileTemp = new File(getFilesDir() + appImagePath, TEMP_PHOTO_FILE_NAME);
                        }

                        FileOutputStream outputStream = new FileOutputStream(mFileTemp);
                        int quality = 100;
                        yourSelectedImage.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
                        outputStream.flush();
                        outputStream.close();
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }

                    //setImagetoImaageView(yourSelectedImage, current_grid_pos, (ImageView) currentGridBox, mFileTemp.getAbsolutePath());
                    startCropImage(mFileTemp);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
			/*
			 * } catch (FileNotFoundException e) { Toast.makeText(ref,
			 * e.getMessage(), Toast.LENGTH_SHORT).show(); } }
			 */

            // Get Image from Camera
			/*
			 * if (requestCode == CAMERA_REQUEST && intent_source == 2) {
			 * String[] imageColumns = { MediaStore.Images.Media._ID,
			 * MediaStore.Images.Media.DATA }; String imageOrderBy =
			 * MediaStore.Images.Media._ID+" DESC"; Cursor imageCursor =
			 * getContentResolver
			 * ().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
			 * imageColumns, null, null, imageOrderBy);
			 * if(imageCursor.moveToFirst()) {
			 * imageCursor.getInt(imageCursor.getColumnIndex
			 * (MediaStore.Images.Media._ID)); String fullPath =
			 * imageCursor.getString
			 * (imageCursor.getColumnIndex(MediaStore.Images.Media.DATA));
			 * imageCursor.close();
			 * 
			 * setImagetoImaageView((Bitmap)
			 * data.getExtras().get("data"),current_grid_pos,(ImageView)
			 * currentGridBox,fullPath); }
			 * 
			 * Uri tempUri = getImageUri(getApplicationContext(), (Bitmap)
			 * data.getExtras().get("data")); File finalFile = new
			 * File(getRealPathFromURI(tempUri)); setImagetoImaageView((Bitmap)
			 * data.getExtras().get("data"),current_grid_pos,(ImageView)
			 * currentGridBox,finalFile.getAbsolutePath());
			 * 
			 * 
			 * }
			 */

            if (requestCode == REQUEST_CODE_TAKE_PICTURE) {
                startCropImage(mFileTemp);
            }

            if (requestCode == REQUEST_CODE_CROP_IMAGE) {
                Bitmap bitmap = BitmapFactory.decodeFile(mFileTemp.getPath());

                // mImageView.setImageBitmap(bitmap);

                Date now = new Date();
                android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss",
                        now);

                try {
                    String state = Environment.getExternalStorageState();
                    if (Environment.MEDIA_MOUNTED.equals(state)) {
                        mFileTemp = new File(
                                Environment.getExternalStorageDirectory()
                                        + appImagePath, TEMP_PHOTO_FILE_NAME);
                    } else {
                        mFileTemp = new File(getFilesDir() + appImagePath,
                                TEMP_PHOTO_FILE_NAME);
                    }

                    FileOutputStream outputStream = new FileOutputStream(
                            mFileTemp);
                    int quality = 100;
                    bitmap.compress(Bitmap.CompressFormat.JPEG, quality,
                            outputStream);
                    outputStream.flush();
                    outputStream.close();

                } catch (Throwable e) {
                    // Several error may come out with file handling or OOM
                    e.printStackTrace();
                }

                setImagetoImaageView(bitmap, current_grid_pos,
                        (ImageView) currentGridBox, mFileTemp.getAbsolutePath());
				/*
				 * ivActivity.setImageBitmap(bitmap);
				 * ivActivity.setScaleType(ImageView.ScaleType.FIT_CENTER);
				 */
                if (isImageSearchDialogShow == true) {
                    imageSearchDialog.dismiss();

                }
            }

            intent_source = 0;
            FullScreencall();
        }
    }

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null,
                null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    private void setImagetoImaageView(Bitmap bmpImage, int imageViewPosition,
                                      ImageView selectedImageView, String imagePath) {
        if (selectedImageView == null) {
            actionEditDialog.setprofileImage(imagePath);
            Toast.makeText(ref, imagePath, Toast.LENGTH_SHORT).show();
            return;
        } else if (imageViewPosition == 13) {
            if (imagePath.length() == 0) {
                ivActivity.setImageBitmap(bmpImage);
            } else {
                imgProc.setImageWith_loader2(ivActivity, imagePath);
                ivActivity.setScaleType(ImageView.ScaleType.FIT_CENTER);
            }
            event = new Event();
            if (ivActivity.getTag() != null) {
                event = (Event) ivActivity.getTag();
                event.setLogo("new");
                ivActivity.setTag(event);
            } else {
                event.setLogo("new");
                ivActivity.setTag(event);
            }
        } else if (imageViewPosition == 14) {
            if (imagePath.length() == 0) {
                ivDailyPrize.setImageBitmap(bmpImage);
            } else {
                imgProc.setImageWith_loader2(ivDailyPrize, imagePath);
                ivDailyPrize
                        .setBackgroundResource(R.drawable.round_corner_purple_prize);
            }
            event = new Event();
            if (ivActivity.getTag() != null) {
                event = (Event) ivActivity.getTag();
                event.set_daily_prize("new");
                ivActivity.setTag(event);
            } else {
                event.set_daily_prize("new");
                ivActivity.setTag(event);
            }
        } else if (imageViewPosition == 15) {
            if (imagePath.length() == 0) {
                ivWeeklyPrize.setImageBitmap(bmpImage);
            } else {
                imgProc.setImageWith_loader2(ivWeeklyPrize, imagePath);
                ivWeeklyPrize
                        .setBackgroundResource(R.drawable.round_corner_purple_prize);
            }
            event = new Event();
            if (ivActivity.getTag() != null) {
                event = (Event) ivActivity.getTag();
                event.set_weekly_prize("new");
                ivActivity.setTag(event);
            } else {
                event.set_weekly_prize("new");
                ivActivity.setTag(event);
            }
        } else {
            if (imagePath.length() == 0) {
                selectedImageView.setImageBitmap(bmpImage);
            } else {
                imgProc.setImageWith_loader2(selectedImageView, imagePath);
                selectedImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);

            }
            eventSeq = new EventSequence();
            if (selectedImageView.getTag() != null) {
                eventSeq = (EventSequence) selectedImageView.getTag();
                eventSeq.setSeqImg("new");
                selectedImageView.setTag(eventSeq);
            } else {
                eventSeq.setStatus("0");
                eventSeq.setSeqImg("new");
                eventSeq.set_seq_position(imageViewPosition);
                selectedImageView.setTag(eventSeq);
            }
        }

        current_grid_pos = 0;
        currentGridBox = null;
    }

    public void cancelLongclick_event(ArrayList<Integer> cancelList) {
        for (int number : cancelList) {
            ImageView resetIv = (ImageView) findViewById(number);
            resetIv.setBackgroundResource(R.drawable.shadow);
        }
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

    OnEditorActionListener edActivitynameEditorListner = new OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER))
                    || (actionId == EditorInfo.IME_ACTION_DONE)) {
                FullScreencall();
            }
            return false;
        }
    };

    OnClickListener ibtnBackOnClickListner = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(EventManageActivity.this,
                    returnIntentPath);
            intent.putExtra("user_id", currentUserId);
            intent.putExtra("event_id", eventId);
            intent.putExtra("returnView", returnView);
            startActivity(intent);
            finish();
        }
    };

    OnClickListener ivEditOnClickListner = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (touchModeChecker.getMode() == 1) {
                ImageView ivAction = (ImageView) findViewById(touchModeChecker
                        .getGrid_selected_ID());

                eventSeq = new EventSequence();
                eventSeq = (EventSequence) ivAction.getTag();
                if (eventSeq == null) {
                    Toast.makeText(ref,
                            "Please select an image for this action first !",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                if (eventSeq.getStatus() != null
                        && isActionManageDialogShow == false) {
                    actionEditDialog = new EventCreateCustomDialog(ref, ref,
                            touchModeChecker.getClickedNumber(touchModeChecker
                                    .getGrid_selected_ID()), ivAction, eventSeq);
                    actionEditDialog.show();
                    isActionManageDialogShow = true;
                }
            }
        }
    };

    OnClickListener ivDeleteOnClickListner = new OnClickListener() {
        @Override
        public void onClick(View v) {
			/*
			 * if (touchModeChecker.getMode() == 2) {
			 * deleteSequence(touchModeChecker.getDeleteable_list());
			 * touchModeChecker.setMode(0); ivCheckSingleTap=null; } else {
			 * Toast.makeText(ref, "Please select atleast an action !",
			 * Toast.LENGTH_SHORT).show(); }
			 */

            if (touchModeChecker.getDeleteable_list().size() > 0) {
                deleteSequence(touchModeChecker.getDeleteable_list());
                touchModeChecker.setMode(0);
                ivCheckSingleTap = null;
                ivDeleteAction.setVisibility(View.INVISIBLE);
                touchModeChecker.getDeleteable_list().clear();
            }

        }
    };

    OnClickListener ivALarmOnOffListner = new OnClickListener() {
        @Override
        public void onClick(View v) {
            // ivALarmOnOff.setBackgroundResource(R.drawable.);
        }
    };

    OnTouchListener llMainOnTouchListner = new OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (ivCheckSingleTap != null)
                ivCheckSingleTap.setBackgroundResource(R.drawable.shadow);
            if (touchModeChecker.getMode() == 2) {
                cancelLongclick_event(touchModeChecker.getDeleteable_list());
                touchModeChecker.clearList();
            }
            hidekeyboard();
            FullScreencall();
            ivDeleteAction.setVisibility(View.INVISIBLE);
            return false;
        }
    };

    public void loadVideoFromDialog(int grid_pos) {
        current_grid_pos = grid_pos;
        load_video_from_dialog = 1;
        Intent intent = new Intent(this, FileChooser.class);
        ArrayList<String> extensions = new ArrayList<String>();
        extensions.add(".3gp");
        extensions.add(".mp4");
        extensions.add(".mkv");
        intent.putStringArrayListExtra("filterFileExtension", extensions);
        startActivityForResult(intent, FILE_CHOOSER);
        intent_source = 3;
    }

    public String eventvalidation() {
        String errorMessage = null;

        boolean checkActions = false;
        for (ImageView ivAction : eventActions) {
            if (ivAction.getTag() != null) {
                checkActions = true;
                break;
            }
        }

        if (edActivityName.getText().toString().length() == 0)
            errorMessage = getResources().getString(
                    R.string.provide_Image_event_desc);
        else if (ivActivity.getTag() == null)
            errorMessage = getResources().getString(
                    R.string.provide_Image_event_avatar);
        else if (!chkMon.isChecked() && !chkTue.isChecked()
                && !chkWed.isChecked() && !chkWed.isChecked()
                && !chkThu.isChecked() && !chkFri.isChecked()
                && !chkSat.isChecked() && !chkSun.isChecked())
            errorMessage = getResources().getString(
                    R.string.provide_Image_event_alarm);
        else if (checkActions == false)
            errorMessage = getResources().getString(
                    R.string.provide_Image_event_sequence);
        return errorMessage;
    }

    public void addButtonlistener() {
        ivSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String errorMsg = eventvalidation();
                if ((errorMsg == null)) {
                    saveEvent();
                    Intent intent = new Intent(EventManageActivity.this,
                            returnIntentPath);
                    intent.putExtra("user_id", currentUserId);
                    intent.putExtra("event_id", eventId);
                    intent.putExtra("returnView", returnView);
                    startActivity(intent);
                } else {
                    Toast.makeText(ref, errorMsg, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void saveEvent() {
        event = new Event();
        event = (Event) ivActivity.getTag();
        event.setDescription(edActivityName.getText().toString());

        if (tglALarmOnOff.isChecked()) {
            event.set_alarm("Y");
        } else {
            event.set_alarm("N");
        }

        if (event.getLogo().equals("new")) {
            String activityImageName = imgProc
                    .imageSave(((BitmapDrawable) ivActivity.getDrawable())
                            .getBitmap());
            event.setLogo(activityImageName);
        }

        if (event.get_daily_prize() != null && event.get_daily_prize().equals("new")) {
            String imageName = imgProc.imageSave(((BitmapDrawable) ivDailyPrize.getDrawable()).getBitmap());
            event.set_daily_prize(imageName);
        }
        if (event.get_weekly_prize() != null
                && event.get_weekly_prize().equals("new")) {
            String imageName = imgProc
                    .imageSave(((BitmapDrawable) ivWeeklyPrize.getDrawable())
                            .getBitmap());
            event.set_weekly_prize(imageName);
        }

        if (eventId == 0) {
            event.setStatus("0");
            event.setUserID(Integer.parseInt(currentUserId));
            int addedEventId = db.addEvent(event);
            saveEventSequence(addedEventId);
            saveEventAlarm(addedEventId);
        } else {
            event.setStatus("0");
            db.updateEvent(event);
            saveEventSequence(eventId);
            deleteEventAlarm(eventId);
            saveEventAlarm(eventId);
            db.deleteEventCompByEventId(eventId);
        }

        AlarmMakerReceiver ma = new AlarmMakerReceiver();
        ma.SetAlarm(ref);

    }

    private void saveEventAlarm(int eventId) {
        if (chkAll.isChecked()) {
            EventAlarm evt_alr = new EventAlarm();
            evt_alr.set_event_id(String.valueOf(eventId));
            evt_alr.set_alarm_time(tvDisplayTime.getText().toString());
            evt_alr.set_alarm_sat(dateToStatus(chkSat.isChecked()));
            evt_alr.set_alarm_fri(dateToStatus(chkFri.isChecked()));
            evt_alr.set_alarm_sun(dateToStatus(chkSun.isChecked()));
            evt_alr.set_alarm_mon(dateToStatus(chkMon.isChecked()));
            evt_alr.set_alarm_tue(dateToStatus(chkTue.isChecked()));
            evt_alr.set_alarm_wed(dateToStatus(chkWed.isChecked()));
            evt_alr.set_alarm_thu(dateToStatus(chkThu.isChecked()));
            evt_alr.set_alarm_week_num("N");
            evt_alr.set_alarm_week_repeat(dateToStatus(chkAll.isChecked()));
            db.addEventAlarm(evt_alr);

        } else {

            ArrayList<String> checkedDays = new ArrayList<String>();
            if (chkMon.isChecked()) {
                checkedDays.add("Mon");
            }
            if (chkTue.isChecked()) {
                checkedDays.add("Tue");
            }
            if (chkWed.isChecked()) {
                checkedDays.add("Wed");
            }
            if (chkThu.isChecked()) {
                checkedDays.add("Thu");
            }
            if (chkFri.isChecked()) {
                checkedDays.add("Fri");
            }
            if (chkSat.isChecked()) {
                checkedDays.add("Sat");
            }
            if (chkSun.isChecked()) {
                checkedDays.add("Sun");
            }

            for (int i = 0; i < checkedDays.size(); i++) {
                String weekNum = CalenderDateList.getDayWiseWeekNum(
                        checkedDays.get(i), tvDisplayTime.getText().toString());

                if (db.checkWeekNumber(weekNum, tvDisplayTime.getText()
                        .toString()) > 0) {
                    EventAlarm evt_alr = new EventAlarm();
                    evt_alr.set_alarm_week_num(weekNum);
                    evt_alr.set_alarm_time(tvDisplayTime.getText().toString());

                    switch (checkedDays.get(i)) {
                        case "Mon":
                            evt_alr.set_alarm_mon("Y");
                            db.updateEventAlarmMon(evt_alr);
                            break;
                        case "Tue":
                            evt_alr.set_alarm_tue("Y");
                            db.updateEventAlarmTue(evt_alr);
                            break;
                        case "Wed":
                            evt_alr.set_alarm_wed("Y");
                            db.updateEventAlarmWed(evt_alr);
                            break;
                        case "Thu":
                            evt_alr.set_alarm_thu("Y");
                            db.updateEventAlarmThu(evt_alr);
                            break;
                        case "Fri":
                            evt_alr.set_alarm_fri("Y");
                            db.updateEventAlarmFri(evt_alr);
                            break;
                        case "Sat":
                            evt_alr.set_alarm_sat("Y");
                            db.updateEventAlarmSat(evt_alr);
                            break;
                        case "Sun":
                            evt_alr.set_alarm_sun("Y");
                            db.updateEventAlarmSun(evt_alr);
                            break;
                    }

                } else {

                    EventAlarm evt_alr = new EventAlarm();
                    evt_alr.set_event_id(String.valueOf(eventId));
                    evt_alr.set_alarm_time(tvDisplayTime.getText().toString());
                    evt_alr.set_alarm_mon("N");
                    evt_alr.set_alarm_tue("N");
                    evt_alr.set_alarm_wed("N");
                    evt_alr.set_alarm_thu("N");
                    evt_alr.set_alarm_fri("N");
                    evt_alr.set_alarm_sat("N");
                    evt_alr.set_alarm_sun("N");
                    evt_alr.set_alarm_week_num(weekNum);
                    evt_alr.set_alarm_week_repeat(dateToStatus(chkAll
                            .isChecked()));

                    switch (checkedDays.get(i)) {
                        case "Mon":
                            evt_alr.set_alarm_mon("Y");
                            break;
                        case "Tue":
                            evt_alr.set_alarm_tue("Y");
                            break;
                        case "Wed":
                            evt_alr.set_alarm_wed("Y");
                            break;
                        case "Thu":
                            evt_alr.set_alarm_thu("Y");
                            break;
                        case "Fri":
                            evt_alr.set_alarm_fri("Y");
                            break;
                        case "Sat":
                            evt_alr.set_alarm_sat("Y");
                            break;
                        case "Sun":
                            evt_alr.set_alarm_sun("Y");
                            break;
                    }
                    db.addEventAlarm(evt_alr);
                }
            }
        }
    }

    private void saveEventSequence(int eventId) {
        for (ImageView ivAction : eventActions) {
            if (ivAction.getTag() != null) {
                eventSeq = new EventSequence();
                eventSeq = (EventSequence) ivAction.getTag();
                if (eventSeq.getSeqImg().equals("new")) {
                    eventSeq.setSeqImg(imgProc
                            .imageSave(((BitmapDrawable) ivAction.getDrawable())
                                    .getBitmap()));
                }
                eventSeq.setEventId(eventId);
                eventSeq.setStatus("0");

                if (eventSeq.getID() == 0) {
                    db.addEventSeq(eventSeq);
                } else {
                    db.updateEventSeq(eventSeq);
                }
            }
        }
    }

    String dateToStatus(boolean isDateChecked) {
        if (isDateChecked) {
            return "Y";
        } else {
            return "N";
        }
    }

    public void getData() {

        event = new Event();
        event = db.getEvent(eventId);
        imgProc.setImageWith_loader(ivActivity, event.getLogo());
        ivActivity.setTag(event);
        ivActivity.setScaleType(ScaleType.FIT_CENTER);
        edActivityName.setText(event.getDescription());

        if (event.get_daily_prize() != null) {
            imgProc.setImageWith_loader(ivDailyPrize, event.get_daily_prize());
            ivDailyPrize.setBackgroundResource(R.drawable.round_corner_purple_prize);
        }

        if (event.get_weekly_prize() != null) {
            imgProc.setImageWith_loader(ivWeeklyPrize, event.get_weekly_prize());
            ivWeeklyPrize.setBackgroundResource(R.drawable.round_corner_purple_prize);
        }

        if (event.get_alarm().equals("Y")) {
            tglALarmOnOff.setChecked(true);
        } else {
            tglALarmOnOff.setChecked(false);
        }

        List<EventSequence> eventSeqList = db.getAllEvtSeqList(eventId);
        if (eventSeqList != null) {
            for (int i = 0; i < eventSeqList.size(); i++) {
                eventSeq = new EventSequence();
                eventSeq = eventSeqList.get(i);
                for (int j = 0; j < eventActions.size(); j++) {
                    if (eventSeq.get_seq_position() == j + 1) {
                        imgProc.setImageWith_loader(eventActions.get(j),
                                eventSeq.getSeqImg());
                        eventActions.get(j).setTag(eventSeq);
                        break;
                    }
                }
            }
        }

        List<EventAlarm> event_occur_list = db.getAllEvtAlrList(eventId);
        if (event_occur_list != null && event_occur_list.size() > 0) {
            EventAlarm current_alarm = event_occur_list.get(0);
            eventAlarmId = current_alarm.get_id();
            tvDisplayTime.setText(current_alarm.get_alarm_time());

            if (current_alarm.get_alarm_week_repeat().equals("Y")) {
                chkAll.setChecked(true);
            }

            if (current_alarm.get_alarm_mon().equals("Y")) {
                chkMon.setChecked(true);
            }

            if (current_alarm.get_alarm_sun().equals("Y")) {
                chkSun.setChecked(true);
            }

            if (current_alarm.get_alarm_sat().equals("Y")) {
                chkSat.setChecked(true);
            }

            if (current_alarm.get_alarm_tue().equals("Y")) {
                chkTue.setChecked(true);
            }

            if (current_alarm.get_alarm_wed().equals("Y")) {
                chkWed.setChecked(true);
            }

            if (current_alarm.get_alarm_thu().equals("Y")) {
                chkThu.setChecked(true);
            }
            if (current_alarm.get_alarm_fri().equals("Y")) {
                chkFri.setChecked(true);
            }
        }

    }

    public void deleteEventAlarm(int eventId) {
        EventAlarm evt_alr = new EventAlarm();
        evt_alr.set_event_id(String.valueOf(eventId));
        db.deleteEventAlaram(evt_alr);
    }

    public void deleteSequence(ArrayList<Integer> delete_list) {
        for (int number : delete_list) {
            ImageView ivAction = (ImageView) findViewById(number);
            eventSeq = new EventSequence();
            if (ivAction != null) {
                if (ivAction.getTag() != null) {
                    eventSeq = (EventSequence) ivAction.getTag();
                    if (eventSeq.getID() > 0) {
                        db.deleteEventSeq(eventSeq);
                    }
                }
            }
            ivAction.setBackgroundResource(R.drawable.shadow);
            ivAction.setImageResource(R.drawable.ic_new_edition);
            ivAction.setTag(null);
        }
    }

    OnClickListener galleryOnClickListner = new OnClickListener() {
        @Override
        public void onClick(View v) {
            currentGridBox = ivActivity;
            current_grid_pos = 13;
            loadImageGallery();
        }
    };

    OnClickListener cameraOnClickListner = new OnClickListener() {
        @Override
        public void onClick(View v) {
            currentGridBox = ivActivity;
            current_grid_pos = 13;
            loadImageCamera();
        }
    };

    OnClickListener sdcardOnClickListner = new OnClickListener() {
        @Override
        public void onClick(View v) {
            currentGridBox = ivActivity;
            current_grid_pos = 13;
			/* loadImageStorage(); */

            if (isImageSearchDialogShow == false) {
                imageSearchDialog = new ImageSearchCustomDialog(ref, ref);
                imageSearchDialog.show();
                isImageSearchDialogShow = true;
            }
        }
    };

    private void allWeek() {
        chkAll.setChecked(true);
        chkMon.setChecked(true);
        chkTue.setChecked(true);
        chkWed.setChecked(true);
        chkThu.setChecked(true);
        chkFri.setChecked(true);
        chkSat.setChecked(true);
        chkSun.setChecked(true);
    }

    private void hidekeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = Images.Media.insertImage(inContext.getContentResolver(),
                inImage, "captured-from-calendar-app-android", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    public void startCropImage(File file) {

        Intent intent = new Intent(this, CropImage.class);
        intent.putExtra(CropImage.IMAGE_PATH, file.getPath());
        intent.putExtra(CropImage.SCALE, true);
        intent.putExtra(CropImage.OUTPUT_X, 512);
        intent.putExtra(CropImage.OUTPUT_Y, 512);
        intent.putExtra(CropImage.ASPECT_X, 2);
        intent.putExtra(CropImage.ASPECT_Y, 2);

        startActivityForResult(intent, REQUEST_CODE_CROP_IMAGE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivDailyPrize:
                current_grid_pos = 14;
                currentGridBox = ivDailyPrize;
                quickAction.show(v);
                break;
            case R.id.ivWeeklyPrize:
                current_grid_pos = 15;
                currentGridBox = ivWeeklyPrize;
                quickAction.show(v);
                break;
        }
    }
}
