package com.example.musicmanpop.aphasiatalkhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.provider.Contacts;
import android.text.TextUtils;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class DatabaseOperation extends SQLiteOpenHelper {

    public static final int database_version = 2;

    //table record info.
    public String CREATE_QUERY = "CREATE TABLE "+TableData.TableInfo.TABLE_NAME+
            "("+TableData.TableInfo.USER_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
            +TableData.TableInfo.P_FIRSTNAME+" TEXT,"+TableData.TableInfo.P_LASTTNAME+" TEXT,"
            +TableData.TableInfo.P_NICKNAME+" TEXT,"+TableData.TableInfo.P_AGE+" TEXT,"
            +TableData.TableInfo.C_FIRSTNAME+" TEXT,"+TableData.TableInfo.C_LASTTNAME+" TEXT,"
            +TableData.TableInfo.C_TEL+" NUMBER,"+TableData.TableInfo.H_NAME+" TEXT,"
            +TableData.TableInfo.H_PHYSICIAN+" TEXT,"+TableData.TableInfo.H_HN+" TEXT );";

    //table keep sentence
    public String CREATE_SENTENCE = "CREATE TABLE "+TableData.TableInfo.TABLE_NAME1+
            "("+TableData.TableInfo.KEY_ID_SHOW_SENTENCE+" INTEGER PRIMARY KEY AUTOINCREMENT,"
            + TableData.TableInfo.IMG_PRESS+" BLOB,"+TableData.TableInfo.TEXT_PRESS+" TEXT );";

    //table keep sentence english
    public String CREATE_SENTENCE_EN = "CREATE TABLE "+TableData.TableInfo.TABLE_NAME5+
            "("+TableData.TableInfo.KEY_ID_SHOW_SENTENCE_EN+" INTEGER PRIMARY KEY AUTOINCREMENT,"
            + TableData.TableInfo.IMG_PRESS_EN+" BLOB,"+TableData.TableInfo.TEXT_PRESS_EN+" TEXT );";

    //table keep history
    public String CREATE_HISTORY = "CREATE TABLE "+TableData.TableInfo.TABLE_NAME2+
            "("+TableData.TableInfo.KEY_ID_HISTORY+" INTEGER PRIMARY KEY AUTOINCREMENT,"
            + TableData.TableInfo.IMAGE+" BLOB,"+TableData.TableInfo.SENTENCE+" TEXT,"
            +TableData.TableInfo.DATE_TIME+" DATETIME );";

    //table keep history
    public String CREATE_ITEM = "CREATE TABLE "+TableData.TableInfo.TABLE_NAME3+
            "("+TableData.TableInfo.KEY_ID_ADD_ITEM+" INTEGER PRIMARY KEY AUTOINCREMENT,"
            +TableData.TableInfo.IMAGE_ADD+" BLOB,"+TableData.TableInfo.WORD_EN+" TEXT,"
            +TableData.TableInfo.WORD_TH+" TEXT );";

    //table check status
    public String CREATE_STATUS = "CREATE TABLE "+TableData.TableInfo.TABLE_NAME4+
            "("+TableData.TableInfo.KEY_ID_CHK+" INTEGER PRIMARY KEY AUTOINCREMENT,"
            +TableData.TableInfo.LANG+" TEXT,"+TableData.TableInfo.GEN+" TEXT );";

    //table add
    public String CREATE_ADD = "CREATE TABLE "+TableData.TableInfo.TABLE_ADD+
            "("+TableData.TableInfo.KEY_ID_ITEM+" INTEGER PRIMARY KEY AUTOINCREMENT,"
            +TableData.TableInfo.PIC_ADD+" BLOB,"+TableData.TableInfo.THAI_ADD+" TEXT,"
            +TableData.TableInfo.ENG_ADD+" TEXT );";

    public DatabaseOperation(Context context) {
        super(context, TableData.TableInfo.DATABASE_NAME, null, database_version);

        Log.d("Database operations", "Database created");
    }

    @Override
    public void onCreate(SQLiteDatabase sdb) {
        //Log.d("DB", "Create Table");
        //sdb.execSQL(CREATE_QUERY);
        sdb.execSQL(CREATE_SENTENCE);
        sdb.execSQL(CREATE_SENTENCE_EN);
        Log.d("Database operations", "Table create sentence created");
        //Log.d("DB","Create TableE");
        try {
            Log.d("DB1", "Create Table");
            sdb.execSQL(CREATE_QUERY);
            sdb.execSQL(CREATE_HISTORY);
            sdb.execSQL(CREATE_STATUS);
            sdb.execSQL(CREATE_ADD);
            Log.d("Database operations", "Table created");
            Log.d("DB1", "Create TableE");
        }catch (Exception e){
            Log.d("DB1", "Error");
        }
        Log.d("Database operations", "Table createddddddddddddddd");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TableData.TableInfo.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TableData.TableInfo.TABLE_NAME1);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TableData.TableInfo.TABLE_NAME5);
        //sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TableData.TableInfo.TABLE_NAME1);
        onCreate(sqLiteDatabase);
        Log.d("DB", "Updateeeeeeeeeeeeeeeeeeeeeeeeeeeee");
    }

    public void putInformation(DatabaseOperation sqLiteDatabase, String strPfirstname, String strPlastname, String strPnickname, String strPage,
                               String strCfirstname, String strClastname,String strCtel, String strHname, String strHphysician, String strHN) {

        SQLiteDatabase SQ = sqLiteDatabase.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(TableData.TableInfo.P_FIRSTNAME, strPfirstname);
        values.put(TableData.TableInfo.P_LASTTNAME, strPlastname);
        values.put(TableData.TableInfo.P_NICKNAME, strPnickname);
        values.put(TableData.TableInfo.P_AGE, strPage);
        values.put(TableData.TableInfo.C_FIRSTNAME, strCfirstname);
        values.put(TableData.TableInfo.C_LASTTNAME, strClastname);
        values.put(TableData.TableInfo.C_TEL, strCtel);
        values.put(TableData.TableInfo.H_NAME, strHname);
        values.put(TableData.TableInfo.H_PHYSICIAN, strHphysician);
        values.put(TableData.TableInfo.H_HN, strHN);

        SQ.insert(TableData.TableInfo.TABLE_NAME, null, values);
        Log.d("Database operations", "One raw in putInformation inserted");
        //SQ.close();

    }

    public void putSentence(DatabaseOperation dop, byte[] strImgPress, String strTxtPress){
        SQLiteDatabase sq = dop.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(TableData.TableInfo.IMG_PRESS, strImgPress);
        values.put(TableData.TableInfo.TEXT_PRESS, strTxtPress);

        sq.insert(TableData.TableInfo.TABLE_NAME1, null, values);
        Log.d("Database operation2", "One raw in image inserted");
        //sq.close();

    }

    public void putSentenceEN(DatabaseOperation dop, byte[] strImgPressEN, String strTxtPressEN){
        SQLiteDatabase sq = dop.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(TableData.TableInfo.IMG_PRESS_EN, strImgPressEN);
        values.put(TableData.TableInfo.TEXT_PRESS_EN, strTxtPressEN);

        sq.insert(TableData.TableInfo.TABLE_NAME5, null, values);
        Log.d("Database operation2", "One raw in image inserted");
        //sq.close();
    }

    //add history
    public void putHistory(DatabaseOperation dop, byte[] strImg, String strSentence, String strDatetime){
        Log.d("DB", "Insert");
        SQLiteDatabase sq = dop.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(TableData.TableInfo.IMAGE, strImg);
        values.put(TableData.TableInfo.SENTENCE, strSentence);
        values.put(TableData.TableInfo.DATE_TIME, strDatetime);

        sq.insert(TableData.TableInfo.TABLE_NAME2, null, values);
        Log.d("DB", "InsertE");
        Log.d("Database operation3", "One raw in history inserted");
        // sq.close();

    }

    //add item
    public void putItem(DatabaseOperation dop, byte[] strImgAdd, String strThaiAdd, String strEngAdd){
        Log.d("DB", "Insert");
        SQLiteDatabase sq = dop.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(TableData.TableInfo.PIC_ADD, strImgAdd);
        values.put(TableData.TableInfo.THAI_ADD, strThaiAdd);
        values.put(TableData.TableInfo.ENG_ADD, strEngAdd);

        sq.insert(TableData.TableInfo.TABLE_ADD, null, values);
        Log.d("DB", "InsertE");
        Log.d("Database operation3", "One row in add item inserted");
        Log.d("Database operation3", ""+strThaiAdd);
        Log.d("Database operation3", ""+strEngAdd);
        Log.d("Database operation3", ""+strImgAdd);

        // sq.close();

    }
    /*public Cursor getItem(DatabaseOperation dop){
        //get data from database
        SQLiteDatabase SQ = dop.getReadableDatabase();
        String[] columns = {TableData.TableInfo.THAI_ADD, TableData.TableInfo.ENG_ADD};
        Cursor CR = SQ.query(TableData.TableInfo.TABLE_ADD, columns, null, null, null, null, null);
        Log.d("Database operation3", "ไม่เออเร่อ");
        return CR;

    }*/

    public Cursor getItem(DatabaseOperation dop,int ID) {
        //get data from database
        SQLiteDatabase SQ = dop.getReadableDatabase();
        String SQLs = "SELECT * FROM " +
                TableData.TableInfo.TABLE_NAME1 +
                " WHERE " + TableData.TableInfo.KEY_ID_SHOW_SENTENCE + "=" + ID + ";";
        return SQ.rawQuery(SQLs, null);
    }
    //get single history
   /* History getHistory(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TableData.TableInfo.TABLE_NAME2,
                new String[]{TableData.TableInfo.KEY_ID_HISTORY,
                        TableData.TableInfo.IMAGE, TableData.TableInfo.SENTENCE},
                TableData.TableInfo.KEY_ID_HISTORY + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        History history = new History(Integer.parseInt(cursor.getString(0)),cursor.getBlob(1),cursor.getString(1));

        return history;
    }*/


    //get all history
    public List<History> getAllHistory(){
        List<History> historyList = new ArrayList<History>();
        //select all history
        Log.d("DB","Start");
        String selectQuery = "SELECT * FROM "
                + TableData.TableInfo.TABLE_NAME2 + " ORDER BY "
                + TableData.TableInfo.KEY_ID_HISTORY + " DESC LIMIT 3";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        int x =0;
        //loop through all rows and add to list
        if (cursor.moveToFirst()){
            Log.d("DB","Inside");
            do {
                x++;
                History history = new History(Integer.parseInt(cursor.getString(0)), cursor.getBlob(1), cursor.getString(2));
                history.setId(Integer.parseInt(cursor.getString(0)));
                history.setImage(cursor.getBlob(1));
                history.setSentence(cursor.getString(2));

                //add history to list
                historyList.add(history);
                Log.d("History count", ""+(cursor.getString(2)));

            }while (cursor.moveToNext());
        }
        //db.close();
        Log.d("DB", "size" + historyList.size());
        return historyList;
    }
    /* //get all history
     public List<HistoryDoctor> getHistoryDoctor(){
         List<HistoryDoctor> docList = new ArrayList<HistoryDoctor>();
         //select all history
         Log.d("DB","Start");
         String selectQuery = "SELECT " + TableData.TableInfo.KEY_ID_HISTORY + ", "
                 + TableData.TableInfo.SENTENCE + ", " + TableData.TableInfo.DATE_TIME + " FROM "
                 + TableData.TableInfo.TABLE_NAME2;
         SQLiteDatabase db = this.getWritableDatabase();
         Cursor cursor = db.rawQuery(selectQuery, null);

         //loop through all rows and add to list
         if (cursor.moveToFirst()){
             Log.d("DB","Inside");
             do {
                 HistoryDoctor history = new HistoryDoctor(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2));
                 history.setId(Integer.parseInt(cursor.getString(0)));
                 history.setSentence(cursor.getString(1));
                 history.setDate(cursor.getString(2));

                 //add history to list
                 docList.add(history);
             }while (cursor.moveToNext());
         }
         //db.close();
         Log.d("DB", "size" + docList.size());
         return docList;
     }
 */
    public void checkStatus(DatabaseOperation dop, String lang, String gen)
    {
        SQLiteDatabase SQ = dop.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TableData.TableInfo.LANG, lang);
        values.put(TableData.TableInfo.GEN, gen);
    }
   /* public Cursor getHistory(SQLiteDatabase sqLiteDatabase){
        String[] getinfo = {TableData.TableInfo.SENTENCE};
        Cursor cursor= sqLiteDatabase.query(TableData.TableInfo.TABLE_NAME2,getinfo,null,null,null,null,null);
        return cursor;
    }*/

    /*public void AddItem(DatabaseOperation dop, byte[] strImgadd, String strTxtTH, String strTxtEN){
        SQLiteDatabase sq = dop.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TableData.TableInfo.IMAGE_ADD, strImgadd);
        values.put(TableData.TableInfo.WORD_EN, strTxtEN);
        values.put(TableData.TableInfo.WORD_TH, strTxtTH);
    }*/

   /* public Cursor getImage(DatabaseOperation dop){
        //get data from database
        SQLiteDatabase SQ = dop.getReadableDatabase();
        String SQLs="SELECT * FROM "
                + TableData.TableInfo.TABLE_NAME1 + " ORDER BY "
                + TableData.TableInfo.KEY_ID_SHOW_SENTENCE + " DESC LIMIT 1;";

        return SQ.rawQuery(SQLs,null);
    }*/

    public void deleteLastRow()
    {
        String selectQuery = "DELETE FROM "
                + TableData.TableInfo.TABLE_NAME1 + " WHERE "
                + TableData.TableInfo.KEY_ID_SHOW_SENTENCE + " = (SELECT MAX("
                + TableData.TableInfo.KEY_ID_SHOW_SENTENCE + ") FROM "
                + TableData.TableInfo.TABLE_NAME1 + " );";
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL(selectQuery);

        Log.d("DB","Deleteeeeeeeeeeeeeeee");
        //return selectQuery;

    }





}
