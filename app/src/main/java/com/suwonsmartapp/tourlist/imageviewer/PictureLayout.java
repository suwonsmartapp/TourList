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

package com.suwonsmartapp.tourlist.imageviewer;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.suwonsmartapp.tourlist.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by junsuk on 15. 4. 7.. 사진을 테이블 형태로 출력 해 주는 레이아웃
 */
public class PictureLayout extends TableLayout implements View.OnClickListener {

    private Context mContext;
    private List<Integer> mList;
    private int mNumColums = 1;

    private OnClickListener mOnClickListener;

    public interface OnClickListener {
        void onClick(Serializable data);
    }

    public void setOnClickListener(OnClickListener listener) {
        mOnClickListener = listener;
    }

    public PictureLayout(Context context) {
        this(context, null);
    }

    public PictureLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        mContext = context;
        mList = new ArrayList<>();

        // 컬럼 수를 2로 설정
        setNumColumns(6);
        setStretchAllColumns(true);

        // TODO 실제 데이타로 교체
        init();

        setOnClickListener(this);
    }

    @Override
    protected void onFinishInflate() {
        setLayout();
    }

    private void setLayout() {
        TableRow.LayoutParams params = new TableRow.LayoutParams(0, 0, 1.0f);
        TableRow tableRow = new TableRow(mContext);
        for (int i = 0; i < mList.size(); i++) {
            if (i % mNumColums == 0) {
                tableRow = new TableRow(mContext);
            }
            SquareImageView squareImageView = new SquareImageView(mContext);
            squareImageView.setImageResource(mList.get(i));
            tableRow.addView(squareImageView, params);

            if (i % mNumColums == mNumColums - 1 || i == mList.size() - 1) {
                addView(tableRow);
            }
        }

    }

    private void init() {
        for (int i = 0; i < 5; i++) {
            mList.add(R.drawable.car);
            mList.add(R.drawable.girl);
            mList.add(R.drawable.gold_apple);
        }
    }

    /**
     * 컬럼 수를 지정한다
     * 
     * @param numColumns 컬럼 수
     */
    public void setNumColumns(int numColumns) {
        if (numColumns < 1) {
            throw new IllegalArgumentException("numColumns must be > 0");
        }
        mNumColums = numColumns;
    }

    @Override
    public void onClick(View v) {
        if (mOnClickListener != null) {
            mOnClickListener.onClick((Serializable)mList);
        }
    }
}
