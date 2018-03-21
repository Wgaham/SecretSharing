package com.wgaham.secretsharing;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import org.litepal.crud.DataSupport;

import java.util.List;

public class SecretShowActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String SECRET_ID = "secret_id";
    public static final String SECRET_NAME = "secret_name";
    private int id;
    private String startTime, endTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secret_show);
        Intent intent = getIntent();
        String secretName = intent.getStringExtra(SECRET_NAME);
        id = intent.getIntExtra(SECRET_ID, 0);
        Toolbar toolbar = (Toolbar) findViewById(R.id.show_toolbar);
        CollapsingToolbarLayout toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        FloatingActionButton actionButton = (FloatingActionButton) findViewById(R.id.restructure_fab);
        actionButton.setOnClickListener(this);
        TextView secretDetail = (TextView) findViewById(R.id.secret_details);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        toolbarLayout.setTitle(secretName);
        Secret secret = DataSupport.find(Secret.class, id, true);
        List<Member> memberList = secret.getMemberList();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.restructure_fab:
        }
    }
}
