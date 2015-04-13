
package com.suwonsmartapp.tourlist;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.suwonsmartapp.tourlist.database.Info_LISTMT;
import com.suwonsmartapp.tourlist.database.TourListFacade;
import com.suwonsmartapp.tourlist.mapalbum.MapAlbumActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class InputActivity extends ActionBarActivity implements View.OnClickListener {

    private static final String TAG = InputActivity.class.getSimpleName();
    private static final int REQUEST_CODE_RESULT = 1;
    private EditText mtitleFront;
    private EditText mtitleMiddle;
    private EditText mtitleRear;
    private EditText mcontents;
    private Spinner mSpinner;
    private EditText mCompanion;

    private Button mTravelDateBtn;
    private DatePicker view;
    private Button mLocationBtn;
    private TextView mid_location_addr;

    private int year;
    private int month;
    private int day;

    private static final int REQUEST_CODE_A = 0x5a5a;
    private static final int REQUEST_CODE_B = 0xa5a5;
    private String myAddress = "";
    private String myAddressError = "주소가 검색되지 않았습니다.";

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
        mSpinner = (Spinner) findViewById(R.id.spinner);
        mCompanion = (EditText) findViewById(R.id.companion);

        mTravelDateBtn = (Button) findViewById(R.id.date_Btn);
        mLocationBtn = (Button) findViewById(R.id.location_Btn);
        mid_location_addr = (TextView) findViewById(R.id.id_location_addr);

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
                // startActivity(intent);

                intent.putExtra("key", "MapAlbum");
                intent.putExtra("code", REQUEST_CODE_A);
                startActivityForResult(intent, REQUEST_CODE_A);

            }
        });

        findViewById(R.id.submit_Btn).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.submit_Btn:
                TourListFacade tourListFacade = new TourListFacade(getApplicationContext());

//                Info_LISTMT info_listmt = new Info_LISTMT(
//                        mtitleFront.getText().toString()
//                        , mtitleMiddle.getText().toString()
//                        , mtitleRear.getText().toString()
//                        , mcontents.getText().toString()
//                        , mSpinner.getSelectedItem().toString()
//                        , mCompanion.getText().toString()
//                        , "location"
//                        , mTravelDateBtn.getText().toString());

                Info_LISTMT info_listmt = new Info_LISTMT(mtitleFront.getText().toString());
                long savedId = tourListFacade.save(info_listmt);

                startActivity(new Intent(getApplicationContext(), ResultsActivity.class));
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_A && resultCode == RESULT_OK) {
            myAddress = data.getStringExtra("data");
            mid_location_addr.setText(myAddress);
        } else {
            mid_location_addr.setText(myAddressError);
        }
    }

}
