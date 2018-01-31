package com.example.lee.androidlabs;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class ListItemsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_items);
        Log.i("ListItemsActivity", "in onCreate()");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("ListItemsActivity", "in onResume()");

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("ListItemsActivity", "in onStart()");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("ListItemsActivity", "in onPause()");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("ListItemsActivity", "in onStop()");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("ListItemsActivity", "in onDestroy()");

    }
}
