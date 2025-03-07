<template>
    <el-dialog title="请输入" 
                :visible.sync="dialogVisible" 
                @close="closeDialog" 
                width="35%"
                >
        <el-form :label-position="'left'" label-width="20%" :model="userInfo">
            <el-form-item label="用户名" >
                <el-input v-model="userInfo.user_name" ></el-input>
            </el-form-item>
        </el-form>
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="closeDialog">取消</el-button>
                <el-button type="primary" @click="saveDialog">保存</el-button>
            </span>
        </template>
    </el-dialog>
</template>
<script>
import request from '@/utils/request';
export default{
    data(){
        return{
            dialogVisible:false,
            userInfo:{
                user_id:'',
                user_name:'',
                user_type:'',
                balance:0,
                passengers:[]
            },
        }
    },
    methods:{
        closeDialog(){
            this.dialogVisible=false;
        },
        saveDialog(){
            request({
                method:'post',
                url:'/users/changeName',
                headers:{
                    'token':localStorage.getItem('tickets_token'),
                    'user_type':localStorage.getItem('tickets_user_type')
                },
                data:{
                    user_id:this.userInfo.user_id,
                    user_name:this.userInfo.user_name
                }
            })
            .then(res=>{
                if(res.data==null){
                    this.$message.error('出错了！')
                }else if(res.data.success==false){
                    this.$message.error(res.data.errorMsg)
                }else{
                    this.$message({
                        message:'修改成功',
                        type:'success'
                    })
                }
            })

            this.dialogVisible=false;
        }
    }
}
</script>
<style>
</style>