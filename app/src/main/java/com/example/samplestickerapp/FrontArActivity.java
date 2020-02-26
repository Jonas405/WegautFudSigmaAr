package com.example.samplestickerapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.media.projection.MediaProjectionManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.PixelCopy;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.ar.core.AugmentedFace;
import com.google.ar.core.Frame;
import com.google.ar.sceneform.ArSceneView;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.Renderable;
import com.google.ar.sceneform.rendering.Texture;
import com.google.ar.sceneform.ux.AugmentedFaceNode;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

public class FrontArActivity extends AppCompatActivity {

    private ModelRenderable modelRenderable;
    private Texture texture;
    private boolean isAdded = false;

    private ImageView img;
    ImageView buttonPhotoFront;

    View frameLayout;

    ImageView imageView;

    private CustomArFrontFragment customArFrontFragment;


    private static final int REQUEST_ID = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front_ar);

        frameLayout = (View) findViewById(R.id.frontFrameView);

        imageView = findViewById(R.id.image);
        //ButtonPhoto
        buttonPhotoFront = findViewById(R.id.btnShanpshotFront);
        customArFrontFragment = (CustomArFrontFragment) getSupportFragmentManager().findFragmentById(R.id.arFrontFragment);
        // getIntent() is a method from the started activity
        Intent myIntent = getIntent(); // gets the previously created intent
        String marco = myIntent.getStringExtra("marco"); // will return "FirstKeyValue"

        Toast.makeText(getBaseContext(),"Marco" + marco,Toast.LENGTH_LONG).show();

        Context c = getApplicationContext();
        int id = c.getResources().getIdentifier("drawable/"+marco, null, c.getPackageName());
        ((ImageView)findViewById(R.id.image)).setImageResource(id);





        ModelRenderable
                .builder().setSource(this,R.raw.fox_face)
                .build()
                .thenAccept(renderable -> {

                    modelRenderable = renderable;
                    modelRenderable.setShadowCaster(false);
                    modelRenderable.setShadowReceiver(false);

                });

        Texture
                .builder().setSource(this, R.drawable.fox_face_mesh_texture)
                .build()
                .thenAccept(texture -> this.texture = texture);

        customArFrontFragment.getArSceneView().setCameraStreamRenderPriority(Renderable.RENDER_PRIORITY_FIRST);

        customArFrontFragment.getArSceneView().getScene().addOnUpdateListener(frameTime -> {

            if (modelRenderable == null || texture == null)
                return;

            Frame frame= customArFrontFragment.getArSceneView().getArFrame();


            Collection<AugmentedFace> augmentedFaces = frame.getUpdatedTrackables(AugmentedFace.class);

            for (AugmentedFace augmentedFace : augmentedFaces){
                if (isAdded)
                    return;
                AugmentedFaceNode augmentedFaceNode = new AugmentedFaceNode(augmentedFace);
                augmentedFaceNode.setParent(customArFrontFragment.getArSceneView().getScene());
                augmentedFaceNode.setFaceRegionsRenderable(modelRenderable);
                augmentedFaceNode.setFaceMeshTexture(texture);

                isAdded = true;
            }
        });

        buttonPhotoFront.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pushButton();
            }
            private void pushButton() {

                  //  takePhoto();
           //     View rootView = getWindow().getDecorView().findViewById(android.R.id.content);
            //                  Bitmap bitmap = getScreenShot(rootView);
              //                store(bitmap, "ScreenShot.png");

                ScreenshotManager.INSTANCE.requestScreenshotPermission(FrontArActivity.this, REQUEST_ID);
                ScreenshotManager.INSTANCE.takeScreenshot(FrontArActivity.this);

                Toast.makeText(getBaseContext(),"BUTTON Screen",Toast.LENGTH_SHORT).show();
            }

        });

    }

    private void shareScreen() {
        try {

            File cacheDir = new File(
                    android.os.Environment.getExternalStorageDirectory(),
                    "devdeeds");

            if (!cacheDir.exists()) {
                cacheDir.mkdirs();
            }

            String path = new File(
                    android.os.Environment.getExternalStorageDirectory(),
                    "devdeeds") + "/screenshot.jpg";

            Utils.savePic(Utils.takeScreenShot(this), path);

            Toast.makeText(getApplicationContext(), "Screenshot Saved", Toast.LENGTH_SHORT).show();


        } catch (NullPointerException ignored) {
            ignored.printStackTrace();
        }
    }


    private void takePhoto() {

        ArSceneView view = customArFrontFragment.getArSceneView();



        // Create a bitmap the size of the scene view.
        final Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),
                Bitmap.Config.ARGB_8888);

        // Create a handler thread to offload the processing of the image.
        final HandlerThread handlerThread = new HandlerThread("PixelCopier");
        handlerThread.start();
        // Make the request to copy.
        PixelCopy.request(view, bitmap, (copyResult) -> {
            if (copyResult == PixelCopy.SUCCESS) {
                try {
                    saveBitmapToDisk(bitmap);
                    } catch (IOException e) {
                        Toast toast = Toast.makeText(FrontArActivity.this, e.toString(),
                                Toast.LENGTH_LONG);
                        toast.show();
                        return;
                    }
                    Toast.makeText(getBaseContext(),"Screenshot saved in /Pictures/Screenshots",Toast.LENGTH_SHORT).show();
                    //   SnackbarUtility.showSnackbarTypeLong(settingsButton, "Screenshot saved in /Pictures/Screenshots");

                } else {

                    Toast.makeText(getBaseContext(),"Failed to take screenshot",Toast.LENGTH_SHORT).show();
                    //  SnackbarUtility.showSnackbarTypeLong(settingsButton, "Failed to take screenshot");

                }
                handlerThread.quitSafely();
            }, new Handler(handlerThread.getLooper()));


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
    }


    public static Bitmap getScreenShot(View view){
        View screemView = view.getRootView();

        screemView.setDrawingCacheEnabled(false);
        Bitmap bitmap = Bitmap.createBitmap(screemView.getDrawingCache());
        screemView.setDrawingCacheEnabled(true);
        return bitmap;
    }

    //Store the image on the device
    public void store(Bitmap bm, String fileName){
        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/MyFudFiles";
        File dir = new File(dirPath);
        if (!dir.exists()){
            dir.mkdirs();
        }
        File file = new File(dirPath,fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this,"Error", Toast.LENGTH_LONG);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ID)
            ScreenshotManager.INSTANCE.onActivityResult(resultCode, data);
    }


}

