
package com.suwonsmartapp.tourlist.list;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.suwonsmartapp.tourlist.InputActivity;
import com.suwonsmartapp.tourlist.R;

public class ListActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {
    public static final String TAG = ListActivity.class.getSimpleName();
    private ListView mListAdapter;
    private ArrayList<TourList> tourList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        mListAdapter = (ListView) findViewById(R.id.lv_list_adapter);

        // 자료 셋팅
        tourListLoad();

        // Adapter 준비
        ListAdapter adapter = new ListAdapter(getApplicationContext(), 0, tourList);

        // View에 붙이기
        mListAdapter.setAdapter(adapter);

        mListAdapter.setOnItemClickListener(this);
    }

    void tourListLoad() {
        tourList = new ArrayList<>();
        tourList.add(new TourList(R.drawable.car, "제목 제목 1", "2015-01-01",
                "본문내용 본문내용 본문내용 본문내용 본문내용 본문내용 본문내용 본문내용 본문내용 본문내용 본문내용 본문내용 ",
                R.drawable.gold_apple, R.drawable.girl, R.drawable.gold_apple, R.drawable.car,
                R.drawable.car));
        tourList.add(new TourList(R.drawable.girl, "제목 제목 2", "2015-02-01",
                "본문내용 본문내용 본문내용 본문내용 본문내용 본문내용 본문내용 본문내용 본문내용 본문내용 본문내용 본문내용  본문내용",
                R.drawable.car, R.drawable.girl, R.drawable.gold_apple, R.drawable.car,
                R.drawable.car));
        tourList.add(new TourList(R.drawable.gold_apple, "제목 제목 3", "2015-03-01",
                "본문내용 본문내용 본문내용 본문내용 본문내용 본문내용 본문내용 본문내용 본문내용 본문내용 본문내용 본문내용  본문내용",
                R.drawable.car, R.drawable.girl, R.drawable.gold_apple, R.drawable.car,
                R.drawable.car));
        tourList.add(new TourList(R.drawable.car, "제목 제목 4", "2015-04-01",
                "본문내용 본문내용 본문내용 본문내용 본문내용 본문내용 본문내용 본문내용 본문내용 본문내용 본문내용 본문내용  본문내용",
                R.drawable.car, R.drawable.girl, R.drawable.gold_apple, R.drawable.car,
                R.drawable.car));
        tourList.add(new TourList(R.drawable.girl, "제목 제목 5", "2015-05-01",
                "본문내용 본문내용 본문내용 본문내용 본문내용 본문내용 본문내용 본문내용 본문내용 본문내용 본문내용 본문내용  본문내용",
                R.drawable.car, R.drawable.girl, R.drawable.gold_apple, R.drawable.car,
                R.drawable.car));
        tourList.add(new TourList(R.drawable.gold_apple, "제목 제목 6", "2015-06-01",
                "본문내용 본문내용 본문내용 본문내용 본문내용 본문내용 본문내용 본문내용 본문내용 본문내용 본문내용 본문내용  본문내용",
                R.drawable.car, R.drawable.girl, R.drawable.gold_apple, R.drawable.car,
                R.drawable.car));
        tourList.add(new TourList(R.drawable.car, "제목 제목 7", "2015-07-01",
                "본문내용 본문내용 본문내용 본문내용 본문내용 본문내용 본문내용 본문내용 본문내용 본문내용 본문내용 본문내용  본문내용",
                R.drawable.car, R.drawable.girl, R.drawable.gold_apple, R.drawable.car,
                R.drawable.car));
        tourList.add(new TourList(R.drawable.girl, "제목 제목 8", "2015-08-01",
                "본문내용 본문내용 본문내용 본문내용 본문내용 본문내용 본문내용 본문내용 본문내용 본문내용 본문내용 본문내용  본문내용",
                R.drawable.car, R.drawable.girl, R.drawable.gold_apple, R.drawable.car,
                R.drawable.car));
        tourList.add(new TourList(R.drawable.gold_apple, "제목 제목 9", "2015-09-01",
                "본문내용 본문내용 본문내용 본문내용 본문내용 본문내용 본문내용 본문내용 본문내용 본문내용 본문내용 본문내용  본문내용",
                R.drawable.car, R.drawable.girl, R.drawable.gold_apple, R.drawable.car,
                R.drawable.car));
        tourList.add(new TourList(R.drawable.car, "제목 제목 10", "2015-10-01",
                "본문내용 본문내용 본문내용 본문내용 본문내용 본문내용 본문내용 본문내용 본문내용 본문내용 본문내용 본문내용  본문내용",
                R.drawable.car, R.drawable.girl, R.drawable.gold_apple, R.drawable.car,
                R.drawable.car));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        startActivity(new Intent(getApplicationContext(), InputActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_first, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        // int id = item.getItemId();

        Intent intent = new Intent(this, InputActivity.class);
        startActivity(intent);

        return true;

    }
}
