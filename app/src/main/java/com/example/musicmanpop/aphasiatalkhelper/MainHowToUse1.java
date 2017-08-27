package com.example.musicmanpop.aphasiatalkhelper;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MainHowToUse1 extends ActionBarActivity {

    AnimationDrawable slideShowAnimation;
    ImageButton next;
    int language_trigger;
    int gender_trigger;
    ImageButton quit;
    ImageButton about;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animation_list_1);

        String l_trigger= getIntent().getStringExtra("language_trigger");
        language_trigger = Integer.parseInt(l_trigger);

        String g_trigger= getIntent().getStringExtra("gender_trigger");
        gender_trigger = Integer.parseInt(g_trigger);

        getIntent();

        ImageView slideShow = (ImageView) findViewById(R.id.imageViewSlideShow);
        slideShow.setBackgroundResource(R.drawable.slide_show);
        slideShowAnimation = (AnimationDrawable) slideShow.getBackground();
        slideShowAnimation.start();

        next = (ImageButton) findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNext();
            }
        });

        quit = (ImageButton) findViewById(R.id.exit);
        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exit();
            }
        });

       /* about = (ImageButton) findViewById(R.id.aboutus);
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goabout();
            }
        });*/

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_how_to_use1, menu);
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

    public void goToNext()
    {
        Intent intent = new Intent(getApplicationContext(), MainHowToUse2.class);
        intent.putExtra("language_trigger", String.valueOf(language_trigger));
        intent.putExtra("gender_trigger", String.valueOf(gender_trigger));
        startActivity(intent);
    }

    public void exit()
    {

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("language_trigger", String.valueOf(language_trigger));
        intent.putExtra("gender_trigger", String.valueOf(gender_trigger));
        startActivity(intent);
    }

    public void goabout()
    {

        Intent intent = new Intent(getApplicationContext(), AboutUs.class);
        intent.putExtra("language_trigger", String.valueOf(language_trigger));
        intent.putExtra("gender_trigger", String.valueOf(gender_trigger));
        startActivity(intent);
    }

}
