package com.taovo.rjp.propertyanim.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.taovo.rjp.propertyanim.R;
import com.taovo.rjp.propertyanim.evaluator.BookEvaluator;
import com.taovo.rjp.propertyanim.evaluator.BookValue;

/**
 * @author Gimpo create on 2017/9/5 10:31
 * @email : jimbo922@163.com
 */

public class BookOpenView extends FrameLayout implements ValueAnimator.AnimatorUpdateListener, Animator.AnimatorListener {

    private TextView tvBookName;
    private TextView tvBookAuthor;

    public BookOpenView(@NonNull Context context) {
        super(context);
        initView(context);
    }

    public BookOpenView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public BookOpenView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_book_open_view, this);
        setBackgroundColor(Color.TRANSPARENT);
        tvBookName = (TextView) findViewById(R.id.tv_book_name);
        tvBookAuthor = (TextView) findViewById(R.id.tv_book_author);
    }

    /**
     * 开启动画
     */
    public void startAnim(BookValue startValue, BookValue endValue) {
        ValueAnimator valueAnimator = ValueAnimator.ofObject(new BookEvaluator(), startValue, endValue);
        valueAnimator.setDuration(3000);
        valueAnimator.addUpdateListener(this);
        valueAnimator.addListener(this);

        tvBookName.setPivotX(0);
        tvBookName.setPivotY(500);
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(tvBookName, "rotationY", 0, -180);
        objectAnimator.setDuration(1000);
        objectAnimator.setStartDelay(300);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(valueAnimator, objectAnimator);
        animatorSet.start();
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        BookValue midBookValue = (BookValue) animation.getAnimatedValue();

        tvBookName.setX(midBookValue.getLeft());
        tvBookName.setY(midBookValue.getTop());
        ViewGroup.LayoutParams layoutParams = tvBookName.getLayoutParams();
        layoutParams.width = midBookValue.getRight() - midBookValue.getLeft();
        layoutParams.height = midBookValue.getBottom() - midBookValue.getTop();
        tvBookName.setLayoutParams(layoutParams);

        tvBookAuthor.setX(midBookValue.getLeft());
        tvBookAuthor.setY(midBookValue.getTop());
        ViewGroup.LayoutParams layoutParams1 = tvBookAuthor.getLayoutParams();
        layoutParams1.width = midBookValue.getRight() - midBookValue.getLeft();
        layoutParams1.height = midBookValue.getBottom() - midBookValue.getTop();
        tvBookAuthor.setLayoutParams(layoutParams1);
    }

    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {
        ViewGroup parent = (ViewGroup) getParent();
        parent.removeView(this);
    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }
}
