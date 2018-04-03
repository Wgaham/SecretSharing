package com.wgaham.secretsharing;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class MemberSelectActivity extends AppCompatActivity implements View.OnClickListener {
    private int id, secretValueFound;
    private String filePath;
    private CheckBox l01, l11, l12, l21, l22, l23;
    private TextView fileView;
    private List<Integer> shares = new ArrayList<>();

    public static final int FILE_REQUESTCODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_select);
        Toolbar toolbar = (Toolbar) findViewById(R.id.member_select_toorbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        findViewById(R.id.file_select_dec).setOnClickListener(this);
        fileView = (TextView) findViewById(R.id.file_view_dec);
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
            shares.add(share);
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
            case R.id.file_select_dec:
                if (ContextCompat.checkSelfPermission(MemberSelectActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MemberSelectActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, FILE_REQUESTCODE);
                } else {
                    Intent fileExploer = new Intent(Intent.ACTION_GET_CONTENT);
                    fileExploer.setType("*/*");
                    fileExploer.addCategory(Intent.CATEGORY_OPENABLE);
                    startActivityForResult(fileExploer, FILE_REQUESTCODE);
                }
                break;
            case R.id.restructure_button:
                List<Integer> selectedCoordinates = new ArrayList<>();
                List<Integer> selectedShares = new ArrayList<>();
                boolean isL1 = false, isL2 = false, isL3 = false;
                if (l01.isChecked()) {
                    selectedCoordinates.add(1);
                    selectedCoordinates.add(0);
                    selectedShares.add(shares.get(0));
                    isL1 = true;
                }
                if (l11.isChecked()) {
                    selectedCoordinates.add(1);
                    selectedCoordinates.add(1);
                    selectedShares.add(shares.get(1));
                    isL2 = true;
                }
                if (l12.isChecked()) {
                    selectedCoordinates.add(2);
                    selectedCoordinates.add(1);
                    selectedShares.add(shares.get(2));
                    isL2 = true;
                }
                if (l21.isChecked()) {
                    selectedCoordinates.add(1);
                    selectedCoordinates.add(2);
                    selectedShares.add(shares.get(3));
                    isL3 = true;
                }
                if (l22.isChecked()) {
                    selectedCoordinates.add(2);
                    selectedCoordinates.add(2);
                    selectedShares.add(shares.get(4));
                    isL3 = true;
                }
                if (l23.isChecked()) {
                    selectedCoordinates.add(3);
                    selectedCoordinates.add(2);
                    selectedShares.add(shares.get(5));
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
                if ("".equals(fileView.getText().toString())) {
                    Toast.makeText(MemberSelectActivity.this, "请选择要解密的文件", Toast.LENGTH_SHORT).show();
                    break;
                }
                if (FileEncryptAndDecrypt.SALT_SUFFIX.equals(filePath.substring(filePath.lastIndexOf(".") - 1))) {
                    Toast.makeText(MemberSelectActivity.this, "请选择被本系统加密后的文件", Toast.LENGTH_SHORT).show();
                    break;
                }
                String fileNameBefore = filePath.substring(0, filePath.lastIndexOf("."));

                Convey convey = new Convey();
                try {
                    FileEncryptAndDecrypt fileEncryptAndDecrypt = new FileEncryptAndDecrypt();
                    double secretValue = convey.Receivingsequence(selectedCoordinatesArr, selectedSharesArr, 3);
                    int selectValueInt = (int) secretValue;
                    fileEncryptAndDecrypt.readFileLastByte(filePath, selectValueInt);
                    fileEncryptAndDecrypt.decrypt(filePath, fileNameBefore, selectValueInt);
                    final AlertDialog.Builder dialogBuild = new AlertDialog.Builder(MemberSelectActivity.this);
                    dialogBuild.setTitle("重构完成");
                    dialogBuild.setMessage("重构得到的秘密值为：" + selectValueInt + "\n记录的原始秘密值为：" + secretValueFound + "\n请到原始目录寻找解密后的文件");
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case FILE_REQUESTCODE:
                    Uri uri = data.getData();
                    filePath = Tool.getPathAfterKitKat(MemberSelectActivity.this, uri);
                    fileView.setText(filePath.substring(filePath.lastIndexOf("/") + 1));


            }
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case FILE_REQUESTCODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent fileExploer = new Intent(Intent.ACTION_GET_CONTENT);
                    fileExploer.setType("*/*");
                    fileExploer.addCategory(Intent.CATEGORY_OPENABLE);
                    startActivityForResult(fileExploer, FILE_REQUESTCODE);
                } else {
                    Toast.makeText(this, "拒绝权限", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
