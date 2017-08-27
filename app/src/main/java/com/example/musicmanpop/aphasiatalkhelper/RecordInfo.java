package com.example.musicmanpop.aphasiatalkhelper;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


public class RecordInfo extends ActionBarActivity {

    EditText P_FIRSTNAME,P_LASTTNAME,P_NICKNAME,P_AGE,C_FIRSTNAME,C_LASTTNAME,C_TEL,H_NAME,H_PHYSICIAN,H_HN;
    String p_firstname,p_lastname,p_nickname,p_age,c_firstname,c_lastname,c_tel,h_name,h_physician,h_hn,gender;
    Button save,skip;
    Context ctx = this;
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
            setContentView(R.layout.activity_record_info);}
        if (language_trigger == 1){
            setContentView(R.layout.activity_record_info_eng);}

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

        GENDER = (TextView) findViewById(R.id.txtGenderPatient);
        if (gender_trigger == 0 && language_trigger == 1){
        GENDER.setText("Male");}
        if (gender_trigger == 0 && language_trigger == 0){
            GENDER.setText("ชาย");}
        if (gender_trigger == 1 && language_trigger == 1){
            GENDER.setText("Female");}
        if (gender_trigger == 1 && language_trigger == 0){
            GENDER.setText("หญิง");}

        //getIntent();
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

        skip = (Button)findViewById(R.id.btnSkip);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open from main
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("language_trigger", String.valueOf(language_trigger));
                intent.putExtra("gender_trigger", String.valueOf(gender_trigger));
                startActivity(intent);

            }
        });

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }


    public void goToHome()
    {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("language_trigger", String.valueOf(language_trigger));
        intent.putExtra("gender_trigger", String.valueOf(gender_trigger));
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
    }

}
