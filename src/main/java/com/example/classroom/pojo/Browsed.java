package com.example.classroom.pojo;

import org.apache.ibatis.type.Alias;
import org.springframework.stereotype.Repository;

@Alias("browsed")
public class Browsed {
    private int browsed_id;
    private String student_id;
    private String video_id;
    private String image_src;
    private String title;

    public int getBrowsed_id() {
        return browsed_id;
    }

    public void setBrowsed_id(int browsed_id) {
        this.browsed_id = browsed_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getVideo_id() {
        return video_id;
    }

    public void setVideo_id(String video_id) {
        this.video_id = video_id;
    }

    public String getImage_src() {
        return image_src;
    }

    public void setImage_src(String image_src) {
        this.image_src = image_src;
    }
}
