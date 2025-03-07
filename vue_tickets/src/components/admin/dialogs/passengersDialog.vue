<template>
    <el-dialog title="请输入" 
                :visible.sync="dialogVisible" 
                @close="closeDialog" 
                width="35%"
                >
        <el-form :label-position="'left'" label-width="80px" :model="dialogData">
            <el-form-item label="乘客号" >
                <el-input v-model="dialogData.passenger_id"></el-input>
            </el-form-item>
            <el-form-item label="乘客名" >
                <el-input v-model="dialogData.passenger_name"></el-input>
            </el-form-item>
            <el-form-item label="用户编号" >
                <el-input v-model="dialogData.user_id"></el-input>
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
            oldPassenger_id:"",
            dialogVisible:false,
            types:{
                passenger:'passenger',
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
                    url:'/passengers/editAndSavePassenger',
                    headers:{
                        'token':localStorage.getItem('tickets_token'),
                        'user_type':localStorage.getItem('tickets_user_type')
                    },
                    data:{
                        passenger_id:this.oldPassenger_id,
                        passenger:this.dialogData
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
                    url:'/passengers/insertPassenger',
                    headers:{
                        'token':localStorage.getItem('tickets_token'),
                        'user_type':localStorage.getItem('tickets_user_type')
                    },
                    data:this.dialogData
                })
                .then(res=>{
                    if(res.data==null||res.data==''){
                        this.$message.error('出错了！')
                    }else if(res.data.success==false){
                        this.$message.error(res.data.errorMsg)
                    }else{
                        console.log(res.data)
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


