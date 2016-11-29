package com.fiuba.tallerii.jobify;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

public class LogInFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>
{
    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mEmailAutocompleteText;
    private EditText mPasswordEditText;
    private View mProgressView;
    private View mLoginFormView;

    private Button mSignInButton;
    private Button mSignUpButton;

     // Variables temporales para configurar Conexión con el servidor
    private EditText mIPEditText;
    private Button mSubmitIPButton;
    private CheckBox mCheckbox;
    //**************************************************************

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        // Set up the login form.
        mEmailAutocompleteText = (AutoCompleteTextView) v.findViewById(R.id.email);
        populateAutoComplete();

        mPasswordEditText = (EditText) v.findViewById(R.id.password);
        mPasswordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent)
            {
                if (id == R.id.login || id == EditorInfo.IME_NULL)
                {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        mSignInButton = (Button) v.findViewById(R.id.sign_in_button);
        mSignInButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                attemptLogin();
            }
        });

        mSignUpButton = (Button) v.findViewById(R.id.login_sign_up_button);
        mSignUpButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //Go to Sign Up Screen
                OpenSignUpActivity();
            }
        });

        mCheckbox = (CheckBox) v.findViewById(R.id.login_checkbox_auth);

         // Debug Information for checkpoint 2
        mIPEditText = (EditText) v.findViewById(R.id.debug_ip_edittext);
        mIPEditText.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent)
            {
                if (id == R.id.debug_submit_ip || id == EditorInfo.IME_NULL)
                {
                    SubmitIP(mIPEditText.getText().toString());
                    return true;
                }
                return false;
            }
        });
        mIPEditText.setText(ServerHandler.get(getActivity()).getServerIP());
        mSubmitIPButton = (Button) v.findViewById(R.id.debug_submit_button);
        mSubmitIPButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                SubmitIP(mIPEditText.getText().toString());
            }
        });

        //***********************************

        mLoginFormView = v.findViewById(R.id.login_form);
        mProgressView = v.findViewById(R.id.login_progress);
        return v;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        resetFields();
    }

    private void resetFields()
    {
        mEmailAutocompleteText.setError(null);
        mEmailAutocompleteText.setText("");
        mPasswordEditText.setError(null);
        mPasswordEditText.setText("");
        mEmailAutocompleteText.requestFocus();
        mIPEditText.setText(ServerHandler.get(getActivity()).getServerIP());
    }

    private void OpenSignUpActivity()
    {
        Intent intent = new Intent(getActivity(), SignUpActivity.class);
        startActivity(intent);
    }

    private void SubmitIP(String ip)
    {
        ServerHandler.get(getActivity()).setServerIP(ip);
        Toast.makeText(getActivity(), "IP: " + ip + " submitted!", Toast.LENGTH_SHORT).show();
    }

    private void populateAutoComplete()
    {
        if (!mayRequestContacts())
        {
            return;
        }

        getLoaderManager().initLoader(0, null, this);
    }

    private boolean mayRequestContacts()
    {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
        {
            return true;
        }
        if (getActivity().checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED)
        {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS))
        {
            Snackbar.make(mEmailAutocompleteText, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener()
                    {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v)
                        {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else
        {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults)
    {
        if (requestCode == REQUEST_READ_CONTACTS)
        {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                populateAutoComplete();
            }
        }
    }


    /**
     * Attempts to sign in the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin()
    {
        if (mAuthTask != null)
        {
            return;
        }

        // Reset errors.
        mEmailAutocompleteText.setError(null);
        mPasswordEditText.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailAutocompleteText.getText().toString();
        String password = mPasswordEditText.getText().toString();

        boolean cancel = false;
        View focusView = null;

        FieldValidator fieldValidator = new FieldValidator();

        // Check for a valid password
        if (TextUtils.isEmpty(password))
        {
            mPasswordEditText.setError(getString(R.string.error_field_required));
            focusView = mPasswordEditText;
            cancel = true;
        }
        else if (!fieldValidator.isPasswordValid(password))
        {
            mPasswordEditText.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordEditText;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email))
        {
            mEmailAutocompleteText.setError(getString(R.string.error_field_required));
            focusView = mEmailAutocompleteText;
            cancel = true;
        } else if (!fieldValidator.isEmailValid(email))
        {
            mEmailAutocompleteText.setError(getString(R.string.error_invalid_email));
            focusView = mEmailAutocompleteText;
            cancel = true;
        }

        if (cancel)
        {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else
        {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(email, password, mCheckbox.isChecked());
            mAuthTask.execute((Void) null);
        }
    }

    /**
     * Shows the progress UI and hides the login form.
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

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter()
            {
                @Override
                public void onAnimationEnd(Animator animation)
                {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

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
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle)
    {
        return new CursorLoader(getActivity(),
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor)
    {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader)
    {

    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection)
    {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(getActivity(),
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailAutocompleteText.setAdapter(adapter);
    }

    private interface ProfileQuery
    {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    /**
     * Represents an asynchronous login task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, String>
    {

        private final String mEmail;
        private final String mPassword;
        boolean mUseAuthentication;

        UserLoginTask(String email, String password, boolean useAuthentication)
        {
            mEmail = email;
            mPassword = password;
            mUseAuthentication = useAuthentication;
        }

        @Override
        protected String doInBackground(Void... params)
        {
            // TODO: attempt authentication against a network service. APPSERVER/HEROKU


            String urlSpec = "http://" + ServerHandler.get(getActivity()).getServerIP() + "/sessions/" + mEmail;
            String loginParams = "";
            try
            {
                JSONObject jsonLoginParams= new JSONObject();
                jsonLoginParams.put("username", mEmail);
                jsonLoginParams.put("password", mPassword);
                loginParams = jsonLoginParams.toString();
            }
            catch(JSONException e)
            {
                Log.e("Jobify", "Error creating Login Json File");
            }

            return ServerHandler.get(getActivity()).POST(urlSpec, loginParams);
           /* ArrayList<String> credentials = serverHandler.getCredentials();
            for (String credential : credentials)
            {
                String[] pieces = credential.split(":");
                if (pieces[0].equals(mEmail))
                {
                    // Account exists, return true if the password matches.
                    return pieces[1].equals(mPassword);
                }
            }*/

            //return false;
        }

        @Override
        protected void onPostExecute(final String response)
        {
            mAuthTask = null;
            showProgress(false);

            boolean success = verifyResponse(response);

            //TODO: use login authentication result
           /* if (success)
            {*/
                // Succesful Authentication
                Intent intent = new Intent(getActivity(), MainScreenActivity.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
           /* } else
            {
                // Invalid mail and/or password
                Toast.makeText(getActivity(), getString(R.string.error_invalid_login_info), Toast.LENGTH_SHORT).show(); // DEBUG
                mPasswordEditText.setError(getString(R.string.error_incorrect_password));
                mPasswordEditText.requestFocus();
            }*/
        }

        private boolean verifyResponse(String response)
        {
            boolean success = false;
            String toastMessage;
            try
            {
                JSONObject loginResponse = new JSONObject(response);
                toastMessage = loginResponse.getString("status");
                String connToken = loginResponse.getString("conn_token");
                ServerHandler.get(getActivity()).setConnectionToken(connToken);
                Log.i("Jobify", "Connected with token: " + connToken);
            }
            catch (JSONException e)
            {
                success = false;
                toastMessage = "Log in failed";
                Log.e("Jobify", "Error parsing Sign in response " + e.getMessage());
            }
            Toast.makeText(getActivity(), toastMessage, Toast.LENGTH_SHORT).show();

            //TODO devolver success
            return true;
        }

        @Override
        protected void onCancelled()
        {
            mAuthTask = null;
            showProgress(false);
        }
    }
}
