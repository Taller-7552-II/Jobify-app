package com.fiuba.tallerii.jobify;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class ProfileFragment extends Fragment
{
    private static int IMG_RESULT = 1;

    private Button mSaveChangesButton;
    private ImageView mProfilePicture;

    private RelativeLayout mNameLayout;
    private EditText mNameEditText;

    private TextView mEmailTextView;
    private TextView mFriendAmountsTextView;

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

        mEmailTextView = (TextView) v.findViewById(R.id.profile_email);
        mFriendAmountsTextView = (TextView) v.findViewById(R.id.profile_amount_friends);

        mProfilePicture = (ImageView) v.findViewById(R.id.fragment_profile_picture);
        mProfilePicture.setDrawingCacheEnabled(true);
        mProfilePicture.setImageBitmap(InformationHolder.get().getProfilePicture());
        mProfilePicture.setOnClickListener(new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            chooseProfilePicture();
        }
    });

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

        updateFields();

        return v;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        mSavingChanges = false;

        updateFields();
    }

    private void updateFields()
    {
        mFriendAmountsTextView.setText("Contacts: " + InformationHolder.get().getContacts().size());
        mNameEditText.setText(InformationHolder.get().getName());
        mResumeEditText.setText(InformationHolder.get().getResume());
        mEmailTextView.setText(ServerHandler.get(getActivity()).getUsername());
        mProfilePicture.setImageBitmap(InformationHolder.get().getProfilePicture());

    }

    private void chooseProfilePicture()
    {
        Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, IMG_RESULT);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        try
        {
            if (requestCode == IMG_RESULT && resultCode == Activity.RESULT_OK && null != data)
            {
                Uri URI = data.getData();
                String[] FILE = { MediaStore.Images.Media.DATA };

                Cursor cursor = getActivity().getContentResolver().query(URI,
                        FILE, null, null, null);

                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(FILE[0]);
                String ImageDecode = cursor.getString(columnIndex);
                cursor.close();

                Bitmap imageBitmap = BitmapFactory.decodeFile(ImageDecode);
                InformationHolder.get().setProfilePicture(imageBitmap);
                mProfilePicture.setImageBitmap(imageBitmap);

                EditPictureTask editPictureTask = new EditPictureTask(imageBitmap);
                editPictureTask.execute();
            }
        } catch (Exception e)
        {
            Toast.makeText(getActivity(), "Please try again", Toast.LENGTH_LONG).show();
        }

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

                    mProfilePicture.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                    mProfilePicture.layout(0, 0, mProfilePicture.getMeasuredWidth(), mProfilePicture.getMeasuredHeight());

                    mProfilePicture.buildDrawingCache(true);
                    Bitmap bitmap = Bitmap.createBitmap(mProfilePicture.getDrawingCache());

                    EditProfileTask editProfileTask = new EditProfileTask(name, resume, bitmap);
                    editProfileTask.execute((Void)null);
                }
            }
        });
    }


    /**
     * Represents an asynchronous editing profile task
     */
    public class EditProfileTask extends AsyncTask<Void, Void, String>
    {

        private final String mName;
        private final String mResume;
        private Bitmap mProfilePicture;

        EditProfileTask(String name, String resume, Bitmap picture)
        {
            mName = name;
            mResume = resume;
            mProfilePicture = picture;
        }

        @Override
        protected String doInBackground(Void... params)
        {
            ServerHandler serverHandler = ServerHandler.get(getActivity());
            String urlSpec = "http://" + serverHandler.getServerIP() + "/users/" + serverHandler.getUsername();
            String loginParams = "";
            try
            {
                //hotfix
                //hotfix
                JSONArray jsonArray = new JSONArray();
                List<Job> jobs = InformationHolder.get().getJobs();
                for (Job job: jobs)
                {
                    JSONObject jobJson = new JSONObject();
                    jobJson.put("name", job.getTittle());
                    jobJson.put("category", job.getCategory());
                    jobJson.put("description", job.getDescription());

                    jsonArray.put(jobJson);
                }

                List<Skill> skills = InformationHolder.get().getSkills();
                JSONArray jsonSkillArray = new JSONArray();
                for (Skill skill: skills)
                {
                    JSONObject skillJson = new JSONObject();
                    skillJson.put("name", skill.getTittle());
                    skillJson.put("category", skill.getCategory());
                    skillJson.put("description", skill.getDescription());

                    jsonSkillArray.put(skillJson);
                }


                JSONObject jsonPutParams= new JSONObject();
                jsonPutParams.put("name", mName);
                jsonPutParams.put("resume", mResume);
                jsonPutParams.put("email", ServerHandler.get(getActivity()).getUsername());
                jsonPutParams.put("skillList", jsonSkillArray.toString());
                jsonPutParams.put("jobList", jsonArray.toString());

                ImageConverter imageConverter = new ImageConverter();
                String pictureBase65 = imageConverter.convertToBase64(mProfilePicture);
                jsonPutParams.put("picture", pictureBase65);

                loginParams = jsonPutParams.toString();
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

    /**
     * Represents an asynchronous posting the profile picture
     */
    public class EditPictureTask extends AsyncTask<Void, Void, String>
    {

        private Bitmap mProfilePicture;

        EditPictureTask(Bitmap picture)
        {
            mProfilePicture = picture;
        }

        @Override
        protected String doInBackground(Void... params)
        {
            ServerHandler serverHandler = ServerHandler.get(getActivity());
            String urlSpec = "http://" + serverHandler.getServerIP() + "/users/" + serverHandler.getUsername();
            String putParams = "";
            try
            {
                //hotfix
                JSONArray jsonArray = new JSONArray();
                List<Job> jobs = InformationHolder.get().getJobs();
                for (Job job: jobs)
                {
                    JSONObject jobJson = new JSONObject();
                    jobJson.put("name", job.getTittle());
                    jobJson.put("category", job.getCategory());
                    jobJson.put("description", job.getDescription());

                    jsonArray.put(jobJson);
                }

                List<Skill> skills = InformationHolder.get().getSkills();
                JSONArray jsonSkillArray = new JSONArray();
                for (Skill skill: skills)
                {
                    JSONObject skillJson = new JSONObject();
                    skillJson.put("name", skill.getTittle());
                    skillJson.put("category", skill.getCategory());
                    skillJson.put("description", skill.getDescription());

                    jsonSkillArray.put(skillJson);
                }


                ImageConverter imageConverter = new ImageConverter();
                String encodedImage = imageConverter.convertToBase64(mProfilePicture);

                JSONObject jsonPutParams= new JSONObject();
                jsonPutParams.put("picture", encodedImage);
                jsonPutParams.put("skillList", jsonArray.toString());
                jsonPutParams.put("jobList", jsonSkillArray.toString());
                putParams = jsonPutParams.toString();
            }
            catch(JSONException e)
            {
                Log.e("Jobify", "Error creating profile picture Json File");
            }

            return serverHandler.PUT(urlSpec, putParams);
        }

        @Override
        protected void onPostExecute(final String response)
        {

            Log.d("Jobify", "Edit Picture Post Response: " + response);

            boolean success = verifyResponse(response);
            String toastMessage = "";

            if (success)
            {
                toastMessage = "Picture saved";
            }
            else
            {
                // Invalid mail and/or password
                toastMessage = "Picture could not be saved";
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
                Log.e("Jobify", "Error parsing picture response " + e.getMessage());
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
