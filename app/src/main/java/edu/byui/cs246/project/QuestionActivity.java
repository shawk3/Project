package edu.byui.cs246.project;

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
    String[] questions;
    int index;
    int[] answers;
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
        if (index > 0){
            saveAnswer();
            index--;
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
        if (index < 2){
            saveAnswer();
            index++;
            displayQuestion();
        }
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
        /*switch(((RadioGroup) findViewById(R.id.answerButtons)).getCheckedRadioButtonId()){
            case R.id.radioButtonYes:
                answers[index] = 3;
                break;
            case R.id.radioButtonNo:
                answers[index] = 2;
                break;
            case R.id.radioButtonNA:
                answers[index] = 1;
                break;
            default:
        }*/
    }

    /***************************************************
     * Retieve Questions
     * -Retieves Question set from its source.
    **************************************************/
    private void retieveQuestions(){
        /*
        questions = new String[]{"What is your name?", "What is your quest?", "What is the airspeed velocity of a swallow?"};
        index = 0;
        answers = new int[]{0,0,0};
        */
        db = new DataBase (this);
        db.open();
        db.insertRow("What is your name?", "U");
        db.insertRow("What is your quest?", "U");
        db.insertRow("What is the airspeed velocity of a swallow?", "U");
        index = 1;
    }

    /***************************************************
     * Display Question
     * -Displays the current question
    ***************************************************/
    private void displayQuestion(){
        ((TextView) findViewById(R.id.QuestionText)).setText("Question " + String.valueOf(index + 1) + ":\n" + (db.getRow(index)).getString(1));
        /*switch(answers[index]){
            case 3:
                ((RadioGroup) findViewById(R.id.answerButtons)).check(R.id.radioButtonYes);
                break;
            case 2:
                ((RadioGroup) findViewById(R.id.answerButtons)).check(R.id.radioButtonNo);
                break;
            case 1:
                ((RadioGroup) findViewById(R.id.answerButtons)).check(R.id.radioButtonNA);
                break;
            case 0:
                ((RadioGroup) findViewById(R.id.answerButtons)).clearCheck();
                break;
        }*/
    }


    //TESTS

    //contains tests for the question activity
    public class QuestionTests extends InstrumentationTestCase {

        public void testNextQuestionIndexOverflow() throws Exception{
            QuestionActivity toTest = new QuestionActivity();
            for (int i = 0; i < 1000; i++)
                 toTest.nextQuestion();
            assert(toTest.index <= 2);
        }
        public void testPrevQuestionIndexOverflow() throws Exception {
            QuestionActivity toTest = new QuestionActivity();
            for (int i = 0; i < 1000; i++)
                toTest.prevQuestion();
            assert (toTest.index >= 0);
        }
    }

}
