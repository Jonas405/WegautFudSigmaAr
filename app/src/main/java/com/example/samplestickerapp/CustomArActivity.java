package com.example.samplestickerapp;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.view.PixelCopy;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;


public class CustomArActivity extends AppCompatActivity implements Scene.OnUpdateListener{


    private CustomArFragment arFragment;
    private boolean shouldAddModel = true;
    Button buttonPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Entro a la clase en el onCreate", "Entro a la clase en el onCreate");
        //Scene AR
        setContentView(R.layout.activity_custom_ar);
        arFragment = (CustomArFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);

        //New code
        arFragment.getPlaneDiscoveryController().hide();
        // New Code method setOn new method addOn
       // arFragment.getArSceneView().getScene().addOnUpdateListener(this::onUpdateFrame);

     //

        //Old Code (comment below)
        arFragment.getArSceneView().getScene().addOnUpdateListener(this);

        //ButtonPhoto
        Button buttonPhoto = findViewById(R.id.btnShanpshot);
        buttonPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pushButton();
            }

            private void pushButton() {

                takePhoto();
                // saveToInternalStorage();
                Toast.makeText(getBaseContext(),"ORESI",Toast.LENGTH_SHORT).show();
            }


        });
    }


    // New Code  /////////////////////////////////////////////////////////////

    //Create database augmented image
  /*  public boolean setupAugmentedImageDb(Config config, Session session){
        AugmentedImageDatabase augmentedImageDatabase;

        Bitmap bitmap = loadAugmentedImage();

        if (bitmap == null){
            return  false;
        }

        augmentedImageDatabase = new AugmentedImageDatabase(session);
        augmentedImageDatabase.addImage("bobsponjajr", bitmap);

        config.setAugmentedImageDatabase(augmentedImageDatabase);
        return true;
    }


*/
  //Other youtube mulyiple imag
/*  private boolean buildDatabase(Config config, Session session){
      AugmentedImageDatabase augmentedImageDatabase;
      try {
          InputStream inputStream = getAssets().open("AugmentedImagesFudSnax.imgdb");
          augmentedImageDatabase = AugmentedImageDatabase.deserialize(session,inputStream);
          config.setAugmentedImageDatabase(augmentedImageDatabase);
          return true;
      } catch (IOException e) {
          e.printStackTrace();
          return false;
      }
  }*/

    //update the frame
