package com.example.classroom.pojo;

import org.apache.ibatis.type.Alias;

@Alias("credentials")
public class Credentials {
    private String author_id;
    private String name;
    private String id_card;
    private String id_card_image;
    private String teacher_certificate_image;
    private String create_time;
    private int flag;
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(String author_id) {
        this.author_id = author_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId_card() {
        return id_card;
    }

    public void setId_card(String id_card) {
        this.id_card = id_card;
    }

    public String getId_card_image() {
        return id_card_image;
    }

    public void setId_card_image(String id_card_image) {
        this.id_card_image = id_card_image;
    }

    public String getTeacher_certificate_image() {
        return teacher_certificate_image;
    }

    public void setTeacher_certificate_image(String teacher_certificate_image) {
        this.teacher_certificate_image = teacher_certificate_image;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
}
