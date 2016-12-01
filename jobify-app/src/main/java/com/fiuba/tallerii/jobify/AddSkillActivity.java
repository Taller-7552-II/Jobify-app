package com.fiuba.tallerii.jobify;


import android.support.v4.app.Fragment;

public class AddSkillActivity extends SingleFragmentActivity
{
    @Override
    protected Fragment createFragment()
    {
        return new AddSkillFragment();
    }
}