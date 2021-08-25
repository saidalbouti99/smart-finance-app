package com.example.smartfinance;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

import java.text.DecimalFormat;

public class MyMarkerView extends MarkerView {

    private TextView tvContent,tvContentLabel;

    public MyMarkerView(Context context, int layoutResource) {
        super(context, layoutResource);
        // this markerview only displays a textview
        tvContent = (TextView) findViewById(R.id.tvContent);
        tvContentLabel = (TextView) findViewById(R.id.tvContentLabel);
    }

// callbacks everytime the MarkerView is redrawn, can be used to update the
// content (user-interface)

    @Override
    public void refreshContent(Entry e, Highlight highlight)
    {
        // here you can change whatever you want to show in following line as x/y or bothc z
        PieEntry pe = (PieEntry) e;
        tvContent.setText(" " + e.getY()); // set the entry-value as the display text
        tvContentLabel.setText(pe.getLabel());
    }
//    private MPPointF mOffset;
//    @Override
//    public MPPointF getOffsetForDrawingAtPoint(float posX, float posY) {
//        if(mOffset == null) {
//            // center the marker horizontally and fixed Y position at the top
//
//            mOffset = new MPPointF(-(getWidth() / 2f), -posY);
//
//        }
//
//        return mOffset;
//    }

    private MPPointF mOffset;

    @Override
    public MPPointF getOffset() {

        if(mOffset == null) {
            // center the marker horizontally and vertically
            mOffset = new MPPointF(-(getWidth() / 2), -getHeight());
        }

        return mOffset;
    }}
