package com.example.musicmanpop.aphasiatalkhelper;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;


public class MainHowToUse3 extends ActionBarActivity {

    ImageButton next;
    ImageButton before;
    AnimationDrawable slideShowAnimation;
    int language_trigger;
    int gender_trigger;
    ImageButton quit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animation_list_3);

        String l_trigger= getIntent().getStringExtra("language_trigger");
        language_trigger = Integer.parseInt(l_trigger);

        String g_trigger= getIntent().getStringExtra("gender_trigger");
        gender_trigger = Integer.parseInt(g_trigger);

        getIntent();

        ImageView slideShow = (ImageView) findViewById(R.id.imageViewSlideShow);
        slideShow.setBackgroundResource(R.drawable.slide_show3);
        slideShowAnimation = (AnimationDrawable) slideShow.getBackground();
        slideShowAnimation.start();

        next = (ImageButton) findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNext();
            }
        });

        before = (ImageButton) findViewById(R.id.before);
        before.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToBefore();
            }
        });

        quit = (ImageButton) findViewById(R.id.exit);
        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exit();
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_how_to_use3, menu);
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
        Intent intent = new Intent(getApplicationContext(), MainHowToUse4.class);
        intent.putExtra("language_trigger", String.valueOf(language_trigger));
        intent.putExtra("gender_trigger", String.valueOf(gender_trigger));
        startActivity(intent);
    }

    public void goToBefore()
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
}
