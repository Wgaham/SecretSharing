package com.wgaham.secretsharing;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class AddActivity extends AppCompatActivity implements View.OnClickListener, TimePicker.OnTimeChangedListener {
    public String view;
    private int currentapiVersion = android.os.Build.VERSION.SDK_INT;
    private TextView startTimeView, endTimeView;
    private EditText secretNameEditText, secretEditText;
    private int startHour, startMinute, endHour, endMinute;
    private StringBuffer startTimeBuffer = null, endTimeBuffer = null;
    public static final int FILE_REQUESTCODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Toolbar toolbar = (Toolbar) findViewById(R.id.add_toolbar);
        setSupportActionBar(toolbar);
        secretNameEditText = (EditText) findViewById(R.id.secret_name_editText);
        secretEditText = (EditText) findViewById(R.id.secret_value);
        secretEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
        startTimeView = (TextView) findViewById(R.id.start_time);
        endTimeView = (TextView) findViewById(R.id.end_time);
        Button memberButton = (Button) findViewById(R.id.member);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        memberButton.setOnClickListener(this);
        findViewById(R.id.start_time_set).setOnClickListener(this);
        findViewById(R.id.end_time_set).setOnClickListener(this);
        findViewById(R.id.file_select).setOnClickListener(this);
        Calendar calendar = Calendar.getInstance();
        if (calendar.get(Calendar.AM_PM) == 0) {
            startHour = endHour = calendar.get(Calendar.HOUR);
        } else {
            startHour = endHour = calendar.get(Calendar.HOUR) + 12;
        }
        startMinute = endMinute = calendar.get(Calendar.MINUTE);
        startTimeBuffer = new StringBuffer();
        endTimeBuffer = new StringBuffer();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_time_set:
                AlertDialog.Builder startTimeBuilder = new AlertDialog.Builder(AddActivity.this);
                startTimeBuilder.setPositiveButton("设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (startTimeBuffer.length() > 0) {
                            startTimeBuffer.delete(0, startTimeBuffer.length());
                        }
                        startTimeView.setText(startTimeBuffer.append(String.valueOf(startHour < 10 ? "0" + startHour : startHour)).append(":").append(String.valueOf(startMinute < 10 ? "0" + startMinute : startMinute)));
                        dialog.dismiss();
                    }
                });
                startTimeBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog startDialog = startTimeBuilder.create();
                View startDialogView = View.inflate(AddActivity.this, R.layout.start_time, null);
                TimePicker startPicker = (TimePicker) startDialogView.findViewById(R.id.start_picker);
                if (currentapiVersion >= Build.VERSION_CODES.M) {
                    startPicker.setHour(startHour);
                    startPicker.setMinute(startMinute);
                } else {
                    startPicker.setCurrentHour(startHour);
                    startPicker.setCurrentMinute(startMinute);
                }
                startPicker.setIs24HourView(true);
                startPicker.setOnTimeChangedListener(this);
                startDialog.setTitle("设置开始时间");
                startDialog.setView(startDialogView);
                startDialog.show();
                break;
            case R.id.end_time_set:
                AlertDialog.Builder endTimeBuilder = new AlertDialog.Builder(AddActivity.this);
                endTimeBuilder.setPositiveButton("设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (endTimeBuffer.length() > 0) {
                            endTimeBuffer.delete(0, endTimeBuffer.length());
                        }
                        endTimeView.setText(endTimeBuffer.append(String.valueOf(endHour < 10 ? "0" + endHour : endHour)).append(":").append(String.valueOf(endMinute < 10 ? "0" + endMinute : endMinute)));
                        dialog.dismiss();
                    }
                });
                endTimeBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog endDialog = endTimeBuilder.create();
                View endDialogView = View.inflate(AddActivity.this, R.layout.end_time, null);
                TimePicker endPicker = (TimePicker) endDialogView.findViewById(R.id.end_picker);
                if (currentapiVersion >= Build.VERSION_CODES.M) {
                    endPicker.setHour(endHour);
                    endPicker.setMinute(endMinute);
                } else {
                    endPicker.setCurrentHour(endHour);
                    endPicker.setCurrentMinute(endMinute);
                }
                endPicker.setIs24HourView(true);
                endPicker.setOnTimeChangedListener(this);
                endDialog.setTitle("设置结束时间");
                endDialog.setView(endDialogView);
                endDialog.show();
                break;
            case R.id.member:
                String secretName = secretNameEditText.getText().toString().trim();
                String secretStr = (secretEditText.getText().toString());
                if ("".equals(secretName) || "".equals(secretStr) || startTimeBuffer.length() == 0 || endTimeBuffer.length() == 0) {
                    Toast.makeText(AddActivity.this, "请将数据填写完整", Toast.LENGTH_SHORT).show();
                    break;
                }
                int secretInt = Integer.parseInt(secretStr);
                SecretTemp secretTemp = new SecretTemp();
                secretTemp.setSecretName(secretName);
                secretTemp.setSecretValue(secretInt);
                secretTemp.setStartTime(startTimeBuffer.toString());
                secretTemp.setEndTime(endTimeBuffer.toString());
                Intent intent = new Intent(this, MemberAddActivity.class);
                intent.putExtra("secret", secretTemp);
                startActivity(intent);
                break;
            case R.id.file_select:
                Intent fileExploer = new Intent(Intent.ACTION_GET_CONTENT);
                fileExploer.setType("*/*");
                fileExploer.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(fileExploer, FILE_REQUESTCODE);
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

    @Override
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
        switch (view.getId()) {
            case R.id.start_picker:
                this.startHour = hourOfDay;
                this.startMinute = minute;
                break;
            case R.id.end_picker:
                this.endHour = hourOfDay;
                this.endMinute = minute;
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == FILE_REQUESTCODE) {
                Uri uri = data.getData();
                Toast.makeText(AddActivity.this, uri.toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
