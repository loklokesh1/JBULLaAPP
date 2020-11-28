package com.mahammadjabi.jbulla.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mahammadjabi.jbulla.Models.StoryModel;
import com.mahammadjabi.jbulla.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterStories extends RecyclerView.Adapter
{
    private List<StoryModel> list;

    public AdapterStories(List<StoryModel> storieslist) {
        this.list = storieslist;
    }




    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_users_stories,parent,false);
        StoryViewholder storyViewholder = new StoryViewholder(view);
        return storyViewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position)
    {
//        StoryViewholder storyViewholder = (StoryViewholder)holder;
//        Picasso.with(holder.itemView.getContext()).load(list.get(position).getImages().get(0))
//                .placeholder(R.drawable.profile1)
//                .into(holder.thumnail);
//        Picasso.with(storyViewholder.itemView.getContext())
//                .load(list.get(position).getImages().get(0))
//                .into(storyViewholder.thumnail);
//
//        ((StoryViewholder) holder).name.setText(list.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class StoryViewholder extends  RecyclerView.ViewHolder{

        private CircleImageView thumnail;
        private TextView name;

        public StoryViewholder(@NonNull View itemView) {
            super(itemView);
            thumnail = itemView.findViewById(R.id.story);
            name = itemView.findViewById(R.id.story_user_name);

        }
    }
}
