package com.example.musicmanpop.aphasiatalkhelper;

import android.provider.BaseColumns;

import java.lang.ref.SoftReference;
import java.security.PublicKey;

public class TableData {
    public TableData()
    {

    }

    public static abstract class TableInfo implements BaseColumns
    {
        //record & show information
        public static final String DATABASE_NAME = "user_info";
        public static final String TABLE_NAME = "add_info";
        public static final String USER_ID = "user_id";
        public static final String P_FIRSTNAME = "p_firstname";
        public static final String P_LASTTNAME = "p_lastname";
        public static final String P_NICKNAME = "p_nickname";
        public static final String P_AGE = "p_age";
        public static final String C_FIRSTNAME = "c_firstname";
        public static final String C_LASTTNAME = "c_lastname";
        public static final String C_TEL = "c_tel";
        public static final String H_NAME = "h_name";
        public static final String H_PHYSICIAN = "h_physician";
        public static final String H_HN = "h_hn";

        //combine table to show sentence
        public static final String TABLE_NAME1 = "table_show_sentence";
        public static final String KEY_ID_SHOW_SENTENCE = "key_id_show_sentence";
        public static final String IMG_PRESS = "img_press";
        public static final String TEXT_PRESS = "text_press";

        //Table keep history
        public static final String TABLE_NAME2 = "table_keep_history";
        public static final String KEY_ID_HISTORY = "key_id_history";
        public static final String IMAGE = "image";
        public static final String SENTENCE = "sentence";
        public static final String SENTENCE_EN = "sentence_en";
        public static final String DATE_TIME = "date_time";

        //Table add item
        public static final String TABLE_NAME3 = "table_add_item";
        public static final String KEY_ID_ADD_ITEM = "key_id_add_item";
        public static final String IMAGE_ADD = "image_add";
        public static final String WORD_TH = "word_th";
        public static final String WORD_EN = "word_en";


        //Table check status
        public static final String TABLE_NAME4 = "table_chk_status";
        public static final String KEY_ID_CHK = "key_id_chk";
        public static final String LANG = "language";
        public static final String GEN = "gender";


        //combine sentence english
        public static final String TABLE_NAME5 = "table_show_sentence_en";
        public static final String KEY_ID_SHOW_SENTENCE_EN = "key_id_show_sentence_en";
        public static final String IMG_PRESS_EN = "img_press_en";
        public static final String TEXT_PRESS_EN = "text_press_en";

        //combine sentence english
        public static final String TABLE_ADD = "table_add";
        public static final String KEY_ID_ITEM = "key_id_item";
        public static final String PIC_ADD = "pic_add";
        public static final String THAI_ADD = "thai_add";
        public static final String ENG_ADD = "eng_add";

    }
}
