package com.example.smitlimbani.noticeme5253;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class NoticeViewHolder extends RecyclerView.ViewHolder {

    private TextView mNoticeTitle;
    private TextView mNoticeContent;
    private TextView mExpiryTime;
    private TextView mNoticeGroup;
    private ImageView mNoticeImage;

    public NoticeViewHolder(View itemView) {
        super(itemView);
        mNoticeTitle = (TextView) itemView.findViewById(R.id.MAnoticeTitle);
        mNoticeContent = (TextView) itemView.findViewById(R.id.MAgroupName);
        mExpiryTime = (TextView) itemView.findViewById(R.id.MAexpiryTime);
        mNoticeGroup = (TextView) itemView.findViewById(R.id.MAnoticeContent);
        mNoticeImage = (ImageView) itemView.findViewById(R.id.MAnoticeImage);
    }
}
