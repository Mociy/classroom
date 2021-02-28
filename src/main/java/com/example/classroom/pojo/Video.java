package com.example.classroom.pojo;


import org.apache.ibatis.type.Alias;
import org.springframework.stereotype.Component;

import java.util.List;

@Alias("video")
@Component("video")
public class Video {
    private String video_id ;
    private String category;
    private List<Comment> comments;
    private String video_src;
    private String image_src;
    private String author;
    private String title;
    private int collections;
    private int v_comments;
    private int total_playback;
    private String create_time;

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public int getTotal_playback() {
        return total_playback;
    }

    public void setTotal_playback(int total_playback) {
        this.total_playback = total_playback;
    }

    public int getV_comments() {
        return v_comments;
    }

    public void setV_comments(int v_comments) {
        this.v_comments = v_comments;
    }

    public int getCollections() {
        return collections;
    }

    public void setCollections(int collections) {
        this.collections = collections;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
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

    public List<Comment> getComments() {
        return comments;
    }

    public String getVideo_src() {
        return video_src;
    }

    public String getAuthor() {
        return author;
    }


    public void setCategory(String category) {
        this.category = category;
    }

    public void setVideo_src(String video_src) {
        this.video_src = video_src;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }



    public void setAuthor(String author) {
        this.author = author;
    }
}
