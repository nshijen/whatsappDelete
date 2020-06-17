package com.shijen.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Shijen";
    AppCompatEditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.et_send);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d(TAG, "beforeTextChanged: s:" + s + " start:" + start + " count:" + count + " after" + after);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d("Shijen", "ontextChanged: start:" + start + "count: " + count + " before:" + before);
                s.toString();
                if (before > count) {
                    Log.d(TAG, "onTextChanged: sequence:" + s);
                    ImageSpan[] spans = ((Editable) s).getSpans(0, s.length(), ImageSpan.class);
                    for (int i = 0; i < spans.length; i++) {
                        int spanStart = ((Editable) s).getSpanStart(spans[i]);
                        int spanEnd = ((Editable) s).getSpanEnd(spans[i]) - 1;
                        Log.d(TAG, "onTextChanged: charAt:SpanStart:" + spanStart + " " + s.charAt(spanStart) + " charAtSpanEnd:" + spanEnd + " " + s.charAt(spanEnd));
                        Log.d(TAG, "onTextChanged: charAt:start+count:" + s.charAt(start + count));
                        if ((start + count - 1) > spanStart && (start + count - 1) <= spanEnd) {
                            Log.d("Shijen", "onTextChanged: SpanField:" + ((Editable) s).subSequence(spanStart, spanEnd));
                            ((Editable) s).delete(spanStart, spanEnd);
                            return;
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }

    public void addNane(View view) {
        String sample = "hi <mentions>@shijen</mentions> ,<mentions>@messi</mentions>how are you";

        editText.setText(getSpanableString(sample));
    }

    SpannableStringBuilder getSpanableString(String sample) {
        SpannableStringBuilder spannable = new SpannableStringBuilder(sample);
        String regex = "<mentions>@[\\w\\s]+</mentions>";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(sample);

        while (matcher.find()) {
            String group = matcher.group();
            String name = group.substring(group.lastIndexOf('@'), group.indexOf("</"));
            spannable.setSpan(new ImageSpan(textAsBitmap(name,
                    dpToPx(50, getApplicationContext()), getResources().getColor(R.color.colorAccent))),
                    matcher.start(),
                    matcher.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spannable;
    }


    Bitmap textAsBitmap(String text, Float textSize, int textColor) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(textSize);
        paint.setColor(textColor);
        paint.setTextAlign(Paint.Align.LEFT);
        Float baseline = -paint.ascent(); // ascent() is negative
        int width = (int) (paint.measureText(text) + 0.5f); // round
        int height = (int) (baseline + paint.descent() + 0.5f);
        Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(image);
        canvas.drawText(text, 0f, baseline, paint);
        return image;
    }

    public static float dpToPx(float dp, Context context) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }
}