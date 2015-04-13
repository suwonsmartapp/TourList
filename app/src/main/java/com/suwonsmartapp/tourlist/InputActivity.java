
package com.suwonsmartapp.tourlist;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.suwonsmartapp.tourlist.database.Info_LISTMT;
import com.suwonsmartapp.tourlist.database.TourListFacade;
import com.suwonsmartapp.tourlist.mapalbum.MapAlbumActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class InputActivity extends ActionBarActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener  {

    private static final String TAG = InputActivity.class.getSimpleName();
    private static final int REQUEST_CODE_RESULT = 1;

    private static final int REQUEST_CODE_A = 0x5a5a;
    private static final int REQUEST_CODE_B = 0xa5a5;
    String[] items = {
            "sunny", "cloudy", "rainy", "snowy", "windy", "hot", "warm", "cool", "freezing"
    };

    private EditText mtitleFront;
    private EditText mtitleMiddle;
    private EditText mtitleRear;
    private EditText mcontents;

    private Spinner mSpinner;
    private EditText mCompanion;
    private TextView textView1;
    private Spinner spinner;
    private Button mTravelDateBtn;
    private DatePicker view;
    private Button mLocationBtn;
    private TextView mid_location_addr;
    private int year;
    private int month;
    private int day;
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


        textView1 = (TextView) findViewById(R.id.weather_spinner);
        spinner = (Spinner) findViewById(R.id.spinner);
        mCompanion = (EditText) findViewById(R.id.companion);

        spinner.setOnItemSelectedListener(this);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getApplicationContext(), android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

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
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        textView1.setText(items[position]);
    }

    public void onNothingSelected(AdapterView<?> parent) {
        textView1.setText("");
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.submit_Btn:
                TourListFacade facade = new TourListFacade(getApplicationContext());

                Info_LISTMT data = new Info_LISTMT();
                data.title1 = mtitleFront.getText().toString();
                data.title2 = mtitleMiddle.getText().toString();
                data.title3 = mtitleRear.getText().toString();
                data.contents = mcontents.getText().toString();
                data.weather = spinner.getSelectedItem().toString();
                data.companion = mCompanion.getText().toString();
                data.location = mid_location_addr.getText().toString();
                data.tdt = mTravelDateBtn.getText().toString();

                long rowId = facade.save(data);
                Log.d(TAG, String.valueOf(rowId));

                if (rowId != -1) {
                    Toast.makeText(getApplicationContext(), "저장 성공", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getApplicationContext(), ResultsActivity.class);
                    intent.putExtra("id", rowId);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "저장 실패", Toast.LENGTH_SHORT).show();
                }
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
