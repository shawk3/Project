package edu.byui.cs246.project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
    }

    public void clickNew(View v){
        //go to a new page
        showOtherOptions();
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
