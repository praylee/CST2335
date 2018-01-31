package com.example.lee.androidlabs;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

public class LoginActivity extends Activity {

    Button button= (Button)(findViewById(R.id.imageButton2));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.i("LoginActivity", "in onCreate()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("LoginActivity", "in onResume()");

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("LoginActivity", "in onStart()");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("LoginActivity", "in onPause()");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("LoginActivity", "in onStop()");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("LoginActivity", "in onDestroy()");

    }
}
