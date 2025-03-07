<template>
    <el-dialog title="请输入" 
                :visible.sync="dialogVisible" 
                @close="closeDialog" 
                width="35%"
                >
        <el-form :label-position="'left'" label-width="80px" :model="dialogData">
            <el-form-item label="用户编号" >
                <el-input v-model="dialogData.user_id"></el-input>
            </el-form-item>
            <el-form-item label="用户名" >
                <el-input v-model="dialogData.user_name"></el-input>
            </el-form-item>
            <el-form-item v-if="insertOrEdit=='insert'" label="用户密码" >
                <el-input v-model="dialogData.user_password" type="password"></el-input>
            </el-form-item>
            <el-form-item label="余额">
                <el-input v-model.number="dialogData.balance" 
                    type="number"
                    oninput="value=value.replace(/[^\d]/g,'')">
                </el-input>
            </el-form-item>
            <el-form-item label="权限">
                <el-select v-model="dialogData.type" placeholder="请选择" style="width: 100%;">
                <el-option label="普通用户" v-model="types.user"></el-option>
                <el-option label="管理员" v-model="types.admin"></el-option>
                </el-select>
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
            insertOrEdit:"",
            oldUser_id:"",
            dialogVisible:false,
            types:{
                user:'user',
                admin:'admin'
            },
            dialogData:{}
        }
    },
    methods:{
        closeDialog(){
            this.dialogVisible=false;
        },
        saveDialog(){
            // this.$emit('childFun',this.dialogData)
            if(this.insertOrEdit == "edit"){
                request({
                    method:'post',
                    url:'/users/editAndSaveUser',
                    headers:{
                        'token':localStorage.getItem('tickets_token'),
                        'user_type':localStorage.getItem('tickets_user_type')
                    },
                    data:{
                        user_id:this.oldUser_id,
                        user:this.dialogData
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
            }
            if(this.insertOrEdit == "insert"){
                request({
                    method:'post',
                    url:'/users/insertUser',
                    headers:{
                        'token':localStorage.getItem('tickets_token'),
                        'user_type':localStorage.getItem('tickets_user_type')
                    },
                    data:this.dialogData
                })
                .then(res=>{
                    if(res.data==null){
                        this.$message.error('出错了！')
                    }else if(res.data.success==false){
                        this.$message.error(res.data.errorMsg)
                    }else{
                        this.$message({
                            message:'插入成功',
                            type:'success'
                        })
                    }
                })
                
            }

            this.dialogVisible=false;
        }
    }
}
</script>
<style>

</style>

