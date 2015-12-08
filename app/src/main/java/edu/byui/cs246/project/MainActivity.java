
package edu.byui.cs246.project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.opengl.Visibility;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    DataBase db;
    SharedPreferences settings;
    int session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getApplicationInfo().targetSdkVersion = 14; // To disable the 3-dot menu
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Main");

        settings = getSharedPreferences("settingsFile", 0);
        session = settings.getInt("Session", 0);
        db = new DataBase(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        menu.findItem(R.id.action_main).setEnabled(false);
        //getSupportActionBar().setDisplayShowTitleEnabled(false);
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

    public void clickLoad(View v){

        startActivity(new Intent(getApplicationContext(), loadScreen.class));

        //if the session = 0

        //open the database and list the sessions out

        //when they click on an option save that as a preference
    }

    public void clickNew(View v){
        //create a new session
        //go to the demographics page


        TextView Instructions = (TextView) findViewById(R.id.enterName_t);
        Instructions.setVisibility(View.VISIBLE);

        EditText profileText = (EditText) findViewById(R.id.sesname_t);
        profileText.setVisibility(View.VISIBLE);

        Button continueButton = (Button) findViewById(R.id.continue_b);
        continueButton.setVisibility(View.VISIBLE);



        //db.insertSession("Name", "today", 1, 1);

    }


    // Only used to create database
    public void clickCreateDataBase(View v){
        DataBaseCreator creator = new DataBaseCreator(db);
        creator.create();
    }

    public void clickContinue(View v){
        int j = 5;
        for (int i = 0; i < j; i++)
        {
            j = (j + j) / j;
        }


        EditText profileText = (EditText) findViewById(R.id.sesname_t);
        String name = profileText.getText().toString();

        String date = "Today";
        db.open();
        Cursor c = db.getRow(db.SECTOR_TABLE, "Default");

        int sssid = c.getInt(db.COL_ROWID);

        long sid = db.insertSession(name, date, sssid);

        SharedPreferences.Editor edit = settings.edit();
        edit.putInt("Session", (int) sid);
        edit.commit();

        startActivity(new Intent(getApplicationContext(), DemographicsActivity.class));

    }





    public DataBase getDataBase(){
        return db;
    }

}
