package edu.byui.cs246.project;

import android.content.SharedPreferences;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Kyle on 12/10/2015.
 */
public class DemographicsData {
    DataBase db;
    int session;

    public DemographicsData (DataBase dataBase, int sessionID){
        db = dataBase;
        db.open();
        session = sessionID;
    }

    public Cursor getMap(){
        Cursor s = db.getRow(db.SESSION_TABLE, session);
        if(s != null){
            int sssID = s.getInt(db.COL_SECTOR_SUB_SECTOR_ID);
            Cursor m = db.getRow(db.SECTOR_SUB_SECTOR_TABLE, sssID);
            return m;
        }
        return s;
    }



    public String getSectorName(){
        String name = "Default";
        Cursor c = getMap();

        if(c != null){
            int sid = c.getInt(db.COL_SECTOR_ID);
            name = db.getRow(db.SECTOR_TABLE, sid).getString(db.COL_SECTOR);
        }

        return name;
    }

    public String getSubSectorName(){
        String name = "Default";
        Cursor c = getMap();

            if(c != null) {
                int subid = c.getInt(db.COL_SUB_SECTOR_ID);
                name = db.getRow(db.SUB_SECTOR_TABLE, subid).getString(db.COL_SUB_SECTOR);
            }

        return name;
    }

    public void updateSession(String sector, String subSector){
        int SID = db.getRow(db.SECTOR_TABLE, sector).getInt(db.COL_ROWID);
        int SUBID = db.getRow(db.SUB_SECTOR_TABLE, subSector).getInt(db.COL_ROWID);
        db.updateSession(session, "today", db.getSectorSubSectorMap(SID, SUBID).getInt(db.COL_ROWID));
    }

    public HashMap<String, List<String>> createMapList() {
        HashMap<String, List<String>> Sectors_And_Subs = new HashMap<String, List<String>>();

        Cursor sect = db.getAllRows(db.SECTOR_TABLE);
        sect.moveToFirst();

        do{
            String sectorName = sect.getString(db.COL_SECTOR);
            List<String> subList = new ArrayList<String>();

            if(!sectorName.equals("Default")) {
                int Sid = sect.getInt(db.COL_ROWID);
                Cursor subSIDs = db.getSubSectors(Sid);


                if (subSIDs != null) {
                    subSIDs.moveToFirst();
                    do {
                        int subId = subSIDs.getInt(db.COL_SUB_SECTOR_ID);
                        Cursor sub = db.getRow(db.SUB_SECTOR_TABLE, subId);
                        String subSectorName = sub.getString(db.COL_SUB_SECTOR);
                        if(subSectorName.equals("Default"))
                            subSectorName = "No Sub-Sectors listed for this Sector";
                        subList.add(subSectorName);
                    } while (subSIDs.moveToNext());
                }


                Sectors_And_Subs.put(sectorName, subList);
            }
        } while(sect.moveToNext());

        return Sectors_And_Subs;
    }

}
