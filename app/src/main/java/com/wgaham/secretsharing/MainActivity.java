package com.wgaham.secretsharing;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toorbar);
        FloatingActionButton addButton = (FloatingActionButton) findViewById(R.id.add_floatactionbutton);
        setSupportActionBar(toolbar);
        addButton.setOnClickListener(this);
        List<Secret> secrets = DataSupport.findAll(Secret.class);
        List<Secretlist> secretlists = new ArrayList<>();
        for (int i = 0; i < secrets.size(); i++) {
            Secretlist secretlist = new Secretlist(secrets.get(i).getName());
            Log.d(TAG, "onCreate:" + secrets.get(i).getName());
            secretlists.add(secretlist);
        }
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.secret_list_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        SecretlistAdapter secretlistAdapter = new SecretlistAdapter(secretlists);
        recyclerView.setAdapter(secretlistAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_floatactionbutton:
                Intent intent = new Intent(this, AddActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.secretlist, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        List<Secret> secrets = DataSupport.findAll(Secret.class);
        List<Secretlist> secretlists = new ArrayList<>();
        for (int i = 0; i < secrets.size(); i++) {
            Secretlist secretlist = new Secretlist(secrets.get(i).getName());
            Log.d(TAG, "onCreate:" + secrets.get(i).getName());
            secretlists.add(secretlist);
            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.secret_list_recyclerview);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            SecretlistAdapter secretlistAdapter = new SecretlistAdapter(secretlists);
            recyclerView.setAdapter(secretlistAdapter);
        }
    }
}
