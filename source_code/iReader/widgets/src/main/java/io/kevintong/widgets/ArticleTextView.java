package io.kevintong.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.Layout;
import android.text.Spannable;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewTreeObserver;

import java.util.ArrayList;
import java.util.List;

public final class ArticleTextView
        extends android.support.v7.widget.AppCompatTextView {

    private StaticLayout textLayout;
    private int indexStart = 0;
    private int indexEnd = 0;
    private WordClickSpan.WordClickListener wordClickListener;

    public ArticleTextView(Context context) {
        this(context,null);
    }

    public ArticleTextView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ArticleTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        ViewTreeObserver vto = getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                setEachWordSpan();
            }
        });
        setMovementMethod(LinkMovementMethod.getInstance());
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        
//        justifyCompsing(canvas);

    }

    /**
     * 两端对齐
     * @param canvas
     */
    private void justifyCompsing(Canvas canvas) {
        TextPaint textPaint = getPaint();
        textPaint.setColor(getCurrentTextColor());
        textPaint.drawableState = getDrawableState();


        String text = getText().toString();

        float lineY = 0;
        lineY += getTextSize() * 1f;

        textLayout =  new StaticLayout(getText(), textPaint, canvas.getWidth(), Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
        final float desiredLineWidth = getMeasuredWidth();

        indexStart = 0;
        indexEnd = 0;

        for (int i = 0, lineCount = textLayout.getLineCount(); i < lineCount; i++) {
            int lineStart = textLayout.getLineStart(i);
            int lineEnd = textLayout.getLineEnd(i);

            String line = text.substring(lineStart, lineEnd);

            drawJustifyText(line, canvas, lineY, desiredLineWidth, textPaint,i,(i < lineCount - 1));
            lineY += getLineHeight();
        }
    }

    /**
     * 绘制两端对齐文字
     * @param lineString
     * @param canvas
     * @param y
     * @param desiredLineWidth
     * @param paint
     * @param line
     * @param isJustify
     */
    private void drawJustifyText(final String lineString, final Canvas canvas, final float y, final float desiredLineWidth, TextPaint paint, int line, boolean isJustify) {
        String[] words = lineString.split(" ");

        float lineWidthWithoutSpaces = StaticLayout.getDesiredWidth(lineString.replace(" ", ""), getPaint());
        float spacing = (desiredLineWidth - lineWidthWithoutSpaces) / (words.length - 1);
        float spacingWidth = StaticLayout.getDesiredWidth(" ", getPaint());
        float x = 0;

        for (String word : words) {
            Log.d("drawJustifyText", "word = " + word + "; x = " + x + "; y = " + y);
            indexEnd = word.length() + indexStart;
            Log.d("drawJustifyText", "word = " + word + "; indexStart = " + indexStart + "; indexEnd = " + indexEnd);
            float cw = StaticLayout.getDesiredWidth(word, paint);

            if(getSelectionStart() == indexStart && getSelectionEnd() == indexEnd){
                paint.setColor(Color.RED);
                Paint paintBackground = new Paint();
                paintBackground.setColor(Color.CYAN);
                Rect rect = new Rect();
                paint.getTextBounds(word, 0, word.length(), rect);
                canvas.drawRect(x,line*rect.height(),x + cw,y ,paintBackground);
            }else {
                paint.setColor(Color.BLACK);
            }
            
            canvas.drawText(word, x, y, paint);
            if(isJustify){
                x += cw + spacing;
            }else{
                x += cw +  spacingWidth;
            }

            indexStart = indexEnd + 1;
        }
    }

    /**
     * 为单词设置 点击事件
     */
    public void setEachWordSpan(){
        Spannable spans = (Spannable)getText();
        Integer[] indices = getIndices(
                getText().toString().trim(), ' ');
        int start = 0;
        int end = 0;
        Log.d("indices","indices.length = "+ indices.length );
        // to cater last/only word loop will run equal to the length of indices.length
        for (int i = 0; i <= indices.length; i++) {
            WordClickSpan clickSpan = new WordClickSpan(this,wordClickListener);
            // to cater last/only word
            end = (i < indices.length ? indices[i] : spans.length());
            spans.setSpan(clickSpan, start, end,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            start = end + 1;
        }

        setHighlightColor(Color.parseColor("#17A093"));
    }

    /**
     * 得到每个单词的起始位置
     * @param string
     * @param separator
     * @return
     */
    public Integer[] getIndices(String string, char separator) {
        int pos = string.indexOf(separator, 0);
        List<Integer> indices = new ArrayList<Integer>();
        while (pos != -1) {
            indices.add(pos);
            pos = string.indexOf(separator, pos + 1);
        }
        return indices.toArray(new Integer[0]);
    }

    public void setWordClickListener(WordClickSpan.WordClickListener wordClickListener) {
        this.wordClickListener = wordClickListener;
    }
}