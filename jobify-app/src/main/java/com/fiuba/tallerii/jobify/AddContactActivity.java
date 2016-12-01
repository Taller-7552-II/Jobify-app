package com.fiuba.tallerii.jobify;

import android.support.v4.app.Fragment;

public class AddContactActivity extends SingleFragmentActivity
{
    @Override
    protected Fragment createFragment()
    {
        return new AddContactFragment();
    }
}