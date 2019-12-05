package com.topdo.admin.radiolive.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.topdo.admin.radiolive.Activity.MainActivity;
import com.topdo.admin.radiolive.Fragment.AdminFragment;
import com.topdo.admin.radiolive.Fragment.FacebookFragment;
import com.topdo.admin.radiolive.Fragment.GalaryFragment;
import com.topdo.admin.radiolive.Fragment.MyFmFragment;
import com.topdo.admin.radiolive.Fragment.TwitterFragment;
import com.topdo.admin.radiolive.Pref;

/**
 * Created by viaviweb-2 on 22-Apr-17.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

    int mNumOfTabs;
    private Context context;

    public ViewPagerAdapter(Context con,FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.context=con;
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                MyFmFragment tab1 = new MyFmFragment();
                MainActivity.onBackPress = true;
                return tab1;
            case 1:
                FacebookFragment tab2 = new FacebookFragment();
                return tab2;
            case 2:
                if(new Pref(context).getBoolean("admin")) {
                    AdminFragment tab3 = new AdminFragment();
                    return tab3;
                }else {
                    TwitterFragment tab3 = new TwitterFragment();
                    return tab3;
                }
            case 3:
                GalaryFragment tab4 = new GalaryFragment();
                return tab4;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}