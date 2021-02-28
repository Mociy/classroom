package com.example.classroom.service;

import com.example.classroom.dao.MultiMediaDao;
import com.example.classroom.dao.UserDao;
import com.example.classroom.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class StudentService {
    @Autowired
    UserDao userDao;
    @Autowired
    MultiMediaDao multiMediaDao;

    public void addStudent(Student student){
        if(getStudent(student.getStudent_id())==null)
        userDao.addStudent(student);

    }

    public Student getStudent(String student_id){
        return userDao.getStudent(student_id);

    }

    public void updateStudent(Student student){
        userDao.updateName(student);
    }

    public void addBrowsed(Browsed browsed){
        if(userDao.queryBrowsed(browsed).isEmpty()) {
            userDao.addBrowsed(browsed);
            multiMediaDao.addPlay(browsed.getVideo_id());
        }
    }
    public List<Browsed> getBrowsed(String student_id){
      return   userDao.getBrowsed(student_id);
    }

    public void addCollection (MyCollection myCollection){
             if(userDao.queryCollection(myCollection).isEmpty()) {
                 userDao.addCollection(myCollection);
                 multiMediaDao.addCollection(myCollection.getVideo_id());

             }
    }
    public void removeCollection (MyCollection myCollection){
        userDao.removeCollection(myCollection);
        multiMediaDao.removeCollection(myCollection.getVideo_id());
    }

    public boolean getState(MyCollection myCollection){
        if(userDao.queryCollection(myCollection).isEmpty())
            return false;
        else
            return true;
    }

    public List<MyCollection> getCollections(String student_id){
        return userDao.getCollections(student_id);
    }

    public List<Video> getByTitle(String title){
        title="%"+title+"%";
        return  userDao.getByTitle(title);
    }

    public void addComplaint(Complaint complaint){
        Video video=multiMediaDao.getVideoById(complaint.getVideo_id());
        complaint.setAuthor(video.getAuthor());
        complaint.setVideo_src(video.getVideo_src());
        complaint.setCreate_time(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
        userDao.addComplaint(complaint);
    }
}
