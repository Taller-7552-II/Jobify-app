.. java:import:: android.os.Bundle;
.. java:import:: android.support.v4.app.Fragment;
.. java:import:: android.support.v4.app.FragmentManager;
.. java:import:: android.support.v7.app.AppCompatActivity;


SingleFragmentActivity
============

.. java:package:: com.fiuba.tallerii.jobify
   :noindex:

.. java:type:: public class SingleFragmentActivity extends AppCompatActivity

   Actividad inicial que contiene un único Fragment para manejar su contenido visible. SingleFragmentActivity es una clase abstract

Methods
-------
createFragment
^^^^^

.. java:method:: protected abstract Fragment createFragment()
   :outertype: SingleFragmentActivity

   Metodo que deben sobreescribir todas las clases que heredan de SingleFragmentActivity, para definir el Fragment que se utilizará.

   :return: nuevo Fragment que se utilizará para manejar la interfaz de usuario de la activity.


onCreate
^^^^^^^^

.. java:method:: @Override protected void onCreate(Bundle savedInstanceState)
   :outertype: SingleFragmentActivity

   Inicializa la activity y crea un Fragment mediante el método abstracto createFragment(). Luego configura dicho Fragment para ser utilizado como la interfaz de usuario de esta Activity. 

   :param savedInstanceState:

