/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 수원스마트앱개발학원
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.suwonsmartapp.tourlist.adapter;

import com.suwonsmartapp.tourlist.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by junsuk on 15. 4. 6.. ResultsActivity 에서 사용할 Picture 어댑터 샘플
 */
public class PictureAdapter extends BaseAdapter {

    private Context mContext;
    private List<Integer> mList;

    public PictureAdapter(Context context) {
        mContext = context;
        mList = new ArrayList<>();

        init();
    }

    // TODO 실제 데이타로 교체
    private void init() {
        for (int i = 0; i < 5; i++) {
            mList.add(R.drawable.car);
            mList.add(R.drawable.girl);
            mList.add(R.drawable.gold_apple);
        }
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SquareImageView imageView;

        if (convertView == null) {
            imageView = new SquareImageView(mContext);
        } else {
            imageView = (SquareImageView) convertView;
        }

        // TODO 실제 데이타로 교체
        imageView.setImageResource(mList.get(position));

        return imageView;
    }
}
