package com.taovo.rjp.propertyanim.bullet_screen;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.taovo.rjp.propertyanim.R;

/**
 * Created by Administrator on 2017/9/3.
 */

public class BulletView extends LinearLayout {

    private ObjectAnimator animator;
    private ImageView ivHeadView;
    private TextView tvTitle;
    private TextView tvZan;

    public BulletView(Context context) {
        super(context);
        initView(context);
    }

    public BulletView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public BulletView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public void initView(Context context){
        LayoutInflater.from(context).inflate(R.layout.layout_bullet_view, this);
        ivHeadView = (ImageView) findViewById(R.id.iv_head_view);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvZan = (TextView) findViewById(R.id.tv_zan);

    }

    public void setData(Bullet bullet){
        tvTitle.setText(bullet.getTitle());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            animator.removeAllListeners();
            animator.cancel();
        }
        if(event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL){
            animator.start();
        }
        return super.onTouchEvent(event);
    }

    /**
     * 开启动画 用duration控制速度变化
     * @param start
     * @param end
     * @param duration
     */
    public void startAnim(Point start, Point end, int duration){
        animator = ObjectAnimator.ofFloat(this, "translationX", start.x, end.x);
        animator.setDuration(duration);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = (Float) animation.getAnimatedValue();

            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                ViewGroup parent = (ViewGroup) getParent();
                parent.removeView(BulletView.this);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();
    }

}
