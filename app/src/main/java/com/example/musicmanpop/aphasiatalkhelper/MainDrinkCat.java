package com.example.musicmanpop.aphasiatalkhelper;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.net.Uri;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Locale;


public class MainDrinkCat extends ActionBarActivity {


    ImageButton hot;
    ImageButton cold;
    ImageButton frappe;
    ImageButton home;
    ImageButton back;
    int language_trigger;
    int gender_trigger;
    AudioManager manager;
    ImageView sosBtn;

    final static String SVOX_TTS_ENGINE = "com.svox.classic";
    TextToSpeech tts;
    String str;

    DatabaseOperation DB;
    SQLiteDatabase SQ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        String l_trigger= getIntent().getStringExtra("language_trigger");
        language_trigger = Integer.parseInt(l_trigger);

        checkTTSEngineInstalled(SVOX_TTS_ENGINE);

        String g_trigger= getIntent().getStringExtra("gender_trigger");
        gender_trigger = Integer.parseInt(g_trigger);

        if (language_trigger == 0){
            setContentView(R.layout.drink_cat);}
        if (language_trigger == 1){
            setContentView(R.layout.drink_cat_eng);}

        DB = new DatabaseOperation(this);
        SQ = DB.getWritableDatabase();

        getIntent();

        home = (ImageButton) findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToHome();
            }
        });

        back = (ImageButton) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToPrevious();
            }
        });

        hot = (ImageButton) findViewById(R.id.hot_cat);
        hot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAndSpeak("เครื่องดื่มร้อน","hot drink");
                goToHotMale();
            }
        });

        cold = (ImageButton) findViewById(R.id.cold_cat);
        cold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAndSpeak("เครื่องดื่มเย็น","cold drink");
                goToColdMale();
            }
        });

        frappe = (ImageButton) findViewById(R.id.frappe_cat);
        frappe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAndSpeak("เครื่องดื่มปั่น","frappe");
                goToFrappeMale();
            }
        });

        sosBtn = (ImageView) findViewById(R.id.sos);
        sosBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SosNotification.class);
                intent.putExtra("language_trigger", String.valueOf(language_trigger));
                intent.putExtra("gender_trigger", String.valueOf(gender_trigger));
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_drink_cat, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void goToHotMale()
    {
        Intent intent = new Intent(getApplicationContext(), MainDrinkHot.class);
        intent.putExtra("language_trigger", String.valueOf(language_trigger));
        intent.putExtra("gender_trigger", String.valueOf(gender_trigger));
        startActivity(intent);
    }

    public void goToColdMale()
    {
        Intent intent = new Intent(getApplicationContext(), MainDrinkCold.class);
        intent.putExtra("language_trigger", String.valueOf(language_trigger));
        intent.putExtra("gender_trigger", String.valueOf(gender_trigger));
        startActivity(intent);
    }

    public void goToFrappeMale()
    {
        Intent intent = new Intent(getApplicationContext(), MainDrinkFrappe.class);
        intent.putExtra("language_trigger", String.valueOf(language_trigger));
        intent.putExtra("gender_trigger", String.valueOf(gender_trigger));
        startActivity(intent);
    }

    public void goToHome()
    {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("language_trigger", String.valueOf(language_trigger));
        intent.putExtra("gender_trigger", String.valueOf(gender_trigger));
        startActivity(intent);
    }

    public void goToPrevious()
    {
        DB.deleteLastRow();
        Intent intent = new Intent(getApplicationContext(), MainIWantMale.class);
        intent.putExtra("language_trigger", String.valueOf(language_trigger));
        intent.putExtra("gender_trigger", String.valueOf(gender_trigger));
        startActivity(intent);
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
    ////////////////////////////////// Text To Speech SVOX ///////////////////////////////////

    public void checkTTSEngineInstalled(String aphasiaTalk) {
        boolean isSvoxInstalled = isAppInstalled(aphasiaTalk);
        if(isSvoxInstalled) {
            if(tts == null)
                tts = new TextToSpeech(MainDrinkCat.this, null, aphasiaTalk);
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
