package com.example.musicmanpop.aphasiatalkhelper;


import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.provider.MediaStore;
import android.graphics.BitmapFactory;
import android.widget.TextView;
import android.widget.Toast;
import android.net.Uri;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileNotFoundException;
import java.util.Locale;


public class addItem extends ActionBarActivity {

    public static final int CAMERA_REQUEST = 10;
    public static final int IMAGE_GALLERY_REQUEST = 30;
    private static final String TAG = "database operation" ;

    ImageView SOSBtn;
    ImageButton selectPhoto,home;
    ImageView photo;
    int language_trigger,gender_trigger;
    TextView cancel;
    EditText th_name;
    EditText eng_name;
    TextView save;
    DatabaseOperation DB;
    SQLiteDatabase SQ;
    String thaitxt,engtxt;
    //Bitmap cameraImage = null;



    final static String SVOX_TTS_ENGINE = "com.svox.classic";
    TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String l_trigger= getIntent().getStringExtra("language_trigger");
        language_trigger = Integer.parseInt(l_trigger);

        String g_trigger= getIntent().getStringExtra("gender_trigger");
        gender_trigger = Integer.parseInt(g_trigger);

        if (language_trigger==0){

        setContentView(R.layout.activity_add_new_item);}

        if (language_trigger==1){

            setContentView(R.layout.activity_add_new_item_english);}

        getIntent();

        DB = new DatabaseOperation(this);
        SQ = DB.getWritableDatabase();

        checkTTSEngineInstalled(SVOX_TTS_ENGINE);

        SOSBtn = (ImageView) findViewById(R.id.imageView2);
        selectPhoto = (ImageButton) findViewById(R.id.add_photo);
        photo = (ImageView) findViewById(R.id.selectedPhoto);
        photo.setVisibility(View.INVISIBLE);
        th_name = (EditText) findViewById(R.id.editTextThai);
        eng_name = (EditText) findViewById(R.id.engname);

