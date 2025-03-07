<template>
    <div>
        <div>
            <el-input placeholder="编号" v-model="queryData.flight_id" style="width: 20%;"></el-input>
            <el-date-picker 
                    v-model="queryData.start_date" 
                    value-format="yyyy-MM-dd"
                    type="date"
                    placeholder="起始时间"
                    style="width: 20%;">
            </el-date-picker>
            <!-- .number用于将input的String转成数值类型 -->
            <el-input v-model.number="queryData.nums" 
                type="number"
                placeholder="票数"
                :min="0"
                oninput="value=value.replace(/[^\d]/g,'')"
                style="width: 20%;">
                <!-- //正整数 -->
            </el-input >

            <el-select v-model="queryData.price" placeholder="所有价格" style="width: 20%;">
                <el-option label="所有价格" v-model="pric.all"></el-option>
                <el-option v-for="(item,index) in pric.arrs"
                        :key="index"
                        v-model="item.value"
                        :label="item.label">
                </el-option>
            </el-select>

            <el-select v-model="queryData.punctuality" placeholder="是否准点" style="width: 20%;">
                <el-option label="准点和晚点" v-model="punc.all"></el-option>
                <el-option label="准点" v-model="punc.yes"></el-option>
                <el-option label="晚点" v-model="punc.no"></el-option>
            </el-select>

            
        </div>
        <div>
            <el-table :data="filtedSchedules" style="width: 100%">
                <el-table-column prop="flight_id" label="航班编号" 
                        :show-overflow-tooltip="true"
                        :sortable="true">

                </el-table-column>
                <el-table-column prop="start_date" label="起始时间" 
                        :show-overflow-tooltip="true"
                        :sortable="true">

                </el-table-column>
                <el-table-column prop="nums" label="剩余票数" 
                        :show-overflow-tooltip="true"
                        :sortable="true">

                </el-table-column>
                <el-table-column prop="price" label="价格" 
                        :show-overflow-tooltip="true"
                        :sortable="true">

                </el-table-column>
                <el-table-column prop="punctuality" label="是否准点" :show-overflow-tooltip="true">
                    <template slot-scope="scope">
                        {{scope.row.punctuality ? "准点":"晚点"}}
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
            schedules:[],
            Response:null,
            queryData:{
                flight_id:'',
                start_date:'',
                nums:'',
                price:'',
                punctuality:'all',
            },
            pric:{
                all:'',
                arrs:[
                    {value:1,label:'0-500元'},
                    {value:2,label:'500-1000元'},
                    {value:3,label:'1000-1500元'},
                    {value:4,label:'1500-2000元'},
                    {value:5,label:'2000-2500元'},
                    {value:6,label:'2500-3000元'},
                    {value:7,label:'3000元及以上'}
                ]
            },
            punc:{
                yes:true,
                no:false,
                all:'all'
            }
        }
    },
    methods:{
        findAllschedules(){
            request({
                method:'get',
                url:'/schedules/findAll',
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
                    this.schedules=res.data.data
                }
            })  
        }
    },
    mounted(){
        this.findAllschedules();
    },
    watch:{
        
    },
    components:{
        
    },
    computed: {
        filtedSchedules () {
            return this.schedules
            .filter((item) => {
                return this.queryData.flight_id == '' 
                        || this.queryData.flight_id == null 
                        || item.flight_id.toLowerCase().indexOf(this.queryData.flight_id.toLowerCase()) != -1
                        // 从item.flight_id中找到this.queryData.flight_id字符串的索引数字，没有为-1
            })
            .filter((item) => {
                return this.queryData.start_date == ''
                        || this.queryData.start_date == null
                        || this.queryData.start_date == item.start_date
            })
            .filter((item) => {
                return this.queryData.nums == ''
                        || this.queryData.nums == null
                        || this.queryData.nums == item.nums
            })
            .filter((item) => {
                if(this.queryData.price == 7){
                    return item.price >= 3000
                }
                return this.queryData.price == ''
                        || this.queryData.price == 0
                        || this.queryData.price == null
                        || ( item.price >= (this.queryData.price-1)*500 
                                && item.price < (this.queryData.price)*500 )
                        
            })
            .filter((item) => {
                return this.queryData.punctuality == 'all'
                        || item.punctuality == this.queryData.punctuality
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