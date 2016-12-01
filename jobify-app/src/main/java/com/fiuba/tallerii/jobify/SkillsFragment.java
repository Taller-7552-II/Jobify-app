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

public class SkillsFragment extends Fragment
{
    private RecyclerView mSkillsRecycleView;
    private SkillsAdapter mSkillsAdapter;

    private Button mAddSkillButton;

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
        View v = inflater.inflate(R.layout.recycler_with_skill_button, container, false);

        mSkillsRecycleView = (RecyclerView) v.findViewById(R.id.recycler_view_with_button);
        mSkillsRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAddSkillButton = (Button) v.findViewById(R.id.button_new_element);
        mAddSkillButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getActivity(), AddSkillActivity.class);
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
                Intent intent = new Intent(getActivity(), AddSkillActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void updateUI()
    {
        InformationHolder informationHolder = InformationHolder.get();
        List<Skill> skills = informationHolder.getSkills();

        mSkillsAdapter = new SkillsAdapter(skills);
        mSkillsRecycleView.setAdapter(mSkillsAdapter);
    }

    /*
        ViewHolder Class
     */
    private class SkillsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        CardView mCardView;
        public TextView mTitleAndCategoryView;
        public TextView mDescriptionView;

        public SkillsViewHolder(View itemView)
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
    private class SkillsAdapter extends RecyclerView.Adapter<SkillsViewHolder>
    {
        private List<Skill> mSkills;

        public SkillsAdapter(List<Skill> skills)
        {
            mSkills = skills;
        }

        @Override
        public SkillsViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_card_skill, parent, false);
            return new SkillsViewHolder(view);
        }

        @Override
        public void onBindViewHolder(SkillsViewHolder holder, int position)
        {
            Skill skill = mSkills.get(position);
            String titleAndCat = skill.getTittle() + " - " + skill.getCategory();
            holder.mTitleAndCategoryView.setText(titleAndCat);
            holder.mDescriptionView.setText(skill.getDescription());
        }

        @Override
        public int getItemCount()
        {
            return mSkills.size();
        }
    }
}
