<template>
    <el-container class="app">
      <!-- 头部 -->
      <el-header>
        <topBar></topBar>
      </el-header>

      <el-container 
        style="height: 100vh; 
        border: 1px solid #eee; 
        margin: 0;
        border: 0;
        padding: 0;">     <!-- style： 隐藏滚动条  去掉白边-->
        <!-- 主页面 -->
        <el-main>
          <router-view ></router-view>
        </el-main>
      </el-container>
    </el-container>
  </template>
   
  <script>
  import topBar from '@/components/user/topBar.vue';
  
  export default {
    name: "App",
    components: {
      topBar
    }
  }
  </script>
  
  <style>
  html,
  body{
    height: 100%;
    margin: 0;
    border: 0;
    padding: 0;
  }
  </style>