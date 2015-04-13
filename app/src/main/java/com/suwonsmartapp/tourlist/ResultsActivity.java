
package com.suwonsmartapp.tourlist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.suwonsmartapp.tourlist.image.GalleryActivity;
import com.suwonsmartapp.tourlist.imageviewer.PictureLayout;

import java.util.List;

public class ResultsActivity extends ActionBarActivity implements View.OnClickListener {

    private static final int REQUEST_CODE_GALLERY = 1;
    private static final String TAG = ResultsActivity.class.getSimpleName();

    private PictureLayout mPictureLayout;
    private Button mBtnGetPicture;

    long id;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        mPictureLayout = (PictureLayout) findViewById(R.id.tableLayout_pictures);
        mBtnGetPicture = (Button) findViewById(R.id.btn_picture_from_gallery);
        mBtnGetPicture.setOnClickListener(this);

        mPictureLayout.setOnClickListener(this);

        Intent intent = getIntent();

        if (intent != null) {
            id = Long.valueOf(getIntent().getLongExtra("id", -1));
            Log.d("ResultsActivity TAG", String.valueOf(id));
            // Toast.makeText(getApplicationContext(), "저장된 아이디 : " + String.valueOf(id), Toast.LENGTH_SHORT).show();
        }

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

    @Override
    public void onClick(View v) {

        // Intent intent = new Intent(getApplicationContext(),
        // ImageViewer.class);
        // intent.putExtra("imageList", data);
        //
        // startActivity(intent);

        switch (v.getId()) {
            case R.id.btn_picture_from_gallery:
                Log.d(TAG, "사진선택");
                Intent intent = new Intent(getApplicationContext(), GalleryActivity.class);
                intent.putExtra("id", id);
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK) {
            List<Uri> uriList = (List<Uri>) data.getSerializableExtra("pictureIdList");

            Toast.makeText(getApplicationContext(), uriList.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
