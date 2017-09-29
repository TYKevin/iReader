package io.kevintong.ireader.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.kevintong.ireader.R;
import io.kevintong.widgets.ArticleTextView;
import io.kevintong.widgets.WordClickSpan;

public class ArticleBrowseActivity extends AppCompatActivity {


    private String articleContent = "KIEV — The European Union warned Ukraine on Thursday time was running out to revive shelved deals on free trade and political association by meeting the bloc'articleContent concerns over the jailing of opposition leader Yulia Tymoshenko and bringing in reforms.\n" +
            "\n" +
            "A senior EU official also made it clear the agreements would fall through if Ukraine joined the Russia-led post-Soviet Customs Union trade bloc. \"We have a window of opportunity. But time is short,'' Stefan Fuele, the European Commissioner for Enlargement and European Neighbourhood Policy, said on a visit to Ukraine.\n" +
            "\n" +
            "Brussels put off signing the landmark agreements after a Ukrainian court jailed former prime minister Tymoshenko, President Viktor Yanukovich'articleContent main opponent, on an abuse- of-office charge in October 2011.\n" +
            "\n" +
            "The EU says the Tymoshenko case and those of other prosecuted opposition politicians are examples of selective justice and are a barrier to Ukraine'articleContent ambition of European integration.\n" +
            "\n" +
            "Two other issues raised by the bloc are related to the electoral system, which came under fire from Western observers following the parliamentary election in October, and legal reforms needed to bring Ukraine closer to EU standards.\n" +
            "\n" +
            "\"The European Union is committed to signing the association agreement...provided there is determined action and tangible progress on the three key issues: selective justice, addressing the shortcomings of the October election and advancing the association agenda reforms,'' Fuele told reporters. \"After several recent setbacks in Ukraine there is a need to regain confidence that Ukraine could emerge as a modern European country.''\n" +
            "\n" +
            "Fuele, whose visit may set the tone of a Feb. 25 EU-Ukraine summit, said the two agreements could be signed at the EU'articleContent Eastern Partnership summit in November if the former Soviet republic met the bloc'articleContent conditions.\n" +
            "\n" +
            "But he warned the Kiev government that joining a customs union with Russia, aggressively promoted by Moscow, would ruin those prospects.\n" +
            "\n" +
            "\"Joining any structure which would imply transferring the ability to set tariffs and define its trade policy to a supranational body would mean that Ukraine would no longer be able to implement the tariff dismantling agreed with the European Union in the context of the DCFTA [Deep and Comprehensive Free Trade Agreement],'' Fuele said in a speech at the Ukrainian parliament.\n" +
            "\n" +
            "\"It would also not be able anymore to regulate areas such as food standards, or technical product standards, all of them important in the framework of the DCFTA. It will not be able to integrate economically with the European Union,\" he continued.\n" +
            "\n" +
            "Ukrainian officials say they are committed to European integration. But they say they are also looking for a way to cooperate with the Customs Union because both blocs are Ukraine'articleContent major trade partners.\n" +
            "\n" +
            "Fuele urged Ukraine to make sure it adopts and implements laws that actually work and adhere to European standards, citing as an example the law on state procurement - purchases of goods and services by the government.\n" +
            "\n" +
            "The EU suspended some of its Ukraine financial aid programs after Kiev adopted a law on state procurement which Brussels said was riddled with loopholes and thus failed to ensure transparent and competitive procedures.";


    @Bind(R.id.tv_article)
    ArticleTextView tvArticle;
    @Bind(R.id.cv_article_layout)
    CardView cvArticleLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_browse);
        ButterKnife.bind(this);

        tvArticle.setText(articleContent, TextView.BufferType.SPANNABLE);
        tvArticle.setWordClickListener(new WordClickSpan.WordClickListener() {
            @Override
            public void onWordClick(String word, int selectionStart, int selectionEnd) {
                toWordSummaryAcitivity(word, selectionStart, selectionEnd);
            }

            private void toWordSummaryAcitivity(String word, int selectionStart, int selectionEnd) {
                Intent intent = new Intent(ArticleBrowseActivity.this, WordSummaryActivity.class);
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(ArticleBrowseActivity.this, cvArticleLayout, "cv_article");
                intent.putExtra(WordSummaryActivity.INTENT_KEY_ARTICLE, articleContent);
                intent.putExtra(WordSummaryActivity.INTENT_KEY_SELECTION_START, selectionStart);
                intent.putExtra(WordSummaryActivity.INTENT_KEY_SELECTION_END, selectionEnd);
                intent.putExtra(WordSummaryActivity.INTENT_KEY_SELECTION_WORD, word);
                startActivity(intent, options.toBundle());
            }

        });
    }


}
