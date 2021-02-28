package com.example.classroom.controller;

import com.example.classroom.pojo.Author;
import com.example.classroom.pojo.Complaint;
import com.example.classroom.pojo.Credentials;
import com.example.classroom.pojo.Reason;
import com.example.classroom.service.AdminService;
import com.example.classroom.untils.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    MD5 md5;
    @Autowired
    AdminService adminService;

    @RequestMapping("/")
    public String loginPage(){
        return "admin";
    }

    @RequestMapping("/user")
    public String userPage(){
        return "admin_user";
    }

    @RequestMapping("/login")
    @ResponseBody
    public Map login(String name, String password, HttpServletRequest request, HttpServletResponse response){

        HashMap<String,String> resultMap=new HashMap();
        password=md5.getMD5(password);
        if(password.equals(adminService.getPassword(name))){
            HttpSession seesion=request.getSession();//获取session
            seesion.setAttribute("admin",name);
            seesion.setMaxInactiveInterval(-1);
            Cookie[] cookies=request.getCookies();
            if(cookies!=null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("JSESSIONID")) {//找到JSESSIONID
                        cookie.setMaxAge(0);
                        Cookie cookie1=new Cookie("JSESSIONID",seesion.getId());//添加cookie
                        cookie1.setMaxAge(60*60*72);//重新设置cookie中的JSESSIONID的时间
                        response.addCookie(cookie1);
                        System.out.println(cookie1.getMaxAge()+"cookie时间 ");
                    }
                }
            }
            resultMap.put("status","ok");
            return resultMap;
        }else {
            resultMap.put("status","error");

        }
        return resultMap;
    }
    @RequestMapping("/isLogin")
    @ResponseBody
    public Map isLogin(String name,HttpServletRequest request){//验证登录状态
        Map<String,String> resultMap=new HashMap<>();
        String user= (String)request.getSession().getAttribute("admin");
        System.out.println("用户名"+user);
        if(user!=null){
            resultMap.put("status","1");
            resultMap.put("user",user);
        }else {
            resultMap.put("status","0");
        }

        return resultMap;
    }

    @RequestMapping("/getAll")
    @ResponseBody
    public List<Credentials> getAll(int page){
        return adminService.getAll(page);
    }

    @RequestMapping("/pass")
    @ResponseBody
    public Map pass(String author_id){
        Map<String,String> resultMap=new HashMap<>();
        adminService.updateFlag(author_id);
        resultMap.put("status","1");
        return resultMap;
    }
    @RequestMapping("/no_pass")
    @ResponseBody
    public Map no_pass(@RequestBody Reason reason){
        Map<String,String> resultMap=new HashMap<>();
        adminService.no_pass(reason.getAuthor_id());
        adminService.addReason(reason);
        resultMap.put("status","1");
        return resultMap;
    }
    @RequestMapping("/getComplaints")
    @ResponseBody
    public List<Complaint> getComplaint(){
        return adminService.getComplaint();
    }

    @RequestMapping("/getAuthor")
    @ResponseBody
    public Author getAuthor(String name){
        return adminService.getAuthor(name);
    }
}
