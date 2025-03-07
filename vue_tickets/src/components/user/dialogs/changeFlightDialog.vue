<template>
    <el-dialog title="请输入" 
            :visible.sync="dialogVisible" 
            @close="closeDialog" 
            width="80%">
            <el-card shadow="never" style="width: 100%;height: 200px;">
            <el-row :gutter="24">
                
                <el-col :span="8">
                    <el-card shadow="hover" style="margin-top:10px;height: 100px">
                        <div style="opacity: 0.5;">
                            航班编号
                        </div>
                        <el-input :disabled="true" v-model="flight_id" style="width: 80%;"></el-input>
                    </el-card>
                </el-col>
                <el-col :span="8">
                    <el-card shadow="hover" style="margin-top:10px;height: 100px">
                        <el-row>
                            <el-col :span="24">
                                <div style="opacity: 0.5;">
                                    出发日期
                                </div>
                                <el-date-picker
                                    v-model="start_date"
                                    type="date"
                                    placeholder="起始日期"
                                    format="yyyy-MM-dd"
                                    value-format="yyyy-MM-dd"
                                    style="width: 90%;">
                                </el-date-picker>
                            </el-col>
                        </el-row>
                    </el-card>
                </el-col>
                <el-col :span="8">
                    <el-card shadow="hover" style="margin-top:10px;height: 100px">
                        <el-row type="flex" justify="space-around" style="align-items: center">
                            <el-col :span="15">
                                <el-button 
                                    type="primary" plain
                                    style="width: 100%;height: 60%;margin-top: 10%;"
                                    @click="clickSearchButton">
                                    搜索
                                </el-button>
                            </el-col>
                        </el-row>
                    </el-card>
                </el-col>
            </el-row>
        </el-card>
        <el-card shadow="never">
            <el-card shadow="hover"  v-for="(item,index) in flightsAndSchedules" style="margin-top: 5px;" :key="index">
                <div>
                    <el-row :gutter="20" type="flex" justify="space-around" style="align-items: center">
                        <el-col :span="8">
                            <p style="font-size: 18px;">{{ item.airline }}</p>
                            <div style="font-size: 13px;">
                                {{ item.flight_id }}&ensp;{{ item.airtype }}
                            </div>
                        </el-col>

                        <el-col :span="8">
                            <el-row :gutter="24" type="flex" justify="space-around" style="align-items: center">
                                <el-col :span="8" style="font-size: 30px;font-weight: 540;">
                                    {{ item.start_time.slice(0, 5) }}
                                </el-col>
                                <el-col :span="8">
                                    {{ getFlyTime(item.start_time,item.end_time) }}
                                </el-col>
                                <el-col :span="8" style="font-size: 30px;font-weight: 540;">
                                    {{ item.end_time.slice(0, 5) }}
                                </el-col>
                            </el-row>
                            
                            <el-row :gutter="24" style="font-size: 16px;font-weight: 360;">
                                <el-col :span="8">
                                    {{ item.start_airport }}
                                </el-col>
                                <el-col :span="8" :offset="8">
                                    {{ item.end_airport }}
                                </el-col>
                            </el-row>
                        </el-col>

                        <el-col :span="8">
                            <el-row :gutter="24" type="flex" justify="space-around" style="align-items: center">
                                <el-col :span="6" :offset="10" style="font-size: 21px;">
                                    ￥{{ item.price }}
                                </el-col>
                                <el-col :span="8">
                                    <el-button @click="changeTicket(item.start_date)" style="font-size: 18px;">改签</el-button>
                                </el-col>
                            </el-row>
                        </el-col>
                    </el-row>
                </div>
            </el-card>
        </el-card>
    </el-dialog>
</template>
<script>
import request from '@/utils/request';
export default{
    data(){
        return{
            dialogVisible:false,
            flightsAndSchedules:[],
            order_id:'',
            start_date:'',
            flight_id:''
        }
    },
    methods:{
        closeDialog(){
            this.dialogVisible=false;
        },
        findAllFS(){
            request({
                method:'post',
                url:'/flightsAndSchedules/findAllByFlight_idAndStart_date',
                headers:{
                    'token':localStorage.getItem('tickets_token'),
                    'user_type':localStorage.getItem('tickets_user_type')
                },
                data:{
                    flight_id:this.flight_id,
                    start_date:this.start_date
                }
            })
            .then(res=>{
                if(res.data==null){
                    this.$message.error('出错了！')
                }else if(res.data.success==false){
                    this.$message.error(res.data.errorMsg)
                }else{
                    this.flightsAndSchedules=res.data.data
                }
            })
        },
        changeTicket(start_datee){
            request({
                method:'post',
                url:'/orders/changeTicket',
                headers:{
                    'token':localStorage.getItem('tickets_token'),
                    'user_type':localStorage.getItem('tickets_user_type')
                },
                data:{
                    order_id:this.order_id,
                    start_date:start_datee
                }
            })
            .then(res=>{
                if(res.data==null){
                    this.$message.error('出错了！')
                }else if(res.data.success==false){
                    this.$message.error(res.data.errorMsg)
                }else{
                    this.$message({
                        message:res.data.data,
                        type:'success'
                    })
                }
            })
        },
        clickSearchButton(){
            this.findAllFS();
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
        },
        getFlyTime(start_time,end_time){
            var start_time_H=start_time.slice(0,2) - '0';
            var start_time_M=start_time.slice(3,5) - '0';
            var end_time_H=end_time.slice(0,2) - '0';
            var end_time_M=end_time.slice(3,5) - '0'
            var d1=new Date(0,0,0,start_time_H,start_time_M,0);
            var d2;
            if(end_time_H<start_time_H || (end_time_H==start_time_H && end_time_M<start_time_M)){
                d2=new Date(0,0,0,end_time_H,end_time_M,0);
            }else{
                d2=new Date(0,0,0,end_time_H,end_time_M,0);
            }

            var difftime = (d2 - d1)/1000; //计算时间差,并把毫秒转换成秒
            var days = parseInt(difftime/86400); // 天  24*60*60*1000 
            var hours = parseInt(difftime/3600)-24*days;    // 小时 60*60 总小时数-过去的小时数=现在的小时数 
            var minutes = parseInt(difftime%3600/60); // 分钟 -(day*24) 以60秒为一整份 取余 剩下秒数 秒数/60 就是分钟数
            var seconds = parseInt(difftime%60);  // 以60秒为一整份 取余 剩下秒数
            return hours+'小时'+minutes+'分钟'
        }
    }
}
</script>
<style>


</style>