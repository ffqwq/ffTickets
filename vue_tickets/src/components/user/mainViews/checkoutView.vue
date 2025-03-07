<template>
    <el-row>
        <el-col :span="16" >
            <el-card style="width: 90%;height: 80%;margin: auto;background-color: aliceblue;">
                <div>乘机人</div>
                <br>
                <div>
                    快速写入：
                    <a class="cursorHand" v-for="(item,index) in passengers" @click="selectOnePassenger(index)" :key="index">
                        {{ item.passenger_name }}
                    </a>
                    
                </div>
                <br>
                <el-card shadow="hover" v-for="(passengerData,index) in selectedPassengers" style="margin-top: 10px;" :key="index">
                    <el-row type="flex" justify="space-around" style="align-items: center">
                        <el-col :span="1" style="font-size: 20px;opacity: 0.7;">
                            {{ index+1 }}
                            <br><br><br>
                        </el-col>
                        <el-col :span="4">
                            <el-image :src="dotLine" fit="contain"></el-image>
                        </el-col>
                        <el-col :span="18">
                            <el-form :label-position="'left'" 
                                    label-width="100px" 
                                    :model="passengerData">
                                <el-form-item label="乘客姓名">
                                    <el-input v-model="passengerData.passenger_name" 
                                            style="width: 80%;" 
                                            placeholder="请输入姓名">
                                    </el-input>
                                </el-form-item>
                                <el-form-item label="乘客身份证号" >
                                    <el-input v-model="passengerData.passenger_id" 
                                            style="width: 80%;" 
                                            placeholder="请输入身份证号">
                                    </el-input>
                                </el-form-item>
                            </el-form>
                        </el-col>
                        <el-col :span="3" class="cursorHand" @click.native="removeFlyPass(index)">
                            <i class="el-icon-close" style="color: #0086F6;"></i>
                            <a style="color: #0086F6;">&ensp;删除</a>
                        </el-col>
                    </el-row>
                </el-card>
                <el-row :gutter="24" type="flex" justify="space-around" style="align-items: center;height: 50px;">
                    <el-col :span="5" class="cursorHand" @click.native="addFlyPass">
                        <i class="el-icon-circle-plus-outline" style="color: #0086F6;"></i>
                        <a class="childContainer" style="color: #0086F6;">&ensp;添加乘机人</a>
                    </el-col>
                </el-row>
            </el-card>
            <el-row :gutter="24" type="flex" justify="space-around" style="align-items: center;height: 50px;">
                <el-button type="primary"
                        style="font-size: 20px;width: 78%;margin-top: 30%;"
                        @click="clickNextStep">
                    下一步
                </el-button>
            </el-row>
            
        </el-col>
        <el-col v-for="(flightAndScheduleData,index) in AllData" :span="8" :key="index">
            <el-card style="background-color: #F5F8F9;">
                <el-row  type="flex" justify="space-around" style="align-items: center;font-size: 20px;font-weight: 540;">
                    {{ flightAndScheduleData.start_city }} 
                    
                    <i class="el-icon-arrow-right"></i>
                    
                    {{ flightAndScheduleData.end_city }}
                    &ensp;&ensp;
                    {{ flightAndScheduleData.start_date }}
                </el-row>
                <br>
                
                <el-row  type="flex" justify="space-around" style="align-items: center;font-size: 15px;font-weight: 380;">
                    {{flightAndScheduleData.airline}}
                    &ensp;
                    {{flightAndScheduleData.flight_id}}
                    &ensp;
                    {{flightAndScheduleData.airtype}}
                </el-row>
                <br>
                <el-divider></el-divider>
                <el-row :gutter="24" type="flex" justify="space-around" style="align-items: center">
                    <el-col :span="8" style="font-size: 30px;font-weight: 540;">
                        {{ flightAndScheduleData.start_time.slice(0, 5) }}
                    </el-col>
                    <el-col :span="8">
                        {{ getFlyTime(flightAndScheduleData.start_time,flightAndScheduleData.end_time) }}
                    </el-col>
                    <el-col :span="8" style="font-size: 30px;font-weight: 540;">
                        {{ flightAndScheduleData.end_time.slice(0, 5) }}
                    </el-col>
                </el-row>
                <el-row :gutter="24"  type="flex" justify="space-around" style="align-items: center;font-size: 16px;font-weight: 360;">
                    <el-col :span="8">
                        {{ flightAndScheduleData.start_airport }}
                    </el-col>
                    <el-col :span="8" :offset="8">
                        {{ flightAndScheduleData.end_airport }}
                    </el-col>
                </el-row>
                <br>
                <el-divider></el-divider>
                <el-row type="flex" justify="space-around" style="align-items: center;">
                    <el-col :span="12" style="font-size: 34px;font-weight: 550;">
                        ￥{{ getTicketsMoney(flightAndScheduleData.price)  }}
                    </el-col>
                    <el-col :span="12">
                        余票：{{ flightAndScheduleData.nums }}
                    </el-col>
                </el-row>
            </el-card>
        </el-col>
    </el-row>
