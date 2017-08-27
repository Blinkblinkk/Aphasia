package com.example.musicmanpop.aphasiatalkhelper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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

public class MainIWantMale extends Activity {
    ImageButton home;
    int language_trigger;
    int gender_trigger;
    AudioManager manager;
    ImageView sosBtn,add;
    DatabaseOperation DB;
    SQLiteDatabase SQ;
    int length;

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

              /*  String text = textView.getText().toString();

                //ImageView iv = (ImageView) findViewById(R.id.splashImageView);
                Drawable d = imageView.getBackground();
                BitmapDrawable bitDw = ((BitmapDrawable) d);
                Bitmap bitmap = bitDw.getBitmap();
                System.out.println(".....d....."+d);
                System.out.println("...bitDw...."+bitDw);
                System.out.println("....bitmap...."+bitmap);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] imageInByte = stream.toByteArray();

                DB.putSentence(DB, imageInByte, text);
                Log.d("Database Operation", "inset put sentense img,text button ");*/

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
        setContentView(R.layout.main_i_want_male);

        DB = new DatabaseOperation(this);
        SQ = DB.getWritableDatabase();
//        DB.onUpgrade(SQ, 1, 1);


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
                    DB.putSentence(DB, img,  teThumbIds[position]);
                    Log.d("Database Operation",  teThumbIds[position]);
                }

                Intent intent2 = new Intent(getApplicationContext(), showResult.class);
                intent2.putExtra("language_trigger", String.valueOf(language_trigger));
                intent2.putExtra("gender_trigger", String.valueOf(gender_trigger));
                startActivity(intent2);

                if (position == 0){
                    checkAndSpeak("กิน","กิน");
                    Intent intent = new Intent(getApplicationContext(), MainEatCat.class);
                    intent.putExtra("language_trigger", String.valueOf(language_trigger));
                    intent.putExtra("gender_trigger", String.valueOf(gender_trigger));
                    startActivity(intent);
                }

                if (position == 1){
                    checkAndSpeak("ดื่ม","ดื่ม");
                    Intent intent = new Intent(getApplicationContext(), MainDrinkCat.class);
                    intent.putExtra("language_trigger", String.valueOf(language_trigger));
                    intent.putExtra("gender_trigger", String.valueOf(gender_trigger));
                    startActivity(intent);
                }

                if (position == 2){
                    checkAndSpeak("ไป","ไป");
                    Intent intent = new Intent(getApplicationContext(), MainGoMale.class);
                    intent.putExtra("language_trigger", String.valueOf(language_trigger));
                    intent.putExtra("gender_trigger", String.valueOf(gender_trigger));
                    startActivity(intent);
                }


            }});
    }


    /**Array of pic**/
    /**Male*/
    public Integer[] mThumbIds = {
            R.drawable.eat_male, R.drawable.drink_male,R.drawable.go_male, R.drawable.slp_male,
            R.drawable.toiletth_male, R.drawable.teethbrush_male,R.drawable.shower_male, R.drawable.hairwash_male,
            R.drawable.medicine, R.drawable.sit_male,R.drawable.walk_male, R.drawable.talk_male,
            R.drawable.shop_male, R.drawable.tv_male,R.drawable.read_male, R.drawable.exercise_male,
            R.drawable.music_male, R.drawable.maitow,R.drawable.wheelchair_male, R.drawable.nail,
            R.drawable.comb_male, R.drawable.powder_male
    };

    /**Female*/
    public Integer[] fmThumbIds = {
            R.drawable.eat_female, R.drawable.drink_female,R.drawable.go_female, R.drawable.slp_female,
            R.drawable.toiletth_female, R.drawable.teethbrush_female,R.drawable.shower_female, R.drawable.hairwash_female,
            R.drawable.medicine, R.drawable.sit_female,R.drawable.walk_female, R.drawable.talk_female,
            R.drawable.shop_female, R.drawable.watchtv_female,R.drawable.read_female, R.drawable.exercise_female,
            R.drawable.listenmusic_female, R.drawable.maitow,R.drawable.wheelchair_female, R.drawable.nail,
            R.drawable.comb_female, R.drawable.powder_female
    };

    /**Array of txt**/
    /**Thai*/
    public String[] tThumbIds = {
            "กิน", "ดื่ม","ไป","นอน",
            "เข้าห้องน้ำ","แปรงฟัน","อาบน้ำ","สระผม",
            "ยา","นั่ง","เดิน","พูดคุย",
            "ซื้อของ","ดูทีวี","อ่านหนังสือ","ออกกำลังกาย",
            "ฟังเพลง","ไม้เท้า","นั่งรถเข็น","ตัดเล็บ",
            "หวีผม","ประแป้ง"
    };

    /**Eng*/
    public String[] teThumbIds = {
            "Eat", "Drink","Go","Sleep",
            "Toilet","Brush Teeth","Shower","Wash Hair",
            "Medicine","Sit","Walk","Talk",
            "Shopping","Watch TV","Read A Book","Exercise",
            "Music","Walker","Wheelchair","Cut Nails",
            "Comb Hair","Powder"
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
                tts = new TextToSpeech(MainIWantMale.this, null, aphasiaTalk);
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
