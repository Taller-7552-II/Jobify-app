package com.fiuba.tallerii.jobify;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
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
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 *
 * Sign Up Form - Has some duplicated code with Log In Fragment
 */
public class SignUpFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>
{
    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    private UserSignUpTask mSignUpTask = null;

    // UI references.
    private AutoCompleteTextView mEmailAutocompleteText;
    private EditText mFirstNameEditText;
    private EditText mLastNameEditText;
    private EditText mPasswordEditText;
    private View mProgressView;
    private View mSignUpFormView;

    private Button mSignUpButton;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_signup, container, false);

        // Set up the sign up form.
        mEmailAutocompleteText = (AutoCompleteTextView) v.findViewById(R.id.signup_email);
        populateAutoComplete();

        mFirstNameEditText  = (EditText) v.findViewById(R.id.signup_first_name);
        mLastNameEditText  = (EditText) v.findViewById(R.id.signup_last_name);

        mPasswordEditText = (EditText) v.findViewById(R.id.signup_password);
        mPasswordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent)
            {
                if (id == R.id.sign_up || id == EditorInfo.IME_NULL)
                {
                    attemptSignUp();
                    return true;
                }
                return false;
            }
        });

        mSignUpButton = (Button) v.findViewById(R.id.signup_signup_button);
        mSignUpButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                attemptSignUp();
            }
        });

        mSignUpFormView = v.findViewById(R.id.signup_form);
        mProgressView = v.findViewById(R.id.signup_progress);
        return v;
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
    private void attemptSignUp()
    {
        if (mSignUpTask != null)
        {
            return;
        }

        // Reset errors.
        mFirstNameEditText.setError(null);
        mLastNameEditText.setError(null);
        mEmailAutocompleteText.setError(null);
        mPasswordEditText.setError(null);

        // Store values at the time of the login attempt.
        String firstName = mFirstNameEditText.getText().toString();
        String lastName = mLastNameEditText.getText().toString();
        String email = mEmailAutocompleteText.getText().toString();
        String password = mPasswordEditText.getText().toString();

        boolean cancel = false;
        View focusView = null;

        FieldValidator fieldValidator = new FieldValidator();

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !fieldValidator.isPasswordValid(password))
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

        //Check for a valid last name, if the user entered one
        if (!TextUtils.isEmpty(lastName) && !fieldValidator.isNameValid(lastName))
        {
            mLastNameEditText.setError(getString(R.string.error_invalid_name));
            focusView = mLastNameEditText;
            cancel = true;
        }

        //Check for a valid first name, if the user entered one
        if (!TextUtils.isEmpty(firstName) && !fieldValidator.isNameValid(firstName))
        {
            mFirstNameEditText.setError(getString(R.string.error_invalid_name));
            focusView = mFirstNameEditText;
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
            User user = new User(firstName, lastName, email, password);
            mSignUpTask = new UserSignUpTask(user);
            mSignUpTask.execute((Void) null);
        }
    }

    /**
     * Shows the progress UI and hides the sign up form.
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

            mSignUpFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mSignUpFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter()
            {
                @Override
                public void onAnimationEnd(Animator animation)
                {
                    mSignUpFormView.setVisibility(show ? View.GONE : View.VISIBLE);
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
            mSignUpFormView.setVisibility(show ? View.GONE : View.VISIBLE);
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
     * Represents an asynchronous registration task used to register a new user
     *
     */
    public class UserSignUpTask extends AsyncTask<Void, Void, Boolean>
    {
        private final User mUser;

        UserSignUpTask(User user)
        {
            mUser = user;
        }

        @Override
        protected Boolean doInBackground(Void... params)
        {
            // TODO: Put new user to server
            ServerHandler serverHandler = ServerHandler.get(getActivity());
            serverHandler.addUser(mUser);

            try
            {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e)
            {
                return false;
            }

            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success)
        {
            mSignUpTask = null;
            showProgress(false);

            if (success)
            {
                Toast.makeText(getActivity(), getString(R.string.prompt_registration_complete), Toast.LENGTH_SHORT).show();
                getActivity().finish();
            } else
            {
                mPasswordEditText.setError(getString(R.string.error_incorrect_password));
                mPasswordEditText.requestFocus();
            }
        }

        @Override
        protected void onCancelled()
        {
            mSignUpTask = null;
            showProgress(false);
        }
    }

}
