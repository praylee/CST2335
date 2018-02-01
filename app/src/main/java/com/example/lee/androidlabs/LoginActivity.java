package com.example.lee.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class LoginActivity extends Activity {
    public static final String prefDefaultEmail = "DefaultEmail";
    SharedPreferences preferences;
    Button loginButton;
    TextView editLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        preferences = getSharedPreferences(prefDefaultEmail, Context.MODE_PRIVATE);
        loginButton= (Button)(findViewById(R.id.buttonLogin));
        editLogin = (TextView) (findViewById(R.id.editLogin));
        Log.i("LoginActivity", "in onCreate()");
        String loginName = preferences.getString(prefDefaultEmail,"prayleee@gmail.com");
        editLogin.setText(loginName);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(prefDefaultEmail,editLogin.getText().toString());
                editor.commit();
                Intent intent = new Intent(LoginActivity.this, StartActivity.class);
                startActivity(intent);
            }

        });

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
