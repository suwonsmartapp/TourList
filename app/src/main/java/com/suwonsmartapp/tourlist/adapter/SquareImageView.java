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

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by junsuk on 15. 4. 6..
 */
public class SquareImageView extends ImageView {

    public SquareImageView(Context context) {
        this(context, null);
    }

    public SquareImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SquareImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setScaleType(ScaleType.CENTER_CROP);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 세로 값을 가로 값과 동일하게 설정하여 정사각형 모양이 되도록
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
