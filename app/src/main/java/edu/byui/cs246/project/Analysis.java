package edu.byui.cs246.project;

import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.TextView;

import org.w3c.dom.Text;

public class Analysis extends AppCompatActivity {
    SharedPreferences settings;
    int sessionID;
    LinearLayout la;
    LinearLayout key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getApplicationInfo().targetSdkVersion = 14; // To disable the 3-dot menu
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Analysis");
        settings = getSharedPreferences("settingsFile", 0);
        sessionID = settings.getInt("Session", 0);

        la = (LinearLayout)findViewById(R.id.lchart);
        key = (LinearLayout)findViewById(R.id.key);

        int color[] = {Color.GREEN,Color.RED,Color.YELLOW, Color.BLUE};

        int height[] = getHeights();

        for(int j = 0; j < height.length; j++){

            drawChart(1,color[j],height[j]);
        }

        drawKey();
        drawPercent(height);
    }

    private int[] getHeights() {
        int heights[] = new int[4];
        DataBase db = new DataBase(this);
        db.open();
        Cursor c = db.getAllAnswers(sessionID);
        if(c.moveToFirst()){
            do {
                String ans = c.getString(db.COL_QUESTION_ANSWER);
                switch (ans) {
                    case "Y":
                        heights[0] += 1;
                        break;
                    case "N":
                        heights[1] += 1;
                        break;
                    case "NA":
                        heights[2] += 1;
                        break;
                    default:
                        heights[3] += 1;
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
        //System.out.println(count+color+height);
        //color = Color.BLUE;

        View view = new View(this);
        view.setBackgroundColor(color);
        view.setLayoutParams(new LinearLayout.LayoutParams(100, height*100));

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)(view.getLayoutParams());

        params.setMargins(90,0,0,0);
        view.setLayoutParams(params);

        la.addView(view);
    }

    private void drawKey(){
        key.addView(smallView(Color.GREEN));
        key.addView(textKey("Yes", 100));
        key.addView(smallView(Color.RED));
        key.addView(textKey("No", 75));
        key.addView(smallView(Color.YELLOW));
        key.addView((textKey("N/A", 100)));
        key.addView(smallView(Color.BLUE));
        key.addView(textKey("UnAnswered", 250));


    }


    private void drawPercent(int height[]) {
        int percent;
        if((height[0] + height[1]) > 0)
            percent = (100 * height[0]) / (height[0] + height[1]);
        else
            percent = 0;
        TextView percentText = (TextView) findViewById(R.id.percentText);

        if (percent >= 85)
            percentText.setTextColor(Color.GREEN);
        else if (percent >= 70)
            percentText.setTextColor(Color.YELLOW);
        else
            percentText.setTextColor(Color.RED);

        percentText.setText(String.valueOf(percent) + " %");
    }

    private TextView textKey(String text, int size){
        TextView view = new TextView(this);
        view.setText(text);

        view.setLayoutParams(new LinearLayout.LayoutParams(size, 50));
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)(view.getLayoutParams());
        params.setMargins(30, 0, 10, 0);
        view.setLayoutParams(params);



        return view;
    }

    private View smallView(int color){
        View view = new View(this);
        view.setLayoutParams(new LinearLayout.LayoutParams(20, 20));
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)(view.getLayoutParams());
        params.setMargins(30, 0, 0, 10);
        view.setLayoutParams(params);

        view.setBackgroundColor(color);

        return view;
    }
}