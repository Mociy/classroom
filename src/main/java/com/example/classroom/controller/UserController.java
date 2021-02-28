package com.example.classroom.controller;

import com.example.classroom.pojo.*;
import com.example.classroom.service.StudentService;
import com.example.classroom.untils.HttpUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {
    private final String appID = "wx887b1866d5b1b6a8";
    private final String appSecret = "cb0cf6708b0f493c2f7d7b67c237d4f4";


    private final String t_appID = "wx0e82fbd8b789bbdd";
    private final String t_appSecret = "43a42e576a72982c1b784b189177f83f";


    @Autowired
    StudentService studentService;
    //学生
    @PostMapping("/login")//获取openid
    @ResponseBody
    public OpenIdJson userLogin(@RequestParam("code") String code) throws IOException {
        String result = "";
        try{//请求微信服务器，用code换取openid。
            result = HttpUtil.doGet(
                    "https://api.weixin.qq.com/sns/jscode2session?appid="
                            + this.appID + "&secret="
                            + this.appSecret + "&js_code="
                            + code
                            + "&grant_type=authorization_code", null);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        ObjectMapper mapper = new ObjectMapper();
        OpenIdJson openIdJson = mapper.readValue(result, OpenIdJson.class);
        System.out.println(result.toString());
        System.out.println(openIdJson.getOpenid());
        return openIdJson;
    }
    //老师
    @PostMapping("/t_login")//获取openid
    @ResponseBody
    public OpenIdJson teacherLogin(@RequestParam("code") String code) throws IOException {
        String result = "";
        try{//请求微信服务器，用code换取openid。
            result = HttpUtil.doGet(
                    "https://api.weixin.qq.com/sns/jscode2session?appid="
                            + this.t_appID + "&secret="
                            + this.t_appSecret + "&js_code="
                            + code
                            + "&grant_type=authorization_code", null);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        ObjectMapper mapper = new ObjectMapper();
        OpenIdJson openIdJson = mapper.readValue(result, OpenIdJson.class);
        System.out.println(result.toString());
        System.out.println(openIdJson.getOpenid());
        return openIdJson;
    }


   @RequestMapping("/getStudent")
   @ResponseBody
   public Student getStudent(String student_id){
        return studentService.getStudent(student_id);
   }

   @RequestMapping("/addStudent")
   @ResponseBody
   public String addStudent(@RequestBody Student student){
        studentService.addStudent(student);
        return "ok";
   }
   @RequestMapping("/updateName")
   @ResponseBody
   public Student updateName(@RequestBody Student student){
           studentService.updateStudent(student);
           return studentService.getStudent(student.getStudent_id());
   }

   @RequestMapping("/getBrowsed")
   @ResponseBody
   public List<Browsed> getBrowsed(String student_id){
        return studentService.getBrowsed(student_id);
   }
    @RequestMapping("/addBrowsed")
    @ResponseBody
   public String addBrowsed(@RequestBody Browsed browsed){
        studentService.addBrowsed(browsed);
        return "ok";
   }
    @RequestMapping("/addCollection")
    @ResponseBody
   public String addCollection(@RequestBody MyCollection myCollection){
        studentService.addCollection(myCollection);
        return "ok";
   }
    @RequestMapping("/removeCollection")
    @ResponseBody
   public String removeCollection(@RequestBody MyCollection myCollection){
        studentService.removeCollection(myCollection);
        return "ok";
   }

   @RequestMapping("/getState")
   @ResponseBody
    public int getState(@RequestBody MyCollection myCollection){
        if(studentService.getState(myCollection))
            return 1;
        else
            return 0;
   }

   @RequestMapping("/getCollections")
   @ResponseBody
   public List<MyCollection> getCollections(String student_id){
        return studentService.getCollections(student_id);
   }

   @RequestMapping("/getByTitle")
   @ResponseBody
   public List<Video> getByTitle(String title){
        return studentService.getByTitle(title);
   }

   @RequestMapping("/complain")
   @ResponseBody
   public String complain(@RequestBody Complaint complaint){
        studentService.addComplaint(complaint);
        return "ok";
   }
}
