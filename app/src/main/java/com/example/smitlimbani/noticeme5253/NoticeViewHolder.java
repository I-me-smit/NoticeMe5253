package com.example.smitlimbani.noticeme5253;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class NoticeViewHolder extends RecyclerView.ViewHolder {

    public View mView;
    public TextView mNoticeTitle;
    public TextView mNoticeContent;
    public TextView mExpiryTime;
    public TextView mNoticeGroup;
    public ImageView mNoticeImage;

    public NoticeViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mNoticeTitle = (TextView) mView.findViewById(R.id.MAnoticeTitle);
        mNoticeContent = (TextView) mView.findViewById(R.id.MAnoticeContent);
        mExpiryTime = (TextView) mView.findViewById(R.id.MAexpiryTime);
//        mNoticeGroup = (TextView) mView.findViewById(R.id.MAgroupName);
        mNoticeImage = (ImageView) mView.findViewById(R.id.MAnoticeImage);
    }
}
