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

ViewPagerAdapter
===============

.. java:package:: com.fiuba.tallerii.jobify
   :noindex:

.. java:type:: public class ViewPagerAdapter extends FragmentPagerAdapter

   Adaptador definido internamente por `MainScreenActivity` para controlar los tabs.

Fields
------
mFragmentList
^^^^^^^^^

.. java:field::  private final List<Fragment> mFragmentList
   :outertype: ViewPagerAdapter

   Lista constante de Fragments que serán manipulados

mFragmentTitleList
^^^^^^^^^

.. java:field::  private final List<String> mFragmentTitleList
   :outertype: ViewPagerAdapter

   Lista constante con los títulos de los Fragments.

mShowTittle
^^^^^^^^^

.. java:field::  private boolean mShowTittle
   :outertype: ViewPagerAdapter

   Determina si el usuario verá el título de cada tab o si solo verá los íconos.


Constructor
------------
ViewPagerAdapter
^^^^^^^^^^^^^^^

.. java:constructor:: public ViewPagerAdapter(FragmentManager manager)
   :outertype: ViewPagerAdapter

   Contructor, inicializa el Adapter y setea por defecto que no se vean los nombre de los tabs.

   :param manager: 



Methods
-------

addFragment
^^^^^^^^

.. java:method:: public void addFragment(Fragment fragment, String title)
   :outertype: ViewPagerAdapter

   Agrega un Fragment con el título pasado como segundo parámetro, para ser manipulado por el ViewPagerAdapter.

   :param fragment: Fragment a manipular
   :param tittle: títuló asociado al Fragment

getItem
^^^^^^^^

.. java:method:: @Override public Fragment getItem(int position)
   :outertype: ViewPagerAdapter

   Devuelve el Fragment en la posicion pasada como parámetro

   :param position: índice del Fragment en la lista.

getCount
^^^^^^^

.. java:method:: @Override public int getCount()
   :outertype: ViewPagerAdapter

   Devuelve la cantidad de Fragments que están siendo manipulados por la ViewPagerAdapter


getPageTitle
^^^^^^^^^

.. java:method:: @Override public CharSequence getPageTitle(int position)
   :outertype: ViewPagerAdapter

   Devuelve el título del Fragment correspondiente al índice pasado por parámetro

   :param position: índice del Fragment en la lista.


