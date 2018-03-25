package com.wgaham.secretsharing;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class MemberSelectActivity extends AppCompatActivity implements View.OnClickListener {
    private int id;
    private int secretValueFound;
    private CheckBox l01, l11, l12, l21, l22, l23;
    private List<Integer> Shares = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_select);
        Toolbar toolbar = (Toolbar) findViewById(R.id.member_select_toorbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        Button restructureButton = (Button) findViewById(R.id.restructure_button);
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        Intent intent = getIntent();
        id = intent.getIntExtra(SecretShowActivity.SECRET_ID, 0);
        Secret secret = DataSupport.find(Secret.class, id, true);
        List<Member> memberList = secret.getMemberList();
        secretValueFound = secret.getSecret();
        List<String> memberNames = new ArrayList<>();
        for (int i = 0; i < memberList.size(); i++) {
            String name = memberList.get(i).getName();
            int share = memberList.get(i).getShare();
            memberNames.add(name);
            Shares.add(share);
        }
        l01 = (CheckBox) findViewById(R.id.checkBox_l01);
        l11 = (CheckBox) findViewById(R.id.checkBox_l11);
        l12 = (CheckBox) findViewById(R.id.checkBox_l12);
        l21 = (CheckBox) findViewById(R.id.checkBox_l21);
        l22 = (CheckBox) findViewById(R.id.checkBox_l22);
        l23 = (CheckBox) findViewById(R.id.checkBox_l23);
        l01.setText("L01: " + memberNames.get(0));
        l11.setText("L11: " + memberNames.get(1));
        l12.setText("L12: " + memberNames.get(2));
        l21.setText("L21: " + memberNames.get(3));
        l22.setText("L22: " + memberNames.get(4));
        l23.setText("L23: " + memberNames.get(5));
        restructureButton.setOnClickListener(this);
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
            case R.id.restructure_button:
                List<Integer> selectedCoordinates = new ArrayList<>();
                List<Integer> selectedShares = new ArrayList<>();
                boolean isL1 = false, isL2 = false, isL3 = false;
                if (l01.isChecked()) {
                    selectedCoordinates.add(1);
                    selectedCoordinates.add(0);
                    selectedShares.add(Shares.get(0));
                    isL1 = true;
                }
                if (l11.isChecked()) {
                    selectedCoordinates.add(1);
                    selectedCoordinates.add(1);
                    selectedShares.add(Shares.get(1));
                    isL2 = true;
                }
                if (l12.isChecked()) {
                    selectedCoordinates.add(2);
                    selectedCoordinates.add(1);
                    selectedShares.add(Shares.get(2));
                    isL2 = true;
                }
                if (l21.isChecked()) {
                    selectedCoordinates.add(1);
                    selectedCoordinates.add(2);
                    selectedShares.add(Shares.get(3));
                    isL3 = true;
                }
                if (l22.isChecked()) {
                    selectedCoordinates.add(2);
                    selectedCoordinates.add(2);
                    selectedShares.add(Shares.get(4));
                    isL3 = true;
                }
                if (l23.isChecked()) {
                    selectedCoordinates.add(3);
                    selectedCoordinates.add(2);
                    selectedShares.add(Shares.get(5));
                    isL3 = true;
                }
                if (!(isL1 && isL2 && isL3)) {
                    Toast.makeText(MemberSelectActivity.this, "请确保每个等级都有人参与", Toast.LENGTH_SHORT).show();
                    break;
                }
                int[] selectedCoordinatesArr = new int[selectedCoordinates.size()];
                for (int i = 0; i < selectedCoordinates.size(); i++) {
                    selectedCoordinatesArr[i] = selectedCoordinates.get(i);
                }
                int[] selectedSharesArr = new int[selectedShares.size()];
                for (int i = 0; i < selectedShares.size(); i++) {
                    selectedSharesArr[i] = selectedShares.get(i);
                }

                Convey convey = new Convey();
                try {
                    double secretValue = convey.Receivingsequence(selectedCoordinatesArr, selectedSharesArr, 3);
                    final AlertDialog.Builder dialogBuild = new AlertDialog.Builder(MemberSelectActivity.this);
                    dialogBuild.setTitle("重构完成");
                    dialogBuild.setMessage("重构得到的秘密值为：" + secretValue + "\n记录的原始秘密值为：" + secretValueFound);
                    dialogBuild.setCancelable(false);
                    dialogBuild.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    dialogBuild.show();
                } catch (Exception e) {
                    Toast.makeText(this, "重构失败", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }
}
