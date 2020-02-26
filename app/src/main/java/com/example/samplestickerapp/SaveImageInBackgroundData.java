package com.example.samplestickerapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;

public class SaveImageInBackgroundData {
    Context context;
    Bitmap image;
    Uri imageUri;
    Runnable finisher;
    int iconSize;
    int previewWidth;
    int previewheight;
    int errorMsgResId;
    void clearImage() {
        image = null;
        imageUri = null;
        iconSize = 0;
    }
    void clearContext() {
        context = null;
    }
}