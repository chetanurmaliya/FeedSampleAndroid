package com.feedsample.android.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.feedsample.android.R;
import com.feedsample.android.entities.Feed;
import com.feedsample.android.utils.ImageLoader;

import java.util.List;

/**
 * Manages the feed list.
 *
 * @author chetan.
 */
public class FeedListAdapter extends RecyclerView.Adapter<FeedListAdapter.FeedViewHolder> {

    private List<Feed> feedList;

    public FeedListAdapter(List<Feed> feedList) {
        this.feedList = feedList;
    }

    @NonNull
    @Override
    public FeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_list_item, parent, false);
        return new FeedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedViewHolder holder, int position) {
        holder.updateView(feedList.get(position));
    }

    @Override
    public int getItemCount() {
        return feedList.size();
    }

    public class FeedViewHolder extends RecyclerView.ViewHolder {

        private TextView feedTitle;
        private ImageView feedImage;

        public FeedViewHolder(View itemView) {
            super(itemView);
            feedTitle = itemView.findViewById(R.id.feed_title);
            feedImage = itemView.findViewById(R.id.feed_image);
        }

        public void updateView(Feed feed) {
            if (feed != null) {
                if (!TextUtils.isEmpty(feed.getTitle())) {
                    feedTitle.setText(Html.fromHtml(feed.getTitle()));
                }

                if (!TextUtils.isEmpty(feed.getImageUrl())) {
                    feedImage.setVisibility(View.VISIBLE);
                    ImageLoader.load(feed.getImageUrl(), feedImage);
                } else {
                    feedImage.setVisibility(View.GONE);
                }
            }
        }
    }
}