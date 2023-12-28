//package com.test;
//
//import android.graphics.Canvas;
//import android.graphics.Paint;
//import android.graphics.RectF;
//import android.text.TextPaint;
//import android.text.style.BackgroundColorSpan;
//
//public class RoundBackgroundColorSpan extends BackgroundColorSpan {
//    private int backgroundColor;
//    private int textColor;
//    private float padding; // 间距
//
//    public RoundBackgroundColorSpan(int backgroundColor, int textColor, float padding) {
//        super(backgroundColor);
//        this.backgroundColor = backgroundColor;
//        this.textColor = textColor;
//        this.padding = padding;
//    }
//
//    @Override
//    public void updateDrawState(TextPaint ds) {
//        super.updateDrawState(ds);
//        ds.setColor(textColor);
//        ds.bgColor = backgroundColor;
//        ds.setUnderlineText(false);
//    }
//
//
//    @Override
//    public void drawBackground(
//            Canvas canvas, Paint paint, int left, int right, int top, int baseline, int bottom,
//            CharSequence text, int start, int end, int lnum) {
//
//        RectF rect = new RectF(left + padding, top, right - padding, bottom);
//        paint.setColor(backgroundColor);
//
//        // 设置圆角效果
//        float radius = 20.0f; // 圆角半径
//        canvas.drawRoundRect(rect, radius, radius, paint);
//    }
//
//}