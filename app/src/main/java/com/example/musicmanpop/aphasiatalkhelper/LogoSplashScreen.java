package com.example.musicmanpop.aphasiatalkhelper;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.app.Activity;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.animation.AnimationUtils;

public class LogoSplashScreen extends Activity {
    Handler handler;
    Runnable runnable;
    long delay_time;
    long time = 3000L;

    ImageView logo_mahidol;
    ImageView logo_ict;
    TextView t1;
    TextView t2;
    TextView t3;
    TextView t4;
    TextView t5;
    TextView t6;
    TextView t7;
    TextView t8;


    Animation fadeIn;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.about_us);

        logo_mahidol = (ImageView) findViewById(R.id.imageView3);
        logo_ict = (ImageView) findViewById(R.id.imageView4);
        t1 = (TextView) findViewById(R.id.textView5);
        t2 = (TextView) findViewById(R.id.textPatientInfo);
        t3 = (TextView) findViewById(R.id.FNamePatient);
        t4 = (TextView) findViewById(R.id.LNamePatient);
        t5 = (TextView) findViewById(R.id.NNamePatient);
        t6 = (TextView) findViewById(R.id.textViewCarerInfo);
        t7 = (TextView) findViewById(R.id.FNameCarer);
        t8 = (TextView) findViewById(R.id.textViewLNameCarer);


        fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);

        putAnimation(logo_mahidol, fadeIn);
        putAnimation(logo_ict, fadeIn);
        putAnimation(t1, fadeIn);
        putAnimation(t2, fadeIn);
        putAnimation(t3, fadeIn);
        putAnimation(t4, fadeIn);
        putAnimation(t5, fadeIn);
        putAnimation(t6, fadeIn);
        putAnimation(t7, fadeIn);
        putAnimation(t8, fadeIn);


    }// end OnCreate

    public void putAnimation(View view, Animation anima)
    {
        view.startAnimation(anima);

        handler = new Handler();

        runnable = new Runnable() {
            public void run() {

                final Intent goMain = new Intent(LogoSplashScreen.this, SplashScreen.class);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        // Do something after 1s = 1000ms
                        startActivity(goMain);
                        finish();
                    }
                }, 200);
            } // end run
        };
    }

    public void onResume() {
        super.onResume();
        delay_time = time;
        handler.postDelayed(runnable, delay_time);
        time = System.currentTimeMillis();
    }

    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
        time = delay_time - (System.currentTimeMillis() - time);
    }

}//end class
