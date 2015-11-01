package edu.byui.cs246.project;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static java.lang.System.exit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void clickLoad(View v){
        //remain on page, enable options
        showOtherOptions();

        //Testing code
        DataBase db = new DataBase(this);
        db.open();

        Cursor c = db.getAllRows();

        if(c.moveToFirst()){
            int id = c.getInt(0);
            String text = c.getString(1);
            String answer = c.getString(2);

            TextView textBox = (TextView) findViewById(R.id.textView);
            textBox.setText("id: " + id + ";  Question: " + text + "\n Answer: " + answer);
        }

        c.close();
        db.close();


    }

    public void clickNew(View v){
        //go to a new page
        showOtherOptions();

        //Testing code
        DataBase db = new DataBase(this);
        db.open();

        db.insertRow("My first Question", "U");

        db.close();
    }

    public void clickQuit(View v){
        exit(1);
    }

    private void showOtherOptions(){
        Button a = (Button) findViewById(R.id.demographics_b);
        a.setVisibility(View.VISIBLE);
        Button b = (Button) findViewById(R.id.questions_b);
        b.setVisibility(View.VISIBLE);
        Button c = (Button) findViewById(R.id.analysis_b);
        c.setVisibility(View.VISIBLE);
    }
}
