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

    public void addSkill(Skill skillToAdd)
    {
        for (Skill skill: mSkills)
        {
            if (skill.getTittle().equals(skillToAdd.getTittle()))
            {
                //exit if contact exists in the list
                return;
            }
        }
        mSkills.add(skillToAdd);
    }

    public void addJob(Job jobToAdd)
    {
        for (Job job: mJobs)
        {
            if (job.getTittle().equals(jobToAdd.getTittle()))
            {
                return;
            }
        }
        mJobs.add(jobToAdd);
    }

    public void setSkills(List<Skill> skills)
    {
        mSkills = skills;
    }

    public void setJobs(List<Job> jobs)
    {
        mJobs = jobs;
    }
}
