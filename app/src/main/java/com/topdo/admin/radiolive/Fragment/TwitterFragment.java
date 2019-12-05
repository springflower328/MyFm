package com.topdo.admin.radiolive.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.topdo.admin.radiolive.Activity.MainActivity;
import com.topdo.admin.radiolive.Adapter.AdapterChat;
import com.topdo.admin.radiolive.ChatTextModel;
import com.topdo.admin.radiolive.ChatTextModelPro;
import com.topdo.admin.radiolive.Pref;
import com.topdo.admin.radiolive.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class TwitterFragment extends Fragment {

    private RecyclerView recyclerList;
    private DatabaseReference mDatabase,mOrdersRef,mCallDB;
    private EditText writtenText;
    private Button sendButton;
    private TextView call_icon;
    private Pref pref;
    private AdapterChat adapterChat;
    private String number="";
    private List<ChatTextModel> chatTextModelList= new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.twitter_fragment, container, false);

        pref=new Pref(getContext());
        recyclerList = (RecyclerView) view.findViewById(R.id.recyclerList);
        call_icon = (TextView) view.findViewById(R.id.call_icon);
        recyclerList.setLayoutManager(new LinearLayoutManager(getContext()));
        writtenText = (EditText) view.findViewById(R.id.msgEdittext);
        sendButton = (Button) view.findViewById(R.id.sendButton);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mOrdersRef = mDatabase.child("chat").child(pref.getString("username").replace("@","").replace(".",""));
        mCallDB = mDatabase.child("call");
        call_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(number)) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + number));
                    startActivity(intent);
                }else Toast.makeText(getContext(), "No Admin Number Found", Toast.LENGTH_SHORT).show();

            }
        });
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(writtenText.getText().toString().trim()))
                {
                    writeNewProduct();
                }
            }
        });

        mCallDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chatTextModelList.clear();
                for (DataSnapshot d: dataSnapshot.getChildren()){
                    Log.w("Rabby",""+d.getKey());
                    number=d.getKey();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


            mOrdersRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    chatTextModelList.clear();
                    for (DataSnapshot d: dataSnapshot.getChildren()){
                        ChatTextModel tD = d.getValue(ChatTextModel.class);
                        chatTextModelList.add(tD);
                    }
                    adapterChat = new AdapterChat(getContext(),recyclerList, chatTextModelList);
                    recyclerList.setAdapter(adapterChat);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });


        return view;
    }

    private void writeNewProduct() {
        ChatTextModelPro realDbModel=new ChatTextModelPro(writtenText.getText().toString().trim(),getCurrentDate("HH:mm"),"user");
        mDatabase.child("chat").child(pref.getString("username").replace("@","").
                replace(".","")).child(getCurrentDate("MMddHHmmss")).setValue(realDbModel);
        writtenText.setText("");
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            MainActivity.onBackPress=false;
        }
        else {
        }
    }

    public String getCurrentDate(String formate) {
        //"MMddHHmmss"
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat(formate);
        String strDate = ""+ mdformat.format(calendar.getTime());
        return strDate;
    }



}
