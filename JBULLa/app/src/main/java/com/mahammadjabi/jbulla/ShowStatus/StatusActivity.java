package com.mahammadjabi.jbulla.ShowStatus;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mahammadjabi.jbulla.Adapters.AdapterStories;
import com.mahammadjabi.jbulla.Models.StoryModel;
import com.mahammadjabi.jbulla.R;

import java.util.ArrayList;
import java.util.List;

public class StatusActivity extends AppCompatActivity {

    private Toolbar statustoolbar;
    private AdapterStories adapterStories;

    private RecyclerView all_users_stories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        statustoolbar = findViewById(R.id.statustoolbar);
        setSupportActionBar(statustoolbar);
        statustoolbar.setNavigationIcon(getResources().getDrawable(R.drawable.doublelefticon));
//        statustoolbar.setBackground(new ColorDrawable(getResources().getColor(R.color.statustoolbarcolor)));
        statustoolbar.setTitle("Stories");
        statustoolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleOnBackPress();
            }
        });

        all_users_stories = findViewById(R.id.all_users_stories);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(StatusActivity.this, LinearLayoutManager.VERTICAL, true);
        all_users_stories.setLayoutManager(linearLayoutManager);

        List<String> images = new ArrayList<>();
        images.add(" ");

        List<StoryModel> list = new ArrayList<>();
        list.add(new StoryModel(images,"janiulla"));
        list.add(new StoryModel(images,"janiulla"));
        list.add(new StoryModel(images,"janiulla"));
        list.add(new StoryModel(images,"janiulla"));
        list.add(new StoryModel(images,"janiulla"));
        list.add(new StoryModel(images,"janiulla"));
        list.add(new StoryModel(images,"janiulla"));
        list.add(new StoryModel(images,"janiulla"));
        list.add(new StoryModel(images,"janiulla"));
        list.add(new StoryModel(images,"janiulla"));
        list.add(new StoryModel(images,"janiulla"));
        list.add(new StoryModel(images,"janiulla"));
        list.add(new StoryModel(images,"janiulla"));
        list.add(new StoryModel(images,"janiulla"));
        list.add(new StoryModel(images,"janiulla"));
        list.add(new StoryModel(images,"janiulla"));
        list.add(new StoryModel(images,"janiulla"));
        list.add(new StoryModel(images,"janiulla"));
        list.add(new StoryModel(images,"janiulla"));
        list.add(new StoryModel(images,"janiulla"));

        adapterStories = new AdapterStories(list);
        all_users_stories.setAdapter(adapterStories);

    }

    private void handleOnBackPress()
    {
        onBackPressed();
    }
}