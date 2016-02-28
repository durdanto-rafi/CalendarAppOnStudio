package com.uniqgroup.utility;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Environment;
import android.util.Base64;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.uniqgroup.application.R;

public class ImageProcessing {
	String imageNameForSDCard;
	private String appImagePath = null;
	Context ctx;
	
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	DisplayImageOptions options;
	
	public ImageProcessing(Context context){
		ctx = context;
		System.out.println(ctx.getResources().getString(R.string.image_folder_path));
		appImagePath = "/Android/Data/"+ ctx.getPackageName()+"/Images/";
		
		imageLoader.init(ImageLoaderConfiguration.createDefault(context));
		options = new DisplayImageOptions.Builder()
				.showImageForEmptyUri(R.drawable.ic_launcher)
				.showImageOnFail(R.drawable.ic_launcher)
				.resetViewBeforeLoading(true).cacheOnDisc(false)
				.imageScaleType(ImageScaleType.EXACTLY)
				// .bitmapConfig(Bitmap.Config.RGB_565).considerExifParams(true)
				.displayer(new FadeInBitmapDisplayer(0)).build();
		 imageLoader.clearDiscCache(); 
		
	}
	
	/*public void setImageWith_loader(ImageView iv, String path){
		imageLoader.displayImage("file:///" +getAbsolutepath_Of_Image(path), iv, options);
	}*/
	
	public void setImageWith_loader2(ImageView iv, String path){
		//System.out.println("file:///"+getAbsolutepath_Of_Image(path));
		imageLoader.displayImage("file:///"+path, iv, options);
	}
	
	public void setImageWith_loader(ImageView iv, String path){
		//System.out.println("file:///"+getAbsolutepath_Of_Image(path));
		imageLoader.displayImage("file:///"+getAbsolutepath_Of_Image(path), iv, options);
	}
	
	public static String encodeTobase64(Bitmap image) {
		//Bitmap immagex = Bitmap.createScaledBitmap(image, 512, 512, false);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 50, baos);
		byte[] b = baos.toByteArray();
		String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

		return imageEncoded;
	}

	// image decode method
	public static Bitmap decodeBase64(String input) {
		byte[] decodedByte = Base64.decode(input, 0);
		return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
	}

	// Save image to SD card
	public String imageSave(Bitmap bmp)
	{
		try
		{
		    File sdCardDirectory = new File(Environment.getExternalStorageDirectory() + appImagePath);
		    if (!sdCardDirectory.exists()) 
	        {
		    	sdCardDirectory.mkdirs();
	        }

		    imageNameForSDCard = "calenApp_"  + System.currentTimeMillis() + ".jpg";
		    File image = new File(sdCardDirectory, imageNameForSDCard);
		    FileOutputStream outStream;

		    outStream = new FileOutputStream(image);
		    bmp.compress(Bitmap.CompressFormat.PNG, 100, outStream); 
		    outStream.flush();
		    outStream.close();
		    bmp.recycle();
		    return imageNameForSDCard;
		}
		catch (Exception e) 
		{
		    e.printStackTrace();
		    return null;
		}
	}
	
	public String getAbsolutepath_Of_Image(String imageName){
		return Environment.getExternalStorageDirectory() + appImagePath + imageName;
	}	
	// Getting Image from SD Card
	
	public Bitmap getImage(String imageName)
	{
		File image = new File(getAbsolutepath_Of_Image(imageName));
	    if (image.exists()) 
        {
			Bitmap bmp = BitmapFactory.decodeFile(image.getAbsolutePath());
			return bmp;
		}
	    else
	    {
	    	return null;
	    }
	}

	private Bitmap resize(Bitmap originalImage, int width, int height)
	{
		Bitmap background = Bitmap.createBitmap((int)width, (int)height, Config.ARGB_8888);
		float originalWidth = originalImage.getWidth(), originalHeight = originalImage.getHeight();
		Canvas canvas = new Canvas(background);
		float scale = width/originalWidth;
		float xTranslation = 0.0f, yTranslation = (height - originalHeight * scale)/2.0f;
		Matrix transformation = new Matrix();
		transformation.postTranslate(xTranslation, yTranslation);
		transformation.preScale(scale, scale);
		Paint paint = new Paint();
		paint.setFilterBitmap(true);
		canvas.drawBitmap(originalImage, transformation, paint);
		originalImage.recycle();
		return background;
	}

	public boolean deleteImage(String imageName)
	{
		boolean deleted = false;
		File image = new File(getAbsolutepath_Of_Image(imageName));
	    if (image.exists()) 
        {
	    	deleted = image.delete();
		}
	    return deleted;
	}

	public String getImageDir()
	{
		return appImagePath;
	}
}
