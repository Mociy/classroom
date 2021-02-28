package com.example.classroom.controller;

import com.example.classroom.pojo.Video;
import com.example.classroom.service.MultiMediaService;
import com.example.classroom.untils.ConverVideoUtils;
import com.example.classroom.untils.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class UploadController {

    @Autowired
    MultiMediaService multiMediaService;

    @Autowired
    Video video;


    @RequestMapping(value = "uploadFile", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> savaVideo(@RequestParam("fileName") MultipartFile file)
            throws IllegalStateException {
        Map<String,String> resultMap = new HashMap<>();
        try{
            //获取文件后缀
            String fileExt = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1)
                    .toLowerCase();
            // 重构文件名称
            String pikId = UUID.randomUUID().toString().replaceAll("-", "");
            String newVidoeName = pikId + "." + fileExt;
            String savePaths= Server.SAVE_VIDEO;
            String webPaths=Server.SERVER_URL+"videos/"+newVidoeName;
            //保存视频
            File fileSave = new File(savePaths, newVidoeName);
            file.transferTo(fileSave);
            resultMap.put("resCode","1");
            resultMap.put("webShowPath",webPaths );
            resultMap.put("video_id",pikId);
            String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date()).toString();
            video.setAuthor("javer");
            video.setCategory("数学");
            video.setImage_src("https://www.lotcloudy.com/scetc-show-videos-mini-api-0.0.1-SNAPSHOT//images/5106ff32-434d-490a-9a01-27c320a51955.JPG");
            video.setVideo_id(pikId);
            video.setVideo_src(webPaths);
            video.setTitle("二十四天SpringBoot从入门到精通");
            video.setCreate_time(date);
            multiMediaService.addVideo(video);
            return  resultMap;
        }catch (Exception e){
            e.printStackTrace();
            resultMap.put("resCode","0");
            return  resultMap ;

        }
    }
    @RequestMapping("/upload")
    @ResponseBody
    public Map<String,String> savaImage(@RequestParam("fileName") MultipartFile file)
            throws IllegalStateException {
        Map<String, String> resultMap = new HashMap<>();
        ConverVideoUtils converVideoUtils = new ConverVideoUtils();
        try {
            //获取文件后缀
            String fileExt = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1)
                    .toLowerCase();
            // 重构文件名称
            String pikId = UUID.randomUUID().toString().replaceAll("-", "");
            String newVidoeName = pikId + "." + fileExt;
            String savePaths = Server.SAVE_VIDEO;
            String webPaths = Server.SERVER_URL + "videos/" + newVidoeName;
            //保存视频
            File fileSave = new File(savePaths, newVidoeName);
            file.transferTo(fileSave);
            if(fileExt.equals("jpg")||fileExt.equals("png"))//上传图片
                   resultMap.put("resCode", "1");
               else if(fileExt.equals("mp4")){//处理mp4类型视频
                resultMap.put("resCode", "2");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        converVideoUtils.processVideoFormat(pikId,".mp4",fileExt);
                        String vido_src=Server.SERVER_URL+"video/"+pikId+".mp4";
                        multiMediaService.updateVideo_src(pikId,vido_src);
                    }
                }).start();
            }
                else {//处理其他视频
                resultMap.put("resCode", "2");
                   new Thread(()-> {
                        converVideoUtils.processVideoFormat(pikId,".mp4",fileExt);
                        String vido_src=Server.SERVER_URL+"video/"+pikId+".mp4";
                        multiMediaService.updateVideo_src(pikId,vido_src);
                }).start();
            }
            resultMap.put("webShowPath", webPaths);
            String date = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()).toString();
            resultMap.put("video_id",pikId);
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("resCode", "0");
            return resultMap;

        }
    }
    @RequestMapping("/addVideo")
    @ResponseBody
    public Map addVideo(@RequestBody Video video){
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
        HashMap resultMap=new HashMap();
        video.setCreate_time(date);
        String zm="https://www.cupidwall.cn/classroom/video/zm.png";
        video.setVideo_src(zm);
        multiMediaService.addVideo(video);
        resultMap.put("resCode", "0");
        return resultMap;
    }

       @RequestMapping("/file")
        public String file(){
        return "file";
        }


        @RequestMapping("/translate")
        @ResponseBody
        public void trans(){
            String path="/usr/videos/3bae31deb6304834b2786e05c975e383.mp4";
            ConverVideoUtils zout = new ConverVideoUtils();
            String targetExtension = ".mp4";
            boolean isDelSourseFile = false;

        }

}
