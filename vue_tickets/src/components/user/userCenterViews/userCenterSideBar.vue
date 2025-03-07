<template>
    <el-menu
        default-active="this.$route.path" router>
        <el-menu-item index="/userHome/userCenterView">
            <i class="el-icon-user-solid"></i>
            <span slot="title" class="menuSpan">用户首页</span>
        </el-menu-item>
        <el-menu-item index="/userHome/userCenterView/userOrdersView">
            <i class="el-icon-s-promotion"></i>
            <span slot="title"  class="menuSpan">我的订单</span>
        </el-menu-item>
        
        <el-submenu index="">
            <template slot="title">
                <i class="el-icon-location"></i>
                <span slot="title"  class="menuSpan">改签与退票</span>
            </template>
            <el-menu-item index="/userHome/userCenterView/userChangeView">
                <i class="el-icon-shopping-cart-2"></i>
                <span slot="title"  class="menuSpan">改签</span>
            </el-menu-item>
            <el-menu-item index="/userHome/userCenterView/userRefundView">
                <i class="el-icon-delete"></i>
                <span slot="title"  class="menuSpan">退票</span>
            </el-menu-item>
        </el-submenu>
        <el-menu-item index="/userHome/userCenterView/userBalanceView">
            <i class="el-icon-bank-card"></i>
            <span slot="title"  class="menuSpan">我的余额</span>
        </el-menu-item>
        <el-menu-item index="/userHome/userCenterView/userInfoView">
            <i class="el-icon-s-custom"></i>
            <span slot="title"  class="menuSpan">个人信息</span>
        </el-menu-item>
        <el-menu-item index="/userHome/userCenterView/userChangePassword">
            <i class="el-icon-lock"></i>
            <span slot="title"  class="menuSpan">修改密码</span>
        </el-menu-item>
        <el-menu-item index="/userHome/userCenterView/userMessagesView">
            <i class="el-icon-bell"></i>
            <span slot="title"  class="menuSpan">我的消息</span>
        </el-menu-item>
        <el-menu-item index="" @click="onMoreServiceButtonClick">
            <i class="el-icon-menu"></i>
            <span slot="title" class="menuSpan">更多服务</span>
        </el-menu-item>
        <el-menu-item index="" @click="onSignOutButtonClick">
            <i class="el-icon-switch-button"></i>
            <span slot="title"  class="menuSpan">退出登录</span>
        </el-menu-item>
    </el-menu>
</template>
<script>
import router from '@/router';
export default{
    data(){
        return{
            
        }
    },
    methods:{
        onMoreServiceButtonClick(){
            this.$message({
                message:'敬请期待',
                type:'success'
            })
        },
        onSignOutButtonClick(){
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
.menuSpan{
    font-size: 17px;
}
</style>