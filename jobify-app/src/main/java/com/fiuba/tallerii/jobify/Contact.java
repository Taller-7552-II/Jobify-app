package com.fiuba.tallerii.jobify;

import android.graphics.Bitmap;

public class Contact
{
    private String mFirstName;
    private String mLastName;
    private String mEmail;
    private Bitmap mPicture;

    public Contact()
    {
        mFirstName = "unnamed";
        mLastName = "unnamed";
        mEmail = "";
    }

    public Contact(String firstName, String lastName, String email, Bitmap pictureBitmap)
    {
        mFirstName = firstName;
        mLastName = lastName;
        mEmail = email;
        mPicture = pictureBitmap;
    }

    public String getFirstName()
    {
        return mFirstName;
    }

    public void setFirstName(String firstName)
    {
        mFirstName = firstName;
    }

    public String getLastName()
    {
        return mLastName;
    }

    public void setLastName(String lastName)
    {
        mLastName = lastName;
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

}
