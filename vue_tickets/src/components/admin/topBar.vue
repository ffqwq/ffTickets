<template>
    <div class="block">
      <div class="inA">
        <el-breadcrumb separator="/" separator-class="el-icon-arrow-right" style="float: left;padding: 20px 30px;">
            <el-breadcrumb-item v-for="(item,index) in lists" :key="item.path">
                <router-link :to="item.path">{{item.meta.title}}</router-link>
            </el-breadcrumb-item>
        </el-breadcrumb>
      </div>
      <div class="inA">
        <el-menu default-active="this.$route.path" router
                    class="el-menu-demo" 
                    mode="horizontal" 
                    style="float: right;">
        <el-menu-item index="/">
            返回登录
        </el-menu-item>
        <el-submenu index="submenu">
            <template slot="title">个人中心</template>
            <el-menu-item index="/adminHome/myInfo">我的信息</el-menu-item>
            <el-menu-item index="/adminHome/changePassword">修改密码</el-menu-item>
            <el-menu-item @click="signOut">退出登录</el-menu-item>
        </el-submenu>
        </el-menu>
      </div>
    </div>
</template>


<script>
import router from '@/router';
export default {
  data() {
      return {
          activeIndex: '',
          lists:[]
      };
  },
  created() {
      this.lists = this.$route.matched  //获取路由内的全部信息
  },
  //这里必须使用监听，否则无法实时获取路由变动信息。
  //监听后路由会实时变动，不然需要手动刷新路径才会改
  watch:{
      $route(to,from)  {
          this.lists = to.matched
      }
  },
  methods: {
    signOut(){
      this.$confirm('确定要退出登录吗？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
      })
      .then(()=>{
          window.localStorage.setItem('tickets_user_id',null)
          window.localStorage.setItem('tickets_token',null)
          window.localStorage.setItem('tickets_user_type',null)
          router.push({
            name:'login'
          })  
      })
      .catch(()=>{

      })
      
    }
  }
}
</script>
<style>



</style>