<template>
    <div>
        <div>
            <el-input placeholder="乘客编号" 
                    v-model="queryData.passenger_id"
                    style="width: 30%;">
            </el-input>
            <el-input placeholder="乘客名" 
                    v-model="queryData.passenger_name"
                    style="width: 30%;">
            </el-input>
            <el-input placeholder="乘客所绑定用户编号" 
                    v-model="queryData.user_id"
                    style="width: 30%;">
            </el-input>
        </div>
        <div>
            <el-table :data="filtedPassengers" style="width: 100%" height="550">
                <el-table-column prop="passenger_id" label="乘客编号" 
                        :show-overflow-tooltip="true"
                        :sortable="true" >
                </el-table-column>
                <el-table-column prop="passenger_name" label="乘客名" 
                        :show-overflow-tooltip="true"
                        :sortable="true" >
                </el-table-column>
                <el-table-column prop="user_id" label="乘客所绑定用户编号" 
                        :show-overflow-tooltip="true"
                        :sortable="true" >
                </el-table-column>
                <el-table-column prop="editAndSave" label="编辑" > 
                    <!-- 固定用法，读取表格元素时使用scope -->
                    <template slot-scope="scope">
                        <el-button @click.native.prevent="editAndSavePassenger(scope.$index,filtedPassengers)">编辑</el-button>
                    </template>
                </el-table-column>

                <el-table-column prop="delete" label="删除" > 
                    <!-- 固定用法，读取表格元素时使用scope -->
                    <template slot-scope="scope">
                        <el-button @click.native.prevent="deletePassenger(filtedPassengers[scope.$index].passenger_id)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>
        <div>
            <el-button @click="insertPassenger">添加乘客</el-button>
        </div>
        <div>
            <passengers-dialog ref='passengersDialog'></passengers-dialog>
        </div>
    </div>
</template>
<script>
import request from '@/utils/request';
import passengersDialog from '@/components/admin/dialogs/passengersDialog.vue';
export default{
    data(){
        return{
            passengers:[],
            queryData:{
                passenger_id:'',
                passenger_name:'',
                user_id:''
            }
        }
    },
    methods:{
        findAllPassengers(){
            request({
                method:'get',
                url:'/passengers/findAll',
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
        editAndSavePassenger(index,passengers){
            this.$refs.passengersDialog.dialogVisible=true;
            this.$refs.passengersDialog.insertOrEdit = "edit";
            this.$refs.passengersDialog.oldPassenger_id=passengers[index].passenger_id;
            this.$refs.passengersDialog.dialogData=passengers[index];
        },
        insertPassenger(){
            this.$refs.passengersDialog.dialogVisible=true;
            this.$refs.passengersDialog.dialogData={};
            this.$refs.passengersDialog.insertOrEdit = "insert";
        },
        deletePassenger(passenger_id){

            this.$confirm('确定要删除此用户吗？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            })
            .then(() => {
                request({
                    method:'delete',
                    url:'/passengers/deletePassenger?passenger_id='+passenger_id,
                    headers:{
                        'token':localStorage.getItem('tickets_token'),
                        'user_type':localStorage.getItem('tickets_user_type')
                    }
                })
                .then(
                    res=>{
                        if(res.data==null){
                            this.$message.error('出错了！')
                        }else if(res.data.success==false){
                            this.$message.error(res.data.errorMsg)
                        }else{
                            this.$message({
                                message:'删除成功',
                                type:'success'
                            })
                        }
                    }
                )    
            })
            .catch(() => {
                
            })
        }
    },
    mounted(){
        this.findAllPassengers();
    },
    watch:{
        
    },
    components:{
        passengersDialog
    },
    computed: {
        //表格数据过滤器
        filtedPassengers () {
            return this.passengers
            .filter((item) => {
                return this.queryData.passenger_id == '' 
                        || this.queryData.passenger_id == null 
                        || item.passenger_id.toLowerCase().indexOf(this.queryData.passenger_id.toLowerCase()) != -1
            })
            .filter((item) => {
                return this.queryData.passenger_name == '' 
                        || this.queryData.passenger_name == null 
                        || item.passenger_name.indexOf(this.queryData.passenger_name) != -1
            })
            .filter((item) => {
                return this.queryData.user_id == '' 
                        || this.queryData.user_id == null 
                        || item.user_id.indexOf(this.queryData.user_id) != -1
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


