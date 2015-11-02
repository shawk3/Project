package edu.byui.cs246.project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startActivity(new Intent(getApplicationContext(), QuestionActivity.class));//TEST CODE

    }

    public void Hello(){
        //Hello;
        //My first change
    }

    public void Hello2(){
        //Hello2;
        //Jason's hello
    }
    
    public void AdamsHello(){
        //This is the Hello
        //I dont think so
    }
}
