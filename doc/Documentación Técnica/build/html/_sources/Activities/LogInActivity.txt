.. java:import:: android.support.v4.app.Fragment;

LogInActivity
============

.. java:package:: com.fiuba.tallerii.jobify
   :noindex:

.. java:type:: public class LogInActivity extends SingleFragmentActivity

   Actividad en la que inicia la aplicación, en la cual el usuario puede loguearse para ingresar a la aplicación, o crearse una nueva cuenta. Crearse una nueva cuenta iniciará una nueva `SignUpActivity`.

Methods
-------
createFragment
^^^^^

.. java:method:: @Override protected Fragment createFragment()
   :outertype: LogInActivity

   Crea el Fragment que se utilizará en la Activity.

   :return: `LogInFragment` en forma de Fragment


