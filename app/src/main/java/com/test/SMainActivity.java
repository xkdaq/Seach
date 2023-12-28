package com.test;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dalipan.search.R;

import java.util.Arrays;
import java.util.List;

public class SMainActivity extends AppCompatActivity {

    private final Handler handler = new Handler();

    private int idx = 0;

    private final List<String> list = Arrays.asList("1", "21339", "12", "123319", "24", "6", "247",
            "5226", "63", "378", "234389", "12395", "2", "1289", "32212", "400");

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

//        NumberFlipView numberFlipView = findViewById(R.id.numberFlipView);
//
//        findViewById(R.id.textView).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                numberFlipView.startNextNumber();
//            }
//        });


//        MultiScrollNumber scrollNumber = (MultiScrollNumber) findViewById(R.id.scroll_number);
//        scrollNumber.setTextColors(new int[]{R.color.red01, R.color.orange01,
//                R.color.blue01, R.color.green01, R.color.purple01});
//        scrollNumber.setScrollVelocity(100);

        RandomTextView randomTextView = findViewById(R.id.randomTextView);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                idx ++;
                //scrollNumber.setNumber(2048 +idx);
                //tickerView.setCharacterLists(""+2048 +idx);
                randomTextView.setText("sadadadasdsad"+(120481 +idx));
                randomTextView.setSpeeds(RandomTextView.LOW_FIRST);
                randomTextView.start();
                handler.postDelayed(this, 1000L);
            }
        }, 1000L);


    }


    public void test1() {
        // 获取要显示的字符串
        String originalString = "Pengguna 4000 telah berhasil memberikan pinjaman hari ini";

        // 创建一个SpannableStringBuilder对象
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(originalString);

        // 设置每个数字的背景颜色、文本颜色和圆角半径
        int[] backgroundColors = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.CYAN};
        int textColor = Color.WHITE;
        float radius = 10.0f; // 圆角半径

        int offset = 0; // 用于记录插入空格后的偏移量

        for (int i = 0; i < originalString.length(); i++) {
            char c = originalString.charAt(i);

            if (Character.isDigit(c)) {
                int digit = Character.getNumericValue(c);
                int startIndex = i + offset;
                int endIndex = startIndex + 1;

                Log.e("xxx", "=====" + digit);

                if (digit >= 0) {
                    // 替换数字的背景颜色和文本颜色
                    Bitmap bitmap = createRoundRectBitmap("" + digit, backgroundColors[0], textColor, radius);
                    ImageSpan imageSpan = new ImageSpan(this, bitmap, ImageSpan.ALIGN_BASELINE);
                    spannableStringBuilder.setSpan(imageSpan, startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                    // 插入空格
                    spannableStringBuilder.insert(endIndex, " ");

                    // 更新偏移量
                    offset++;
                }
            }
        }

        // 将SpannableString设置到TextView中
        TextView textView = findViewById(R.id.textView);
        textView.setText(spannableStringBuilder);

    }

    private Bitmap createRoundRectBitmap(String digit, int backgroundColor, int textColor, float radius) {
        Paint paint = new Paint();
        paint.setColor(backgroundColor);
        paint.setStyle(Paint.Style.FILL);

        Paint textPaint = new Paint();
        textPaint.setColor(textColor);
        textPaint.setTextSize(3 * radius);
        textPaint.setTextAlign(Paint.Align.CENTER);

        float textHeight = textPaint.descent() - textPaint.ascent();
        float textOffset = (textHeight / 2) - textPaint.descent();

//        float width = 2 * radius;
        float width = 40;
        float height = textHeight + 2 * textOffset;

        Log.e("======", "textOffset===" + textOffset);
        Log.e("======", "textHeight===" + textHeight);
        Log.e("======", "height===" + height);


        Bitmap bitmap = Bitmap.createBitmap((int) width, (int) height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        RectF rect = new RectF(0, 0 + textOffset, width, height);
        canvas.drawRoundRect(rect, radius, radius, paint);

        canvas.drawText(digit, width / 2, height / 2 + textOffset + textOffset / 2, textPaint);

        return bitmap;
    }
}
