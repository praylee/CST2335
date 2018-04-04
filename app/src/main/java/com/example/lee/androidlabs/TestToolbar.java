package com.example.lee.androidlabs;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;


public class TestToolbar extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_toolbar);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setActionBar(toolbar);

        FloatingActionButton floatingActionButton = (FloatingActionButton)findViewById(R.id.floatingActionButton);
    }

    public boolean onCreateOptionsMenu(Menu m){
        getMenuInflater().inflate(R.menu.toolbar_menu, m );
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem mi){
        switch (mi.getItemId()){
            case R.id.action_one:
                Log.d("Toolbar", "Option 1 selected");
                break;
            case R.id.action_two:
                Log.d("Toolbar", "Option 1 selected");
                break;
            case R.id.action_three:
                Log.d("Toolbar", "Option 1 selected");
                break;
            case R.id.about:
                CharSequence text = "Version 1.0 by Peng Li";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(this , text, duration);
                toast.show();
        }
        return true;
    }
}
