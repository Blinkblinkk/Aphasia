package com.example.musicmanpop.aphasiatalkhelper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.musicmanpop.aphasiatalkhelper.R;

public class MainGenderSelect extends Activity {

int language_trigger;

    public static final String PREFS_NAME = "MyPrefsFile";

    public class MyAdapter extends BaseAdapter {

        private Context context;
        private LayoutInflater layoutInflater;

        MyAdapter(Context c){
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
            if(convertView==null){
                grid = new View(context);
                grid = layoutInflater.inflate(R.layout.gendergrid, null);
            }else{
                grid = (View)convertView;
            }
            if (language_trigger == 0) {
                ImageView imageView = (ImageView) grid.findViewById(R.id.image);
                imageView.setImageResource(mThumbIds[position]);
                TextView textView = (TextView) grid.findViewById(R.id.text);
                textView.setText(tThumbIds[position]);

            }

            if (language_trigger == 1) {
                ImageView imageView = (ImageView) grid.findViewById(R.id.image);
                imageView.setImageResource(mThumbIds[position]);
                TextView textView = (TextView) grid.findViewById(R.id.text);
                textView.setText(teThumbIds[position]);

            }

            return grid;
        }

    }

    GridView gridView;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gender_select);

        String l_trigger= getIntent().getStringExtra("language_trigger");
       language_trigger = Integer.parseInt(l_trigger);

        gridView = (GridView)findViewById(R.id.grid);

        MyAdapter adapter = new MyAdapter(this);
        gridView.setAdapter(adapter);

        getIntent();


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

               //male
                if (position == 0){
                    int num2 = 0 ;
                    Intent intent = new Intent(getApplicationContext(), RecordInfo.class);
                    intent.putExtra("gender_trigger", String.valueOf(num2));
                    intent.putExtra("language_trigger", String.valueOf(language_trigger));
                    startActivity(intent);}
                //female
                if (position == 1){
                    int num2 = 1 ;
                    Intent intent = new Intent(getApplicationContext(), RecordInfo.class);
                    intent.putExtra("gender_trigger", String.valueOf(num2));
                    intent.putExtra("language_trigger", String.valueOf(language_trigger));
                    startActivity(intent);}


            }});





    }

    /**Array of Pic**/
    public Integer[] mThumbIds = {
            R.drawable.gender_male, R.drawable.gender_female
    };

    /**Array of Txt**/
    /**Th*/
    public String[] tThumbIds = {
            "ผู้ชาย", "ผู้หญิง"
    };

    /**Eng*/
    public String[] teThumbIds = {
            "Male", "Female"
    };



}
