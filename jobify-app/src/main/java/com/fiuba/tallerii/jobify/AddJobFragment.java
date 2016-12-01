package com.fiuba.tallerii.jobify;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import java.util.List;

public class AddJobFragment extends Fragment
{
    private EditText mTitleEditText;
    private EditText mCategoryEditText;
    private EditText mDescriptionEditText;
    private Button mAddJobButton;

    private static final String ADD_SKILL_RESPONSE = "Changes saved";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_add_job, container, false);

        mTitleEditText = (EditText) v.findViewById(R.id.edit_text_title_add_job);
        mCategoryEditText = (EditText) v.findViewById(R.id.edit_text_category_add_job);
        mDescriptionEditText = (EditText) v.findViewById(R.id.edit_text_description_add_job);

        mTitleEditText.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent)
            {
                if (id == R.id.add_job || id == EditorInfo.IME_NULL)
                {
                    mCategoryEditText.requestFocus();
                    return true;
                }
                return false;
            }
        });

        mCategoryEditText.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent)
            {
                if (id == R.id.add_job || id == EditorInfo.IME_NULL)
                {
                    mDescriptionEditText.requestFocus();
                    return true;
                }
                return false;
            }
        });


        mDescriptionEditText = (EditText) v.findViewById(R.id.edit_text_description_add_job);
        mDescriptionEditText.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent)
            {
                if (id == R.id.add_job || id == EditorInfo.IME_NULL)
                {
                    addJob();
                    return true;
                }
                return false;
            }
        });

        mAddJobButton = (Button) v.findViewById(R.id.button_add_job);
        mAddJobButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                addJob();
            }
        });

        return v;
    }

    private void addJob()
    {
        String tittle = mTitleEditText.getText().toString();
        String category = mCategoryEditText.getText().toString();
        String description = mDescriptionEditText.getText().toString();

        AddJobAsyncTask addSkillTask = new AddJobAsyncTask(tittle, category, description);
        addSkillTask.execute();

    }


    /*
    Add Job class
 */
    public class AddJobAsyncTask extends AsyncTask<Void, Void, String>
    {
        String mTitle;
        String mCategory;
        String mDescription;

        public AddJobAsyncTask(String title, String category, String description)
        {
            mTitle = title;
            mCategory = category;
            mDescription = description;
        }
        @Override
        protected String doInBackground(Void... params)
        {
            ServerHandler serverHandler = ServerHandler.get(getActivity());
            String url = "http://" + serverHandler.getServerIP() + "/users/" + serverHandler.getUsername();

            String putParams = "{}";
            try
            {
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
                JSONObject jobJson = new JSONObject();
                jobJson.put("name", mTitle);
                jobJson.put("category", mCategory);
                jobJson.put("description", mDescription);
                jsonArray.put(jobJson);

                //hotfix
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

                JSONObject json = new JSONObject();
                json.put("jobList", jsonArray.toString());
                json.put("skillList", jsonSkillArray.toString());

                json.put("name", InformationHolder.get().getName());
                json.put("resume", InformationHolder.get().getResume());
                json.put("email", ServerHandler.get(getActivity()).getUsername());
                ImageConverter imageConverter = new ImageConverter();
                String pictureBase65 = imageConverter.convertToBase64(InformationHolder.get().getProfilePicture());
                json.put("picture", pictureBase65);

                putParams = json.toString();
                Log.d("Jobify","Add Job Put Parameters: " +  putParams);

            }
            catch(JSONException e)
            {
                Log.e("Jobify", "Error creating add Job Json File");
            }
            return serverHandler.PUT(url, putParams);
        }

        @Override
        protected void onPostExecute(final String response)
        {
            Log.d("Jobify", "Add Job response: " + response);
            try
            {
                JSONObject jsonObjectResponse = new JSONObject(response);
                String status = jsonObjectResponse.getString("status");
                boolean success = status.equals(ADD_SKILL_RESPONSE); //cambiar parseando el string
                if (success)
                {
                    Toast.makeText(getActivity(), "Job added", Toast.LENGTH_LONG).show();

                    Job job = new Job(mTitle, mCategory, mDescription);
                    InformationHolder.get().addJob(job);

                    getActivity().finish();
                }
            }
            catch (JSONException e)
            {
                mTitleEditText.requestFocus();
                Log.e("Jobify", "Error parsing add job response. " + e.getMessage());
            }
        }


        @Override
        protected void onCancelled()
        {
        }
    }
}
