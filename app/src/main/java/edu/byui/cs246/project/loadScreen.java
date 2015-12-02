package edu.byui.cs246.project;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.w3c.dom.Text;

public class loadScreen extends AppCompatActivity {
    DataBase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_screen);

        db = new DataBase(this);
        db.open();

        Cursor c = db.getAllRows(db.SESSION_TABLE);
        TableLayout table = (TableLayout) findViewById(R.id.table);

        if(c.getCount() == 0){
            TableRow tR = new TableRow(this);

            TextView text = new TextView(this);
            text.setText("There are no previous files");

            tR.addView(text);

            table.addView(tR);
            //display text "no previous files"
        }
        else{
            c.moveToFirst();
            do{
                TableRow tR = new TableRow(this);
                tR.setPadding(5, 5, 5, 5);

                TextView text = new TextView(this);
                text.setText(c.getString(db.COL_SESSION_NAME));

                CheckBox box = new CheckBox(this);

                tR.addView(text);
                tR.addView(box);
    
                table.addView(tR);
            } while(c.moveToNext());
        }

    }
}
