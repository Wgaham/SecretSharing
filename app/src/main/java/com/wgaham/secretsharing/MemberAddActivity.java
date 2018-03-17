package com.wgaham.secretsharing;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class MemberAddActivity extends AppCompatActivity implements View.OnClickListener {
    private int secretValue;

    private String secretName, startTime, endTime;

    private EditText l01, l11, l12, l21, l22, l23;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_add);
        Toolbar toolbar = (Toolbar) findViewById(R.id.member_toolbar);
        l01 = (EditText) findViewById(R.id.l01_name);
        l11 = (EditText) findViewById(R.id.l11_name);
        l12 = (EditText) findViewById(R.id.l12_name);
        l21 = (EditText) findViewById(R.id.l21_name);
        l22 = (EditText) findViewById(R.id.l22_name);
        l23 = (EditText) findViewById(R.id.l23_name);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        SecretTemp secretTemp = (SecretTemp) getIntent().getSerializableExtra("secret");
        secretValue = secretTemp.getSecretValue();
        secretName = secretTemp.getSecretName();
        startTime = secretTemp.getStartTime();
        endTime = secretTemp.getEndTime();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
