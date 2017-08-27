package com.example.musicmanpop.aphasiatalkhelper;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.net.Uri;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Locale;


public class MainActivity extends ActionBarActivity {

    int language_trigger;
    int gender_trigger;
    ImageView how;
    ImageView sosBtn;
    ImageView setting;
    ImageView th;
    ImageView en;
    ImageView open;
    ImageView close;
    String gen;
    String lang;
    ImageView genMale;
    ImageView genFemale;
    AudioManager manager;
    DatabaseOperation DB;
    SQLiteDatabase SQ;
    ImageView info;
    GridView gridView;


    final static String SVOX_TTS_ENGINE = "com.svox.classic";
    TextToSpeech tts;
    String str;

    public class MyAdapter extends BaseAdapter {
        private Context context;
        private LayoutInflater layoutInflater;
       // private List<String> myList;

        MyAdapter(Context c) {
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

            if (convertView == null) {
                grid = new View(context);
                grid = layoutInflater.inflate(R.layout.maingrid, null);
            } else {
                grid = (View) convertView;
            }
            if (language_trigger == 0 && gender_trigger == 0) {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkTTSEngineInstalled(SVOX_TTS_ENGINE);

        String l_trigger = getIntent().getStringExtra("language_trigger");
        language_trigger = Integer.parseInt(l_trigger);

        String g_trigger = getIntent().getStringExtra("gender_trigger");
        gender_trigger = Integer.parseInt(g_trigger);

        DB = new DatabaseOperation(this);
        SQ = DB.getWritableDatabase();
        DB.onUpgrade(SQ, 1, 1);

        MyAdapter adapter = new MyAdapter(this);
        gridView = (GridView) findViewById(R.id.grid);

        gridView.setAdapter(adapter);
        getIntent();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                checkAndSpeak(tThumbIds[position], teThumbIds[position]);


                if (language_trigger == 0 && gender_trigger ==0 && position != 2 && position != 3) {

                    Bitmap b = BitmapFactory.decodeResource(getResources(), mThumbIds[position]);
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    b.compress(Bitmap.CompressFormat.PNG, 100, bos);
                    byte[] img = bos.toByteArray();
                    DB.putSentence(DB, img, tThumbIds[position]);
                    Log.d("Database Operation", tThumbIds[position]);
                }

                if (language_trigger == 1 && gender_trigger ==0 && position!= 2 && position != 3) {

                    Bitmap b = BitmapFactory.decodeResource(getResources(), mThumbIds[position]);
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    b.compress(Bitmap.CompressFormat.PNG, 100, bos);
                    byte[] img = bos.toByteArray();
                    DB.putSentence(DB, img, teThumbIds[position]);
                    Log.d("Database Operation", teThumbIds[position]);
                }

                if (language_trigger == 0 && gender_trigger ==1 && position!= 2 && position != 3) {

                    Bitmap b = BitmapFactory.decodeResource(getResources(), fmThumbIds[position]);
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    b.compress(Bitmap.CompressFormat.PNG, 100, bos);
                    byte[] img = bos.toByteArray();
                    DB.putSentence(DB, img, tThumbIds[position]);
                    Log.d("Database Operation", tThumbIds[position]);
                }
                if (language_trigger == 1 && gender_trigger ==1 && position!= 2 && position != 3) {

                    Bitmap b = BitmapFactory.decodeResource(getResources(), fmThumbIds[position]);
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    b.compress(Bitmap.CompressFormat.PNG, 100, bos);
                    byte[] img = bos.toByteArray();
                    DB.putSentence(DB, img, teThumbIds[position]);
                    Log.d("Database Operation", teThumbIds[position]);
                }

                if (position == 0) {

                    Intent intent = new Intent(getApplicationContext(), MainIWantMale.class);
                    intent.putExtra("language_trigger", String.valueOf(language_trigger));
                    intent.putExtra("gender_trigger", String.valueOf(gender_trigger));
                    startActivity(intent);
                }

                if (position == 1) {

                    Intent intent = new Intent(getApplicationContext(), MainIFeelMale.class);
                    intent.putExtra("language_trigger", String.valueOf(language_trigger));
                    intent.putExtra("gender_trigger", String.valueOf(gender_trigger));
                    startActivity(intent);
                }

                if (position == 2) {
                    if (language_trigger == 0) {
                        Intent intent = new Intent(getApplicationContext(), Drawing.class);
                        intent.putExtra("language_trigger", String.valueOf(language_trigger));
                        intent.putExtra("gender_trigger", String.valueOf(gender_trigger));
                        startActivity(intent);
                    }
                    if (language_trigger == 1) {
                        Intent intent = new Intent(getApplicationContext(), DrawingEng.class);
                        intent.putExtra("language_trigger", String.valueOf(language_trigger));
                        intent.putExtra("gender_trigger", String.valueOf(gender_trigger));
                        startActivity(intent);
                    }
                }

                if (position == 3) {

                    Intent intent = new Intent(getApplicationContext(), txtToSpeech.class);
                    intent.putExtra("language_trigger", String.valueOf(language_trigger));
                    intent.putExtra("gender_trigger", String.valueOf(gender_trigger));
                    startActivity(intent);
                }

            }
        });



