package com.github.wangkangmao.view;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.Gravity;

/**
 * Created time 2016-12-17 10:25
 * Class description:
 *
 * @author wangkangmao
 * @email: kangmao.wang.@foxmail.com
 */

public class RevealDrawable extends Drawable {

    private Drawable mUnSelectedDrawable;
    private Drawable mSelectedDrawable;
    private int mOrientation;
    public static final int HORIZONTAL = 1;
    public static final int VERTICAL = 2;

    public RevealDrawable(Drawable unselectedDrawable, Drawable selectedDrawable, int orientation) {
        this.mUnSelectedDrawable = unselectedDrawable;
        this.mSelectedDrawable = selectedDrawable;
        this.mOrientation = orientation;
    }

    @Override
    public void draw(Canvas canvas) {
        //由两张图片裁剪并且拼接而成
        //得到当前Drawable自身矩形区域，把当前区域分为两半

        int level = getLevel();
        if (level == 10000 || level == 0) {
            mUnSelectedDrawable.draw(canvas);
        } else if (level == 5000) {
            mSelectedDrawable.draw(canvas);
        } else {
            Rect bounds = getBounds();
            /**
             * 先画灰色部分
             */
            //从一个已经有的矩形区域抠出一个矩形来
            Rect rectLeft = new Rect();
            int widthLeft = bounds.width();
            int heightLeft = bounds.height();
            // level: 0~5000~10000
            // ratio: -1~1 (-1~0,0~1)
            float ratiounSelected = (level / 5000f) - 1f;
            if (mOrientation == HORIZONTAL) {
                widthLeft = (int) (widthLeft * (widthLeft * Math.abs(ratiounSelected)));
            }
            if (mOrientation == VERTICAL) {
                heightLeft = (int) (heightLeft * Math.abs(ratiounSelected));
            }
            getCilpReact(bounds, ratiounSelected < 0 ? Gravity.LEFT : Gravity.RIGHT, rectLeft, widthLeft, heightLeft);
            canvas.save();
            //把画布剪切
            canvas.clipRect(rectLeft);
            //画没有选中图片
            mUnSelectedDrawable.draw(canvas);
            canvas.restore();
            /**
             * 再画彩色部分
             */
            Rect rectRight = new Rect();
            int withRight = bounds.width();
            int heightRight = bounds.height();
            //level :1~5000~10000
            // ratio :-1~1 (-1~0~1)
            float ratioSelected = ((float) level / 5000f) - 1f;
            if (mOrientation == HORIZONTAL) {
                withRight -= (withRight * Math.abs(ratioSelected));
            }
            if (mOrientation == VERTICAL) {
                withRight -= (heightRight * Math.asin(ratioSelected));
            }

            //得到右边矩形区域
            getCilpReact(bounds, ratioSelected < 0 ? Gravity.RIGHT : Gravity.LEFT, rectRight, withRight, heightRight);
            canvas.save();
            //把画布剪切
            canvas.clipRect(rectRight);
            //画选中图片
            mSelectedDrawable.draw(canvas);
            canvas.restore();
        }
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        //定好两个Drawable宽高定好
        mUnSelectedDrawable.setBounds(bounds);
        mSelectedDrawable.setBounds(bounds);
    }

    @Override
    protected boolean onLevelChange(int level) {
        invalidateSelf();
        return true;

    }

    @Override
    public int getIntrinsicWidth() {
        //得到Drawable的实际宽度
        return Math.max(mSelectedDrawable.getIntrinsicWidth(), mUnSelectedDrawable.getIntrinsicWidth());
    }


    @Override
    public int getIntrinsicHeight() {
        //得到Drawable的实际高度
        return Math.max(mSelectedDrawable.getIntrinsicHeight(), mUnSelectedDrawable.getIntrinsicHeight());
    }

    private void getCilpReact(Rect bounds, int gravity, Rect rect, int width, int height) {
        Gravity.apply(gravity, width, height, bounds, rect);
    }

    @Override
    public void setAlpha(int i) {

    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return 0;
    }
}
