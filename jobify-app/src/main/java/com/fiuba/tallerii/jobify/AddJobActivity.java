package com.fiuba.tallerii.jobify;


import android.support.v4.app.Fragment;

public class AddJobActivity extends SingleFragmentActivity
{
    @Override
    protected Fragment createFragment()
    {
        return new AddJobFragment();
    }
}