package com.example.classroom.pojo;

import org.apache.ibatis.type.Alias;

import java.util.List;

@Alias("work")
public class Work {
    private int work_id;
    private int course_id;
    private String work_descr;
    private List<W_I> image_list;
    private String[] images;
    private String create_time;

    public List<W_I> getImage_list() {
        return image_list;
    }

    public void setImage_list(List<W_I> image_list) {
        this.image_list = image_list;
    }

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }

    public int getWork_id() {
        return work_id;
    }

    public void setWork_id(int work_id) {
        this.work_id = work_id;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public String getWork_descr() {
        return work_descr;
    }

    public void setWork_descr(String work_descr) {
        this.work_descr = work_descr;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
