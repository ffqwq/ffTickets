<template>
    <el-dialog title="请输入" 
                :visible.sync="dialogVisible" 
                @close="closeDialog" 
                width="35%"
                >
        <el-form :label-position="'left'" label-width="80px" :model="dialogData">
            <el-form-item v-if="insertOrEdit =='edit'" label="订单编号" >
                <el-input  v-model="dialogData.order_id"></el-input>
            </el-form-item>
            <el-form-item label="用户编号" >
                <el-input v-model="dialogData.user_id"></el-input>
            </el-form-item>
            <el-form-item label="身份证号" >
                <el-input v-model="dialogData.passenger_id"></el-input>
            </el-form-item>
            <el-form-item label="航班编号" >
                <el-input v-model="dialogData.flight_id"></el-input>
            </el-form-item>
            <el-form-item label="起始日期" >
                <el-date-picker 
                        v-model="dialogData.start_date" 
                        value-format="yyyy-MM-dd"
                        type="date"
                        style="width: 100%;">
                </el-date-picker>
            </el-form-item>
            <el-form-item label="金额" >
                <el-input-number v-model="dialogData.amount" 
                        :precision="2" :step="0.1" 
                        :max="99999999" 
                        :min="0"
                        style="width: 100%;">
                    
                </el-input-number>
            </el-form-item>
            <el-form-item label="提交时间">
                <el-date-picker
                    v-model="dialogData.update_time"
                    type="datetime"
                    value-format="yyyy-MM-dd HH:mm:ss"
                    style="width: 100%;">
                </el-date-picker>
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
            oldOrder_id:"",
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
                this.dialogData.passenger_id==''||
                this.dialogData.flight_id==''||
                this.dialogData.start_date==''||
                this.dialogData.amount==''||
                this.dialogData.update_time==''||
                this.dialogData.user_id==null||
                this.dialogData.passenger_id==null||
                this.dialogData.flight_id==null||
                this.dialogData.start_date==null||
                this.dialogData.amount==null||
                this.dialogData.update_time==null
            ){
                this.$message.error("请完整输入");
                return;
            }
            if(this.insertOrEdit == "edit"){
                if(this.oldOrder_id==''||this.oldOrder_id==null){
                    return
                }
                request({
                    method:'post',
                    url:'/orders/editAndSaveOrder',
                    headers:{
                        'token':localStorage.getItem('tickets_token'),
                        'user_type':localStorage.getItem('tickets_user_type')
                    },
                    data:{
                        order_id:this.oldOrder_id,
                        order:this.dialogData
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
                    url:'/orders/insertOrder',
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

