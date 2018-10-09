package com.mobdev.pxl.pokmart.utilities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.URL;

public class UrlBitmapLoader {
    public static Bitmap LoadBitmapFromUrl(URL url) {
        Bitmap bmp = null;
        try {
            InputStream urlStream = url.openStream();
            bmp = BitmapFactory.decodeStream(urlStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bmp;
    }
}
