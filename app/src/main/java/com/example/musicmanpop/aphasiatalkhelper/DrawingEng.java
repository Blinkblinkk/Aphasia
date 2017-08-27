package com.example.musicmanpop.aphasiatalkhelper;

import android.app.Dialog;
import android.app.AlertDialog;
import android.media.AudioManager;
import android.view.Window;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.speech.tts.TextToSpeech;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Locale;
import java.util.UUID;


public class DrawingEng extends ActionBarActivity {

    private DrawingView drawView;
    private ImageButton currPaint;
    private float smallBrush, mediumBrush, largeBrush;
    private boolean click;
    private SharedPreferences pref;
    private Editor editor;
    Context context;
    Boolean checkSave = false;
    int eraserColor;
    AudioManager manager;

    final static String SVOX_TTS_ENGINE = "com.svox.classic";
    TextToSpeech tts;

    ImageButton home;
    ImageButton sos;
    int language_trigger;
    int gender_trigger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawing_eng);

        String l_trigger= getIntent().getStringExtra("language_trigger");
        language_trigger = Integer.parseInt(l_trigger);

        String g_trigger= getIntent().getStringExtra("gender_trigger");
        gender_trigger = Integer.parseInt(g_trigger);

        checkTTSEngineInstalled(SVOX_TTS_ENGINE);

        //this.getActionBar().setDisplayHomeAsUpEnabled(true);
        //this.getActionBar().setHomeButtonEnabled(true);

        click = false; //use to toggle pen list
        pref = getApplicationContext().getSharedPreferences("AphasiaHPref", MODE_PRIVATE);
        editor = pref.edit();

        smallBrush = getResources().getInteger(R.integer.small_size);
        mediumBrush = getResources().getInteger(R.integer.medium_size);
        largeBrush = getResources().getInteger(R.integer.large_size);

        //get View from layout
        drawView = (DrawingView)findViewById(R.id.drawer);
        drawView.setBrushSize(mediumBrush); //initial pen size
        LinearLayout paintColorLayout = (LinearLayout)findViewById(R.id.paint_colors);

        //set the first button of palette to be pressed
        currPaint = (ImageButton) paintColorLayout.getChildAt(0);
        currPaint.setImageDrawable(getResources().getDrawable(R.drawable.painted_press));

        home = (ImageButton) findViewById(R.id.home);
        sos = (ImageButton) findViewById(R.id.imageView2);

        //click home button
        home.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                 if(tts != null)
                 tts.setLanguage(new Locale("th"));
                 tts.speak("home", TextToSpeech.QUEUE_FLUSH, null);
                if(checkSave) {
                    //change screen
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("language_trigger", String.valueOf(language_trigger));
                    intent.putExtra("gender_trigger", String.valueOf(gender_trigger));
                    startActivity(intent);
                }
                else
                {
                    checkSaveImage();
                }
            }
        });

        //click sos to turn on emergency notification
        sos.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //change screen
                Intent intent = new Intent(getApplicationContext(), SosNotification.class);
                intent.putExtra("language_trigger", String.valueOf(language_trigger));
                intent.putExtra("gender_trigger", String.valueOf(gender_trigger));
                startActivity(intent);
            }
        });

    }

    //click on color palette
    public void paintClicked(View view){
        if(view!=currPaint){
            ImageButton imgView = (ImageButton) view;
            String color = view.getTag().toString();
            drawView.setColor(color);
            imgView.setImageDrawable(getResources().getDrawable(R.drawable.painted_press));
            //unpressed the previous color button
            currPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint));
            currPaint= imgView;
        }
        drawView.setEraseMode(false);
        drawView.setTextMode(false);
        drawView.setBrushSize(drawView.getLastBrushSize());
    }//end paint clicked

    //new page
    public void newPage(View v){
        AlertDialog.Builder newDialog = new AlertDialog.Builder(this);
        newDialog.setTitle("Do you want a new page?"); //R.string.new_page_title
        //newDialog.setMessage("Do you want a new page?"); //R.string.new_page_msg
        if(tts != null)
        tts.speak("Do you want a new page?", TextToSpeech.QUEUE_FLUSH,null);

        //R.string.yes
        newDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                drawView.startNew();
                dialog.dismiss();
            }
        });
        //R.string.cancle
        newDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        newDialog.show();
    } // end new page

    //type text
    public void typeText(View v){
        if(tts != null)
        tts.speak("Type text", TextToSpeech.QUEUE_FLUSH, null);
        drawView.setTextMode(true);

    } //end type text

    //choose pen
    public void choosePen(View v){
        final RelativeLayout pen_layout = (RelativeLayout) findViewById(R.id.pen_selection);
        if(tts != null)
        tts.speak("pen", TextToSpeech.QUEUE_FLUSH, null);
        if(click){
            pen_layout.removeAllViews();// pen brush layout
            RelativeLayout p_layout = (RelativeLayout) findViewById(R.id.eraser_selection);// eraser brush layout
            p_layout.removeAllViews();
            click = false;
        }
        else{
            LayoutInflater pen_list = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            pen_layout.addView(pen_list.inflate(R.layout.pen_list, null));
            click = true;

            ImageButton smallBtn = (ImageButton) findViewById(R.id.small_brush);
            smallBtn.setOnClickListener(new OnClickListener(){
                @Override
                public void onClick(View v) {
                    drawView.setEraseMode(false);
                    drawView.setTextMode(false);
                    drawView.setBrushSize(smallBrush);
                    drawView.setLastBrushSize(smallBrush);
                    pen_layout.removeAllViews();
                    click = false;
                }
            });

            ImageButton mediumBtn = (ImageButton) findViewById(R.id.medium_brush);
            mediumBtn.setOnClickListener(new OnClickListener(){
                @Override
                public void onClick(View v) {
                    drawView.setEraseMode(false);
                    drawView.setTextMode(false);
                    drawView.setBrushSize(mediumBrush);
                    drawView.setLastBrushSize(mediumBrush);
                    pen_layout.removeAllViews();
                    click = false;
                }
            });

            ImageButton largeBtn = (ImageButton) findViewById(R.id.large_brush);
            largeBtn.setOnClickListener(new OnClickListener(){
                @Override
                public void onClick(View v) {
                    drawView.setEraseMode(false);
                    drawView.setTextMode(false);
                    drawView.setBrushSize(largeBrush);
                    drawView.setLastBrushSize(largeBrush);
                    pen_layout.removeAllViews();
                    click = false;
                }
            });
        }
    }//end choose pen

    //choose eraser
    public void chooseEraser(View v){
        final RelativeLayout pen_layout = (RelativeLayout) findViewById(R.id.eraser_selection);

        if(tts != null)
        tts.speak("eraser", TextToSpeech.QUEUE_FLUSH, null);
        if(click){
            pen_layout.removeAllViews();//eraser brush layout
            RelativeLayout p_layout = (RelativeLayout) findViewById(R.id.pen_selection);// pen brush layout
            p_layout.removeAllViews();
            click = false;
        }
        else{
            LayoutInflater pen_list = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            pen_layout.addView(pen_list.inflate(R.layout.pen_list, null));
            click = true;

            ImageButton smallBtn = (ImageButton) findViewById(R.id.small_brush);
            smallBtn.setOnClickListener(new OnClickListener(){
                @Override
                public void onClick(View v) {
                    drawView.setEraseMode(true);
                    drawView.setTextMode(false);
                    drawView.setBrushSize(smallBrush);
                    pen_layout.removeAllViews();
                    click = false;
                }
            });

            ImageButton mediumBtn = (ImageButton) findViewById(R.id.medium_brush);
            mediumBtn.setOnClickListener(new OnClickListener(){
                @Override
                public void onClick(View v) {
                    drawView.setEraseMode(true);
                    drawView.setTextMode(false);
                    drawView.setBrushSize(mediumBrush);
                    pen_layout.removeAllViews();
                    click = false;
                }
            });

            ImageButton largeBtn = (ImageButton) findViewById(R.id.large_brush);
            largeBtn.setOnClickListener(new OnClickListener(){
                @Override
                public void onClick(View v) {
                    drawView.setEraseMode(true);
                    drawView.setTextMode(false);
                    drawView.setBrushSize(largeBrush);
                    pen_layout.removeAllViews();
                    click = false;
                }
            });

        }
    }//end choose eraser

    //save image
    public void saveImage(View v){
        AlertDialog.Builder saveDialog = new AlertDialog.Builder(this);
        saveDialog.setTitle("Do you want to save?");
        //saveDialog.setMessage("Save drawing to AphasiaTalk Folder?");
        if(tts != null)
          tts.setLanguage(new Locale("th"));
        tts.speak("Do you want to save?", TextToSpeech.QUEUE_FLUSH, null);

        saveDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which){
                if(saveImage()){ //success

                    Dialog builder = new Dialog(DrawingEng.this);
                    builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    builder.setContentView(R.layout.dialog_saved_eng);
                    builder.setCancelable(true);
                    builder.show();

                    if(tts != null)
                      tts.setLanguage(new Locale("th"));
                    //  tts.speak("บันทึกเรียบร้อยแล้ว", TextToSpeech.QUEUE_FLUSH,null);

                    Toast savedToast = Toast.makeText(getApplicationContext(),
                            "Saved!", Toast.LENGTH_SHORT);
                    savedToast.show();
                    editor.clear();
                    editor.commit();
                }
                else{
                    //unsuccess
                    Toast unsavedToast = Toast.makeText(getApplicationContext(),
                            "Failed!", Toast.LENGTH_SHORT);
                    unsavedToast.show();
                }
            }
        });

        saveDialog.setNegativeButton("No", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which){
                dialog.cancel();
            }
        });
        saveDialog.show();
    }//end save image

    public void onResume(){
        super.onResume();
        if(pref.contains("path")){
            File file = new File(pref.getString("path", ""));
            file.delete();
            editor.clear();
            editor.commit();
        }
    }

    public boolean saveImage(){
        drawView.setDrawingCacheEnabled(true);
        //save
        File filepath = Environment.getExternalStorageDirectory();
        File directory = new File(filepath.getAbsolutePath()
                + "/AphasiaTalk Helper/");
        directory.mkdirs();
        //File directory = new File(Environment.getExternalStorageDirectory()+"/AphasiaTalk Helper");
        // if(!directory.exists()) directory.mkdir(); // create AphasiaTalk on device storage
        File mypath = new File(directory,UUID.randomUUID().toString()+".png");
        //FileOutputStream fos = null;
        try {

            FileOutputStream  fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            drawView.getDrawingCache().compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();

            editor.putString("path", mypath.getAbsolutePath()); //save path for image to be deleted
            editor.commit();

            fos = null;
            mypath = null;
            //success
            drawView.destroyDrawingCache();
            checkSave = true;
            return true;
        }
        catch (Exception e) {
            //dummy
        }
        drawView.destroyDrawingCache();
        return false; //unsuccess
    }

    public void checkSaveImage()
    {
        //ask
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("Do you want to save?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        saveImage();

                        if(saveImage()){ //success
                            Toast savedToast = Toast.makeText(getApplicationContext(),
                                    "Saved!", Toast.LENGTH_SHORT);
                            savedToast.show();
                            editor.clear();
                            editor.commit();
                        }
                        else{
                            //unsuccess
                            Toast unsavedToast = Toast.makeText(getApplicationContext(),
                                    "Failed!", Toast.LENGTH_SHORT);
                            unsavedToast.show();
                        }
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //go home
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("language_trigger", String.valueOf(language_trigger));
                        intent.putExtra("gender_trigger", String.valueOf(gender_trigger));
                        startActivity(intent);
                    }
                })
                .create();
        alertDialog.show();

        checkSave = true;
    }

    public void displayConfirmSave()
    {
        Dialog builder = new Dialog(context);
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.setCancelable(true);
        builder.show();
        // make it disappear automatically
    }


    ////////////////////////////////// Text To Speech SVOX ///////////////////////////////////
    public void checkTTSEngineInstalled(String aphasiaTalk) {
        boolean isSvoxInstalled = isAppInstalled(aphasiaTalk);
        if(isSvoxInstalled) {
            if(tts == null)
                tts = new TextToSpeech(DrawingEng.this, null, aphasiaTalk);
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

    @Override
    public void onBackPressed() {
    }

}
