package com.example.classroom.dao;

import com.example.classroom.pojo.Comment;
import com.example.classroom.pojo.Video;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MultiMediaDao {

    List<Video> getVideosByCategory(String category);


    Video getVideoById(String video_id);

    List<Comment> getCommentsById(String video_id);

    List<Video> getVideosByAuthor(String author);

    List<Video> getAll(int page);



    void addVideo(Video video);

    void addComment(Comment comment);//添加在comments表

    void addCollection(String video_id);

    void removeCollection(String video_id);

    void addComments(String video_id);//视频评论量

    void addPlay(String video_id);

    void removeComments(String video_id);
    void updateVideo_src(Video video);



}
