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

public class SplashScreen extends Activity {
    Handler handler;
    Runnable runnable;
    long delay_time;
    long time = 3000L;

    ImageView logo;
    TextView aphasia;
    TextView talk;

    Animation fadeIn;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash_screen);

        logo = (ImageView) findViewById(R.id.imageViewLogo);
        aphasia = (TextView) findViewById(R.id.textViewAphasia);
        talk = (TextView) findViewById(R.id.textViewTalk);

        fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);

        putAnimation(logo, fadeIn);
        putAnimation(aphasia, fadeIn);
        putAnimation(talk, fadeIn);

    }// end OnCreate

    public void putAnimation(View view, Animation anima)
    {
        view.startAnimation(anima);

        handler = new Handler();

        runnable = new Runnable() {
            public void run() {

                int num = 0 ;
                final Intent intent = new Intent(getApplicationContext(), MainGenderSelect.class);
                intent.putExtra("language_trigger", String.valueOf(num));


                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        // Do something after 1s = 1000ms
                        startActivity(intent);
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
