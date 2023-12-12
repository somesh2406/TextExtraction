package com.somesh.textextraction.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.SimpleDateFormat;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class CameraUtils {
        public static File createImageFile(Context context) throws IOException {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String imageFileName = "JPEG" + timeStamp + "_";
            File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            File image = File.createTempFile(
                    imageFileName, ".jpg", storageDir
            );

            return image;
        }


        public static Bitmap getBitmap(String path) {
            try {

                File f= new File(path);
                Bitmap myBitmap = BitmapFactory.decodeFile(f.getAbsolutePath());
                return myBitmap;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }}
    }

}
