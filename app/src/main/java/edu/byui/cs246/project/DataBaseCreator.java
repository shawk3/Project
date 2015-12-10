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
 * @author Kyle
 * @since 2015-11-7
 */
public class DataBaseCreator {

    /*public static HashMap<String, List<String>> getInfo() {
        HashMap<String, List<String>> Sectors_And_Subs = new HashMap<String, List<String>>();

        List<String> Chemical = new ArrayList<String>();
        Chemical.add("Basic");
        Chemical.add("Specialty");
        Chemical.add("Agricultural");
        Chemical.add("Pharmaceuticals");
        Chemical.add("Consumer");

        List<String> ComFac = new ArrayList<String>();
        ComFac.add("Entertainment & Media");
        ComFac.add("Gaming");
        ComFac.add("Lodging");
        ComFac.add("Outdoor Events");
        ComFac.add("Public Assembly");
        ComFac.add("Real Estate");
        ComFac.add("Retail");
        ComFac.add("Sports Leagues");

        List<String> Communications = new ArrayList<String>();

        List<String> CritMan = new ArrayList<String>();
        CritMan.add("Primary Metal");
        CritMan.add("Machinery");
        CritMan.add("Electrical Equipment");
        CritMan.add("Transportation Equipment");

        List<String> Dams = new ArrayList<String>();

        List<String> DIB = new ArrayList<String>();

        List<String> EmerSer = new ArrayList<String>();
        EmerSer.add("Law Enforcement");
        EmerSer.add("Fire & Emergency Services");
        EmerSer.add("Emergency Management");
        EmerSer.add("Emergency Medical Services");
        EmerSer.add("Public Works");

        List<String> Energy = new ArrayList<String>();
        Energy.add("Electricity");
        Energy.add("Petroleum");
        Energy.add("Natural Gas");

        List<String> FiSi = new ArrayList<String>();

        List<String> FAA = new ArrayList<String>();

        List<String> GovFac = new ArrayList<String>();
        GovFac.add("Education Facilities");
        GovFac.add("National Monuments");

        List<String> Health = new ArrayList<String>();
        List<String> Info = new ArrayList<String>();
        List<String> NMW = new ArrayList<String>();

        List<String> TranSys = new ArrayList<String>();
        TranSys.add("Aviation");
        TranSys.add("Highway Infrastructure");
        TranSys.add("Maritime Transportation");
        TranSys.add("Mass Transit");
        TranSys.add("Pipeline Systems");
        TranSys.add("Freight Rail");
        TranSys.add("Postal and Shipping");

        List<String> Water = new ArrayList<String>();

        Sectors_And_Subs.put("Chemical", Chemical);
        Sectors_And_Subs.put("Commercial Facilities", ComFac);
        Sectors_And_Subs.put("Communications", Communications);
        Sectors_And_Subs.put("Critical Manufacturing", CritMan);
        Sectors_And_Subs.put("Dams", Dams);
        Sectors_And_Subs.put("Defense Industrial Base", DIB);
        Sectors_And_Subs.put("Emergency Services", EmerSer);
        Sectors_And_Subs.put("Energy", Energy);
        Sectors_And_Subs.put("Financial Services", FiSi);
        Sectors_And_Subs.put("Food and Agriculture", FAA);
        Sectors_And_Subs.put("Government Facilites", GovFac);
        Sectors_And_Subs.put("Healthcare and Public Health", Health);
        Sectors_And_Subs.put("Information Technology", Info);
        Sectors_And_Subs.put("Nuclear Reactors, Materials, and Waste", NMW);
        Sectors_And_Subs.put("Transportaion Systems", TranSys);
        Sectors_And_Subs.put("Water and Wasterwater Systems", Water);

        return Sectors_And_Subs;
    }*/

    String[] demographics = {"Default", "Chemical", "Commercial Facilities", "Communications",
            "Critical Manufacturing", "Dams", "Defense Industrial Base", "Emergency Services",
            "Energy", "Financial Services", "Food and Agriculture", "Government Facilities",
            "Healthcare and Public Health", "Information Technology", "Nuclear Reactors, Materials, and Waste",
            "Transportation Systems", "Water and Wastewater Systems"};

    String[] subSectors = { "Default", "Basic", "Specialty", "Agricultural", "Pharmaceuticals", "Consumer", "Entertainment and Media",
                        "Gaming", "Lodging", "Outdoor Events", "Public Assembly", "Real Estate", "Retail", "Sports Leagues",
                        "Primary Metal", "Machinery", "Electrical Equipment", "Transportation Equipment",
                        "Law Enforcement", "Fire and Emergency Services", "Emergency Management", "Emergency Medical Services", "Public Works",
                        "Electricity", "Petroleum", "Natural Gas", "Education Facilities", "National Monuments", "Aviation",
                        "Highway Infrastructure", "Maritime Transportation", "Mass Transit", "Pipeline Systems", "Freight Rail", "Postal and Shipping",
                        "Private Sector", "Public Sector"};

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

    Context context;
    DataBase dataBase;

    public DataBaseCreator(DataBase c){
        //context = c;
        dataBase = c;


    }

    /**
     * create a new instance of the data base
     */
    public void create(){
        //DataBase dataBase = new DataBase(context);
        dataBase.open();

        //dataBase.deleteAll();
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
        createSessions();
        dataBase.close();


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

        for(int i = 0; i < map.length; i++){
            sect = dataBase.getRow(dataBase.SECTOR_TABLE, demographics[map[i][0]]);
            sectorID = sect.getInt(dataBase.COL_ROWID);
            sub = dataBase.getRow(dataBase.SUB_SECTOR_TABLE, subSectors[map[i][1]]);
            subID = sub.getInt(dataBase.COL_ROWID);

            dataBase.insertSectorSubSector(sectorID, subID);

        }
    }

    private void createSessions(){
        dataBase.insertSession("Test Assessment", "today", 1);
    }
}
