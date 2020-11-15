package com.mahammadjabi.jbulla;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class NoInternetActivity extends AppCompatActivity {

    private SwipeRefreshLayout swipeRefreshLayout;
    private Button tryagain;

    boolean connection;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_internet);

        tryagain = findViewById(R.id.tryagain);

        tryagain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                connection = isNetworkAvailable();

                if (connection)
                {
                    Intent nointernet = new  Intent(NoInternetActivity.this, MainActivity.class);
                    nointernet.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(nointernet);
                    finish();
                }
                else
                {
                    Toast.makeText(NoInternetActivity.this, "Please ckeck your InternetConnection", Toast.LENGTH_SHORT).show();
                }
            }
        });

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshlayoutnowifi);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh()
            {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run()
                    {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                },2000);

                connection = isNetworkAvailable();

                if (connection)
                {
                    Intent nointernet = new  Intent(NoInternetActivity.this, MainActivity.class);
                    nointernet.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(nointernet);
                    finish();
                }
                else
                {
                    Toast.makeText(NoInternetActivity.this, "Please ckeck your InternetConnection", Toast.LENGTH_SHORT).show();
                }

            }
        });

        swipeRefreshLayout.setColorSchemeColors(Color.BLUE,
                Color.GREEN,Color.BLACK,Color.RED);
    }

    private boolean isNetworkAvailable()
    {
        ConnectivityManager connectivityManager = (ConnectivityManager)this.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkinfo = connectivityManager.getActiveNetworkInfo();
        return networkinfo != null;
    }
}