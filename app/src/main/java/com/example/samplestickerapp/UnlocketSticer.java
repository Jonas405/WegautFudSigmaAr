package com.example.samplestickerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Fragment;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class UnlocketSticer extends AppCompatActivity {

    private ImageView img;
    ImageView buttonPhotoFront, imageMovingB;

    String nameStickerDra = "bobteam";
    String price = "300";

    //Frame
    ImageView marcox1;
    ImageView marcox2;
    ImageView marcox3;
    ImageView marcox4;
    ImageView marcox5;
    ImageView marcox6;
    ImageView marcox7;
    ImageView marcox8;
    ImageView marcox9;
    ImageView marcox10;

    String marco;

    MarcoInfo marcoInfo = new MarcoInfo();

    //SQLITE
    ImageView imageViewStickerSQLite;
  //  public static SQLiteHelper sqLiteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unlocket_sticer);
        //Add Sticker to SQLite
        //add image SQLite
        imageViewStickerSQLite = (ImageView) findViewById(R.id.imageMoving);
    //    sqLiteHelper = new SQLiteHelper(this, "FoodDB.sqlite", null, 1);
    //    sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS FOOD(Id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, price VARCHAR, image BLOB)");

        Button addSticker = findViewById(R.id.addStickerInCollection);

        //imageMoving
        imageMovingB = findViewById(R.id.imageMoving);
        //ButtonPhoto
        buttonPhotoFront = findViewById(R.id.btnShanpshotFront);

        // getIntent() is a method from the started activity Marco
         Intent myIntent = getIntent(); // gets the previously created intent
         marco = myIntent.getStringExtra("marco"); // will return "FirstKeyValue"
         Context c = getApplicationContext();
    //    int id = c.getResources().getIdentifier("drawable/"+marco, null, c.getPackageName());
    //    ((ImageView)findViewById(R.id.image)).setImageResource(id);

        // getIntent() is a method from the started activity Front Image

        String frontImage = myIntent.getStringExtra("frontImage"); // will return "frontImage"
        Context cFront = getApplicationContext();
        int cFrontImage = cFront.getResources().getIdentifier("drawable/"+frontImage, null, c.getPackageName());
        ((ImageView)findViewById(R.id.imageMoving)).setImageResource(cFrontImage);

        marcoInfo.setMarcoInfo(myIntent.getStringExtra("marco"));

        addSticker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (marcoInfo.getMarcoInfo().equals("R.drawable.sponja1finalfinal")){
                    saveStickerIntoSQLitesponja1finalfinal();

                }  if (marcoInfo.getMarcoInfo().equals("R.drawable.esponja2finalfinal")){
                    saveStickerIntoSQLiteesponja2finalfinal();

                }  if (marcoInfo.getMarcoInfo().equals("R.drawable.esponja3finalfinal")){
                    saveStickerIntoSQLiteesponja3finalfinal();


                }  if (marcoInfo.getMarcoInfo().equals("R.drawable.esponja4finalfinal")){
                    saveStickerIntoSQLiteesponja4finalfinal();

                }  if (marcoInfo.getMarcoInfo().equals("R.drawable.sponja5finalfinal")){
                    saveStickerIntoSQLitesponja5finalfinal();

                }  if (marcoInfo.getMarcoInfo().equals("R.drawable.cangrejo1finalfinal")){
                    saveStickerIntoSQLitecangrejo1finalfinal();

                }  if (marcoInfo.getMarcoInfo().equals("R.drawable.arenita1finalfinal")){
                    saveStickerIntoSQLitearenita1finalfinal();

                }  if (marcoInfo.getMarcoInfo().equals("R.drawable.arenita2finalfinal")){
                    saveStickerIntoSQLitearenita2finalfinal();

                }  if (marcoInfo.getMarcoInfo().equals("R.drawable.arenita3finalfinal")){
                    saveStickerIntoSQLitearenita3finalfinal();

                }  if (marcoInfo.getMarcoInfo().equals("R.drawable.bobteam1finalfinal")){
                    saveStickerIntoSQLitebobteam1finalfinal();

                }  if (marcoInfo.getMarcoInfo().equals("R.drawable.bobteam2finalfinal")){
                    Toast.makeText(getApplicationContext(),"Id Registro: "+ marco ,Toast.LENGTH_SHORT).show();
                    saveStickerIntoSQLitebobteam2finalfinal();

                }  if (marcoInfo.getMarcoInfo().equals("R.drawable.calamardo2finalfinal")){
                    saveStickerIntoSQLitecalamardo2finalfinal();

                }  if (marcoInfo.getMarcoInfo().equals("R.drawable.calamardo3finalfinal")){
                    saveStickerIntoSQLitecalamardo3finalfinal();

                }  if (marcoInfo.getMarcoInfo().equals("R.drawable.medusas1finalfinal")){
                    saveStickerIntoSQLitemedusas1finalfinall();

                }  if (marcoInfo.getMarcoInfo().equals("R.drawable.robot1finalfinal")){
                    saveStickerIntoSQLiterobot1finalfinal();

                }  if (marcoInfo.getMarcoInfo().equals("R.drawable.gary1finalfinal")){
                    saveStickerIntoSQLitegary1finalfinal();

                }  if (marcoInfo.getMarcoInfo().equals("R.drawable.teamboat1finalfinal")){
                    saveStickerIntoSQLiteteamboat1finalfinal();

                }  if (marcoInfo.getMarcoInfo().equals("R.drawable.teambot2finalfinal")){
                    saveStickerIntoSQLiteteambot2finalfinal();

                }

              //  saveStickerIntoSQLite();
        //        startActivity(new Intent(UnlocketSticer.this, EntryActivity.class));
            }

        });

    }

    private void saveStickerIntoSQLitesponja1finalfinal() {

        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(this,"bd_usuarios",null,1);
        SQLiteDatabase db=conn.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(StickerUtilitiesSQLite.CAMPO_ID,"33234");
        values.put(StickerUtilitiesSQLite.CAMPO_NOMBRE,"Texto");
        values.put(StickerUtilitiesSQLite.CAMPO_IMAGE, R.drawable.esponja1finalfinal);
        Long idResultante=db.insert(StickerUtilitiesSQLite.TABLA_USUARIO,StickerUtilitiesSQLite.CAMPO_ID,values);
        Toast.makeText(getApplicationContext(),"Id Registro: "+idResultante + "Texto" + marco,Toast.LENGTH_SHORT).show();
        db.close();

        startActivity(new Intent(UnlocketSticer.this, EntryActivity.class));
    }

    private void saveStickerIntoSQLiteesponja2finalfinal() {

        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(this,"bd_usuarios",null,1);
        SQLiteDatabase db=conn.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(StickerUtilitiesSQLite.CAMPO_ID,"33234");
        values.put(StickerUtilitiesSQLite.CAMPO_NOMBRE,"Texto");
        values.put(StickerUtilitiesSQLite.CAMPO_IMAGE, R.drawable.esponja2finalfinal);
        Long idResultante=db.insert(StickerUtilitiesSQLite.TABLA_USUARIO,StickerUtilitiesSQLite.CAMPO_ID,values);
        Toast.makeText(getApplicationContext(),"Id Registro: "+idResultante + "Texto" + marco,Toast.LENGTH_SHORT).show();
        db.close();

        startActivity(new Intent(UnlocketSticer.this, EntryActivity.class));
    }
    private void saveStickerIntoSQLiteesponja3finalfinal() {

        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(this,"bd_usuarios",null,1);
        SQLiteDatabase db=conn.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(StickerUtilitiesSQLite.CAMPO_ID,"33234");
        values.put(StickerUtilitiesSQLite.CAMPO_NOMBRE,"Texto");
        values.put(StickerUtilitiesSQLite.CAMPO_IMAGE, R.drawable.esponja3finalfinal);
        Long idResultante=db.insert(StickerUtilitiesSQLite.TABLA_USUARIO,StickerUtilitiesSQLite.CAMPO_ID,values);
        Toast.makeText(getApplicationContext(),"Id Registro: "+idResultante + "Texto" + marco,Toast.LENGTH_SHORT).show();
        db.close();

        startActivity(new Intent(UnlocketSticer.this, EntryActivity.class));
    }
    private void saveStickerIntoSQLiteesponja4finalfinal() {

        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(this,"bd_usuarios",null,1);
        SQLiteDatabase db=conn.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(StickerUtilitiesSQLite.CAMPO_ID,"33234");
        values.put(StickerUtilitiesSQLite.CAMPO_NOMBRE,"Texto");
        values.put(StickerUtilitiesSQLite.CAMPO_IMAGE, R.drawable.esponja4finalfinal);
        Long idResultante=db.insert(StickerUtilitiesSQLite.TABLA_USUARIO,StickerUtilitiesSQLite.CAMPO_ID,values);
        Toast.makeText(getApplicationContext(),"Id Registro: "+idResultante + "Texto" + marco,Toast.LENGTH_SHORT).show();
        db.close();

        startActivity(new Intent(UnlocketSticer.this, EntryActivity.class));
    }
    private void saveStickerIntoSQLitesponja5finalfinal() {

        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(this,"bd_usuarios",null,1);
        SQLiteDatabase db=conn.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(StickerUtilitiesSQLite.CAMPO_ID,"33234");
        values.put(StickerUtilitiesSQLite.CAMPO_NOMBRE,"Texto");
        values.put(StickerUtilitiesSQLite.CAMPO_IMAGE, R.drawable.sponja5finalfinal);
        Long idResultante=db.insert(StickerUtilitiesSQLite.TABLA_USUARIO,StickerUtilitiesSQLite.CAMPO_ID,values);
        Toast.makeText(getApplicationContext(),"Id Registro: "+idResultante + "Texto" + marco,Toast.LENGTH_SHORT).show();
        db.close();

        startActivity(new Intent(UnlocketSticer.this, EntryActivity.class));
    }
    private void saveStickerIntoSQLitecangrejo1finalfinal() {

        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(this,"bd_usuarios",null,1);
        SQLiteDatabase db=conn.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(StickerUtilitiesSQLite.CAMPO_ID,"33234");
        values.put(StickerUtilitiesSQLite.CAMPO_NOMBRE,"Texto");
        values.put(StickerUtilitiesSQLite.CAMPO_IMAGE, R.drawable.cangrejo1finalfinal);
        Long idResultante=db.insert(StickerUtilitiesSQLite.TABLA_USUARIO,StickerUtilitiesSQLite.CAMPO_ID,values);
        Toast.makeText(getApplicationContext(),"Id Registro: "+idResultante + "Texto" + marco,Toast.LENGTH_SHORT).show();
        db.close();

        startActivity(new Intent(UnlocketSticer.this, EntryActivity.class));
    }
    private void saveStickerIntoSQLitearenita1finalfinal() {

        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(this,"bd_usuarios",null,1);
        SQLiteDatabase db=conn.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(StickerUtilitiesSQLite.CAMPO_ID,"33234");
        values.put(StickerUtilitiesSQLite.CAMPO_NOMBRE,"Texto");
        values.put(StickerUtilitiesSQLite.CAMPO_IMAGE, R.drawable.arenita1finalfinal);
        Long idResultante=db.insert(StickerUtilitiesSQLite.TABLA_USUARIO,StickerUtilitiesSQLite.CAMPO_ID,values);
        Toast.makeText(getApplicationContext(),"Id Registro: "+idResultante + "Texto" + marco,Toast.LENGTH_SHORT).show();
        db.close();

        startActivity(new Intent(UnlocketSticer.this, EntryActivity.class));
    }
    private void  saveStickerIntoSQLitearenita2finalfinal() {

        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(this,"bd_usuarios",null,1);
        SQLiteDatabase db=conn.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(StickerUtilitiesSQLite.CAMPO_ID,"33234");
        values.put(StickerUtilitiesSQLite.CAMPO_NOMBRE,"Texto");
        values.put(StickerUtilitiesSQLite.CAMPO_IMAGE, R.drawable.arenita2finalfinal);
        Long idResultante=db.insert(StickerUtilitiesSQLite.TABLA_USUARIO,StickerUtilitiesSQLite.CAMPO_ID,values);
        Toast.makeText(getApplicationContext(),"Id Registro: "+idResultante + "Texto" + marco,Toast.LENGTH_SHORT).show();
        db.close();

        startActivity(new Intent(UnlocketSticer.this, EntryActivity.class));
    }
    private void saveStickerIntoSQLitearenita3finalfinal() {

        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(this,"bd_usuarios",null,1);
        SQLiteDatabase db=conn.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(StickerUtilitiesSQLite.CAMPO_ID,"33234");
        values.put(StickerUtilitiesSQLite.CAMPO_NOMBRE,"Texto");
        values.put(StickerUtilitiesSQLite.CAMPO_IMAGE, R.drawable.arenita3finalfinal);
        Long idResultante=db.insert(StickerUtilitiesSQLite.TABLA_USUARIO,StickerUtilitiesSQLite.CAMPO_ID,values);
        Toast.makeText(getApplicationContext(),"Id Registro: "+idResultante + "Texto" + marco,Toast.LENGTH_SHORT).show();
        db.close();

        startActivity(new Intent(UnlocketSticer.this, EntryActivity.class));
    }
    private void saveStickerIntoSQLitebobteam1finalfinal() {

        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(this,"bd_usuarios",null,1);
        SQLiteDatabase db=conn.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(StickerUtilitiesSQLite.CAMPO_ID,"33234");
        values.put(StickerUtilitiesSQLite.CAMPO_NOMBRE,"Texto");
        values.put(StickerUtilitiesSQLite.CAMPO_IMAGE, R.drawable.bobteam1finalfinal);
        Long idResultante=db.insert(StickerUtilitiesSQLite.TABLA_USUARIO,StickerUtilitiesSQLite.CAMPO_ID,values);
        Toast.makeText(getApplicationContext(),"Id Registro: "+idResultante + "Texto" + marco,Toast.LENGTH_SHORT).show();
        db.close();

        startActivity(new Intent(UnlocketSticer.this, EntryActivity.class));
    }
    private void saveStickerIntoSQLitebobteam2finalfinal() {

        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(this,"bd_usuarios",null,1);
        SQLiteDatabase db=conn.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(StickerUtilitiesSQLite.CAMPO_ID,"33234");
        values.put(StickerUtilitiesSQLite.CAMPO_NOMBRE,"Texto");
        values.put(StickerUtilitiesSQLite.CAMPO_IMAGE, R.drawable.bobteam2finalfinal);
        Long idResultante=db.insert(StickerUtilitiesSQLite.TABLA_USUARIO,StickerUtilitiesSQLite.CAMPO_ID,values);
        Toast.makeText(getApplicationContext(),"Id Registro: "+idResultante + "Texto" + marco,Toast.LENGTH_SHORT).show();
        db.close();

        startActivity(new Intent(UnlocketSticer.this, EntryActivity.class));
    }
    private void saveStickerIntoSQLitecalamardo2finalfinal() {

        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(this,"bd_usuarios",null,1);
        SQLiteDatabase db=conn.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(StickerUtilitiesSQLite.CAMPO_ID,"33234");
        values.put(StickerUtilitiesSQLite.CAMPO_NOMBRE,"Texto");
        values.put(StickerUtilitiesSQLite.CAMPO_IMAGE, R.drawable.calamardo2finalfinal);
        Long idResultante=db.insert(StickerUtilitiesSQLite.TABLA_USUARIO,StickerUtilitiesSQLite.CAMPO_ID,values);
        Toast.makeText(getApplicationContext(),"Id Registro: "+idResultante + "Texto" + marco,Toast.LENGTH_SHORT).show();
        db.close();

        startActivity(new Intent(UnlocketSticer.this, EntryActivity.class));
    }
    private void saveStickerIntoSQLitecalamardo3finalfinal() {

        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(this,"bd_usuarios",null,1);
        SQLiteDatabase db=conn.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(StickerUtilitiesSQLite.CAMPO_ID,"33234");
        values.put(StickerUtilitiesSQLite.CAMPO_NOMBRE,"Texto");
        values.put(StickerUtilitiesSQLite.CAMPO_IMAGE, R.drawable.calamardo3finalfinal);
        Long idResultante=db.insert(StickerUtilitiesSQLite.TABLA_USUARIO,StickerUtilitiesSQLite.CAMPO_ID,values);
        Toast.makeText(getApplicationContext(),"Id Registro: "+idResultante + "Texto" + marco,Toast.LENGTH_SHORT).show();
        db.close();

        startActivity(new Intent(UnlocketSticer.this, EntryActivity.class));
    }
    private void saveStickerIntoSQLitemedusas1finalfinall() {

        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(this,"bd_usuarios",null,1);
        SQLiteDatabase db=conn.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(StickerUtilitiesSQLite.CAMPO_ID,"33234");
        values.put(StickerUtilitiesSQLite.CAMPO_NOMBRE,"Texto");
        values.put(StickerUtilitiesSQLite.CAMPO_IMAGE, R.drawable.medusas1finalfinal);
        Long idResultante=db.insert(StickerUtilitiesSQLite.TABLA_USUARIO,StickerUtilitiesSQLite.CAMPO_ID,values);
        Toast.makeText(getApplicationContext(),"Id Registro: "+idResultante + "Texto" + marco,Toast.LENGTH_SHORT).show();
        db.close();

        startActivity(new Intent(UnlocketSticer.this, EntryActivity.class));
    }
    private void  saveStickerIntoSQLiterobot1finalfinal() {

        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(this,"bd_usuarios",null,1);
        SQLiteDatabase db=conn.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(StickerUtilitiesSQLite.CAMPO_ID,"33234");
        values.put(StickerUtilitiesSQLite.CAMPO_NOMBRE,"Texto");
        values.put(StickerUtilitiesSQLite.CAMPO_IMAGE, R.drawable.robot1finalfinal);
        Long idResultante=db.insert(StickerUtilitiesSQLite.TABLA_USUARIO,StickerUtilitiesSQLite.CAMPO_ID,values);
        Toast.makeText(getApplicationContext(),"Id Registro: "+idResultante + "Texto" + marco,Toast.LENGTH_SHORT).show();
        db.close();

        startActivity(new Intent(UnlocketSticer.this, EntryActivity.class));
    }
    private void saveStickerIntoSQLitegary1finalfinal() {

        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(this,"bd_usuarios",null,1);
        SQLiteDatabase db=conn.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(StickerUtilitiesSQLite.CAMPO_ID,"33234");
        values.put(StickerUtilitiesSQLite.CAMPO_NOMBRE,"Texto");
        values.put(StickerUtilitiesSQLite.CAMPO_IMAGE, R.drawable.gary1finalfinal);
        Long idResultante=db.insert(StickerUtilitiesSQLite.TABLA_USUARIO,StickerUtilitiesSQLite.CAMPO_ID,values);
        Toast.makeText(getApplicationContext(),"Id Registro: "+idResultante + "Texto" + marco,Toast.LENGTH_SHORT).show();
        db.close();

        startActivity(new Intent(UnlocketSticer.this, EntryActivity.class));
    }
    private void  saveStickerIntoSQLiteteamboat1finalfinal() {

        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(this,"bd_usuarios",null,1);
        SQLiteDatabase db=conn.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(StickerUtilitiesSQLite.CAMPO_ID,"33234");
        values.put(StickerUtilitiesSQLite.CAMPO_NOMBRE,"Texto");
        values.put(StickerUtilitiesSQLite.CAMPO_IMAGE, R.drawable.teamboat1finalfinal);
        Long idResultante=db.insert(StickerUtilitiesSQLite.TABLA_USUARIO,StickerUtilitiesSQLite.CAMPO_ID,values);
        Toast.makeText(getApplicationContext(),"Id Registro: "+idResultante + "Texto" + marco,Toast.LENGTH_SHORT).show();
        db.close();

        startActivity(new Intent(UnlocketSticer.this, EntryActivity.class));
    }
    private void saveStickerIntoSQLiteteambot2finalfinal() {

        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(this,"bd_usuarios",null,1);
        SQLiteDatabase db=conn.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(StickerUtilitiesSQLite.CAMPO_ID,"33234");
        values.put(StickerUtilitiesSQLite.CAMPO_NOMBRE,"Texto");
        values.put(StickerUtilitiesSQLite.CAMPO_IMAGE, R.drawable.teambot2finalfinal);
        Long idResultante=db.insert(StickerUtilitiesSQLite.TABLA_USUARIO,StickerUtilitiesSQLite.CAMPO_ID,values);
        Toast.makeText(getApplicationContext(),"Id Registro: "+idResultante + "Texto" + marco,Toast.LENGTH_SHORT).show();
        db.close();

        startActivity(new Intent(UnlocketSticer.this, EntryActivity.class));
    }
}
