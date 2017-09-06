package com.taovo.rjp.propertyanim.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Region;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.taovo.rjp.propertyanim.R;

/**
 * @author Gimpo create on 2017/9/6 17:50
 * @email : jimbo922@163.com
 */

public class BookView extends FrameLayout {

    private TextView pageOne;
    private TextView pageTwo;
    private int mWidth;
    private int mHeight;
    private float mMaxLength;
    private Path mPath0;
    private Path mPath1;
    private Point mBezierStart1;
    private Point mBezierControl1;
    private Point mTouch;
    private Point mBezierEnd1;
    private Point mBezierEnd2;
    private Point mBezierControl2;
    private Point mBezierStart2;
    private float mCornerX;
    private float mCornerY;
    private Point mBeziervertex1;
    private Point mBeziervertex2;
    private float mDegrees;
    private boolean mIsRTandLB;
    private int mTouchToCornerDis;
    private GradientDrawable mBackShadowDrawableLR;
    private GradientDrawable mBackShadowDrawableRL;
    private View currentView;

    public BookView(@NonNull Context context) {
        super(context);
        initView(context);
    }

    public BookView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public void initView(Context context){
        LayoutInflater.from(context).inflate(R.layout.layout_book_view, this);
        pageOne = (TextView) findViewById(R.id.page_one);
        pageTwo = (TextView) findViewById(R.id.page_two);

        mPath0 = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {

    }

    @Override
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        if(child.equals(currentView)) {
            drawCurrentPageArea(canvas, child, mPath0);
        } else {
            drawNextPageAreaAndShadow(canvas, child);
        }
        return true;
    }

    private void drawCurrentPageArea(Canvas canvas, View child, Path path) {
        mPath0.reset();
        mPath0.moveTo(mBezierStart1.x, mBezierStart1.y);
        mPath0.quadTo(mBezierControl1.x, mBezierControl1.y, mBezierEnd1.x,
                mBezierEnd1.y);
        mPath0.lineTo(mTouch.x, mTouch.y);
        mPath0.lineTo(mBezierEnd2.x, mBezierEnd2.y);
        mPath0.quadTo(mBezierControl2.x, mBezierControl2.y, mBezierStart2.x,
                mBezierStart2.y);
        mPath0.lineTo(mCornerX, mCornerY);
        mPath0.close();

        canvas.save();
        canvas.clipPath(path, Region.Op.XOR);//这里即裁剪出了当前页应该绘制的区域
        child.draw(canvas);//这里再将canvas交给子视图绘制
        canvas.restore();
    }

    private void drawNextPageAreaAndShadow(Canvas canvas, View child) {
        mPath1.reset();
        mPath1.moveTo(mBezierStart1.x, mBezierStart1.y);
        mPath1.lineTo(mBeziervertex1.x, mBeziervertex1.y);
        mPath1.lineTo(mBeziervertex2.x, mBeziervertex2.y);
        mPath1.lineTo(mBezierStart2.x, mBezierStart2.y);
        mPath1.lineTo(mCornerX, mCornerY);
        mPath1.close();

        mDegrees = (float) Math.toDegrees(Math.atan2(mBezierControl1.x
                - mCornerX, mBezierControl2.y - mCornerY));
        int leftx;
        int rightx;
        GradientDrawable mBackShadowDrawable;
        if (mIsRTandLB) {
            leftx = (int) (mBezierStart1.x);
            rightx = (int) (mBezierStart1.x + mTouchToCornerDis / 4);
            mBackShadowDrawable = mBackShadowDrawableLR;
        } else {
            leftx = (int) (mBezierStart1.x - mTouchToCornerDis / 4);
            rightx = (int) mBezierStart1.x;
            mBackShadowDrawable = mBackShadowDrawableRL;
        }
        canvas.save();
        canvas.clipPath(mPath0);
        canvas.clipPath(mPath1, Region.Op.INTERSECT);//这里裁剪出下一页应该绘制的区域
        child.draw(canvas);//这里子视图开始绘制
        canvas.rotate(mDegrees, mBezierStart1.x, mBezierStart1.y);//这里旋转是用来画阴影的
        mBackShadowDrawable.setBounds(leftx, (int) mBezierStart1.y, rightx,
                (int) (mMaxLength + mBezierStart1.y));
        mBackShadowDrawable.draw(canvas);
        canvas.restore();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mWidth = getWidth();
        mHeight = getHeight();
        mMaxLength = (float) Math.hypot(mWidth, mHeight);               //添加在这里

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        return super.onTouchEvent(event);
    }
}
