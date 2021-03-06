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
 * Creates the initial DataBase
 *
 * @author Kyle
 * @since 2015-11-7
 */
public class DataBaseCreator {

    /** List of all the Sectors */
    String[] demographics = {"Default", "Chemical", "Commercial Facilities", "Communications",
            "Critical Manufacturing", "Dams", "Defense Industrial Base", "Emergency Services",
            "Energy", "Financial Services", "Food and Agriculture", "Government Facilities",
            "Health-care and Public Health", "Information Technology", "Nuclear Reactors, Materials, and Waste",
            "Transportation Systems", "Water and Waste-water Systems"};

    /** List of all the sub-Sectors */
    String[] subSectors = { "Default", "Basic", "Specialty", "Agricultural", "Pharmaceuticals", "Consumer", "Entertainment and Media",
                        "Gaming", "Lodging", "Outdoor Events", "Public Assembly", "Real Estate", "Retail", "Sports Leagues",
                        "Primary Metal", "Machinery", "Electrical Equipment", "Transportation Equipment",
                        "Law Enforcement", "Fire and Emergency Services", "Emergency Management", "Emergency Medical Services", "Public Works",
                        "Electricity", "Petroleum", "Natural Gas", "Education Facilities", "National Monuments", "Aviation",
                        "Highway Infrastructure", "Maritime Transportation", "Mass Transit", "Pipeline Systems", "Freight Rail", "Postal and Shipping",
                        "Private Sector", "Public Sector"};

    /** Sector sub-sector mapping by index */
    int[][] map = new int[][]{{0,0},
            {1,1},{1,2},{1,3},{1,4},{1,5},
            {2,6},{2,7},{2,8},{2,9},{2,10},{2,11},{2,12},{2,13},
            {3,35},
            {4,14},{4,15},{4,16},{4,17},
            {5,35},
            {6,35},{6,36},
            {7,18},{7,19},{7,20},{7,21},{7,22},
            {8,23},{8,24},{8,25},
            {9,36},
            {10,36},
            {11,26},{11,27},
            {12,36},
            {13,35},{13,36},
            {14,35},
            {15,28},{15,29},{15,30},{15,31},{15,32},{15,33},{15,34},
            {16,35}};

    /**  List of all the questions */
    String questions[] = new String[]{"Have all default passwords been changed?",
            "Are there policies and procedures concerning the generation and use of passwords?",
            "Does the system uniquely identify and authenticate organizational users?",
            "Does the system monitor and manage communications at the system boundary and at key internal boundaries within the system?",
            "Are the number of access points to the system limited to allow for better monitoring of inbound and outbound network traffic?",
            "Does the system deny network traffic by default and allow network traffic by exception?",
            "Do you encrypt communication over all untrusted communication channels?",
            "Are all factory default authentication credentials changed on system components and applications upon installation?",
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

    /* Our DataBase */
    DataBase dataBase;

    public DataBaseCreator(DataBase d){
        dataBase = d;


    }

    /**
     * create a new instance of the data base
     */
    public void create(){
        dataBase.open();

        dataBase.deleteAll(dataBase.ATABLE);
        dataBase.deleteAll(dataBase.QTABLE);
        dataBase.deleteAll(dataBase.SESSION_TABLE);
        dataBase.deleteAll(dataBase.SECTOR_SUB_SECTOR_TABLE);
        dataBase.deleteAll(dataBase.SUB_SECTOR_TABLE);
        dataBase.deleteAll(dataBase.SECTOR_TABLE);

        createQuestions();
        createSectors();
        createSubSectors();
        createSectorSubSectorMap();
        //createSessions();
        dataBase.close();


    }


    /** Send questions to the dataBase. */
    private void createQuestions(){

        for(int i = 0; i < questions.length; i++)
            dataBase.insertRow(dataBase.QTABLE, questions[i]);
    }

    /** Send sector list to the dataBase. */
    private void createSectors(){

        for(int i = 0; i < demographics.length; i++)
            dataBase.insertRow(dataBase.SECTOR_TABLE, demographics[i]);
    }

    /** Send Sub-Sectors to the DataBase */
    private void createSubSectors(){
        for(int i = 0; i < subSectors.length; i++)
            dataBase.insertRow(dataBase.SUB_SECTOR_TABLE, subSectors[i]);
    }

    /** Send Sector mapping to the DataBase */
    private void createSectorSubSectorMap(){

        int sectorID;
        int subID;

        Cursor sect;
        Cursor sub;

        for(int i = 0; i < map.length; i++){
            sect = dataBase.getRow(dataBase.SECTOR_TABLE, demographics[map[i][0]]);
            sectorID = sect.getInt(dataBase.COL_ROWID);
            sub = dataBase.getRow(dataBase.SUB_SECTOR_TABLE, subSectors[map[i][1]]);
            subID = sub.getInt(dataBase.COL_ROWID);

            dataBase.insertSectorSubSector(sectorID, subID);

        }
    }
}
