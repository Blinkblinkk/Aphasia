package com.example.musicmanpop.aphasiatalkhelper;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ShowInformation extends ActionBarActivity {

    ImageView home;
    Button edit,save;
    Button info;
    Cursor cursor;
    DatabaseOperation databaseOperation;
    SQLiteDatabase sqLiteDatabase;
    String Pfirstname, Plastname, Pnickname, Page,Cfirstname, Clastname, Ctel, Hname, Hphysician, HN;
    int Pid;
    TextView showPfirstname,showPlastname,showPnickname,showPage,showCfirstname,showClastname,showCtel,showHname,showHphysician,showHN, showPid;
    int language_trigger;
    int gender_trigger;
    TextView GENDER;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String l_trigger= getIntent().getStringExtra("language_trigger");
        language_trigger = Integer.parseInt(l_trigger);

        String g_trigger= getIntent().getStringExtra("gender_trigger");
        gender_trigger = Integer.parseInt(g_trigger);

        if (language_trigger == 0){
            setContentView(R.layout.activity_show_information);}
        if (language_trigger == 1){
            setContentView(R.layout.activity_show_information_eng);}


        databaseOperation = new DatabaseOperation(this);
        sqLiteDatabase = databaseOperation.getWritableDatabase();

        //showPid = (TextView)findViewById(R.id.txtid);
        showPfirstname = (TextView)findViewById(R.id.txtFirstNamePatient);
        showPlastname = (TextView)findViewById(R.id.txtLastNamePatient);
        showPnickname = (TextView)findViewById(R.id.txtNickNamePatient);
        showPage = (TextView)findViewById(R.id.txtAgePatient);
        showCfirstname = (TextView)findViewById(R.id.txtFirstNameCarer);
        showClastname = (TextView)findViewById(R.id.txtLastNameCarer);
        showCtel = (TextView)findViewById(R.id.txtTelphoneNumber);
        showHname = (TextView)findViewById(R.id.txtHospital);
        showHphysician = (TextView)findViewById(R.id.txtDoctorName);
        showHN = (TextView)findViewById(R.id.txtHN);

        String array[] = new String[11];
        cursor = sqLiteDatabase.rawQuery("SELECT * FROM "
                + TableData.TableInfo.TABLE_NAME + " ORDER BY "
                + TableData.TableInfo.USER_ID + " DESC LIMIT 1;",null);

        if (cursor.moveToFirst()){
            //Pid = Integer.parseInt(cursor.getString(0));
            Pfirstname = cursor.getString(1);
            Plastname = cursor.getString(2);
            Pnickname = cursor.getString(3);
            Page = cursor.getString(4);
            Cfirstname = cursor.getString(5);
            Clastname = cursor.getString(6);
            Ctel = cursor.getString(7);
            Hname = cursor.getString(8);
            Hphysician = cursor.getString(9);
            HN = cursor.getString(10);
            Log.d("Cursor","show cursor");
        }
        Provider pro = new Provider(Pfirstname, Plastname, Pnickname, Page, Cfirstname, Clastname, Ctel, Hname, Hphysician, HN);
        //  array[0] = String.valueOf(pro.getKEY_ID_SHOW_SENTENCE());
        array[1] = pro.getPfirstname();
        array[2] = pro.getPlastname();
        array[3] = pro.getPnickname();
        array[4] = pro.getPage();
        array[5] = pro.getCfirstname();
        array[6] = pro.getClastname();
        array[7] = pro.getCtel();
        array[8] = pro.getHname();
        array[9] = pro.getHphysician();
        array[10] = pro.getHN();
        Log.d("Provider","show provider");

        // showPid.setText(array[0]);
        showPfirstname.setText(array[1]);
        showPlastname.setText(array[2]);
        showPnickname.setText(array[3]);
        showPage.setText(array[4]);
        showCfirstname.setText(array[5]);
        showClastname.setText(array[6]);
        showCtel.setText(array[7]);
        showHname.setText(array[8]);
        showHphysician.setText(array[9]);
        showHN.setText(array[10]);
        Log.d("DB", "show setTxt");

        GENDER = (TextView) findViewById(R.id.txtGenderPatient);
        if (gender_trigger == 0 && language_trigger == 1){
            GENDER.setText("Male");}
        if (gender_trigger == 0 && language_trigger == 0){
            GENDER.setText("ชาย");}
        if (gender_trigger == 1 && language_trigger == 1){
            GENDER.setText("Female");}
        if (gender_trigger == 1 && language_trigger == 0){
            GENDER.setText("หญิง");}

        home = (ImageView) findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("language_trigger", String.valueOf(language_trigger));
                intent.putExtra("gender_trigger", String.valueOf(gender_trigger));
                startActivity(intent);
            }
        });

        edit = (Button) findViewById(R.id.btnEdit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditInfo.class);
                intent.putExtra("language_trigger", String.valueOf(language_trigger));
                intent.putExtra("gender_trigger", String.valueOf(gender_trigger));
                startActivity(intent);
            }
        });

        save = (Button) findViewById(R.id.btnSave);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("language_trigger", String.valueOf(language_trigger));
                intent.putExtra("gender_trigger", String.valueOf(gender_trigger));
                startActivity(intent);
            }
        });

        info = (Button) findViewById(R.id.btnhis);
        info.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ShowDoctorHistory.class);
                intent.putExtra("language_trigger", String.valueOf(language_trigger));
                intent.putExtra("gender_trigger", String.valueOf(gender_trigger));
                startActivity(intent);
            }
        });


    }
    @Override
    public void onBackPressed() {
    }


}
