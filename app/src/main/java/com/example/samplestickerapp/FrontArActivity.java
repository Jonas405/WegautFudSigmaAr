package com.example.samplestickerapp;

import android.annotation.SuppressLint;
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
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.PixelCopy;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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

    //Add parameter to moving img
    private int xDelta;
    private int yDelta;

    //Frame
    ImageView marcox1;
    ImageView marcox2;
    ImageView marcox3;
    ImageView marcox4;
    ImageView marcox5;
    ImageView marcox6;
    ImageView marcox7;
    ImageView marcox8;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front_ar);

        frameLayout = (View) findViewById(R.id.frontFrameView);

        imageView = findViewById(R.id.image);

        Button backButton = findViewById(R.id.volverButton);

        //imageMoving
        //ButtonPhoto
        buttonPhotoFront = findViewById(R.id.btnShanpshotFront);
        customArFrontFragment = (CustomArFrontFragment) getSupportFragmentManager().findFragmentById(R.id.arFrontFragment);


        //-------------- This is for receiver from the front the intent and then put the image


        // getIntent() is a method from the started activity Marco
        /*Intent myIntent = getIntent(); // gets the previously created intent
        String marco = myIntent.getStringExtra("marco"); // will return "FirstKeyValue"
        Context c = getApplicationContext();
        int id = c.getResources().getIdentifier("drawable/"+marco, null, c.getPackageName());
        ((ImageView)findViewById(R.id.image)).setImageResource(id);


        // getIntent() is a method from the started activity Front Image

        String frontImage = myIntent.getStringExtra("frontImage"); // will return "frontImage"
        Context cFront = getApplicationContext();
        int cFrontImage = cFront.getResources().getIdentifier("drawable/"+frontImage, null, c.getPackageName());
        ((ImageView)findViewById(R.id.imageMoving)).setImageResource(cFrontImage);

*/

        LinearLayout gallery = findViewById(R.id.gallery);
        LayoutInflater inflater = LayoutInflater.from(this);

      /*  for (int i = 0; i < 6; i ++){
            View view = inflater.inflate(R.layout.item, gallery, false);
            ImageView imageView = view.findViewById(R.id.imageView);
            imageView.setImageResource(R.mipmap.ic_launcher);
            gallery.addView(view);
        }*/

        View marco1 = inflater.inflate(R.layout.item, gallery, false);
        marcox1 = marco1.findViewById(R.id.imageView);
        marcox1.setImageResource(R.drawable.macrco1finalfinal);


        View marco2 = inflater.inflate(R.layout.item, gallery, false);
        marcox2 = marco2.findViewById(R.id.imageView);
        marcox2.setImageResource(R.drawable.marco2finalfinal);


        View marco3 = inflater.inflate(R.layout.item, gallery, false);
        marcox3 = marco3.findViewById(R.id.imageView);
        marcox3.setImageResource(R.drawable.marco3finalfinal);


        View marco4 = inflater.inflate(R.layout.item, gallery, false);
        marcox4 = marco4.findViewById(R.id.imageView);
        marcox4.setImageResource(R.drawable.marco4finalfinal);

        View marco5 = inflater.inflate(R.layout.item, gallery, false);
        marcox5 = marco5.findViewById(R.id.imageView);
        marcox5.setImageResource(R.drawable.marco5finalfinal);

        View marco6 = inflater.inflate(R.layout.item, gallery, false);
        marcox6 = marco6.findViewById(R.id.imageView);
        marcox6.setImageResource(R.drawable.marco6finalfinal);

        View marco7 = inflater.inflate(R.layout.item, gallery, false);
        marcox7 = marco7.findViewById(R.id.imageView);
        marcox7.setImageResource(R.drawable.marco7finalfinal);

        View marco8 = inflater.inflate(R.layout.item, gallery, false);
        marcox8 = marco8.findViewById(R.id.imageView);
        marcox8.setImageResource(R.drawable.marco8finalfinal);


        gallery.addView(marco1);
        gallery.addView(marco2);
        gallery.addView(marco3);
        gallery.addView(marco4);
        gallery.addView(marco5);
        gallery.addView(marco6);
        gallery.addView(marco7);
        gallery.addView(marco8);

      /*  marcox1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustomArActivity.this, EntryActivity.class));
            }
        });*/

        marcox1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(),"Marco1",Toast.LENGTH_SHORT).show();
                ((ImageView)findViewById(R.id.image)).setImageResource(R.drawable.macrco1finalfinal);
            }
        });
        marcox2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(),"Marco2",Toast.LENGTH_SHORT).show();
                ((ImageView)findViewById(R.id.image)).setImageResource(R.drawable.marco2finalfinal);
            }
        });
        marcox3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(),"Marco3",Toast.LENGTH_SHORT).show();
                ((ImageView)findViewById(R.id.image)).setImageResource(R.drawable.marco3finalfinal);
            }
        });
        marcox4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(),"Marco4",Toast.LENGTH_SHORT).show();
                ((ImageView)findViewById(R.id.image)).setImageResource(R.drawable.marco4finalfinal);
            }
        });
        marcox5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(),"Marco4",Toast.LENGTH_SHORT).show();
                ((ImageView)findViewById(R.id.image)).setImageResource(R.drawable.marco5finalfinal);
            }
        });
        marcox6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(),"Marco4",Toast.LENGTH_SHORT).show();
                ((ImageView)findViewById(R.id.image)).setImageResource(R.drawable.marco6finalfinal);
            }
        });
        marcox7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(),"Marco4",Toast.LENGTH_SHORT).show();
                ((ImageView)findViewById(R.id.image)).setImageResource(R.drawable.marco7finalfinal);
            }
        });
        marcox8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(),"Marco4",Toast.LENGTH_SHORT).show();
                ((ImageView)findViewById(R.id.image)).setImageResource(R.drawable.marco8finalfinal);
            }
        });
        
        //Comment augmented facing
/*        ModelRenderable
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
                .thenAccept(texture -> this.texture = texture);*/

/*
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
*/

        Runnable mRunnable;
        Handler mHandler=new Handler();

        mRunnable= new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                //     yourLayoutObject.setVisibility(View.INVISIBLE); //If you want just hide the View. But it will retain space occupied by the View.
              //This will remove the View. and free s the space occupied by the View
                buttonPhotoFront.setVisibility(View.VISIBLE);
                backButton.setVisibility(View.VISIBLE);
            }
        };

       backButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               startActivity(new Intent(FrontArActivity.this, CustomArActivity.class));
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
                backButton.setVisibility(View.GONE);
                buttonPhotoFront.setVisibility(View.GONE);
                ScreenshotManager.INSTANCE.requestScreenshotPermission(FrontArActivity.this, REQUEST_ID);
                ScreenshotManager.INSTANCE.takeScreenshot(FrontArActivity.this);
                mHandler.postDelayed(mRunnable,10*500);

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