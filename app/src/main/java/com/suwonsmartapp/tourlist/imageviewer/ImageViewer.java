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

import com.suwonsmartapp.tourlist.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by junsuk on 15. 4. 10..
 */
public class ImageViewer extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_imageviewer);

        Intent intent = getIntent();
        if (intent != null) {
            Serializable data = intent.getSerializableExtra("imageList");
            if (data instanceof ArrayList) {
                ImagePagerAdapter adapter = new ImagePagerAdapter(getSupportFragmentManager(), (ArrayList)data);

                ((ViewPager)findViewById(R.id.pager)).setAdapter(adapter);
            }
        }


    }

    public static class ImagePagerAdapter extends FragmentPagerAdapter {
        private ArrayList<Integer> mData;

        public ImagePagerAdapter(FragmentManager fm, ArrayList<Integer> data) {
            super(fm);
            mData = data;
        }

        @Override
        public Fragment getItem(int position) {
            return ImageViewerFragment.newInstance(mData.get(position));
        }

        @Override
        public int getCount() {
            return mData.size();
        }
    }

    public static class ImageViewerFragment extends Fragment {
        int mRes;

        static ImageViewerFragment newInstance(int num) {
            ImageViewerFragment f = new ImageViewerFragment();

            // Supply num input as an argument.
            Bundle args = new Bundle();
            args.putInt("num", num);
            f.setArguments(args);

            return f;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            mRes = getArguments() != null ? getArguments().getInt("num") : -1;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_pager_imageviewer, container, false);
            ImageView imageView = (ImageView)view.findViewById(R.id.iv_image);
            imageView.setImageResource(mRes);
            return view;
        }
    }
}





