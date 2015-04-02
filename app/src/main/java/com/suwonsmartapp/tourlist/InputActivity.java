
package com.suwonsmartapp.tourlist;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.EditText;

public class InputActivity extends ActionBarActivity {

    private EditText mtitleFront;
    private EditText mtitleMiddle;
    private EditText mtitleRear;
    private EditText mcontents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        mtitleFront = (EditText) findViewById(R.id.title_front);
        mtitleMiddle = (EditText) findViewById(R.id.title_middle);
        mtitleRear = (EditText) findViewById(R.id.title_rear);
        mcontents = (EditText) findViewById(R.id.contents);

    }


}
