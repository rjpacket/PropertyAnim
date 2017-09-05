package com.taovo.rjp.propertyanim;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.taovo.rjp.propertyanim.evaluator.NumberEvaluator;

public class ScrollNumberActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_number);

        final TextView tvNumber1 = (TextView) findViewById(R.id.tv_number1);

        ValueAnimator numberAnim = ValueAnimator.ofObject(new NumberEvaluator(), "", "676767676767");
        numberAnim.setDuration(3000);
        numberAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                String number = (String) animation.getAnimatedValue();
                tvNumber1.setText(number);
            }
        });
        numberAnim.start();
        numberAnim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                tvNumber1.setText("676767676767");
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        final TextView tvNumber2 = (TextView) findViewById(R.id.tv_number2);

        ValueAnimator numberAnim2 = ValueAnimator.ofObject(new NumberEvaluator(), "", "345364");
        numberAnim2.setDuration(3000);
        numberAnim2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                String number = (String) animation.getAnimatedValue();
                tvNumber2.setText(number);
            }
        });
        numberAnim2.start();
        numberAnim2.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                tvNumber2.setText("345364");
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });


        final TextView tvNumber3 = (TextView) findViewById(R.id.tv_number3);

        ValueAnimator numberAnim3 = ValueAnimator.ofObject(new NumberEvaluator(), "", "48");
        numberAnim3.setDuration(3000);
        numberAnim3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                String number = (String) animation.getAnimatedValue();
                tvNumber3.setText(number);
            }
        });
        numberAnim3.start();
        numberAnim3.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                tvNumber3.setText("48");
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }
}
