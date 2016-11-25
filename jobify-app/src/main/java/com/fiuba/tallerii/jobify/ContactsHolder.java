package com.fiuba.tallerii.jobify;
import java.util.ArrayList;
import java.util.List;

public class ContactsHolder
{
    private static ContactsHolder sContactsHolder;

    private List<Contact> mContacts;

    public static ContactsHolder get()
    {
        if (sContactsHolder == null)
        {
            sContactsHolder = new ContactsHolder();
        }
        return sContactsHolder;
    }

    private ContactsHolder()
    {
        mContacts = new ArrayList<Contact>();
    }

    public List<Contact> getContacts()
    {
        mContacts.add(new Contact());
        mContacts.add(new Contact());
        mContacts.add(new Contact());
        mContacts.add(new Contact());
        return mContacts;
    }

    public Contact getContactWithName(String name)
    {
        for (Contact contact: mContacts)
        {
            if (contact.getFirstName().equals(name))
            {
                return contact;
            }
        }
        return null;
    }

    public Contact getContactWithEmail(String email)
    {
        for (Contact contact: mContacts)
        {
            if (contact.getEmail().equals(email))
            {
                return contact;
            }
        }
        return null;
    }


}
