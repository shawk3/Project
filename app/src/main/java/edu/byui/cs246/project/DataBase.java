package edu.byui.cs246.project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.test.InstrumentationTestCase;

import java.util.HashMap;

/**
 * @author Kyle
 * @since 2015-10-31
 */
public class DataBase {
    HashMap<String, String[]> tables = new HashMap<>();
    public static final String DBNAME = "myDB";

    public static final String QTABLE = "QuestionsTable";
    public static final String ATABLE = "AnsTable";
    public static final String SECTOR_TABLE = "SectorTable";
    public static final String SUB_SECTOR_TABLE = "SubSectorTable";
    public static final String SECTOR_SUB_SECTOR_TABLE = "SectorSubSectorTable";
    public static final String SESSION_TABLE = "STable";
    public static final int VERSION = 5;

    //All Tables
    public static final String Key_ROWID = "id";
    public static final int COL_ROWID = 0;

    //Question Table
    public static final String Key_QUESTION_TEXT = "QText";
    public static final int COL_QUESTION_TEXT = 1;

    public static final String[] ALL_QUESTION_KEYS = new String[] {Key_ROWID, Key_QUESTION_TEXT};

    private static final String CREATE_QTable = "create table if not exists " + QTABLE
            + " (" + Key_ROWID + " integer primary key autoincrement, "
            + Key_QUESTION_TEXT + " text not null"
            + ");";


    //Answer Table
    public static final String Key_QID = "QId";
    public static final String Key_SID = "SessionID";
    public static final String Key_QUESTION_ANSWER = "Answer";
    public static final int COL_QUESTION_ID = 1;
    public static final int Col_Session_ID = 2;
    public static final int COL_QUESTION_ANSWER = 3;

    public static final String[] ALL_Answer_KEYS = new String[] {Key_ROWID, Key_QID, Key_SID, Key_QUESTION_ANSWER};

    private static final String CREATE_ATable = "create table if not exists " + ATABLE
            + " (" + Key_ROWID + " integer primary key autoincrement, "
            + Key_QID + " integer not null, "
            + Key_SID + " integer not null, "
            + Key_QUESTION_ANSWER + " text not null"
            + ");";

    //Sector Table
    public static final String Key_SECTOR = "Sector";
    public static final int COL_SECTOR = 1;

    public static final String[] ALL_SECTOR_KEYS = new String[] {Key_ROWID, Key_SECTOR};

    private static final String CREATE_SECTOR_Table = "create table if not exists " + SECTOR_TABLE
            + " (" + Key_ROWID + " integer primary key autoincrement, "
            + Key_SECTOR + " text not null"
            + ");";

    //sub-Sector Table
    public static final String Key_SUB_SECTOR = "SubSector";
    public static final int COL_SUB_SECTOR = 1;

    public static final String[] ALL_SUB_SECTOR_KEYS = new String[] {Key_ROWID, Key_SUB_SECTOR};

    private static final String CREATE_SUB_SECTOR_Table = "create table if not exists " + SUB_SECTOR_TABLE
            + " (" + Key_ROWID + " integer primary key autoincrement, "
            + Key_SUB_SECTOR + " text not null"
            + ");";

    //Sector vs SubSector Table
    public static final String Key_SECTOR_ID = "SectorID";
    public static final String Key_Sub_Sector_ID = "SubSectorID";

    public static final int COL_SECTOR_ID = 1;
    public static final int COL_SUB_SECTOR_ID = 2;

    public static final String[] ALL_SECTOR_SUBSECTOR_KEYS = new String[] {Key_ROWID, Key_SECTOR_ID, Key_Sub_Sector_ID};

    private static final String CREATE_SECTOR_SUBSECTOR_Table = "create table if not exists " + SECTOR_SUB_SECTOR_TABLE
            + " (" + Key_ROWID + " integer primary key autoincrement, "
            + Key_SECTOR_ID + " integer not null, "
            + Key_Sub_Sector_ID + " integer not null"
            + ");";

