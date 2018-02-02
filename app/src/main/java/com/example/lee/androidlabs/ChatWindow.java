package com.example.lee.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        chatView = (ListView)(findViewById(R.id.chatView));
        inputText = (EditText)(findViewById(R.id.input_text));
        sendButton = (Button)(findViewById(R.id.button_send));

        chatMessage = new ArrayList<>();
        final ChatAdapter adapter = new ChatAdapter(this);
        chatView.setAdapter(adapter);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chatMessage.add(inputText.getText().toString());
                adapter.notifyDataSetChanged();
                inputText.setText("");
            }
        });



    }

    private class ChatAdapter extends ArrayAdapter<String>{
        public ChatAdapter(Context ctx) {
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
}