        how = (ImageButton) findViewById(R.id.imageView8);
        how.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goHowTo();
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

        setting = (ImageView) findViewById(R.id.setting);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAndSpeak("ตั้งค่า", "setting");
                settingPopUp();
            }
        });

        info = (ImageView) findViewById(R.id.information);
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ShowInformation.class);
                intent.putExtra("language_trigger", String.valueOf(language_trigger));
                intent.putExtra("gender_trigger", String.valueOf(gender_trigger));
                startActivity(intent);
            }
        });



    }

    public Integer[] mThumbIds = {
            R.drawable.miwant, R.drawable.mifeel, R.drawable.drawing, R.drawable.txt,
    };

    /**
     * Female
     */
    public Integer[] fmThumbIds = {
            R.drawable.fmiwant, R.drawable.fmifeel, R.drawable.drawing, R.drawable.txt,
    };

    /**Array of txt**/
    /**
     * Thai
     */
    public String[] tThumbIds = {
            "ฉันต้องการ", "ฉันรู้สึก", "เขียนข้อความ", "พิมพ์ข้อความ",
    };

    /**
     * Eng
     */
    public String[] teThumbIds = {
            "I Want", "I Feel", "Drawing", "Text To Speech",
    };

    public void goHowTo() {
        Intent intent = new Intent(getApplicationContext(), MainHowToUse1.class);
        intent.putExtra("language_trigger", String.valueOf(language_trigger));
        intent.putExtra("gender_trigger", String.valueOf(gender_trigger));
        startActivity(intent);
    }

    public void settingPopUp() {
        Dialog builder = new Dialog(this);
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (language_trigger == 0) {
            builder.setContentView(R.layout.dialog_setting);
        }
        if (language_trigger == 1) {
            builder.setContentView(R.layout.dialog_setting_eng);
        }
        builder.setCancelable(true);
        builder.show();


        open = (ImageView) builder.findViewById(R.id.imageSoundOpen);
        close = (ImageView) builder.findViewById(R.id.imageSoundClose);
        genMale = (ImageView) builder.findViewById(R.id.imageGenMale);
        genFemale = (ImageView) builder.findViewById(R.id.imageGenFemale);



        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                manager.setStreamVolume(AudioManager.STREAM_NOTIFICATION,15,0);
                manager.setStreamVolume(AudioManager.STREAM_ALARM,15,0);
                manager.setStreamVolume(AudioManager.STREAM_MUSIC,15,0);
                manager.setStreamVolume(AudioManager.STREAM_RING,15,0);
                manager.setStreamVolume(AudioManager.STREAM_SYSTEM,15,0);
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                manager.setStreamVolume(AudioManager.STREAM_NOTIFICATION,0,0);
                manager.setStreamVolume(AudioManager.STREAM_ALARM,0,0);
                manager.setStreamVolume(AudioManager.STREAM_MUSIC,0,0);
                manager.setStreamVolume(AudioManager.STREAM_RING,0,0);
                manager.setStreamVolume(AudioManager.STREAM_SYSTEM,0,0);
            }
        });


        genMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAndSpeak("เพศชาย", "male");
                int num2 = 0;
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("gender_trigger", String.valueOf(num2));
                intent.putExtra("language_trigger", String.valueOf(language_trigger));
                startActivity(intent);
                gen = "m";
            }
        });

        genFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAndSpeak("เพศหญิง", "female");
                int num2 = 1;
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("gender_trigger", String.valueOf(num2));
                intent.putExtra("language_trigger", String.valueOf(language_trigger));
                startActivity(intent);

                gen = "f";
            }
        });

        DB.checkStatus(DB, lang, gen);
    }

    public void checkAndSpeak(String sTH, String sEN) {
        if (language_trigger == 0) {
            str = sTH;
            tts.setLanguage(new Locale("th"));
        }
        if (language_trigger == 1) {
            str = sEN;
        }
        if (tts != null)
            tts.speak(str, TextToSpeech.QUEUE_FLUSH, null);
    }

    ////////////////////////////////// Text To Speech SVOX ///////////////////////////////////

    public void checkTTSEngineInstalled(String aphasiaTalk) {
        boolean isSvoxInstalled = isAppInstalled(aphasiaTalk);
        if (isSvoxInstalled) {
            if (tts == null)
                tts = new TextToSpeech(MainActivity.this, null, aphasiaTalk);
        } else if (!isSvoxInstalled) {
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
        } catch (PackageManager.NameNotFoundException e) {
        }
        return app_installed;
    }

    @Override
    public void onBackPressed() {
    }

    public class EXTRA_MESSAGE1 {
    }
}
