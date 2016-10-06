package com.fiuba.tallerii.jobify;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

public class MenuFragment extends Fragment
{
    EditText mUrlEditText;
    EditText mContentEditText;

    RadioButton mRadioButtonGet;
    RadioButton mRadioButtonPost;
    RadioButton mRadioButtonPut;
    RadioButton mRadioButtonDelete;

    private View mProgressView;

    Button mSubmitButton;

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

        mContentEditText = (EditText) v.findViewById(R.id.menu_content_edit_text);
        mUrlEditText = (EditText) v.findViewById(R.id.menu_edit_text_url);
        mUrlEditText.setText("jobify-professional.herokuapp.com/db");
        mRadioButtonGet = (RadioButton) v.findViewById(R.id.menu_radio_button_get);
        mRadioButtonGet.setChecked(true);
        mRadioButtonPost = (RadioButton) v.findViewById(R.id.menu_radio_button_post);
        mRadioButtonPut = (RadioButton) v.findViewById(R.id.menu_radio_button_put);
        mRadioButtonDelete = (RadioButton) v.findViewById(R.id.menu_radio_button_delete);

        mProgressView = v.findViewById(R.id.http_menu_progress);

        mSubmitButton = (Button) v.findViewById(R.id.menu_button_submit);
        mSubmitButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                SubmitRequest();
            }
        });

        return v;
    }

    private void SubmitRequest()
    {
        mContentEditText.setText("");
        String urlSpec = "http://" + ServerHandler.get(getActivity()).getServerIP() + mUrlEditText.getText().toString();
        if (mRadioButtonGet.isChecked())
        {
            showProgress(true);
            GetAsyncTask getTask = new GetAsyncTask(urlSpec);
            getTask.execute();
        }
        else if (mRadioButtonPost.isChecked())
        {
            showProgress(true);
            PostAsyncTask postAsyncTask = new PostAsyncTask(urlSpec, mContentEditText.getText().toString());
            postAsyncTask.execute();
        }
        else
        {
            mContentEditText.setText("Nope!");
        }
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
            mContentEditText.setText(s);
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
        }

        @Override
        protected void onCancelled()
        {
            showProgress(false);
        }
    }
}
