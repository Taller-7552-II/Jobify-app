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



}
