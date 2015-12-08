package edu.byui.cs246.project;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class loadScreen extends AppCompatActivity {
    DataBase db;
    int session = 0;
    SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_screen);

        db = new DataBase(this);
        db.open();

        settings = getSharedPreferences("settingsFile", 0);

        final Cursor c = db.getAllRows(db.SESSION_TABLE);
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

                Button button = new Button(this);
                button.setText(c.getString(db.COL_SESSION_NAME));
                button.setBackgroundResource(R.drawable.blue_button);

                button.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {

                        //String name = ((button)view).getText();
                        Button button = (Button)view;
                        String name = button.getText().toString();

                        Cursor c = db.getRow(db.SESSION_TABLE, name);
                        int s = c.getInt(db.COL_ROWID);

                        SharedPreferences.Editor edit = settings.edit();
                        edit.putInt("Session", s);
                        edit.commit();

                        System.exit(0);
                    }
                });

                tR.addView(button);

                table.addView(tR);
            } while(c.moveToNext());
        }

    }

}
