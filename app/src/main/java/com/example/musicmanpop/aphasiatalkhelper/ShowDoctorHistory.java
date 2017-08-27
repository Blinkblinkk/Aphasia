package com.example.musicmanpop.aphasiatalkhelper;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShowDoctorHistory extends ActionBarActivity {

    DatabaseOperation db;
    SQLiteDatabase sq;
    Cursor cursor;
    ListView listView;
    ImageButton home;
    int language_trigger,gender_trigger;
    ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_doctor_history);
        String l_trigger = getIntent().getStringExtra("language_trigger");
        language_trigger = Integer.parseInt(l_trigger);

        String g_trigger = getIntent().getStringExtra("gender_trigger");
        gender_trigger = Integer.parseInt(g_trigger);

        listView = (ListView) findViewById(R.id.listView);



        db = new DatabaseOperation(this);
        sq = db.getWritableDatabase();

        cursor = sq.rawQuery("SELECT " + TableData.TableInfo.KEY_ID_HISTORY + ", "
                + TableData.TableInfo.SENTENCE + ", " + TableData.TableInfo.DATE_TIME + " FROM "
                + TableData.TableInfo.TABLE_NAME2, null);

        ArrayList<String> dirArray = new ArrayList<String>();

        cursor.moveToFirst();
        while ( !cursor.isAfterLast() ){
            if (language_trigger == 0){
            dirArray.add("ID : " + cursor.getString(cursor.getColumnIndex(TableData.TableInfo.KEY_ID_HISTORY)) + "\n"
                    + "ประโยค : " + cursor.getString(cursor.getColumnIndex(TableData.TableInfo.SENTENCE)) + "\n"
                    + "วันที่เวลา : " + cursor.getString(cursor.getColumnIndex(TableData.TableInfo.DATE_TIME)) + "\n"
                    + "----------------------------------------------------------------------------------------------------");}
            if (language_trigger == 1){
                dirArray.add("ID : " + cursor.getString(cursor.getColumnIndex(TableData.TableInfo.KEY_ID_HISTORY)) + "\n"
                        + "Sentence : " + cursor.getString(cursor.getColumnIndex(TableData.TableInfo.SENTENCE)) + "\n"
                        + "DateTime : " + cursor.getString(cursor.getColumnIndex(TableData.TableInfo.DATE_TIME)) + "\n"
                        + "----------------------------------------------------------------------------------------------------");}
            cursor.moveToNext();
        }

        ArrayAdapter<String> adapterDir = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.custom_textview, dirArray);




        listView.setAdapter(adapterDir);

        home = (ImageButton) findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("language_trigger", String.valueOf(language_trigger));
                intent.putExtra("gender_trigger", String.valueOf(gender_trigger));
                startActivity(intent);
            }
        });

        back = (ImageButton) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ShowInformation.class);
                intent.putExtra("language_trigger", String.valueOf(language_trigger));
                intent.putExtra("gender_trigger", String.valueOf(gender_trigger));
                startActivity(intent);
            }
        });
    }


    public void onPause() {
        super.onPause();
        db.close();
        sq.close();
    }
    @Override
    public void onBackPressed() {
    }



}
