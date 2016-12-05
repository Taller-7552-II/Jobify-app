.. java:import:: android.animation.Animator;
.. java:import:: android.animation.AnimatorListenerAdapter;
.. java:import:: android.content.pm.PackageManager;
.. java:import:: android.database.Cursor;
.. java:import:: android.net.Uri;
.. java:import:: android.os.AsyncTask;
.. java:import:: android.os.Build;
.. java:import:: android.os.Bundle;
.. java:import:: android.provider.ContactsContract;
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
.. java:import:: android.widget.EditText;
.. java:import:: android.widget.TextView;
.. java:import:: android.widget.Toast;

.. java:import:: org.json.JSONException;
.. java:import:: org.json.JSONObject;

.. java:import:: java.util.ArrayList;
.. java:import:: java.util.List;


SignUpFragment
==================

.. java:package:: com.fiuba.tallerii.jobify
   :noindex:

.. java:type:: public class SignUpFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>

   Establece la interfaz de usuario para crearse una nueva cuenta en la aplicación.

Fields
------
mSignUpTask
^^^^^^^^

.. java:field:: private UserSignUpTask mSignUpTask;
   :outertype: SignUpFragment

   Task asincrónica para conectarse al servidor y crearse una nueva cuenta sin detener la UI thread.

mEmailAutocompleteText
^^^^^^^^

.. java:field::  private AutoCompleteTextView mEmailAutocompleteText;
   :outertype: SignUpFragment

   Referencia al campo EditText que contiene la forma para ingresar el email.

mPasswordEditText
^^^^^^^^

.. java:field::  private EditText mPasswordEditText;
   :outertype: SignUpFragment

   Referencia al campo EditText que contiene la forma para ingresar el password

mFirstNameEditText
^^^^^^^^

.. java:field::  private EditText mFirstNameEditText;
   :outertype: SignUpFragment

   Referencia al campo EditText que contiene la forma para ingresar el nombre del usuario.

mLastNameEditText
^^^^^^^^

.. java:field::  private EditText mLastNameEditText;
   :outertype: SignUpFragment

   Referencia al campo EditText que contiene la forma para ingresar el apellido del usuario.

mSignUpButton
^^^^^^^^

.. java:field::  private Button mSignUpButton;
   :outertype: SignUpFragment

   Referencia al botón de Sign up, utilizado para confirmar los campos ingresados y crearse una nueva cuenta.
   

Methods
-------
onCreateView
^^^^^^^^^^^^^^^^^^

.. java:method:: @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
   :outertype: SignUpFragment

   Infla el fragmento con el layout correspondiente e inicializa las referencias y componentes utilizadas.

   :param inflater:
   :param container:
   :param savedInstanceState:


attemptSignUp
^^^^^^^^

.. java:method:: private void attemptSignUp()
   :outertype: SignUpFragment

   Se fija que todos los campos ingresados tengan información válida. De ser así, inicia una tarea asincrónica para intentar conectarse al servidor y crear la cuenta. Si la cuenta se crea con éxito, la aplicación regresa a la Activity: `LogInActivity`.

onResume
^^^^^^^^

.. java:method:: @Override public void onResume()
   :outertype: SignUpFragment

   Invoca a `resetFields` para limpiar los campos de los formularios.

resetFields
^^^^^^^^

.. java:method::  private void resetFields()
   :outertype: SignUpFragment

   Limpia los campos de los formularios.

OpenSignUpActivity
^^^^^^^^

.. java:method:: private void OpenSignUpActivity()
   :outertype: SignUpFragment

   Inicia la Activity: `SignUpActivity`.

populateAutoComplete
^^^^^^^^

.. java:method::  private void populateAutoComplete()
   :outertype: SignUpFragment

   Sugiere emails conocidos para ingresar en el campo `mEmailAutocompleteText`.


showProgress
^^^^^^^^

.. java:method::  private void showProgress(final boolean show)
   :outertype: SignUpFragment

   Si el parámetro ingresado es true, esconde los elementos de UI de logueo y muestra la animación de cargado. Si es true, esconde la animación de cargado y regresa la aplicación a su estado ordinario.

   :param show: Determina si se muestra la animación de cargando, o si se esconde.

