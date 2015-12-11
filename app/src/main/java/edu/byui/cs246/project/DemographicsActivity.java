package edu.byui.cs246.project;

import android.app.Fragment;
import android.app.ListActivity;
import android.content.ClipData;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Jason
 * @since 2015-11
 */
import edu.byui.cs246.project.SectorAdapter;

public class DemographicsActivity extends AppCompatActivity {

    DataBase db;
    SharedPreferences settings;
    int sessionID;
    /*String[] sectors = {"Chemical", "Commercial Facilities", "Communications",
            "Critical Manufacturing", "Dams", "Defense Industrial Base", "Emergency Services",
            "Energy", "Financial Services", "Food and Agriculture", "Government Facilities",
            "Healthcare and Public Health", "Information Technology", "Nuclear Reactors, Materials, and Waste",
            "Sector-Specific Agencies", "Transportation Systems", "Water and Wastewater Systems"};*/


    //ArrayAdapter<String> adapter;
    HashMap<String, List<String>> Sector_And_Subs;
    List<String> Sector_List;
    ExpandableListView sectorList;
    SectorAdapter sectorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demographic);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Sector");

        db = new DataBase(this);
        db.open();

        settings = getSharedPreferences("settingsFile", 0);
        sessionID = settings.getInt("Session", 0);

        sectorList = (ExpandableListView) findViewById(R.id.sectorList);
        Sector_And_Subs = getInfo();
        Sector_List = new ArrayList<String>(Sector_And_Subs.keySet());
        sectorAdapter = new SectorAdapter(this, Sector_And_Subs, Sector_List);
        sectorList.setAdapter(sectorAdapter);

        sectorList.setOnItemClickListener(new ListView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.setSelected(true);
            }
        });
        sectorList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                v.setSelected(true);
                Toast.makeText(getBaseContext(), Sector_And_Subs.get(Sector_List.get(groupPosition)).get(childPosition) + " was clicked", Toast.LENGTH_LONG).show();
                //startActivity(new Intent(getApplicationContext(), QuestionActivity.class));
                return true;
            }
        });

      /*  sectorList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                if(Sector_List.get(groupPosition).isEmpty()){
                    startActivity(new Intent(getApplicationContext(), QuestionActivity.class));
                    return true;
                }
                return true;
            }
        });
*/
        /*sectorList = (ExpandableListView) findViewById(R.id.sectorList);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, sectors);
        sectorList.setAdapter(adapter);
        sectorList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.setSelected(true);


            }
        });
        */

    }

    /**
     * Inflates and prepares the action bar.
     * @param menu  A variable representing the action bar
     * @return      The return value for this overridden method. Not used in this application.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        menu.findItem(R.id.action_demographics).setEnabled(false);
        return true;
    }

    /**
     * Preforms actions when the action bar is clicked.
     *
     * This action bar is used for traveling between activities. Each item in the action bar menu
     * represents a different activity clicking on that item will start the desired activity.
     *
     * @param item  Represents the action bar menu item that was clicked.
     * @return      Returns true if a menu action was successfully completed.
     */
    public boolean onOptionsItemSelected (MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_main:
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                break;
            case R.id.action_demographics:
                startActivity(new Intent(getApplicationContext(), DemographicsActivity.class));
                break;
            case R.id.action_questions:
                startActivity(new Intent(getApplicationContext(), QuestionActivity.class));
                break;
            case R.id.action_analysis:
                startActivity(new Intent(getApplicationContext(), Analysis.class));
                break;
            default:
                return false;
        }
        return true;
    }

    //Example function
    public void onClick(String text){
        List<String> subSectors= new ArrayList<String>();

        Cursor SidCursor = db.getRow(db.SECTOR_TABLE, text);
        int Sid = SidCursor.getInt(db.COL_SECTOR_ID);
        Cursor c = db.getSubSectors(Sid);
        do{
            String j = c.getString(db.COL_SUB_SECTOR_ID);
            subSectors.add(j);
        }while(c.moveToNext());



    }

    public HashMap<String, List<String>> getInfo() {
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
