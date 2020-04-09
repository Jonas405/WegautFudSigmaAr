package com.example.samplestickerapp;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.Image;
import android.media.ImageReader;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Display;
import android.view.PixelCopy;
import android.view.View;
import android.view.WindowManager;
import android.view.contentcapture.ContentCaptureSession;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.UiThread;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.ar.core.Anchor;
import com.google.ar.core.AugmentedImage;
import com.google.ar.core.AugmentedImageDatabase;
import com.google.ar.core.Config;
import com.google.ar.core.Frame;
import com.google.ar.core.Session;
import com.google.ar.core.TrackingState;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.ArSceneView;
import com.google.ar.sceneform.FrameTime;
import com.google.ar.sceneform.Scene;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.Renderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;

public class CustomArActivity extends AppCompatActivity implements Scene.OnUpdateListener{


    private CustomArFragment arFragment;
    private boolean shouldAddModel = true;
    ImageView buttonPhoto;
    ImageView InfoBtn;
    ImageView ProfileBtn;
    ImageView GalleryBtn;

    Button buttonFrontTest;



    ImageView logoFud;
    ImageView logoBob;

    android.widget.RelativeLayout menuBar;

    //Screen permission request
    private static final int REQUEST_ID = 1;

    //Add preview screenshot
    private View main;
    private ImageView imageViewPreview;

    public static final int PICK_IMAGE = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        //Scene AR
        setContentView(R.layout.activity_custom_ar);
        arFragment = (CustomArFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);

        //New code
        arFragment.getPlaneDiscoveryController().hide();

        //Old Code (comment below)
        arFragment.getArSceneView().getScene().addOnUpdateListener(this);

        //Screenshot preview
        main = findViewById(R.id.main);
        imageViewPreview = (ImageView) findViewById(R.id.imageViewPreview);

        //Fragment and logo

        ImageView bobSponjaLogo = findViewById(R.id.logoBobFragment);
        ImageView fudLogo = findViewById(R.id.logoFudFragment);

        //ButtonPhoto
        buttonPhoto = findViewById(R.id.btnShanpshot);
        InfoBtn = findViewById(R.id.infoBtn);
        ProfileBtn = findViewById(R.id.ProfileBtn);
        GalleryBtn = findViewById(R.id.PhotoBtnGallery);
        //menuBar
        menuBar = findViewById(R.id.menuBar);
        //FrontButton
        buttonFrontTest = findViewById(R.id.frontTest);

        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(this,"bd_usuarios",null,1);

        buttonFrontTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CustomArActivity.this, FrontArActivity.class));
            }
        });


        InfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustomArActivity.this, intrucctions.class));
            }
        });

        Runnable mRunnable;
        Handler mHandler=new Handler();

        mRunnable=new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
           //     yourLayoutObject.setVisibility(View.INVISIBLE); //If you want just hide the View. But it will retain space occupied by the View.
                buttonPhoto.setVisibility(View.VISIBLE); //This will remove the View. and free s the space occupied by the View
                menuBar.setVisibility(View.VISIBLE);
            }
        };

        buttonPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    pushButton();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            private void pushButton() throws IOException {

                buttonPhoto.setVisibility(View.GONE);
                menuBar.setVisibility(View.GONE);

                ScreenshotManager.INSTANCE.requestScreenshotPermission(CustomArActivity.this, REQUEST_ID);
                ScreenshotManager.INSTANCE.takeScreenshot(CustomArActivity.this);
                mHandler.postDelayed(mRunnable,10*500);

            }


        });


        ProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustomArActivity.this, EntryActivity.class));
            }
        });


        GalleryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);

            }
        });
    }

    public void setupDatabase(Config config, Session session){
        //dba config and created
        //Get resource from drawable path
        Bitmap bobsponaJrBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.bobsponjajr);
        Bitmap calamardoJrBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.calamardojr);
        Bitmap patricioJrBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.patriciojr);
        Bitmap donconcangrejoJrBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.doncangrejojr);





        //setup database with final images
        //esponjas
        Bitmap esponja1finalfinalBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.esponja1finalfinal);
        Bitmap esponja2finalfinalBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.esponja2finalfinal);
        Bitmap esponja3finalfinalBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.esponja3finalfinal);
        Bitmap esponja4finalfinalBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.esponja4finalfinal);
        Bitmap sponja5finalfinalBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.sponja5finalfinal);
        //Cangrejos
        Bitmap cangrejo1finalfinalBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.cangrejo1finalfinal);
        //arenita
        Bitmap arenita1finalfinalBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.arenita1finalfinal);
        Bitmap arenita2finalfinalBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.arenita2finalfinal);
        Bitmap arenita3finalfinalBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.arenita3finalfinal);
        //Bobteam
        Bitmap bobteam1finalfinalBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.bobteam1finalfinal);
        Bitmap bobteam2finalfinalBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.bobteam2finalfinal);
        //calamardo
        Bitmap calamardo2finalfinalBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.calamardo2finalfinal);
        Bitmap calamardo3finalfinalBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.calamardo3finalfinal);
        //Gary
        Bitmap gary1finalfinalBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.gary1finalfinal);
        //Medusas
        Bitmap medusas1finalfinalBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.medusas1finalfinal);
        //Robot
        Bitmap robot1finalfinalBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.robot1finalfinal);
        //team boat
        Bitmap teamboat1finalfinalBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.teamboat1finalfinal);
        Bitmap teambot2finalfinalBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.teambot2finalfinal);
        //Create new image database and start session
        AugmentedImageDatabase aid = new AugmentedImageDatabase(session);
        //Add image to image db
        aid.addImage("esponja1finalfinal",esponja1finalfinalBitmap);
        aid.addImage("esponja2finalfinal",esponja2finalfinalBitmap);
        aid.addImage("esponja3finalfinal",esponja3finalfinalBitmap);
        aid.addImage("esponja4finalfinal",esponja4finalfinalBitmap);
        aid.addImage("sponja5finalfinal",sponja5finalfinalBitmap);
        aid.addImage("cangrejo1finalfinal",cangrejo1finalfinalBitmap);
        aid.addImage("arenita1finalfinal",arenita1finalfinalBitmap);
        aid.addImage("arenita2finalfinal",arenita2finalfinalBitmap);
        aid.addImage("arenita3finalfinal",arenita3finalfinalBitmap);
        aid.addImage("bobteam1finalfinal",bobteam1finalfinalBitmap);
        aid.addImage("bobteam2finalfinal",bobteam2finalfinalBitmap);
        aid.addImage("calamardo2finalfinal",calamardo2finalfinalBitmap);
        aid.addImage("calamardo3finalfinal",calamardo3finalfinalBitmap);
        aid.addImage("medusas1finalfinal",medusas1finalfinalBitmap);
        aid.addImage("robot1finalfinal",robot1finalfinalBitmap);
        aid.addImage("gary1finalfinal",gary1finalfinalBitmap);
        aid.addImage("teamboat1finalfinal",teamboat1finalfinalBitmap);
        aid.addImage("teambot2finalfinal",teambot2finalfinalBitmap);


        //Set configuration in the dba
        config.setAugmentedImageDatabase(aid);

    }

    @Override
    public void onUpdate(FrameTime frameTime) {

        Frame frame = arFragment.getArSceneView().getArFrame();

        // If there is no frame, just return.
        if (frame == null) {
            return;
        }

        Collection<AugmentedImage> images = frame.getUpdatedTrackables(AugmentedImage.class);
        for (AugmentedImage image : images) {

            if (image.getTrackingState() == TrackingState.TRACKING) {
                //Create Bob Sponja
                if (image.getName().equals("esponja1finalfinal") && shouldAddModel){
                /*    placeObject(arFragment,
                            image.createAnchor(image.getCenterPose()),
                            Uri.parse("bob_young_V101.sfb"));*/

                    Intent myIntent = new Intent(CustomArActivity.this, UnlocketSticer.class);
                    myIntent.putExtra("frontImage","esponja1finalfinal");
                    myIntent.putExtra("marco","R.drawable.esponja1finalfinal");
                    startActivity(myIntent);
                    shouldAddModel = false;


                }  if (image.getName().equals("esponja2finalfinal") && shouldAddModel){
                /*    placeObject(arFragment,
                            image.createAnchor(image.getCenterPose()),
                            Uri.parse("bob_young_V101.sfb"));*/

                    Intent myIntent = new Intent(CustomArActivity.this, UnlocketSticer.class);
                    myIntent.putExtra("frontImage","esponja2finalfinal");
                    myIntent.putExtra("marco","R.drawable.esponja2finalfinal");
                    startActivity(myIntent);
                    shouldAddModel = false;


                }  if (image.getName().equals("esponja3finalfinal") && shouldAddModel){
                /*    placeObject(arFragment,
                            image.createAnchor(image.getCenterPose()),
                            Uri.parse("bob_young_V101.sfb"));*/

                    Intent myIntent = new Intent(CustomArActivity.this, UnlocketSticer.class);
                    myIntent.putExtra("frontImage","esponja3finalfinal");
                    myIntent.putExtra("marco","R.drawable.esponja3finalfinal");
                    startActivity(myIntent);
                    shouldAddModel = false;


                }  if (image.getName().equals("esponja4finalfinal") && shouldAddModel){
                /*    placeObject(arFragment,
                            image.createAnchor(image.getCenterPose()),
                            Uri.parse("bob_young_V101.sfb"));*/

                    Intent myIntent = new Intent(CustomArActivity.this, UnlocketSticer.class);
                    myIntent.putExtra("frontImage","esponja4finalfinal");
                    myIntent.putExtra("marco","R.drawable.esponja4finalfinal");
                    startActivity(myIntent);
                    shouldAddModel = false;


                }  if (image.getName().equals("sponja5finalfinal") && shouldAddModel){
                /*    placeObject(arFragment,
                            image.createAnchor(image.getCenterPose()),
                            Uri.parse("bob_young_V101.sfb"));*/

                    Intent myIntent = new Intent(CustomArActivity.this, UnlocketSticer.class);
                    myIntent.putExtra("frontImage","sponja5finalfinal");
                    myIntent.putExtra("marco","R.drawable.sponja5finalfinal");
                    startActivity(myIntent);
                    shouldAddModel = false;


                }  if (image.getName().equals("cangrejo1finalfinal") && shouldAddModel){
                /*    placeObject(arFragment,
                            image.createAnchor(image.getCenterPose()),
                            Uri.parse("bob_young_V101.sfb"));*/

                    Intent myIntent = new Intent(CustomArActivity.this, UnlocketSticer.class);
                    myIntent.putExtra("frontImage","cangrejo1finalfinal");
                    myIntent.putExtra("marco","R.drawable.cangrejo1finalfinal");
                    startActivity(myIntent);
                    shouldAddModel = false;


                }  if (image.getName().equals("arenita1finalfinal") && shouldAddModel){
                /*    placeObject(arFragment,
                            image.createAnchor(image.getCenterPose()),
                            Uri.parse("bob_young_V101.sfb"));*/

                    Intent myIntent = new Intent(CustomArActivity.this, UnlocketSticer.class);
                    myIntent.putExtra("frontImage","arenita1finalfinal");
                    myIntent.putExtra("marco","R.drawable.arenita1finalfinal");
                    startActivity(myIntent);
                    shouldAddModel = false;


                }  if (image.getName().equals("arenita2finalfinal") && shouldAddModel){
                /*    placeObject(arFragment,
                            image.createAnchor(image.getCenterPose()),
                            Uri.parse("bob_young_V101.sfb"));*/

                    Intent myIntent = new Intent(CustomArActivity.this, UnlocketSticer.class);
                    myIntent.putExtra("frontImage","arenita2finalfinal");
                    myIntent.putExtra("marco","R.drawable.arenita2finalfinal");
                    startActivity(myIntent);
                    shouldAddModel = false;


                }  if (image.getName().equals("arenita3finalfinal") && shouldAddModel){
                /*    placeObject(arFragment,
                            image.createAnchor(image.getCenterPose()),
                            Uri.parse("bob_young_V101.sfb"));*/

                    Intent myIntent = new Intent(CustomArActivity.this, UnlocketSticer.class);
                    myIntent.putExtra("frontImage","arenita3finalfinal");
                    myIntent.putExtra("marco","R.drawable.arenita3finalfinal");
                    startActivity(myIntent);
                    shouldAddModel = false;


                }  if (image.getName().equals("bobteam1finalfinal") && shouldAddModel){
                /*    placeObject(arFragment,
                            image.createAnchor(image.getCenterPose()),
                            Uri.parse("bob_young_V101.sfb"));*/

                    Intent myIntent = new Intent(CustomArActivity.this, UnlocketSticer.class);
                    myIntent.putExtra("frontImage","bobteam1finalfinal");
                    myIntent.putExtra("marco","R.drawable.bobteam1finalfinal");
                    startActivity(myIntent);
                    shouldAddModel = false;


                }  if (image.getName().equals("bobteam2finalfinal") && shouldAddModel){
                /*    placeObject(arFragment,
                            image.createAnchor(image.getCenterPose()),
                            Uri.parse("bob_young_V101.sfb"));*/

                    Intent myIntent = new Intent(CustomArActivity.this, UnlocketSticer.class);
                    myIntent.putExtra("frontImage","bobteam2finalfinal");
                    myIntent.putExtra("marco","R.drawable.bobteam2finalfinal");
                    startActivity(myIntent);
                    shouldAddModel = false;


                }  if (image.getName().equals("calamardo2finalfinal") && shouldAddModel){
                /*    placeObject(arFragment,
                            image.createAnchor(image.getCenterPose()),
                            Uri.parse("bob_young_V101.sfb"));*/

                    Intent myIntent = new Intent(CustomArActivity.this, UnlocketSticer.class);
                    myIntent.putExtra("frontImage","calamardo2finalfinal");
                    myIntent.putExtra("marco","R.drawable.calamardo2finalfinal");
                    startActivity(myIntent);
                    shouldAddModel = false;


                }  if (image.getName().equals("calamardo3finalfinal") && shouldAddModel){
                /*    placeObject(arFragment,
                            image.createAnchor(image.getCenterPose()),
                            Uri.parse("bob_young_V101.sfb"));*/

                    Intent myIntent = new Intent(CustomArActivity.this, UnlocketSticer.class);
                    myIntent.putExtra("frontImage","calamardo3finalfinal");
                    myIntent.putExtra("marco","R.drawable.calamardo3finalfinal");
                    startActivity(myIntent);
                    shouldAddModel = false;


                }  if (image.getName().equals("medusas1finalfinal") && shouldAddModel){
                /*    placeObject(arFragment,
                            image.createAnchor(image.getCenterPose()),
                            Uri.parse("bob_young_V101.sfb"));*/

                    Intent myIntent = new Intent(CustomArActivity.this, UnlocketSticer.class);
                    myIntent.putExtra("frontImage","medusas1finalfinal");
                    myIntent.putExtra("marco","R.drawable.medusas1finalfinal");
                    startActivity(myIntent);
                    shouldAddModel = false;


                }  if (image.getName().equals("robot1finalfinal") && shouldAddModel){
                /*    placeObject(arFragment,
                            image.createAnchor(image.getCenterPose()),
                            Uri.parse("bob_young_V101.sfb"));*/

                    Intent myIntent = new Intent(CustomArActivity.this, UnlocketSticer.class);
                    myIntent.putExtra("frontImage","robot1finalfinal");
                    myIntent.putExtra("marco","R.drawable.robot1finalfinal");
                    startActivity(myIntent);
                    shouldAddModel = false;


                }  if (image.getName().equals("gary1finalfinal") && shouldAddModel){
                /*    placeObject(arFragment,
                            image.createAnchor(image.getCenterPose()),
                            Uri.parse("bob_young_V101.sfb"));*/

                    Intent myIntent = new Intent(CustomArActivity.this, UnlocketSticer.class);
                    myIntent.putExtra("frontImage","gary1finalfinal");
                    myIntent.putExtra("marco","R.drawable.gary1finalfinal");
                    startActivity(myIntent);
                    shouldAddModel = false;


                }  if (image.getName().equals("teamboat1finalfinal") && shouldAddModel){
                /*    placeObject(arFragment,
                            image.createAnchor(image.getCenterPose()),
                            Uri.parse("bob_young_V101.sfb"));*/

                    Intent myIntent = new Intent(CustomArActivity.this, UnlocketSticer.class);
                    myIntent.putExtra("frontImage","teamboat1finalfinal");
                    myIntent.putExtra("marco","R.drawable.teamboat1finalfinal");
                    startActivity(myIntent);
                    shouldAddModel = false;


                }  if (image.getName().equals("teambot2finalfinal") && shouldAddModel){
                /*    placeObject(arFragment,
                            image.createAnchor(image.getCenterPose()),
                            Uri.parse("bob_young_V101.sfb"));*/

                    Intent myIntent = new Intent(CustomArActivity.this, UnlocketSticer.class);
                    myIntent.putExtra("frontImage","teambot2finalfinal");
                    myIntent.putExtra("marco","R.drawable.teambot2finalfinal");
                    startActivity(myIntent);
                    shouldAddModel = false;
                }
            }
        }
    }

    /// Code adaptation
    private void placeObject(ArFragment fragment, Anchor anchor, Uri model){
        Log.d("Construyendo", "Construyendo");
        ModelRenderable.builder()
                .setSource(fragment.getContext(), model)
                .build()
                .thenAccept(renderable -> addNodeToScene(fragment, anchor, renderable))
                .exceptionally((throwable -> {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage(throwable.getMessage())
                            .setTitle("Error!");
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    return null;
                }));
    }

    private void addNodeToScene(ArFragment fragment, Anchor anchor,Renderable renderable){
        Log.d("Construyendo", "Construyendo Nodo");
        AnchorNode anchorNode = new AnchorNode(anchor);
        TransformableNode node = new TransformableNode(fragment.getTransformationSystem());
        node.setRenderable(renderable);
        node.setParent(anchorNode);
        node.setParent(anchorNode);

        fragment.getArSceneView().getScene().addChild(anchorNode);

    }


    // take a photo method
    private void takePhoto() {

        ArSceneView view = arFragment.getArSceneView();

      //  CustomArFragment view = (CustomArFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);

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
          /*          File file = new File(CustomArActivity.this.getExternalCacheDir(),"myImage.png");
                    FileOutputStream fOut = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.PNG,80,fOut);
                    fOut.flush();
                    fOut.close();
                    file.setReadable(true,false);
                    //Share intent
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
                    intent.setType("image/*");
                    startActivity(Intent.createChooser(intent,"Share Image via"));*/

                } catch (IOException e) {
                    Toast toast = Toast.makeText(CustomArActivity.this, e.toString(),
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


    //Screenshot function
    //get screen shot of the app
    public static Bitmap getScreenShot(View view){
        View screemView = view.getRootView();
        screemView.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(screemView.getDrawingCache());
        screemView.setDrawingCacheEnabled(false);
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


    //------------------------------- Button -------------------------------------

    public void tackeAndSaveScreenShot(Activity mActivity) {


        View MainView = mActivity.getWindow().getDecorView();
        MainView.setDrawingCacheEnabled(true);
        MainView.buildDrawingCache();
        Bitmap MainBitmap = MainView.getDrawingCache();
        Rect frame = new Rect();

        mActivity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        //to remove statusBar from the taken sc
        int statusBarHeight = frame.top;
        //using screen size to create bitmap
        int width = mActivity.getWindowManager().getDefaultDisplay().getWidth();
        int height = mActivity.getWindowManager().getDefaultDisplay().getHeight();
        Bitmap OutBitmap = Bitmap.createBitmap(MainBitmap, 0, statusBarHeight, width, height - statusBarHeight);
        MainView.destroyDrawingCache();
        try {

            String path = Environment.getExternalStorageDirectory().toString();
            OutputStream fOut = null;
            //you can also using current time to generate name
            String name="YourName";
            File file = new File(path, name + ".png");
            fOut = new FileOutputStream(file);

            OutBitmap.compress(Bitmap.CompressFormat.PNG, 90, fOut);
            fOut.flush();
            fOut.close();

            //this line will add the saved picture to gallery
            MediaStore.Images.Media.insertImage(getContentResolver(), file.getAbsolutePath(), file.getName(), file.getName());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

   //shared image

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ID)
            ScreenshotManager.INSTANCE.onActivityResult(resultCode, data);

        if (requestCode == PICK_IMAGE) {
            //TODO: action
        }
    }

}