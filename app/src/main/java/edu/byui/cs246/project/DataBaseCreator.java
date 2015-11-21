package edu.byui.cs246.project;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Kyle on 11/7/2015.
 */
public class DataBaseCreator {
    HashMap<String, String> questions = new HashMap<String, String>();
    Context context;
    DataBase dataBase;

    public DataBaseCreator(DataBase c){
        //context = c;
        dataBase = c;
    }

    public void create(){
        upload();
        //DataBase dataBase = new DataBase(context);
        dataBase.open();

        dataBase.deleteAll();

        Set<String> e = questions.keySet();
        for (String s:e) {
            dataBase.insertRow(s, questions.get(s));
        }

        Cursor c = dataBase.getRow("First Question");
        long id = c.getInt(dataBase.COL_ROWID);
        //dataBase.close();
    }

    private void upload() {
        questions.put("First Question", "U");
        questions.put("Second Question", "Y");
        questions.put("Third Question", "U");
        questions.put("Fourth Question", "U");
        questions.put("Fith Question", "N");
        questions.put("Sixth", "U");
        questions.put("One more Question with a lot of text, text, text text! And here is some more text.. Hi.  Some more", "U");
        questions.put("Last Question", "U");


    }
}
