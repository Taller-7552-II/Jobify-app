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


SkillsFragment
==================

.. java:package:: com.fiuba.tallerii.jobify
   :noindex:

.. java:type:: public class SkillsFragment extends Fragment

   Utiliza un RecyclerView para enlistar en forma eficiente las destrezas, permitiendo además, la edición de las mismas.

Fields
------
mSkillsRecycleView
^^^^^^^^

.. java:field:: private RecyclerView mSkillsRecycleView;
   :outertype: SkillsFragment

   Referencia al RecyclerView que maneja la vista de las destrzas

mSkillsAdapter
^^^^^^^^

.. java:field::  private SkillsAdapter mSkillsAdapter;
   :outertype: SkillsFragment

   Adapter utilizado para manejar la vista de las destrzas en el RecyclerView. Ver `SkillsAdapter`

mAddSkillButton
^^^^^^^^

.. java:field::  private Button mAddSkillButton;
   :outertype: SkillsFragment

   Referencia al botón utilizado para agregar destrezas.
   

Methods
-------
updateUI
^^^^^^^^^^^^^^^^^^

.. java:method:: private void updateUI()
   :outertype: SkillsFragment

   Actualiza la vista de las destrezas. Para eso pide la información a `InformationHandler`, luego crea un `SkillsAdapter` y lo asigna al RecyclerView.



onCreateView
^^^^^^^^

.. java:method:: @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
   :outertype: SkillsFragment

   Infla el Fragment con su layout correspondiente e inicializa las referencias y componentes.


onResume
^^^^^^^^

.. java:method:: @Override public void onResume()
   :outertype: SkillsFragment

   Al resumir el Fragment se actualiza la vista de las destrezas invocando a UpdateUI().

