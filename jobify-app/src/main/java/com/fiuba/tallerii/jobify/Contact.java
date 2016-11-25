package com.fiuba.tallerii.jobify;

public class Contact
{
    private String mFirstName;
    private String mLastName;
    private String mEmail;

    public Contact()
    {
        mFirstName = "unnamed";
        mLastName = "unnamed";
        mEmail = "";
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

}
