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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jason
 * @since 2015-11
 */
public class DemographicsActivity extends AppCompatActivity implements ItemFragment.OnFragmentInteractionListener {

    DataBase db;
    SharedPreferences settings;
    int sessionID;
    String[] sectors = {"Chemical", "Commercial Facilities", "Communications",
            "Critical Manufacturing", "Dams", "Defense Industrial Base", "Emergency Services",
            "Energy", "Financial Services", "Food and Agriculture", "Government Facilities",
            "Healthcare and Public Health", "Information Technology", "Nuclear Reactors, Materials, and Waste",
            "Sector-Specific Agencies", "Transportation Systems", "Water and Wastewater Systems"};

    ListView sectorList;
    ArrayAdapter<String> adapter;


    ItemFragment listFrag;
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



        sectorList = (ListView) findViewById(R.id.sectorList);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, sectors);
        sectorList.setAdapter(adapter);
        sectorList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.setSelected(true);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        menu.findItem(R.id.action_demographics).setEnabled(false);
        return true;
    }

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

    @Override
    public void onFragmentInteraction(String id) {

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
}
