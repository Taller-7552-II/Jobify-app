package com.fiuba.tallerii.jobify;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class MainScreenActivity extends AppCompatActivity
{
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);

        toolbar = (Toolbar) findViewById(R.id.toolbar_main_screen);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager_main_screen);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs_main_screen);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
    }

    private void setupTabIcons()
    {
        tabLayout.getTabAt(0).setIcon(R.mipmap.ic_home_white_24dp);
        tabLayout.getTabAt(1).setIcon(R.mipmap.ic_supervisor_account_white_24dp);
        tabLayout.getTabAt(2).setIcon(R.mipmap.ic_notifications_white_24dp);
        tabLayout.getTabAt(3).setIcon(R.mipmap.ic_comment_white_24dp);
    }

    private void setupViewPager(ViewPager viewPager)
    {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ProfileFragment(), "Profile");
        adapter.addFragment(new ContactsFragment(), "Contacts");
        adapter.addFragment(new MenuFragment(), "Menu2");
        adapter.addFragment(new MenuFragment(), "Menu4");
        viewPager.setAdapter(adapter);
    }

    /*
        Adapter Class for managing tabs

     */
    class ViewPagerAdapter extends FragmentPagerAdapter
    {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        private boolean mShowTittle;

        public ViewPagerAdapter(FragmentManager manager)
        {
            super(manager);
            mShowTittle = false;
        }

        @Override
        public Fragment getItem(int position)
        {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount()
        {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title)
        {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position)
        {
            if (!mShowTittle)
            {
                //Display only icons
                return null;
            }
            return mFragmentTitleList.get(position);
        }
    }
}