package com.fiuba.tallerii.jobify;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;


public class JobsFragment extends Fragment
{
    private RecyclerView mJobsRecycleView;
    private JobsAdapter mJobsAdapter;

    private Button mAddJobButton;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.recycler_with_job_button, container, false);

        mJobsRecycleView = (RecyclerView) v.findViewById(R.id.recycler_view_with_button);
        mJobsRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAddJobButton = (Button) v.findViewById(R.id.button_new_element);
        mAddJobButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getActivity(), AddJobActivity.class);
                startActivity(intent);
            }
        });

        updateUI();

        return v;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        updateUI();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater)
    {
        super.onCreateOptionsMenu(menu, menuInflater);
        menuInflater.inflate(R.menu.fragment_skills_add, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menu_item_add:
                Intent intent = new Intent(getActivity(), AddJobActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void updateUI()
    {
        InformationHolder informationHolder = InformationHolder.get();
        List<Job> jobs = informationHolder.getJobs();

        mJobsAdapter = new JobsAdapter(jobs);
        mJobsRecycleView.setAdapter(mJobsAdapter);
    }

    /*
        ViewHolder Class
     */
    private class JobsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        CardView mCardView;
        public TextView mTitleAndCategoryView;
        public TextView mDescriptionView;

        public JobsViewHolder(View itemView)
        {
            super(itemView);
            itemView.setOnClickListener(this);

            mCardView = (CardView) itemView.findViewById(R.id.card_view_skill);
            mTitleAndCategoryView = (TextView) itemView.findViewById(R.id.list_item_skill_tittle_category);
            mDescriptionView = (TextView) itemView.findViewById(R.id.list_item_skill_description);
        }

        @Override
        public void onClick(View view)
        {
            //Iniciar actividad de ver contacto
        }
    }

    /*
        Adapter Class
     */
    private class JobsAdapter extends RecyclerView.Adapter<JobsViewHolder>
    {
        private List<Job> mJobs;

        public JobsAdapter(List<Job> jobs)
        {
            mJobs = jobs;
        }

        @Override
        public JobsViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_card_skill, parent, false);
            return new JobsViewHolder(view);
        }

        @Override
        public void onBindViewHolder(JobsViewHolder holder, int position)
        {
            Job job = mJobs.get(position);
            String titleAndCat = job.getTittle() + " - " + job.getCategory();
            holder.mTitleAndCategoryView.setText(titleAndCat);
            holder.mDescriptionView.setText(job.getDescription());
        }

        @Override
        public int getItemCount()
        {
            return mJobs.size();
        }
    }
}
