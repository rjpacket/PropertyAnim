package com.taovo.rjp.propertyanim.evaluator;

import android.animation.TypeEvaluator;

/**
 * @author Gimpo create on 2017/9/5 11:22
 * @email : jimbo922@163.com
 */

public class BookEvaluator implements TypeEvaluator<BookValue> {
    @Override
    public BookValue evaluate(float fraction, BookValue startValue, BookValue endValue) {
        int startLeft = startValue.getLeft();
        int startTop = startValue.getTop();
        int startRight = startValue.getRight();
        int startBottom = startValue.getBottom();

        int endLeft = endValue.getLeft();
        int endTop = endValue.getTop();
        int endRight = endValue.getRight();
        int endBottom = endValue.getBottom();

        BookValue bookValue = new BookValue();
        bookValue.setLeft((int) (startLeft - fraction * (startLeft - endLeft)));
        bookValue.setTop((int) (startTop - fraction * (startTop - endTop)));
        bookValue.setRight((int) (startRight - fraction * (startRight - endRight)));
        bookValue.setBottom((int) (startBottom - fraction * (startBottom - endBottom)));

        return bookValue;
    }
}
