package com.fiuba.tallerii.jobify;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

public class MenuFragment extends Fragment
{
    EditText mUrlEditText;
    EditText mResponseEditText;
    EditText mPayloadEditText;

    Button mSearchProfessionalButton;
    Button mViewProfileButton;
    Button mEditProfileButton;
    Button mResetFieldsButton;

    private View mProgressView;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_menu, container, false);

        mResponseEditText = (EditText) v.findViewById(R.id.menu_content_edit_text);
        mPayloadEditText = (EditText) v.findViewById(R.id.menu_payload_edit_text);
        mUrlEditText = (EditText) v.findViewById(R.id.menu_edit_text_url);
        mUrlEditText.setText("/");

        mProgressView = v.findViewById(R.id.http_menu_progress);

        mSearchProfessionalButton = (Button) v.findViewById(R.id.button_search_proffesional);
        mSearchProfessionalButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                searchProfessional();
            }
        });

        mViewProfileButton = (Button) v.findViewById(R.id.button_view_profile);
        mViewProfileButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                viewProfile();
            }
        });

        mEditProfileButton = (Button) v.findViewById(R.id.button_edit_profile);
        mEditProfileButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                editProfile();
            }
        });

        mResetFieldsButton = (Button) v.findViewById(R.id.action_reset_fields);
        mResetFieldsButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                mResponseEditText.setText("");
                mPayloadEditText.setText("");
            }
        });

        return v;
    }

    private void searchProfessional()
    {
        String urlSpec = "http://" + ServerHandler.get(getActivity()).getServerIP() + mUrlEditText.getText().toString();
        showProgress(true);
        GetAsyncTask getTask = new GetAsyncTask(urlSpec);
        getTask.execute();
    }

    private void viewProfile()
    {
        String urlSpec = "http://" + ServerHandler.get(getActivity()).getServerIP() + mUrlEditText.getText().toString();
        showProgress(true);
        GetAsyncTask getTask = new GetAsyncTask(urlSpec);
        getTask.execute();
    }

    private void editProfile()
    {
        String urlSpec = "http://" + ServerHandler.get(getActivity()).getServerIP() + mUrlEditText.getText().toString();
        showProgress(true);
        if (mPayloadEditText.getText().toString().isEmpty())
        {
            try
            {
                JSONObject jsonParam = new JSONObject();
                jsonParam.put("mail", "");
                jsonParam.put("password", "");
                jsonParam.put("name", "");
                jsonParam.put("lastName", "");
                mPayloadEditText.setText(jsonParam.toString());
            } catch (JSONException e)
            {
                Log.e("Json Error", "Error creating Json File");
            }
        }
        PostAsyncTask postAsyncTask = new PostAsyncTask(urlSpec, mPayloadEditText.getText().toString());
        postAsyncTask.execute();
    }


    /**
     * Shows the progress UI while doing a request
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show)
    {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2)
        {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);


            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter()
            {
                @Override
                public void onAnimationEnd(Animator animation)
                {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else
        {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }

    /**
     *
     * Receive an http connection ready to execute a GET operation
     */
    private class GetAsyncTask extends AsyncTask<Void, Void, String>
    {
        String mUrlSpec;

        public GetAsyncTask(String urlSpec)
        {
            mUrlSpec = urlSpec;
        }

        @Override
        protected String doInBackground(Void... voids)
        {
            return ServerHandler.get(getActivity()).GET(mUrlSpec);
        }

        @Override
        protected void onPostExecute(String s)
        {
            showProgress(false);
            super.onPostExecute(s);
            mResponseEditText.setText(s);
        }

        @Override
        protected void onCancelled()
        {
            showProgress(false);
        }
    }

    private class PostAsyncTask extends AsyncTask<Void, Void, String>
    {
        String mUrlSpec;
        String mParameters;

        public PostAsyncTask(String urlSpec, String parameters)
        {
            mUrlSpec = urlSpec;
            mParameters = parameters;
        }

        @Override
        protected String doInBackground(Void... voids)
        {
            return ServerHandler.get(getActivity()).POST(mUrlSpec, mParameters);
        }

        @Override
        protected void onPostExecute(String s)
        {
            showProgress(false);
            super.onPostExecute(s);
            mResponseEditText.setText(s);
        }

        @Override
        protected void onCancelled()
        {
            showProgress(false);
        }
    }
}
