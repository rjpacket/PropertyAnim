package com.taovo.rjp.propertyanim.bullet_screen;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.y;

/**
 * Created by Administrator on 2017/9/3.
 */

public class BulletScreenView extends FrameLayout {
    private Context mContext;
    private int mWidth;
    private int mHeight;
    private int bulletHeight;
    private int space;
    private List<Rect> rightRect = new ArrayList<>();
    private List<BulletView> bulletViews = new ArrayList<>();

    public BulletScreenView(Context context) {
        super(context);
        initView(context, null);
    }

    public BulletScreenView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public BulletScreenView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();

        if(mHeight != 0) {
            rightRect.clear();
            int lineCount = mHeight / bulletHeight - 1;
            int totalSpace = mHeight - lineCount * bulletHeight;
            space = totalSpace / (lineCount + 1);
            for (int i = 0; i < lineCount; i++) {
                rightRect.add(new Rect(mWidth, (bulletHeight + space) * i, mWidth + 100, (bulletHeight + space) * (i + 1)));
            }
        }
    }

    public void initView(Context context, AttributeSet attrs) {
        mContext = context;

        BulletView bulletView = new BulletView(mContext);
        //动态测量view的宽度
        int spec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        bulletView.measure(spec,spec);
        bulletHeight = bulletView.getMeasuredHeight();
    }

    public void addBullet(List<Bullet> bullets) {
        for (Bullet bullet : bullets) {
            BulletView bulletView = new BulletView(mContext);
            bulletView.setData(bullet);
            int spec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            bulletView.measure(spec,spec);
            int measuredWidth = bulletView.getMeasuredWidth();
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.topMargin = seekLineTop(Math.random() * mHeight);
            addView(bulletView, params);
            bulletViews.add(bulletView);
            bulletView.startAnim(new Point(mWidth, y), new Point(-measuredWidth, y), 5000);
        }
    }

    /**
     * 找到一个合适的高度
     * @param randomHeight
     * @return
     */
    private int seekLineTop(double randomHeight) {
        for (Rect rect : rightRect) {
            if(rect.contains(mWidth + 1, (int) randomHeight)){
                return rect.top + space;
            }
        }
        return 0;
    }
}
