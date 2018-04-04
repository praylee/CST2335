package com.example.lee.androidlabs;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
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
FrameLayout frame;
Cursor cursor;
boolean isTablet;
ChatAdapter adapter;
ChatDatabaseHelper chatDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chatMessage = new ArrayList<>();
        setContentView(R.layout.activity_chat_window);
        chatView = (ListView)(findViewById(R.id.chatView));
        inputText = (EditText)(findViewById(R.id.input_text));
        sendButton = (Button)(findViewById(R.id.button_send));
        frame = (FrameLayout)findViewById(R.id.frame);
        isTablet = frame!=null;

        chatDatabaseHelper = new ChatDatabaseHelper(this);
        adapter = new ChatAdapter(this);
        chatView.setAdapter(adapter);

        db = chatDatabaseHelper.getWritableDatabase();
        refreshChatList();
//        cursor.close();

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = inputText.getText().toString();
                ContentValues cv = new ContentValues();
                cv.put(ChatDatabaseHelper.KEY_MESSAGE, content);
                db.insert(ChatDatabaseHelper.TABLE_NAME,"",cv);
                inputText.setText("");
                cursor = db.query(false, ChatDatabaseHelper.TABLE_NAME, new String[]{ChatDatabaseHelper.KEY_ID,ChatDatabaseHelper.KEY_MESSAGE},
                        null,null,null,null,null,null);
                cursor.moveToPosition(cursor.getCount()-1);
                chatMessage.add(cursor.getString( cursor.getColumnIndex( ChatDatabaseHelper.KEY_MESSAGE)));
                adapter.notifyDataSetChanged();
            }
        });

        chatView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                bundle.putBoolean("isTablet",isTablet);
                bundle.putLong("ID",l);
                bundle.putString("Content",chatMessage.get(i));
                if(isTablet){
                    MessageFragment mf = new MessageFragment();
                    mf.setArguments(bundle);
                    FragmentTransaction ft = getFragmentManager().beginTransaction().replace(R.id.frame, mf);
                    ft.commit();
                }else{
                    Intent intent = new Intent(ChatWindow.this,MessageDetails.class);
                    intent.putExtras(bundle);
                    startActivityForResult(intent,7);
                }

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==70){
            long id = data.getLongExtra("ID",-1);
            deleteARowOnId(id);
        }
    }

    void deleteARowOnId(long id){
//        chatDatabaseHelper = new ChatDatabaseHelper(this);
//        SQLiteDatabase dbwrite = chatDatabaseHelper.getWritableDatabase();
        db.delete(ChatDatabaseHelper.TABLE_NAME, ChatDatabaseHelper.KEY_ID+"=?", new String[]{String.valueOf(id)});
        refreshChatList();
        adapter.notifyDataSetChanged();
    }

//    void tabletDelete(long id){
////        chatDatabaseHelper = new ChatDatabaseHelper(this);
////        SQLiteDatabase dbwrite = chatDatabaseHelper.getWritableDatabase();
//        String deleteStatement = "DELETE FROM " + ChatDatabaseHelper.TABLE_NAME +" WHERE " + ChatDatabaseHelper.KEY_ID + " = " + id + ";";
//        db.execSQL(deleteStatement);
//        refreshChatList();
//        adapter.notifyDataSetChanged();
//    }

    private void refreshChatList(){
        chatMessage.clear();
        cursor = db.query(false, ChatDatabaseHelper.TABLE_NAME, new String[]{ChatDatabaseHelper.KEY_ID,ChatDatabaseHelper.KEY_MESSAGE},
                null,null,null,null,null,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            chatMessage.add(cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));
            Log.i(this.getClass().getSimpleName(), "Column Count:" + cursor.getColumnCount() );
            Log.i(this.getClass().getSimpleName(), "Column Name:" + cursor.getColumnName( cursor.getColumnIndex( ChatDatabaseHelper.KEY_MESSAGE ) ) );
            Log.i(this.getClass().getSimpleName(), "Column Value:" + cursor.getString( cursor.getColumnIndex( ChatDatabaseHelper.KEY_MESSAGE) ) );
            cursor.moveToNext();
        }
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
            cursor.moveToPosition(position);
            return cursor.getInt(cursor.getColumnIndex(ChatDatabaseHelper.KEY_ID));
//            return position;
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
        cursor.close();
        db.close();
        Log.i(this.getClass().getSimpleName(), "in onDestroy()");
    }
}
