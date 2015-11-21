package edu.byui.cs246.project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.test.InstrumentationTestCase;

/**
 * Created by Kyle on 10/31/2015.
 * .b  .t  .v
 */
public class DataBase {
    public static final String NAME = "myDB";
    public static final String QTABLE = "mainTable";
    public static final String DTABLE = "DemoTable";
    public static final int VERSION = 3;

    //both tables
    public static final String Key_ROWID = "id";
    public static final int COL_ROWID = 0;

    //Question Table
    public static final String Key_QUESTION_TEXT = "QText";
    public static final String Key_QUESTION_ANSWER = "Answer";
    public static final int COL_QUESTION_TEXT = 1;
    public static final int COL_QUESTION_ANSWER = 2;

    public static final String[] ALL_QUESTION_KEYS = new String[] {Key_ROWID, Key_QUESTION_TEXT, Key_QUESTION_ANSWER};

    private static final String CREATE_QTable = "create table if not exists" + QTABLE
        + " (" + Key_ROWID + " integer primary key autoincrement, "
        + Key_QUESTION_TEXT + " text not null, "
        + Key_QUESTION_ANSWER + " text not null"
        + ");";

    //Demographics Table
    public static final String Key_SECTOR = "Sector";
    public static final String Key_SUB_SECTOR = "SubSector";
    public static final int COL_SECTOR = 1;
    public static final int COL_SUB_SECTOR = 2;

    public static final String[] ALL_DEMOGRAPHIC_KEYS = new String[] {Key_ROWID, Key_SECTOR, Key_SUB_SECTOR};

    private static final String CREATE_DTable = "create table if not exists" + DTABLE
            + " (" + Key_ROWID + " integer primary key autoincrement, "
            + Key_SECTOR + " text not null, "
            + Key_SUB_SECTOR + " text not null"
            + ");";



    private Context context;

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

        return db.insert(QTABLE, null, initialValues);
    }

    public boolean deleteRow(long rowID){
        String where = Key_ROWID + " = " + rowID;
        return db.delete(QTABLE, where, null) > 0;
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
        Cursor c = db.query(true, QTABLE, ALL_QUESTION_KEYS, null, null, null, null, null, null);
        if(c != null){
            c.moveToFirst();
        }
        return c;
    }

    public Cursor getRow(long rowId){
        String where = Key_ROWID + " = " + rowId;
        Cursor c = db.query(true, QTABLE, ALL_QUESTION_KEYS, where, null, null, null, null, null);
        if(c != null){
            c.moveToFirst();
        }
        return c;
    }

    public Cursor getRow(String questionText){
        String where = Key_QUESTION_TEXT + " = '" + questionText + "'";
        Cursor c = db.query(true, QTABLE, ALL_QUESTION_KEYS, where, null, null, null, null, null);
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

        return db.update(QTABLE, newValues, where, null) > 0;
    }

    public boolean updateRow(long rowId, String Answer){
        String where = Key_ROWID + " = " + rowId;
        ContentValues  newValues = new ContentValues();
        newValues.put(Key_QUESTION_ANSWER, Answer);

        return db.update(QTABLE, newValues, where, null) > 0;
    }

    public long insertDRow(String sector, String subSector){
        ContentValues initialValues = new ContentValues();
        initialValues.put(Key_SECTOR, sector);
        initialValues.put(Key_SUB_SECTOR, subSector);

        return db.insert(DTABLE, null, initialValues);
    }

    public boolean deleteDRow(long rowID){
        String where = Key_ROWID + " = " + rowID;
        return db.delete(DTABLE, where, null) > 0;
    }

    public void deleteAllD(){
        Cursor c = getAllDRows();
        long rowId = c.getColumnIndexOrThrow(Key_ROWID);
        if (c.moveToFirst()){
            do{
                deleteDRow(c.getLong((int) rowId));
            }while(c.moveToNext());
        }
        c.close();
    }

    public Cursor getAllDRows(){
        Cursor c = db.query(true, DTABLE, ALL_DEMOGRAPHIC_KEYS, null, null, null, null, null, null);
        if(c != null){
            c.moveToFirst();
        }
        return c;
    }

    public Cursor getDRow(long rowId){
        String where = Key_ROWID + " = " + rowId;
        Cursor c = db.query(true, DTABLE, ALL_DEMOGRAPHIC_KEYS, where, null, null, null, null, null);
        if(c != null){
            c.moveToFirst();
        }
        return c;
    }

    public Cursor getDRow(String sector){
        String where = Key_SECTOR + " = '" + sector + "'";
        Cursor c = db.query(true, DTABLE, ALL_DEMOGRAPHIC_KEYS, where, null, null, null, null, null);
        if(c != null){
            c.moveToFirst();
        }
        return c;
    }




    private static class DataBaseHelper extends SQLiteOpenHelper {

        public DataBaseHelper(Context context) {
            super(context, NAME, null, VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL(CREATE_QTable);
            db.execSQL(CREATE_DTable);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("Drop Table if exists " + QTABLE);
            db.execSQL("Drop Table if exists " + DTABLE);

            onCreate(db);
        }
    }

    public class DataBaseTests extends InstrumentationTestCase {
        public long id;

        public void testInsertRow(){
            id = insertRow("My Test Question", "U");
            Cursor c = getRow(id);
            assert(c.moveToFirst());

            c.close();
        }

        public void testUpdateRow(){
            updateRow(id, "Y");
            Cursor c = getRow(id);
            assert(c.moveToFirst());
            String ans = c.getString(COL_QUESTION_ANSWER);
            assertEquals(ans, "Y");
            assert(1 == 2);

        }
    }

}
