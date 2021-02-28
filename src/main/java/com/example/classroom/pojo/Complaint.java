package com.example.classroom.pojo;

import org.apache.ibatis.type.Alias;

@Alias("complaint")
public class Complaint {
    private int id;
    private String video_id;
    private String complaint_content;
    private String video_src;
    private String author;
    private String create_time;

    public String getComplaint_content() {
        return complaint_content;
    }

    public void setComplaint_content(String complaint_content) {
        this.complaint_content = complaint_content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVideo_id() {
        return video_id;
    }

    public void setVideo_id(String video_id) {
        this.video_id = video_id;
    }

    public String getVideo_src() {
        return video_src;
    }

    public void setVideo_src(String video_src) {
        this.video_src = video_src;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String creat_time) {
        this.create_time = creat_time;
    }
}
