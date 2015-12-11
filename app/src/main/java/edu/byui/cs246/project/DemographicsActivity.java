package edu.byui.cs246.project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

import edu.byui.cs246.project.SectorAdapter;

/**
 * Class for selecting a sector and sub-sector
 *
 * the sector page allows the user to choose a sector and
 * sub-sector for their given profile
 *
 * @author Jason
 * @since 2015-11
 */
public class DemographicsActivity extends AppCompatActivity {

    /** The database */
    DataBase db;
    /** database helper */
    DemographicsData data;
    /** storing sessions */
    SharedPreferences settings;
    /** profile id */
    int sessionID;
    /** HashMap of Sectors with associated sub-sectors*/
    HashMap<String, List<String>> Sector_And_Subs;
    /** list of sectors */
    List<String> Sector_List;
    /** the list that sectors and sub-sectors are put into */
    ExpandableListView sectorList;
    /** adapter for ExpandableListView */
    SectorAdapter sectorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demographic);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Sector");

        /** opening the database */
        db = new DataBase(this);
        db.open();

        /** creating the session */
        settings = getSharedPreferences("settingsFile", 0);
        sessionID = settings.getInt("Session", 0);

        /** retrieving data from DemographicsData */
        data = new DemographicsData(db, sessionID);

        /** declaration of certain class variables */
        sectorList = (ExpandableListView) findViewById(R.id.sectorList);
        Sector_And_Subs = data.createMapList();
        Sector_List = new ArrayList<String>(Sector_And_Subs.keySet());
        sectorAdapter = new SectorAdapter(this, Sector_And_Subs, Sector_List);

        /** setting adapter to its default state */
        sectorAdapter.setDefault(data.getSectorName(), data.getSubSectorName());
        sectorList.setAdapter(sectorAdapter);
        int g = sectorAdapter.getDefaultGroup();
        if(g >= 0)
            sectorList.expandGroup(g);

        /** what happens when a sub-sector is clicked */
        sectorList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                v.setSelected(true);
                Toast.makeText(getBaseContext(), Sector_And_Subs.get(Sector_List.get(groupPosition)).get(childPosition) + " was clicked", Toast.LENGTH_LONG).show();
                String sid = sectorAdapter.getGroup(groupPosition).toString();
                String subID = sectorAdapter.getChild(groupPosition, childPosition).toString();
                data.updateSession(sid, subID);

                return true;
            }
        });
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
}
