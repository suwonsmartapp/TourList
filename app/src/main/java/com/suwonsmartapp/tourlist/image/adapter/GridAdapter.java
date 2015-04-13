
package com.suwonsmartapp.tourlist.image.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.suwonsmartapp.tourlist.R;
import com.suwonsmartapp.tourlist.image.bitmapUtil.BitmapScaleSetting;
import com.suwonsmartapp.tourlist.image.bitmapUtil.DynamicBitmapLoading;

import java.util.List;

/**
 * Created by sol on 2015-04-02.
 */
public class GridAdapter extends BaseAdapter {

    private static final String TAG = GridAdapter.class.getSimpleName();

    private LayoutInflater layoutInflater;
    private ViewHolder viewHolder;
    private Context mContext;
    private int mMainImgPosition;
    private int mClickedImgPosition;
    private boolean mShowBtns;


    // Data
    private BitmapScaleSetting mBitmapScaleSetting;
    private List<Uri> mUriList;
    // private ImageCache mImageCache;


    /**
     * 생성자
     */
    public GridAdapter(Context context, List<Uri> uriList, BitmapScaleSetting bitmapScaleSetting) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        mUriList = uriList;
        mContext = context;
        mBitmapScaleSetting = bitmapScaleSetting;

        mMainImgPosition = -1; // 초기화
        mClickedImgPosition = -1;
        mShowBtns = false;
    }

    /**
     * ViewHolder 클래스(이미지 로딩 최적화를 위해서)
     */
    static class ViewHolder {
        ImageView imageView;
        LinearLayout btnListLayout;
        View vStroke;
        Button btnTrash;
        Button btnCheck;
        // String path;
    }

    @Override
    public int getCount() {
        return mUriList.size();
    }

    @Override
    public Uri getItem(int position) {
        return mUriList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if (view == null) {
            view = layoutInflater.inflate(R.layout.grid_image_item, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.iv_item_in_gv);
            Button btnTrash = (Button) view.findViewById(R.id.btn_trash);
            Button btnCheck = (Button) view.findViewById(R.id.btn_main_check);
            LinearLayout btnListLayout = (LinearLayout) view.findViewById(R.id.ll_image_btns);
            View vStroke = view.findViewById(R.id.v_stroke);

            viewHolder = new ViewHolder();
            viewHolder.imageView = imageView;
            viewHolder.btnTrash = btnTrash;
            viewHolder.btnCheck = btnCheck;
            viewHolder.vStroke = vStroke;
            viewHolder.btnListLayout = btnListLayout;

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }


        /*
            // 캐시 사용
            String path = mTourImgList.get(position).getPath();
            Bitmap cacheBitmap = mImageCache.getBitmap(path);
            if (cacheBitmap == null) {
                mImageCache.addBitmap(path, mTourImgList.get(position).getBitmap());
                cacheBitmap = mImageCache.getBitmap(path);
            }
            viewHolder.imageView.setImageBitmap(cacheBitmap);
        */

        /*
        if (cancelPotentialWork(mUriList.get(position), viewHolder.imageView)) {
            final BitmapWorkerTask task = new BitmapWorkerTask(viewHolder.imageView, mBitmapScaleSetting);
            final AsyncDrawable asyncDrawable = new AsyncDrawable(mUriList.get(position), task);
            viewHolder.imageView.setImageDrawable(asyncDrawable);
            task.execute(mUriList.get(position));
        }
        */

        // BitmapWorkerTask task = new BitmapWorkerTask(viewHolder.imageView, mBitmapScaleSetting);
        // task.execute(mUriList.get(position));


        // 비트맵 이미지 붙이기
        DynamicBitmapLoading dynamicBitmapLoading = new DynamicBitmapLoading(mContext);
        dynamicBitmapLoading.loadBitmap(mUriList.get(position), viewHolder.imageView, mBitmapScaleSetting);


        if (mMainImgPosition != -1 && mMainImgPosition == position) { // 대표 이미지표시
            viewHolder.imageView.setBackgroundResource(R.drawable.img_main_selected);
            viewHolder.vStroke.setVisibility(View.VISIBLE);
        } else {
            viewHolder.imageView.setBackgroundResource(R.drawable.img_grid_style);
            viewHolder.vStroke.setVisibility(View.GONE);
        }


        if (ismShowBtns() == false) { // 버튼 리스트 표시하지 않음
            viewHolder.btnListLayout.setVisibility(View.GONE);
        } else {
            if (mClickedImgPosition == position) { // 선택된 위치의 버튼리스트만 표시
                viewHolder.btnListLayout.setVisibility(View.VISIBLE);
            } else {
                viewHolder.btnListLayout.setVisibility(View.GONE);
            }
        }


        // 이미지 클릭
        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ismShowBtns()) { // true (한번 클릭된 것)
                    if (mClickedImgPosition == position) { // 안보이게
                        setmShowBtns(false);
                    } else { // 다른 이미지가 클릭 된 것
                        mClickedImgPosition = position;
                    }
                } else { // false - 보이게 하는 것
                    mClickedImgPosition = position;
                    setmShowBtns(true);
                }
                notifyDataSetChanged(); // 이미지 로드를 다시 타는 듯???
            }
        });

        // 휴지통 버튼 클릭
        viewHolder.btnTrash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUriList.remove(position);
                if (mMainImgPosition == position) { // 지우는 거랑 같으면
                    mMainImgPosition = -1;
                }
                setmShowBtns(false);
                notifyDataSetChanged();
            }
        });

        // check 버튼 클릭(대표이미지로 선택)
        viewHolder.btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMainImgPosition == position) { // 이전에 선택된 position이랑 같으면
                                                    // 대표이미지 해제
                    mMainImgPosition = -1;
                } else { // 이전에 선택된 것과 다르면 대표이미지로 선택
                    mMainImgPosition = position;
                }
                setmShowBtns(false);
                notifyDataSetChanged();
            }
        });

        return view;
    }

    // Getter and Setters
    public boolean ismShowBtns() {
        return mShowBtns;
    }
    public void setmShowBtns(boolean mShowBtns) {
        this.mShowBtns = mShowBtns;
    }

    public int getmMainImgPosition() {
        return mMainImgPosition;
    }

    // 메모리에 정해진 갯수의 이미지만 가지고 있게 해야된다(정한갯수만큼) - 캐시, 캐시로딩?
    // 리스트뷰에 이미지 로드하는 예제를 찾다보면 캐시를 구현해 놓은게 있다
    // 스크롤해도 동적으로 로딩하고, 로딩하다가 화면 넘어가면 캔슬하고... 어댑터, 스레드에서?
    // 캐시가 내장된 라이브러리 사용
    //

    // Managing Bitmap Memory
    // softReference, weekReference

    // 스레드 써서 동적 로딩?

    // 메모리 캐시는 의미가 없다? 이미지리스트에 넣는 순간 메모리를 먹고 캐시에서도 메모리를 먹으니깐? ... 이해가 안가넹

    // 로드하는 부분을 스레드 처리를 해서 디스크 캐시를 처리해야된다



}
