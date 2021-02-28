<%--
  Created by IntelliJ IDEA.
  User: liao
  Date: 2020/2/17
  Time: 19:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>上传课程</title>
    <link rel="stylesheet" type="text/css" href="../classroom/css/iview.css">
    <link type="text/css" rel="styleSheet"  href="../classroom/css/upload.css" />
</head>
<body>
<script src="../classroom/js/vue.min.js"></script>
<script src="https://cdn.staticfile.org/vue-resource/1.5.1/vue-resource.min.js"></script>
<script type="text/javascript" src="../classroom/js/iview.min.js"></script>
   <div id="app">

       <div id="v_form">
           <div id="one" style="display: flex;flex-direction: row">
               <p style="margin-top: 8px;">输入标题: </p> <i-input v-model="title" style="width: 300px;" type="text"/>
           </div>
           <div id="two">
               <Upload
                       :before-upload=" handleUpload_v"
                       action="//jsonplaceholder.typicode.com/posts/">
                   <Button class="i_style" >选择视频</Button>
               </Upload>
               <div v-if="l_video !== null">已选择: {{ l_video.name }} <i-Button type="primary" @click="upload_video" >确定上传</i-Button></div>
           </div>
           <div id="three">
               <Upload
                       :before-upload=" handleUpload_i"
                       action="//jsonplaceholder.typicode.com/posts/">
                   <Button class="i_style" >选择封面图片</Button>
               </Upload>
               <div v-if="l_image !== null">已选择: {{ l_image.name }}</div>
               <img :src="image_src"/>
           </div>
           <div id="four">
               <p style="margin-bottom: 8px;">选择视频类别：</p>
               <a class="menu" v-for="(category,index) in categories" @click="go(index)" :class="{selected:flag===index}">
                   {{category}}
               </a>
           </div>
           <div class="five" v-if="video_src!=null&&video_src!=1"  >
               <i-Button type="primary" @click="submit" >确认发布此课程</i-Button></div>
           <div class="five" v-if="video_src==1" >
               <i-Button  type="warning" >请耐心等待视频上传完成再发布</i-Button></div>

       </div>

           </div>


           </div>

   </div>
<script>
    new Vue({
        el:'#app',
        data:{
            picture: {},
            result: '111',
            file: null,
            title:"",
            l_image:null,
            l_video:null,
            loadingStatus: false,
            border:"金斑蝶",
            image_src:"https://www.lotcloudy.com/scetc-show-videos-mini-api-0.0.1-SNAPSHOT//images/5106ff32-434d-490a-9a01-27c320a51955.JPG",
            video_src:null,
            video_id:null,
            categories:["语文","数学","英语","其他"],
            category:"语文",
            flag:0,
            author:"",
        },
        methods:{
            go(index) {
                this.flag=index;
                this.category=this.categories[index];

            },
            upload(file) {
                var that = this;

                var formData = new FormData();
                formData.append('fileName', file);
                // specify Content-Type, with formData as well
                that.$http.post('../classroom/upload', formData, {
                    headers: { 'Content-Type': 'multipart/form-data' }
                }).then(function (res) {
                    console.log(res.body)
                    if(res.body.resCode=="1")
                        that.image_src=res.body.webShowPath;
                    else if (res.body.resCode=="2")
                    {
                        that.video_src = res.body.webShowPath;
                        that.video_id=res.body.video_id;

                    }
                    that.$Notice.open({
                        title: '文件上传提醒',
                        desc:'视频上传成功！'
                    });
                }, function (res) {
                    console.log(res.body);
                });
            },
            handleUpload_i (file) {
                this.l_image = file;
                this.upload_image();
                return false;
            },
            handleUpload_v (file) {
                this.l_video = file;
                return false;
            },
            upload_image(){
               this.toUpload(this.l_image);
            },
            upload_video(){
                this.video_src=1;
                this.toUpload(this.l_video);
            },
            toUpload(file){
                var that=this;
                that.upload(file);

            },
            submit(){
                var that=this;

                that.$http.post('../classroom/addVideo', {
                   video_id:that.video_id,video_src:that.video_src,image_src:that.image_src,title:that.title,author:that.author,category:that.category
                }, {emulateJSON: false}).then(function (res) {
                    that.clearSome();
                    alert("课程发布成功！")

                }, function () {
                    console.log('请求失败处理');
                });
            },
            clearSome(){
                title:"",
                    this.l_image=null
                    this.l_video=null
                    this.loadingStatus= false
                    this.border="金斑蝶"
                    this.image_src="https://www.lotcloudy.com/scetc-show-videos-mini-api-0.0.1-SNAPSHOT//images/5106ff32-434d-490a-9a01-27c320a51955.JPG"
                    this.video_src=null
                    this.video_id=null
                    this.title=""
                    this.category=""
            },
            getStatus(){
                this.$http.get('../classroom/author/isLogin').then(function (res) {
                    if(res.body.status=="1") {
                        this.author = res.body.user;
                        console.log(this.author)
                    }
                    else
                        window.location.href="/classroom/";
                })
            },
        },
        mounted() {
            this.getStatus();
        }
    })
</script>
</body>

</html>
