package com.example.classroom.dao;

import com.example.classroom.pojo.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao {

    void addStudent(Student student);

    void updateName(Student student);

    void addBrowsed(Browsed browsed);

    void addCollection(MyCollection myCollection);

    void removeCollection(MyCollection myCollection);

    void addComplaint(Complaint complaint);


    Student getStudent(String student_id);

    List<Browsed> getBrowsed(String student_id);

    List<Browsed> queryBrowsed(Browsed browsed);

    List<MyCollection> queryCollection(MyCollection myCollection);

    List<MyCollection> getCollections(String student_id);

    List<Video> getByTitle(String title);



}
