package com.github.wangkangmao.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
/**
 * Created time 2016-12-17 11:25
 * Class description:
 *
 * @author wangkangmao
 * @email: kangmao.wang.@foxmail.com
 */

public class GallaryHorizonalScrollView extends HorizontalScrollView implements OnTouchListener {

    private LinearLayout container;
    private int centerX;
    private int icon_width;

    public GallaryHorizonalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GallaryHorizonalScrollView(Context context) {
        super(context);
        init();
    }

    private void init() {
        //在ScrollView里面放置一个水平线性布局，再往里面放置很多ImageView
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        container = new LinearLayout(getContext());
        container.setLayoutParams(params);
        setOnTouchListener(this);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        //得到某一张图片的宽度
        View v = container.getChildAt(0);
        icon_width = v.getWidth();
        //得到hzv的中间x坐标
        centerX = getWidth() / 2;
        //处理下，中心坐标改为中心图片的左边界
        centerX = centerX - icon_width / 2;
        //给LinearLayout和hzv之间设置边框距离
        container.setPadding(centerX, 0, centerX, 0);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            //渐变图片
            reveal();
        }

        return false;
    }

    private void reveal() {
        // 渐变效果
        //得到hzv滑出去的距离
        int scrollX = getScrollX();
        //找到两张渐变的图片的下标--左，右
        int index_left = scrollX / icon_width;
        int index_right = index_left + 1;
        //设置图片的level
        for (int i = 0; i < container.getChildCount(); i++) {
            if (i == index_left || i == index_right) {
                //变化
                //比例：
                float ratio = 5000f / icon_width;
                ImageView iv_left = (ImageView) container.getChildAt(index_left);
                //scrollX%icon_width:代表滑出去的距离
                //滑出去了icon_width/2  icon_width/2%icon_width
                iv_left.setImageLevel(
                        (int) (5000 - scrollX % icon_width * ratio)
                );
                //右边
                if (index_right < container.getChildCount()) {
                    ImageView iv_right = (ImageView) container.getChildAt(index_right);
                    //scrollX%icon_width:代表滑出去的距离
                    //滑出去了icon_width/2  icon_width/2%icon_width
                    iv_right.setImageLevel(
                            (int) (10000 - scrollX % icon_width * ratio)
                    );
                }
            } else {
                //灰色
                ImageView iv = (ImageView) container.getChildAt(i);
                iv.setImageLevel(0);
            }
        }


    }

    //添加图片的方法
    public void addImageViews(Drawable[] revealDrawables) {
        for (int i = 0; i < revealDrawables.length; i++) {
            ImageView img = new ImageView(getContext());
            img.setImageDrawable(revealDrawables[i]);
            container.addView(img);
            if (i == 0) {
                img.setImageLevel(5000);
            }
        }
        addView(container);
    }

}
