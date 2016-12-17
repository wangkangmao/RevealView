package com.github.wangkangmao.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

/**
 * Created time 2016-12-17 10:53
 * Class description:
 *
 * @author wangkangmao
 * @email: kangmao.wang.@foxmail.com
 */

public class MyView extends View {
    public MyView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.GREEN);
        canvas.save();//保存当前画布的状态，保存到画布栈里面了。
        canvas.clipRect(new Rect(100, 100, 500, 500));
        canvas.drawColor(Color.BLUE);
//        canvas.restore();//恢复画布状态。
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        canvas.drawCircle(100, 100, 100, paint);
        canvas.restore();
        paint.setColor(Color.YELLOW);
        canvas.drawCircle(150,150,100,paint);

    }
}
