package com.github.wangkangmao;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.wangkangmao.view.GallaryHorizonalScrollView;
import com.github.wangkangmao.view.RevealDrawable;

/**
 * Created time 2016-12-17 10:20
 * Class description:
 *
 * @author wangkangmao
 * @email: kangmao.wang.@foxmail.com
 */
public class MainActivity extends AppCompatActivity {

    private int[] mImgIds_default = new int[]{
            R.drawable.ic_zero_default,
            R.drawable.ic_one_default,
            R.drawable.ic_two_default,
            R.drawable.ic_three_default,
            R.drawable.ic_four_default,
            R.drawable.ic_five_default,
            R.drawable.ic_six_default,
            R.drawable.ic_seven_default,
            R.drawable.ic_eight_default,
            R.drawable.ic_nine_default,
    };
    private int[] mImgIds_default_selected = new int[]{
            R.drawable.ic_zero_selected,
            R.drawable.ic_one_selected,
            R.drawable.ic_two_selected,
            R.drawable.ic_three_selected,
            R.drawable.ic_four_selected,
            R.drawable.ic_five_selected,
            R.drawable.ic_six_selected,
            R.drawable.ic_seven_selected,
            R.drawable.ic_eight_selected,
            R.drawable.ic_nine_selected,
    };

    public Drawable[] revealDrawables;
    private GallaryHorizonalScrollView hzv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
    }

    private void initData() {
        revealDrawables = new Drawable[mImgIds_default.length];
    }

    private void initView() {
        for (int i = 0; i < mImgIds_default.length; i++) {
            RevealDrawable rd = new RevealDrawable(
                    getResources().getDrawable(mImgIds_default[i]),
                    getResources().getDrawable(mImgIds_default_selected[i]),
                    1);
            revealDrawables[i] = rd;
        }
        hzv = (GallaryHorizonalScrollView) findViewById(R.id.hsv);
        hzv.addImageViews(revealDrawables);
    }

}
