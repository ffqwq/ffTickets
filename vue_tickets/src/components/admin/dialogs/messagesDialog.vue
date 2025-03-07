<template>
    <el-dialog title="请输入" 
                :visible.sync="dialogVisible" 
                @close="closeDialog" 
                width="35%"
                >
        <el-form :label-position="'left'" label-width="80px" :model="dialogData">
            <el-form-item v-if="insertOrEdit =='edit'" label="消息编号" >
                <el-input  v-model="dialogData.message_id"></el-input>
            </el-form-item>
            <el-form-item label="用户编号" >
                <el-input v-model="dialogData.user_id"></el-input>
            </el-form-item>
            <el-form-item label="消息内容" >
                <el-input v-model="dialogData.msg"></el-input>  
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
            oldMessage_id:"",
            pickerDate:'',
            dialogVisible:false,
            dialogData:{}
        }
    },
    methods:{
        closeDialog(){
            this.dialogVisible=false;
        },
        saveDialog(){
            if(this.dialogData.user_id==''||
                this.dialogData.msg==''||
                this.dialogData.user_id==null||
                this.dialogData.msg==null
            ){
                this.$message.error("请完整输入");
                return
            }
            if(this.insertOrEdit == "edit"){
                this.dialogData.update_time=this.dateToYMD_HMS(new Date());
                request({
                    method:'post',
                    url:'/messages/editAndSaveMessage',
                    headers:{
                        'token':localStorage.getItem('tickets_token'),
                        'user_type':localStorage.getItem('tickets_user_type')
                    },
                    data:{
                        message_id:this.oldMessage_id,
                        message:this.dialogData
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
                this.dialogData.message_id=0;
                this.dialogData.update_time=this.dateToYMD_HMS(new Date());
                request({
                    method:'post',
                    url:'/messages/insertMessage',
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
                        location.reload();
                    }
                })
                
            }

            this.dialogVisible=false;
        },
        dateToYMD_HMS(now) {
            let year = now.getFullYear();
            let month = now.getMonth() + 1;
            let day = now.getDate();
            if(month < 10){
                month = "0" + month
            }
            if(day < 10){
                day = "0" + day
            }

            let hours = now.getHours();
            let minutes = now.getMinutes();
            let seconds = now.getSeconds();
            //当小于 10 的是时候，在前面加 0
            if (hours < 10) {
                hours = "0" + hours;
            }
            if (minutes < 10) {
                minutes = "0" + minutes;
            }
            if (seconds < 10) {
                seconds = "0" + seconds;
            }

            return year + "-" + month + "-" + day +' ' + hours + ":" + minutes + ":" + seconds;
        },
    }
}
</script>
<style>

</style>


