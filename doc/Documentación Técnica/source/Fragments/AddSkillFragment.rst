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

AddSkillFragment
==================

.. java:package:: com.fiuba.tallerii.jobify
   :noindex:

.. java:type:: public class AddSkillFragment extends Fragment

   Otorga al usuario una interfaz para agregar un skill a su perfil.

Fields
------
mTitleEditText
^^^^^^^^

.. java:field:: rivate EditText mTitleEditText;
   :outertype: AddSkillFragment

   Referencia al EditText correspondiente a la forma para ingresar el título de la destreza a agregar.

mCategoryEditText
^^^^^^^^

.. java:field::  private EditText mCategoryEditText;
   :outertype: AddSkillFragment

   Referencia al EditText correspondiente a la forma para ingresar la categoría de la destreza a agregar.

mDescriptionEditText
^^^^^^^^

.. java:field::  private EditText mDescriptionEditText;
   :outertype: AddSkillFragment

   Referencia al EditText correspondiente a la forma para ingresar la descripción de la destreza a agregar.


mAddSkillButton
^^^^^^^^

.. java:field::  private Button mAddSkillButton;
   :outertype: AddSkillFragment

   Referencia al botón que confirma los campos ingresados e intenta agregat la nueva destreza al usuario.
   

Methods
-------
addSkill
^^^^^^^^^^^^^^^^^^

.. java:method::  private void addSkill()
   :outertype: AddSkillFragment

   Recolecta los datos ingresados en las formas mostradas e inicia una tarea asincrónica para conectarse al servidor y agregar la nueva destreza.


onCreateView
^^^^^^^^

.. java:method:: @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
   :outertype: AddSkillFragment

   Infla el Fragment con su layout correspondiente e inicializa las referencias y componentes.


