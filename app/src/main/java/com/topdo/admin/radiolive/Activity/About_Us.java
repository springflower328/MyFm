package com.topdo.admin.radiolive.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.topdo.admin.radiolive.Fragment.FacebookFragment;
import com.topdo.admin.radiolive.R;
import com.topdo.admin.radiolive.Util.Constants;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by viaviweb-2 on 05-May-17.
 */

public class About_Us extends AppCompatActivity {

    public Toolbar toolbar;
    private TextView app_name, app_version, app_author, app_contact, app_email, app_website, app_description,toolbar_name;
    private ImageView app_logo;
    private Constants constants;
    WebView webView;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_us);

        Constants.forceRTLIfSupported(getWindow(),About_Us.this);

        constants=new Constants(About_Us.this);

        webView = (WebView)findViewById(R.id.webView);
        webView.setWebViewClient(new MyBrowser());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://diademmultimedia.org");
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

        app_name = (TextView) findViewById(R.id.app_name_about_us);
        app_version = (TextView) findViewById(R.id.app_version_about_us);
       /* app_author = (TextView) findViewById(R.id.app_author_about_us);
        app_contact = (TextView) findViewById(R.id.app_contact_about_us);
        app_email = (TextView) findViewById(R.id.app_email_about_us);
        app_website = (TextView) findViewById(R.id.app_website_about_us);
        app_description = (TextView) findViewById(R.id.app_description_about_us);*/
        toolbar_name=(TextView)findViewById(R.id.toolbar_title_about_us);

        app_logo = (ImageView) findViewById(R.id.app_logo_about_us);

        toolbar = (Toolbar) findViewById(R.id.about_us_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        toolbar_name.setTypeface(constants.contm);
        toolbar_name.setText(getResources().getString(R.string.About_Us));

      /*  final Drawable upArrow = getResources().getDrawable(R.drawable.ic_back);
        upArrow.setColorFilter(getResources().getColor(R.color.arrow_color), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);*/

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
