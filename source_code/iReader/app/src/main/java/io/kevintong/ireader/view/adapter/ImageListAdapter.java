package io.kevintong.ireader.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import io.kevintong.ireader.R;

/**
 * Created by kevin on 2017/9/29.
 */
public class ImageListAdapter extends RecyclerView.Adapter {
    private final Context mContext;
    private String[] mImageUrls;

    public ImageListAdapter(String[] imageUrls,Context context) {
        this.mImageUrls = imageUrls;
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_image_card, parent, false);
        ImageViewHolder vh = new ImageViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

          Picasso.with(mContext)
                  .load(mImageUrls[position])
                .placeholder(R.drawable.bg_default)
                .error(R.drawable.bg_default)
                .into(((ImageViewHolder)holder).ivImage);
    }

    @Override
    public int getItemCount() {
        return mImageUrls.length;
    }


    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivImage;
        public ImageViewHolder(View v) {
            super(v);
            ivImage = v.findViewById(R.id.iv_image);
        }
    }
}
