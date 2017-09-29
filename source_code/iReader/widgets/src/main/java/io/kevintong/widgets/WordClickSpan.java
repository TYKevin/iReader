package io.kevintong.widgets;

import android.graphics.Color;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

/**
 * Created by kevin on 2017/9/29.
 */

public class WordClickSpan extends ClickableSpan {

    TextView textView;
    WordClickListener wordClickListener;

    public WordClickSpan(TextView textView,WordClickListener wordClickListener) {
        this.textView = textView;
        this.wordClickListener = wordClickListener;
    }

    @Override
    public void onClick(View widget) {
        String s = textView.getText()
                .subSequence(textView.getSelectionStart(),
                        textView.getSelectionEnd()).toString();

        if(wordClickListener != null){
            wordClickListener.onWordClick(s,textView.getSelectionStart(),textView.getSelectionEnd());
        }
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setColor(Color.BLACK);
        ds.setUnderlineText(false);
    }


    public interface WordClickListener{
        void onWordClick(String word,int selectionStart,int selectionEnd);
    }
}
