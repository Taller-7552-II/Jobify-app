package com.fiuba.tallerii.jobify;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
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

import org.json.JSONArray;
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

    private boolean mLoggingIn = false;

    private static final String FRIENDLIST_FIELD = "friendList";
    private static final String NAME_FIELD = "name";
    private static final String PICTURE_FIELD = "picture";
    private static final String SKILLS_FIELD = "skillList";
    private static final String JOBS_FIELD = "jobList";
    private static final String NOTIFICATIONS_FIELD = "notificaciones";
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

        mLoggingIn = false;

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

    private void joinApplication()
    {
        Intent intent = new Intent(getActivity(), MainScreenActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
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
            if (mLoggingIn)
            {
                return;
            }
            else
            {
                mLoggingIn = true;
                showProgress(true);
                mAuthTask = new UserLoginTask(email, password, mCheckbox.isChecked());
                mAuthTask.execute((Void) null);
            }
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

            String urlSpec = "http://" + ServerHandler.get(getActivity()).getServerIP() + "/sessions/";
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
        }

        @Override
        protected void onPostExecute(final String response)
        {
            mAuthTask = null;

            Log.d("Jobify", "Log in response: " + response);

            boolean success = verifyResponse(response);

            if (success)
            {
                // Succesful Authentication
                // get data from server to initialize the application
                RequestUserInformationAsyncTask requestInfoTask = new RequestUserInformationAsyncTask();
                requestInfoTask.execute((Void) null);

            }
            else
            {
                // Invalid mail and/or password
                Toast.makeText(getActivity(), getString(R.string.error_invalid_login_info), Toast.LENGTH_SHORT).show(); // DEBUG
                mPasswordEditText.requestFocus();
                mLoggingIn = false;
                showProgress(false);
            }
        }

        private boolean verifyResponse(String response)
        {
            boolean success;
            try
            {
                JSONObject loginResponse = new JSONObject(response);
                String connToken = loginResponse.getString("conn_token");
                ServerHandler.get(getActivity()).setConnectionToken(connToken);
                ServerHandler.get(getActivity()).setUsername(mEmail);
                Log.i("Jobify", "Connected with token: " + connToken);

                success = true;
            }
            catch (JSONException e)
            {
                success = false;
                Log.e("Jobify", "Error parsing Sign in response " + e.getMessage());
            }
            return success;
        }

        @Override
        protected void onCancelled()
        {
            mAuthTask = null;
            showProgress(false);
            mLoggingIn = false;
        }
    }

    /*
    Async class for requesting user information
 */
    public class RequestUserInformationAsyncTask extends AsyncTask<Void, Void, Boolean>
    {
        private String mProfileData;
        private List<String> mContactsData;

        public RequestUserInformationAsyncTask()
        {
            mContactsData = new ArrayList<>();
        }

        @Override
        protected Boolean doInBackground(Void... params)
        {
            //Parsea la informacion de perfil y el perfil de cada amigo
            ServerHandler serverHandler = ServerHandler.get(getActivity());
            String url = "http://" + serverHandler.getServerIP() + "/users/" + serverHandler.getUsername();
            mProfileData = serverHandler.GET(url);
            Log.d("Jobify", "Profile response: " + mProfileData);

            if (!parseBasicInfo(mProfileData))
            {
                return false;
            }
            if (!parseFriendsList(mProfileData))
            {
                return false;
            }
            if (!parseNotifications(mProfileData))
            {
                return false;
            }
            if (!parseSkills(mProfileData))
            {
                return false;
            }
            if (!parseJobs(mProfileData))
            {
                return false;
            }

            for (int i = 0; i < mContactsData.size(); ++i)
            {
                String contactUsername = mContactsData.get(i);
                String requestURL = "http://" + serverHandler.getServerIP() + "/users/" + contactUsername + "/profile/";
                String response = serverHandler.GET(requestURL);
                Log.d("Jobify", "Contact " + contactUsername + " response: " + response);

                if (!parseFriendProfile(response, contactUsername))
                {
                    return false;
                }
            }

            return true;
        }


        @Override
        protected void onPostExecute(Boolean success)
        {
            showProgress(false);
            String toastMessage;

            if (success)
            {
                toastMessage = "Welcome to Jobify!";
                joinApplication();
            }
            else
            {
                toastMessage = "Log in failed";
            }

            Toast.makeText(getActivity(), toastMessage, Toast.LENGTH_SHORT).show();
            mLoggingIn = false;
        }

        private boolean parseBasicInfo(String response)
        {
            boolean success = true;
            try
            {
                JSONObject jsonContactsObject = new JSONObject(response);
                ImageConverter imageConverter = new ImageConverter();

                String name = jsonContactsObject.getString(NAME_FIELD);
                String resume = jsonContactsObject.getString("resume");
                Log.d("Jobify", "name: " + name);
                Bitmap picture = imageConverter.decodeFromBase64ToBitmap(jsonContactsObject.getString(PICTURE_FIELD));

                InformationHolder.get().setMail(ServerHandler.get(getActivity()).getUsername());
                InformationHolder.get().setResume(resume);
                InformationHolder.get().setName(name);
                InformationHolder.get().setProfilePicture(picture);


            }
            catch (JSONException e)
            {
                success = false;
                Log.e("Jobify", "Error parsing user information request response. " + e.getMessage());
            }
            return success;
        }

        private boolean parseFriendsList(String response)
        {
            boolean success = true;
            try
            {
                JSONObject jsonContactsObject = new JSONObject(response);
                JSONArray jsonContactsArray = jsonContactsObject.getJSONArray(FRIENDLIST_FIELD);

                for(int i=0; i < jsonContactsArray.length(); i++)
                {
                    // iterate the JSONArray and extract each contact username to use later
                    String contactUsername = jsonContactsArray.getString(i);
                    Log.d("Jobify", "Friend found: " + contactUsername);
                    mContactsData.add(contactUsername); // agrega mail a la lista para parsear luego los contactos
                }
            }
            catch (JSONException e)
            {
                success = false;
                Log.e("Jobify", "Error parsing friends response. " + e.getMessage());
            }
            return success;
        }

        private boolean parseNotifications(String response)
        {
            boolean success = true;
            try
            {
                JSONObject jsonContactsObject = new JSONObject(response);
                JSONArray jsonContactsArray = jsonContactsObject.getJSONArray(NOTIFICATIONS_FIELD);

                InformationHolder informationHolder = InformationHolder.get();
                for(int i=0; i < jsonContactsArray.length(); i++)
                {
                    String notificationString = jsonContactsArray.getString(i);
                    String notificationCode = notificationString.substring(0, 1);
                    int notCode = Integer.parseInt(notificationCode);
                    String notificationContent = notificationString.substring(2, notificationString.length());

                    Log.d("Jobify", "Notification found: " + notificationString + "code: " + notificationCode + " , content: " + notificationContent);
                    Notification notification = new Notification(notificationContent, notCode);
                    informationHolder.addNotification(notification);
                }
            }
            catch (JSONException e)
            {
                success = false;
                Log.e("Jobify", "Error parsing notifications: " + e.getMessage());
            }
            return success;
        }

        private boolean parseFriendProfile(String response, String friendUsername)
        {
            boolean success = true;
            try
            {
                JSONObject jsonContactsObject = new JSONObject(response);
                ImageConverter imageConverter = new ImageConverter();

                String contactName = jsonContactsObject.getString(NAME_FIELD);
                Log.d("jobify", "friend name: " + contactName);
                Bitmap picture = imageConverter.decodeFromBase64ToBitmap(jsonContactsObject.getString(PICTURE_FIELD));

                List<Skill> skills = parseFriendSkills(response);
                List<Job> jobs = parseFriendJobs(response);

                Contact contact = new Contact(contactName, friendUsername, picture);
                contact.setSkills(skills);
                contact.setJobs(jobs);
                InformationHolder.get().addContact(contact);

            }
            catch (JSONException e)
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

                Log.d("Jobify", "Skills Json Array: " + jsonSkillsArray);

                for(int i=0; i < jsonSkillsArray.length(); i++)
                {
                    // iterate the JSONArray and extract each skill

                    Log.d("Jobify", "Skills Json first object: " + jsonSkillsArray.getString(i));
                    JSONObject jsonSkillObject = new JSONObject(jsonSkillsArray.getString(i));
                    String skillTittle = jsonSkillObject.getString("name");
                    String skillDescription = jsonSkillObject.getString("description");
                    String skillCategory = jsonSkillObject.getString("category");

                    Skill skill = new Skill(skillTittle, skillCategory, skillDescription);
                    InformationHolder.get().addSkill(skill);
                }
            }
            catch (JSONException e)
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

                for(int i=0; i < jsonJobsArray.length(); i++)
                {
                    // iterate the JSONArray and extract each job
                    JSONObject jsonJobObject = new JSONObject(jsonJobsArray.getString(i));
                    String jobTittle = jsonJobObject.getString("name");
                    String jobDescription = jsonJobObject.getString("description");
                    String jobCategory = jsonJobObject.getString("category");

                    Job job = new Job(jobTittle, jobCategory, jobDescription);
                    InformationHolder.get().addJob(job);
                }
            }
            catch (JSONException e)
            {
                success = false;
                Log.e("Jobify", "Error parsing jobs. " + e.getMessage());
            }
            return success;
        }

        private List<Skill> parseFriendSkills(String response)
        {
            List<Skill> skills = new ArrayList<>();
            try
            {
                JSONObject jsonObject = new JSONObject(response);

                String fixedSkillsJson = jsonObject.getString(SKILLS_FIELD);
                JSONArray jsonSkillsArray = new JSONArray(fixedSkillsJson);

                for(int i=0; i < jsonSkillsArray.length(); i++)
                {
                    JSONObject jsonSkillObject = new JSONObject(jsonSkillsArray.getString(i));
                    String skillTittle = jsonSkillObject.getString("name");
                    String skillDescription = jsonSkillObject.getString("description");
                    String skillCategory = jsonSkillObject.getString("category");

                    Skill skill = new Skill(skillTittle, skillCategory, skillDescription);
                    skills.add(skill);
                }
            }
            catch (JSONException e)
            {
                Log.e("Jobify", "Error parsing friend skills. " + e.getMessage());
            }
            return skills;
        }

        private List<Job> parseFriendJobs(String response)
        {
            List<Job> jobs = new ArrayList<>();
            try
            {
                JSONObject jsonObject = new JSONObject(response);

                String fixedJobsJson = jsonObject.getString(JOBS_FIELD);
                JSONArray jsonJobsArray = new JSONArray(fixedJobsJson);

                for(int i=0; i < jsonJobsArray.length(); i++)
                {
                    JSONObject jsonJobObject = new JSONObject(jsonJobsArray.getString(i));
                    String jobTittle = jsonJobObject.getString("name");
                    String jobDescription = jsonJobObject.getString("description");
                    String jobCategory = jsonJobObject.getString("category");

                    Job job = new Job(jobTittle, jobCategory, jobDescription);
                    jobs.add(job);
                }
            }
            catch (JSONException e)
            {
                Log.e("Jobify", "Error parsing friend jobs. " + e.getMessage());
            }
            return jobs;
        }



        @Override
        protected void onCancelled()
        {
            mLoggingIn = false;
            showProgress(false);
        }
    }

}
