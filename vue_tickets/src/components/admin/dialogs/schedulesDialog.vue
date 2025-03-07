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
            <el-form-item label="起始时间" >
                <el-date-picker 
                        v-model="dialogData.start_date" 
                        value-format="yyyy-MM-dd"
                        type="date"
                        style="width: 100%;">
                </el-date-picker>
            </el-form-item>
            <el-form-item label="票数">
                <!-- .number用于将input的String转成数值类型 -->
                <el-input v-model.number="dialogData.nums" 
                    type="number"
                    oninput="value=value.replace(/[^\d]/g,'')">
                    <!-- //正整数 -->
                </el-input>
            </el-form-item>
            <el-form-item label="价格" >
                <el-input-number v-model="dialogData.price" 
                        :precision="2" :step="0.1" 
                        :max="99999999" 
                        :min="0"
                        style="width: 100%;">
                    
                </el-input-number>
                <!-- <el-input v-model="dialogData.price" 
                    oninput="value=value.replace(/[^\d.]/g,'')">
                </el-input> -->
            </el-form-item>

            <el-form-item label="是否准点">
                <el-select v-model="dialogData.punctuality" placeholder="请选择" style="width: 100%;">
                <el-option label="准点" v-model="punc.yes"></el-option>
                <el-option label="晚点" v-model="punc.no"></el-option>
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
            oldFlight_id:"",
            oldStart_date:"",
            dialogVisible:false,
            dialogData:{},
            punc:{
                yes:true,
                no:false
            }
        }
    },
    methods:{
        closeDialog(){
            this.dialogVisible=false;
        },
        saveDialog(){
            if(this.dialogData.flight_id==''||
                this.dialogData.start_date==''||
                this.dialogData.nums==''||
                this.dialogData.price==''||
                this.dialogData.punctuality==''||
                this.dialogData.flight_id==null||
                this.dialogData.start_date==null||
                this.dialogData.nums==null||
                this.dialogData.price==null||
                this.dialogData.punctuality==null
            ){
                this.$message.error("请完成输入");
                return;
            }
            if(this.insertOrEdit == "edit"){
                request({
                    method:'post',
                    url:'/schedules/editAndSaveSchedule',
                    headers:{
                        'token':localStorage.getItem('tickets_token'),
                        'user_type':localStorage.getItem('tickets_user_type')
                    },
                    data:{
                        flight_id:this.oldFlight_id,
                        start_date:this.oldStart_date,
                        schedule:this.dialogData
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
                    url:'/schedules/insertSchedule',
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
        },
        dateToYMD(now) {
            let year = now.getFullYear();
            let month = now.getMonth() + 1;
            let day = now.getDate();
            if(month < 10){
                month = "0" + month
            }
            if(day < 10){
                day = "0" + day
            }
            return year + "-" + month + "-" + day;
        },
        dateToHMS(now){
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
            return hours + ":" + minutes + ":" + seconds;
        }
    }
}
</script>
<style>


</style>