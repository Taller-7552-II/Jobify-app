.. java:import:: android.content.DialogInterface;
.. java:import:: android.graphics.Bitmap;
.. java:import:: android.os.AsyncTask;
.. java:import:: android.os.Bundle;
.. java:import:: android.support.v4.app.Fragment;
.. java:import:: android.support.v7.app.AlertDialog;
.. java:import:: android.support.v7.widget.CardView;
.. java:import:: android.support.v7.widget.LinearLayoutManager;
.. java:import:: android.support.v7.widget.RecyclerView;
.. java:import:: android.util.Log;
.. java:import:: android.view.LayoutInflater;
.. java:import:: android.view.View;
.. java:import:: android.view.ViewGroup;
.. java:import:: android.widget.TextView;
.. java:import:: android.widget.Toast;

.. java:import:: org.json.JSONArray;
.. java:import:: org.json.JSONException;
.. java:import:: org.json.JSONObject;

.. java:import:: java.util.List;

NotificationAdapter
===============

.. java:package:: com.fiuba.tallerii.jobify
   :noindex:

.. java:type:: pprivate class NotificationAdapter extends RecyclerView.Adapter<NotificationsViewHolder>

   Adaptador definido internamente por `NotificationsFragment` para controlar cada cada notificación en forma de un `NotificationsViewHolder`

Fields
------
mNotifications
^^^^^^^^^

.. java:field::  private List<Notification> mNotifications;
   :outertype: NotificationAdapter

   Lista de `Notification`, que contienen la información acerca de las notificaciones del usuario.


Constructor
------------
NotificationAdapter
^^^^^^^^^^^^^^^

.. java:constructor:: public NotificationAdapter(List<Notification> notifications)
   :outertype: NotificationAdapter

   Inicializa la lista de `Notification` a manipular.

   :param notifications: 


Methods
-------


onCreateViewHolder
^^^^^^^^

.. java:method:: @Override public NotificationsViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
   :outertype: NotificationAdapter

   Crea el View para mostrar un `Notification` de la forma deseada y crea con el mismo, un `NotificationsViewHolder' 

   :param parent:
   :param viewType:

getItemCount
^^^^^^^

.. java:method:: @Override public int getItemCount()
   :outertype: NotificationAdapter

   Devuelve la cantidad de Notification's que se están manipulando


onBindViewHolder
^^^^^^^^^

.. java:method:: @Override public void onBindViewHolder(NotificationsViewHolder holder, int position)
   :outertype: NotificationAdapter

   Rellena los valores del NotificationsViewHolder para que pueda ser mostrado de la forma deseada.

   :param holder: NotificationsViewHolder que se está agregando
   :param position: índice en la lista de Notifications, del `Notification` que está siendo agregado.


