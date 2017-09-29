package io.kevintong.ireader.view.activity;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.kevintong.ireader.R;
import io.kevintong.ireader.utils.DisplayUtil;
import io.kevintong.ireader.view.adapter.ImageListAdapter;

/**
 * 图片列表页
 */
public class ImageListActivity extends AppCompatActivity {

    @Bind(R.id.rv_imageList)
    RecyclerView rvImageList;

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_list);
        ButterKnife.bind(this);

        setupImageList();
    }

    private void setupImageList() {
        rvImageList.setHasFixedSize(true);
        rvImageList.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);

                outRect.bottom = DisplayUtil.dip2px(ImageListActivity.this,10);
            }
        });
        mLayoutManager = new LinearLayoutManager(this);
        rvImageList.setLayoutManager(mLayoutManager);

        mAdapter = new ImageListAdapter(getResources().getStringArray(R.array.imageUrls),this);
        rvImageList.setAdapter(mAdapter);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        toSplashActivity();
        return false;
    }

    /**
     * 跳转至 文章页
     */
    public void toSplashActivity() {
        Intent intent = new Intent(ImageListActivity.this, SplashActivity.class);
        startActivity(intent);
        finish();
    }
}
