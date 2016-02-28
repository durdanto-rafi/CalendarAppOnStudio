package com.uniqgroup.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.uniqgroup.application.R;
import com.uniqgroup.application.UserCreateActivity;
import com.uniqgroup.application.UserEventListActivity;
import com.uniqgroup.pojo.User;
import com.uniqgroup.utility.EditModeSetting;
import com.uniqgroup.utility.ImageProcessing;

public class UserCreateAdapter extends BaseAdapter {

	List<User> result;
	Context context;
	int[] imageId;
	private static LayoutInflater inflater = null;

	public UserCreateAdapter(Context context, List<User> user_list) {
		// TODO Auto-generated constructor stub
		result = user_list;
		this.context = context;
		// imageId=prgmImages;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return result.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public class Holder {
		TextView tv;
		ImageView img;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		
		View rowView = convertView;
	    // reuse views
	    if (rowView == null) {
	      rowView = inflater.inflate(R.layout.user_grid_custom_row, null);
	      Holder holder = new Holder();
	      holder.tv = (TextView) rowView.findViewById(R.id.user_title);
		  holder.img = (ImageView) rowView.findViewById(R.id.user_avatar);
		  rowView.setTag(holder);
	    }

	    // fill data
	    Holder holder = (Holder) rowView.getTag();
	    holder.tv.setText(result.get(position).getName());
		//Bitmap pro_pic = ImageProcessing.decodeBase64(result.get(position).getProfilePic());
	    ImageProcessing imgProc = new ImageProcessing(context);
	    imgProc.setImageWith_loader(holder.img,result.get(position).getProfilePic());
		//holder.img.setImageBitmap(imgProc.getImage(result.get(position).getProfilePic()));
		
		//Boolean isEdit = ((UserCreateActivity)context).isEditMode();
		
		
		rowView.setOnTouchListener(new CustomOntouchListener(result.get(position).getID()));

//		rowView.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				
//			}
//		});

		return rowView;
	}

	public void resetAdapterData(List<User> new_user_list) {

		result = new_user_list;
		notifyDataSetChanged();

	}
	
	//OnTouchListener toach_listener_cell = new CustomOntouchListener()
			
	class CustomOntouchListener	implements OnTouchListener {
		
		
		int id = 0;
		View v = null;
		int user_id = 0;
		public CustomOntouchListener( int user_id ){
			this.user_id = user_id;
		}
		
		GestureDetector gestureDetector = new GestureDetector( context,
				new GestureDetector.SimpleOnGestureListener() {
					@Override
					public boolean onDoubleTap(MotionEvent e) {
//						image_buttonClick_listener(v, id);
						// Toast.makeText(EventCreateActivity.this, "DoubleTap",
						// Toast.LENGTH_LONG).show();
						return super.onDoubleTap(e);

					}

					@Override
					public boolean onSingleTapConfirmed(MotionEvent e) {
						// Toast.makeText(EventCreateActivity.this,
						// "onSingleTapConfirmed", Toast.LENGTH_LONG).show();
						

						//Log.d("chk",);
						
						if(EditModeSetting.isEditMode == 1){
							//custom dialog .....and save .....
							((UserCreateActivity)context).showEditDiallog(user_id);
							
						}else{
							Intent eventList = new Intent(context, UserEventListActivity.class);
							eventList.putExtra("user_id", String.valueOf(user_id)); //Optional parameters
							context.startActivity(eventList);
							((Activity)context).finish();
						}
						
						/*Intent eventList = new Intent(context, UserEventListActivity.class);
						eventList.putExtra("user_id", String.valueOf(user_id)); //Optional parameters
						context.startActivity(eventList);
						((Activity)context).finish();*/
						
						
						return true;
					}

					@Override
					public void onLongPress(MotionEvent e) {
						// Toast.makeText(EventCreateActivity.this,
						// "onLongPress", Toast.LENGTH_LONG).show();
						
					}
				});

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			id = v.getId();
			this.v = v;
			gestureDetector.onTouchEvent(event);
			return true;
		}
	};
	
	
	

}