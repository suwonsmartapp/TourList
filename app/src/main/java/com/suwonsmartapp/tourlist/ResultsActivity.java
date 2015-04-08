
package com.suwonsmartapp.tourlist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

public class ResultsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_results, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_crystal) {

            return true;
        } else if (id == R.id.action_deletion) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("삭제 하시겠습니까?");
            builder.setCancelable(false);
            builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                // 확인 버튼 클릭시 설정
                public void onClick(DialogInterface dialog, int WhichButton) {
                    finish();

                }
            });

            builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                // 취소 버튼 클릭시 설정
                public void onClick(DialogInterface dialog, int WhichButton) {
                    dialog.cancel();
                }
            });
            builder.show();

            return true;
        } else if (id == R.id.action_preferences) {
            Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
