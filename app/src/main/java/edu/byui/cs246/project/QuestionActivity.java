package edu.byui.cs246.project;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.test.InstrumentationTestCase;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

public class QuestionActivity extends AppCompatActivity {
    //long index;
    Cursor index;
    //long max;
    DataBase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        this.retieveQuestions();
        this.displayQuestion();
    }
    /***************************************************
     * Retieve Questions
     * -Retieves Question set from its source.
     **************************************************/
    private void retieveQuestions(){
        db = new DataBase (this);
        db.open();
        db.deleteAll();
        db.insertRow("What is your name?", "U");
        db.insertRow("What is your quest?", "U");
        db.insertRow("What is the airspeed velocity of a swallow?", "U");
        //db.updateRow(1, "WHat is your name?", "Y");
        //db.updateRow(2, "Y");
        //db.updateRow(3, "Y");
        index = db.getAllRows();
    }

    /***************************************************
     * Previous Click
     * -Contains instructions for "<prev" click.
     ***************************************************/
    public void prevClick(View view){
        this.prevQuestion();
    }

    /***************************************************
     * Next Click
     * -Contains instructions for "next>" click.
     ***************************************************/
    public void nextClick(View view){
        this.nextQuestion();
    }
    /***************************************************
     * Previous Question
     * -Moves to the previous question in the set. Also saves
     *  answer for current question and displays new
     *  question.
     ***************************************************/
    private void prevQuestion(){
        if (!index.isFirst()){
            saveAnswer();
            index.moveToPrevious();
            displayQuestion();
        }
    }

    /***************************************************
     * Next Question
     * -Moves to the next question in the set. Also saves
     *  answer for current question and displays new
     *  question.
    ***************************************************/
    private void nextQuestion(){
        if (!index.isLast()) {
            saveAnswer();
            index.moveToNext();
            displayQuestion();
        }
        else
            ;


    }

    /***************************************************
     * Clear Display
     * -Clears the Radio group display
    ***************************************************/
    private void clearDisplay(){
        ((RadioGroup) findViewById(R.id.answerButtons)).clearCheck();
    }

    /***************************************************
     * Save Answer
     * -Saves currently selected answer
    ***************************************************/
    private void saveAnswer(){
        //detect which radio button is pressed and save the corresponding answer
        switch(((RadioGroup) findViewById(R.id.answerButtons)).getCheckedRadioButtonId()){
            case R.id.radioButtonYes:
                db.updateRow(index.getPosition() + 1, "Y");
                //((TextView) findViewById(R.id.testView)).setText(String.valueOf(index.getPosition()));
                break;
            case R.id.radioButtonNo:
                db.updateRow(index.getPosition() + 1, "N");
                break;
            case R.id.radioButtonNA:
                db.updateRow(index.getPosition() + 1, "NA");
                break;
            default:
                //((TextView) findViewById(R.id.testView)).setText(index.getString(2));
        }

    }

    /***************************************************
     * Display Question
     * -Displays the current question
    ***************************************************/
    private void displayQuestion(){
        //display current question
        ((TextView) findViewById(R.id.QuestionText)).setText("Question " + String.valueOf(index.getPosition() + 1) + ":\n" + index.getString(1));

        //display saved answer, if any
        switch(index.getString(2)){
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
            ((TextView) findViewById(R.id.prevText)).setText("<prev");
        else
            ((TextView) findViewById(R.id.prevText)).setText("");

        if(!index.isLast())
            ((TextView) findViewById(R.id.nextText)).setText("next>");
        else
            ((TextView) findViewById(R.id.nextText)).setText("Finish");

        //((TextView) findViewById(R.id.testView)).setText(index.getString(2));
    }


    //TESTS

    //contains tests for the question activity
    public class QuestionTests extends InstrumentationTestCase {

        public void testNextQuestionIndexOverflow() throws Exception{
            QuestionActivity toTest = new QuestionActivity();
            for (int i = 0; i < 1000; i++)
                 toTest.nextQuestion();
            assert(toTest.index.getPosition() <= 3);
        }
        public void testPrevQuestionIndexOverflow() throws Exception {
            QuestionActivity toTest = new QuestionActivity();
            for (int i = 0; i < 1000; i++)
                toTest.prevQuestion();
            assert (toTest.index.getPosition() >= 1);
        }
    }

}
