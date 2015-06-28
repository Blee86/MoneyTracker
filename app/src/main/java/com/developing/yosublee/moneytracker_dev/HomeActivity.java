package com.developing.yosublee.moneytracker_dev;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import android.view.Menu;
import android.view.MenuItem;

import com.developing.yosublee.moneytracker_dev.Tabs.AccountFragment;
import com.developing.yosublee.moneytracker_dev.Tabs.GraphFragment;
import com.developing.yosublee.moneytracker_dev.Tabs.TransactionFragment;


public class HomeActivity extends FragmentActivity {
    private ViewPager mViewPager;
    private TabPagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialization
        mPagerAdapter = new TabPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mPagerAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    ////////////////////////////////////////////////////////
    // Pager Adapter Class that manages tab pages
    // - Position starts from 0
    ////////////////////////////////////////////////////////
    public class TabPagerAdapter extends FragmentPagerAdapter {
        static final int NUMBER_OF_PAGES = 3;
        public TabPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new TransactionFragment();
                case 1:
                    return new GraphFragment();
                case 2:
                    return new AccountFragment();
            }

            return null;
        }

        @Override
        public int getCount() {
            // Equal to number of tabs
            return NUMBER_OF_PAGES;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getResources().getString(R.string.tab_title_1).toUpperCase();
                case 1:
                    return getResources().getString(R.string.tab_title_3).toUpperCase();
                case 2:
                    return getResources().getString(R.string.tab_title_2).toUpperCase();
            }


            return "Undefined Tab";
        }
    }
}
