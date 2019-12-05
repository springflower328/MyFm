package com.topdo.admin.radiolive.Fragment;

import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.topdo.admin.radiolive.Activity.MainActivity;
import com.topdo.admin.radiolive.Adapter.AdapterAdmin;
import com.topdo.admin.radiolive.Adapter.AdapterChat;
import com.topdo.admin.radiolive.Adapter.AdapterGalary;
import com.topdo.admin.radiolive.ChatTextModel;
import com.topdo.admin.radiolive.ChatTextModelPro;
import com.topdo.admin.radiolive.Pref;
import com.topdo.admin.radiolive.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class AdminFragment extends Fragment {

    private RecyclerView recyclerList;
    private DatabaseReference mDatabase,mOrdersRef;

    private Pref pref;
    private AdapterAdmin adapterChat;

    private List<String> chatTextModelList= new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.admin_fragment, container, false);

        pref=new Pref(getContext());
        recyclerList = (RecyclerView) view.findViewById(R.id.recyclerList);

        recyclerList.setLayoutManager(new LinearLayoutManager(getContext()));

        mDatabase = FirebaseDatabase.getInstance().getReference();



        mOrdersRef = mDatabase.child("chat");


            mOrdersRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    chatTextModelList.clear();
                    for (DataSnapshot d: dataSnapshot.getChildren()){
                        Log.w("Rabby",""+d.getKey());
                        chatTextModelList.add(d.getKey());
                    }
                    adapterChat = new AdapterAdmin(getContext(),recyclerList, chatTextModelList);
                    recyclerList.setAdapter(adapterChat);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });


        return view;
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