</template>

<script>
import request from '@/utils/request';
export default{
    data(){
        return{
            dotLine:require("@/assets/img/dotLine.png"),
            isDirect:true,
            AllData:[],
            // flightAndScheduleData:{
            //     flight_id:'',
            //     airline:'',
            //     airtype:'',
            //     start_time:'',
            //     end_time:'',
            //     start_city:'',
            //     end_city:'',
            //     start_airport:'',
            //     end_airport:'',
            //     start_date:'',
            //     nums:0,
            //     price:0,
            //     punctuality:true          
            // },
            passengers:[],//数据库中存有的用户对应所有的乘客
            selectedPassengers:[],//需要被填入数据的乘客数组
            ticketsNum:0
        }
    },
    methods:{
        addFlyPass(){
            this.selectedPassengers.push({
                passenger_name:'',
                passenger_id:''
            });
            this.ticketsNum++;
        },
        removeFlyPass(index){
            if(this.selectedPassengers.length==1)return;
            this.selectedPassengers.splice(index,1);
            this.ticketsNum--;
        },
        getPassengers(user_id){
            request({
                method:'get',
                url:'/passengers/findPassengersByUser_id?user_id='+user_id,
                headers:{
                    'token':localStorage.getItem('tickets_token'),
                    'user_type':localStorage.getItem('tickets_user_type')
                }
            })
            .then(res=>{
                if(res.data==null){
                    this.$message.error('出错了！')
                }else if(res.data.success==false){
                    this.$message.error(res.data.errorMsg)
                }else{
                    this.passengers=res.data.data
                }
            })
        },
        selectOnePassenger(index){
            this.selectedPassengers.push({
                passenger_name:this.passengers[index].passenger_name,
                passenger_id:this.passengers[index].passenger_id
            });
            this.ticketsNum++;
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
        },
        clickNextStep(){
            for(var i=0;i<this.selectedPassengers.length;i++){
                if(this.selectedPassengers[i].passenger_id==''||this.selectedPassengers[i].passenger_name==''){
                    this.$message.error('请完整输入');
                    return
                }
            }
            for(var i=0;i<this.selectedPassengers.length;i++){
                for(var j=0;j<this.AllData.length;j++){
                    request({
                        method:'post',
                        url:'/orders/insertOrder',
                        headers:{
                            'token':localStorage.getItem('tickets_token'),
                            'user_type':localStorage.getItem('tickets_user_type')
                        },
                        data:{
                            order_id:null,
                            user_id:window.localStorage.getItem('tickets_user_id') , 
                            passenger_id:this.selectedPassengers[i].passenger_id , 
                            flight_id:this.AllData[j].flight_id, 
                            start_date:this.AllData[j].start_date, 
                            amount:this.AllData[j].price, 
                            update_time:this.dateToYMD_HMS(new Date()), 
                            is_pay:false
                        }
                    })
                    .then(res=>{
                        if(res.data==null){
                            this.$message.error('出错了！')
                        }else if(res.data.success==false){
                            this.$message.error(res.data.errorMsg)
                        }else{
                            this.$message({
                                message:"订单已提交",
                                type:"success"
                            })
                        }
                    })
                }
            }
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
        getTicketsMoney(num){
            return num * this.ticketsNum
        }
    },
    mounted(){
        this.AllData=this.$route.params.AllData;
        this.isDirect=this.$route.params.isDirect;
        this.getPassengers(window.localStorage.getItem('tickets_user_id'));
        this.addFlyPass();
        for(var i=0;i<this.passengers.length;i++){
            checkboxPassenger.push(false)
        }
    },
    computed:{
        
    }
}
</script>
<style>
.divJuZhong {
    /*居中*/
    display: flex;
    justify-content: center;/*垂直居中*/
    align-items: center;/*水平居中*/
}
.cursorHand:hover{
    cursor:pointer;
    color: #0086F6;
}
</style>