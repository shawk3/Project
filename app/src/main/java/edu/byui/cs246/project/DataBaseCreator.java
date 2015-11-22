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
    HashMap<String, String> sectors = new HashMap<>();
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

        //dataBase.close();

        String[] demographics = {"Chemical", "Commercial Facilities", "Communications",
                "Critical Manufacturing", "Dams", "Defense Industrial Base", "Emergency Services",
                "Energy", "Financial Services", "Food and Agriculture", "Government Facilities",
                "Healthcare and Public Health", "Information Technology", "Nuclear Reactors, Materials, and Waste",
                "Sector-Specific Agencies", "Transportation Systems", "Water and Wastewater Systems"};
        for(int i = 0; i < demographics.length; i++){
            dataBase.insertDRow(demographics[i], "Sub-Sector 1");
            dataBase.insertDRow(demographics[i], "Sub-Sector 2");
        }
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

        sectors.put("","");

    }
}
