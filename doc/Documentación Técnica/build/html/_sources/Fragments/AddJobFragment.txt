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

AddJobFragment
==================

.. java:package:: com.fiuba.tallerii.jobify
   :noindex:

.. java:type:: public class AddJobFragment extends Fragment

   Otorga al usuario una interfaz para agregar una experiencia laboral a su perfil.

Fields
------
mTitleEditText
^^^^^^^^

.. java:field:: rivate EditText mTitleEditText;
   :outertype: AddJobFragment

   Referencia al EditText correspondiente a la forma para ingresar el título de la experiencia laboral a agregar.

mCategoryEditText
^^^^^^^^

.. java:field::  private EditText mCategoryEditText;
   :outertype: AddJobFragment

   Referencia al EditText correspondiente a la forma para ingresar la categoría de la experiencia laboral a agregar.

mDescriptionEditText
^^^^^^^^

.. java:field::  private EditText mDescriptionEditText;
   :outertype: AddJobFragment

   Referencia al EditText correspondiente a la forma para ingresar la descripción de la experiencia laboral a agregar.


mAddJobButton
^^^^^^^^

.. java:field::  private Button mAddJobButton;
   :outertype: AddJobFragment

   Referencia al botón que confirma los campos ingresados e intenta agregat la nueva experiencia laboral al usuario.
   

Methods
-------
addJob
^^^^^^^^^^^^^^^^^^

.. java:method::  private void addSkill()
   :outertype: AddJobFragment

   Recolecta los datos ingresados en las formas mostradas e inicia una tarea asincrónica para conectarse al servidor y agregar la nueva experiencia laboral.


onCreateView
^^^^^^^^

.. java:method:: @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
   :outertype: AddJobFragment

   Infla el Fragment con su layout correspondiente e inicializa las referencias y componentes.


