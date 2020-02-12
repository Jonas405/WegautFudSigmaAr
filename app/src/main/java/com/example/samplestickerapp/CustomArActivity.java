package com.example.samplestickerapp;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.ar.core.Anchor;
import com.google.ar.core.AugmentedImage;
import com.google.ar.core.AugmentedImageDatabase;
import com.google.ar.core.Config;
import com.google.ar.core.Frame;
import com.google.ar.core.Session;
import com.google.ar.core.TrackingState;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.FrameTime;
import com.google.ar.sceneform.Scene;
import com.google.ar.sceneform.rendering.ModelRenderable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;

public class CustomArActivity extends AppCompatActivity implements Scene.OnUpdateListener {


    private CustomArFragment arFragment;
    Button buttonPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Entro a la clase en el onCreate", "Entro a la clase en el onCreate");
        //Scene AR
        setContentView(R.layout.activity_custom_ar);
        arFragment = (CustomArFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        arFragment.getArSceneView().getScene().addOnUpdateListener(this);

        //ButtonPhoto
        Button buttonPhoto = findViewById(R.id.btnShanpshot);
        buttonPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pushButton();
            }

            private void pushButton() {

                // saveToInternalStorage();
                Toast.makeText(getBaseContext(),"ORESI",Toast.LENGTH_SHORT).show();
            }


        });
    }


    private String saveToInternalStorage(Bitmap bitmapImage){
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

 /*   private void readInternalStorageloadImageFromStorage(String path)
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

    }*/

    public void setupDatabase(Config config, Session session){
        //dba config and created
        //Get resource from drawable path
        Bitmap foxBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.fox);
        Bitmap bobsponaJrBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.bobsponjajr);
        Bitmap calamardoJrBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.calamardojr);
        Bitmap patricioJrBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.patriciojr);
        Bitmap donconcangrejoJrBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.doncangrejojr);

        //Create new image database and start session
        AugmentedImageDatabase aid = new AugmentedImageDatabase(session);

        //Add image to image db
        aid.addImage("fox",foxBitmap);
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
        Collection<AugmentedImage> images = frame.getUpdatedTrackables(AugmentedImage.class);
        for (AugmentedImage image : images) {

            if (image.getTrackingState() == TrackingState.TRACKING) {

                //Create Model Fox
                if (image.getName().equals("fox")) {
                    Anchor anchor = image.createAnchor(image.getCenterPose());
                    createModelFox(anchor);

                    //Create Bob Sponja
                } if (image.getName().equals("bobsponjajr")) {
                    Anchor anchor = image.createAnchor(image.getCenterPose());
                    createModelBob(anchor);

                    //Create Calamardo
                } if (image.getName().equals("calamardojr")) {
                    Anchor anchor = image.createAnchor(image.getCenterPose());
                    createModelCalamardo(anchor);

                    //Create Patricio
                } if (image.getName().equals("patriciojr")) {
                    Anchor anchor = image.createAnchor(image.getCenterPose());
                    createModelPatricio(anchor);

                    //Create Don cangrejo
                } if (image.getName().equals("donconcangrejojr")) {
                    Anchor anchor = image.createAnchor(image.getCenterPose());
                    createModelDonCangrejo(anchor);
                }
            }
        }
    }

    private void createModelFox(Anchor anchor){

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


    private void placeModel(ModelRenderable modelRenderable, Anchor anchor) {

        AnchorNode anchorNode = new AnchorNode(anchor);
        anchorNode.setRenderable(modelRenderable);
        arFragment.getArSceneView().getScene().addChild(anchorNode);
    }



}
