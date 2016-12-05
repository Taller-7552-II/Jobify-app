.. java:import:: android.os.AsyncTask;
.. java:import:: android.os.Bundle;
.. java:import:: android.support.v4.app.Fragment;
.. java:import:: android.util.Log;
.. java:import:: android.view.KeyEvent;
.. java:import:: android.view.LayoutInflater;
.. java:import:: android.view.View;
.. java:import:: android.view.ViewGroup;
.. java:import:: android.view.inputmethod.EditorInfo;
.. java:import:: android.widget.Button;
.. java:import:: android.widget.EditText;
.. java:import:: android.widget.TextView;
.. java:import:: android.widget.Toast;

.. java:import:: org.json.JSONArray;
.. java:import:: org.json.JSONException;
.. java:import:: org.json.JSONObject;

.. java:import:: java.util.List;

AddContactFragment
==================

.. java:package:: com.fiuba.tallerii.jobify
   :noindex:

.. java:type:: public class AddContactFragment extends Fragment

   Otorga al usuario una interfaz para agregar una experiencia laboral a su perfil.

Fields
------
mUsernameEditText
^^^^^^^^

.. java:field:: private EditText mUsernameEditText;
   :outertype: AddContactFragment

   Referencia al EditText correspondiente a la forma para buscar un contacto.


mAddContactButton
^^^^^^^^

.. java:field::  private Button mAddContactButton;
   :outertype: AddContactFragment

   Referencia al botón que envía una solicitud de amistad al contacto seleccionado, para esperar ser aceptado.
   

Methods
-------
addContact
^^^^^^^^^^^^^^^^^^

.. java:method::  private void addContact()
   :outertype: AddContactFragment

   Inicia una tarea asincrónica para conectarse al servidor y agregar al contacto seleccionado.

isUsernameValid
^^^^^^^^^^^^^^^^^^

.. java:method::  private boolean isUsernameValid(String username)
   :outertype: AddContactFragment

   Devuelve true si el usuario ingresado por parámetro existe en la base de datos del servidor. False en caso contrario.

   :param username: nombre de usuario del contacto a agregar


onCreateView
^^^^^^^^

.. java:method:: @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
   :outertype: AddContactFragment

   Infla el Fragment con su layout correspondiente e inicializa las referencias y componentes.


