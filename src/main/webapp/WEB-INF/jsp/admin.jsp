<%--
  Created by IntelliJ IDEA.
  User: liao
  Date: 2020/5/12
  Time: 15:24
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>管理员登录</title>
    <link rel="stylesheet" type="text/css" href="https://unpkg.com/iview/dist/styles/iview.css">
    <link type="text/css" rel="styleSheet"  href="/classroom/css/login.css" />
</head>
<body>
<script src="/classroom/js/vue.min.js"></script>
<script src="https://cdn.staticfile.org/vue-resource/1.5.1/vue-resource.min.js"></script>
<script type="text/javascript" src="/classroom/js/iview.min.js"></script>
<div id="login">
    <div id="center">
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
           <i-button  class="submit"  type="success" @click="login">登录</i-button>

        </span>
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
        },
        methods:{
            login:function () {
                if(this.name==""||this.password==""){
                    alert("用户名和密码不能为空！")
                }else {
                    this.$http.post('/classroom/admin/login', {
                        name: this.name,
                        password: this.password
                    }, {emulateJSON: true}).then(function (res) {
                        this.status = res.body.status;
                        if (this.status == "ok") {
                            window.location = "/classroom/admin/user";
                        } else if (this.status = "error") {
                            this.modal2=true;
                        }

                    }, function (res) {
                        console.log(res.body.status);
                        alert("登录失败！");
                    });
                }

            },

        },
        created() {

        }
    })
</script>
</body>

</html>

