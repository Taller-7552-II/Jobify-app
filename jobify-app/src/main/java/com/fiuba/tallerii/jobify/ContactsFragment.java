package com.fiuba.tallerii.jobify;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class ContactsFragment  extends Fragment
{

    private RecyclerView mContactsRecycleView;
    private ContactsAdapter mContactsAdapter;


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

    private void requestContacts(Context context)
    {
        RequestContactsAsyncTask requestTask = new RequestContactsAsyncTask();
        requestTask.execute((Void) null);
    }

    private void updateUI()
    {
        ContactsHolder contactsHolder = ContactsHolder.get();
        List<Contact> contacts = contactsHolder.getContacts();

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
            holder.mContactFullName.setText(contact.getFirstName() + " " + contact.getLastName());
            //bindear Foto
        }

        @Override
        public int getItemCount()
        {
            return mContacts.size();
        }
    }


    /*
        Async class for requesting contacts
     */
    public class RequestContactsAsyncTask extends AsyncTask<Void, Void, String>
    {

        @Override
        protected String doInBackground(Void... params)
        {
            ServerHandler serverHandler = ServerHandler.get(getActivity());
            String url = "http://" + serverHandler.getServerIP() + "/users/" + serverHandler.getUsername() + "/friends/";
            return serverHandler.GET(url);
        }

        @Override
        protected void onPostExecute(final String response)
        {
            parseFriendsResponse(response);
        }

        private void parseFriendsResponse(String response)
        {
            try
            {
                //TODO parse and update ContactsHolder contacts
                JSONObject loginResponse = new JSONObject(response);
            }
            catch (JSONException e)
            {
                Log.e("Jobify", "Error parsing contact request response. " + e.getMessage());
            }
        }

        @Override
        protected void onCancelled()
        {
            updateUI();
        }
    }

}