/*
    private void onUpdateFrame(FrameTime frameTime){
            Frame frame = arFragment.getArSceneView().getArFrame();

            Collection<AugmentedImage> augmentedImages = frame.getUpdatedTrackables(AugmentedImage.class);

            for (AugmentedImage augmentedImage : augmentedImages){

                if (augmentedImage.getTrackingState() == TrackingState.TRACKING){

                    if (augmentedImage.getName().equals("bobsponjajr") && shouldAddModel){
                        placeObject(arFragment,
                                augmentedImage.createAnchor(augmentedImage.getCenterPose()),
                                Uri.parse("bob_young_V101.sfb"));
                        shouldAddModel = false;
                    }

                }
            }

    }
*/


 /*   // Create a node Object AR
    private void placeObject(ArFragment fragment, Anchor anchor, Uri model){
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

    private void addNodeToScene(ArFragment fragment, Anchor anchor, Renderable renderable){
        AnchorNode anchorNode = new AnchorNode(anchor);
        TransformableNode node = new TransformableNode(fragment.getTransformationSystem());
        node.setRenderable(renderable);
        node.setParent(anchorNode);
        fragment.getArSceneView().getScene().addChild(anchorNode);
        node.select();
    }
*/




    ////////////////////////////////////////////////////////////////////////// Old Code

   /* private String saveToInternalStorage(Bitmap bitmapImage){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath=new File(directory,"profile.jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }

    private void readInternalStorageloadImageFromStorage(String path)
    {

        try {
            File f=new File(path, "profile.jpg");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            ImageView img=(ImageView)findViewById(R.id.imgPicker);
            img.setImageBitmap(b);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

    }
*/
    public void setupDatabase(Config config, Session session){
        //dba config and created
        //Get resource from drawable path
        Bitmap bobsponaJrBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.bobsponjajr);
        Bitmap calamardoJrBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.calamardojr);
        Bitmap patricioJrBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.patriciojr);
        Bitmap donconcangrejoJrBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.doncangrejojr);

        //Create new image database and start session
        AugmentedImageDatabase aid = new AugmentedImageDatabase(session);

        //Add image to image db
        aid.addImage("bobsponjajr",bobsponaJrBitmap);
        aid.addImage("calamardojr",calamardoJrBitmap);
        aid.addImage("patriciojr",patricioJrBitmap);
        aid.addImage("doncagrejojr",donconcangrejoJrBitmap);

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
                  if (image.getName().equals("bobsponjajr") && shouldAddModel){
                        placeObject(arFragment,
                                image.createAnchor(image.getCenterPose()),
                                Uri.parse("bob_young_V101.sfb"));
                        shouldAddModel = false;

                    //Create Calamardo
                } if (image.getName().equals("calamardojr") && shouldAddModel) {
                        placeObject(arFragment,
                                image.createAnchor(image.getCenterPose()),
                                Uri.parse("squidward_young_V083.sfb"));
                        shouldAddModel = false;

                    //Create Patricio
                } if (image.getName().equals("patriciojr") && shouldAddModel) {
                            placeObject(arFragment,
                                    image.createAnchor(image.getCenterPose()),
                                    Uri.parse("patrick_young_V069.sfb"));
                            shouldAddModel = false;

                    //Create Don cangrejo
                } if (image.getName().equals("donconcangrejojr") && shouldAddModel) {
                            placeObject(arFragment,
                                    image.createAnchor(image.getCenterPose()),
                                    Uri.parse("MrKrabs_valid.sfb"));
                            shouldAddModel = false;
                }
            }
        }
    }

  /*  private void createModelFox(Anchor anchor){

        ModelRenderable.builder()
                .setSource(this, Uri.parse("ArcticFox_Posed.sfb"))
                .build()
                .thenAccept(modelRenderable -> placeModel(modelRenderable, anchor));
    }

    private void createModelBob(Anchor anchor){

        ModelRenderable.builder()
                .setSource(this, Uri.parse("bob_young_V101.sfb"))
                .build()
                .thenAccept(modelRenderable -> placeModel(modelRenderable, anchor));
    }

    private void createModelDonCangrejo(Anchor anchor){

        ModelRenderable.builder()
                .setSource(this, Uri.parse("MrKrabs_valid.sfb"))
                .build()
                .thenAccept(modelRenderable -> placeModel(modelRenderable, anchor));
    }

    private void createModelCalamardo(Anchor anchor){

        ModelRenderable.builder()
                .setSource(this, Uri.parse("squidward_young_V083.sfb"))
                .build()
                .thenAccept(modelRenderable -> placeModel(modelRenderable, anchor));
    }

    private void createModelPatricio(Anchor anchor){

        ModelRenderable.builder()
                .setSource(this, Uri.parse("patrick_young_V069.sfb"))
                .build()
                .thenAccept(modelRenderable -> placeModel(modelRenderable, anchor));
    }
*/
/*
    private void placeModel(ModelRenderable modelRenderable, Anchor anchor) {


        AnchorNode anchorNode = new AnchorNode(anchor);
        anchorNode.setRenderable(modelRenderable);
        arFragment.getArSceneView().getScene().addChild(anchorNode);
    }*/


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

    private void addNodeToScene(ArFragment fragment, Anchor anchor, Renderable renderable){
        Log.d("Construyendo", "Construyendo Nodo");
        AnchorNode anchorNode = new AnchorNode(anchor);
        TransformableNode node = new TransformableNode(fragment.getTransformationSystem());
        node.setRenderable(renderable);
        node.setParent(anchorNode);
        fragment.getArSceneView().getScene().addChild(anchorNode);

    }


    // take a photo method
    private void takePhoto() {

        ArSceneView view = arFragment.getArSceneView();

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

}
