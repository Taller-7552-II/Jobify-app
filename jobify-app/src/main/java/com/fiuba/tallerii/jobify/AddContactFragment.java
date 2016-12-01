package com.fiuba.tallerii.jobify;


import android.graphics.Bitmap;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AddContactFragment extends Fragment
{

    private EditText mUsernameEditText;
    private Button mAddContactButton;

    private static final String ADD_FRIEND_RESPONSE = "Notification sent";

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
            String url = "http://" + serverHandler.getServerIP() + "/users/" + serverHandler.getUsername() + "/addFriend/" + mUsername;

            String postParams = "{}";

            return serverHandler.POST(url, postParams);
        }

        @Override
        protected void onPostExecute(final String response)
        {
            Log.d("Jobify", "Add friend response: " + response);
            try
            {
                JSONObject jsonObjectResponse = new JSONObject(response);
                String status = jsonObjectResponse.getString("status");
                boolean success = status.equals(ADD_FRIEND_RESPONSE); //cambiar parseando el string
                if (success)
                {
                    RequestAddedContactTask requestContactTask = new RequestAddedContactTask(mUsername);
                    requestContactTask.execute();
                }
            }
            catch (JSONException e)
            {
                mUsernameEditText.setError(getString(R.string.error_add_contact));
                mUsernameEditText.requestFocus();
                Log.e("Jobify", "Error parsing add contact response. " + e.getMessage());
            }
        }


        @Override
        protected void onCancelled()
        {
        }
    }

    /*
    Class for requesting friend profile data
 */
    public class RequestAddedContactTask extends AsyncTask<Void, Void, Boolean>
    {
        private String mContactUsername;

        Contact mContactInfo;

        private static final String NAME_FIELD = "name";
        private static final String PICTURE_FIELD = "picture";
        private static final String SKILLS_FIELD = "skillList";
        private static final String JOBS_FIELD = "jobList";

        public RequestAddedContactTask(String contactUsername)
        {
            mContactUsername = contactUsername;

            mContactInfo = new Contact("", mContactUsername, null);
        }

        @Override
        protected Boolean doInBackground(Void... params)
        {
            //Parsea la informacion de perfil y el perfil de cada amigo
            ServerHandler serverHandler = ServerHandler.get(getActivity());
            String url = "http://" + serverHandler.getServerIP() + "/users/" + mContactUsername + "/profile/";
            String profileDataResponse = serverHandler.GET(url);
            Log.d("Jobify", mContactUsername + " Profile response: " + profileDataResponse);

            if (!parseBasicInfo(profileDataResponse))
            {
                return false;
            }
            if (!parseSkills(profileDataResponse))
            {
                return false;
            }
            if (!parseJobs(profileDataResponse))
            {
                return false;
            }

            return true;
        }

        private boolean parseBasicInfo(String response)
        {
            boolean success = true;
            try
            {
                JSONObject jsonContactsObject = new JSONObject(response);
                ImageConverter imageConverter = new ImageConverter();

                String name = jsonContactsObject.getString(NAME_FIELD);
                Log.d("Jobify", "Contact name: " + name);
                Bitmap picture = imageConverter.decodeFromBase64ToBitmap(jsonContactsObject.getString(PICTURE_FIELD));

                mContactInfo.setName(name);
                mContactInfo.setPicture(picture);


            } catch (JSONException e)
            {
                success = false;
                Log.e("Jobify", "Error parsing user information request response. " + e.getMessage());
            }
            return success;
        }

        private boolean parseSkills(String response)
        {
            boolean success = true;
            try
            {
                JSONObject jsonObject = new JSONObject(response);

                String fixedSkillsJson = jsonObject.getString(SKILLS_FIELD);
                JSONArray jsonSkillsArray = new JSONArray(fixedSkillsJson);

                for (int i = 0; i < jsonSkillsArray.length(); i++)
                {
                    // iterate the JSONArray and extract each skill
                    JSONObject jsonSkillObject = new JSONObject(jsonSkillsArray.getString(i));
                    String skillTittle = jsonSkillObject.getString("name");
                    String skillDescription = jsonSkillObject.getString("description");
                    String skillCategory = jsonSkillObject.getString("category");

                    Skill skill = new Skill(skillTittle, skillCategory, skillDescription);
                    mContactInfo.addSkill(skill);
                }
            } catch (JSONException e)
            {
                success = false;
                Log.e("Jobify", "Error parsing skills. " + e.getMessage());
            }
            return success;
        }

        private boolean parseJobs(String response)
        {
            boolean success = true;
            try
            {
                JSONObject jsonObject = new JSONObject(response);
                String fixedJobsJson = jsonObject.getString(JOBS_FIELD);
                JSONArray jsonJobsArray = new JSONArray(fixedJobsJson);

                for (int i = 0; i < jsonJobsArray.length(); i++)
                {
                    // iterate the JSONArray and extract each job
                    JSONObject jsonJobObject = new JSONObject(jsonJobsArray.getString(i));
                    String jobTittle = jsonJobObject.getString("name");
                    String jobDescription = jsonJobObject.getString("description");
                    String jobCategory = jsonJobObject.getString("category");

                    Job job = new Job(jobTittle, jobCategory, jobDescription);
                    mContactInfo.addJob(job);
                }
            } catch (JSONException e)
            {
                success = false;
                Log.e("Jobify", "Error parsing jobs. " + e.getMessage());
            }
            return success;
        }

        @Override
        protected void onPostExecute(Boolean success)
        {
            if (success)
            {
                Toast.makeText(getActivity(), getString(R.string.notification_sent), Toast.LENGTH_LONG).show();
                InformationHolder.get().addContact(mContactInfo);
                getActivity().finish();
            } else
            {
                Log.d("Jobify", "Error collecting " + mContactUsername + " profile data.");
            }


        }

        @Override
        protected void onCancelled()
        {
        }
    }
}
