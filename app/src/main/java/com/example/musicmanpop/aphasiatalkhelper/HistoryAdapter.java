package com.example.musicmanpop.aphasiatalkhelper;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by porpla on 14/2/2559.
 */
public class HistoryAdapter extends ArrayAdapter<History>{

    Context context;
    int resouce;
    ArrayList<History> data = new ArrayList<History>();

    public HistoryAdapter(Context context, int resouce1, ArrayList<History> data) {
        super(context, resouce1, data);
        this.resouce = resouce1;
        this.context = context;
        this.data = data;
        Log.d("test00", "data0" + data.size());
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View row = convertView;
        try {
            ImageHolder holder = null;
            if (row == null)
            {
                Log.d("Row", "Start");
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                row = inflater.inflate(resouce, parent, false);
                holder = new ImageHolder();
                holder.img = (ImageView) row.findViewById(R.id.imgIcon);
                holder.txtSentence = (TextView) row.findViewById(R.id.txtTitle);
            }
            else
            {
                Log.d("Row", "full");
                holder = (ImageHolder)row.getTag();
            } //check adapter already


            if(holder != null){

                History pic = data.get(position);

                Log.d("Adapter", "text_start");

                if(pic.sentence !=null)
                    {
                    holder.txtSentence.setText(pic.sentence);
                     }
                else
                    {
                    holder.txtSentence.setText("-");
                    }
                Log.d("Adapter", "pic_start");

                //convert bytee to bitmap take from history class
                     if(pic.image != null)
                     {
                         byte[] outimg = pic.image;
                         ByteArrayInputStream imgStream = new ByteArrayInputStream(outimg);
                        Bitmap theimg = BitmapFactory.decodeStream(imgStream);
                         holder.img.setImageBitmap(theimg);
                     }
                     else
                     {
                        Log.d("TsetER", "Pic Null");
                     }
            }

            else
            {
                Log.d("Adapter", "= Null");
            }
        }

        catch (Exception e)
            {
            row = null;
            Log.d("HE", "Error"+e);
            }

        return row;
    }

    public class ImageHolder {
        public ImageView img;
        public TextView txtSentence;
    }
}
