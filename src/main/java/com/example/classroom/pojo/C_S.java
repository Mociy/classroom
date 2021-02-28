package com.example.classroom.pojo;

import org.apache.ibatis.type.Alias;
//学生——课程
@Alias("c_s")
public class C_S {
    private String student_id;
    private int course_id;

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }
}
