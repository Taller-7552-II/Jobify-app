package com.fiuba.tallerii.jobify;


public class Job
{
    private String mTittle;
    private String mCategory;
    private String mDescription;

    public Job(String tittle, String category, String description)
    {
        mTittle = tittle;
        mCategory = category;
        mDescription = description;
    }

    public String getTittle()
    {
        return mTittle;
    }

    public void setTittle(String tittle)
    {
        mTittle = tittle;
    }

    public String getCategory()
    {
        return mCategory;
    }

    public void setCategory(String category)
    {
        mCategory = category;
    }

    public String getDescription()
    {
        return mDescription;
    }

    public void setDescription(String description)
    {
        mDescription = description;
    }
}