    //Session Table
    public static final String Key_SESSION_NAME = "Name";
    public static final String Key_Date = "Date";
    public static final String Key_SECTOR_SUBSECTOR_ID = "SectorSubSectorID";
    public static final int COL_SESSION_NAME = 1;
    public static final int COL_DATE = 2;
    public static final int COL_SECTOR_SUB_SECTOR_ID = 3;

    public static final String[] ALL_SESSION_KEYS = new String[] {Key_ROWID, Key_SESSION_NAME, Key_Date, Key_SECTOR_SUBSECTOR_ID};

    private static final String CREATE_SESSION_Table = "create table if not exists " + SESSION_TABLE
            + " (" + Key_ROWID + " integer primary key autoincrement, "
            + Key_SESSION_NAME + " text not null, "
            + Key_Date + " text not null, "
            + Key_SECTOR_SUBSECTOR_ID + " integer not null"
            + ");";



    private Context context;

    private DataBaseHelper myDBHelper;
    private SQLiteDatabase db;

    public DataBase(Context ctx){
        this.context = ctx;
        myDBHelper = new DataBaseHelper(context);
        tables.put(ATABLE, ALL_Answer_KEYS);
        tables.put(QTABLE, ALL_QUESTION_KEYS);
        tables.put(SESSION_TABLE, ALL_SESSION_KEYS);
        tables.put(SECTOR_TABLE, ALL_SECTOR_KEYS);
        tables.put(SUB_SECTOR_TABLE, ALL_SUB_SECTOR_KEYS);
        tables.put(SECTOR_SUB_SECTOR_TABLE, ALL_SECTOR_SUBSECTOR_KEYS);
    }

    /**
     * Allow the database to be written to.
     *
     * @return a writable database
     */
    public DataBase open(){
        db = myDBHelper.getWritableDatabase();
        return this;
    }

    /**
     * close the writable database
     */
    public void close(){
        myDBHelper.close();
    }



    /**
     * General insert, Only works with tables that have an Int id and one String column
     *
     * @param table The name of the dataTable
     * @param text The string value to insert
     * @return The row id
     */
    public long insertRow(String table, String text){
        ContentValues initialValues = new ContentValues();
        String columnName = tables.get(table)[1];
        initialValues.put(columnName, text);

        return db.insert(table, null, initialValues);
    }



    /**
     * insert a row on the Answer Table
     *
     * @param Sid The row id of the session this answer belongs to
     * @param Qid The row id of the question this answer belongs to
     * @param ans The value of the Answer, should be either Y, N, NA
     * @return The Row id of this answer
     */
    public long insertAnswer(int Sid, int Qid, String ans){
        ContentValues initialValues = new ContentValues();
        Cursor c = getAnswer(Qid, Sid);
        if(c == null){
            initialValues.put(Key_QID, Qid);
            initialValues.put(Key_SID, Sid);
            initialValues.put(Key_QUESTION_ANSWER, ans);

            return db.insert(ATABLE, null, initialValues);
        }
        else{
            updateAnswer(Qid, Sid, ans);
            return c.getInt(COL_QUESTION_ID);
        }


    }



    /**
     * insert a row to the Session Table
     *
     * @param name The name of the session (e.g. Assessment 1)
     * @param date The date the session was last worked on
     * @param sectorSubSectorID The id of the SectorSubSector that belong to this session
     * @return  The row id of this session
     */
    public long insertSession(String name, String date, int sectorSubSectorID){
        ContentValues initialValues = new ContentValues();
        initialValues.put(Key_SESSION_NAME, name);
        initialValues.put(Key_Date, date);
        initialValues.put(Key_SECTOR_SUBSECTOR_ID, sectorSubSectorID);

        return db.insert(SESSION_TABLE, null, initialValues);
    }



