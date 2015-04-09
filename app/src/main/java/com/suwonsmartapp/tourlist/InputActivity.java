
package com.suwonsmartapp.tourlist;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.suwonsmartapp.tourlist.mapalbum.MapAlbumActivity;

public class InputActivity extends ActionBarActivity implements View.OnClickListener {

    private EditText mtitleFront;
    private EditText mtitleMiddle;
    private EditText mtitleRear;
    private EditText mcontents;
    private Button mTravelDateBtn;
    private DatePicker view;
    private Button mLocationBtn;

    private int year;
    private int month;
    private int day;
    private DatePickerDialog.OnDateSetListener dateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    SimpleDateFormat sf = new SimpleDateFormat("yyyy년 MM월 dd일");
                    GregorianCalendar tmpCalendar = new GregorianCalendar();
                    tmpCalendar.set(year, monthOfYear, dayOfMonth);

                    mTravelDateBtn.setText(sf.format(tmpCalendar.getTime()));

                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        mtitleFront = (EditText) findViewById(R.id.title_front);
        mtitleMiddle = (EditText) findViewById(R.id.title_middle);
        mtitleRear = (EditText) findViewById(R.id.title_rear);
        mcontents = (EditText) findViewById(R.id.contents);
        mTravelDateBtn = (Button) findViewById(R.id.date_Btn);
        mLocationBtn = (Button) findViewById(R.id.location_Btn);

        GregorianCalendar calendar = new GregorianCalendar();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        mTravelDateBtn.setText(year + "년  " + (month + 1) + "월  " + day + "일");

        mTravelDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog date = new DatePickerDialog(InputActivity.this, dateSetListener,
                        year, month, day);
                date.show();
            }
        });

        mLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), MapAlbumActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.submit_Btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(getApplicationContext(), ResultsActivity.class));
    }
}
