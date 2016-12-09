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


JobsFragment
==================

.. java:package:: com.fiuba.tallerii.jobify
   :noindex:

.. java:type:: public class JobsFragment extends Fragment

   Utiliza un RecyclerView para enlistar en forma eficiente las experiencias laborales, permitiendo además, la edición de las mismas.

Fields
------
mJobsRecycleView
^^^^^^^^

.. java:field:: private RecyclerView mJobsRecycleView;
   :outertype: JobsFragment

   Referencia al RecyclerView que maneja la vista de las experiencias laborales

mJobsAdapter
^^^^^^^^

.. java:field::  private JobsAdapter mJobsAdapter;
   :outertype: JobsFragment

   Adapter utilizado para manejar la vista de las experiencias laborales en el RecyclerView. Ver `JobsAdapter`

mAddJobButton
^^^^^^^^

.. java:field::  private Button mAddJobButton;
   :outertype: JobsFragment

   Referencia al botón utilizado para agregar experiencias laborales
   

Methods
-------
updateUI
^^^^^^^^^^^^^^^^^^

.. java:method:: private void updateUI()
   :outertype: JobsFragment

   Actualiza la vista de las experiencias laborales. Para eso pide la información a `InformationHandler`, luego crea un `JobsAdapter` y lo asigna al RecyclerView.



onCreateView
^^^^^^^^

.. java:method:: @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
   :outertype: JobsFragment

   Infla el Fragment con su layout correspondiente e inicializa las referencias y componentes.


onResume
^^^^^^^^

.. java:method:: @Override public void onResume()
   :outertype: JobsFragment

   Al resumir el Fragment se actualiza la vista de experiencias laborales.

