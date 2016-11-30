package com.fiuba.tallerii.jobify;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ProfileFragment extends Fragment
{

    private ImageView mProfilePicture;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        mProfilePicture = (ImageView) v.findViewById(R.id.fragment_profile_picture);

        mProfilePicture.setImageBitmap(InformationHolder.get().getProfilePicture());

        return v;
    }
}
