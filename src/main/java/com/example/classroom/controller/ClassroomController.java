package com.example.classroom.controller;

import com.example.classroom.pojo.Course;
import com.example.classroom.pojo.Work;
import com.example.classroom.pojo.WorkInfo;
import com.example.classroom.service.ClassroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/class")
public class ClassroomController {

    @Autowired
    ClassroomService classroomService;

    @RequestMapping("/getCoursesByStudent_id")
    @ResponseBody
    public List<Course> getCoursesByStudent_id(String student_id){
        return classroomService.getCoursesByStudent_id(student_id);
    }
    @RequestMapping("/getWorksByCourse_id")
    @ResponseBody
    public List<Work> getWorksByCourse_id(int course_id){

        return classroomService.getWorksByCourse_id(course_id);
    }

    @RequestMapping("/getWork")
    @ResponseBody
    public Work getWorkByWork_id(int work_id){
        return classroomService.getWorkByWork_id(work_id);
    }

    @RequestMapping("/addWork")
    @ResponseBody
    public String addWork(@RequestBody Work work){
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
        work.setCreate_time(date);
        classroomService.addWork(work);
        return "ok";
    }

    @RequestMapping("/submit")
    @ResponseBody
    public int submitWork(@RequestBody WorkInfo workInfo){
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
        workInfo.setCreate_time(date);
        classroomService.addStudent_work(workInfo);
        return 1;
    }


}
