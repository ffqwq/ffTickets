<template>
  <div class="aside" style="height: 100%; width: 100%;">
    <el-row class="tac" style="height: 100%; width: 100%;">
      <el-col :span="12" style="height: 100%; width: 100%;">
        <el-menu 
          default-active="this.$route.path" router
          class="el-menu-vertical-demo"
          style="height: 100%; width: 100%;">
          
          <el-menu-item index="/adminHome/flightsView">
            <i class="el-icon-menu"></i>
            <span slot="title">航班管理</span>
          </el-menu-item>
          <el-menu-item index="/adminHome/schedulesView">
            <i class="el-icon-document"></i>
            <span slot="title">调度管理</span>
          </el-menu-item>
          <el-menu-item index="/adminHome/usersView">
            <i class="el-icon-user"></i>
            <span slot="title">用户管理</span>
          </el-menu-item>
          <el-menu-item index="/adminHome/passengersView">
            <i class="el-icon-user-solid"></i>
            <span slot="title">乘客管理</span>
          </el-menu-item>
          <el-menu-item index="/adminHome/ordersView">
            <i class="el-icon-goods"></i>
            <span slot="title">订单管理</span>
          </el-menu-item>
          <el-menu-item index="/adminHome/messagesView">
            <i class="el-icon-bell"></i>
            <span slot="title">消息管理</span>
          </el-menu-item>
        </el-menu>
      </el-col>
    </el-row>
  </div>
</template>
  
<script>
export default {
    methods: {
     
    }
  }
</script>

  