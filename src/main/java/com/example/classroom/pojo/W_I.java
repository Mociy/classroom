package com.example.classroom.pojo;

import org.apache.ibatis.type.Alias;

@Alias("w_i")
public class W_I {
    private int work_id;
    private String image_src;

    public int getWork_id() {
        return work_id;
    }

    public void setWork_id(int work_id) {
        this.work_id = work_id;
    }

    public String getImage_src() {
        return image_src;
    }

    public void setImage_src(String image_src) {
        this.image_src = image_src;
    }
}
