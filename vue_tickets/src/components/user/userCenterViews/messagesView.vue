<template>
    <div>
        <el-card shadow="hover" v-for="(item,index) in messages"style="margin-top:10px;" :key="index">
            <el-row :gutter="24">
                <el-col :span="8">
                    {{ item.update_time }}
                </el-col>
                <el-col :span="16">
                    {{ item.msg }}
                </el-col>
            </el-row>
        </el-card>
    </div>
</template>

<script>
import request from '@/utils/request';
export default{
    data(){
        return{
            messages:[]
        }
    },
    methods:{
        findMessages(user_id){
            request({
                method:'get',
                url:'/messages/findMessagesByUser_id?user_id='+user_id,
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
                        this.messages=res.data.data
                    }
                }
            )    
        }
    },
    mounted(){
        this.findMessages(window.localStorage.getItem("tickets_user_id"));
        document.body.style.overflow = "hidden";//阻止整个页面滚动
    },
    beforeDestroy() {
        document.body.style.overflow = "visible";//阻止整个页面滚动
    },
    components:{
        
    }
}
</script>

<style>

</style>