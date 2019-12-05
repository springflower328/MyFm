package com.topdo.admin.radiolive.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.topdo.admin.radiolive.Activity.MainActivity;
import com.topdo.admin.radiolive.ActivityStreamPlayer;
import com.topdo.admin.radiolive.NetworkChecking;
import com.topdo.admin.radiolive.R;


/**
 * Created by viaviweb-2 on 19-Apr-17.
 */

public class FacebookFragment extends Fragment {

    private WebView webView;
    private String postUrl;
    ImageView channel_image;
    TextView channel_name, channel_category;
    WebView channel_description;
    Snackbar snackbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.facebook_fragment, container, false);

        postUrl=getResources().getString(R.string.facebookLink);
//        postUrl="https://www.aljazeera.com/";
        postUrl="https://diademmultimedia.org/live/";

        channel_image = (ImageView) view.findViewById(R.id.channel_image);
        channel_name = (TextView) view.findViewById(R.id.channel_name);
        channel_category = (TextView) view.findViewById(R.id.channel_category);
        channel_description = (WebView) view.findViewById(R.id.webView_facebook);

        webView = (WebView)view.findViewById(R.id.webView_facebook);
        webView.setWebViewClient(new MyBrowser());

        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(postUrl);
        webView.setHorizontalScrollBarEnabled(false);

        webView.setOnKeyListener( new View.OnKeyListener() {
            @Override
            public boolean onKey( View v, int keyCode, KeyEvent event ) {
                if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
                    webView.goBack();
                    return true;
                }

                return false;
            }
        });



        channel_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (NetworkChecking.hasConnection(getContext())) {


                            Intent intent = new Intent(getContext(), ActivityStreamPlayer.class);
                            intent.putExtra("url", "ss");
                            startActivity(intent);


                } else {
                    Toast.makeText(getContext(), "Network required", Toast.LENGTH_SHORT).show();
                }

            }
        });


        return view;

    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
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

   /* public boolean canGoBack() {
        return this.webView != null && this.webView.canGoBack();
    }

    public void goBack() {
        if(this.webView != null) {
            this.webView.goBack();
        }
    }*/


}