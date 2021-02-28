package com.example.classroom.controller;

import com.example.classroom.pojo.Comment;
import com.example.classroom.pojo.Video;
import com.example.classroom.service.MultiMediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class MultiMediaController {

    @Autowired
    MultiMediaService multiMediaService;


    @RequestMapping("/getByCategory")
    @ResponseBody
    public List<Video> getVideosByCategory(String category){
        System.out.println(category);

        return multiMediaService.getVideosByCategory(category);
    }


    @RequestMapping("/getById")
    @ResponseBody
    public Video getVideoById(String video_id){
        System.out.println(video_id);
        return multiMediaService.getVideoById(video_id);
    }

    @RequestMapping("/getByAuthor")
    @ResponseBody
    public List<Video> getByAuthor(String author){
      return multiMediaService.getVideosByAuthor(author);
    }

    @RequestMapping("/addComment")
    @ResponseBody
    public String addComment(@RequestBody Comment comment){
        System.out.println(comment.getAvatarUrl()+"---------"+comment.getText()+"---------"+comment.getNickName());
        multiMediaService.addComment(comment);
        return "ok";
    }
    @RequestMapping("/getComments")
    @ResponseBody
    public List<Comment> getCommentsById(String video_id){
        return multiMediaService.getCommentsById(video_id);
    }


}
