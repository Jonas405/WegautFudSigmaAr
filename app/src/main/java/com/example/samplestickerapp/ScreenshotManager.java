package com.example.samplestickerapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.Image;
import android.media.ImageReader;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.HandlerThread;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.UiThread;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.security.auth.callback.Callback;

public class ScreenshotManager {
    private static final String SCREENCAP_NAME = "screencap";
    private static final int VIRTUAL_DISPLAY_FLAGS = DisplayManager.VIRTUAL_DISPLAY_FLAG_OWN_CONTENT_ONLY | DisplayManager.VIRTUAL_DISPLAY_FLAG_PUBLIC;
    public static final ScreenshotManager INSTANCE = new ScreenshotManager();
    private Intent mIntent;

    Bitmap screenPhotoTaked;

    ScreenshotManager() {
    }

    public void requestScreenshotPermission(@NonNull Activity activity, int requestId) {
        MediaProjectionManager mediaProjectionManager = (MediaProjectionManager) activity.getSystemService(Context.MEDIA_PROJECTION_SERVICE);
        activity.startActivityForResult(mediaProjectionManager.createScreenCaptureIntent(), requestId);
    }


    public void onActivityResult(int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && data != null)
            mIntent = data;
        else mIntent = null;
    }

    @UiThread
    public boolean takeScreenshot(@NonNull Context context) {
        if (mIntent == null)
            return false;
        final MediaProjectionManager mediaProjectionManager = (MediaProjectionManager) context.getSystemService(Context.MEDIA_PROJECTION_SERVICE);
        final MediaProjection mediaProjection = mediaProjectionManager.getMediaProjection(Activity.RESULT_OK, mIntent);
        if (mediaProjection == null)
            return false;
        final int density = context.getResources().getDisplayMetrics().densityDpi;
        final Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        final Point size = new Point();
        display.getSize(size);
        final int width = size.x, height = size.y;

        // start capture reader
        final ImageReader imageReader = ImageReader.newInstance(width, height, PixelFormat.RGBA_8888, 1);
        final VirtualDisplay virtualDisplay = mediaProjection.createVirtualDisplay(SCREENCAP_NAME, width, height, density, VIRTUAL_DISPLAY_FLAGS, imageReader.getSurface(), null, null);
        imageReader.setOnImageAvailableListener(new ImageReader.OnImageAvailableListener() {
            @Override
            public void onImageAvailable(final ImageReader reader) {
                Log.d("AppLog", "onImageAvailable");
                mediaProjection.stop();
                new AsyncTask<Void, Void, Bitmap>() {
                    @Override
                    protected Bitmap doInBackground(final Void... params) {
                        Image image = null;
                        Bitmap bitmap = null;
                        try {
                            image = reader.acquireLatestImage();
                            if (image != null) {
                                Image.Plane[] planes = image.getPlanes();
                                ByteBuffer buffer = planes[0].getBuffer();
                                int pixelStride = planes[0].getPixelStride(), rowStride = planes[0].getRowStride(), rowPadding = rowStride - pixelStride * width;
                                bitmap = Bitmap.createBitmap(width + rowPadding / pixelStride, height, Bitmap.Config.ARGB_8888);
                                bitmap.copyPixelsFromBuffer(buffer);
                                saveBitmapToDisk(bitmap);

                         /*       File file = new File(context.getCacheDir(), "bob.png");
                                FileOutputStream fOut = new FileOutputStream(file);
                                bitmap.compress(Bitmap.CompressFormat.PNG, 80, fOut);
                                fOut.flush();
                                fOut.close();
                                file.setReadable(true,false);*/
                                //Sharing intent

                                String path = MediaStore.Images.Media.insertImage(context.getContentResolver(),
                                        bitmap, "Design", null);

                                Uri uri = Uri.parse(path);

                                Intent intent = new Intent(Intent.ACTION_SEND);
                                intent.setType("image/*");
                                intent.putExtra(Intent.EXTRA_STREAM, uri);
                                intent.putExtra(Intent.EXTRA_TEXT, "I found something cool!");
                                context.startActivity(Intent.createChooser(intent, "Share Your Design!"));

                           //     intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                     //           intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
                           //     intent.setType("image/*");
                          //      context.startActivity(Intent.createChooser(intent, "Share"));
                                return bitmap;
                            }
                        } catch (Exception e) {
                            if (bitmap != null)
                                bitmap.recycle();
                            e.printStackTrace();
                        }
                        if (image != null)
                            image.close();
                        reader.close();
                        return null;
                    }

                    @Override
                    protected void onPostExecute(final Bitmap bitmap) {
                        super.onPostExecute(bitmap);
                        Log.d("AppLog", "Got bitmap?" + (bitmap != null));
                    }
                }.execute();
            }
        }, null);

        mediaProjection.registerCallback(new MediaProjection.Callback() {
            @Override
            public void onStop() {
                super.onStop();
                if (virtualDisplay != null)
                    virtualDisplay.release();
                imageReader.setOnImageAvailableListener(null, null);
                mediaProjection.unregisterCallback(this);
            }
        },null);
        return true;
    }

    public void saveBitmapToDisk(Bitmap bitmap) throws IOException {

        // String path = Environment.getExternalStorageDirectory().toString() +  "/Pictures/Screenshots/";

        File videoDirectory = null;
        if (videoDirectory == null) {
            videoDirectory =
                    new File(
                            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                                    + "/Screenshots");
        }

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss");
        String formattedDate = df.format(c.getTime());
        File mediaFile = new File(videoDirectory, "FieldVisualizer"+formattedDate+".jpeg");
        FileOutputStream fileOutputStream = new FileOutputStream(mediaFile);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, fileOutputStream);
        fileOutputStream.flush();
        fileOutputStream.close();

     //   screenPhotoTaked = bitmap;

      /*  File file = new File(context.getExternalCacheDir(), "images");
        mediaFile.setReadable(true,false);
        //Sharing intent
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
        intent.setType("image/*");
        context.startActivity(Intent.createChooser(intent, "Share"));
*/

    }

    /*public void shareToSocialMedia(Context context) throws IOException {


        File file = new File(context.getCacheDir(), "bob.png");
        FileOutputStream fOut = new FileOutputStream(file);
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, fOut);
        fOut.flush();
        fOut.close();
        file.setReadable(true,false);
        //Sharing intent
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
        intent.setType("image/*");
        context.startActivity(Intent.createChooser(intent, "Share"));
    }*/


}