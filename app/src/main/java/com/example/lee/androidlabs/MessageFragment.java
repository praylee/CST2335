package com.example.lee.androidlabs;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by LEE on 2018-03-27.
 */

public class MessageFragment extends Fragment {
    View fragmentView;
    TextView textId;
    TextView textContent;
    Button buttonDelete;
    Bundle bundle;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle =getArguments();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        super.onCreateView(inflater, container, savedInstanceState);
        final boolean isTablet = bundle.getBoolean("isTablet");
        final long id = bundle.getLong("ID");
        String text = bundle.getString("Content");
        fragmentView = inflater.inflate(R.layout.fragment_layout,container,false);
        textId = (TextView)fragmentView.findViewById(R.id.idNumber);
        textContent = (TextView) fragmentView.findViewById(R.id.messageContent);
        textId.setText(String.valueOf(id));
        textContent.setText(text);
        buttonDelete = fragmentView.findViewById(R.id.deleteMessageButton);
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isTablet){
                    ChatWindow cw = (ChatWindow) getActivity();
                    cw.deleteARowOnId(id);
                    getFragmentManager().beginTransaction().remove(MessageFragment.this).commit();
                }else {
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putLong("ID",id);
                    intent.putExtras(bundle);
                    getActivity().setResult(70,intent);
                    getActivity().finish();
                }
            }
        });

        return fragmentView;
    }
}
