<template>
    <div >
        <el-form :label-position="'left'" label-width="80px" :model="userInfo">
            <el-form-item label="用户编号" >
                {{ userInfo.user_id }}
            </el-form-item>
            <el-form-item label="用户名" >
                {{ userInfo.user_name }}
            </el-form-item>
            <el-form-item label="用户权限" >
                {{ userInfo.user_type }}
            </el-form-item>
            <el-form-item v-for="(item,index) in userInfo.passengers" label="乘客" :key="index">
                <el-form-item label="乘客名" >
                    {{ item.passenger_name }}
                </el-form-item>
                <el-form-item label="身份证号" >
                    {{ item.passenger_id }}
                </el-form-item>
            </el-form-item>
        </el-form>
    </div>
</template>

<script>
import request from '@/utils/request';
export default{
    data(){
        return{
            userInfo:{
                user_id:'',
                user_name:'',
                passengers:[]
            }
        }
    },
    methods:{
        findUserInfo(){
            request({
                method:'get',
                url:'/users/findUserInfo?user_id='+window.localStorage.getItem('tickets_user_id'),
                headers:{
                    'token':localStorage.getItem('tickets_token'),
                    'user_type':localStorage.getItem('tickets_user_type')
                }
            })
            .then(res=>{
                if(res.data==null){
                    this.$message.error('出错了！')
                }else if(res.data.success==false){
                    this.$message.error(res.data.errorMsg)
                }else{
                    this.userInfo=res.data.data
                }
            })  
        }
    },
    mounted(){
        this.findUserInfo();
    }
}
</script>