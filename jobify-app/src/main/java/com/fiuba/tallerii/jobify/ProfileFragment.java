package com.fiuba.tallerii.jobify;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class ProfileFragment extends Fragment
{

    private Button mSaveChangesButton;
    private ImageView mProfilePicture;

    private RelativeLayout mNameLayout;
    private EditText mNameEditText;

    private RelativeLayout mResumeLayout;
    private EditText mResumeEditText;

    private RelativeLayout mSkillsLayout;
    private RelativeLayout mJobsLayout;

    private KeyListener mNameKeyListener;
    private KeyListener mResumeKeyListener;

    private boolean mSavingChanges;
    private final String CHANGES_SAVED_RESPONSE = "Changes saved";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        mProfilePicture = (ImageView) v.findViewById(R.id.fragment_profile_picture);
        mProfilePicture.setImageBitmap(InformationHolder.get().getProfilePicture());

        mSkillsLayout = (RelativeLayout) v.findViewById(R.id.profile_skills_layout);
        mSkillsLayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getActivity(), SkillsActivity.class);
                startActivity(intent);
            }
        });
        mJobsLayout = (RelativeLayout) v.findViewById(R.id.profile_jobs_layout);
        mJobsLayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getActivity(), JobsActivity.class);
                startActivity(intent);
            }
        });

        mSavingChanges = false;

        setUpNameField(v);
        setUpResumeField(v);
        setUpSaveChangesButton(v);

        return v;
    }

    private void setUpNameField(View v)
    {
        mNameEditText = (EditText) v.findViewById(R.id.profile_name);
        mNameKeyListener = mNameEditText.getKeyListener();
        mNameEditText.setKeyListener(null);

        mNameLayout = (RelativeLayout) v.findViewById(R.id.profile_name_layout);
        mNameLayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mNameEditText.setKeyListener(mNameKeyListener);
                mNameEditText.requestFocus();
            }
        });
        mNameEditText.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent)
            {
                if (id == R.id.change_name || id == EditorInfo.IME_NULL)
                {
                    mNameEditText.setKeyListener(null);
                    mNameEditText.clearFocus();
                    return true;
                }
                return false;
            }
        });
    }

    private void setUpResumeField(View v)
    {
        mResumeEditText = (EditText) v.findViewById(R.id.profile_resumen);
        mResumeKeyListener = mResumeEditText.getKeyListener();
        mResumeEditText.setKeyListener(null);

        mResumeLayout = (RelativeLayout) v.findViewById(R.id.profile_resume_layout);
        mResumeLayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mResumeEditText.setKeyListener(mResumeKeyListener);
                mResumeEditText.requestFocus();
            }
        });
        mResumeEditText.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent)
            {
                if (id == R.id.change_name || id == EditorInfo.IME_NULL)
                {
                    mResumeEditText.setKeyListener(null);
                    mResumeEditText.clearFocus();
                    return true;
                }
                return false;
            }
        });
    }

    private void setUpSaveChangesButton(View v)
    {
        mSaveChangesButton = (Button) v.findViewById(R.id.profile_button_save_changes);
        mSaveChangesButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (!mSavingChanges)
                {
                    mSavingChanges = true;
                    String name = mNameEditText.getText().toString();
                    String resume = mResumeEditText.getText().toString();
                    EditProfileTask editProfileTask = new EditProfileTask(name, resume);
                    editProfileTask.execute((Void)null);
                }
            }
        });
    }


    /**
     * Represents an asynchronous login task used to authenticate
     * the user.
     */
    public class EditProfileTask extends AsyncTask<Void, Void, String>
    {

        private final String mName;
        private final String mResume;

        EditProfileTask(String name, String resume)
        {
            mName = name;
            mResume = resume;
        }

        @Override
        protected String doInBackground(Void... params)
        {
            ServerHandler serverHandler = ServerHandler.get(getActivity());
            String urlSpec = "http://" + serverHandler.getServerIP() + "/users/" + serverHandler.getUsername();
            String loginParams = "";
            try
            {
                JSONObject jsonLoginParams= new JSONObject();
                jsonLoginParams.put("name", mName);
                jsonLoginParams.put("resume", mResume);
                loginParams = jsonLoginParams.toString();
            }
            catch(JSONException e)
            {
                Log.e("Jobify", "Error creating Login Json File");
            }

            return serverHandler.PUT(urlSpec, loginParams);
        }

        @Override
        protected void onPostExecute(final String response)
        {

            Log.d("Jobify", "Edit Profile Post Response: " + response);

            boolean success = verifyResponse(response);
            String toastMessage = "";

            if (success)
            {
                toastMessage = "Changed saved";
            }
            else
            {
                // Invalid mail and/or password
                toastMessage = "Changed could not be saved";
                mSavingChanges = false;
            }
            Toast.makeText(getActivity(), toastMessage, Toast.LENGTH_SHORT).show();
        }

        private boolean verifyResponse(String response)
        {
            boolean success = false;
            try
            {
                JSONObject putResponse = new JSONObject(response);
                if (putResponse.getString("status").equals(CHANGES_SAVED_RESPONSE))
                {
                    success = true;
                }

            }
            catch (JSONException e)
            {
                Log.e("Jobify", "Error parsing Sign in response " + e.getMessage());
            }
            return success;
        }

        @Override
        protected void onCancelled()
        {
            mSavingChanges = false;
        }
    }
}
