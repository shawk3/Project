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

    String[] subSectors = {"Basic", "Specialty", "Agricultural", "Pharmaceuticals", "Consumer", "", "", "",
                          "Entertainment and Media", "Gaming", "Lodging", "Outdoor Events", "Public Assembly", "Real Estate", "Retail", "Sports Leagues",
                           "", "","", "", "", "", "", "",
                          "Primary Metal", "Machinery", "Electrical Equipment", "Transportation Equipment", "", "", "", "",
                          "", "","", "", "", "", "", "",
                          "", "","", "", "", "", "", "",
                           "Law Enforcement", "Fire and Emergency Services", "Emergency Management", "Emergency Medical Services", "Public Works", "","", "",
                          "Electricity", "Petroleum", "Natural Gas", "","","","","",
                         "", "","", "", "", "", "", "",
                         "", "","", "", "", "", "", "",
                         "Education Facilities", "National Monuments", "","","","","","",
                         "", "","", "", "", "", "", "",
                         "", "","", "", "", "", "", "",
                         "", "","", "", "", "", "", "",
                         "", "","", "", "", "", "", "",
                         "Aviation", "Highway Infrastructure", "Maritime Transportation", "Mass Transit", "Pipeline Systems", "Freight Rail", "Postal and Shipping",
                         "", "","", "", "", "", "", "",};



    String questions[] = new String[]{"Have all default passwords been changed?",
            "Are there policies and procedures concerning the generation and use of passwords?",
            "Does the system uniquely identify and authenticate organizational users?",
            "Does the system monitor and manage communications at the system boundary and at key internal boundaries within the system?",
            "Are the number of access points to the system limited to allow for better monitoring of inbound and outbound network traffic?",
            "Does the system deny network traffic by default and allow network traffic by exception?",
            "Do you encrypt communication over all untrusted communication channels?",
            "Are all factory default authentication credentials changed on system components and applications upon installation",
            "Is wireless access to the system authorized, monitored, and managed?",
            "Is there a thorough scan for unauthorized changes to software and information?",
            "Does the system protect against or limit the effects of denial-of-service attacks based on a defined list of types of denial-of-service attacks?",
            "Are malicious code protection mechanisms updated whenever new releases are available in accordance with configuration management policy and procedures?",
            "Does the system prevent users from circumventing host-based malicious code protection capabilities?",
            "Is there a capability to recover and reconstitute the system to a known secure state after a disruption, compromise, or failure?",
            "Is the concept of least privilege used to accomplish assigned tasks?",
            "Does the system automatically disable inactive accounts after a defined time period?",
            "Does the system provide mechanisms to protect the authenticity of device-to-device communications sessions?",
            "Is entry to the facility controlled by physical access devices and/or guards?",
            "Does remote access to the network require authentication prior to system connection?",
    };

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

        createQuestions();
        createSectors();
        createSubSectors();
        createSectorSubSectorMap();
        createSessions();
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
            dataBase.insertRow(dataBase.SUB_SECTOR_TABLE, subSectors[i]);
    }

    private void createSectorSubSectorMap(){

        int sectorID;
        int subID;

        Cursor sect;
        Cursor sub;

        sect = dataBase.getRow(dataBase.SECTOR_TABLE, demographics[0]);
        sectorID = sect.getInt(dataBase.COL_ROWID);
        sub = dataBase.getRow(dataBase.SUB_SECTOR_TABLE, subSectors[0]);
        subID = sub.getInt(dataBase.COL_ROWID);

        dataBase.insertSectorSubSector(sectorID, subID);

        subID = dataBase.getRow(dataBase.SUB_SECTOR_TABLE, subSectors[1]).getInt(dataBase.COL_ROWID);
        dataBase.insertSectorSubSector(sectorID, subID);



        sectorID = dataBase.getRow(dataBase.SECTOR_TABLE, demographics[1]).getInt(dataBase.COL_ROWID);
        subID = dataBase.getRow(dataBase.SUB_SECTOR_TABLE, subSectors[0]).getInt(dataBase.COL_ROWID);
        dataBase.insertSectorSubSector(sectorID, subID);

    }

    private void createSessions(){
        dataBase.insertSession("Test Assessment", "today", 1);
        dataBase.insertSession("Test Assessment 2", "yesterday", 1);
    }
}
