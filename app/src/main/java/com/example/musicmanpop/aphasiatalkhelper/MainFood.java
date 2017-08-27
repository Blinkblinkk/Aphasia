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

public class MainFood extends Activity {

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
                grid = layoutInflater.inflate(R.layout.gridlayout2, null);
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
                imageView.setImageResource(mThumbIds[position]);
                TextView textView = (TextView) grid.findViewById(R.id.text);
                textView.setText(tThumbIds[position]);


            }

            if (language_trigger == 1 && gender_trigger == 1) {
                ImageView imageView = (ImageView) grid.findViewById(R.id.image);
                imageView.setImageResource(mThumbIds[position]);
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
        setContentView(R.layout.food);

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

        DB = new DatabaseOperation(this);
        SQ = DB.getWritableDatabase();

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

 gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                Log.d("click", "outcome = " + tThumbIds[position]);
                if (position >=17 && position<= 22){
                    checkAndSpeak(tThumbIds[position], teThumbIds[position]);

                }
                else checkAndSpeak(tThumbIds[position], teThumbIds[position]);

                if (language_trigger == 0) {

                    Bitmap b = BitmapFactory.decodeResource(getResources(), mThumbIds[position]);
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    b.compress(Bitmap.CompressFormat.PNG, 100, bos);
                    byte[] img = bos.toByteArray();
                    DB.putSentence(DB, img, tThumbIds[position]);
                    Log.d("Database Operation", tThumbIds[position]);
                }

                if (language_trigger == 1) {

                    Bitmap b = BitmapFactory.decodeResource(getResources(), mThumbIds[position]);
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
            }
        });
    }

    /**Array of Pic**/
    public Integer[] mThumbIds = {
            R.drawable.somtamplara, R.drawable.somtam,R.drawable.padthai, R.drawable.kangkeawwhan,
            R.drawable.panangmhoo, R.drawable.masaman,R.drawable.padseeeiw, R.drawable.kaipadking,
            R.drawable.khaipalo, R.drawable.kaokaijeaw,R.drawable.kaomunkai, R.drawable.kanomjeen,
            R.drawable.tomyamkung, R.drawable.kaokhamhoo,R.drawable.bamheemhoodang, R.drawable.kaotompla,
            R.drawable.kaopadmhoo,R.drawable.frenchfries,R.drawable.beefsteak,R.drawable.friedchic,
            R.drawable.mushroomsoup,R.drawable.pizza,R.drawable.spaghetti
    };

    /**Array of Txt**/
    /**Th*/
    public String[] tThumbIds = {
            "ส้มตำปลาร้า", "ตำไทย","ผัดไท","แกงเขียวหวานไก่",
            "แพนงหมู","มัสมั่น","ผัดซีอิ๊ว","ไก่ผัดขิง",
            "ไข่พะโล้","ข้าวไข่เจียว","ข้าวมันไก่","ขนมจีน",
            "ต้มยำกุ้ง","ข้าวขาหมู","บะหมี่หมูแดง","ข้าวต้มปลา",
            "ข้าวผัดหมู","เฟรนช์ฟราย","สเต็กเนื้อ","ไก่ทอด",
            "ซุปเห็ด","พิซซ่า","สปาเก็ตตี้ซอสแดง"
    };

    /**Eng*/
    public String[] teThumbIds = {
            "Som Tam Pla Ra", "Tam Thai","Pad Thai","Kang Kheaw Whan Kai",
            "Pa Nang Mhoo","Masaman","Pad See Eiw","Kai Pad King",
            "Khai Pa Lho","Kao Khai Jeaw","Kao Mun Kai","Ka Nhom Jeen",
            "Tom Yam Kung","Kao Kha Mhoo","Ba Mhee","Kao Tom Pla",
            "Kao Pad Mhoo","French Fries","Beef Steak","Fried Chicken",
            "Mushroom Soup","Pizza","Red Sauce Spaghetti"
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
        Intent intent = new Intent(getApplicationContext(), MainEatCat.class);
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
                tts = new TextToSpeech(MainFood.this, null, aphasiaTalk);
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
