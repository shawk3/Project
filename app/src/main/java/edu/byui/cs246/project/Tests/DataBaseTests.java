package edu.byui.cs246.project.Tests;

import android.content.Context;
import android.database.Cursor;
import android.test.InstrumentationTestCase;
import android.test.RenamingDelegatingContext;
import android.test.mock.MockContext;

import edu.byui.cs246.project.DataBase;
import edu.byui.cs246.project.MainActivity;
import edu.byui.cs246.project.QuestionActivity;

import static java.security.AccessController.getContext;

/**
 * Created by Kyle on 11/7/2015.
 */
public class DataBaseTests extends InstrumentationTestCase{
/*
    public static long id;

    public void testInsertRow(){
        MainActivity toTest = new MainActivity();
        DataBase db = toTest.getDataBase();
        db.open();
        id = db.insertRow("My Test Question", "U");
        Cursor c = db.getRow(id);
        assert(c.moveToFirst());

        c.close();
        db.close();
    }

    public void testUpdateRow(){
        MainActivity toTest = new MainActivity();
        DataBase db = toTest.getDataBase();
        db.open();
        db.updateRow(id, "Y");
        Cursor c = db.getRow(id);
        assert(c.moveToFirst());
        String ans = c.getString(db.COL_QUESTION_ANSWER);
        assertEquals(ans, "Y");

        db.close();
    }*/
}
