.. java:import:: android.content.Intent;
.. java:import:: android.os.Bundle;
.. java:import:: android.support.v4.app.Fragment;
.. java:import:: android.support.v7.widget.CardView;
.. java:import:: android.support.v7.widget.LinearLayoutManager;
.. java:import:: android.support.v7.widget.RecyclerView;
.. java:import:: android.view.LayoutInflater;
.. java:import:: android.view.Menu;
.. java:import:: android.view.MenuInflater;
.. java:import:: android.view.MenuItem;
.. java:import:: android.view.View;
.. java:import:: android.view.ViewGroup;
.. java:import:: android.widget.Button;
.. java:import:: android.widget.TextView;

.. java:import:: java.util.List;


ContactsFragment
==================

.. java:package:: com.fiuba.tallerii.jobify
   :noindex:

.. java:type:: public class ContactsFragment  extends Fragment

   Utiliza un RecyclerView para enlistar en forma eficiente los contactos del usuario, permitiendo además, la edición de los mismos.

Fields
------
mContactsRecycleView
^^^^^^^^

.. java:field:: private RecyclerView mContactsRecycleView;
   :outertype: ContactsFragment

   Referencia al RecyclerView que maneja la vista de los contactos

mContactsAdapter
^^^^^^^^

.. java:field::  private ContactsAdapter mContactsAdapter;
   :outertype: ContactsFragment

   Adapter utilizado para manejar la vista de los contactos en el RecyclerView. Ver `ContactsAdapter`

mSearchButton
^^^^^^^^

.. java:field::  private Button mSearchButton;
   :outertype: ContactsFragment

   Referencia al botón utilizado para buscar contactos.
   

Methods
-------

updateUI
^^^^^^^^^^^^^^^^^^

.. java:method:: private void startSearchContactActivity()
   :outertype: ContactsFragment

   Inicia la Activity: `AddContactActivity`.

updateUI
^^^^^^^^^^^^^^^^^^

.. java:method:: private void updateUI()
   :outertype: ContactsFragment

   Actualiza la vista de los contactos en el RecyclerView. Para eso pide la información a `InformationHandler`, luego crea un `ContactsAdapter` y lo asigna al RecyclerView.



onCreateView
^^^^^^^^

.. java:method:: @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
   :outertype: ContactsFragment

   Infla el Fragment con su layout correspondiente e inicializa las referencias y componentes.


onResume
^^^^^^^^

.. java:method:: @Override public void onResume()
   :outertype: ContactsFragment

   Al resumir el Fragment se actualiza la vista de los contactos invocando a UpdateUI().

