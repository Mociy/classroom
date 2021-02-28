package com.example.classroom.pojo;

import org.apache.ibatis.type.Alias;

import java.util.List;

@Alias("workInfo")
public class WorkInfo {

    private int work_id;
    private String student_id;
    private String nickName;
    private List<W_S_I> image_list;
    private String[] images;
    private String work_descr;
    private String create_time;

    public String getWork_descr() {
        return work_descr;
    }

    public void setWork_descr(String work_descr) {
        this.work_descr = work_descr;
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

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public List<W_S_I> getImage_list() {
        return image_list;
    }

    public void setImage_list(List<W_S_I> image_list) {
        this.image_list = image_list;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
}
