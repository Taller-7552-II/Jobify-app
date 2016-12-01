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

public class AddSkillFragment extends Fragment
{
    private EditText mTitleEditText;
    private EditText mCategoryEditText;
    private EditText mDescriptionEditText;
    private Button mAddSkillButton;

    private static final String ADD_SKILL_RESPONSE = "Changes saved";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_add_skill, container, false);

        mTitleEditText = (EditText) v.findViewById(R.id.edit_text_title_add_skill);
        mCategoryEditText = (EditText) v.findViewById(R.id.edit_text_category_add_skill);
        mDescriptionEditText = (EditText) v.findViewById(R.id.edit_text_description_add_skill);

        mTitleEditText.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent)
            {
                if (id == R.id.add_skill || id == EditorInfo.IME_NULL)
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
                if (id == R.id.add_skill || id == EditorInfo.IME_NULL)
                {
                    mDescriptionEditText.requestFocus();
                    return true;
                }
                return false;
            }
        });

        mDescriptionEditText = (EditText) v.findViewById(R.id.edit_text_description_add_skill);
        mDescriptionEditText.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent)
            {
                if (id == R.id.add_skill || id == EditorInfo.IME_NULL)
                {
                    addSkill();
                    return true;
                }
                return false;
            }
        });

        mAddSkillButton = (Button) v.findViewById(R.id.button_add_skill);
        mAddSkillButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                addSkill();
            }
        });

        return v;
    }

    private void addSkill()
    {
        String tittle = mTitleEditText.getText().toString();
        String category = mCategoryEditText.getText().toString();
        String description = mDescriptionEditText.getText().toString();

        AddSkillAsyncTask addSkillTask = new AddSkillAsyncTask(tittle, category, description);
        addSkillTask.execute();

    }


    /*
    Add Skill class
 */
    public class AddSkillAsyncTask extends AsyncTask<Void, Void, String>
    {
        String mTitle;
        String mCategory;
        String mDescription;

        public AddSkillAsyncTask(String title, String category, String description)
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
                List<Skill> skills = InformationHolder.get().getSkills();

                for (Skill skill: skills)
                {
                    JSONObject skillJson = new JSONObject();
                    skillJson.put("name", skill.getTittle());
                    skillJson.put("category", skill.getCategory());
                    skillJson.put("description", skill.getDescription());

                    jsonArray.put(skillJson);
                }
                JSONObject skillJson = new JSONObject();
                skillJson.put("name", mTitle);
                skillJson.put("category", mCategory);
                skillJson.put("description", mDescription);
                jsonArray.put(skillJson);

                JSONObject json = new JSONObject();
                json.put("skillList", jsonArray.toString());
                putParams = json.toString();
                Log.d("Jobify","Add Skill Put Parameters: " +  putParams);

            }
            catch(JSONException e)
            {
                Log.e("Jobify", "Error creating add Skill Json File");
            }
            return serverHandler.PUT(url, putParams);
        }

        @Override
        protected void onPostExecute(final String response)
        {
            Log.d("Jobify", "Add Skill response: " + response);
            try
            {
                JSONObject jsonObjectResponse = new JSONObject(response);
                String status = jsonObjectResponse.getString("status");
                boolean success = status.equals(ADD_SKILL_RESPONSE); //cambiar parseando el string
                if (success)
                {
                    Toast.makeText(getActivity(), "Skill added", Toast.LENGTH_LONG).show();

                    Skill skill = new Skill(mTitle, mCategory, mDescription);
                    InformationHolder.get().addSkill(skill);

                    getActivity().finish();
                }
            }
            catch (JSONException e)
            {
                mTitleEditText.requestFocus();
                Log.e("Jobify", "Error parsing add skill response. " + e.getMessage());
            }
        }


        @Override
        protected void onCancelled()
        {
        }
    }
}
