package com.taovo.rjp.propertyanim;

import android.animation.TypeEvaluator;

/**
 * @author Gimpo create on 2017/9/4 15:25
 * @email : jimbo922@163.com
 */

public class NumberEvaluator implements TypeEvaluator<String> {

    @Override
    public String evaluate(float fraction, String startValue, String endValue) {
        int size = endValue.length();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            if(i * 1.0 / size > fraction) {
                sb.append((int) Math.ceil(Math.random() * 9));
            }else{
                sb.append(String.valueOf(endValue).charAt(i));
            }
        }
        return sb.toString();
    }
}
