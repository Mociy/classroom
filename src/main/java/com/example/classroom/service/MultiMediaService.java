package com.example.classroom.service;


import com.example.classroom.dao.MultiMediaDao;
import com.example.classroom.pojo.Comment;
import com.example.classroom.pojo.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Service
public class MultiMediaService {

    @Autowired
    MultiMediaDao multiMediaDao;

    public List<Video> getVideosByCategory(String category){//获取某一类视频

        return multiMediaDao.getVideosByCategory(category);
    }

    public void addVideo(Video video){//添加视频
        multiMediaDao.addVideo(video);
    }
    public Video getVideoById(String video_id){//通过Id获取视频
        Video video=multiMediaDao.getVideoById(video_id);
        video.setComments(multiMediaDao.getCommentsById(video_id));
       return video;
    }
    public void addComment(Comment comment){
        multiMediaDao.addComment(comment);
        multiMediaDao.addComments(comment.getVideo_id());
    }

    public void updateVideo_src(String video_id,String video_src){
        Video video=new Video();
        video.setVideo_src(video_src);
        video.setVideo_id(video_id);
        multiMediaDao.updateVideo_src(video);
    }
    public List<Comment> getCommentsById(String video_id){
        return multiMediaDao.getCommentsById(video_id);
    }

    public List<Video> getVideosByAuthor(String author){
        return multiMediaDao.getVideosByAuthor(author);
    }
}
