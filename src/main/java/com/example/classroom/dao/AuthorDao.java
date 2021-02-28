package com.example.classroom.dao;

import com.example.classroom.pojo.Author;
import com.example.classroom.pojo.Credentials;
import com.example.classroom.pojo.Video;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorDao {

    void addAuthor(Author author);

    String isExists(String name);

    String getId(String name);

    void check(Credentials credentials);

    void deleteReason(String author_id);

    void delete_this(String video_id);

    Integer isChecking(String author_id);

    String getReason(String author_id);

    List<Video>getVideosByAuthor(String author);

    List<Video> getVideosByAuthor(@Param("author")String author, @Param("page")int page);


}
