package com.fiuba.tallerii.jobify;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ContactsFragment  extends Fragment
{

    private RecyclerView mContactsRecycleView;
    private ContactsAdapter mContactsAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_contacts_list, container, false);

        mContactsRecycleView = (RecyclerView) v.findViewById(R.id.recycler_view_contacts);
        mContactsRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));

        requestContacts(getActivity());
        updateUI();

        return v;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        updateUI();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater)
    {
        super.onCreateOptionsMenu(menu, menuInflater);
        menuInflater.inflate(R.menu.fragment_contacts_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menu_item_add_contact:
                Intent intent = new Intent(getActivity(), AddContactActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void requestContacts(Context context)
    {
        /*RequestContactsAsyncTask requestTask = new RequestContactsAsyncTask();
        requestTask.execute((Void) null);*/
    }

    private void updateUI()
    {
        InformationHolder informationHolder = InformationHolder.get();
        List<Contact> contacts = informationHolder.getContacts();

        mContactsAdapter = new ContactsAdapter(contacts);
        mContactsRecycleView.setAdapter(mContactsAdapter);
    }

    /*
        ViewHolder Class
     */
    private class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        CardView mCardView;
        public ImageView mContactImageView;
        public TextView mContactFullName;

        public ContactViewHolder(View itemView)
        {
            super(itemView);
            itemView.setOnClickListener(this);

            mCardView = (CardView) itemView.findViewById(R.id.card_view_contact);
            mContactFullName = (TextView) itemView.findViewById(R.id.list_item_contact_name);
            mContactImageView = (ImageView) itemView.findViewById(R.id.list_item_contact_photo);
        }

        @Override
        public void onClick(View view)
        {
            //Iniciar actividad de ver contacto
        }
    }

    /*
        Adapter Class
     */
    private class ContactsAdapter extends RecyclerView.Adapter<ContactViewHolder>
    {
        private List<Contact> mContacts;

        public ContactsAdapter(List<Contact> contacts)
        {
            mContacts = contacts;
        }

        @Override
        public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_card_contact, parent, false);
            return new ContactViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ContactViewHolder holder, int position)
        {
            Contact contact = mContacts.get(position);
            holder.mContactFullName.setText(contact.getName());
            holder.mContactImageView.setImageBitmap(contact.getPicture());
        }

        @Override
        public int getItemCount()
        {
            return mContacts.size();
        }
    }


}
