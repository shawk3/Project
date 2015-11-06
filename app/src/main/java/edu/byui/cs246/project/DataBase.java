package edu.byui.cs246.project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Kyle on 10/31/2015.
 */
public class DataBase {
    public static final String NAME = "myDB";
    public static final String TABLE = "mainTable";
    public static final int VERSION = 1;

    public static final String Key_ROWID = "id";
    public static final int COL_ROWID = 0;

    public static final String Key_QUESTION_TEXT = "QText";
    public static final String Key_QUESTION_ANSWER = "Answer";

    public static final int COL_QUESTION_TEXT = 1;
    public static final int COL_QUESTION_ANSWER = 2;

    public static final String[] ALL_KEYS = new String[] {Key_ROWID, Key_QUESTION_TEXT, Key_QUESTION_ANSWER};

    private static final String DATABASE_CREATE_SQL = "create table " + TABLE
        + " (" + Key_ROWID + " integer primary key autoincrement, "
        + Key_QUESTION_TEXT + " text not null, "
        + Key_QUESTION_ANSWER + " text not null"
        + ");";

    private final Context context;

    private DataBaseHelper myDBHelper;
    private SQLiteDatabase db;

    public DataBase(Context ctx){
        this.context = ctx;
        myDBHelper = new DataBaseHelper(context);
    }

    public DataBase open(){
        db = myDBHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        myDBHelper.close();
    }

    public long insertRow(String QuestionText, String Answer){
        ContentValues initialValues = new ContentValues();
        initialValues.put(Key_QUESTION_TEXT, QuestionText);
        initialValues.put(Key_QUESTION_ANSWER, Answer);

        return db.insert(TABLE, null, initialValues);
    }

    public boolean deleteRow(long rowID){
        String where = Key_ROWID + " = " + rowID;
        return db.delete(TABLE, where, null) > 0;
    }

    public void deleteAll(){
        Cursor c = getAllRows();
        long rowId = c.getColumnIndexOrThrow(Key_ROWID);
        if (c.moveToFirst()){
            do{
                deleteRow(c.getLong((int) rowId));
            }while(c.moveToNext());
        }
        c.close();
    }

    public Cursor getAllRows(){
        Cursor c = db.query(true, TABLE, ALL_KEYS, null, null, null, null, null, null);
        if(c != null){
            c.moveToFirst();
        }
        return c;
    }

    public Cursor getRow(long rowId){
        String where = Key_ROWID + " = " + rowId;
        Cursor c = db.query(true, TABLE, ALL_KEYS, where, null, null, null, null, null);
        if(c != null){
            c.moveToFirst();
        }
        return c;
    }

    public boolean updateRow(long rowId, String text, String Answer){
        String where = Key_ROWID + " = " + rowId;
        ContentValues  newValues = new ContentValues();
        newValues.put(Key_QUESTION_TEXT, text);
        newValues.put(Key_QUESTION_ANSWER, Answer);

        return db.update(TABLE, newValues, where, null) > 0;
    }

    public boolean updateRow(long rowId, String Answer){
        String where = Key_ROWID + " = " + rowId;
        ContentValues  newValues = new ContentValues();
        newValues.put(Key_QUESTION_ANSWER, Answer);

        return db.update(TABLE, newValues, where, null) > 0;
    }



    private static class DataBaseHelper extends SQLiteOpenHelper {

        public DataBaseHelper(Context context) {
            super(context, NAME, null, VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE_SQL);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("Drop Table if exists " + NAME);

            onCreate(db);
        }
    }

}
