<%--
  Created by IntelliJ IDEA.
  User: liao
  Date: 2020/4/15
  Time: 13:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>login</title>
    <link rel="stylesheet" type="text/css" href="https://unpkg.com/iview/dist/styles/iview.css">
    <link type="text/css" rel="styleSheet"  href="../classroom/css/login.css" />
</head>
<body>
<script src="../classroom/js/vue.min.js"></script>
<script src="https://cdn.staticfile.org/vue-resource/1.5.1/vue-resource.min.js"></script>
<script type="text/javascript" src="../classroom/js/iview.min.js"></script>
<div id="login">
    <div id="center">
        <div class="dlogin" style="height: 51px;">
            <span id="na">
               <a class="navigation" href="javascript:void(0);" v-for="(navigation,index) in navigations" :class="{changeTwo:flag==index}" @click="changed(index)">{{navigation}}</a>

            </span>
        </div>
        <div class="dlogin">
            <i-input class="ilogin" size="large" v-model="name" placeholder="用户名">
                <Icon type="ios-contact" slot="prefix" />
            </i-input>
        </div>
        <div  class="dlogin">
            <i-input class="ilogin" type="password" size="large" v-model="password" placeholder="密码" >
                <Icon type="ios-lock" slot="prefix" />
            </i-input>
            <i-input v-if="flag==1" class="ilogin"  size="large" v-model="mail" placeholder="邮箱" >
                <Icon type="ios-lock" slot="prefix" />
            </i-input>
        </div>
        <span style="display: flex;flex-direction: row;align-content: center">
           <i-button v-if="flag==0" class="submit"  type="success" @click="login">登录</i-button>
           <i-button v-if="flag==1" class="submit"  type="success" @click="register">注册</i-button></span>

    </div>
    <Modal
            v-model="modal2"
            title="提示！！"
    >
        <h3 style="color: red;">密码错误！！</h3>
    </Modal>
</div>
<script>
    vm=new Vue({
        el:'#login',
        data:{
            name:"",
            password:"",
            mail:"",
            modal2:false,
            flag:0,
            navigations:["登录","注册"],
        },
        methods:{
            login:function () {
                if(this.name==""||this.password==""){
                    alert("用户名和密码不能为空！")
                }else {
                    this.$http.post('../classroom/author/login', {
                        name: this.name,
                        password: this.password
                    }, {emulateJSON: true}).then(function (res) {
                        this.status = res.body.status;
                        if (this.status == "ok") {
                            window.location = "./author/user";
                        } else if (this.status = "error") {
                            this.modal2=true;
                        }

                    }, function (res) {
                        console.log(res.body.status);
                        alert("登录失败！");
                    });
                }

            },
            register:function () {
                var emreg=/^\w{3,}(\.\w+)*@[A-z0-9]+(\.[A-z]{2,5}){1,2}$/;
                if(emreg.test(this.mail)) {
                    if (this.name == "" || this.password == "" || this.mail == "") {
                        alert("用户名和密码不能为空")
                    }
                    this.$http.post('../classroom/author/register', {
                        name: this.name,
                        password: this.password,
                        mailbox: this.mail
                    }, {emulateJSON: false}).then(function (res) {
                        this.status = res.body.status;
                        if (this.status == "ok") {
                            alert("已发送激活链接，请在邮箱中查收！")
                        } else if (this.status == "repeat") {
                            alert("用户名已存在！")
                        } else alert(this.status)

                    }, function (res) {
                        console.log(res.body.status);
                        alert("注册失败！")
                    });
                }
                else alert("邮箱格式错误！")
            },
            changed:function (index) {
                this.flag=index;
            }
        },
        created() {

        }
    })
</script>
</body>

</html>

