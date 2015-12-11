package edu.byui.cs246.project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.test.InstrumentationTestCase;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

/**
 * Is where the questionnaire is answered by the user.
 *
 * In this activity the user is presented with all the questions in the database. They can be
 * answered "true", "false", "Not applicable" (N/A) or left unanswered. These answers will also be
 * stored in the database. If the database already has answers stored in it, they will be displayed
 * along with each question. This activity is a precursor to the analysis activity.
 *
 * @author Adam Cameron
 * @since 2015-11
 */
public class QuestionActivity extends AppCompatActivity {

    /**
     * Used for the logger.
     */
    private static final String TAG = QuestionActivity.class.getSimpleName();

    /**
     * A cursor that holds that current position in the database.
     */
    Cursor index;

    /** Our DataBase */
    DataBase db;

    /** storing sessions */
    SharedPreferences settings;

    /** The id of the current session */
    int sessionID;


    /**
     * Does the work to be done at the start of the activity.
     *
     * At the start of the activity, the layout is selected. The action bar is then is created from
     * the toolbar in the layout. Data from the previous activities is accessed and the question
     * display is begun.
     *
     * @param savedInstanceState    A way of receiving data from the calling activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getApplicationInfo().targetSdkVersion = 14;                 // To disable the 3-dot menu
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Questions");

        settings = getSharedPreferences("settingsFile", 0);
        sessionID = settings.getInt("Session", 0);

        this.retieveQuestions();
        this.displayQuestion();
    }

    /**
     * Retrieves Question set from its source.
     *
     * In this case that source is the data base.
     */
    private void retieveQuestions(){
        db = new DataBase (this);
        db.open();
        index = db.getAllRows(db.QTABLE);

        //basic logging code
        if(index.moveToFirst()) {
            Log.i(TAG, "Successfully pulled from data base.");
        }
        else{
            Log.e(TAG, "Did not pull from data base.");
        }
    }

    /**
     * Contains instructions for "prev.b" button click.
     *
     * @param view  Needed for communication with the button.
     */
    public void prevClick(View view){
        this.prevQuestion();
    }


    /**
     * Contains instructions for "next.b" button click.
     *
     * @param view  Needed for communication with the button.
     */
    public void nextClick(View view){
        this.nextQuestion();
    }


    /**
     * Moves to the previous question.
     *
     * Moves to the previous question in the database. Also saves answer for current question and
     * displays new question. If it the first question in the database, it does nothing.
     */
    private void prevQuestion(){
        if (!index.isFirst()){
            saveAnswer();
            index.moveToPrevious();
            displayQuestion();
        }
    }

    /**
     * Moves to next question.
     *
     * Moves to the next question in the database. Also saves answer for current question and
     * displays new question. If it is the last question in the data base, this will instead start
     * the analysis activity.
     */
    private void nextQuestion(){
            saveAnswer();
        if (!index.isLast()) {
            index.moveToNext();
            displayQuestion();
        }
        else
            startActivity(new Intent(getApplicationContext(), Analysis.class));
    }

    /**
     * Clears the Radio group display
     */
    private void clearDisplay(){
        ((RadioGroup) findViewById(R.id.answerButtons)).clearCheck();
    }


    /**
     * Saves currently selected answer.
     *
     * Saves the currently selected answer in the database for later retrieval and use.
     */
    private void saveAnswer(){
        //detect which radio button is pressed and save the corresponding answer
        int Qid = index.getInt(db.COL_ROWID);
        //int id = db.getRow(db.QTABLE,index.getString(db.COL_QUESTION_TEXT)).getInt(db.COL_ROWID);
        switch(((RadioGroup) findViewById(R.id.answerButtons)).getCheckedRadioButtonId()){
            case R.id.radioButtonYes:
                db.insertAnswer(sessionID, Qid, "Y");
                //((TextView) findViewById(R.id.testView)).setText(String.valueOf(index.getPosition()));
                break;
            case R.id.radioButtonNo:
                db.insertAnswer(sessionID, Qid, "N");
                break;
            case R.id.radioButtonNA:
                db.insertAnswer(sessionID, Qid, "NA");
                break;
            default:
        }

    }

    /**
     * Displays the current question.
     *
     * First this activity uses the database to display the number of the current question. Next the
     * question itself is displayed. If the data base indicates that an answer has been given to
     * this question previously, the radio button corrisponding to that answer will be illuminated.
     * If it is the first question in the database, the "prev.b" button will temporarily be hidden.
     * If it is the last question in the database, the "next.b" button will change its text to
     * "Finish", in preparation for the move to the analysis activity.
     */
    private void displayQuestion(){
        //display current question
        String questionText = index.getString(db.COL_QUESTION_TEXT);
        Cursor updatedC = db.getRow(db.QTABLE, questionText);
        ((TextView) findViewById(R.id.QuestionText)).setText("Question " + String.valueOf(index.getPosition() + 1) + ":\n" + questionText);
        Cursor a = db.getAnswer(updatedC.getInt(db.COL_ROWID), sessionID);

        //used if the question is unanswered
        String ans;
        if(a == null)
            ans = "U";
        else
            ans = a.getString(db.COL_QUESTION_ANSWER);

        //display saved answer, if any
        switch(ans){
            case "Y":
                ((RadioGroup) findViewById(R.id.answerButtons)).check(R.id.radioButtonYes);
                break;
            case "N":
                ((RadioGroup) findViewById(R.id.answerButtons)).check(R.id.radioButtonNo);
                break;
            case "NA":
                ((RadioGroup) findViewById(R.id.answerButtons)).check(R.id.radioButtonNA);
                break;
            case "U":
                ((RadioGroup) findViewById(R.id.answerButtons)).clearCheck();
                break;
        }


        //set prev/next button apearance
        if(!index.isFirst())
            ((TextView) findViewById(R.id.prev_b)).setVisibility(View.VISIBLE);
        else
            ((TextView) findViewById(R.id.prev_b)).setVisibility(View.INVISIBLE);

        if(!index.isLast())
            ((TextView) findViewById(R.id.next_b)).setText("next>");
        else
            ((TextView) findViewById(R.id.next_b)).setText("Finish");

        Log.i(TAG, "The current quetion is \"" + index.getString(1) + "\".");
    }

    /**
     * @author Adam Cameron
     * @since 2015-11
     */
    public class QuestionTests extends InstrumentationTestCase {

        /**
         * Test to ensure that the nextQuestion method maintains a proper index.
         *
         * @throws Exception    Caught by the system in the case of a test fail.
         */
        public void testNextQuestionIndexOverflow() throws Exception{
            QuestionActivity toTest = new QuestionActivity();
            for (int i = 0; i < 1000; i++)
                 toTest.nextQuestion();
            assert(toTest.index.getPosition() >= 1);
        }

        /**
         * Test to ensure that the prevQuestion method maintains a proper index.
         *
         * @throws Exception    Caught by the system in the case of a test fail.
         */
        public void testPrevQuestionIndexOverflow() throws Exception {
            QuestionActivity toTest = new QuestionActivity();
            for (int i = 0; i < 1000; i++)
                toTest.prevQuestion();
            assert (toTest.index.getPosition() >= 1);
        }
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
        menu.findItem(R.id.action_questions).setEnabled(false);
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
        //save current answer beforepage switch
        saveAnswer();

        //switch to the appropriate page
        switch(item.getItemId()) {
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
