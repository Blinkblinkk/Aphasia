package com.example.musicmanpop.aphasiatalkhelper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.musicmanpop.aphasiatalkhelper.R;

import java.io.ByteArrayOutputStream;
import java.util.Locale;

public class MainPartMale extends Activity {
    ImageButton home;
    ImageButton back;
    int language_trigger;
    int gender_trigger;
    AudioManager manager;
    ImageView sosBtn;
    DatabaseOperation DB;
    SQLiteDatabase SQ;

    final static String SVOX_TTS_ENGINE = "com.svox.classic";
    TextToSpeech tts;
    String str;

    public class MyAdapter extends BaseAdapter {

        private Context context;
        private LayoutInflater layoutInflater;

        MyAdapter(Context c){
            context = c;
            layoutInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return mThumbIds.length;
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub

            View grid;
            if(convertView==null){
                grid = new View(context);
                grid = layoutInflater.inflate(R.layout.gridlayout, null);
            }else{
                grid = (View)convertView;
            }

            if (language_trigger == 0 &&  gender_trigger == 0) {
                ImageView imageView = (ImageView) grid.findViewById(R.id.image);
                imageView.setImageResource(mThumbIds[position]);
                TextView textView = (TextView) grid.findViewById(R.id.text);
                textView.setText(tThumbIds[position]);

            }

            if (language_trigger == 1 && gender_trigger == 0) {
                ImageView imageView = (ImageView) grid.findViewById(R.id.image);
                imageView.setImageResource(mThumbIds[position]);
                TextView textView = (TextView) grid.findViewById(R.id.text);
                textView.setText(teThumbIds[position]);

            }

            if (language_trigger == 0 && gender_trigger == 1) {
                ImageView imageView = (ImageView) grid.findViewById(R.id.image);
                imageView.setImageResource(fmThumbIds[position]);
                TextView textView = (TextView) grid.findViewById(R.id.text);
                textView.setText(tThumbIds[position]);


            }

            if (language_trigger == 1 && gender_trigger == 1) {
                ImageView imageView = (ImageView) grid.findViewById(R.id.image);
                imageView.setImageResource(fmThumbIds[position]);
                TextView textView = (TextView) grid.findViewById(R.id.text);
                textView.setText(teThumbIds[position]);


            }
            return grid;
        }

    }

    GridView gridView;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.part_male);

        DB = new DatabaseOperation(this);
        SQ = DB.getWritableDatabase();

        checkTTSEngineInstalled(SVOX_TTS_ENGINE);

        String l_trigger= getIntent().getStringExtra("language_trigger");
        language_trigger = Integer.parseInt(l_trigger);

        String g_trigger= getIntent().getStringExtra("gender_trigger");
        gender_trigger = Integer.parseInt(g_trigger);

        gridView = (GridView)findViewById(R.id.grid);

        MyAdapter adapter = new MyAdapter(this);
        gridView.setAdapter(adapter);
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
 /* Integer num = (Integer) gridView.getTag() ;
                gridView.setTag(Integer.valueOf(num));
                if(position == num)
                    blah blah blahhh */

         gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                checkAndSpeak(tThumbIds[position], teThumbIds[position]);

                if (language_trigger == 0 && gender_trigger ==0 ) {

                    Bitmap b = BitmapFactory.decodeResource(getResources(), mThumbIds[position]);
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    b.compress(Bitmap.CompressFormat.PNG, 100, bos);
                    byte[] img = bos.toByteArray();
                    DB.putSentence(DB, img, tThumbIds[position]);
                    Log.d("Database Operation", "Add hungry button Success");
                }

                if (language_trigger == 1 && gender_trigger ==0 ) {

                    Bitmap b = BitmapFactory.decodeResource(getResources(), mThumbIds[position]);
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    b.compress(Bitmap.CompressFormat.PNG, 100, bos);
                    byte[] img = bos.toByteArray();
                    DB.putSentence(DB, img, teThumbIds[position]);
                    Log.d("Database Operation", "Add hungry button Success");
                }

                if (language_trigger == 0 && gender_trigger ==1 ) {

                    Bitmap b = BitmapFactory.decodeResource(getResources(), fmThumbIds[position]);
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    b.compress(Bitmap.CompressFormat.PNG, 100, bos);
                    byte[] img = bos.toByteArray();
                    DB.putSentence(DB, img, tThumbIds[position]);
                    Log.d("Database Operation", "Add hungry button Success");
                }
                if (language_trigger == 1 && gender_trigger ==1 ) {

                    Bitmap b = BitmapFactory.decodeResource(getResources(), fmThumbIds[position]);
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    b.compress(Bitmap.CompressFormat.PNG, 100, bos);
                    byte[] img = bos.toByteArray();
                    DB.putSentence(DB, img, teThumbIds[position]);
                    Log.d("Database Operation", "Add hungry button Success");
                }

                Intent intent2 = new Intent(getApplicationContext(), showResult.class);
                intent2.putExtra("language_trigger", String.valueOf(language_trigger));
                intent2.putExtra("gender_trigger", String.valueOf(gender_trigger));
                startActivity(intent2);

            }});

    }

    /** Array of pic */

    /**Male*/
    public Integer[] mThumbIds = {
            R.drawable.head_male, R.drawable.eye,R.drawable.ear, R.drawable.nose,
            R.drawable.teeth, R.drawable.neck,R.drawable.chest, R.drawable.shoulder,
            R.drawable.lhung, R.drawable.arm,R.drawable.elbow, R.drawable.hand,
            R.drawable.stomach_male, R.drawable.waist_male,R.drawable.sapoke_male, R.drawable.bottom_male,
            R.drawable.leg_male, R.drawable.knee, R.drawable.feet
    };

    /** Female*/
    public Integer[] fmThumbIds = {
            R.drawable.head_female, R.drawable.eye,R.drawable.ear, R.drawable.nose,
            R.drawable.teeth, R.drawable.neck,R.drawable.chest, R.drawable.shoulder,
            R.drawable.lhung, R.drawable.arm,R.drawable.elbow, R.drawable.hand,
            R.drawable.stomach_female, R.drawable.waist_female,R.drawable.sapoke_female, R.drawable.bottom_female,
            R.drawable.leg_female, R.drawable.knee, R.drawable.feet
    };


    /** Array of text*/

    /**Thai*/
    public String[] tThumbIds = {
            "หัว", "ตา","หู","จมูก",
            "ฟัน","คอ","หน้าอก","ไหล่",
            "หลัง","แขน","ข้อศอก","มือ",
            "ท้อง","เอว","สะโพก","ก้น",
            "ขา","หัวเข่า","เท้า"
    };

    /**Eng*/
    public String[] teThumbIds = {
            "Head", "Eye","Ear","Nose",
            "Teeth","Neck","Chest","Shoulder",
            "Back","Arm","Elbow","Hand",
            "Stomach","Waist","Hip","Bottom",
            "Leg","Knee","Foot"
    };

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
        Intent intent = new Intent(getApplicationContext(), MainIFeelMale.class);
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
                tts = new TextToSpeech(MainPartMale.this, null, aphasiaTalk);
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
