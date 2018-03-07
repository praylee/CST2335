package com.example.lee.androidlabs;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ChatWindow extends Activity {
ListView chatView;
EditText inputText;
Button sendButton;
ArrayList<String> chatMessage;
SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chatMessage = new ArrayList<>();
        setContentView(R.layout.activity_chat_window);
        chatView = (ListView)(findViewById(R.id.chatView));
        inputText = (EditText)(findViewById(R.id.input_text));
        sendButton = (Button)(findViewById(R.id.button_send));
        final ChatDatabaseHelper chatDatabaseHelper = new ChatDatabaseHelper(this);
        final ChatAdapter adapter = new ChatAdapter(this);
        chatView.setAdapter(adapter);

        db = chatDatabaseHelper.getReadableDatabase();
        Cursor cursor = db.query(false, ChatDatabaseHelper.TABLE_NAME, new String[]{ChatDatabaseHelper.KEY_MESSAGE},
                null,null,null,null,null,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            chatMessage.add(cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));
            Log.i(this.getClass().getSimpleName(), "SQL MESSAGE:" + cursor.getString( cursor.getColumnIndex( ChatDatabaseHelper.KEY_MESSAGE) ) );
            cursor.moveToNext();
        }
        cursor.close();

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chatMessage.add(inputText.getText().toString());
                ContentValues cv = new ContentValues();
                cv.put(ChatDatabaseHelper.KEY_MESSAGE, chatMessage.get(chatMessage.size()-1));
                db = chatDatabaseHelper.getWritableDatabase();
                db.insert(ChatDatabaseHelper.TABLE_NAME,"",cv);
                adapter.notifyDataSetChanged();
                inputText.setText("");
            }
        });

    }

    private class ChatAdapter extends ArrayAdapter<String>{
        protected ChatAdapter(Context ctx) {
            super(ctx, 0);
        }

        @Override
        public int getCount(){
            return chatMessage.size();
        }

        @Override
        public String getItem(int position){
            return chatMessage.get(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
            View result = null;
            if(position%2==0){
                result = inflater.inflate(R.layout.chat_row_incoming, null);
            }else{
                result = inflater.inflate(R.layout.chat_row_outgoing, null);
            }
            TextView message = (TextView)(result.findViewById(R.id.message_text));
            message.setText(getItem(position));
            return result;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(this.getClass().getSimpleName(), "in onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(this.getClass().getSimpleName(), "in onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(this.getClass().getSimpleName(), "in onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(this.getClass().getSimpleName(), "in onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
        Log.i(this.getClass().getSimpleName(), "in onDestroy()");
    }
}
