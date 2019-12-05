package com.topdo.admin.radiolive.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jean.jcplayer.model.JcAudio;
import com.example.jean.jcplayer.view.JcPlayerView;
import com.topdo.admin.radiolive.Activity.MainActivity;
import com.topdo.admin.radiolive.ActivityStreamPlayer;
import com.topdo.admin.radiolive.Adapter.AdapterAdmin;
import com.topdo.admin.radiolive.Adapter.AdapterGalary;
import com.topdo.admin.radiolive.Adapter.AudioItemAdapter;
import com.topdo.admin.radiolive.AudioAdapterModel;
import com.topdo.admin.radiolive.ChatTextModel;
import com.topdo.admin.radiolive.NetworkChecking;
import com.topdo.admin.radiolive.R;
import com.topdo.admin.radiolive.Util.DividerItemDecoration;
import com.topdo.admin.radiolive.Util.VerticalSpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by viaviweb-2 on 19-Apr-17.
 */

public class GalaryFragment extends Fragment  {

    private RecyclerView galaryRecycler;
    private AudioItemAdapter adapterGalary;
    private JcPlayerView jcplayerView;
    private List<ChatTextModel> chatTextModelList= new ArrayList<>();
    private List<AudioAdapterModel> audioModelList= new ArrayList<>();
    private String songurl="";
    private RecyclerView.LayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.galary_fragment, container, false);

        galaryRecycler=view.findViewById(R.id.galaryRecycler);
        jcplayerView = (JcPlayerView) view.findViewById(R.id.jcplayer);

        layoutManager = new LinearLayoutManager(getContext());
//        galaryRecycler.addItemDecoration(new VerticalSpaceItemDecoration(48));
//        galaryRecycler.addItemDecoration(new DividerItemDecoration(getContext()));
        galaryRecycler.setLayoutManager(layoutManager);
        galaryRecycler.addItemDecoration(new DividerItemDecoration(getContext(), R.drawable.divider));
//        galaryRecycler.setLayoutManager(new GridLayoutManager(getContext(),3));
        adapterGalary = new AudioItemAdapter(getContext(), getList());
        galaryRecycler.setAdapter(adapterGalary);

        return view;

    }

//    private List<ChatTextModel> getList() {
//        chatTextModelList.add(new ChatTextModel("r","https://diademmultimedia.org/wp-content/uploads/2019/11/GOD-By-EmmyG.mp3",
//                "https://diademmultimedia.org/wp-content/uploads/2019/11/EMMYG-GOD-768x768.jpg"));
//
//        chatTextModelList.add(new ChatTextModel("r","https://diademmultimedia.org/wp-content/uploads/2019/11/Nmamma-Dirigi-by-Sorochi.mp3",
//                "https://diademmultimedia.org/wp-content/uploads/2019/11/PHOTO-2018-10-16-20-03-47-1-768x768.jpg"));
//
//        chatTextModelList.add(new ChatTextModel("r","https://diademmultimedia.org/wp-content/uploads/2019/08/Amazing-God-Live.mp3",
//                "https://diademmultimedia.org/wp-content/uploads/2019/08/000-768x768.jpg"));
//
//        chatTextModelList.add(new ChatTextModel("r","https://diademmultimedia.org/wp-content/uploads/2019/08/Amazing-God-Live.mp3",
//                "https://diademmultimedia.org/wp-content/uploads/2019/08/Onos-Ariyo-Ft.-Jekalyn-Carr-%E2%80%93-Your-Name-Jesus-Reprise.jpeg"));
//
//
//        chatTextModelList.add(new ChatTextModel("r","https://diademmultimedia.org/wp-content/uploads/2019/08/No-one-like-You.mp3",
//                "https://diademmultimedia.org/wp-content/uploads/2019/08/IMG-20190724-WA0000-600x600.jpg"));
//
//        return chatTextModelList;
//    }

    private List<AudioAdapterModel> getList() {

        audioModelList.add(new AudioAdapterModel("GOD BY EMMYG", "https://diademmultimedia.org/wp-content/uploads/2019/11/GOD-By-EmmyG.mp3",
                "https://diademmultimedia.org/wp-content/uploads/2019/11/EMMYG-GOD-768x768.jpg"));

        audioModelList.add(new AudioAdapterModel("NMA NMA DIRI GI. By Sorochi", "https://diademmultimedia.org/wp-content/uploads/2019/11/Nmamma-Dirigi-by-Sorochi.mp3",
                "https://diademmultimedia.org/wp-content/uploads/2019/11/PHOTO-2018-10-16-20-03-47-1-768x768.jpg"));

        audioModelList.add(new AudioAdapterModel("“Amazing God” (Live) By Tinuade Ilesanmi", "https://diademmultimedia.org/wp-content/uploads/2019/08/Amazing-God-Live.mp3",
                "https://diademmultimedia.org/wp-content/uploads/2019/08/000-768x768.jpg"));

        audioModelList.add(new AudioAdapterModel("Music: Onos Ariyo – Your Name Jesus (Reprise) Feat. Jekalyn Carr", "https://diademmultimedia.org/wp-content/uploads/2019/08/Amazing-God-Live.mp3",
                "https://diademmultimedia.org/wp-content/uploads/2019/08/Onos-Ariyo-Ft.-Jekalyn-Carr-%E2%80%93-Your-Name-Jesus-Reprise.jpeg"));

        audioModelList.add(new AudioAdapterModel("Emmy G has released his new single ‘Eternity’,", "https://diademmultimedia.org/wp-content/uploads/2019/08/No-one-like-You.mp3",
                "https://diademmultimedia.org/wp-content/uploads/2019/08/IMG-20190724-WA0000-600x600.jpg"));

        return audioModelList;
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



}