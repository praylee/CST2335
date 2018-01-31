package com.example.lee.androidlabs;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class StartActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Log.i("StartActivity", "in onCreate()");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("StartActivity", "in onResume()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("StartActivity", "in onStart()");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("StartActivity", "in onPause()");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("StartActivity", "in onStop()");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("StartActivity", "in onDestroy()");

    }
}
