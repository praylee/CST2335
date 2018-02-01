package com.example.lee.androidlabs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;


public class ListItemsActivity extends Activity {
static final int REQUEST_IMAGE_CAPTURE = 1;
ImageButton imageButton;
Switch aSwitch;
CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_items);
        Log.i("ListItemsActivity", "in onCreate()");
        imageButton = findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });
        setOnCheckedChanged();
        aSwitch = (Switch) (findViewById(R.id.aSwitch));
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                setOnCheckedChanged();
            }
        });
        checkBox=(CheckBox)(findViewById(R.id.checkBox));
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                onCheckChanged();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageButton.setImageBitmap(imageBitmap);
        }
    }

    protected void onCheckChanged(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.dialog_message);
        builder.setTitle(R.string.dialog_title)
        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent resultIntent = new Intent(  );
                resultIntent.putExtra("Response", "ListItemsActivity passed: My information to share");
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        })
        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        })
        .show();
    }
    protected void setOnCheckedChanged(){
        CharSequence text;
        aSwitch = (Switch) (findViewById(R.id.aSwitch));
        int duration;
        if(aSwitch.isChecked()) {
            text="Switch is On";
            duration = Toast.LENGTH_SHORT;
        }else{
            text="Switch is Off";
            duration = Toast.LENGTH_LONG;
        }
        Toast toast = Toast.makeText(this, text,duration);
        toast.show();
    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
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
