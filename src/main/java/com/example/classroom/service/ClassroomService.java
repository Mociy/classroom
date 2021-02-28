package com.example.classroom.service;

import com.example.classroom.dao.ClassroomDao;
import com.example.classroom.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassroomService {

    @Autowired
    ClassroomDao classroomDao;

    public List<Course> getCoursesByStudent_id(String student_id){
        return classroomDao.getCoursesByStudent_id(student_id);
    }

    public List<Work> getWorksByCourse_id(int course_id){

        return classroomDao.getWorksByCourse_id(course_id);
    }


    public List<W_I> getImages(int work_id){
        return classroomDao.getImages(work_id);
    }
    public Work getWorkByWork_id(int work_id){
          Work work=classroomDao.getWorkByWork_id(work_id);
          work.setImage_list(classroomDao.getImages(work_id));

        return work;
    }

    public void addWork(Work work){
        W_I w_i=new W_I();
        classroomDao.addWork(work);
        System.out.println(work.getWork_id()+"    work_id");
        String[] images=work.getImages();
        for(int i=0;i<images.length;i++){
            w_i.setWork_id(work.getWork_id());
            w_i.setImage_src(images[i]);
            classroomDao.addImage(w_i);
        }
    }

    public void addStudent_work(WorkInfo workInfo){
        W_S_I w_s_i=new W_S_I();
        w_s_i.setWork_id(workInfo.getWork_id());
        w_s_i.setStudent_id(workInfo.getStudent_id());
        String[] images=workInfo.getImages();
        for(int i=0;i<images.length;i++){
            w_s_i.setImage_src(images[i]);
            classroomDao.addStudent_image(w_s_i);
        }
        classroomDao.addStudent_work(workInfo);
    }
}
