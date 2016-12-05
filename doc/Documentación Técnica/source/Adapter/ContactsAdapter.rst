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

ContactsAdapter
===============

.. java:package:: com.fiuba.tallerii.jobify
   :noindex:

.. java:type:: private class ContactsAdapter extends RecyclerView.Adapter<ContactViewHolder>

   Adaptador definido internamente por `ContactsFragment` para controlar la vista de los contactos en forma de un `ContactsViewHolder`

Fields
------
mContacts
^^^^^^^^^

.. java:field::  private List<Contact> mContacts;
   :outertype: ContactsAdapter

   Lista de `Contact`, que contienen la información acerca de los contactos del usuario.


Constructor
------------
ContactsAdapter
^^^^^^^^^^^^^^^

.. java:constructor:: public ContactsAdapter(List<Contact> contacts)
   :outertype: ContactsAdapter

   Inicializa la lista de `Contact` a manipular.

   :param contacts: 


Methods
-------


onCreateViewHolder
^^^^^^^^

.. java:method:: @Override public ContactsViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
   :outertype: ContactsAdapter

   Crea el View para mostrar un Contact de la forma deseada y crea con el mismo un `ContactsViewHolder' 

   :param parent:
   :param viewType:

getItemCount
^^^^^^^

.. java:method:: @Override public int getItemCount()
   :outertype: ContactsAdapter

   Devuelve la cantidad de Contact's que se están manipulando


onBindViewHolder
^^^^^^^^^

.. java:method:: @Override public void onBindViewHolder(ContactsViewHolder holder, int position)
   :outertype: ContactsAdapter

   Rellena los valores del ContactsViewHolder para que pueda ser mostrado de la forma deseada.

   :param holder: ContactsViewHolder que se está agregando
   :param position: índice en la lista de Contacts, del `Contact` que está siendo agregado


