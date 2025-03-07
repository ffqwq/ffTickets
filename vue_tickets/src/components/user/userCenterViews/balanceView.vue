<template>
    <el-card shadow="never" style="width: 80%;height: 200px;">
        <el-row :gutter="24" type="flex" justify="space-around" style="align-items: center;font-size: 20px;"> 
            <el-col :span="12">
                <el-card shadow="hover" style="height: 150px;">
                    <el-row type="flex" 
                            justify="space-around" 
                            style="align-items: center;margin-top: 10%;">
                        <el-col :span="10">余额：</el-col>
                        <el-col :span="12">{{ balance }}</el-col>
                        <el-col :span="2">元</el-col>
                    </el-row>
                </el-card>
            </el-col>
            <el-col :span="12">
                <el-card shadow="hover" style="height: 150px;"class="JuZhong">
                    <el-button @click="onReChargeButtonClick">
                        充值
                    </el-button>
                </el-card>
            </el-col>
        </el-row>
    </el-card>
</template>
<script>
import request from '@/utils/request';
export default{
    data(){
        return{
            balance:0
        }
    },
    methods:{
        findBalance(user_id){
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
                        this.balance=res.data.data.balance
                    }
                }
            )    
        },
        onReChargeButtonClick(){
            this.$prompt('请输入充值款数', '请输入', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                inputPattern: /^\d+(?:\.\d+)?$/,
                inputErrorMessage: '格式不正确'
            }).then(({ value }) => {
                this.increaseBalance(value)
            }).catch(() => {
                      
            });
        },
        increaseBalance(num){
            request({
                method:'post',
                url:'/users/increaseUserBalance',
                headers:{
                    'token':localStorage.getItem('tickets_token'),
                    'user_type':localStorage.getItem('tickets_user_type')
                },
                data:{
                    user_id:window.localStorage.getItem("tickets_user_id"),
                    amount:num
                }
            })
            .then(
                res=>{
                    if(res.data==null){
                        this.$message.error('出错了！')
                    }else if(res.data.success==false){
                        this.$message.error(res.data.errorMsg)
                    }else{
                        this.$message({
                            message:'充值成功',
                            type:'success'
                        })
                        this.findBalance(window.localStorage.getItem('tickets_user_id'));
                    }
                }
            )    
        }
    },
    mounted(){
        this.findBalance(window.localStorage.getItem('tickets_user_id'));
    }
}
</script>
<style>
.JuZhong{
    display:flex;
    justify-content: center;
    align-items: center;
}
</style>