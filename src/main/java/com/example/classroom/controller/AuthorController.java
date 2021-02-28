package com.example.classroom.controller;

import com.example.classroom.pojo.Author;
import com.example.classroom.pojo.Credentials;
import com.example.classroom.pojo.Video;
import com.example.classroom.service.AuthorService;
import com.example.classroom.untils.MD5;
import com.example.classroom.untils.SendMail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
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
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/author")
public class AuthorController {

    @Autowired
    private SendMail sendMail;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    MD5 md5;
    @Autowired
    AuthorService authorService;
    private ConcurrentHashMap<String, Author> ids=new ConcurrentHashMap<>();
    @RequestMapping("/login")
    @ResponseBody
    public Map login(String name, String password, HttpServletRequest request, HttpServletResponse response){//登录

        HashMap<String,String> resultMap=new HashMap();
        password=md5.getMD5(password);

        if(password.equals(authorService.isExists(name))){
            HttpSession seesion=request.getSession();//获取session
            seesion.setAttribute("user",name);
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
        String user= (String)request.getSession().getAttribute("user");
        System.out.println("用户名"+user);
        if(user!=null){
            resultMap.put("status","1");
            resultMap.put("user",user);
        }else {
           resultMap.put("status","0");
        }

        return resultMap;
    }
    @RequestMapping("/register")
    @ResponseBody
    public Map register(@RequestBody Author author){//注册
        HashMap<String,String> map=new HashMap();
        String uuid = UUID.randomUUID().toString();   //转化为String对象
        uuid = uuid.replace("-", ""); //因为UUID本身为32位只是生成时多了“-”，所以将它们去点就可
        redisTemplate.opsForValue().set(uuid,1);
        System.out.println(uuid);
        redisTemplate.expire(uuid,1800, TimeUnit.SECONDS);//设置过期时间
        ids.put(uuid,author);
        author.setPassword(md5.getMD5(author.getPassword()));
        if(authorService.isExists(author.getName())==null) {

            System.out.println(author.getMailbox());
            if (sendMail.sendLink(author.getMailbox(),uuid))

                map.put("status","ok");
            else map.put("status","邮箱错误！");
        }else {
            map.put("status","repeat");
        }

        return  map;

    }
    @RequestMapping("/s_s/{id}")//激活链接
    @ResponseBody
    public String getLink( @PathVariable("id") String id){
        if(redisTemplate.hasKey(id)) {
            authorService.addAuthor(ids.get(id));
            ids.remove(id);
            redisTemplate.delete(id);
            return "激活成功";
        }
        else
            return "已过期";
    }
    @RequestMapping("/check")
    @ResponseBody
    public Map check(@RequestBody Credentials credentials){
        authorService.check(credentials);
        Map<String,String> resultMap=new HashMap<>();
        resultMap.put("status","ok");
        return resultMap;
    }

    @RequestMapping("/getReason")
    @ResponseBody
    public Map getReason(String author){
        String author_id=authorService.getId(author);
        Map<String,String> resultMap=new HashMap<>();
        resultMap.put("status","ok");
        resultMap.put("reason", authorService.getReason(author_id));
        return resultMap;
    }

    @RequestMapping("/isChecking")
    @ResponseBody
    public Map isChecking(String author){
        Map<String,Integer> resultMap=new HashMap<>();
        if(authorService.isChecking(author)!=null)
            resultMap.put("status",authorService.isChecking(author));
        else
            resultMap.put("status",-1);
        return resultMap;
    }
    @RequestMapping("/delete_this")
    @ResponseBody
    public Map delete_this(String video_id){
        authorService.delete_this(video_id);
        Map<String,String> resultMap=new HashMap<>();
        resultMap.put("status","ok");
        return resultMap;
    }
    @RequestMapping("/getByAuthor")
    @ResponseBody
    public List<Video> getVideosByAuthor(String author){
        return authorService.getVideosByAuthor(author);
    }
    @RequestMapping("/user")
    public String toUser(){
        return "user";
    }
}