        BitmapDrawable drawable = (BitmapDrawable) selectPhoto.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        home= (ImageButton) findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("language_trigger", String.valueOf(language_trigger));
                intent.putExtra("gender_trigger", String.valueOf(gender_trigger));
                startActivity(intent);
            }
        });

        cancel= (TextView) findViewById(R.id.textViewCancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });

        save= (TextView) findViewById(R.id.textViewSave);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get str
                thaitxt = th_name.getText().toString();
                engtxt = eng_name.getText().toString();
                keepItem();

            }
        });


        SOSBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //go to SOS screen
                goToSOSScreen();
            }
        });

        selectPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //show pop up of picture selecting
                //select from camera or gallery
                if(tts != null)
                    tts.setLanguage(new Locale("th"));
                tts.speak("เลือกรูป", TextToSpeech.QUEUE_FLUSH,null);
                selectingPopUp();
            }
        });

        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectingPopUp();
            }
        });
    }

    //go to SOS
    public void goToSOSScreen()
    {
        Intent intent = new Intent(getApplicationContext(), SosNotification.class);
        intent.putExtra("language_trigger", String.valueOf(language_trigger));
        intent.putExtra("gender_trigger", String.valueOf(gender_trigger));
        startActivity(intent);
    }

    // @Override
    public void selectingPopUp()
    {
        CharSequence[] items = { "กล้องถ่ายรูป", "คลังภาพ"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //taking photo by camera
                if(which == 0)
                {
                    if(tts != null)
                        tts.setLanguage(new Locale("th"));
                    tts.speak("กล้องถ่ายรูป", TextToSpeech.QUEUE_FLUSH,null);
                    takePhotoClick();
                }

                //select photo from gallery
                if(which == 1)
                {
                    if(tts != null)
                        tts.setLanguage(new Locale("th"));
                    tts.speak("คลังภาพ", TextToSpeech.QUEUE_FLUSH,null);
                    galleryClicked();
                }

            }
        });

        builder.show();
    }
    Uri cameraImagePath;
    //select photo by camera
    public void takePhotoClick()
    {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra("language_trigger", String.valueOf(language_trigger));
        cameraIntent.putExtra("gender_trigger", String.valueOf(gender_trigger));
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
        ;
    }


    //select photo by gallery
    public void galleryClicked()
    {
        //invoke the image gallery
       /* Intent pickPhotoIntent = new Intent(Intent.ACTION_PICK);
        pickPhotoIntent.setType("image/*");
        pickPhotoIntent.setAction(Intent.ACTION_GET_CONTENT);
        pickPhotoIntent.putExtra("language_trigger", String.valueOf(language_trigger));
        pickPhotoIntent.putExtra("gender_trigger", String.valueOf(gender_trigger));

        //invoke this activivity,and get something back on it
        // startActivityForResult(pickPhotoIntent, IMAGE_GALLERY_REQUEST);
        startActivityForResult(Intent.createChooser(pickPhotoIntent, "Select Picture"), IMAGE_GALLERY_REQUEST);*/

        //invoke the image gallery
        Intent pickPhotoIntent = new Intent(Intent.ACTION_PICK);
        //find the data
        File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String pictureDirectPath = pictureDirectory.getPath();
        //get uri representation
        Uri data = Uri.parse(pictureDirectPath);
        //set the data and type. get all image type
        pickPhotoIntent.setDataAndType(data,"image/*");

        pickPhotoIntent.setAction(Intent.ACTION_GET_CONTENT);
        pickPhotoIntent.putExtra("language_trigger", String.valueOf(language_trigger));
        pickPhotoIntent.putExtra("gender_trigger", String.valueOf(gender_trigger));

        //invoke this activivity,and get something back on it
        startActivityForResult(Intent.createChooser(pickPhotoIntent, "Select Picture"), IMAGE_GALLERY_REQUEST);
    }

    Bitmap cameraImage = null;


   @Override
   protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK)
        {

            if(requestCode == CAMERA_REQUEST)
            {
                cameraImage = (Bitmap) data.getExtras().get("data");
                //show image to users
                photo.setImageBitmap(cameraImage);
                selectPhoto.setVisibility(View.INVISIBLE);
                photo.setVisibility(View.VISIBLE);
                //bit to byte
                //getBytesImage(cameraImage);
            }

            else if(requestCode == IMAGE_GALLERY_REQUEST && null != data)
            {
                //the address of the image
                Uri imageUri = data.getData();

                //read the image data from SD card
                InputStream inputStream;

                try
                {
                    inputStream = getContentResolver().openInputStream(imageUri);
                    //get bitmap
                    cameraImage = BitmapFactory.decodeStream(inputStream);
                    //show image to the user
                    photo.setImageBitmap(cameraImage);
                    selectPhoto.setVisibility(View.INVISIBLE);
                    photo.setVisibility(View.VISIBLE);
                }
                catch (FileNotFoundException e)
                {
                    e.printStackTrace();
                    Toast.makeText(this, "Unable to open image", Toast.LENGTH_LONG).show();
                }

            } // end try of gallery
        } //end camera
    }

    ////////////////////////////////// Text To Speech SVOX ///////////////////////////////////
    public void checkTTSEngineInstalled(String aphasiaTalk) {
        boolean isSvoxInstalled = isAppInstalled(aphasiaTalk);
        if(isSvoxInstalled) {
            if(tts == null)
                tts = new TextToSpeech(addItem.this, null, aphasiaTalk);
        } else if(!isSvoxInstalled){
            Toast.makeText(getApplicationContext()
                    , "Please install SVOX Text-to-Speech Engine", Toast.LENGTH_LONG).show();
            Intent svoxIntent = new Intent(Intent.ACTION_VIEW);
            svoxIntent.setData(Uri.parse("market://details?id=" + aphasiaTalk));
            startActivity(svoxIntent);
        }
    }

    public boolean isAppInstalled(String aphasiaTalk) {
        PackageManager pm = getPackageManager();
        boolean app_installed = false;
        try {
            pm.getPackageInfo(aphasiaTalk, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) { }
        return app_installed ;
    }

    public void goToPrevious()
    {
        Intent intent = new Intent(getApplicationContext(), MainEatCat.class);
        intent.putExtra("language_trigger", String.valueOf(language_trigger));
        intent.putExtra("gender_trigger", String.valueOf(gender_trigger));
        startActivity(intent);
    }

    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }

    // convert from bitmap to byte array for keeping in the database
    public static byte[] getBytesImage(Bitmap bitmap)
    {
        if(bitmap!=null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 0, stream);
            return stream.toByteArray();
        }
        else return null;
    }

    public void keepItem()
    {
        Intent goKeepItem = new Intent(getApplicationContext(),box.class);
        goKeepItem.putExtra("language_trigger", String.valueOf(language_trigger));
        goKeepItem.putExtra("gender_trigger", String.valueOf(gender_trigger));
        //DatabaseOperation db = new DatabaseOperation(getApplicationContext());

        th_name = (EditText) findViewById(R.id.editTextThai);
        eng_name = (EditText) findViewById(R.id.engname);

        //goKeepItem.putExtra("Username",username);
        //put user variable
        if (getBytesImage(cameraImage)!=null)
        {
            DB.putItem(DB,getBytesImage(cameraImage),thaitxt,engtxt);
            Log.d("Database keepItem","img showww");
        }
        else
        {
            DB.putItem(DB,null,thaitxt,engtxt);
            Log.d("Database keepItem","img nullllll");
        }

        //Cursor cs = db.getItem(db);
        //change text only
        /*if(cs.isFirst()){
            msgLine.setText(cs.getColumnName(cs.getColumnIndex("title")));
        }
        *///change act
        /*goKeepItem.putExtra(MainActivity.EXTRA_MESSAGE1,title.getText().toString());
        goKeepItem.putExtra(MainActivity.EXTRA_MESSAGE2,date.getText().toString());
        goKeepItem.putExtra(MainActivity,msgLine.getText().toString());*/

        startActivity(goKeepItem);

        //goKeepDiary.putExtra()
    }






}
