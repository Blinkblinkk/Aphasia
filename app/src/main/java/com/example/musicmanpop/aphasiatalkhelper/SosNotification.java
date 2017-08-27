package com.example.musicmanpop.aphasiatalkhelper;


import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.telephony.gsm.SmsManager;
import android.widget.Toast;




public class SosNotification extends ActionBarActivity {

    MediaPlayer sosSound;
    SmsManager smsEmergency;
    AudioManager manager;
    int language_trigger;
    int gender_trigger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String l_trigger= getIntent().getStringExtra("language_trigger");
        language_trigger = Integer.parseInt(l_trigger);

        String g_trigger= getIntent().getStringExtra("gender_trigger");
        gender_trigger = Integer.parseInt(g_trigger);


        if (language_trigger == 0){
            setContentView(R.layout.activity_sos_notification_th);}
        if (language_trigger == 1){
            setContentView(R.layout.activity_sos_notification);}

        getIntent();

        // if sound = mute --> turn on method
        manager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        manager.setStreamVolume(AudioManager.STREAM_NOTIFICATION,10,0);
        manager.setStreamVolume(AudioManager.STREAM_ALARM,10,0);
        manager.setStreamVolume(AudioManager.STREAM_MUSIC,10,0);
        manager.setStreamVolume(AudioManager.STREAM_RING,10,0);
        manager.setStreamVolume(AudioManager.STREAM_SYSTEM,10,0);

        //set volume MAX
        manager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        manager.setStreamVolume(AudioManager.STREAM_MUSIC,
                manager.getStreamMaxVolume(AudioManager.STREAM_MUSIC),0);


        //play SOS sound
        sosSound = MediaPlayer.create(SosNotification.this,R.raw.sos_sound);
        sosSound.setLooping(true);
        sosSound.start();

        //sending emergency message
        String phone = "0851888442";
        String message = "Emergency Message!!";
        try {
            smsEmergency.getDefault();
            smsEmergency.sendTextMessage(phone,null,message,null,null);
            Toast.makeText(getApplicationContext(), "SMS Sent!",
                    Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),
                    "SMS failed, please try again later!",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

        //stop SOS sound
        Button stopBtn = (Button) findViewById(R.id.stopButton);
        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sosSound.stop();
                sosSound.release();
                sosSound = null;

                //go to main screen
                goBackHome();
            }
        });

    }

    //go back Main screen
    public void goBackHome()
    {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("language_trigger", String.valueOf(language_trigger));
        intent.putExtra("gender_trigger", String.valueOf(gender_trigger));
        startActivity(intent);
    }

    //stop sound after close application
    public void onDestroy() {
        super.onDestroy();
        sosSound.stop();
        sosSound.release();
        sosSound = null;
    }

    @Override
    public void onBackPressed() {
    }

}
