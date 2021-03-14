package com.example.tmaptest;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BalloonOverlayView extends FrameLayout {

    private LinearLayout layout;
    private TextView title;
    private TextView subTitle;

    private TextView txtContents1;
    private TextView txtContents2;
    private TextView txtContents3;

    public BalloonOverlayView(Context context, String labelName, String id) {

        super(context);

        setPadding(10, 0, 10, 0);
        layout = new LinearLayout(context);
        layout.setVisibility(VISIBLE);

        setupView(context, layout, labelName, id);

        LayoutParams params = new LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.NO_GRAVITY;
        addView(layout, params);
    }

    public BalloonOverlayView(Context context, String contents1, String contents2, String contents3) {

        super(context);

        setPadding(10, 0, 10, 0);
        layout = new LinearLayout(context);
        layout.setVisibility(VISIBLE);

        setupView(context, layout, contents1, contents2, contents3);

        LayoutParams params = new LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.NO_GRAVITY;
        addView(layout, params);
    }


    protected void setupView(Context context, final ViewGroup parent, String labelName, String id) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        View view = inflater.inflate(R.layout.bubble_popup, parent, true);

        title = (TextView) view.findViewById(R.id.bubble_title);
        subTitle = (TextView) view.findViewById(R.id.bubble_subtitle);

        setTitle(labelName);
        setSubTitle(id);

    }

    protected void setupView(Context context, final ViewGroup parent, String contents1, String contents2, String contents3) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        View view = inflater.inflate(R.layout.bubble_popup, parent, true);

        txtContents1 = (TextView) view.findViewById(R.id.bubble_contents1);
        txtContents2 = (TextView) view.findViewById(R.id.bubble_contents2);
        txtContents3 = (TextView) view.findViewById(R.id.bubble_contents3);

        setTxtContents1(contents1);
        setTxtContents1(contents2);
        setTxtContents1(contents3);
    }

    public void setTitle(String str) {
        title.setText(str);
    }

    public void setSubTitle(String str) {
        subTitle.setText(str);
    }

    public void setTxtContents1(String strContents) {
        txtContents1.setText(strContents);
    }

    public void setTxtContents2(String strContents) {
        txtContents2.setText(strContents);
    }

    public void setTxtContents3(String strContents) {
        txtContents3.setText(strContents);
    }


}
