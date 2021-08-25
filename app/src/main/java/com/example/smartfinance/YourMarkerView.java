package com.example.smartfinance;

import android.content.Context;
import android.widget.TextView;

import com.example.smartfinance.R;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

public class YourMarkerView extends MarkerView {

    private TextView tvContent,tvContentLabel;

    public void MyMarkerView(Context context, int layoutResource) {
//        super(context, layoutResource);

        // find your layout components
        tvContent = (TextView) findViewById(R.id.tvContent);
        tvContentLabel = (TextView) findViewById(R.id.tvContentLabel);
    }

    /**
     * Constructor. Sets up the MarkerView with a custom layout resource.
     *
     * @param context
     * @param layoutResource the layout resource to use for the MarkerView
     */
    public YourMarkerView(Context context, int layoutResource) {
        super(context, layoutResource);
    }

    // callbacks everytime the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    @Override
    public void refreshContent(Entry e, Highlight highlight) {

        tvContent.setText("" + e.getY());
        tvContentLabel.setText("" + e.getY());
        // this will perform necessary layouting
        super.refreshContent(e, highlight);
    }

    private MPPointF mOffset;

    @Override
    public MPPointF getOffset() {

        if(mOffset == null) {
            // center the marker horizontally and vertically
            mOffset = new MPPointF(-(getWidth() / 2), -getHeight());
        }

        return mOffset;
    }
}