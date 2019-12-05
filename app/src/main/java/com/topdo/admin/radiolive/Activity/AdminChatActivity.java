package com.topdo.admin.radiolive.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.topdo.admin.radiolive.Adapter.AdapterChat;
import com.topdo.admin.radiolive.ChatTextModel;
import com.topdo.admin.radiolive.ChatTextModelPro;
import com.topdo.admin.radiolive.Pref;
import com.topdo.admin.radiolive.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AdminChatActivity extends AppCompatActivity  {

    private RecyclerView recyclerList;
    private DatabaseReference mDatabase,mOrdersRef;
    private EditText writtenText;
    private Button sendButton;
    private Pref pref;
    private AdapterChat adapterChat;
    private List<ChatTextModel> chatTextModelList= new ArrayList<>();
    private String chatHead="";
    private long maxid = 0;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_chat_fragment);

        chatHead=getIntent().getExtras().getString("heads");


        pref=new Pref(this);
        recyclerList = (RecyclerView) findViewById(R.id.recyclerList);
        recyclerList.setLayoutManager(new LinearLayoutManager(AdminChatActivity.this));
        writtenText = (EditText) findViewById(R.id.msgEdittext);
        sendButton = (Button) findViewById(R.id.sendButton);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mOrdersRef = mDatabase.child("chat").child(chatHead);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(writtenText.getText().toString().trim()))
                {
                    writeNewProduct();
                }
            }
        });

        mOrdersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists())
                    maxid = dataSnapshot.getChildrenCount();

                chatTextModelList.clear();
                for (DataSnapshot d: dataSnapshot.getChildren()){
                    ChatTextModel tD = d.getValue(ChatTextModel.class);
                    chatTextModelList.add(tD);
                }
                adapterChat = new AdapterChat(AdminChatActivity.this,recyclerList, chatTextModelList);
                recyclerList.setAdapter(adapterChat);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        
    }

    @Override
    public void onBackPressed() {
    finish();
    }

    private void writeNewProduct() {
        ChatTextModelPro realDbModel=new ChatTextModelPro(writtenText.getText().toString().trim(),getCurrentDate("HH:mm"),"admin");
        mDatabase.child("chat").child(chatHead).child(getCurrentDate("MMddHHmmss")).setValue(realDbModel);
//        mDatabase.child("chat").child(chatHead).child(String.valueOf(maxid + 1)).setValue(realDbModel);
        writtenText.setText("");
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    public String getCurrentDate(String formate) {
        //"MMddHHmmss"
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat(formate);
        String strDate = ""+ mdformat.format(calendar.getTime());
        return strDate;
    }
}
