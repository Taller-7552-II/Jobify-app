package com.fiuba.tallerii.jobify;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class AddContactFragment extends Fragment
{

    private EditText mUsernameEditText;
    private Button mAddContactButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_add_contact, container, false);


        mUsernameEditText = (EditText) v.findViewById(R.id.edit_text_username_add_contact);
        mUsernameEditText.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent)
            {
                if (id == R.id.add_contact || id == EditorInfo.IME_NULL)
                {
                    addContact();
                    return true;
                }
                return false;
            }
        });

        mAddContactButton = (Button) v.findViewById(R.id.button_add_contact);
        mAddContactButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                addContact();
            }
        });

        return v;
    }

    private void addContact()
    {
        String contactUsername = mUsernameEditText.getText().toString();
        if (isUsernameValid(contactUsername))
        {
            AddContactAsyncTask addContactTask = new AddContactAsyncTask(contactUsername);
            addContactTask.execute();
        }

    }

    private boolean isUsernameValid(String username)
    {
        boolean valid = true;
        if (TextUtils.isEmpty(username))
        {
            mUsernameEditText.setError(getString(R.string.error_field_required));
            mUsernameEditText.requestFocus();
            valid = false;
        }

        FieldValidator validator = new FieldValidator();
        if (!validator.isEmailValid(username))
        {
            mUsernameEditText.setError(getString(R.string.error_invalid_username));
            mUsernameEditText.requestFocus();
            valid = false;
        }
        return valid;
    }

    /*
    Add Contact class
 */
    public class AddContactAsyncTask extends AsyncTask<Void, Void, String>
    {
        String mUsername;

        public AddContactAsyncTask(String contactUsername)
        {
            mUsername = contactUsername;
        }
        @Override
        protected String doInBackground(Void... params)
        {
            ServerHandler serverHandler = ServerHandler.get(getActivity());
            String url = "http://" + serverHandler.getServerIP() + "/users/" + serverHandler.getUsername() + "/friends/";

            String postParams = "";
            try
            {
                JSONObject jsonAddContactParams = new JSONObject();
                jsonAddContactParams.put("mail", mUsername);
                postParams = jsonAddContactParams.toString();
            }
            catch(JSONException e)
            {
                Log.e("Jobify", "Error creating add Contact Json File");
            }
            return serverHandler.POST(url, postParams);
        }

        @Override
        protected void onPostExecute(final String response)
        {
            String toastMessage = "";
            try
            {
                JSONObject jsonObjectResponse = new JSONObject(response);

                boolean success = true; //cambiar parseando el string
                if (success)
                {
                    toastMessage = getString(R.string.response_sync_success);
                }
            }
            catch (JSONException e)
            {
                toastMessage = getString(R.string.response_sync_failed);
                Log.e("Jobify", "Error parsing add contact response. " + e.getMessage());
            }
            Toast.makeText(getActivity(), toastMessage, Toast.LENGTH_SHORT).show();
        }


        @Override
        protected void onCancelled()
        {
        }
    }
}
