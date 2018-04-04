package com.example.lee.androidlabs;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

public class MessageDetails extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_details);
        MessageFragment mf = new MessageFragment();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        mf.setArguments(bundle);
        getFragmentManager().beginTransaction().add(R.id.message_detail_layout,mf).commit();
    }
}
