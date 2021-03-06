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

/**
 * Show graphs
 *
 * This Activity shows graphs describing how the User has performed in their assessment
 *
 * @author Kyle
 * @since 2015-11
 */
public class Analysis extends AppCompatActivity {
    SharedPreferences settings;
    int sessionID;

    /** The linear layout of the bars for the bar chart*/
    LinearLayout la;
    /** The layout for the graph key */
    LinearLayout key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getApplicationInfo().targetSdkVersion = 14; // To disable the 3-dot menu
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Analysis");

        //get the current session
        settings = getSharedPreferences("settingsFile", 0);
        sessionID = settings.getInt("Session", 0);

        //set views
        la = (LinearLayout)findViewById(R.id.lchart);
        key = (LinearLayout)findViewById(R.id.key);

        //Colors for the bars in bar chart
        int drawableId[] = {R.drawable.analysis_chart_green,
                            R.drawable.analysis_chart_red,
                            R.drawable.analysis_chart_yellow,
                            R.drawable.analysis_chart_blue};

        //Heights of the bar chart, to be calculated off of counts
        int height[] = getHeights();

        //draw the graph
        for(int j = 0; j < height.length; j++){

            drawChart(drawableId[j],height[j]);
        }

        drawKey();
        drawPercent(height);
    }

    /**
     * Get the counts of each answer of this session
     *
     * Loops through the dataTable and counts how many of each answer there are. It also counts
     * how many questions there are so that it can calculate how many unanswered questions there
     * are.
     *
     * @return The counts of Yes, no,... answers there are.
     */
    private int[] getHeights() {
        int heights[] = new int[4];
        DataBase db = new DataBase(this);
        db.open();
        Cursor c = db.getAllAnswers(sessionID);
        Cursor q = db.getAllRows(db.QTABLE);
        heights[3] = q.getCount();
        if(c != null && c.moveToFirst()){
            do {
                String ans = c.getString(db.COL_QUESTION_ANSWER);
                switch (ans) {
                    case "Y":
                        heights[0] += 1;
                        heights[3] -= 1;
                        break;
                    case "N":
                        heights[1] += 1;
                        heights[3] -= 1;
                        break;
                    case "NA":
                        heights[2] += 1;
                        heights[3] -= 1;
                        break;
                    default:
                }
            }while(c.moveToNext());
        }

        return heights;
    }

    /**
     * Draw a rectangle
     *
     * Create a rectangle of the appropriate height and color for the bar graph
     *
     *
     * @param drawableId
     * @param height The Count of questions with the same answer, used to calculate height
     */
    private void drawChart(int drawableId, int height) {
        //System.out.println(count+color+height);
        //color = Color.BLUE;

        View view = new View(this);
        view.setLayoutParams(new LinearLayout.LayoutParams(100, height * 40));

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)(view.getLayoutParams());

        params.setMargins(90, 0, 0, 0);
        view.setLayoutParams(params);
        view.setBackgroundResource(drawableId);

        la.addView(view);
    }

    /**
     * Draw the key to our graph
     */
    private void drawKey(){
        key.addView(smallView(Color.GREEN,100));
        key.addView(textKey("Yes", 100));
        key.addView(smallView(Color.RED, 30));
        key.addView(textKey("No", 75));
        key.addView(smallView(Color.YELLOW, 30));
        key.addView((textKey("N/A", 100)));
        key.addView(smallView(Color.BLUE, 30));
        key.addView(textKey("UnAnswered", 250));
    }

    /**
     * Calculate and display the percentage of passed questions the User achieved.
     *
     * @param height A list of [Yes, No, NA, U] counts
     */
    private void drawPercent(int height[]) {
        int percent;
        if((height[0] + height[1] + height[3]) != 0)
            percent = (100 * height[0]) / (height[0] + height[1] + height[3]);
        else
            percent = 0;

        TextView percentText = (TextView) findViewById(R.id.percentText);

        if (percent >= 85)
            percentText.setTextColor(Color.GREEN);
        else if (percent >= 70)
            percentText.setTextColor(Color.YELLOW);
        else
            percentText.setTextColor(Color.RED);

        percentText.setText(String.valueOf(percent) + "% Passed");
    }

    /**
     * Create a Key text view.
     *
     * This view will be used in the key as descriptive text. It will sit next to a little colored
     * box
     *
     * @param text The text to insert
     * @param size The length of the text
     * @return A View
     */
    private TextView textKey(String text, int size){
        TextView view = new TextView(this);
        view.setText(text);

        view.setLayoutParams(new LinearLayout.LayoutParams(size, 50));
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)(view.getLayoutParams());
        params.setMargins(30, 0, 10, 0);
        view.setLayoutParams(params);

        return view;
    }

    /**
     * Create a little box of a specific color to be inserted in the key
     *
     * @param color The color the box will be
     * @param leftMargin The Left Margin size
     * @return A little box View
     */
    private View smallView(int color, int leftMargin){
        View view = new View(this);
        view.setLayoutParams(new LinearLayout.LayoutParams(20, 20));
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)(view.getLayoutParams());
        params.setMargins(leftMargin, 0, 0, 10);
        view.setLayoutParams(params);

        view.setBackgroundColor(color);

        return view;
    }


    /**
     * Inflates and prepares the action bar.
     * @param menu  A variable representing the action bar
     * @return      The return value for this overridden method. Not used in this application.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        menu.findItem(R.id.action_analysis).setEnabled(false);
        //getSupportActionBar().setDisplayShowTitleEnabled(false);
        return true;
    }

    /**
     * Preforms actions when the action bar is clicked.
     *
     * This action bar is used for traveling between activities. Each item in the action bar menu
     * represents a different activity clicking on that item will start the desired activity.
     *
     * @param item  Represents the action bar menu item that was clicked.
     * @return      Returns true if a menu action was successfully completed.
     */
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

}