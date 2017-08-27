package com.example.musicmanpop.aphasiatalkhelper;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class EditInfo extends ActionBarActivity {

    ImageView back;
    Button edit,save;
    ImageButton info;
    Cursor cursor;
    DatabaseOperation databaseOperation;
    SQLiteDatabase sqLiteDatabase;
    String Pfirstname, Plastname, Pnickname, Page,Cfirstname, Clastname, Ctel, Hname, Hphysician, HN;
    int Pid;
    TextView showPfirstname,showPlastname,showPnickname,showPage,showCfirstname,showClastname,showCtel,showHname,showHphysician,showHN, showPid;
    int language_trigger;
    int gender_trigger;
    TextView GENDER;
    Context ctx = this;
    EditText P_FIRSTNAME,P_LASTTNAME,P_NICKNAME,P_AGE,C_FIRSTNAME,C_LASTTNAME,C_TEL,H_NAME,H_PHYSICIAN,H_HN;
    String p_firstname,p_lastname,p_nickname,p_age,c_firstname,c_lastname,c_tel,h_name,h_physician,h_hn,gender;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String l_trigger= getIntent().getStringExtra("language_trigger");
        language_trigger = Integer.parseInt(l_trigger);

        String g_trigger= getIntent().getStringExtra("gender_trigger");
        gender_trigger = Integer.parseInt(g_trigger);

        if (language_trigger == 0){
            setContentView(R.layout.edit_info);}
        if (language_trigger == 1){
            setContentView(R.layout.edit_info_eng);}


        P_FIRSTNAME = (EditText) findViewById(R.id.txtFirstNamePatient);
        P_LASTTNAME = (EditText) findViewById(R.id.txtLastNamePatient);
        P_NICKNAME = (EditText) findViewById(R.id.txtNickNamePatient);
        P_AGE = (EditText) findViewById(R.id.txtAgePatient);
        C_FIRSTNAME = (EditText) findViewById(R.id.txtFirstNameCarer);
        C_LASTTNAME = (EditText) findViewById(R.id.txtLastNameCarer);
        C_TEL = (EditText) findViewById(R.id.txtTelphoneNumber);
        H_NAME = (EditText) findViewById(R.id.txtHospital);
        H_PHYSICIAN = (EditText) findViewById(R.id.txtDoctorName);
        H_HN = (EditText) findViewById(R.id.txtHN);

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

        save = (Button) findViewById(R.id.btnSave);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                p_firstname = P_FIRSTNAME.getText().toString();
                p_lastname = P_LASTTNAME.getText().toString();
                p_nickname = P_NICKNAME.getText().toString();
                p_age = P_AGE.getText().toString();
                c_firstname = C_FIRSTNAME.getText().toString();
                c_lastname = C_LASTTNAME.getText().toString();
                c_tel = C_TEL.getText().toString();
                h_name = H_NAME.getText().toString();
                h_physician = H_PHYSICIAN.getText().toString();
                h_hn = H_HN.getText().toString();
                gender = GENDER.getText().toString();
                //check
                if (p_firstname.equals("")) {
                    Toast.makeText(getBaseContext(), "Please input Patient first name", Toast.LENGTH_LONG).show();
                    return;
                }
                if (p_lastname.equals("")) {
                    Toast.makeText(getBaseContext(), "Please input Patient last name", Toast.LENGTH_LONG).show();
                    return;
                }
                if (p_nickname.equals("")) {
                    Toast.makeText(getBaseContext(), "Please input Patient nick name", Toast.LENGTH_LONG).show();
                    return;
                }
                if (p_age.equals("")) {
                    Toast.makeText(getBaseContext(), "Please input Patient age", Toast.LENGTH_LONG).show();
                    return;
                }
                if (c_firstname.equals("")) {
                    Toast.makeText(getBaseContext(), "Please input Carer first name", Toast.LENGTH_LONG).show();
                    return;
                }
                if (c_lastname.equals("")) {
                    Toast.makeText(getBaseContext(), "Please input Carer last name", Toast.LENGTH_LONG).show();
                    return;
                }
                if (c_tel.equals("")) {
                    Toast.makeText(getBaseContext(), "Please input Carer telephone", Toast.LENGTH_LONG).show();
                    return;
                }
                if (h_name.equals("")) {
                    Toast.makeText(getBaseContext(), "Please input Hospital name", Toast.LENGTH_LONG).show();
                    return;
                }
                if (h_physician.equals("")) {
                    Toast.makeText(getBaseContext(), "Please input Physician", Toast.LENGTH_LONG).show();
                    return;
                }
                if (h_hn.equals("")) {
                    Toast.makeText(getBaseContext(), "Please input HN", Toast.LENGTH_LONG).show();
                    return;
                } else{
                    DatabaseOperation DB = new DatabaseOperation(ctx);
                    DB.putInformation(DB, p_firstname, p_lastname, p_nickname, p_age, c_firstname, c_lastname,c_tel, h_name, h_physician, h_hn);
                    Toast.makeText(getBaseContext(), "Add Information Success" ,Toast.LENGTH_LONG).show();
                    //finish();
                    goToHome();

                }

            }
        });


        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ShowInformation.class);
                intent.putExtra("language_trigger", String.valueOf(language_trigger));
                intent.putExtra("gender_trigger", String.valueOf(gender_trigger));
                startActivity(intent);
            }
        });


        /*info = (ImageButton) findViewById(R.id.btnhis);
        info.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                Intent newActivity = new Intent(ShowInformation.this, ShowDoctorHistory.class);
                startActivity(newActivity);
            }
        });*/

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }

    public void goToHome()
    {
        Intent intent = new Intent(getApplicationContext(), ShowInformation.class);
        intent.putExtra("language_trigger", String.valueOf(language_trigger));
        intent.putExtra("gender_trigger", String.valueOf(gender_trigger));
        startActivity(intent);
    }

}