    /**
     * insert mapping from a sector to a subsector
     *
     * @param SID The id of the sector
     * @param SubSID The id of the subSector
     * @return The row id of the mapping
     */
    public long insertSectorSubSector(int SID, int SubSID){
        ContentValues initialValues = new ContentValues();
        initialValues.put(Key_SECTOR_ID, SID);
        initialValues.put(Key_Sub_Sector_ID, SubSID);

        return db.insert(SECTOR_SUB_SECTOR_TABLE, null, initialValues);
    }



    /**
     * Delete a row from a table
     *
     * @param table The name of the dataTable
     * @param rowID The id of the row to be deleted
     * @return A true of false value as to the sucess of the deletion
     */
    public boolean deleteRow(String table, long rowID){
        String where = Key_ROWID + " = " + rowID;
        return db.delete(table, where, null) > 0;
    }

    //Delete an Answer

    /**
     * Delete an Answer from the AnswerTable
     * @param qID The id of the Question it belongs to
     * @param sID The id of the Session it belongs to
     * @return The success of the deletion as a boolean
     */
    public boolean deleteAnswer(int qID, int sID){
        String where = Key_QID + " = " + qID;
        where += " AND " + Key_SID + " = " + sID;
        return db.delete(ATABLE, where, null) > 0;
    }

    /**
     * Delete all the rows in a table
     *
     * @param table The name of the table to be deleted
     */
    public void deleteAll(String table){
        Cursor c = getAllRows(table);
        long rowId = c.getColumnIndexOrThrow(Key_ROWID);
        if (c.moveToFirst()){
            do{
                deleteRow(table, c.getLong((int) rowId));
            }while(c.moveToNext());
        }
        c.close();
    }


    /**
     * Get all the rows from a table
     *
     * @param table The name of the table
     * @return A Cursor object with all the rows
     */
    public Cursor getAllRows(String table){
        String keys[] = tables.get(table);
        Cursor c = db.query(true, table, keys, null, null, null, null, null, null);
        if(c != null){
            c.moveToFirst();
        }
        return c;
    }

    /**
     * Gets a single row from a table according to a row id
     *
     * @param table The name of the table
     * @param rowId The id of the row
     * @return A Cursor with that row
     */
    public Cursor getRow(String table, long rowId){
        String where = Key_ROWID + " = " + rowId;
        String keys[] = tables.get(table);
        Cursor c = db.query(true, table, keys , where, null, null, null, null, null);
        if(c.getCount() <= 0){
            return null;
        }
        c.moveToFirst();
        return c;
    }

    /**
     * Get The rows tht match the associating text
     *
     * Carefull with this one! the text is matched with that of the second column in the table, if
     * the second column is not a string value then this function will throw an exception and the
     * program will probably crash
     *
     * @param table The name of the table
     * @param text The text that will be compared to the dataTable
     * @return A cursor with the matching rows
     */
    public Cursor getRow(String table, String text){
        String keys[] = tables.get(table);
        String columnName = keys[1];
        String where = columnName + " = '" + text + "'";

        Cursor c = db.query(true, table, keys, where, null, null, null, null, null);
        if(c.getCount() <= 0){
            return null;
        }
        c.moveToFirst();
        return c;
    }

    /**
     * Get the sub-sectors that map to a specific sector
     *
     * @param sectorID The id of the sector
     * @return A cursor with all the sub-sectors that match that sector
     */
    public Cursor getSubSectors(long sectorID){
        String where = Key_SECTOR_ID + " = " + sectorID;
        Cursor c = db.query(true, SECTOR_SUB_SECTOR_TABLE, ALL_SECTOR_SUBSECTOR_KEYS, where, null, null, null, null, null);
        if(c.getCount() <= 0)
            return null;

        c.moveToFirst();
        return c;
    }

