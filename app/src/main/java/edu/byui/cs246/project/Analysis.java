package edu.byui.cs246.project;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

public class Analysis extends AppCompatActivity {

    LinearLayout la;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getApplicationInfo().targetSdkVersion = 14; // To disable the 3-dot menu
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Analysis");

        la = (LinearLayout)findViewById(R.id.lchart);

        int color[] = {Color.GREEN,Color.RED,Color.YELLOW, Color.BLUE};

        int height[] = getHeights();

        for(int j = 0; j < height.length; j++){

            drawChart(1,color[j],height[j]);
        }
    }

    private int[] getHeights() {
        int heights[] = new int[4];
        DataBase db = new DataBase(this);
        db.open();
        Cursor c = db.getAllRows();
        if(c.moveToFirst()){
            do {
                String ans = c.getString(db.COL_QUESTION_ANSWER);
                switch (ans) {
                    case "Y":
                        heights[0] += 100;
                        break;
                    case "N":
                        heights[1] += 100;
                        break;
                    case "NA":
                        heights[2] += 100;
                        break;
                    default:
                        heights[3] += 100;
                }
            }while(c.moveToNext());
        }
        return heights;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        menu.findItem(R.id.action_analysis).setEnabled(false);
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

    private void drawChart(int count, int color, int height) {
        System.out.println(count+color+height);
        //color = Color.BLUE;

        View view = new View(this);
        view.setBackgroundColor(color);
        view.setLayoutParams(new LinearLayout.LayoutParams(30, height));

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)(view.getLayoutParams());

        params.setMargins(3,0,0,0);
        view.setLayoutParams(params);

        la.addView(view);
    }
}
