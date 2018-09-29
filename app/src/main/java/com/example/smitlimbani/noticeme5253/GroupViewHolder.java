package com.example.smitlimbani.noticeme5253;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;


class GroupViewHolder extends RecyclerView.ViewHolder{

    public View mView;
    public TextView mGroupName;
    public TextView mOrgName;
    public Button mGroupsBtn;
    public Button mMembersBtn;
    public Button mJoinRequest;

    public GroupViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mGroupName=(TextView)mView.findViewById(R.id.groupName);
        mOrgName=(TextView)mView.findViewById(R.id.orgName);
        mGroupsBtn=(Button)mView.findViewById(R.id.groupsBtn);
        mMembersBtn=(Button)mView.findViewById(R.id.membersBtn);
        mJoinRequest = (Button)mView.findViewById(R.id.joinRequests);
    }
}
