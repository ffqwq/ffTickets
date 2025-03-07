<template>
    <div>
        <div>
            <el-input placeholder="编号" 
                    v-model="queryData.flight_id"
                    style="width: 10%;">
            </el-input>
            <el-input placeholder="航空公司" 
                    v-model="queryData.airline"
                    style="width: 10%;">
            </el-input>
            <el-input placeholder="机型" 
                    v-model="queryData.airtype"
                    style="width: 10%;">
            </el-input>
            <el-select v-model="queryData.start_time" 
                    placeholder="起始起始时间" 
                    style="width: 15%;">
                <el-option label="所有起始时间" v-model="queryData.timeRange.allTime"></el-option>
                <el-option label="凌晨 0~6点" v-model="queryData.timeRange.lingchen"></el-option>
                <el-option label="上午 6~12点" v-model="queryData.timeRange.shangwu"></el-option>
                <el-option label="下午 12~18点" v-model="queryData.timeRange.xiawu"></el-option>
                <el-option label="晚上 18~24点" v-model="queryData.timeRange.wanshang"></el-option>
            </el-select>
            <el-select v-model="queryData.end_time" 
                    placeholder="到达到达时间" 
                    style="width: 15%;">
                <el-option label="所有到达时间" v-model="queryData.timeRange.allTime"></el-option>
                <el-option label="凌晨 0~6点" v-model="queryData.timeRange.lingchen"></el-option>
                <el-option label="上午 6~12点" v-model="queryData.timeRange.shangwu"></el-option>
                <el-option label="下午 12~18点" v-model="queryData.timeRange.xiawu"></el-option>
                <el-option label="晚上 18~24点" v-model="queryData.timeRange.wanshang"></el-option>
            </el-select>
            <el-input placeholder="起始城市" 
                    v-model="queryData.start_city"
                    style="width: 10%;">
            </el-input>
            <el-input placeholder="到达城市" 
                    v-model="queryData.end_city"
                    style="width: 10%;">
            </el-input>
            <el-input placeholder="起始机场" 
                    v-model="queryData.start_airport"
                    style="width: 10%;">
            </el-input>
            <el-input placeholder="到达机场" 
                    v-model="queryData.end_airport"
                    style="width: 10%;">
            </el-input>
            
        </div>

        <div>
            <el-table :data="filtedFlights" style="width: 100%" height="550">
                <el-table-column prop="flight_id" label="航班编号" 
                        :show-overflow-tooltip="true"
                        :sortable="true" >
                </el-table-column>
                <el-table-column prop="airline" label="航空公司" 
                        :show-overflow-tooltip="true"
                        :sortable="true" >
                </el-table-column>
                <el-table-column prop="airtype" label="机型" 
                        :show-overflow-tooltip="true"
                        :sortable="true" >
                </el-table-column>
                <el-table-column prop="start_time" label="起始时间" 
                        :show-overflow-tooltip="true"
                        :sortable="true" >
                </el-table-column>
                <el-table-column prop="end_time" label="到达时间" 
                        :show-overflow-tooltip="true"
                        :sortable="true" >
                </el-table-column>
                <el-table-column prop="start_city" label="起始城市" 
                        :show-overflow-tooltip="true"
                        :sortable="true" >
                </el-table-column>
                <el-table-column prop="end_city" label="到达城市" 
                        :show-overflow-tooltip="true"
                        :sortable="true">
                </el-table-column>
                <el-table-column prop="start_airport" label="起始机场" 
                        :show-overflow-tooltip="true"
                        :sortable="true">
                </el-table-column>
                <el-table-column prop="end_airport" label="到达机场" 
                        :show-overflow-tooltip="true"
                        :sortable="true">
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
            flights:[],
            Response:null,
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
        findAllFlights(){
            request({
                method:'get',
                url:'/flights/findAll',
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
                    this.flights=res.data.data
                }
            })  
        }
    },
    mounted(){
        // this.findAllFlights();
        // console.log(this.$route.params.start_date)
        // console.log(this.$route.params.start_city)
        // console.log(this.$route.params.end_city)
    },
    watch:{
        
    },
    components:{
        
    },
    computed: {
        //表格数据过滤器
        filtedFlights () {
            return this.flights
            .filter((item) => {
                return this.queryData.flight_id == '' 
                        || this.queryData.flight_id == null 
                        || item.flight_id.toLowerCase().indexOf(this.queryData.flight_id.toLowerCase()) != -1
                        // 从item.flight_id中找到this.queryData.flight_id字符串的索引数字，没有为-1
            })
            .filter((item) => {
                return this.queryData.airline == '' 
                        || this.queryData.airline == null 
                        || item.airline.indexOf(this.queryData.airline) != -1
            })
            .filter((item) => {
                return this.queryData.airtype == '' 
                        || this.queryData.airtype == null 
                        || item.airtype.toLowerCase().indexOf(this.queryData.airtype.toLowerCase()) != -1
            })
            .filter((item) => {
                return this.queryData.start_city == '' 
                        || this.queryData.start_city == null 
                        || item.start_city.indexOf(this.queryData.start_city) != -1
            })
            .filter((item) => {
                return this.queryData.end_city == '' 
                        || this.queryData.end_city == null 
                        || item.end_city.indexOf(this.queryData.end_city) != -1
            })
            .filter((item) => {
                return this.queryData.start_airport == '' 
                        || this.queryData.start_airport == null 
                        || item.start_airport.indexOf(this.queryData.start_airport) != -1
            })
            .filter((item) => {
                return this.queryData.end_airport == '' 
                        || this.queryData.end_airport == null 
                        || item.end_airport.indexOf(this.queryData.end_airport) != -1
            })
            .filter((item) => {
                var isTrue = this.queryData.start_time == '' 
                            || this.queryData.start_time == null ;
                if(isTrue)return true;
                switch(this.queryData.start_time){
                    case "lingchen":{
                        //00:00:00 - 05:59:59
                        return item.start_time.match(/^(0[0-5]):([0-5][0-9]):([0-5][0-9])$/) != null
                    };
                    case "shangwu":{
                        //06:00:00 - 11:59:59
                        return item.start_time.match(/^(0[6-9]|1[0-1]):([0-5][0-9]):([0-5][0-9])$/);
                    };
                    case "xiawu":{
                        //12:00:00 - 17:59:59
                        return item.start_time.match(/^(1[2-7]):([0-5][0-9]):([0-5][0-9])$/);
                    };
                    case "wanshang":{
                        //18:00:00 - 23:59:59
                        return item.start_time.match(/^(1[8-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$/);
                    }
                }
                return true;
            })
            .filter((item) => {
                var isTrue = this.queryData.end_time == '' 
                            || this.queryData.end_time == null ;
                if(isTrue)return true;
                switch(this.queryData.end_time){
                    case "lingchen":{
                        //00:00:00 - 05:59:59
                        return item.end_time.match(/^(0[0-5]):([0-5][0-9]):([0-5][0-9])$/) != null
                    };
                    case "shangwu":{
                        //06:00:00 - 11:59:59
                        return item.end_time.match(/^(0[6-9]|1[0-1]):([0-5][0-9]):([0-5][0-9])$/);
                    };
                    case "xiawu":{
                        //12:00:00 - 17:59:59
                        return item.end_time.match(/^(1[2-7]):([0-5][0-9]):([0-5][0-9])$/);
                    };
                    case "wanshang":{
                        //18:00:00 - 23:59:59
                        return item.end_time.match(/^(1[8-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$/);
                    }
                }
                return true;
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