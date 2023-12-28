package com.test;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class NumberFlipView extends View {
    private int currentNumber = 4876;
    private int nextNumber = 4877;

    private Paint paint;
    private float xOffset;
    private float yOffset;
    private float transitionY;

    private ValueAnimator animator;

    public NumberFlipView(Context context) {
        super(context);
        init();
    }

    public NumberFlipView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(100);
        paint.setTextAlign(Paint.Align.LEFT);

        yOffset = 150; // 设置垂直方向偏移
        xOffset = 20; // 设置水平方向偏移

        startNumberAnimation();
    }

    private void startNumberAnimation() {
        animator = ValueAnimator.ofFloat(0f, 1f);
        animator.setDuration(1000); // 1秒内完成动画

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float fraction = animation.getAnimatedFraction();
                transitionY = yOffset - fraction * paint.getTextSize() * 2; // 控制翻页效果的位置
                invalidate();
            }
        });

        animator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        String currentNumberString = String.valueOf(currentNumber);
        String nextNumberString = String.valueOf(nextNumber);

        float x = xOffset;

        for (int i = 0; i < currentNumberString.length(); i++) {
            char currentDigit = currentNumberString.charAt(i);
            char nextDigit = nextNumberString.charAt(i);

            String currentDigitString = String.valueOf(currentDigit);
            String nextDigitString = String.valueOf(nextDigit);

            // 绘制当前数字
            canvas.drawText(currentDigitString, x, yOffset, paint);

            // 绘制翻页效果
            if (i == currentNumberString.length() - 1) {
                float transitionX = x + paint.measureText(currentDigitString);
                canvas.drawText(nextDigitString, x , transitionY, paint);
            }

            // 增加水平方向偏移
            x += paint.measureText(currentDigitString);
        }
    }

    public void startNextNumber() {
        currentNumber++;
        nextNumber++;
        startNumberAnimation();
    }
}
