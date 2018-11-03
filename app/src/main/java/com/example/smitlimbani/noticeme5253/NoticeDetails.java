package com.example.smitlimbani.noticeme5253;

public class NoticeDetails
{
    private String notice_sender;

    private String notice_title;

    private String notice_content;

    private String notice_expiry;

    public NoticeDetails() {
    }

    public NoticeDetails(String notice_sender, String notice_title, String notice_content, String notice_expiry) {

        this.notice_sender = notice_sender;
        this.notice_title = notice_title;
        this.notice_content = notice_content;
        this.notice_expiry = notice_expiry;
    }

    public String getNotice_sender() {
        return notice_sender;
    }

    public void setNotice_sender(String notice_sender) {
        this.notice_sender = notice_sender;
    }

    public String getNotice_title() {
        return notice_title;
    }

    public void setNotice_title(String notice_title) {
        this.notice_title = notice_title;
    }

    public String getNotice_content() {
        return notice_content;
    }

    public void setNotice_content(String notice_content) {
        this.notice_content = notice_content;
    }

    public String getNotice_expiry() {
        return notice_expiry;
    }

    public void setNotice_expiry(String notice_expiry) {
        this.notice_expiry = notice_expiry;
    }
}
