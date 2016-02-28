package com.uniqgroup.application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import br.com.thinkti.android.filechooser.FileChooser;

import com.uniqgroup.adapter.UserCreateAdapter;
import com.uniqgroup.customdialog.ImageSearchCustomDialog;
import com.uniqgroup.customdialog.PasswordDialog;
import com.uniqgroup.customdialog.PasswordDialog.SwitchONEditMode;
import com.uniqgroup.customdialog.UserCreateCustomDialog;
import com.uniqgroup.customui.Animanation;
import com.uniqgroup.database.DatabaseHandler;
import com.uniqgroup.pojo.User;
import com.uniqgroup.utility.AlarmManage;
import com.uniqgroup.utility.Custom_Touch_Event;
import com.uniqgroup.utility.Custom_Touch_Event.OnFling_right_left;
import com.uniqgroup.utility.EditModeSetting;
import com.uniqgroup.utility.ImageProcessing;
import com.uniqgroup.utility.InternalStorageContentProvider;
import com.uniqgroup.utility.PasswordStorage;

import eu.janmuller.android.simplecropimage.CropImage;

public class UserCreateActivity extends Activity implements OnFling_right_left,
        SwitchONEditMode, OnClickListener, OnLongClickListener {

    private static final int SELECT_PICTURE = 1;

    private static final int CAMERA_REQUEST = 1;
    private static final int FILE_CHOOSER = 1;
    public static final int REQUEST_CODE_CROP_IMAGE = 7;
    public static final int REQUEST_CODE_TAKE_PICTURE = 0x8;
    public File mFileTemp;
    public static final String TEMP_PHOTO_FILE_NAME = "temp_photo.jpg";
    public ImageSearchCustomDialog imageSearchDialog = null;
    private String appImagePath = null;

    public boolean isUserManageDialogShow = false;
    public boolean isImageSearchDialogShow = false;
    UserCreateActivity ref;
    int intent_source = 0, checkLong = 0, currentUserIdForDelete = 0;

    String pro_pic;
    GridView gv;
    Context context;
    ArrayList prgmName;
    ImageButton addUserButton, ibtnBack, ibtnClearEditMode, ibtnDelete, ibtnAlarm;
    DatabaseHandler db;
    Bitmap preview_bitmap;
    RelativeLayout rl_animation_finger_usercreate;
    LinearLayout llBorder;
    ArrayList<Integer> userIdForDelete = new ArrayList<Integer>();
    ArrayList<LinearLayout> userSelectedLayout = new ArrayList<LinearLayout>();

    boolean isEmpty = false;
    private String selectedImagePath;
    UserCreateAdapter user_adapter;

    // UserCreateActivity myref;

    UserCreateCustomDialog userManageDialog;
    /******************
     * EDITING PROPERTIES START
     ************************************/
    int isEditMode = 0;

    // public int edituser = 1;
    User currentuserToEdit;

    /******************
     * EDITING PROPERTIES END
     ************************************/

    LinearLayout gesturelistener_ln;
    Custom_Touch_Event listener_customTouch;

    ImageView add_event_IV, two_finger_image;

    LinearLayout user_linear_cell1, user_linear_cell2, user_linear_cell3,
            user_linear_cell4, user_linear_cell5, user_linear_cell6,
            user_linear_cell7, user_linear_cell8, user_linear_cell9,
            user_linear_cell10, user_linear_cell11, user_linear_cell12,
            user_linear_cell13, user_linear_cell14, user_linear_cell15;

    int selectedCell = -1;

    OnTouchListener fling_listener = new OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (listener_customTouch != null)
                listener_customTouch.onTouch(v, event);

            // two_finger_image.setVisibility(View.GONE);
            // rl_animation_finger_usercreate.setVisibility(View.GONE);
            //
            // two_finger_image.clearAnimation();

            return true;
        }
    };

    public ImageProcessing imgProc;
    List<User> user_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_create);
        imgProc = new ImageProcessing(this);
        // check if edit mode is set
        llBorder = (LinearLayout) findViewById(R.id.llBorder);
        gesturelistener_ln = (LinearLayout) findViewById(R.id.fling_space_ll);
        gesturelistener_ln.setOnTouchListener(fling_listener);
        listener_customTouch = new Custom_Touch_Event(this);
        ref = this;
        rl_animation_finger_usercreate = (RelativeLayout) findViewById(R.id.ll_animation_finger_usercreate);

        user_linear_cell1 = (LinearLayout) findViewById(R.id.user_linear_cell1);
        user_linear_cell2 = (LinearLayout) findViewById(R.id.user_linear_cell2);
        user_linear_cell3 = (LinearLayout) findViewById(R.id.user_linear_cell3);
        user_linear_cell4 = (LinearLayout) findViewById(R.id.user_linear_cell4);
        user_linear_cell5 = (LinearLayout) findViewById(R.id.user_linear_cell5);
        user_linear_cell6 = (LinearLayout) findViewById(R.id.user_linear_cell6);
        user_linear_cell7 = (LinearLayout) findViewById(R.id.user_linear_cell7);
        user_linear_cell8 = (LinearLayout) findViewById(R.id.user_linear_cell8);
        user_linear_cell9 = (LinearLayout) findViewById(R.id.user_linear_cell9);
        user_linear_cell10 = (LinearLayout) findViewById(R.id.user_linear_cell10);
        user_linear_cell11 = (LinearLayout) findViewById(R.id.user_linear_cell11);
        user_linear_cell12 = (LinearLayout) findViewById(R.id.user_linear_cell12);
        user_linear_cell13 = (LinearLayout) findViewById(R.id.user_linear_cell13);
        user_linear_cell14 = (LinearLayout) findViewById(R.id.user_linear_cell14);
        user_linear_cell15 = (LinearLayout) findViewById(R.id.user_linear_cell15);

        user_linear_cell1.setOnClickListener(this);
        user_linear_cell2.setOnClickListener(this);
        user_linear_cell3.setOnClickListener(this);
        user_linear_cell4.setOnClickListener(this);
        user_linear_cell5.setOnClickListener(this);
        user_linear_cell6.setOnClickListener(this);
        user_linear_cell8.setOnClickListener(this);
        user_linear_cell9.setOnClickListener(this);
        user_linear_cell10.setOnClickListener(this);
        user_linear_cell11.setOnClickListener(this);
        user_linear_cell12.setOnClickListener(this);
        user_linear_cell13.setOnClickListener(this);
        user_linear_cell14.setOnClickListener(this);
        user_linear_cell15.setOnClickListener(this);

        user_linear_cell1.setOnLongClickListener(this);
        user_linear_cell2.setOnLongClickListener(this);
        user_linear_cell3.setOnLongClickListener(this);
        user_linear_cell4.setOnLongClickListener(this);
        user_linear_cell5.setOnLongClickListener(this);
        user_linear_cell6.setOnLongClickListener(this);
        user_linear_cell7.setOnLongClickListener(this);
        user_linear_cell8.setOnLongClickListener(this);
        user_linear_cell9.setOnLongClickListener(this);
        user_linear_cell10.setOnLongClickListener(this);
        user_linear_cell11.setOnLongClickListener(this);
        user_linear_cell12.setOnLongClickListener(this);
        user_linear_cell13.setOnLongClickListener(this);
        user_linear_cell14.setOnLongClickListener(this);
        user_linear_cell15.setOnLongClickListener(this);

        two_finger_image = (ImageView) findViewById(R.id.animation_finger_usercreate);
        ref = this;
        db = new DatabaseHandler(this);

        // List<User> user_list = db.getAllUsers();

        addUserButton = (ImageButton) findViewById(R.id.add_user);
        ibtnBack = (ImageButton) findViewById(R.id.ibtnBack);
        ibtnClearEditMode = (ImageButton) findViewById(R.id.ibtnClearEditMode);
        ibtnDelete = (ImageButton) findViewById(R.id.ibtnDelete);
        ibtnAlarm = (ImageButton) findViewById(R.id.ibtnAlarm);

        setCells();
        visibilitySetting();
        appImagePath = imgProc.getImageDir();

        addUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (db.getUsersCount() > 14) {
                    Toast.makeText(
                            ref,
                            getResources().getString(R.string.userExceedMessage),
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                // resetting the edit mode
                for (LinearLayout i : userSelectedLayout) {
                    i.setBackgroundResource(R.drawable.round_corner_white);
                }
                userIdForDelete.clear();
                checkLong = 0;
                // edituser = 0;
                currentuserToEdit = null;

                if (isUserManageDialogShow == false) {
                    userManageDialog = new UserCreateCustomDialog(
                            UserCreateActivity.this, ref,
                            UserCreateActivity.this);
                    userManageDialog.show();
                    isUserManageDialogShow = true;
                }
            }
        });

        ibtnBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                flingRL_happened(false);
                Intent intent = new Intent(UserCreateActivity.this,
                        MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        ibtnClearEditMode.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                flingRL_happened(false);
                for (LinearLayout i : userSelectedLayout) {
                    i.setBackgroundResource(R.drawable.round_corner_white);
                }
                checkLong = 0;
                userIdForDelete.clear();
            }
        });

        ibtnDelete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (currentUserIdForDelete > 0) {
                    db.deleteAllUserData(currentUserIdForDelete);
                } else {
                    if (userIdForDelete.size() == 0) {
                        return;
                    }
                    for (int i : userIdForDelete) {
                        db.deleteAllUserData(i);
                    }
                    userIdForDelete.clear();
                }
                currentUserIdForDelete = 0;
                checkLong = 0;
                onCreate(null);
                ibtnDelete.setVisibility(View.INVISIBLE);
                Toast.makeText(ref, getResources().getString(R.string.userDeleteMessage), Toast.LENGTH_SHORT).show();
            }
        });

        ibtnAlarm.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                db.updateAlarmOfUser(String.valueOf(currentUserIdForDelete), AlarmManage.alarmOn, AlarmManage.alarmOff);
                ibtnAlarm.setVisibility(View.INVISIBLE);
                Toast.makeText(ref, getResources().getString(R.string.alarmSetForUserMessage), Toast.LENGTH_SHORT).show();
                setCells();

                AlarmMakerReceiver ma = new AlarmMakerReceiver();
                ma.SetAlarm(ref);
            }
        });

        llBorder.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshSelection();
            }
        });

        FullScreencall();
    }

    public void setData_in_cell(LinearLayout cell, User user_model) {
        TextView username = (TextView) cell.findViewById(R.id.userName_tv);
        ImageView user_iv = (ImageView) cell
                .findViewById(R.id.userpic_usercreatecell_IV);
        ImageButton ibtnAlarmNotification = (ImageButton) cell
                .findViewById(R.id.ibtnAlarmPropic);

        username.setText(user_model.getName());
        imgProc.setImageWith_loader(user_iv, user_model.getProfilePic());
        cell.setVisibility(View.VISIBLE);
        if (user_model.get_alarm().equals(AlarmManage.alarmOn)) {
            ibtnAlarmNotification.setVisibility(View.VISIBLE);
        } else {
            ibtnAlarmNotification.setVisibility(View.INVISIBLE);
        }
    }

    public void packCells(int pos, User user_model) {
        switch (pos) {
            case 0:
                setData_in_cell(user_linear_cell1, user_model);
                break;
            case 1:
                setData_in_cell(user_linear_cell2, user_model);
                break;
            case 2:
                setData_in_cell(user_linear_cell3, user_model);
                break;
            case 3:
                setData_in_cell(user_linear_cell4, user_model);
                break;
            case 4:
                setData_in_cell(user_linear_cell5, user_model);
                break;
            case 5:
                setData_in_cell(user_linear_cell6, user_model);
                break;
            case 6:
                setData_in_cell(user_linear_cell7, user_model);
                break;
            case 7:
                setData_in_cell(user_linear_cell8, user_model);
                break;
            case 8:
                setData_in_cell(user_linear_cell9, user_model);
                break;
            case 9:
                setData_in_cell(user_linear_cell10, user_model);
                break;
            case 10:
                setData_in_cell(user_linear_cell11, user_model);
                break;
            case 11:
                setData_in_cell(user_linear_cell12, user_model);
                break;
            case 12:
                setData_in_cell(user_linear_cell13, user_model);
                break;
            case 13:
                setData_in_cell(user_linear_cell14, user_model);
                break;
            case 14:
                setData_in_cell(user_linear_cell15, user_model);
                break;

            default:
                break;
        }
    }

    public void setCells() {
        user_list = db.getAllUsers();
        if (user_list != null) {
            for (int i = 0; i < user_list.size(); i++) {
                packCells(i, user_list.get(i));
            }
            ibtnBack.setEnabled(true);
            ibtnClearEditMode.setEnabled(true);
            Animanation.clear(addUserButton);
            rl_animation_finger_usercreate.setVisibility(View.INVISIBLE);

        } else {
            ibtnBack.setEnabled(false);
            ibtnClearEditMode.setEnabled(false);
            Animanation.blink(addUserButton);

            user_list = new ArrayList<User>();

            TranslateAnimation animation = new TranslateAnimation(-600.0f,
                    0.0f, 0.0f, 0.0f);
            animation.setDuration(3000); // animation duration
            animation.setRepeatCount(Animation.INFINITE);
            animation.setRepeatMode(1);
            two_finger_image.setVisibility(View.VISIBLE);
            two_finger_image.startAnimation(animation);

            isEmpty = true;
        }

    }

    /******************
     * EDITING PROPERTIES START
     ************************************/

    public Boolean isEditMode() {

        return isEditMode == 1;
    }

    public void setEditMode() {

        if (EditModeSetting.getEditMode() == 1) {
            isEditMode = 1;
        } else {
            isEditMode = 0;
        }
    }

    public User getCurrentUser() {

        return currentuserToEdit;
    }

    public void switchEdit_ON(boolean flag) {
        if (flag == true && isUserManageDialogShow == false) {

            userManageDialog = new UserCreateCustomDialog(UserCreateActivity.this, ref, this);
            userManageDialog.show();
            isUserManageDialogShow = true;
            Animanation.clear(addUserButton);
        }
    }

    // showing Edit Dialog
    public void showEditDiallog(int user_id) {

        // ResetEdit Mode in this activity if new Button is clicked ...
        setEditMode();
        // show dialog ...
        currentuserToEdit = db.getUser(user_id);
        int curr_pass_index = PasswordStorage
                .getCurrentPasswordIndex(currentuserToEdit.getPassword());

        if (curr_pass_index == 0) {

            switchEdit_ON(true);
        } else {

            new PasswordDialog(this, curr_pass_index, this).show();
        }

    }

    /******************
     * EDITING PROPERTIES END
     ************************************/

    // grabing images from galley and set it in image buttom
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Drawable profileImage = null;
        if (resultCode == RESULT_OK) {

            /** Loading From File Chooser **/
            if ((requestCode == FILE_CHOOSER) && intent_source == 3) {
                String fileSelected = data.getStringExtra("fileSelected");
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                preview_bitmap = BitmapFactory
                        .decodeFile(fileSelected, options);
                profileImage = new BitmapDrawable(getResources(),
                        preview_bitmap);
                pro_pic = ImageProcessing.encodeTobase64(preview_bitmap);
            }

            /** Loading From Gallery **/
            if (requestCode == SELECT_PICTURE && intent_source == 1) {

                try {
                    InputStream inputStream = getContentResolver().openInputStream(data.getData());
                    Bitmap yourSelectedImage = BitmapFactory.decodeStream(inputStream);

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

                    startCropImageUser(mFileTemp);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

            /** Loading From Camera **/
            if (requestCode == REQUEST_CODE_TAKE_PICTURE && intent_source == 2) {
                startCropImageUser(mFileTemp);
            }

            /** Loading From Image Cropper **/
            if (requestCode == REQUEST_CODE_CROP_IMAGE) {
                Bitmap bitmap = BitmapFactory.decodeFile(mFileTemp.getPath());
                Date now = new Date();
                android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

                try {
                    String state = Environment.getExternalStorageState();
                    if (Environment.MEDIA_MOUNTED.equals(state)) {
                        mFileTemp = new File(Environment.getExternalStorageDirectory() + appImagePath, TEMP_PHOTO_FILE_NAME);
                    } else {
                        mFileTemp = new File(getFilesDir() + appImagePath, TEMP_PHOTO_FILE_NAME);
                    }

                    FileOutputStream outputStream = new FileOutputStream(mFileTemp);
                    int quality = 100;
                    bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
                    outputStream.flush();
                    outputStream.close();

                } catch (Throwable e) {
                    // Several error may come out with file handling or OOM
                    e.printStackTrace();
                }
                if (isImageSearchDialogShow == true) {
                    imageSearchDialog.dismiss();
                    isImageSearchDialogShow = false;
                }
                profileImage = new BitmapDrawable(getResources(), bitmap);
            }
            userManageDialog.setprofileImage(profileImage);
            intent_source = 0;
        }
    }

    /** Saving a User (called from dialog) **/
    public void saveUser(String name, String user_password, BitmapDrawable profilePic, String selectedView) {

        if (currentuserToEdit != null) {
            currentuserToEdit.setName(name);
            currentuserToEdit.setProfilePic(imgProc.imageSave(profilePic.getBitmap()));
            currentuserToEdit.set_default_view(selectedView);
            if (user_password.length() > 0) {
                currentuserToEdit.setPassword(user_password);
            }
            db.updateUser(currentuserToEdit);
        } else {
            db.addUser(new User(name, user_password, imgProc.imageSave(profilePic.getBitmap()), "admin", selectedView, AlarmManage.alarmOff));
        }

        isEmpty = false;

        setCells();
    }

    // get image path
    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    // load image from gallery

    public void loadImageGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        // taking selected picture in new intent
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),
                SELECT_PICTURE);

        intent_source = 1;
    }

    // load image from Camera
    public void loadImageCamera() {

		/*Intent cameraIntent = new Intent(
                android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(cameraIntent, CAMERA_REQUEST);*/

        String state1 = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state1)) {
            mFileTemp = new File(Environment.getExternalStorageDirectory() + appImagePath, TEMP_PHOTO_FILE_NAME);
        } else {
            mFileTemp = new File(getFilesDir() + appImagePath, TEMP_PHOTO_FILE_NAME);
        }

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        try {
            Uri mImageCaptureUri = null;
            String state2 = Environment.getExternalStorageState();
            if (Environment.MEDIA_MOUNTED.equals(state2)) {
                mImageCaptureUri = Uri.fromFile(mFileTemp);
            } else {
                /*
                 * The solution is taken from here: http://stackoverflow.com/questions/10042695/how-to-get-camera-result-as-a-uri-in-data-folder
	        	 */
                mImageCaptureUri = InternalStorageContentProvider.CONTENT_URI;
            }
            intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
            intent.putExtra("return-data", true);
            startActivityForResult(intent, REQUEST_CODE_TAKE_PICTURE);
        } catch (ActivityNotFoundException e) {
        }

        intent_source = 2;
    }

    // load Image from Storage
    public void loadImageStorage() {
        Intent intent = new Intent(this, FileChooser.class);
        ArrayList<String> extensions = new ArrayList<String>();
        extensions.add(".png"); // can be used for multiple filters
        extensions.add(".jpg");
        intent.putStringArrayListExtra("filterFileExtension", extensions);
        startActivityForResult(intent, FILE_CHOOSER);

        intent_source = 3;
    }

    // fling event capture
    @Override
    public void flingRL_happened(boolean editOn) {

        if (editOn) {
            two_finger_image.setVisibility(View.GONE);
            rl_animation_finger_usercreate.setVisibility(View.GONE);
            two_finger_image.clearAnimation();
            EditModeSetting.setEditMode(1);

        } else {
            if (!isEmpty) {
                Animanation.clear(addUserButton);
                switchEdit_ON(false);
                EditModeSetting.setEditMode(0);
            }
        }
        visibilitySetting();

    }

    // checking edit button visibility
    public void visibilitySetting() {
        isEditMode = EditModeSetting.getEditMode();
        if (isEditMode()) {
            addUserButton.setVisibility(View.VISIBLE);
            ibtnBack.setVisibility(View.VISIBLE);
            ibtnClearEditMode.setVisibility(View.VISIBLE);
            llBorder.setBackgroundResource(R.drawable.bg_transparente_edition);
        } else {
            addUserButton.setVisibility(View.INVISIBLE);
            ibtnBack.setVisibility(View.INVISIBLE);
            ibtnClearEditMode.setVisibility(View.INVISIBLE);
            llBorder.setBackground(null);
            ibtnDelete.setVisibility(View.INVISIBLE);
            ibtnAlarm.setVisibility(View.INVISIBLE);
        }

    }

    public void FullScreencall() {
        if (Build.VERSION.SDK_INT < 19) {
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else {
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    public void userClicked(int user_id) {
        if (isEditMode()) {
            two_finger_image.setVisibility(View.GONE);
            rl_animation_finger_usercreate.setVisibility(View.GONE);
            two_finger_image.clearAnimation();
            showEditDiallog(user_id);

        } else {
            Intent intent = new Intent(this, UserEventListActivity.class);
            intent.putExtra("user_id", String.valueOf(user_id));
            intent.putExtra("returnView", "");
            startActivity(intent);
            finish();
        }
        // Toast.makeText(ref, String.valueOf(edituser),
        // Toast.LENGTH_SHORT).show();
    }

    public void setCellSelectedColor(LinearLayout previous_cell,
                                     LinearLayout current_cell) {
        if (previous_cell != null) {
            LinearLayout userCell = (LinearLayout) previous_cell
                    .findViewById(R.id.container_linear_cell_user);
            userCell.setBackgroundResource(R.drawable.round_corner_white);
            TextView username = (TextView) previous_cell
                    .findViewById(R.id.round_hanger_cell_user);
            username.setBackgroundResource(R.drawable.round_shape_white);

            LinearLayout userCell2 = (LinearLayout) current_cell
                    .findViewById(R.id.container_linear_cell_user);
            userCell2.setBackgroundResource(R.drawable.round_corner_purple);
            TextView username2 = (TextView) current_cell
                    .findViewById(R.id.round_hanger_cell_user);
            username2.setBackgroundResource(R.drawable.round_shape_purple2);

        } else {
            LinearLayout userCell2 = (LinearLayout) current_cell
                    .findViewById(R.id.container_linear_cell_user);
            userCell2.setBackgroundResource(R.drawable.round_corner_purple);
            TextView username2 = (TextView) current_cell
                    .findViewById(R.id.round_hanger_cell_user);
            username2.setBackgroundResource(R.drawable.round_shape_purple2);
        }
        if (isEditMode()) {
            ibtnDelete.setVisibility(View.VISIBLE);
            ibtnAlarm.setVisibility(View.VISIBLE);
        }

    }

    View previous_cell_selected = null;
    View current_cell_selected = null;

    @Override
    public void onClick(View v) {
        if (previous_cell_selected == null) {
            previous_cell_selected = current_cell_selected;
            current_cell_selected = v;
        } else {
            previous_cell_selected = current_cell_selected;
            current_cell_selected = v;
        }

        switch (v.getId()) {
            case R.id.user_linear_cell1:
                if (checkLong == 1) {
                    LinearLayout userCellFull = (LinearLayout) v
                            .findViewById(R.id.container_linear_cell_user_full);
                    if (userSelectedLayout.contains(userCellFull)) {
                        userCellFull
                                .setBackgroundResource(R.drawable.round_corner_white);
                        userSelectedLayout.remove(userCellFull);
                        userIdForDelete.remove((Integer) user_list.get(0).getID());
                        break;
                    }
                    handleLongSelect(v, user_list.get(0).getID());
                    break;
                }
                if (previous_cell_selected == current_cell_selected)
                    userClicked(user_list.get(0).getID());
                else {
                    setCellSelectedColor((LinearLayout) previous_cell_selected,
                            (LinearLayout) current_cell_selected);

                    currentUserIdForDelete = user_list.get(0).getID();
                }
                break;
            case R.id.user_linear_cell2:
                if (checkLong == 1) {
                    LinearLayout userCellFull = (LinearLayout) v
                            .findViewById(R.id.container_linear_cell_user_full);
                    if (userSelectedLayout.contains(userCellFull)) {
                        userCellFull
                                .setBackgroundResource(R.drawable.round_corner_white);
                        userSelectedLayout.remove(userCellFull);
                        userIdForDelete.remove((Integer) user_list.get(1).getID());
                        break;
                    }
                    handleLongSelect(v, user_list.get(1).getID());
                    break;
                }
                if (previous_cell_selected == current_cell_selected)
                    userClicked(user_list.get(1).getID());
                else {
                    setCellSelectedColor((LinearLayout) previous_cell_selected,
                            (LinearLayout) current_cell_selected);

                    currentUserIdForDelete = user_list.get(1).getID();
                }
                break;
            case R.id.user_linear_cell3:
                if (checkLong == 1) {
                    LinearLayout userCellFull = (LinearLayout) v
                            .findViewById(R.id.container_linear_cell_user_full);
                    if (userSelectedLayout.contains(userCellFull)) {
                        userCellFull
                                .setBackgroundResource(R.drawable.round_corner_white);
                        userSelectedLayout.remove(userCellFull);
                        userIdForDelete.remove((Integer) user_list.get(2).getID());
                        break;
                    }
                    handleLongSelect(v, user_list.get(2).getID());
                    break;
                }
                if (previous_cell_selected == current_cell_selected)
                    userClicked(user_list.get(2).getID());
                else {
                    setCellSelectedColor((LinearLayout) previous_cell_selected,
                            (LinearLayout) current_cell_selected);

                    currentUserIdForDelete = user_list.get(2).getID();
                }
                break;
            case R.id.user_linear_cell4:
                if (checkLong == 1) {
                    LinearLayout userCellFull = (LinearLayout) v
                            .findViewById(R.id.container_linear_cell_user_full);
                    if (userSelectedLayout.contains(userCellFull)) {
                        userCellFull
                                .setBackgroundResource(R.drawable.round_corner_white);
                        userSelectedLayout.remove(userCellFull);
                        userIdForDelete.remove((Integer) user_list.get(3).getID());
                        break;
                    }
                    handleLongSelect(v, user_list.get(3).getID());
                    break;
                }
                if (previous_cell_selected == current_cell_selected)
                    userClicked(user_list.get(3).getID());
                else {
                    setCellSelectedColor((LinearLayout) previous_cell_selected,
                            (LinearLayout) current_cell_selected);
                    currentUserIdForDelete = user_list.get(3).getID();
                }
                break;
            case R.id.user_linear_cell5:
                if (checkLong == 1) {
                    LinearLayout userCellFull = (LinearLayout) v
                            .findViewById(R.id.container_linear_cell_user_full);
                    if (userSelectedLayout.contains(userCellFull)) {
                        userCellFull
                                .setBackgroundResource(R.drawable.round_corner_white);
                        userSelectedLayout.remove(userCellFull);
                        userIdForDelete.remove((Integer) user_list.get(4).getID());
                        break;
                    }
                    handleLongSelect(v, user_list.get(4).getID());
                    break;
                }
                if (previous_cell_selected == current_cell_selected)
                    userClicked(user_list.get(4).getID());
                else {
                    setCellSelectedColor((LinearLayout) previous_cell_selected,
                            (LinearLayout) current_cell_selected);
                    currentUserIdForDelete = user_list.get(4).getID();
                }
                break;
            case R.id.user_linear_cell6:
                if (checkLong == 1) {
                    LinearLayout userCellFull = (LinearLayout) v
                            .findViewById(R.id.container_linear_cell_user_full);
                    if (userSelectedLayout.contains(userCellFull)) {
                        userCellFull
                                .setBackgroundResource(R.drawable.round_corner_white);
                        userSelectedLayout.remove(userCellFull);
                        userIdForDelete.remove((Integer) user_list.get(5).getID());
                        break;
                    }
                    handleLongSelect(v, user_list.get(5).getID());
                    break;
                }
                if (previous_cell_selected == current_cell_selected)
                    userClicked(user_list.get(5).getID());
                else {
                    setCellSelectedColor((LinearLayout) previous_cell_selected,
                            (LinearLayout) current_cell_selected);
                    currentUserIdForDelete = user_list.get(5).getID();
                }
                break;
            case R.id.user_linear_cell7:
                if (checkLong == 1) {
                    LinearLayout userCellFull = (LinearLayout) v
                            .findViewById(R.id.container_linear_cell_user_full);
                    if (userSelectedLayout.contains(userCellFull)) {
                        userCellFull
                                .setBackgroundResource(R.drawable.round_corner_white);
                        userSelectedLayout.remove(userCellFull);
                        userIdForDelete.remove((Integer) user_list.get(6).getID());
                        break;
                    }
                    handleLongSelect(v, user_list.get(6).getID());
                    break;
                }
                if (previous_cell_selected == current_cell_selected)
                    userClicked(user_list.get(6).getID());
                else {
                    setCellSelectedColor((LinearLayout) previous_cell_selected,
                            (LinearLayout) current_cell_selected);
                    currentUserIdForDelete = user_list.get(6).getID();
                }
                break;
            case R.id.user_linear_cell8:
                if (checkLong == 1) {
                    LinearLayout userCellFull = (LinearLayout) v
                            .findViewById(R.id.container_linear_cell_user_full);
                    if (userSelectedLayout.contains(userCellFull)) {
                        userCellFull
                                .setBackgroundResource(R.drawable.round_corner_white);
                        userSelectedLayout.remove(userCellFull);
                        userIdForDelete.remove((Integer) user_list.get(7).getID());
                        break;
                    }
                    handleLongSelect(v, user_list.get(7).getID());
                    break;
                }
                if (previous_cell_selected == current_cell_selected)
                    userClicked(user_list.get(7).getID());
                else {
                    setCellSelectedColor((LinearLayout) previous_cell_selected,
                            (LinearLayout) current_cell_selected);
                    currentUserIdForDelete = user_list.get(7).getID();
                }
                break;
            case R.id.user_linear_cell9:
                if (checkLong == 1) {
                    LinearLayout userCellFull = (LinearLayout) v
                            .findViewById(R.id.container_linear_cell_user_full);
                    if (userSelectedLayout.contains(userCellFull)) {
                        userCellFull
                                .setBackgroundResource(R.drawable.round_corner_white);
                        userSelectedLayout.remove(userCellFull);
                        userIdForDelete.remove((Integer) user_list.get(8).getID());
                        break;
                    }
                    handleLongSelect(v, user_list.get(8).getID());
                    break;
                }
                if (previous_cell_selected == current_cell_selected)
                    userClicked(user_list.get(8).getID());
                else {
                    setCellSelectedColor((LinearLayout) previous_cell_selected,
                            (LinearLayout) current_cell_selected);
                    currentUserIdForDelete = user_list.get(8).getID();
                }
                break;
            case R.id.user_linear_cell10:
                if (checkLong == 1) {
                    LinearLayout userCellFull = (LinearLayout) v
                            .findViewById(R.id.container_linear_cell_user_full);
                    if (userSelectedLayout.contains(userCellFull)) {
                        userCellFull
                                .setBackgroundResource(R.drawable.round_corner_white);
                        userSelectedLayout.remove(userCellFull);
                        userIdForDelete.remove((Integer) user_list.get(9).getID());
                        break;
                    }
                    handleLongSelect(v, user_list.get(9).getID());
                    break;
                }
                if (previous_cell_selected == current_cell_selected)
                    userClicked(user_list.get(9).getID());
                else {
                    setCellSelectedColor((LinearLayout) previous_cell_selected,
                            (LinearLayout) current_cell_selected);
                    currentUserIdForDelete = user_list.get(9).getID();
                }
                break;
            case R.id.user_linear_cell11:
                if (checkLong == 1) {
                    LinearLayout userCellFull = (LinearLayout) v
                            .findViewById(R.id.container_linear_cell_user_full);
                    if (userSelectedLayout.contains(userCellFull)) {
                        userCellFull
                                .setBackgroundResource(R.drawable.round_corner_white);
                        userSelectedLayout.remove(userCellFull);
                        userIdForDelete.remove((Integer) user_list.get(10).getID());
                        break;
                    }
                    handleLongSelect(v, user_list.get(10).getID());
                    break;
                }
                if (previous_cell_selected == current_cell_selected)
                    userClicked(user_list.get(10).getID());
                else {
                    setCellSelectedColor((LinearLayout) previous_cell_selected,
                            (LinearLayout) current_cell_selected);
                    currentUserIdForDelete = user_list.get(10).getID();
                }
                break;
            case R.id.user_linear_cell12:
                if (checkLong == 1) {
                    LinearLayout userCellFull = (LinearLayout) v
                            .findViewById(R.id.container_linear_cell_user_full);
                    if (userSelectedLayout.contains(userCellFull)) {
                        userCellFull
                                .setBackgroundResource(R.drawable.round_corner_white);
                        userSelectedLayout.remove(userCellFull);
                        userIdForDelete.remove((Integer) user_list.get(11).getID());
                        break;
                    }
                    handleLongSelect(v, user_list.get(11).getID());
                    break;
                }
                if (previous_cell_selected == current_cell_selected)
                    userClicked(user_list.get(11).getID());
                else {
                    setCellSelectedColor((LinearLayout) previous_cell_selected,
                            (LinearLayout) current_cell_selected);
                    currentUserIdForDelete = user_list.get(11).getID();
                }
                break;
            case R.id.user_linear_cell13:
                if (checkLong == 1) {
                    LinearLayout userCellFull = (LinearLayout) v
                            .findViewById(R.id.container_linear_cell_user_full);
                    if (userSelectedLayout.contains(userCellFull)) {
                        userCellFull
                                .setBackgroundResource(R.drawable.round_corner_white);
                        userSelectedLayout.remove(userCellFull);
                        userIdForDelete.remove((Integer) user_list.get(12).getID());
                        break;
                    }
                    handleLongSelect(v, user_list.get(12).getID());
                    break;
                }
                if (previous_cell_selected == current_cell_selected)
                    userClicked(user_list.get(12).getID());
                else {
                    setCellSelectedColor((LinearLayout) previous_cell_selected,
                            (LinearLayout) current_cell_selected);
                    currentUserIdForDelete = user_list.get(12).getID();
                }
                break;
            case R.id.user_linear_cell14:
                if (checkLong == 1) {
                    LinearLayout userCellFull = (LinearLayout) v
                            .findViewById(R.id.container_linear_cell_user_full);
                    if (userSelectedLayout.contains(userCellFull)) {
                        userCellFull
                                .setBackgroundResource(R.drawable.round_corner_white);
                        userSelectedLayout.remove(userCellFull);
                        userIdForDelete.remove((Integer) user_list.get(13).getID());
                        break;
                    }
                    handleLongSelect(v, user_list.get(13).getID());
                    break;
                }
                if (previous_cell_selected == current_cell_selected)
                    userClicked(user_list.get(13).getID());
                else {
                    setCellSelectedColor((LinearLayout) previous_cell_selected,
                            (LinearLayout) current_cell_selected);
                    currentUserIdForDelete = user_list.get(13).getID();
                }
                break;
            case R.id.user_linear_cell15:
                if (checkLong == 1) {
                    LinearLayout userCellFull = (LinearLayout) v
                            .findViewById(R.id.container_linear_cell_user_full);
                    if (userSelectedLayout.contains(userCellFull)) {
                        userCellFull
                                .setBackgroundResource(R.drawable.round_corner_white);
                        userSelectedLayout.remove(userCellFull);
                        userIdForDelete.remove((Integer) user_list.get(14).getID());
                        break;
                    }
                    handleLongSelect(v, user_list.get(14).getID());
                    break;
                }
                if (previous_cell_selected == current_cell_selected)
                    userClicked(user_list.get(14).getID());
                else {
                    setCellSelectedColor((LinearLayout) previous_cell_selected,
                            (LinearLayout) current_cell_selected);
                    currentUserIdForDelete = user_list.get(14).getID();
                }
                break;

            default:
                break;
        }

    }

    private void handleLongSelect(View v, int userId) {
        if (isEditMode()) {
            LinearLayout userCell = (LinearLayout) v
                    .findViewById(R.id.container_linear_cell_user);
            LinearLayout userCellFull = (LinearLayout) v
                    .findViewById(R.id.container_linear_cell_user_full);
            userCell.setBackgroundResource(R.drawable.round_corner_white);
            userCellFull.setBackgroundResource(R.drawable.round_corner_blue);

            TextView username = (TextView) v
                    .findViewById(R.id.round_hanger_cell_user);
            username.setBackgroundResource(R.drawable.round_shape_white);
            if (!userIdForDelete.contains(userId))
                userIdForDelete.add(userId);

            if (!userSelectedLayout.contains(userCellFull))
                userSelectedLayout.add(userCellFull);

            ibtnDelete.setVisibility(View.VISIBLE);
            ibtnAlarm.setVisibility(View.VISIBLE);
        } else {
            checkLong = 0;
        }
    }

    @Override
    public boolean onLongClick(View v) {
        currentUserIdForDelete = 0;
        switch (v.getId()) {
            case R.id.user_linear_cell1:
                checkLong = 1;
                handleLongSelect(v, user_list.get(0).getID());
                break;
            case R.id.user_linear_cell2:
                checkLong = 1;
                handleLongSelect(v, user_list.get(1).getID());
                break;
            case R.id.user_linear_cell3:
                checkLong = 1;
                handleLongSelect(v, user_list.get(2).getID());
                break;
            case R.id.user_linear_cell4:
                checkLong = 1;
                handleLongSelect(v, user_list.get(3).getID());
                break;
            case R.id.user_linear_cell5:
                checkLong = 1;
                handleLongSelect(v, user_list.get(4).getID());
                break;
            case R.id.user_linear_cell6:
                checkLong = 1;
                handleLongSelect(v, user_list.get(5).getID());
                break;
            case R.id.user_linear_cell7:
                checkLong = 1;
                handleLongSelect(v, user_list.get(6).getID());
                break;
            case R.id.user_linear_cell8:
                checkLong = 1;
                handleLongSelect(v, user_list.get(7).getID());
                break;
            case R.id.user_linear_cell9:
                checkLong = 1;
                handleLongSelect(v, user_list.get(8).getID());
                break;
            case R.id.user_linear_cell10:
                checkLong = 1;
                handleLongSelect(v, user_list.get(9).getID());
                break;
            case R.id.user_linear_cell11:
                checkLong = 1;
                handleLongSelect(v, user_list.get(10).getID());
                break;
            case R.id.user_linear_cell12:
                checkLong = 1;
                handleLongSelect(v, user_list.get(11).getID());
                break;
            case R.id.user_linear_cell13:
                checkLong = 1;
                handleLongSelect(v, user_list.get(12).getID());
                break;
            case R.id.user_linear_cell14:
                checkLong = 1;
                handleLongSelect(v, user_list.get(13).getID());
                break;
            case R.id.user_linear_cell15:
                checkLong = 1;
                handleLongSelect(v, user_list.get(14).getID());
                break;
            default:
                break;
        }
        return true;
    }

    private void refreshSelection() {
        for (LinearLayout i : userSelectedLayout) {
            i.setBackgroundResource(R.drawable.round_corner_white);
        }
        userIdForDelete.clear();
        checkLong = 0;
        // Toast.makeText(ref, userSelectedLayout.size(),
        // Toast.LENGTH_SHORT).show();
    }

    public void startCropImageUser(File file) {

        Intent intent = new Intent(this, CropImage.class);
        intent.putExtra(CropImage.IMAGE_PATH, file.getPath());
        intent.putExtra(CropImage.SCALE, true);
        intent.putExtra(CropImage.OUTPUT_X, 512);
        intent.putExtra(CropImage.OUTPUT_Y, 512);
        intent.putExtra(CropImage.ASPECT_X, 2);
        intent.putExtra(CropImage.ASPECT_Y, 2);

        startActivityForResult(intent, REQUEST_CODE_CROP_IMAGE);
    }

}
