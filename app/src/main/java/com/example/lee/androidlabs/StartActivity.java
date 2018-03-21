package com.example.lee.androidlabs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class StartActivity extends Activity {
    Button button;
    Button button_StartChat;
    Button button_StartWeatherForecast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Log.i("StartActivity", "in onCreate()");
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartActivity.this, ListItemsActivity.class);
                startActivityForResult(intent,50);
            }
        });
        button_StartChat = findViewById(R.id.startChat_Button);
        button_StartChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("StartActivity", "User clicked Start Chat");
                Intent intent = new Intent(StartActivity.this, ChatWindow.class);
                startActivity(intent);
            }
        });
        button_StartWeatherForecast = findViewById(R.id.start_Weather_Forecast);
        button_StartWeatherForecast.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Log.i(this.getClass().getSimpleName(), "User Clicked Start Weather Forecast");
                Intent intent = new Intent(StartActivity.this,WeatherForecast.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected  void onActivityResult(int requestCode, int responseCode, Intent data){
        if(requestCode==50){
            Log.i("StartActivity","Returned to StartActivity.onActivityResult");
        }
        if(responseCode==Activity.RESULT_OK){
            String messagePassed = data.getStringExtra("Response");
            Toast toast = Toast.makeText(this, messagePassed,Toast.LENGTH_SHORT);
            toast.show();
            Log.i("StartActivity",messagePassed);
        }
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
