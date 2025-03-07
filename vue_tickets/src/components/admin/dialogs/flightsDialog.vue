<template>
    <el-dialog title="请输入" 
                :visible.sync="dialogVisible" 
                @close="closeDialog" 
                width="35%"
                >
        <el-form :label-position="'left'" label-width="80px" :model="dialogData">
            <el-form-item label="航班编号" >
                <el-input v-model="dialogData.flight_id"></el-input>
            </el-form-item>
            <el-form-item label="航空公司" >
                <el-input v-model="dialogData.airline"></el-input>
            </el-form-item>
            <el-form-item label="机型" >
                <el-input v-model="dialogData.airtype "></el-input>
            </el-form-item>
            <el-form-item label="起始时间" >
                <el-time-picker v-model="dialogData.start_time" value-format="HH:mm:ss">
                </el-time-picker>
            </el-form-item>
            <el-form-item label="到达时间" >
                <el-time-picker v-model="dialogData.end_time" value-format="HH:mm:ss">
                </el-time-picker>
            </el-form-item>
            <el-form-item label="起始城市" >
                <el-input v-model="dialogData.start_city"></el-input>
            </el-form-item>
            <el-form-item label="到达城市" >
                <el-input v-model="dialogData.end_city"></el-input>
            </el-form-item>
            <el-form-item label="起始机场" >
                <el-input v-model="dialogData.start_airport"></el-input>
            </el-form-item>
            <el-form-item label="到达机场" >
                <el-input v-model="dialogData.end_airport"></el-input>
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
            oldFlight_id:"",
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
            if(this.dialogData.flight_id==''||
                this.dialogData.airline==''||
                this.dialogData.airtype==''||
                this.dialogData.start_time==''||
                this.dialogData.end_time==''||
                this.dialogData.start_city==''||
                this.dialogData.end_city==''||
                this.dialogData.start_airport==''||
                this.dialogData.end_airport==''||
                this.dialogData.flight_id==null||
                this.dialogData.airline==null||
                this.dialogData.airtype==null||
                this.dialogData.start_time==null||
                this.dialogData.end_time==null||
                this.dialogData.start_city==null||
                this.dialogData.end_city==null||
                this.dialogData.start_airport==null||
                this.dialogData.end_airport==null
            ){
                this.$message.error("请完整输入");
                return
            }
            if(this.insertOrEdit == "edit"){
                request({
                    method:'post',
                    url:'/flights/editAndSaveFlight',
                    headers:{
                        'token':localStorage.getItem('tickets_token'),
                        'user_type':localStorage.getItem('tickets_user_type')
                    },
                    data:{
                        flight_id:this.oldFlight_id,
                        flight:this.dialogData
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
                    url:'/flights/insertFlight',
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