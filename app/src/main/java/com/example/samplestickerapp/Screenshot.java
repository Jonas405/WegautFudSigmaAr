package com.example.samplestickerapp;

import android.graphics.Bitmap;
import android.view.View;

public class Screenshot {

    public static Bitmap takescreenShot(View v){
        v.setDrawingCacheEnabled(true);
        v.buildDrawingCache(true);
        Bitmap b = Bitmap.createBitmap(v.getDrawingCache());
        v.setDrawingCacheEnabled(false);
        return b;
    }

    public static Bitmap takeScreenshotOfRootView(View v){
        return takescreenShot(v.getRootView());
    }
}
