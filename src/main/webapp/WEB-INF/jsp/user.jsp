<%--
  Created by IntelliJ IDEA.
  User: liao
  Date: 2019/5/24
  Time: 12:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>个人中心</title>
    <link rel="icon" type="image/x-icon" href="/images/favicon.ico"/>
    <link rel="stylesheet" type="text/css" href="https://unpkg.com/iview/dist/styles/iview.css">
    <link type="text/css" rel="styleSheet" href="/classroom/css/user.css">
</head>
<body>
<script src="/js/vue.min.js"></script>
<script src="https://cdn.staticfile.org/vue-resource/1.5.1/vue-resource.min.js"></script>
<script type="text/javascript" src="/js/iview.min.js"></script>
<div id="App">
    <div id="header">
        <a> <img src="/image/bg.jpg"></a>
    </div>
    <div id="center">
        <div id="left">
            <a class="menu" v-for="(menu,index) in menus" @click="go(index)" :class="{selected:flag===index}">
                {{menu}}
            </a>
        </div>
        <div id="right">
            <div v-if="flag==0">
                <div class="blog" style="display: flex;" v-for="video in videos">
                    <div style="width: 85%;">
                    <h2>{{video.title}}</h2>
                    <a style="font-size: 15px;" @click="gopage(video.video_src)">点击查看课程视频</a>
                    <p>
                        <span>收藏人数</span><span style="color: #18b566">{{video.collections}}</span>
                        <span style="margin-left: 10px;">观看次数</span><span style="color: #18b566">{{video.total_playback}}</span>
                    </p>
                    <p style="color: #A0522D;">{{video.create_time}}</p>

                        <Modal
                                v-model="modal1"
                                title="提示！！"
                                @on-ok="ok"
                                @on-cancel="cancel">
                            <h3 style="color: red;">你确定要删除此课程吗吗？</h3>
                        </Modal>
                    </div>
                    <div style="width:15%;">
                        <i-Button style="margin-top: 20px;" @click="toDelete(video.video_id)" type="error">删除</i-Button>
                    </div>
                </div>

            </div>
            <div v-if="flag==2&&isChecking_==6">
                <p v-if="reason!=null" style="color: #ed4014;font-size: 16px;margin-top: 20px;margin-left: 220px;">{{reason}}，请修改后上传认证</p>
                <div class="left_2" style="margin: 0 auto;width: 500px;padding: 50px;" >
                <p style="margin-top: 8px;padding-left: 36px;">姓名:  <i-input v-model="name" style="width: 300px;" type="text"/></p>
                <p style="margin-top: 8px;">身份证号码:  <i-input v-model="ID_card" style="width: 300px;" type="text"/></p>

                    <Upload style="margin-left:80px;"
                            :before-upload=" handleUpload_i">
                        <Button class="i_style" >选择身份证照片</Button>
                    </Upload>
                <div v-if="ID_image!==null">
                    <img :src="ID_image" style="width:210px;height:140px;margin-left: 60px;"></div>

                <Upload style="margin-left:80px;"
                        :before-upload=" handleUpload_t">
                    <Button class="i_style" >选择教师资格证照片</Button>
                </Upload>
                <div v-if="teacher_image!==null">
                    <img :src="teacher_image" style="width:210px;height:140px;margin-left: 60px;"></div>
                <i-Button type="primary" @click="check" style="margin-left: 115px" >确认上传</i-Button>
            </div>
            </div>
            <div v-if="flag==2&&isChecking_==0">
                  <p style="font-size: 20px;color: darkblue;margin-left: 100px;margin-top: 200px;">资料正在审核中，请耐心等待......</p>
            </div>
            <div v-if="flag==2&&isChecking_==1">
                <p style="font-size: 20px;color: darkblue;margin-left: 100px;margin-top: 200px;">您已完成教师资质认证！</p>
            </div>
        </div>
    </div>

