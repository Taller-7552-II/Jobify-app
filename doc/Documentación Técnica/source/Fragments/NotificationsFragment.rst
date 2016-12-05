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


NotificationsFragment
==================

.. java:package:: com.fiuba.tallerii.jobify
   :noindex:

.. java:type:: public class NotificationsFragment extends Fragment

   Utiliza un RecyclerView para enlistar en forma eficiente las destrezas, permitiendo además, la edición de las mismas.

Fields
------
mNotificationsRecycleView
^^^^^^^^

.. java:field:: private RecyclerView mNotificationsRecycleView;
   :outertype: NotificationsFragment

   Referencia al RecyclerView que maneja la vista de las notificaciones.

mNotificationsAdapter
^^^^^^^^

.. java:field::  private NotificationsAdapter mNotificationsAdapter;
   :outertype: NotificationsFragment

   Adapter utilizado para manejar la vista de las notificaciones en el RecyclerView. Ver `NotificationsAdapter`.

mAddingFriend
^^^^^^^^

.. java:field::  private boolean mAddingFriend = false;
   :outertype: NotificationsFragment

   Define si se verán o no los títulos de los tabs. Por defecto, los títulos no se verán.
   

Methods
-------

createAddFriendDialog
^^^^^^^^^^^^^^^^^^

.. java:method:: private void createAddFriendDialog(final String friendUsername, final int notificationIndex)
   :outertype: NotificationsFragment

   Crea un Dialog en respuesta a una notificación de solicitud de amistad, en el cual se puede aceptar o rechazar al contacto solicitante.

   :param friendUsername: Nombre de usuario del usuario que envío la solicitud.
   :param notificationIndex: índice correspondiente a la lista de notificaciones.


updateUI
^^^^^^^^^^^^^^^^^^

.. java:method:: private void updateUI()
   :outertype: NotificationsFragment

   Actualiza la vista de las notificaciones. Para eso pide la información a `InformationHandler`, luego crea un `NotificationsAdapter` y lo asigna al RecyclerView.



onCreate
^^^^^^^^

.. java:method:: @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
   :outertype: NotificationsFragment

   Infla el Fragment con su layout correspondiente e inicializa las referencias y componentes.


onResume
^^^^^^^^

.. java:method:: @Override public void onResume()
   :outertype: NotificationsFragment

   Al resumir el Fragment se actualiza la vista de las notificaciones invocando a UpdateUI().

