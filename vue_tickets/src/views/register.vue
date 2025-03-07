<template>
  <div>
    <el-card class="login-form-layout">
      <el-form
        autocomplete="on"
        :model="loginForm"
        ref="loginForm"
        label-position="left">

        <h2 class="login-title color-main">注册</h2>
        <el-form-item prop="user_id">
          <el-input
            name="user_id"
            type="text"
            v-model="loginForm.user_id"
            autocomplete="on"
            placeholder="请输入用户编号">
          </el-input>
        </el-form-item>

        <el-form-item prop="user_name">
          <el-input
            name="user_name"
            type="text"
            @keyup.enter.native="handleLogin"
            v-model="loginForm.user_name"
            autocomplete="on"
            placeholder="请输入用户名">
          </el-input>
        </el-form-item>

        <el-form-item>

          <el-input
            name="user_password"
            :type="pwdType"
            @keyup.enter.native="handleLogin"
            v-model="loginForm.user_password"
            autocomplete="on"
            placeholder="请输入密码">
          </el-input>
          
        </el-form-item>
        <el-form-item >
          <el-button
            style="width: 100%"
            type="primary"
            :loading="loading"
            @click.native.prevent="handleRegister"
          >注册</el-button>
          <label @click="gotoLogin" style="float: right;">返回登录</label>
        </el-form-item>
        
      </el-form>
    </el-card>
  </div>
</template>

<script>
import router from '@/router';
import request from '@/utils/request';
export default {
  name: "login",
  data() {
    return {
      loginForm: {
          user_id: "",
          user_name:"",
          user_password: ""
      },
      loading: false,
      pwdType: "password"
    };
  },
  methods: {
    showPwd() {
      if (this.pwdType === "password") {
        this.pwdType = "";
      } else {
        this.pwdType = "password";
      }
    },
    handleRegister() {
      if(this.loginForm.user_id==''||this.loginForm.user_id==null){
        this.$message.error("用户编号不能为空")
      }else if(this.loginForm.user_name==''||this.loginForm.user_name==null){
        this.$message.error("用户名不能为空")
      }else if(this.loginForm.user_password==''||this.loginForm.user_password==null){
        this.$message.error("密码不能为空")
      }else {
        request.post('/users/register',this.loginForm).then(
          res=>{
            if(res.data.success){
              this.$message({
                message:"注册成功",
                type:'success'
              })
            }else{
              this.$message.error(res.data.errorMsg);
            }
          }
        )
      }
    },
    gotoLogin(){
      router.push({
        name:'login'
      })
    }
  }
};
</script>

<style scoped>
.login-form-layout {
  position: absolute;
  left: 0;
  right: 0;
  width: 360px;
  margin: 140px auto;
  /* border-top: 10px solid #409eff; */
}

.login-title {
  text-align: center;
}

.login-center-layout {
  background: #409eff;
  width: auto;
  height: auto;
  max-width: 100%;
  max-height: 100%;
  margin-top: 200px;
}
</style>