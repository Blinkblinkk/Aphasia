package com.example.musicmanpop.aphasiatalkhelper;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;


public class showResult extends ActionBarActivity {

    TextView showSentence;
    String Showsentence;
    DatabaseOperation databaseOperation;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;
    Cursor cur;
    ImageView btnhome,btnspeaker,btncopy,btnsos,showImg;
    ImageButton btnback;
    ImageView speaker, copy;
    byte[] img;
    Bitmap b;
    Button btnfinish;
    int language_trigger,gender_trigger;
    String str;

    final static String SVOX_TTS_ENGINE = "com.svox.classic";
    TextToSpeech tts;
    private ClipboardManager myClipboard;
    private ClipData myClip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String l_trigger= getIntent().getStringExtra("language_trigger");
        language_trigger = Integer.parseInt(l_trigger);

        String g_trigger= getIntent().getStringExtra("gender_trigger");
        gender_trigger = Integer.parseInt(g_trigger);

        if (language_trigger == 0){
        setContentView(R.layout.result_display);}
        if (language_trigger == 1) {
            setContentView(R.layout.result_display_eng);
        }

        checkTTSEngineInstalled(SVOX_TTS_ENGINE);
        myClipboard = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);

        databaseOperation = new DatabaseOperation(this);
        sqLiteDatabase = databaseOperation.getWritableDatabase();


        btnhome= (ImageView) findViewById(R.id.Home_button);
        btnhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAndSpeak("หน้าหลัก","Home");
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("language_trigger", String.valueOf(language_trigger));
                intent.putExtra("gender_trigger", String.valueOf(gender_trigger));
                startActivity(intent);
            }
        });

        btnfinish= (Button) findViewById(R.id.finishButton);
        btnfinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("language_trigger", String.valueOf(language_trigger));
                intent.putExtra("gender_trigger", String.valueOf(gender_trigger));
                startActivity(intent);
            }
        });

        speaker = (ImageView) findViewById(R.id.speakerButton);
        speaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // read sentence again
                if(tts != null)
                    tts.setLanguage(new Locale("th"));
                tts.speak(Showsentence, TextToSpeech.QUEUE_FLUSH,null);


            }
        });



        showImg = (ImageView) findViewById(R.id.imageDisplay);
        showSentence = (TextView)findViewById(R.id.textResult);

        cursor = sqLiteDatabase.rawQuery("SELECT " + TableData.TableInfo.TEXT_PRESS + " FROM "
                + TableData.TableInfo.TABLE_NAME1 + " ORDER BY "
                + TableData.TableInfo.KEY_ID_SHOW_SENTENCE,null);

        cur = sqLiteDatabase.rawQuery("SELECT " + TableData.TableInfo.IMG_PRESS + " FROM "
                + TableData.TableInfo.TABLE_NAME1 + " ORDER BY "
                + TableData.TableInfo.KEY_ID_SHOW_SENTENCE + " DESC LIMIT 1;",null);

        ArrayList<String> arrayList = new ArrayList<String>();
        cursor.moveToFirst();

       /* ArrayList<String> arr = new ArrayList<>();
        cur.moveToFirst();*/

        if (cur.moveToNext()){
            img = cur.getBlob(cur.getColumnIndex("img_press"));
            b = BitmapFactory.decodeByteArray(img, 0, img.length);
            showImg.setImageBitmap(b);
            Log.d("Database operation", "Retrieve image successful");
        }


        while (!cursor.isAfterLast()){
            arrayList.add(cursor.getString(cursor.getColumnIndex(TableData.TableInfo.TEXT_PRESS)));

            cursor.moveToNext();

            cursor.getPosition();

        }

        if( language_trigger == 1){
        Showsentence = " ";
        for(int i = 0; i < arrayList.size(); i++){
            Showsentence = Showsentence+" "+arrayList.get(i);
            Log.d("Database Operrtion",Showsentence);
        }}

        if( language_trigger == 0){
            Showsentence = " ";
            for(int i = 0; i < arrayList.size(); i++){
                Showsentence = Showsentence+arrayList.get(i);
                Log.d("Database Operrtion",Showsentence);
            }}

        Calendar c = Calendar.getInstance();
        System.out.println("Current time =&gt; " + c.getTime());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(c.getTime());

        Log.d("Database Operation", "Add sentence and dateTime Success");

        showSentence.setText(Showsentence);
        if(img != null && !Showsentence.equals(" ")) {
            databaseOperation.putHistory(databaseOperation, img, Showsentence, formattedDate);
        }
        //databaseOperation.onUpgrade(sqLiteDatabase,1,1);
    }

    public void checkTTSEngineInstalled(String aphasiaTalk) {
        boolean isSvoxInstalled = isAppInstalled(aphasiaTalk);
        if(isSvoxInstalled) {
            if(tts == null)
                tts = new TextToSpeech(showResult.this, null, aphasiaTalk);
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

    public void checkAndSpeak(String sTH, String sEN)
    {
        if(language_trigger==0)
        { str = sTH;
            tts.setLanguage(new Locale("th")); }
        if(language_trigger ==1)
        { str = sEN;}
        if(tts!=null)
            tts.speak(str, TextToSpeech.QUEUE_FLUSH,null);
    }
    @Override
    public void onBackPressed() {
    }

}
