package com.example.smitlimbani.noticeme5253;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class OrgViewHolder extends RecyclerView.ViewHolder{

    public View mView;
    public TextView mOrgType;
    public TextView mOrgName;
    public RelativeLayout mOrgCard;


    public OrgViewHolder(View itemView) {
        super(itemView);
        mView = itemView;

        mOrgName=(TextView)mView.findViewById(R.id.displayOrgNameMC);
        mOrgType=(TextView)mView.findViewById(R.id.orgTypeMC);
        mOrgCard = (RelativeLayout)mView.findViewById(R.id.orgCard);

    }
}
