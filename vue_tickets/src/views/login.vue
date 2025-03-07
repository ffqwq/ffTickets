<template>
  <div>
    <!-- <router-view> -->

    
    <el-card class="login-form-layout">
      <el-form
        autocomplete="on"
        :model="loginForm"
        ref="loginForm"
        label-position="left">

        <h2 class="login-title color-main">登录飞机票售票系统</h2>
        <el-form-item prop="user_id">
          <el-input
            name="user_id"
            type="text"
            v-model="loginForm.user_id"
            autocomplete="on"
            placeholder="请输入用户编号">
          </el-input>
        </el-form-item>
        <el-form-item prop="user_password">
          <el-input
            name="user_password"
            :type="pwdType"
            @keyup.enter.native="handleLogin"
            v-model="loginForm.user_password"
            autocomplete="on"
            placeholder="请输入密码">
          </el-input>
        </el-form-item>

        <el-form-item>

          <el-select v-model="userOrAdmin.value" placeholder="请选择" style="width: 100%;">
            <el-option label="用户" v-model="userOrAdmin.user"></el-option>
            <el-option label="管理员" v-model="userOrAdmin.admin"></el-option>
          </el-select>
          
        </el-form-item>
        <el-form-item >
          <el-button
            style="width: 100%"
            type="primary"
            :loading="loading"
            @click.native.prevent="handleLogin"
          >登录</el-button>
        <label @click="gotoRegister" style="float: right;">注册</label>
        </el-form-item>
        
      </el-form>
    </el-card>
  <!-- </router-view> -->
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
          user_id: "17667399193",
          user_password: "123456"
      },
      loading: false,
      pwdType: "password",
      userOrAdmin:{
        value:'用户',
        user:"用户",
        admin:"管理员"
      },
      
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
    gotoRegister(){
      router.push({
        name:'register'
      })
    },
    handleLogin() {
      if(this.loginForm.user_id==''||this.loginForm.user_id==null){
        this.$message.error("用户编号不能为空")
      }else if(this.loginForm.user_password==''||this.loginForm.user_password==null){
        this.$message.error("密码不能为空")
      }else if(this.userOrAdmin.value==''){
        this.$message.error('权限不能为空')
      }else if(this.userOrAdmin.value=='用户' || this.userOrAdmin.value=='管理员'){
        request({
          method:'post',
          url:'/users/login',
          data:{
            'user_id':this.loginForm.user_id,
            'user_password':this.loginForm.user_password,
            'user_type':this.userOrAdmin.value=='用户'?'user':'admin'
          }
        })
        .then(
          res=>{
            if(res.data.success){
              
              window.localStorage.setItem('tickets_token',res.data.data.token)
              window.localStorage.setItem('tickets_user_id',this.loginForm.user_id)
              if(this.userOrAdmin.value=='用户'){
                window.localStorage.setItem('tickets_user_type','user')
              }else{
                window.localStorage.setItem('tickets_user_type','admin')
              }
              this.$message({
                message:"登录成功",
                type:"success"
              })
              if(this.userOrAdmin.value=='用户'){
                router.push({
                  name:'userInputView'
                })
              }
              if(this.userOrAdmin.value=='管理员'){
                router.push({
                  name:'adminHome'
                })
              }
            }else{
              this.$message.error(res.data.errorMsg);
            }
          }
        )
      }
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