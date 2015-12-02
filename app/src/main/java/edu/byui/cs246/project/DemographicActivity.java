package edu.byui.cs246.project;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class DemographicActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    String[] demographics = {"Chemical and a few things to see how it works", "Commercial Facilities",
            "Communications", "Critical Manufacturing", "Dams", "Defense Industrial Base",
            "Emergency Services", "Energy", "Financial Services", "Food and Agriculture",
            "Government Facilities", "Healthcare and Public Health", "Information Technology",
            "Nuclear Reactors, Materials, and Waste", "Sector-Specific Agencies", "Transportation",
            "Water and Wastewater Systems"};

    ListView l;
    EditText e; 

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demographic);
        l = (ListView) findViewById(R.id.view);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, demographics);
        l.setAdapter(adapter);
        e = (EditText) findViewById(R.id.text);
        l.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TextView temp = (TextView)view;
        Toast.makeText(this,temp.getText(), Toast.LENGTH_SHORT).show();
    }
}
