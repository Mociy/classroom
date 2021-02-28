package com.example.classroom.dao;

import com.example.classroom.pojo.Author;
import com.example.classroom.pojo.Complaint;
import com.example.classroom.pojo.Credentials;
import com.example.classroom.pojo.Reason;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminDao {
    String getPassword (String name);

    List<Credentials> getAll(int page);

    List<Complaint> getComplaint();

    Author getAuthor(String name);

    void updateFlag(String author_id);

    void no_pass(String author_id);

    void addReason(Reason reason);

    void deleteComplaint(String video_di);
}
