<template>
    <div id="div-nav">
        <el-card id="div-nav" shadow="never">
            <el-card shadow="hover">
                <el-form :label-position="'left'" label-width="20%" :model="userInfo">
                    <el-form-item label="用户编号" >
                        <el-input v-model="userInfo.user_id" :disabled="userInfoDisable" style="width: 60%;"></el-input>
                    </el-form-item>
                    <el-form-item label="用户名" >
                        <el-input v-model="userInfo.user_name" :disabled="userInfoDisable" style="width: 60%;"></el-input>
                    </el-form-item>
                    <el-form-item label="用户类型" >
                        <el-input v-model="userInfo.user_type" :disabled="userInfoDisable" style="width: 60%;"></el-input>
                    </el-form-item>
                    <el-form-item label="余额" >
                        <el-input v-model="userInfo.balance" :disabled="userInfoDisable" style="width: 60%;"></el-input>
                    </el-form-item>
                    <el-form-item label="" >
                        <el-button type="primary" @click="changeUserInfo" style="width: 60%;">
                            修改
                        </el-button>
                    </el-form-item>
                </el-form>
            </el-card>
            <br>
            <p>已经绑定的乘客：</p>
            <br>
            <el-card shadow="hover" v-for="(item,index) in userInfo.passengers" :key="index">
                <el-form :label-position="'left'" label-width="20%" :model="userInfo.passengers[index]">
                    <el-form-item label="乘客名" >
                        <el-input :disabled="userInfoDisable" v-model="userInfo.passengers[index].passenger_name" style="width: 60%;"></el-input>
                    </el-form-item>
                    <el-form-item label="乘客身份证号" >
                        <el-input :disabled="userInfoDisable" v-model="userInfo.passengers[index].passenger_id" style="width: 60%;"></el-input>
                    </el-form-item>
                </el-form>
            </el-card>
            <el-button type="primary" @click="addPassenger" style="margin-top: 20px;margin-left: 20%;width: 48%;">
                添加乘客
            </el-button>
        </el-card>
        <div>
            <info-dialog ref='infoDialog'></info-dialog>
        </div>
        <div>
            <addPassengerDialog ref="addPassengerDialog"></addPassengerDialog>
        </div>
    </div>
</template>

<script>
import request from '@/utils/request';
import infoDialog from '@/components/user/dialogs/changeUserInfoDialog.vue';
import addPassengerDialog from '../dialogs/addPassengerDialog.vue';
export default{
    data(){
        return{
            userInfo:{
                user_id:'',
                user_name:'',
                user_type:'',
                balance:0,
                passengers:[]
            },
            userInfoDisable:true
        }
    },
    methods:{
        findInfo(user_id){
            request({
                method:'get',
                url:'/users/findUserInfo?user_id='+user_id,
                headers:{
                    'token':localStorage.getItem('tickets_token'),
                    'user_type':localStorage.getItem('tickets_user_type')
                }
            })
            .then(
                res=>{
                    if(res.data==null){
                        this.$message.error('出错了！')
                    }else if(res.data.success==false){
                        this.$message.error(res.data.errorMsg)
                    }else{
                        this.userInfo=res.data.data
                    }
                }
            )    
        },
        changeUserInfo(){
            this.$refs.infoDialog.dialogVisible=true;
            this.$refs.infoDialog.userInfo = this.userInfo;
        },
        addPassenger(){
            this.$refs.addPassengerDialog.dialogVisible=true;
        }
    },
    mounted(){
        this.findInfo(window.localStorage.getItem("tickets_user_id"));
        document.body.style.overflow = "hidden";//阻止整个页面滚动
    },
    beforeDestroy() {
        document.body.style.overflow = "visible";//阻止整个页面滚动
    },
    components:{
        infoDialog,
        addPassengerDialog
    }
}
</script>

<style>
.JuZhong{
    display:flex;
    justify-content: center;
    align-items: center;
}
#div-nav::-webkit-scrollbar {
  display: none;
}
</style>