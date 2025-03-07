<template>
    <el-card>
      <el-container class="app">
      <el-container 
        style="height: 100vh; 
        border: 1px solid #eee; 
        margin: 0;
        border: 0;
        padding: 0;">     <!-- style： 隐藏滚动条  去掉白边-->
        <!-- 侧边栏 -->
        <el-aside style="width: 200px;">
          <sideBar></sideBar>
        </el-aside>
        <!-- 主页面 -->
        <el-main id="div-nav">
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>
    </el-card>
</template>
   
<script>
import sideBar from '@/components/user/userCenterViews/userCenterSideBar.vue'
  
export default {
  data(){
    return{
      name: "App"
    }
  },
  components: {
    sideBar,
  },
  mounted(){
      document.body.style.overflow = "hidden";//阻止整个页面滚动
  },
  beforeDestroy() {
      document.body.style.overflow = "visible";//阻止整个页面滚动
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
  