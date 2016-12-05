.. java:import:: android.os.Bundle;
.. java:import:: android.support.design.widget.TabLayout;
.. java:import:: android.support.v4.app.Fragment;
.. java:import:: android.support.v4.app.FragmentManager;
.. java:import:: android.support.v4.app.FragmentPagerAdapter;
.. java:import:: android.support.v4.view.ViewPager;
.. java:import:: android.support.v7.app.AppCompatActivity;
.. java:import:: android.support.v7.widget.Toolbar;

.. java:import:: java.util.ArrayList;
.. java:import:: java.util.List;


MainScreenActivity
==================

.. java:package:: com.fiuba.tallerii.jobify
   :noindex:

.. java:type:: public class MainScreenActivity extends AppCompatActivity

   Actividad principal de la aplicación. Contiene 4 Fragments correspondientes a las principales ventanas de la aplicación: Perfil, Contactos, Notificaciones, Chats. Para manipular dichos Fragments, utiliza la clase `ViewPagerAdapter`.

Fields
------
mToolbar
^^^^^^^^

.. java:field:: private Toolbar mToolbar;
   :outertype: MainScreenActivity


mTabLayout
^^^^^^^^

.. java:field::  private TabLayout mTabLayout;
   :outertype: MainScreenActivity

   Referencia al layout que contiene y establece como se muestran los tabs.

mViewPager
^^^^^^^^

.. java:field::  private ViewPager mViewPager;
   :outertype: MainScreenActivity

   ViewPager que maneja el paginado de los distintos Fragments que componen a la Activity.

Methods
-------
setupTabIcons
^^^^^^^^^^^^^^^^^^

.. java:method:: private void setupTabIcons()
   :outertype: MainScreenActivity

   Establece las asociaciones entre íconos y Tabs.

setupViewPager
^^^^^^^^^

.. java:method:: private void setupViewPager(ViewPager viewPager)
   :outertype: MainScreenActivity

   Crea todos los Fragments a utilizar en la MainScreenActivity y los agrega a un `ViewPagerAdapter`, para luego asignarselo al viewPager pasado como parámetro.

   :param viewPager: Referencia al viewPager utiliado para manejar el paginado de los Fragments. A esta ViewPager se le seteará el Adapter `ViewPagerAdapter` específico de la aplicación.
   