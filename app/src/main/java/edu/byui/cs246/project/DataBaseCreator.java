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

    String[] demographics = {"Chemical", "Commercial Facilities", "Communications",
            "Critical Manufacturing", "Dams", "Defense Industrial Base", "Emergency Services",
            "Energy", "Financial Services", "Food and Agriculture", "Government Facilities",
            "Healthcare and Public Health", "Information Technology", "Nuclear Reactors, Materials, and Waste",
            "Sector-Specific Agencies", "Transportation Systems", "Water and Wastewater Systems"};

    String[] subSectors = {"transportation", "distribution"};


    String questions[] = new String[]{"Have all default passwords been changed?",
            "Are all passwords at least 9 characters in length?",
    };

    HashMap<String, String> sectors = new HashMap<>();
    Context context;
    DataBase dataBase;

    public DataBaseCreator(DataBase c){
        //context = c;
        dataBase = c;
    }

    public void create(){
        //DataBase dataBase = new DataBase(context);
        dataBase.open();

        //dataBase.deleteAll();

        //dataBase.close();


    }



    private void createQuestions(){

        for(int i = 0; i < questions.length; i++)
            dataBase.insertRow(dataBase.QTABLE, questions[i]);
    }

    private void createSectors(){

        for(int i = 0; i < demographics.length; i++)
            dataBase.insertRow(dataBase.SECTOR_TABLE, demographics[i]);
    }

    private void createSubSectors(){
        for(int i = 0; i < subSectors.length; i++)
            dataBase.insertRow(dataBase.SECTOR_TABLE, subSectors[i]);
    }

    private void createSectorSubSectorMap(){
        Cursor c = dataBase.getAllRows(dataBase.SUB_SECTOR_TABLE);
        int sectorID = dataBase.getRow(dataBase.SECTOR_TABLE, demographics[0]).getInt(dataBase.COL_ROWID);
        int subID = dataBase.getRow(dataBase.SUB_SECTOR_TABLE, subSectors[0]).getInt(dataBase.COL_ROWID);
        dataBase.insertSectorSubSector(sectorID, subID);
        subID = dataBase.getRow(dataBase.SUB_SECTOR_TABLE, subSectors[1]).getInt(dataBase.COL_ROWID);
        dataBase.insertSectorSubSector(sectorID, subID);



        sectorID = dataBase.getRow(dataBase.SECTOR_TABLE, demographics[1]).getInt(dataBase.COL_ROWID);
        subID = dataBase.getRow(dataBase.SUB_SECTOR_TABLE, subSectors[0]).getInt(dataBase.COL_ROWID);
        dataBase.insertSectorSubSector(sectorID, subID);

    }
}
