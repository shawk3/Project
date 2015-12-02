
package edu.byui.cs246.project;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import static java.lang.System.exit;

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
        SharedPreferences.Editor edit = settings.edit();
        edit.putInt("Session", 0);
        edit.commit();
        //db.insertSession("Name", "today", 1, 1);
        startActivity(new Intent(getApplicationContext(), DemographicsActivity.class));

    }


    // Only used to create database
    public void clickCreateDataBase(View v){
        DataBaseCreator creator = new DataBaseCreator(db);
        creator.create();
    }

    public void setSession(int sess){

    }



    public DataBase getDataBase(){
        return db;
    }

}
