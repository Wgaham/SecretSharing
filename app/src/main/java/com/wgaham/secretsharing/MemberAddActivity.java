package com.wgaham.secretsharing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
        Button complete = (Button) findViewById(R.id.complete_button);
        complete.setOnClickListener(this);
        SecretTemp secretTemp = (SecretTemp) getIntent().getSerializableExtra("secret");
        secretName = secretTemp.getSecretName();
        startTime = secretTemp.getStartTime();
        endTime = secretTemp.getEndTime();
        secretValue = secretTemp.getSecretValue();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.complete_button:
                String l01Name, l11Name, l12Name, l21Name, l22Name, l23Name;
                l01Name = l01.getText().toString().trim();
                l11Name = l11.getText().toString().trim();
                l12Name = l12.getText().toString().trim();
                l21Name = l21.getText().toString().trim();
                l22Name = l22.getText().toString().trim();
                l23Name = l23.getText().toString().trim();
                if ("".equals(l01Name) || "".equals(l11Name) || "".equals(l12Name) || "".equals(l21Name) || "".equals(l22Name) || "".equals(l23Name)) {
                    Toast.makeText(MemberAddActivity.this, "请将各项填写完整", Toast.LENGTH_SHORT).show();
                    break;
                }
                String[] strings = {randomInt(), "1", randomInt(), "2"};
                int[] ints = {1, 2, 3};
                Convey convey = new Convey();
                try {
                    int[] share = convey.Receivingserect(secretValue, 3, 6, strings, ints);
                    Member member01 = new Member();
                    Member member11 = new Member();
                    Member member12 = new Member();
                    Member member21 = new Member();
                    Member member22 = new Member();
                    Member member23 = new Member();
                    saveMember(member01, 0, 1, 1, share[0], l01Name);
                    saveMember(member11, 1, 2, 1, share[1], l11Name);
                    saveMember(member12, 1, 2, 2, share[2], l12Name);
                    saveMember(member21, 2, 3, 1, share[3], l21Name);
                    saveMember(member22, 2, 3, 2, share[4], l22Name);
                    saveMember(member23, 2, 3, 3, share[5], l23Name);
                    List<Member> memberList = new ArrayList<>();
                    memberList.add(member01);
                    memberList.add(member11);
                    memberList.add(member12);
                    memberList.add(member21);
                    memberList.add(member22);
                    memberList.add(member23);
                    DataSupport.saveAll(memberList);
                    Secret secret = new Secret();
                    secret.setName(secretName);
                    secret.setTimeOfStart(startTime);
                    secret.setTimeOfEnd(endTime);
                    secret.setSecret(secretValue);
                    secret.setAllLevel(3);
                    secret.setAllParson(6);
                    secret.setMemberList(memberList);
                    secret.save();
                } catch (Exception e) {
                    Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            default:
                break;

        }
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

    private void saveMember(Member member, int level, int threshold, int parson, int share, String name) {
        member.setLevel(level);
        member.setThreshold(threshold);
        member.setParson(parson);
        member.setShare(share);
        member.setName(name);
    }

    private String randomInt() {
        Random random = new Random();
        return Integer.toString(random.nextInt(10) + 1);
    }
}
