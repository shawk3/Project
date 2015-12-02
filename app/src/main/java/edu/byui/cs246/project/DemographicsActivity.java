package edu.byui.cs246.project;

import android.app.Fragment;
import android.app.ListActivity;
import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;

public class DemographicsActivity extends AppCompatActivity implements ItemFragment.OnFragmentInteractionListener {

    ItemFragment listFrag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demographic);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Sector");


        /*String[] demographics = {"Chemical", "Commercial Facilities", "Communications",
                "Critical Manufacturing", "Dams", "Defense Industrial Base", "Emergency Services",
                "Energy", "Financial Services", "Food and Agriculture", "Government Facilities",
                "Healthcare and Public Health", "Information Technology", "Nuclear Reactors, Materials, and Waste",
                "Sector-Specific Agencies", "Transportation Systems", "Water and Wastewater Systems"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getListView().getContext(),
                android.R.layout.simple_list_item_1, demographics);
        getListView().setAdapter(adapter);
        */
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
}
