
package edu.byui.cs246.project;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import static java.lang.System.exit;

public class MainActivity extends AppCompatActivity {
    DataBase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DataBase(this);


    }

    public void clickLoad(View v){
        //remain on page, enable options


        showOtherOptions();
    }

    public void clickNew(View v){
        //go to a new page
        showOtherOptions();
    }

    public void clickQuit(View v){
        exit(1);
    }

    // Only used to create database
    public void clickCreateDataBase(View v){
        //DataBaseCreator creator = new DataBaseCreator(db);
        //creator.create();
    }

    private void showOtherOptions(){
        Button a = (Button) findViewById(R.id.demographics_b);
        a.setVisibility(View.VISIBLE);
        Button b = (Button) findViewById(R.id.questions_b);
        b.setVisibility(View.VISIBLE);
        Button c = (Button) findViewById(R.id.analysis_b);
        c.setVisibility(View.VISIBLE);
    }

    public DataBase getDataBase(){
        return db;
    }

    public void clickAnalysis(View v){
        startActivity(new Intent(getApplicationContext(), Analysis.class));//TEST CODE
    }

    public void clickQuestions(View v){
        startActivity(new Intent(getApplicationContext(), QuestionActivity.class));//TEST CODE
    }

}
