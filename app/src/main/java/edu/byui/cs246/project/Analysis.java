package edu.byui.cs246.project;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class Analysis extends AppCompatActivity {

    LinearLayout la;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis);

        la = (LinearLayout)findViewById(R.id.lchart);

        int color[] = {2,2,2, 1,1,3,3,3,3};

        int Height[] ={200,200,200,100,300,300,200,100,100};

        for(int j = 0; j < color.length; j++){

            drawChart(1,color[j],Height[j]);
        }
    }

    private void drawChart(int count, int color, int height) {
        System.out.println(count+color+height);
        color = Color.BLUE;

        View view = new View(this);
        view.setBackgroundColor(color);
        view.setLayoutParams(new LinearLayout.LayoutParams(30, height));

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)(view.getLayoutParams());

        params.setMargins(3,0,0,0);
        view.setLayoutParams(params);

        la.addView(view);
    }
}
