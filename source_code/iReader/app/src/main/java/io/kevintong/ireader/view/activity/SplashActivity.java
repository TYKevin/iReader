package io.kevintong.ireader.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.kevintong.ireader.R;

/**
 * 进入页面
 */
public class SplashActivity extends AppCompatActivity {
    private static final long ENTER_DELAY_MILLIS = 3000;

    @Bind(R.id.iv_splash)
    ImageView ivSplash;

    Handler handler = new Handler();

    Runnable enterRunnable = new Runnable() {
        @Override
        public void run() {
            toArticleBrowseActivity();
        }
    };
    @Bind(R.id.fab_more_image)
    FloatingActionButton fabMoreImage;
    @Bind(R.id.rl_info)
    LinearLayout rlInfo;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        setupSplashImage();
        //延迟3秒进入
        handler.postDelayed(enterRunnable, ENTER_DELAY_MILLIS);
    }

    /**
     * 设置 随机播放图片
     */
    private void setupSplashImage() {
        String[] imageUrls = getResources().getStringArray(R.array.imageUrls);
        String imageUrl = imageUrls[(int) Math.floor(Math.random() * (imageUrls.length - 1))];
        Picasso.with(this)
                .load(imageUrl)
                .placeholder(R.drawable.bg_default)
                .error(R.drawable.bg_default)
                .into(ivSplash);
    }

    /**
     * 跳转至 文章页
     */
    public void toArticleBrowseActivity() {
        Intent intent = new Intent(SplashActivity.this, ArticleBrowseActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * 去图片列表页
     */
    private void toImageListActivity() {
        handler.removeCallbacks(enterRunnable);

        //进入图片列表页
        Intent intent = new Intent(SplashActivity.this, ImageListActivity.class);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.iv_splash)
    public void onClickImage() {
        toImageListActivity();
    }


    @OnClick(R.id.fab_more_image)
    public void onClickFab() {
        toImageListActivity();
    }
}
