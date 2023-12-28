package com.test;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.text.style.DynamicDrawableSpan;

class RoundBackgroundSpan extends DynamicDrawableSpan {
    private int backgroundColor;
    private int textColor;
    private float radius;

    RoundBackgroundSpan(int backgroundColor, int textColor, float radius) {
        this.backgroundColor = backgroundColor;
        this.textColor = textColor;
        this.radius = radius;
    }

    @Override
    public Drawable getDrawable() {
        return new Drawable() {
            @Override
            public void draw(Canvas canvas) {
                Paint paint = new Paint();
                paint.setColor(backgroundColor);
                paint.setStyle(Paint.Style.FILL);

                RectF rect = new RectF(0, 0, canvas.getWidth(), canvas.getHeight());
                canvas.drawRoundRect(rect, radius, radius, paint);

                paint.setColor(textColor);
                paint.setTextSize(rect.height());
                paint.setTextAlign(Paint.Align.CENTER);

                Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
                float baseline = (rect.bottom + rect.top - fontMetrics.bottom - fontMetrics.top) / 2f;

                canvas.drawText("222222", rect.centerX(), baseline, paint);
            }

            @Override
            public void setAlpha(int alpha) {
            }

            @Override
            public void setColorFilter(ColorFilter colorFilter) {
            }

            @Override
            public int getOpacity() {
                return PixelFormat.TRANSLUCENT;
            }
        };
    }
}