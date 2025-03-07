<template>
    <div>
        <div>
            <el-input placeholder="消息编号" 
                    v-model="queryData.message_id"
                    style="width: 25%;">
            </el-input>
            <el-input placeholder="用户编号" 
                    v-model="queryData.user_id"
                    style="width: 25%;">
            </el-input>
            <el-input placeholder="消息内容" 
                    v-model="queryData.msg"
                    style="width: 25%;">
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
            <el-table :data="filtedMessages" style="width: 100%" height="550">
                <el-table-column prop="message_id" label="消息编号" 
                        :show-overflow-tooltip="true"
                        :sortable="true" >
                </el-table-column>
                <el-table-column prop="user_id" label="用户编号" 
                        :show-overflow-tooltip="true"
                        :sortable="true" >
                </el-table-column>
                <el-table-column prop="msg" label="消息内容" 
                        :show-overflow-tooltip="true"
                        :sortable="true" >
                </el-table-column>
                <el-table-column prop="update_time" label="提交时间" 
                        :show-overflow-tooltip="true"
                        :sortable="true">
                </el-table-column>
                <el-table-column prop="editAndSave" label="编辑" > 
                    <!-- 固定用法，读取表格元素时使用scope -->
                    <template slot-scope="scope">
                        <el-button @click.native.prevent="editAndSaveMessage(scope.$index,filtedMessages)">编辑</el-button>
                    </template>
                </el-table-column>

                <el-table-column prop="delete" label="删除" > 
                    <!-- 固定用法，读取表格元素时使用scope -->
                    <template slot-scope="scope">
                        <el-button @click.native.prevent="deleteMessage(filtedMessages[scope.$index].message_id)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>
        <div>
            <el-button @click="insertMessage">添加消息</el-button>
        </div>
        <div>
            <messages-dialog ref='messagesDialog'></messages-dialog>
        </div> 
    </div>
</template>
<script>
import request from '@/utils/request';
import messagesDialog from '@/components/admin/dialogs/messagesDialog.vue';
export default{
    data(){
        return{
            messages:[],
            Response:null,
            queryData:{
                message_id:'',
                user_id:'',
                msg:'',
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
        findAllMessages(){
            request({
                method:'get',
                url:'/messages/findAll',
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
                    this.messages=res.data.data
                }
            })  
        },
        editAndSaveMessage(index,messages){
            this.$refs.messagesDialog.dialogVisible=true;
            this.$refs.messagesDialog.insertOrEdit = "edit";
            this.$refs.messagesDialog.oldMessage_id=messages[index].message_id;
            this.$refs.messagesDialog.dialogData=messages[index];
        },
        insertMessage(){
            this.$refs.messagesDialog.dialogVisible=true;
            this.$refs.messagesDialog.dialogData={};
            this.$refs.messagesDialog.insertOrEdit = "insert";
        },
        deleteMessage(message_id){

            this.$confirm('确定要删除此消息吗？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            })
            .then(() => {
                request({
                    method:'delete',
                    url:'/messages/deleteMessage?message_id='+message_id,
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
                            location.reload();
                        }
                    }
                ) 
            })
            .catch(() => {
                
            }) 
        }
    },
    mounted(){
        this.findAllMessages();
    },
    watch:{
        
    },
    components:{
        messagesDialog
    },
    computed: {
        //表格数据过滤器
        filtedMessages () {
            return this.messages
            .filter((item) => {
                return this.queryData.message_id == '' 
                        || this.queryData.message_id == null 
                        || item.message_id==this.queryData.message_id.toLowerCase()
            })
            .filter((item) => {
                return this.queryData.user_id == '' 
                        || this.queryData.user_id == null 
                        || item.user_id.toLowerCase().indexOf(this.queryData.user_id.toLowerCase()) != -1
            })
            .filter((item) => {
                return this.queryData.msg == '' 
                        || this.queryData.msg == null 
                        || item.msg.toLowerCase().indexOf(this.queryData.msg.toLowerCase()) != -1
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


