package com.example.classroom.pojo;

import org.apache.ibatis.type.Alias;

@Alias("reason")
public class Reason {
    private String author_id;
    private String reason;

    public String getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(String author_id) {
        this.author_id = author_id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
