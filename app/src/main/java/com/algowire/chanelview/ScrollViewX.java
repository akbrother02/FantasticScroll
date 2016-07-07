package com.algowire.chanelview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class ScrollViewX extends ScrollView {

    private OnScrollViewListener mOnScrollViewListener;

    public ScrollViewX(Context context) {
        super(context);
    }

    public ScrollViewX(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollViewX(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public interface OnScrollViewListener{
        void onScrollChanged(ScrollViewX view, int l, int t, int oldl, int oldt );
    }

    public void setOnScrollViewListener(OnScrollViewListener l) {
        this.mOnScrollViewListener = l;
    }

    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        mOnScrollViewListener.onScrollChanged( this, l, t, oldl, oldt );
        super.onScrollChanged(l, t, oldl, oldt);
    }
}