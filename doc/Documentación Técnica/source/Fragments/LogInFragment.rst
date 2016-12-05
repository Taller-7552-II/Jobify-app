.. java:import:: android.animation.Animator;
.. java:import:: android.animation.AnimatorListenerAdapter;
.. java:import:: android.annotation.TargetApi;
.. java:import:: android.content.Intent;
.. java:import:: android.content.pm.PackageManager;
.. java:import:: android.database.Cursor;
.. java:import:: android.graphics.Bitmap;
.. java:import:: android.net.Uri;
.. java:import:: android.os.AsyncTask;
.. java:import:: android.os.Build;
.. java:import:: android.os.Bundle;
.. java:import:: android.provider.ContactsContract;
.. java:import:: android.support.annotation.NonNull;
.. java:import:: android.support.design.widget.Snackbar;
.. java:import:: android.support.v4.app.Fragment;
.. java:import:: android.support.v4.app.LoaderManager;
.. java:import:: android.support.v4.content.CursorLoader;
.. java:import:: android.support.v4.content.Loader;
.. java:import:: android.text.TextUtils;
.. java:import:: android.util.Log;
.. java:import:: android.view.KeyEvent;
.. java:import:: android.view.LayoutInflater;
.. java:import:: android.view.View;
.. java:import:: android.view.ViewGroup;
.. java:import:: android.view.inputmethod.EditorInfo;
.. java:import:: android.widget.ArrayAdapter;
.. java:import:: android.widget.AutoCompleteTextView;
.. java:import:: android.widget.Button;
.. java:import:: android.widget.CheckBox;
.. java:import:: android.widget.EditText;
.. java:import:: android.widget.TextView;
.. java:import:: android.widget.Toast;

.. java:import:: org.json.JSONArray;
.. java:import:: org.json.JSONException;
.. java:import:: org.json.JSONObject;

.. java:import:: java.util.ArrayList;
.. java:import:: java.util.List;


LogInFragment
==================

.. java:package:: com.fiuba.tallerii.jobify
   :noindex:

.. java:type:: public class LogInFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>

   Establece la interfaz de usuario para loguearse a la aplicación o crearse cuenta de no poseer una.

Fields
------
mAuthTask
^^^^^^^^

.. java:field:: private UserLoginTask mAuthTask
   :outertype: LogInFragment

   Task asincrónica para intentar loguearse al servidor sin detener la UI thread.

mEmailAutocompleteText
^^^^^^^^

.. java:field::  private AutoCompleteTextView mEmailAutocompleteText;
   :outertype: LogInFragment

   Referencia al campo EditText que contiene la forma para ingresar el email.

mPasswordEditText
^^^^^^^^

.. java:field::  private EditText mPasswordEditText;
   :outertype: LogInFragment

   Referencia al campo EditText que contiene la forma para ingresar el password

mSignInButton
^^^^^^^^

.. java:field::  private Button mSignInButton;
   :outertype: LogInFragment

   Referencia al botón de Sign Up utilizado para iniciar una `SignUpActivity`.

mSignUpButton
^^^^^^^^

.. java:field::  private Button mSignUpButton;
   :outertype: LogInFragment

   Referencia al botón de Log In utilizado para intentar acceder a la aplicación.
   

Methods
-------
onCreateView
^^^^^^^^^^^^^^^^^^

.. java:method:: @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
   :outertype: LogInFragment

   Infla el fragmento con el layout correspondiente e inicializa las referencias y componentes utilizadas.

   :param inflater:
   :param container:
   :param savedInstanceState:


joinApplication
^^^^^^^^

.. java:method:: private void joinApplication()
   :outertype: LogInFragment

   Inicia la Activity: `MainScreenActivity`.


onResume
^^^^^^^^

.. java:method:: @Override public void onResume()
   :outertype: LogInFragment

   Invoca a `resetFields` para limpiar los campos de los formularios.

resetFields
^^^^^^^^

.. java:method::  private void resetFields()
   :outertype: LogInFragment

   Limpia los campos de los formularios.

OpenSignUpActivity
^^^^^^^^

.. java:method:: private void OpenSignUpActivity()
   :outertype: LogInFragment

   Inicia la Activity: `SignUpActivity`.

populateAutoComplete
^^^^^^^^

.. java:method::  private void populateAutoComplete()
   :outertype: LogInFragment

   Sugiere emails conocidos para ingresar en el campo `mEmailAutocompleteText`.


attemptLogin
^^^^^^^^

.. java:method:: private void attemptLogin()
   :outertype: LogInFragment

   Se fija que todos los campos ingresados tengan información válida. De ser así, inicia una tarea asincrónica para intentar conectarse al servidor con el email y password ingresados.

showProgress
^^^^^^^^

.. java:method::  private void showProgress(final boolean show)
   :outertype: LogInFragment

   Si el parámetro ingresado es true, esconde los elementos de UI de logueo y muestra la animación de cargado. Si es true, esconde la animación de cargado y regresa la aplicación a su estado ordinario.

   :param show: Determina si se muestra la animación de cargando, o si se esconde.

