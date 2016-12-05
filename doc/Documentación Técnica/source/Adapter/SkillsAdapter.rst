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

SkillsAdapter
===============

.. java:package:: com.fiuba.tallerii.jobify
   :noindex:

.. java:type:: private class SkillsAdapter extends RecyclerView.Adapter<SkillsViewHolder>

   Adaptador definido internamente por `SkillsFragment` para controlar cada destreza en forma de un `SkillsViewHolder`

Fields
------
mSkills
^^^^^^^^^

.. java:field::  private List<Skill> mSkills;
   :outertype: SkillsAdapter

   Lista de `Skill`, que contienen la información acerca de las destrezas del usuario.


Constructor
------------
SkillsAdapter
^^^^^^^^^^^^^^^

.. java:constructor:: public SkillsAdapter(List<Skill> skills)
   :outertype: SkillsAdapter

   Inicializa la lista de `Skill` a manipular.

   :param skills: 


Methods
-------


onCreateViewHolder
^^^^^^^^

.. java:method:: @Override public NotificationsViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
   :outertype: SkillsAdapter

   Crea el View para mostrar un `Skill` de la forma deseada y crea con el mismo, un `SkillsViewHolder' 

   :param parent:
   :param viewType:

getItemCount
^^^^^^^

.. java:method:: @Override public int getItemCount()
   :outertype: SkillsAdapter

   Devuelve la cantidad de Skill's que se están manipulando


onBindViewHolder
^^^^^^^^^

.. java:method:: @Override public void onBindViewHolder(SkillsViewHolder holder, int position)
   :outertype: SkillsAdapter

   Rellena los valores del SkillsViewHolder para que pueda ser mostrado de la forma deseada.

   :param holder: SkillsViewHolder que se está agregando
   :param position: índice en la lista de Skills, del `Skill` que está siendo agregado.


