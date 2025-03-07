<template>
    <div style="text-align:center;">
        <el-form
          label-position="left">
  
          <h2 class="login-title color-main">修改密码</h2>
          <el-form-item prop="beforePassword">
            <el-input
              name="user_id"
              type="text"
              v-model="passwordForm.oldPassword"
              autocomplete="on"
              placeholder="请输入原密码"
              style="width: 30%">
            </el-input>
          </el-form-item>
          <el-form-item prop="afterPassword">
            <el-input
              name="user_password"
              @keyup.enter.native="handleLogin"
              v-model="passwordForm.newPassword"
              autocomplete="on"
              placeholder="请输入新密码"
              style="width: 30%">
            </el-input>
          </el-form-item>
  
          <el-form-item >
            <el-button
              style="width: 30%"
              type="primary"
              @click.native.prevent="handleChangePassword"
            >确认修改</el-button>
          </el-form-item>
          
        </el-form>
    </div>
  </template>
  
  <script>
  import request from '@/utils/request';
  import router from '@/router';
  export default {
    name: "login",
    data() {
      return {
        passwordForm: {
            oldPassword:'',
            newPassword:''
        }
      };
    },
    methods: {
        handleChangePassword(){
            if(this.passwordForm.oldPassword==null
                ||this.passwordForm.newPassword==null
                ||this.passwordForm.oldPassword==''
                ||this.passwordForm.newPassword=='')
            {
                this.$message.error('输入不能为空')
                return
            }
            if(this.passwordForm.newPassword==this.passwordForm.oldPassword){
                this.$message.error('新旧密码不能相同')
                return
            }
            request({
                method:'post',
                url:'/users/changePassword',
                headers:{
                    'token':localStorage.getItem('tickets_token'),
                    'user_type':localStorage.getItem('tickets_user_type')
                },
                data:{
                    'user_id':localStorage.getItem('tickets_user_id'),
                    'oldPassword':this.passwordForm.oldPassword,
                    'newPassword':this.passwordForm.newPassword
                }
            })
            .then(res=>{
                if(res.data==null){
                    this.$message.error('出错了！')
                }else if(res.data.success==false){
                    this.$message.error(res.data.errorMsg)
                }else{
                    this.$message({
                        message:"修改成功",
                        type:"success"
                    })
                    router.push({
                        name:'login'
                    })
                }
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
    background: #71a4d8;
    width: auto;
    height: auto;
    max-width: 100%;
    max-height: 100%;
    margin-top: 200px;
  }
  </style>