<template>
    <div>
        <!-- <el-row>
            <el-button>默认按钮</el-button>
            <el-button type="primary">主要按钮</el-button>
            <el-button type="success">成功按钮</el-button>
            <el-button type="info">信息按钮</el-button>
            <el-button type="warning">警告按钮</el-button>
            <el-button type="danger">危险按钮</el-button>
        </el-row> -->
        <el-tooltip effect="light" content="点击查看退票规则" placement="right-end">
            <el-card class="cursorHand" @click.native="lookRefundRule" shadow="hover" style="width: 150px;">
                用户退票
            </el-card>
        </el-tooltip>
        <br><br>
        <div>
            <el-input placeholder="订单编号" 
                    v-model="queryData.order_id"
                    style="width: 15%;">
            </el-input>
            <el-input placeholder="乘客身份证号" 
                    v-model="queryData.passenger_id"
                    style="width: 15%;">
            </el-input>
            <el-input placeholder="航班编号" 
                    v-model="queryData.flight_id"
                    style="width: 15%;">
            </el-input>
            <el-date-picker
                v-model="queryData.start_date"
                type="date"
                placeholder="起始日期"
                format="yyyy-MM-dd"
                value-format="yyyy-MM-dd"
                style="width: 15%;">
            </el-date-picker>
            <el-input placeholder="价格" 
                    v-model="queryData.amount"
                    style="width: 10%;">
            </el-input>
            <el-date-picker
                placeholder="提交时间" 
                v-model="queryData.update_time"
                type="datetimerange"
                :picker-options="pickerOptions"
                format="yyyy-MM-dd HH:mm:ss"
                value-format="yyyy-MM-dd HH:mm:ss"
                range-separator="至"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
                style="width: 25%;">
            </el-date-picker>
        </div>

        <div>
            <el-table :data="filtedOrders" style="width: 100%" height="550">
                <el-table-column prop="order_id" label="订单编号" 
                        :show-overflow-tooltip="true"
                        :sortable="true" >
                </el-table-column>
                <el-table-column prop="passenger_id" label="身份证号" 
                        :show-overflow-tooltip="true"
                        :sortable="true" >
                </el-table-column>
                <el-table-column prop="flight_id" label="航班编号" 
                        :show-overflow-tooltip="true"
                        :sortable="true" >
                </el-table-column>
                <el-table-column prop="start_date" label="起始日期" 
                        :show-overflow-tooltip="true"
                        :sortable="true" >
                </el-table-column>
                <el-table-column prop="update_time" label="提交时间" 
                        :show-overflow-tooltip="true"
                        :sortable="true">
                </el-table-column>
                <el-table-column prop="amount" label="价格" 
                        :show-overflow-tooltip="true"
                        :sortable="true" >
                </el-table-column>
                <el-table-column prop="operate" label="操作" > 
                    <!-- 固定用法，读取表格元素时使用scope -->
                    <template slot-scope="scope">
                        <el-button 
                            v-if="filtedOrders[scope.$index].start_date > dateToYMD(new Date())" 
                            @click.native.prevent="refundTickets(filtedOrders[scope.$index].order_id)">
                            退票
                        </el-button>
                        <span v-else>
                            已过期
                        </span>
                    </template>
                </el-table-column>
            </el-table>
        </div>  
    </div>
</template>
<script>
import request from '@/utils/request';
export default{
    data(){
        return{
            orders:[],
            Response:null,
            queryData:{
                order_id:'',
                user_id:'',
                passenger_id:'',
                flight_id:'',
                start_date:'',
                amount:'',
                update_time:''
            },
            pickerOptions: {
                shortcuts: [
                    {
                        text: '最近一周',
                        onClick(picker) {
                            const end = new Date();
                            const start = new Date();
                            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
                            picker.$emit('pick', [start, end]);
                        }
                    },
                    {
                        text: '最近一个月',
                        onClick(picker) {
                            const end = new Date();
                            const start = new Date();
                            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
                            picker.$emit('pick', [start, end]);
                        }
                    }, 
                    {
                        text: '最近三个月',
                        onClick(picker) {
                            const end = new Date();
                            const start = new Date();
                            start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
                            picker.$emit('pick', [start, end]);
                        }
                    }
                ]
            },
        }
    },
    methods:{
        findAllOrders(){
            request({
                method:'get',
                url:'/orders/findAll',
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
                    this.orders=res.data.data
                }
            })  
        },
        refundTickets(order_idd){
            this.$confirm('确定要退票吗？退票存在手续费', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            })
            .then(()=>{
                request({
                    method:'post',
                    url:'/orders/refundTicket',
                    headers:{
                        'token':localStorage.getItem('tickets_token'),
                        'user_type':localStorage.getItem('tickets_user_type')
                    },
                    data:{
                        order_id:order_idd
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
                            type:'success',
                            onClose:()=>{
                                location.reload();
                            }
                        })
                        
                    }
                })  
            })
            .catch(()=>{

            })
        },
        
        lookRefundRule(){
            var rule='<table border="0" style="width:80%;font-size:16px">'+
                '<tr>'+
                    '<td>退票时间</td>'+
                    '<td>手续费</td>'+
                '</tr>'+
                '<tr>'+
                    '<td></td>'+
                    '<td></td>'+
                '</tr>'+
                '<tr>'+
                    '<td>起飞前14天及以前</td>'+
                    '<td>40%</td>'+
                '</tr>'+
                '<tr>'+
                    '<td>2天（含）至14天（不含）</td>'+
                    '<td>60%</td>'+
                '</tr>'+
                '<tr>'+
                    '<td>4小时（含）至2天（不含）</td>'+
                    '<td>90%</td>'+
                '</tr>'+
                '<tr>'+
                    '<td>4小时以内</td>'+
                    '<td>100%</td>'+
                '</tr>'+
            '</table>';
            
            this.$alert(rule, '退票规则', {
                confirmButtonText: '确定',
                center: true,
                dangerouslyUseHTMLString: true,
                callback: action => {}
            });
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
        this.findAllOrders();
    },
    watch:{
        
    },
    components:{   
        
    },
    computed: {
        //表格数据过滤器
        filtedOrders () {
            return this.orders
            .filter((item) => {
                return item.is_pay == true
            })
            .filter((item) => {
                return this.queryData.order_id == '' 
                        || this.queryData.order_id == null 
                        || item.order_id.toLowerCase().indexOf(this.queryData.order_id.toLowerCase()) != -1
                        // 从item.order_id中找到this.queryData.order_id字符串的索引数字，没有为-1
            })
            .filter((item) => {
                return this.queryData.passenger_id == '' 
                        || this.queryData.passenger_id == null 
                        || item.passenger_id.toLowerCase().indexOf(this.queryData.passenger_id.toLowerCase()) != -1
            })
            .filter((item) => {
                return this.queryData.flight_id == '' 
                        || this.queryData.flight_id == null 
                        || item.flight_id.toLowerCase().indexOf(this.queryData.flight_id.toLowerCase()) != -1
            })
            .filter((item) => {
                return this.queryData.start_date == '' 
                        || this.queryData.start_date == null 
                        || this.queryData.start_date == item.start_date
            })
            .filter((item) => {
                return this.queryData.amount == '' 
                        || this.queryData.amount == null 
                        || this.queryData.amount == item.amount
            })
            .filter((item) => {
                return this.queryData.update_time == '' 
                        || this.queryData.update_time == null 
                        || 
                        (
                            item.update_time >= this.queryData.update_time[0] 
                            &&
                            item.update_time <= this.queryData.update_time[1]
                        )
            })
        }
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

