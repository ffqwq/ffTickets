<template>
    <div>
        <el-card shadow="never" style="width: 100%;height: 200px;">
            <el-row :gutter="20">
                <el-col :span="8" style="height: 30px;">
                    <div style="margin-top: 0px;">
                        <el-checkbox v-model="isSingal">是否单程</el-checkbox>
                        <el-checkbox v-model="isDirect">只看直飞</el-checkbox>
                        <el-checkbox v-model="onlyTransit">只看中转</el-checkbox>
                    </div>
                </el-col>
                <el-col :span="8">
                    
                </el-col>
                <el-col :span="8">
                    
                </el-col>
            </el-row>
            <el-row :gutter="20">
                <el-col :span="5">
                    <el-card shadow="hover" style="margin-top:10px;height: 100px">
                        <div style="opacity: 0.5;">
                            出发地
                        </div>
                        <el-input v-model="start_city" style="width: 80%;"></el-input>
                    </el-card>
                </el-col>
                <el-col :span="5">
                    <el-card shadow="hover" style="margin-top:10px;height: 100px">
                        <div style="opacity: 0.5;">
                            目的地
                        </div>
                        <el-input v-model="end_city" style="width: 80%;"></el-input>
                    </el-card>
                </el-col>
                <el-col :span="9">
                    <el-card shadow="hover" style="margin-top:10px;height: 100px">
                        <el-row>
                            <el-col :span="12">
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
                            <el-col :span="12">
                                <div style="opacity: 0.5;">
                                    返程日期
                                </div>
                                <el-date-picker
                                    v-model="back_date"
                                    type="date"
                                    placeholder=""
                                    format="yyyy-MM-dd"
                                    value-format="yyyy-MM-dd"
                                    style="width: 90%;"
                                    :disabled="isSingal">
                                </el-date-picker>
                            </el-col>
                        </el-row>
                    </el-card>
                </el-col>
                <el-col :span="5">
                    <el-card shadow="hover" style="margin-top:10px;height: 100px">
                        <el-row type="flex" justify="space-around" style="align-items: center">
                            <el-col :span="10">
                                <el-button 
                                    type="primary" plain
                                    :disabled="!isSingal"
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
        <el-row style="width: 300px;">
            <el-col style="width: 150px;" >
                <el-card @click.native="clickGoto" 
                        v-bind:shadow="isClickSignal?'always':'never'" 
                        v-bind:style="{background:isClickSignal?'#F5F5F5':'#ffffff'}"
                        class="cursorHand">
                    <span style="font-size: 20px">
                        去程
                    </span>
                </el-card>
            </el-col>
            <el-col style="width: 150px;">
                <el-card @click.native="clickBack" 
                        v-bind:shadow="isClickSignal?'never':'always'" 
                        v-bind:disabled="isSingal" v-bind:style="{background:isClickSignal?'#ffffff':'#F5F5F5'}"
                        class="cursorHand">
                    <span style="font-size: 20px">
                        返程
                    </span>
                </el-card>
            </el-col>
        </el-row>
        <el-card shadow="never">
            <el-card shadow="hover" v-if="!onlyTransit" v-for="(item,index) in flightsAndSchedules" style="margin-top: 10px;" :key="index">
                <div>
                    <el-row :gutter="24" type="flex" justify="space-around" style="align-items: center">
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
                                <el-col :span="6" :offset="10" style="font-size: 25px;">
                                    ￥{{ item.price }}
                                </el-col>
                                <el-col :span="8">
                                    <el-button @click="buyTicket(item)" style="font-size: 18px;">购买</el-button>
                                </el-col>
                            </el-row>
                        </el-col>
                    </el-row>
                </div>
            </el-card>
            <el-card shadow="hover" v-for="(item,index) in TransitArray" style="margin-top: 10px;" :key="-index-1">
                <div>
                    <el-row :gutter="20" type="flex" justify="space-around" style="align-items: center">
                        <el-col :span="8">
                            <p style="font-size: 18px;">{{ item.first.airline }}{{ item.first.flight_id }}&ensp;{{ item.first.airtype }}</p>
                            <p style="font-size: 18px;">{{ item.second.airline }}{{ item.second.flight_id }}&ensp;{{ item.second.airtype }}</p>
                        </el-col>

                        <el-col :span="8">
                            <el-row :gutter="24" type="flex" justify="space-around" style="align-items: center">
                                <el-col :span="8" style="font-size: 30px;font-weight: 540;">
                                    {{ item.first.start_time.slice(0, 5) }}
                                </el-col>
                                <el-col :span="8">
                                    {{ getFlyTime(item.first.start_time,item.second.end_time) }}
                                </el-col>
                                <el-col :span="8" style="font-size: 30px;font-weight: 540;">
                                    {{ item.second.end_time.slice(0, 5) }}
                                </el-col>
                            </el-row>
                            
                            <el-row :gutter="24" style="font-size: 16px;font-weight: 360;">
                                <el-col :span="8">
                                    {{ item.first.start_airport }}
                                </el-col>
                                <el-col :span="8" :offset="8">
                                    {{ item.second.end_airport }}
                                </el-col>
                            </el-row>
                        </el-col>

                        <el-col :span="8">
                            <el-row :gutter="24" type="flex" justify="space-around" style="align-items: center">
                                <el-col :span="10">
                                    中转一次
                                </el-col>
                                <el-col :span="6"  style="font-size: 25px;">
                                    ￥{{ item.first.price + item.second.price }}
                                </el-col>
                                <el-col :span="8">
                                    <el-button @click="buyTransitTicket(item)" style="font-size: 18px;">购买</el-button>
                                </el-col>
                            </el-row>
                        </el-col>
                    </el-row>
                </div>
            </el-card>
        </el-card>
        
    </div>
