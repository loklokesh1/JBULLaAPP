package com.mahammadjabi.jbulla.ShowStatus;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mahammadjabi.jbulla.R;

public class StatusActivity extends AppCompatActivity {

    private Toolbar statustoolbar;

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
        all_users_stories.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(StatusActivity.this, LinearLayoutManager.VERTICAL, false);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        all_users_stories.setLayoutManager(linearLayoutManager);

    }

    private void handleOnBackPress()
    {
        onBackPressed();
    }
}