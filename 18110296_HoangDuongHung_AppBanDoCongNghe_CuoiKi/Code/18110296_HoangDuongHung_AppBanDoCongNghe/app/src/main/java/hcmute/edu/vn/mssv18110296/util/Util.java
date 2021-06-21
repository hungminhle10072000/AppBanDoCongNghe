package hcmute.edu.vn.mssv18110296.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {
    public static boolean saveImageToSDCard (Context context,Bitmap image, String folder, String name){
        String fullpath = context.getApplicationContext().getExternalFilesDir(null) + folder + "/";

        try {
            File dir = new File(fullpath);
            if (!dir.exists()){
                dir.mkdirs();
            }

            OutputStream fOut = null;
            File file = new File(fullpath, name);
            if (!file.exists()) {
                file.createNewFile();
            }
            fOut = new FileOutputStream(file);

            image.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();

            return true;
        } catch (Exception e){
            Log.e("saveToExternalStorage",e.getMessage());
            return false;
        }
    }
    public static String getCurrentDateTime(){
        ///get date time
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datetime =  dateFormat.format(new Date());
        return datetime;
    }

    public static boolean isSDReadble(){

        boolean mExternalStorageAvailable = false;
        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)) {

            ///We can read and write the media
            mExternalStorageAvailable = true;
            Log.i("isSdreadable","External storage card is readable");
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)){
            ///Some thing else is wrong, It may be one of many other
            ///State, but all we need to know  is we can neithe read nor write
            mExternalStorageAvailable = false;
            Log.i("isSdreadable","External storage card is readable");
        } else {
            mExternalStorageAvailable = false;
        }
        return mExternalStorageAvailable;
    }

    public static Date convertStringToDate (String datetime){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = dateFormat.parse(datetime);
            return date;
        } catch (Exception e){
            return null;
        }
    }

    public static void setBitmapToImage (final Context context, final String folder, final String name, final ImageView imageView){
        final Handler handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                Bitmap bitmap = (Bitmap) msg.obj;
                if(bitmap != null){
                    imageView.setImageBitmap(bitmap);
                } else {
                    imageView.setVisibility(ImageView.GONE);
                }
            }
        };

        //set Flag
        try {
            Thread thread = new Thread((Runnable) () -> {
                Bitmap bitmap =  hcmute.edu.vn.mssv18110296.util.Util.readImage(folder, name, context);
                Message msg = new Message();
                msg.obj = bitmap;
                handler.sendMessage(msg);
            });
            thread.start();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static Bitmap readImage(String folder, String filename, Context context){
        Bitmap img = null;

        ///read image from SD card
        String fullpath = context.getApplicationContext().getExternalFilesDir(null) + folder + "/" + filename;
        try {
            img = BitmapFactory.decodeFile(fullpath);
        } catch (Exception e){
            Log.i("DemoReadWriteImage","Can not read image fromSD card");
        }

        ///read image from internal memory
        try {
            File myFile = context.getFileStreamPath(filename);
            FileInputStream fln = new FileInputStream(myFile);

            img = BitmapFactory.decodeStream(fln);
        } catch (Exception e){
            Log.i("DemoWriteImage","Can not read image from internal memory");
        }
        return img;
    }
    public static String convertStringDatetimeToFileName(String date){
        return date.toString().replace(":","").replace(" ","").replace("-","");
    }
}
