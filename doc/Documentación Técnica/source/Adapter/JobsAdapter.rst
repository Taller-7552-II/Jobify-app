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

JobsAdapter
===============

.. java:package:: com.fiuba.tallerii.jobify
   :noindex:

.. java:type:: private class JobsAdapter extends RecyclerView.Adapter<JobsViewHolder>

   Adaptador definido internamente por `JobsFragment` para controlar cada experiencia laboral en forma de un `JobsViewHolder`

Fields
------
mJobs
^^^^^^^^^

.. java:field::  private List<Job> mJobs;
   :outertype: JobsAdapter

   Lista de `Job`, que contienen la información acerca de las experiencias laborales.


Constructor
------------
JobsAdapter
^^^^^^^^^^^^^^^

.. java:constructor:: public JobsAdapter(List<Job> jobs)
   :outertype: JobsAdapter

   Inicializa la lista de `Job` a manipular.

   :param jobs: 


Methods
-------


onCreateViewHolder
^^^^^^^^

.. java:method:: @Override public JobsViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
   :outertype: JobsAdapter

   Crea el View para mostrar un Job de la forma deseada y crea con el mismo un `JobsViewHolder' 

   :param parent:
   :param viewType:

getItemCount
^^^^^^^

.. java:method:: @Override public int getItemCount()
   :outertype: JobsAdapter

   Devuelve la cantidad de Job's que se están manipulando


onBindViewHolder
^^^^^^^^^

.. java:method:: @Override public void onBindViewHolder(JobsViewHolder holder, int position)
   :outertype: JobsAdapter

   Rellena los valores del JobsViewHolder para que pueda ser mostrado de la forma deseada.

   :param holder: JobsViewHolder que se está agregando
   :param position: índice en la lista de Jobs, del `Job` que está siendo agregado


