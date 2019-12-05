package com.topdo.admin.radiolive.Adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.topdo.admin.radiolive.ChatTextModel;
import com.topdo.admin.radiolive.R;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AdapterGalary extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;

    private List<ChatTextModel> items = new ArrayList<>();

    private boolean loading;
    private OnLoadMoreListener onLoadMoreListener;

    private Context context;
    private int currentPlayingPosition;
    private SeekBar seekBar;

    private OnBluetoothDeviceClickedListener mBluetoothClickListener;



    // Provide a suitable constructor (depends on the kind of dataset)
    public AdapterGalary(Context context, RecyclerView view, List<ChatTextModel> items) {
        this.items = items;
        this.context = context;
        lastItemViewDetector(view);
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case

        public ImageView userImage;
        public ImageView playbtn;


        public OriginalViewHolder(View v) {
            super(v);

            userImage = (ImageView) v.findViewById(R.id.userImage);
            playbtn = (ImageView) v.findViewById(R.id.playbtn);
        }
    }

    public interface OnBluetoothDeviceClickedListener {
        void onBluetoothDeviceClicked(String deviceAddress);
    }
    public void setOnBluetoothDeviceClickedListener(OnBluetoothDeviceClickedListener l) {
        mBluetoothClickListener = l;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;

                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.lsv_item_image, parent, false);
                vh = new OriginalViewHolder(v);


        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final ChatTextModel p = items.get(position);
        final OriginalViewHolder originalViewHolder=(OriginalViewHolder) holder;

        Log.w("Rabby",p.getIcon());
            Glide
            .with(context)
            .load(Uri.parse(p.getIcon()))
            .into(originalViewHolder.userImage);


        if(p.getchatmsg().equals("playing")){
            originalViewHolder.playbtn.setBackgroundResource(R.drawable.ic_stop_circular_button);}
        else
            originalViewHolder.playbtn.setBackgroundResource(R.drawable.ic_play_button);


        originalViewHolder.playbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mBluetoothClickListener != null) {

//                    for (ChatTextModel chatTextModel:items) {
//                        chatTextModel.setchatmsg("R");
//                    }


                    if(!p.getchatmsg().equals("playing")) {
                        p.setchatmsg("playing");
//                        originalViewHolder.playbtn.setBackgroundResource(R.drawable.ic_play_button);
                    } else {
                        p.setchatmsg("r");
//                        originalViewHolder.playbtn.setBackgroundResource(R.drawable.ic_stop_circular_button);
                    }


                    notifyDataSetChanged();

                    mBluetoothClickListener.onBluetoothDeviceClicked(p.getchattime());
                }
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {

        return VIEW_ITEM;

    }



    private void lastItemViewDetector(RecyclerView recyclerView) {
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            final LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    int lastPos = layoutManager.findLastVisibleItemPosition();
                    if (!loading && lastPos == getItemCount() - 1 && onLoadMoreListener != null) {
                        if (onLoadMoreListener != null) {
                            int current_page = getItemCount() / 100;
                            onLoadMoreListener.onLoadMore(current_page);
                        }
                        loading = true;
                    }
                }
            });
        }
    }

    public interface OnLoadMoreListener {
        void onLoadMore(int current_page);
    }

}