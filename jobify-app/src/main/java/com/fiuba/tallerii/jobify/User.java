package com.fiuba.tallerii.jobify;

/**
 * Created by Gonzalo on 03/10/2016.
 */
public class User
{
    private String mFirstName;
    private String mLastName;
    private String mEmail;
    private String mPassword;

    public User(String firstName, String lastName, String email, String password)
    {
        mFirstName = firstName;
        mLastName = lastName;
        mEmail = email;
        mPassword = password;
    }

    public String getFirstName()
    {
        return mFirstName;
    }

    public String getLastName()
    {
        return mLastName;
    }

    public String getEmail()
    {
        return mEmail;
    }

    public String getPassword()
    {
        return mPassword;
    }

    public void setPassword(String password)
    {
        mPassword = password;
    }
}
