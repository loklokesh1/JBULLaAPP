package com.mahammadjabi.jbulla.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mahammadjabi.jbulla.Models.AskHelpUsersModel;
import com.mahammadjabi.jbulla.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterAskHelpUsers extends RecyclerView.Adapter
{
    public static ProgressBar progressBar;

    public AdapterAskHelpUsers(List<AskHelpUsersModel> askHelpUsersModelList)
    {
        this.askHelpUsersModelList = askHelpUsersModelList;
    }

    List<AskHelpUsersModel> askHelpUsersModelList;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_users_ask_help_local,parent,false);
        ViewHolderClass viewHolderClass = new ViewHolderClass(view);
        return viewHolderClass;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position)
    {
        ViewHolderClass viewHolderClass = (ViewHolderClass)holder;

        AskHelpUsersModel askHelpUsersModel = askHelpUsersModelList.get(position);

        viewHolderClass.date1.setText(askHelpUsersModel.getDate());
        viewHolderClass.time1.setText(askHelpUsersModel.getTime());
        viewHolderClass.UserUserName.setText(askHelpUsersModel.getUsername());
        viewHolderClass.postdescription1.setText(askHelpUsersModel.getDescription());
        viewHolderClass.asktime1.setText(askHelpUsersModel.getAsktime());
        viewHolderClass.askamount1.setText(askHelpUsersModel.getAskamount());

        Picasso.with(viewHolderClass.itemView.getContext())
                .load(askHelpUsersModel.getProfileimage())
                .into(viewHolderClass.UserProfileImage);

    }

    @Override
    public int getItemCount() {
        return askHelpUsersModelList.size();
    }

    public class ViewHolderClass extends RecyclerView.ViewHolder
    {

        TextView date1;
        TextView UserUserName;
        TextView time1;
        TextView postdescription1;
        TextView asktime1;
        TextView askamount1;
        CircleImageView UserProfileImage;

        public ViewHolderClass(@NonNull View itemView) {


            super(itemView);

            date1 = (TextView)itemView.findViewById(R.id.ask_post_date);//
            UserProfileImage = (CircleImageView)itemView.findViewById(R.id.ask_profile_image);//
            UserUserName = (TextView)itemView.findViewById(R.id.ask_user_name);//
            time1 = (TextView)itemView.findViewById(R.id.ask_post_time);//
            postdescription1 =  (TextView)itemView.findViewById(R.id.ask_post_description);//
//            progressBar = itemView.findViewById(R.id.progressbarask);
            asktime1 = itemView.findViewById(R.id.asktimetocomplete);
            askamount1 = itemView.findViewById(R.id.askamount);


        }
    }
}