    /**
     * Get the Answer that is associated with a question and session
     *
     * Returns null if no answer yet exists
     *
     * @param qID The question id
     * @param sID The session id
     * @return A Cursor with the row that matches the two parameters
     */
    public Cursor getAnswer(int qID, int sID){
        String where = Key_QID + " = " + qID;
        where += " AND " + Key_SID + " = " + sID;
        Cursor c = db.query(true, ATABLE, ALL_Answer_KEYS , where, null, null, null, null, null);
        if(c != null){
            c.moveToFirst();
        }
        if(c.getCount() == 0)
            return null;
        return c;
    }

    public Cursor getSectorSubSectorMap(int sID, int subID){
        String where = Key_SECTOR_ID + " = " + sID;
        where += " AND " + Key_Sub_Sector_ID + " = " + subID;
        Cursor c = db.query(true, SECTOR_SUB_SECTOR_TABLE, ALL_SECTOR_SUBSECTOR_KEYS , where, null, null, null, null, null);
        if(c != null){
            c.moveToFirst();
        }
        if(c.getCount() == 0)
            return null;
        return c;
    }

    public Cursor getSectorSubSectorMap(String sector, String subSector){
        int sID = getRow(SECTOR_TABLE, sector).getInt(COL_ROWID);
        int subID = getRow(SUB_SECTOR_TABLE, subSector).getInt(COL_ROWID);

        Cursor c = getSectorSubSectorMap(sID, subID);
        return c;
    }

    /**
     * Get all the Answers pertaining to a particular session
     *
     * @param sID The session id
     * @return A Cursor with all the rows that pertain to this session
     */
    public Cursor getAllAnswers(int sID) {
        String where = Key_SID + " = " + sID;

        Cursor c = db.query(true, ATABLE, ALL_Answer_KEYS , where, null, null, null, null, null);
        if(c != null){
            c.moveToFirst();
        }
        if(c.getCount() == 0)
            return null;
        return c;
    }

    /**
     * Update the Answer to a question
     *
     * @param qID The question id
     * @param sID The current session id
     * @param ans The new answer
     * @return The success of the update as a bool
     */
    public boolean updateAnswer(long qID, long sID, String ans){
        String where = Key_QID + " = " + qID;
        where += " AND " + Key_SID + " = " + sID;
        ContentValues  newValues = new ContentValues();
        newValues.put(Key_QUESTION_ANSWER, ans);

        return db.update(ATABLE, newValues, where, null) > 0;
    }

    /**
     * Update the Session information
     *
     * @param rowId The session id
     * @param date  The new date
     * @param sectorSubSectorID The new sectorSubsector map id
     * @return The sucess of the update as a bool
     */
    public boolean updateSession(long rowId, String date, long sectorSubSectorID){
        String where = Key_ROWID + " = " + rowId;

        ContentValues newValues = new ContentValues();

        newValues.put(Key_Date, date);
        newValues.put(Key_SECTOR_SUBSECTOR_ID, sectorSubSectorID);

        return db.update(SESSION_TABLE, newValues, where, null) > 0;
    }




    //Adam's version
    //public boolean roughUpdateRow(Cursor toRow, String answer){
        //get data from row
      //  long rowId = toRow.getPosition();
        //String question = toRow.getString(1);

        //delete row
        //deleteRow(rowId);

        //make new row
//        insertRow(question, answer);

  //      return true;
    //}




    private static class DataBaseHelper extends SQLiteOpenHelper {

        public DataBaseHelper(Context context) {
            super(context, DBNAME, null, VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL(CREATE_QTable);
            db.execSQL(CREATE_ATable);
            db.execSQL(CREATE_SECTOR_SUBSECTOR_Table);
            db.execSQL(CREATE_SECTOR_Table);
            db.execSQL(CREATE_SUB_SECTOR_Table);
            db.execSQL(CREATE_SESSION_Table);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("Drop Table if exists " + QTABLE);

            onCreate(db);
        }
    }

    /*public class DataBaseTests extends InstrumentationTestCase {
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
    }*/

}
