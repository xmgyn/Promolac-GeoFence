package com.example.PromoLac.Adapters;

public class Subscribe {
    String topicname_,topicid_,topicimage_,toipcdiscription_;

    public Subscribe() { }

    public Subscribe(String topicname_, String topicid_, String topicimage_, String toipcdiscription_) {
        this.topicname_ = topicname_;
        this.topicid_ = topicid_;
        this.topicimage_ = topicimage_;
        this.toipcdiscription_ = toipcdiscription_;
    }

    public String getToipcdiscription_() {
        return toipcdiscription_;
    }

    public void setToipcdiscription_(String toipcdiscription_) {
        this.toipcdiscription_ = toipcdiscription_;
    }

    public String getTopicname_() {
        return topicname_;
    }

    public void setTopicname_(String topicname_) {
        this.topicname_ = topicname_;
    }

    public String getTopicid_() {
        return topicid_;
    }

    public void setTopicid_(String topicid_) {
        this.topicid_ = topicid_;
    }

    public String getTopicimage_() {
        return topicimage_;
    }

    public void setTopicimage_(String topicimage_) {
        this.topicimage_ = topicimage_;
    }
}
