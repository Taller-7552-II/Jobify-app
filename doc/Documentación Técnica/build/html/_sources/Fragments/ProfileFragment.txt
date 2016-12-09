
.. java:import:: android.app.Activity;
.. java:import:: android.content.Intent;
.. java:import:: android.database.Cursor;
.. java:import:: android.graphics.Bitmap;
.. java:import:: android.graphics.BitmapFactory;
.. java:import:: android.net.Uri;
.. java:import:: android.os.AsyncTask;
.. java:import:: android.os.Bundle;
.. java:import:: android.provider.MediaStore;
.. java:import:: android.support.v4.app.Fragment;
.. java:import:: android.text.method.KeyListener;
.. java:import:: android.util.Log;
.. java:import:: android.view.KeyEvent;
.. java:import:: android.view.LayoutInflater;
.. java:import:: android.view.View;
.. java:import:: android.view.ViewGroup;
.. java:import:: android.view.inputmethod.EditorInfo;
.. java:import:: android.widget.Button;
.. java:import:: android.widget.EditText;
.. java:import:: android.widget.ImageView;
.. java:import:: android.widget.RelativeLayout;
.. java:import:: android.widget.TextView;
.. java:import:: android.widget.Toast;

.. java:import:: org.json.JSONArray;
.. java:import:: org.json.JSONException;
.. java:import:: org.json.JSONObject;

.. java:import:: java.util.List;


ProfileFragment
==================

.. java:package:: com.fiuba.tallerii.jobify
   :noindex:

.. java:type:: public class ProfileFragment extends Fragment

   Provee la interfaz para visualizar el perfil del usuario.



Methods
-------
onCreateView
^^^^^^^^^^^^^^^^^^

.. java:method:: @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
   :outertype: ProfileFragment

   Infla el fragmento con el layout correspondiente e inicializa las referencias y componentes utilizadas.

   :param inflater:
   :param container:
   :param savedInstanceState:


attemptSignUp
^^^^^^^^

.. java:method:: private void attemptSignUp()
   :outertype: ProfileFragment

   Se fija que todos los campos ingresados tengan información válida. De ser así, inicia una tarea asincrónica para intentar conectarse al servidor y crear la cuenta. Si la cuenta se crea con éxito, la aplicación regresa a la Activity: `LogInActivity`.

onResume
^^^^^^^^

.. java:method:: @Override public void onResume()
   :outertype: ProfileFragment

   Actualiza los campos del perfil a través del método `updateFields`.

updateFields
^^^^^^^^

.. java:method::  private void updateFields()
   :outertype: ProfileFragment

   Actualiza los valores de los componentes de la vista, utilizando los datos contenidos en el Singleto `InformationHolder`.

chooseProfilePicture
^^^^^^^^

.. java:method:: private void chooseProfilePicture()
   :outertype: ProfileFragment

   Inicia un Intent para seleccionar una foto del dispositivo con cualquier aplicación capaz.

onActivityResult
^^^^^^^^

.. java:method::  public void onActivityResult(int requestCode, int resultCode, Intent data)
   :outertype: ProfileFragment

   Si el resultado obtenido corresponde al de haber seleccionado una imagen satisfactoriamente. Procede a agregar la imagen como foto de perfil.


setUpResumeField
^^^^^^^^

.. java:method::  private void setUpResumeField(View v)
   :outertype: ProfileFragment

   Prepara el campo del Resumen para que pueda ser editable como corresponde.

setUpNameField
^^^^^^^^

.. java:method::  private void setUpNameField(View v)
   :outertype: ProfileFragment

   Prepara el campo del Nombre para que pueda ser editable como corresponde.

setUpSaveChangesButton
^^^^^^^^

.. java:method::  private void setUpSaveChangesButton(View v)
   :outertype: ProfileFragment

   Inicializa el botón de guardar los cambios y le agrega los listeners correspondientes.
