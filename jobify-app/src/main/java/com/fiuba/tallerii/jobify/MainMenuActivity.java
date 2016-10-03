package com.fiuba.tallerii.jobify;


import android.support.v4.app.Fragment;

public class MainMenuActivity extends SingleFragmentActivity
{
    @Override
    protected Fragment createFragment()
    {
        return new MenuFragment();
    }
}
