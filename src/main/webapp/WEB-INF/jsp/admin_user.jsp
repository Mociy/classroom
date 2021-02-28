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
<script src="/classroom/js/vue.min.js"></script>
<script src="https://cdn.staticfile.org/vue-resource/1.5.1/vue-resource.min.js"></script>
<script type="text/javascript" src="/classroom/js/iview.min.js"></script>
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
            <div v-if="flag==2">
                <div style="flex-grow: 3;">
                    <i-input size="large" v-model="value"  search=true enter-button="搜索"  @on-search="getAuthor"  style="width: 50%;margin: 0 auto;margin-top: 10px"/>
                </div>
                <div v-if="authors!=1" class="blog" style="display: flex;border-top: 2px #999999 solid;" >
                    <div style="width: 85%;">
                      <p><span style="font-size: 15px;">{{authors.name}}</span></p>
                      <p><span>{{authors.mailbox}}</span></p>
                    </div>
                    <div style="width:15%;">
                        <i-Button style="margin-top: 20px;" @click="toDelete(complaint.video_id)" type="error">删除</i-Button>
                    </div>
                </div>
            </div>
            <div v-if="flag==1">
                  <div class="blog" style="display: flex;" v-for="complaint in complaints " >
                      <div style="width: 85%;">
                      <p>举报原因：<span style="color: red">{{complaint.complaint_content}}</span></p>
                      <a style="font-size: 15px;" @click="gopage(complaint.video_src)">点击查看课程视频</a>
                      <p>作者：{{complaint.author}}</p>
                          <Modal
                                  v-model="modal1"
                                  title="提示！！"
                                  @on-ok="ok1"
                                  @on-cancel="cancel1">
                              <h3 style="color: red;">你确定要删除此课程吗吗？</h3>
                          </Modal>
                      </div>
                      <div style="width:15%;">
                          <i-Button style="margin-top: 20px;" @click="toDelete(complaint.video_id)" type="error">删除</i-Button>
                      </div>
                  </div>
            </div>
            <div v-if="flag==0">
                <div class="blog" v-for="cre in credentials " style="height: auto;">
                    <p class="cre_p">姓名:{{cre.name}}</p>
                    <p class="cre_p">身份证号码:{{cre.id_card}}</p>
                    <p  >申请时间:{{cre.create_time}}</p>
                   <p style="margin-top: 10px;">
                       <img @click="big_image(cre.id_card_image)"  :src="cre.id_card_image" style="width: 300px;height: 200px;">
                       <img @click="big_image(cre.teacher_certificate_image)"  :src="cre.teacher_certificate_image" style="width: 300px;height: 200px;">
                   </p>
                    <p style="margin-top: 10px;padding-left: 220px;">
                        <i-button  type="success" @click="pass(cre.author_id)">审核通过</i-button>   <i-button  type="error" @click="no_pass(cre.author_id)">审核不通过</i-button>
                    </p>
                    <Modal
                            v-model="modal"
                            title="提示"
                            @on-ok="ok"
                            @on-cancel="cancel">
                        </p> <i-input v-model="value" placeholder="请输入不通过原因" style="width: 300px;" type="text"/>
                    </Modal>
                </div>
                <div style="margin: 0 auto;width: 180px;margin-top: 8px;" v-if="type==1"  >
                    <i-button type="primary" @click="getpage(1)">
                        <Icon type="ios-arrow-back"></Icon>
                        上一页
                    </i-button>
                    <i-button type="primary" @click="getpage(2)">
                        下一页
                        <Icon type="ios-arrow-forward"></Icon>
                    </i-button>
                </div>
            </div>

        </div>
    </div>

</div>
<script>
    vm=new Vue({
        el:'#App',
        data:{
            menus:["认证信息列表","举报管理","用户管理"],
            author:"",
            flag:0,
            credentials:null,
            isLogin:"0",
            modal:false,
            value:"",
            author_id:"",
            complaints:"",
            video_tmp:null,
            modal1:false,
            authors:1,
            now:0,
            type:0,
            value:"",
        },
        methods:{
            go:function (index) {
                this.flag=index;
                if(index==1) {
                    this.getComplaints();
                }else if(index==2){

                }else if(index==0){
                    this.getAll(0);
                }

            },
            gopage(video_src){
                window.open(video_src);
            },
            pass(author_id){
                this.$http.post('/classroom/admin/pass',{author_id:author_id},{emulateJSON: true}).then(function (res) {
                    console.log(res.body.status)
                    this.getAll(0);
                }, function (res) {
                    console.log(res.status);
                });
            },
            no_pass(author_id){
                this.modal=true;
                this.author_id=author_id;

            },
            ok(){
                this.$http.post('/classroom/admin/no_pass',{author_id:this.author_id,reason:this.value}).then(function (res) {
                    console.log(res.body.status)
                    this.getAll(0);
                }, function (res) {
                    console.log(res.status);
                });
            },
            cancel(){

            },
            getAll:function (page) {
                this.$http.post('/classroom/admin/getAll',{page:page},{emulateJSON: true}).then(function (res) {
                    this.credentials=res.body;
                    if(this.credentials.length==2)
                        this.type=1;
                    else this.type=0;
                }, function (res) {
                    console.log("error");
                });
            },
            getpage(num){
               if(num==1&&this.now>0){
                  this.now=this.now-1;
                  this.getAll(this.now);
               }  else{
                   this.now=this.now+1;
                   this.getAll(this.now);
               }

            },
            getComplaints(){
                this.$http.get('/classroom/admin/getComplaints').then(function (res) {
                    this.complaints=res.body;
                }, function (res) {
                    console.log("error");
                });
            },
            getStatus: function () {
                this.$http.post('/classroom/admin/isLogin').then(function (res) {
                    this.islogin=res.body.status;
                    if(this.islogin=="1"){
                        this.author=res.body.user;
                    }
                    else
                        window.location.href="/classroom/admin/";
                }, function (res) {
                    console.log(res.status);
                });
            },
            big_image(src){
              window.open(src);
            },
            toDelete(video_id){
                this.modal1=true;
                this.video_tmp=video_id;
            },
            delete_this(){
                this.$http.post('/classroom/author/delete_this',{video_id:this.video_tmp},{emulateJSON: true}).then(function (res) {
                    console.log(res.body)
                    this.getComplaints();

                }, function (res) {
                    console.log(res.status);
                });
            },
            ok1(){
                this.delete_this();

            },
            cancel1(){
                this.modal1=false;
            },
            getAuthor(){
                this.$http.post('/classroom/admin/getAuthor',{name:this.value},{emulateJSON: true}).then(function (res) {
                     console.log(res.body)
                     this.authors=res.body;
                }, function (res) {
                    console.log(res.status);
                });
            }
        },
        mounted() {
            this.getStatus();
            this.getAll(0);
        }
    })
</script>
</body>
</html>

