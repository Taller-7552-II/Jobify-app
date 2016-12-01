package com.fiuba.tallerii.jobify;
import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

public class InformationHolder
{
    private static InformationHolder sInformationHolder;

    private String mName;
    private String mResume;
    private String mMail;
    private Bitmap mProfilePicture;
    private List<Contact> mContacts;
    private List<Job> mJobs;
    private List<Skill> mSkills;
    private List<Notification> mNotifications;

    public static InformationHolder get()
    {
        if (sInformationHolder == null)
        {
            sInformationHolder = new InformationHolder();
        }
        return sInformationHolder;
    }

    private InformationHolder()
    {
        mContacts = new ArrayList<>();
        mJobs = new ArrayList<>();
        mSkills = new ArrayList<>();
        mNotifications = new ArrayList<>();
    }

    public List<Contact> getContacts()
    {
        return mContacts;
    }

    public void replaceContact(String username, Contact newContact)
    {
        for (int i = 0; i < mContacts.size(); ++i)
        {
            if (mContacts.get(i).getEmail().equals(username))
            {
                mContacts.set(i, newContact);
                return;
            }
        }
        //si no lo encontro
        mContacts.add(newContact);
    }

    public List<Job> getJobs()
    {
        return mJobs;
    }

    public List<Skill> getSkills()
    {
        return mSkills;
    }

    // add a contact if it doesn't exist in the list.
    // a contact is considered duplicated if it has the same email(username) as another one in the list
    public void addContact(Contact contactToAdd)
    {
        for (Contact contact: mContacts)
        {
            if (contact.getEmail().equals(contactToAdd.getEmail()))
            {
                //exit if contact exists in the list
                return;
            }
        }
        mContacts.add(contactToAdd);
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

    public void addNotification(Notification notificationToAdd)
    {
        mNotifications.add(notificationToAdd);
    }

    public void removeNotification(int index)
    {
        mNotifications.remove(index);
    }

    public Notification getNotification(int index)
    {
        return mNotifications.get(index);
    }


    public String getName()
    {
        return mName;
    }

    public void setName(String name)
    {
        mName = name;
    }

    public String getMail()
    {
        return mMail;
    }

    public void setMail(String mail)
    {
        mMail = mail;
    }

    public Bitmap getProfilePicture()
    {
        return mProfilePicture;
    }

    public String getResume()
    {
        return mResume;
    }

    public void setResume(String resume)
    {
        mResume = resume;
    }


    public void setProfilePicture(Bitmap profilePicture)
    {
        mProfilePicture = profilePicture;
    }

    public List<Notification> getNotifications()
    {
        return mNotifications;
    }
}
