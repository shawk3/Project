package edu.byui.cs246.project;

import android.app.ListActivity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;

public class DemographicsActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_demographics);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        String[] demographics = {"Chemical", "Commercial Facilities", "Communications",
                "Critical Manufacturing", "Dams", "Defense Industrial Base", "Emergency Services",
                "Energy", "Financial Services", "Food and Agriculture", "Government Facilities",
                "Healthcare and Public Health", "Information Technology", "Nuclear Reactors, Materials, and Waste",
                "Sector-Specific Agencies", "Transportation Systems", "Water and Wastewater Systems"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getListView().getContext(),
                android.R.layout.simple_list_item_1, demographics);
        getListView().setAdapter(adapter);
    }


}