</template>
<script>
import request from '@/utils/request';
import router from '@/router';
export default{
    data(){
        return{
            flightsAndSchedules:[],
            TransitArray:[],//中转航班的数组

            start_date:'',
            back_date:'',
            start_city:'',
            end_city:'',
            isSingal:true,
            isDirect:true,
            onlyTransit:false,

            isClickSignal:true,

            queryData:{
                flight_id:'',
                airline:'',
                airtype:'',
                start_time:'',
                end_time:'',
                timeRange:{
                    allTime:'',
                    lingchen:'lingchen',
                    shangwu:'shangwu',
                    xiawu:'xiawu',
                    wanshang:'wanshang'
                },
                start_city:'',
                end_city:'', 
                start_airport:'', 
                end_airport:''
            }
        }
    },
    methods:{
        findAllByStart_dateAndStart_cityAndEnd_city(){
            if(this.start_date==null||this.start_date==''
                ||this.start_city==null||this.start_city==''
                ||this.end_city==null||this.end_city==''
            ){
                this.$message.error("请输入完整内容")
                return;
            }
            request({
                method:'post',
                url:'/flightsAndSchedules/findAllByStart_dateAndStart_cityAndEnd_city',
                headers:{
                    'token':localStorage.getItem('tickets_token'),
                    'user_type':localStorage.getItem('tickets_user_type')
                },
                data:{
                    start_date:this.start_date,
                    start_city:this.start_city,
                    end_city:this.end_city
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
            if(this.isDirect)return;
            request({
                method:'post',
                url:'/flightsAndSchedules/findTransitByStart_dateAndStart_cityAndEnd_city',
                headers:{
                    'token':localStorage.getItem('tickets_token'),
                    'user_type':localStorage.getItem('tickets_user_type')
                },
                data:{
                    start_date:this.start_date,
                    start_city:this.start_city,
                    end_city:this.end_city
                }
            })
            .then(res=>{
                if(res.data==null){
                    this.$message.error('出错了！')
                }else if(res.data.success==false){
                    this.$message.error(res.data.errorMsg)
                }else{
                    this.TransitArray=res.data.data
                }
            })
        },
        clickGoto(){
            this.isClickSignal=true;
            this.TransitArray=[];
            this.findAllByStart_dateAndStart_cityAndEnd_city();
        },
        clickBack(){
            if(this.isSingal)return;
            if(this.start_date==null||this.start_date==''
                ||this.back_date==null||this.back_date==''
                ||this.start_city==null||this.start_city==''
                ||this.end_city==null||this.end_city==''
            ){
                this.$message.error("请输入完整内容")
                return;
            }
            this.isClickSignal=false;
            this.TransitArray=[];
            request({
                method:'post',
                url:'/flightsAndSchedules/findAllByStart_dateAndStart_cityAndEnd_city',
                headers:{
                    'token':localStorage.getItem('tickets_token'),
                    'user_type':localStorage.getItem('tickets_user_type')
                },
                data:{
                    start_date:this.back_date,
                    start_city:this.end_city,
                    end_city:this.start_city
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
        clickSearchButton(){
            this.findAllByStart_dateAndStart_cityAndEnd_city();
        },
        buyTicket(item){
            if(this.dateToYMD(new Date())>item.start_date){
                this.$message.error("请购买日期位于您之后的机票")
                return;
            }
            var arr=new Array();
            arr.push(item);
            router.push({
                name:'userCheckoutView',
                params:{
                    AllData:arr,
                    isDirect:true
                }
            })
        },
        buyTransitTicket(item){
            if(this.dateToYMD(new Date())>item.first.start_date){
                this.$message.error("请购买日期位于您之后的机票")
                return;
            }
            var arr=new Array();
            arr.push(item.first);
            arr.push(item.second);
            router.push({
                name:'userCheckoutView',
                params:{
                    AllData:arr,
                    isDirect:false
                }
            })
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
        }
    },
    mounted(){
        this.start_date=this.$route.params.start_date
        this.start_city=this.$route.params.start_city
        this.back_date=this.$route.params.back_date
        this.end_city=this.$route.params.end_city
        this.isSingal=this.$route.params.isSingal
        this.isDirect=this.$route.params.isDirect
        this.onlyTransit=this.$route.params.onlyTransit
        this.findAllByStart_dateAndStart_cityAndEnd_city();

        document.body.style.overflow = "hidden";
    },
    beforeDestroy() {
        document.body.style.overflow = "visible";
    },
    watch:{
        
    },
    components:{
        
    },
    computed: {
        
    },
    created() {
        
    }

}

</script>

<style scoped>
.demonstration {
  color: var(--el-text-color-secondary);
}

.el-carousel__item h3 {
  color: #475669;
  opacity: 0.75;
  line-height: 150px;
  margin: 0;
  text-align: center;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n + 1) {
  background-color: #d3dce6;
}

</style>