</div>
<script>
    vm=new Vue({
        el:'#App',
        data:{
            menus:["我发布的课程>>","上传课程>>","教师认证>>"],
            author:"",
            videos:"",
            deleteid:"",
            modal1:false,
            num:0,
            page:0,
            empty:false,
            type:1,
            flag:0,
            name:"",
            ID_card:"",
            ID_image:null,
            teacher_image:null,
            reason:null,
            isChecking_:6,
            video_tmp:null,

        },
        methods:{
            go:function (index) {
                this.flag=index;
                if(index==1) {
                    this.flag=0;
                    if(this.isChecking_==1)
                    window.open("/classroom/file");
                    else
                        alert("请先完成教师资质认证！");
                }else if(index==2){
                    this.getReason();
                    this.isChecking();
                }else if(index==0){
                    this.getVideos();
                }

            },
            gopage(video_src){
              window.open(video_src);
            },
            getVideos(){
                this.$http.post('/classroom/author/getByAuthor',{author:this.author},{emulateJSON: true}).then(function (res) {
                    console.log(res.body)
                     this.videos=res.body;
                }, function (res) {
                    console.log(res.status);
                });
            },
            getStatus(){
              this.$http.get('/classroom/author/isLogin').then(function (res) {
                  if(res.body.status=="1") {
                      this.author = res.body.user;
                      this.getVideos();
                      this.getReason();
                      this.isChecking();
                      if(this.isChecking_==1)
                          this.flag=0;
                      console.log("okk")
                  }
                  else
                      window.location.href="/classroom/";
              })
            },
            check(){
                console.log(this.ID_card)
                this.$http.post('/classroom/author/check',{
                    name:this.name,
                    id_card:this.ID_card,
                    id_card_image:this.ID_image,
                    teacher_certificate_image:this.teacher_image,
                    userName:this.author},
                    {emulateJSON: false}).then(function (res) {
                    if(res.body.status=="ok"){
                        this.getReason();
                        this.isChecking();
                        console.log("ok")
                    }else {
                        alert("修改失败！")
                    }

                }, function (res) {
                    console.log(res.status);
                });
            },

            change:function () {
                this.$http.post('/classroom/change',{oldp:this.oldp,newp:this.newp},{emulateJSON: true}).then(function (res) {
                    if(res.body.status=="ok"){
                        this.modal2=true;
                        console.log("ok")
                    }else {
                        alert("修改失败！")
                    }

                }, function (res) {
                    console.log(res.status);
                });
            },
            handleUpload_i (file) {
               this.upload(file,1);

            },
            handleUpload_t (file) {
                this.upload(file,2);

            },
            upload(file,flag) {
                var that = this;

                var formData = new FormData();
                formData.append('fileName', file);
                // specify Content-Type, with formData as well
                that.$http.post('/classroom/upload', formData, {
                    headers: { 'Content-Type': 'multipart/form-data' }
                }).then(function (res) {

                    that.$Notice.open({
                        title: '文件上传提醒',
                        desc:'上传成功！'
                    });
                    console.log(res.body.webShowPath);
                   if(flag==1)
                       this.ID_image=res.body.webShowPath;
                   else
                       this.teacher_image=res.body.webShowPath;
                }, function (res) {
                    console.log(res.body);
                });
            },
            getReason(){
                this.$http.post('/classroom/author/getReason',{author:this.author},{emulateJSON: true}).then(function (res) {
                    console.log(res.body)
                    this.reason=res.body.reason;
                }, function (res) {
                    console.log(res.status);
                });
            },
           isChecking(){
                this.$http.post('/classroom/author/isChecking',{author:this.author},{emulateJSON: true}).then(function (res) {
                    console.log(res.body)
                   if(res.body.status!=-1)
                       this.isChecking_=res.body.status;
                }, function (res) {
                    console.log(res.status);
                });
            },
            toDelete(video_id){
                 this.modal1=true;
                 this.video_tmp=video_id;
            },
            delete_this(){
                this.$http.post('/classroom/author/delete_this',{video_id:this.video_tmp},{emulateJSON: true}).then(function (res) {
                    console.log(res.body)
                    this.getVideos();

                }, function (res) {
                    console.log(res.status);
                });
            },
            ok(){
              this.delete_this();

            },
            cancel(){
                this.modal1=false;
            }

        },
        mounted() {
            this.getStatus();
        }

    })
</script>
</body>
</html>
