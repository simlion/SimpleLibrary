package com.simlion.simplelibrary;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class RelativeLayoutPageStateView extends RelativeLayout {

    public RelativeLayoutPageStateView(Context context) {
        super(context);
    }

    public RelativeLayoutPageStateView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RelativeLayoutPageStateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void showEmptyView(Config config) {

        removeAllViews();

        View emptyView = LayoutInflater.from(getContext()).inflate(R.layout.layout_common_empty,null);

        emptyView.setTag("empty_view");

        if (null != config.layoutParams) {
            addView(emptyView,config.layoutParams);
        } else {
            addView(emptyView);
        }

        if (!TextUtils.isEmpty(config.content)) {
            TextView textView = (TextView) emptyView.findViewById(R.id.text_tip_empty);
            textView.setText(config.content);
        }

        if (config.imgResId != -1) {
            ImageView imageView = (ImageView) emptyView.findViewById(R.id.img_error_tip);
            imageView.setBackgroundResource(config.imgResId);
        }
    }

    public void hideEmptyView() {

        View emptyView = findViewWithTag("empty_view");

        if (null != emptyView) {
            removeView(emptyView);
        }

    }

    public void showNetworkErrorView(final Config config) {

        removeAllViews();

        View networkErrorView = LayoutInflater.from(getContext()).inflate(R.layout.layout_common_error,null);

        networkErrorView.setTag("network_error_view");

        if (null != config.layoutParams) {
            addView(networkErrorView,config.layoutParams);
        } else {
            addView(networkErrorView);
        }

        if (config.imgResId != -1) {
            ImageView imageView = (ImageView) networkErrorView.findViewById(R.id.img_error_tip);
            imageView.setBackgroundResource(config.imgResId);
        }

        if (config.backgroundResId != -1) {
            networkErrorView.setBackgroundResource(config.backgroundResId);
        }

        if (TextUtils.isEmpty(config.content)) {
            TextView textView = (TextView) networkErrorView.findViewById(R.id.text_error_tip);
            textView.setText(config.content);
        }
        if (null != config.retryClickListener) {
            Button btnRetry = (Button) networkErrorView.findViewById(R.id.btn_retry);
            btnRetry.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    config.retryClickListener.onClick(view);
                }
            });
        }
    }

    public void hideNetworkErrorView() {

        View networkErrorView = findViewWithTag("network_error_view");

        if (null != networkErrorView) {
            removeView(networkErrorView);
        }
    }

    public interface OnButtonClickListener {
        void onClick(View view);
    }

    public static class Config {

        public String content;

        public String retryText;

        public int imgResId = -1;

        public OnButtonClickListener retryClickListener = null;
        //
        public RelativeLayout.LayoutParams layoutParams;

        public int backgroundResId = -1;

        public static Builder create() {
            return new Builder();
        }

        public static class Builder {
            private Config config;

            private Builder() {
                config = new Config();
            }

            public Config build() {
                return config;
            }

            public Builder setContent(String content) {
                config.content = content;
                return this;
            }

            public Builder retryText(String _retryText) {
                config.retryText = _retryText;
                return this;
            }

            public Builder setButtonClickListener(OnButtonClickListener _listener) {
                config.retryClickListener = _listener;
                return this;
            }

            public Builder setLayoutParams(RelativeLayout.LayoutParams params) {
                config.layoutParams = params;
                return this;
            }

            public Builder setBackgroundResId(int resId) {
                config.backgroundResId = resId;
                return this;
            }

            public Builder setImgResId(int imgResId) {
                config.imgResId = imgResId;
                return this;
            }
        }
    }
}
