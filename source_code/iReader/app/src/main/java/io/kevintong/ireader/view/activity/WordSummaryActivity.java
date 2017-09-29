package io.kevintong.ireader.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.BackgroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.kevintong.ireader.R;
import io.kevintong.ireader.bean.WordDetails;
import io.kevintong.ireader.network.httpMethods.WordHttpMethod;
import io.kevintong.ireader.network.subscribers.NetworkSubscriber;
import io.kevintong.ireader.network.subscribers.NetworkSubscriberListener;
import io.kevintong.widgets.ArticleTextView;
import io.kevintong.widgets.WordClickSpan;

public class WordSummaryActivity extends AppCompatActivity {

    public static final String INTENT_KEY_ARTICLE = "intent_key_article";
    public static final String INTENT_KEY_SELECTION_START = "intent_key_selection_start";
    public static final String INTENT_KEY_SELECTION_END = "intent_key_selection_end";
    public static final String INTENT_KEY_SELECTION_WORD = "intent_key_selection_word";
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.rl_word_summary)
    LinearLayout rlWordSummary;
    @Bind(R.id.tv_article)
    ArticleTextView tvArticle;
    @Bind(R.id.cv_article_layout)
    CardView cvArticleLayout;
    @Bind(R.id.tv_word)
    TextView tvWord;
    @Bind(R.id.tv_pronunciation)
    TextView tvPronunciation;
    @Bind(R.id.tv_definition)
    TextView tvDefinition;

    private String article;
    private int selectionStart;
    private int selectionEnd;
    private String selectionWord;
    private WordDetails wordDetails;
    private MediaPlayer audioPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_summary);
        ButterKnife.bind(this);

        getIntentExtra();
        setArticleTextView();
        setAudioFabButton();

        getWordDetailsNetWork(selectionWord);

    }

    /**
     * 设置 单词播放按钮
     */
    private void setAudioFabButton() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.show();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (wordDetails == null || audioPlayer == null) {
                    return;
                }

                //当播放完音频资源时，会触发onCompletion事件，可以在该事件中释放音频资源，
                //以便其他应用程序可以使用该资源:
                audioPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mp.release();//释放音频资源

                    }
                });
                try {
                    //在播放音频资源之前，必须调用Prepare方法完成些准备工作
                    audioPlayer.prepare();
                    //开始播放音频
                    audioPlayer.start();

                } catch (IllegalStateException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 设置文章 textview
     */
    private void setArticleTextView() {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(article);
        if (selectionStart != -1 && selectionEnd != -1) {
            spannableStringBuilder.setSpan(new BackgroundColorSpan(Color.parseColor("#17A093")), selectionStart, selectionEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        tvArticle.setText(article, TextView.BufferType.SPANNABLE);
        tvArticle.setWordClickListener(new WordClickSpan.WordClickListener() {
            @Override
            public void onWordClick(String word, int selectionStart, int selectionEnd) {
                getWordDetailsNetWork(word);
            }

        });
    }

    private String getIntentExtra() {
        Intent intent = getIntent();
        article = intent.getStringExtra(INTENT_KEY_ARTICLE);
        selectionStart = intent.getIntExtra(INTENT_KEY_SELECTION_START, -1);
        selectionEnd = intent.getIntExtra(INTENT_KEY_SELECTION_END, -1);
        selectionWord = intent.getStringExtra(INTENT_KEY_SELECTION_WORD);
        return article;
    }

    /**
     * 从网络获取单词解释
     *
     * @param word
     */
    public void getWordDetailsNetWork(final String word) {
        tvWord.setText(word);
        tvPronunciation.setText("/…/");
        tvDefinition.setText("正在查询中……");
        wordDetails = null;

        NetworkSubscriberListener subscriberListener = new NetworkSubscriberListener<WordDetails>() {
            @Override
            public void onNext(WordDetails wordDetails) {
                WordSummaryActivity.this.wordDetails = wordDetails;

                Log.d("NetworkSubscriber", "wordDetails = " + wordDetails);
                if (wordDetails == null) {
                    return;
                }

                audioPlayer = createAudioPlayler(wordDetails.getAudio());

                tvPronunciation.setText("/" + wordDetails.getPronunciation() + "/");
                String definition = wordDetails.getDefinition().replace("\\n", " ");
                tvDefinition.setText(definition);
            }

            @Override
            public void onError(Throwable e) {

            }
        };
        WordHttpMethod.getInstance().getWordDetails(new NetworkSubscriber<WordDetails>(subscriberListener, this), word);
    }

    /**
     * 创建 单词音频对象
     *
     * @return
     */
    public MediaPlayer createAudioPlayler(String url) {
        MediaPlayer mp = new MediaPlayer();
        try {
            mp.setDataSource(url);
        } catch (IllegalArgumentException e) {
            return null;
        } catch (IllegalStateException e) {
            return null;
        } catch (IOException e) {
            return null;
        }
        return mp;
    }

}
