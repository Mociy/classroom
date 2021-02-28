package com.example.classroom.dao;

import com.example.classroom.pojo.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassroomDao {

    List<Course> getCoursesByStudent_id(String student_id);

    List<Work> getWorksByCourse_id(int course_id);

    Work getWorkByWork_id(int work_id);

    List<W_I> getImages(int work_id);

    void addWork(Work work);

    void addImage(W_I w_i);

    void addStudent_image(W_S_I w_s_i);

    void addStudent_work(WorkInfo workInfo);
}
