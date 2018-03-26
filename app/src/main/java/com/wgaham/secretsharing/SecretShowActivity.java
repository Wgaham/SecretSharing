package com.wgaham.secretsharing;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.util.List;



public class SecretShowActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String SECRET_ID = "secret_id";
    public static final String SECRET_NAME = "secret_name";
    private int id;
    private String startTime, endTime;
    private static final String TAG = "SecretShowActivity";

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
        TextView secretDetail = (TextView) findViewById(R.id.secret_details);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        toolbarLayout.setTitle(secretName);
        if (id == 0) {
            Toast.makeText(this, "ID传递出错，返回上一页面再试一次", Toast.LENGTH_SHORT).show();
            return;
        }
        Secret secret = DataSupport.find(Secret.class, id, true);
        List<Member> memberList = secret.getMemberList();
        StringBuffer secretBuffer = new StringBuffer();
        secretBuffer.append("此秘密共有").append(secret.getAllParson()).append("个参与者，分为").append(secret.getAllLevel()).append("个等级\n");
        for (int i = 0; i < memberList.size(); i++) {
            secretBuffer.append("参与者“").append(memberList.get(i).getName()).append("”是等级").append(memberList.get(i).getLevel()).append("的第")
                    .append(memberList.get(i).getParson()).append("个人，他所占有的子份额数是：").append(memberList.get(i).getShare()).append("\n");
        }
        String secretMember = secretBuffer.toString();
        secretBuffer.setLength(0);
        if (!"".equals(secretMember)) {
            secretDetail.setText(secretMember);
        }
        startTime = secret.getTimeOfStart();
        endTime = secret.getTimeOfEnd();
        actionButton.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.restructure_fab:
                if (!Tool.restructureTimeJudgment(startTime, endTime)) {
                    Toast.makeText(SecretShowActivity.this, "现在时间不在重构允许时间之内", Toast.LENGTH_SHORT).show();
                    break;
                }
                Intent intent = new Intent(this, MemberSelectActivity.class);
                intent.putExtra(SECRET_ID, id);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
