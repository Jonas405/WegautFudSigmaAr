package com.example.samplestickerapp;

import android.util.Log;
import android.widget.ImageView;

import com.google.ar.core.Config;
import com.google.ar.core.Session;
import com.google.ar.sceneform.ux.ArFragment;

public class CustomArFragment extends ArFragment {



    @Override
    protected Config getSessionConfiguration(Session session) {


        getPlaneDiscoveryController().setInstructionView(null);

        Config config = new Config(session);
        config.setUpdateMode(Config.UpdateMode.LATEST_CAMERA_IMAGE);
        config.setFocusMode(Config.FocusMode.AUTO);
        session.configure(config);
        this.getArSceneView().setupSession(session);


     /*   if (((CustomArActivity)getActivity()).setupAugmentedImageDb(config,session)){
            Log.d("SetupAugImgDB", "Sucess");
        }
         else{
             Log.e("SetupAugImgDB", "Failed to setup db");
        }*/

        this.getArSceneView().setupSession(session);
        ((CustomArActivity) getActivity()).setupDatabase(config,session);
        return config;

    }
}
