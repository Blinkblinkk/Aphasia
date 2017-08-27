package com.example.musicmanpop.aphasiatalkhelper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
import java.util.ArrayList;
import java.util.Locale;

public class MainIFeelMale extends Activity {
    ImageButton home;
    ImageButton back;
    int language_trigger;
    int gender_trigger;
    AudioManager manager;
    ImageView sosBtn;
    ImageView add;
    DatabaseOperation DB;
    SQLiteDatabase SQ;
    String str;
    Cursor cursor;
    ArrayList<String> arrayList = new ArrayList<String>();
    byte[] img;
    Bitmap b;
    Bitmap arrImg[];






    final static String SVOX_TTS_ENGINE = "com.svox.classic";
    TextToSpeech tts;


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
                grid = layoutInflater.inflate(R.layout.gridlayout, null);
            }else{
                grid = (View)convertView;
            }
            if (language_trigger == 0 &&  gender_trigger == 0) {
                ImageView imageView = (ImageView) grid.findViewById(R.id.image);
                imageView.setImageResource(mThumbIds[position]);
                TextView textView = (TextView) grid.findViewById(R.id.text);
                textView.setText(tThumbIds[position]);

                if (position == mThumbIds.length){
                do {
                    int c=0;
                    imageView = (ImageView) grid.findViewById(R.id.image);
                    imageView.setImageBitmap(arrImg[c]);
                    textView = (TextView) grid.findViewById(R.id.text);
                    textView.setText("555");
                    c++;
                }
                    while (cursor.moveToNext());
            }

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
        setContentView(R.layout.main_i_feel_male);

        checkTTSEngineInstalled(SVOX_TTS_ENGINE);

        String l_trigger= getIntent().getStringExtra("language_trigger");
        language_trigger = Integer.parseInt(l_trigger);

        String g_trigger= getIntent().getStringExtra("gender_trigger");
        gender_trigger = Integer.parseInt(g_trigger);

        gridView = (GridView) findViewById(R.id.grid);

        MyAdapter adapter = new MyAdapter(this);
        gridView.setAdapter(adapter);
        getIntent();

        DB = new DatabaseOperation(this);
        SQ = DB.getWritableDatabase();

        home = (ImageButton) findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToHome();
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





//Item Selected
       gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                Log.d("click", "outcome = " + tThumbIds[position]);
                checkAndSpeak(tThumbIds[position], teThumbIds[position]);

                if (language_trigger == 0 && gender_trigger ==0 ) {

                    Bitmap b = BitmapFactory.decodeResource(getResources(), mThumbIds[position]);
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    b.compress(Bitmap.CompressFormat.PNG, 100, bos);
                    byte[] img = bos.toByteArray();
                    DB.putSentence(DB, img, tThumbIds[position]);
                    Log.d("Database Operation", tThumbIds[position]);


                }

                if (language_trigger == 1 && gender_trigger ==0 ) {

                    Bitmap b = BitmapFactory.decodeResource(getResources(), mThumbIds[position]);
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    b.compress(Bitmap.CompressFormat.PNG, 100, bos);
                    byte[] img = bos.toByteArray();
                    DB.putSentence(DB, img, teThumbIds[position]);
                    Log.d("Database Operation", teThumbIds[position]);
                }

                if (language_trigger == 0 && gender_trigger ==1 ) {

                    Bitmap b = BitmapFactory.decodeResource(getResources(), fmThumbIds[position]);
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    b.compress(Bitmap.CompressFormat.PNG, 100, bos);
                    byte[] img = bos.toByteArray();
                    DB.putSentence(DB, img, tThumbIds[position]);
                    Log.d("Database Operation", tThumbIds[position]);
                }
                if (language_trigger == 1 && gender_trigger ==1 ) {

                    Bitmap b = BitmapFactory.decodeResource(getResources(), fmThumbIds[position]);
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    b.compress(Bitmap.CompressFormat.PNG, 100, bos);
                    byte[] img = bos.toByteArray();
                    DB.putSentence(DB, img, teThumbIds[position]);
                    Log.d("Database Operation", teThumbIds[position]);
                }

                Intent intent2 = new Intent(getApplicationContext(), showResult.class);
                intent2.putExtra("language_trigger", String.valueOf(language_trigger));
                intent2.putExtra("gender_trigger", String.valueOf(gender_trigger));
                startActivity(intent2);


                if (position == 0) {
                    Intent intent = new Intent(getApplicationContext(), MainPartMale.class);
                    intent.putExtra("language_trigger", String.valueOf(language_trigger));
                    intent.putExtra("gender_trigger", String.valueOf(gender_trigger));
                    startActivity(intent);
                }



            }
        });




        cursor = SQ.rawQuery("SELECT " + TableData.TableInfo.PIC_ADD + " FROM "
                + TableData.TableInfo.TABLE_ADD + " ORDER BY "
                + TableData.TableInfo.KEY_ID_ITEM,null);

        arrImg = new Bitmap[cursor.getCount()];

        if(cursor != null)

        {
            if (cursor.moveToFirst()) {
                int i= 0;
                do {

                    img = cursor.getBlob(cursor.getColumnIndex("pic_add"));
                    b = BitmapFactory.decodeByteArray(img, 0, img.length);
                    arrImg[i]=b;

                    i++;
                } while (cursor.moveToNext());
            }
        }
        cursor.close();


    }



    /**Array of Pic**/
    /**Male*/
    public Integer[] mThumbIds = {
            R.drawable.pain_male, R.drawable.pee_male,R.drawable.khee_male, R.drawable.tired_male,
            R.drawable.hungry_male, R.drawable.hot_male,R.drawable.cold_male, R.drawable.stress_male,
            R.drawable.sleepy_male, R.drawable.happy_male,R.drawable.sad_male, R.drawable.scared_male,
            R.drawable.angry_male,R.drawable.scratch_male
    };

    /**Female*/
    public Integer[] fmThumbIds = {
            R.drawable.pain_female, R.drawable.pee_female,R.drawable.khee_female, R.drawable.tired_female,
            R.drawable.hungry_female, R.drawable.hot_female,R.drawable.cold_female, R.drawable.stress_female,
            R.drawable.sleepy_female, R.drawable.happy_female,R.drawable.sad_female, R.drawable.scared_female,
            R.drawable.angry_female,R.drawable.scratch_female
    };



    /**Array of Txt**/
    /**Th*/
    public String[] tThumbIds = {
            "ปวด", "ปวดฉี่","ปวดอึ","เหนื่อย",
            "หิว","ร้อน","หนาว","เครียด",
            "ง่วง","มีความสุข","เศร้า","กลัว",
            "โกรธ","คัน"
    };

    /**Eng*/
    public String[] teThumbIds = {
            "Pain", "Pee","Feces","Tired",
            "Hungry","Hot","Cold","Stress",
            "Sleepy","Happy","Sad","Scared",
            "Angry","Itchy"
    };

    public void goToHome()
    {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
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
                tts = new TextToSpeech(MainIFeelMale.this, null, aphasiaTalk);
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

