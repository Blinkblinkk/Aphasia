package com.example.musicmanpop.aphasiatalkhelper;


import android.app.Dialog;
import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RecentUse extends ListActivity {

    ListView datalist;
    ArrayList<History> imageArr = new ArrayList<>();
    HistoryAdapter historyAdapter;
    List<History> histories;

    DatabaseOperation databaseOperation;
    SQLiteDatabase sqLiteDatabase;

    ImageButton btnhome,sosBtn;
    String str;

    final static String SVOX_TTS_ENGINE = "com.svox.classic";
    TextToSpeech tts;
    int language_trigger,gender_trigger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_use);

        String l_trigger= getIntent().getStringExtra("language_trigger");
        language_trigger = Integer.parseInt(l_trigger);

        String g_trigger= getIntent().getStringExtra("gender_trigger");
        gender_trigger = Integer.parseInt(g_trigger);
        getIntent();

        databaseOperation = new DatabaseOperation(this);

        histories = databaseOperation.getAllHistory();
        for(History hn:histories){
            String log = "ID:" + hn.getId() + " Image: " + hn.getImage() + " Sentence: " + hn.getSentence();

            //writing his to log
            Log.d("Result: ", log);
            //add his to arraylist
            imageArr.add(hn);
        }
        Log.d("Tset00", "size" + imageArr.size());
        if(imageArr.size() != 0) {
            historyAdapter = new HistoryAdapter(this, R.layout.row_layout, imageArr);
            datalist = (ListView) findViewById(android.R.id.list);
            if(historyAdapter!=null && datalist!=null) {
                try {
                    datalist.setAdapter(historyAdapter);
                } catch (Exception e) {
                    Log.d("Tset00", "Error" + e);
                }
            }else{
                Log.d("Tset00", "historyAdapter : NULL");
            }
        }else{
            Log.d("Tset00","Buggggg");
        }

        btnhome= (ImageButton) findViewById(R.id.home);
        btnhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("language_trigger", String.valueOf(language_trigger));
                intent.putExtra("gender_trigger", String.valueOf(gender_trigger));
                startActivity(intent);
            }
        });

        //turn on SOS notification
        sosBtn= (ImageButton) findViewById(R.id.imageView2);
        sosBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SosNotification.class);
                intent.putExtra("language_trigger", String.valueOf(language_trigger));
                intent.putExtra("gender_trigger", String.valueOf(gender_trigger));
                startActivity(intent);
            }
        });

        //listView1 = (ListView)findViewById(R.id.listSentence);

        /*while (cursor.moveToNext()){
            //id = cursor.getString(cursor.getColumnIndex(TableData.TableInfo.KEY_ID_HISTORY));
            sentence = cursor.getString(cursor.getColumnIndex(TableData.TableInfo.SENTENCE));

            History history = new History(id,sentence);

        }*/


    }

    ////////////////////////////////// Text To Speech SVOX ///////////////////////////////////

    public void checkTTSEngineInstalled(String aphasiaTalk) {
        boolean isSvoxInstalled = isAppInstalled(aphasiaTalk);
        if(isSvoxInstalled) {
            if(tts == null)
                tts = new TextToSpeech(RecentUse.this, null, aphasiaTalk);
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
