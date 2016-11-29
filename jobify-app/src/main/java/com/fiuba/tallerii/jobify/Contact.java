package com.fiuba.tallerii.jobify;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

public class Contact
{
    private String mName;
    private String mEmail;
    private Bitmap mPicture;
    private List<Skill> mSkills;
    private List<Job> mJobs;

    public Contact(String name, String email, Bitmap pictureBitmap)
    {
        mName = name;
        mEmail = email;
        mPicture = pictureBitmap;
        mSkills = new ArrayList<>();
        mJobs = new ArrayList<>();
    }

    public String getName()
    {
        return mName;
    }

    public void setName(String name)
    {
        mName = name;
    }

    public String getEmail()
    {
        return mEmail;
    }

    public void setEmail(String email)
    {
        mEmail = email;
    }

    public Bitmap getPicture()
    {
        return mPicture;
    }

    public void setPicture(Bitmap picture)
    {
        mPicture = picture;
    }

    public List<Skill> getSkills()
    {
        return mSkills;
    }

    public List<Job> getJobs()
    {
        return mJobs;
    }
}
