<template>
    <div>
        <div>
            <el-input placeholder="用户编号" 
                    v-model="queryData.user_id"
                    style="width: 25%;">
            </el-input>
            <el-input placeholder="用户名" 
                    v-model="queryData.user_name"
                    style="width: 25%;">
            </el-input>
            <el-input placeholder="余额" 
                    v-model="queryData.balance"
                    style="width: 25%;">
            </el-input>
            <el-select v-model="queryData.type" placeholder="全部用户" style="width: 25%;">
                <el-option label="全部用户" v-model="userType.all"></el-option>
                <el-option label="普通用户" v-model="userType.user"></el-option>
                <el-option label="管理员" v-model="userType.admin"></el-option>
            </el-select>
        </div>
        <div>
            <el-table :data="filtedUsers" style="width: 100%" height="550">
                <el-table-column prop="user_id" label="用户编号" 
                        :show-overflow-tooltip="true"
                        :sortable="true" >
                </el-table-column>
                <el-table-column prop="user_name" label="用户名" 
                        :show-overflow-tooltip="true"
                        :sortable="true" >
                </el-table-column>
                <el-table-column prop="balance" label="用户余额" 
                        :show-overflow-tooltip="true"
                        :sortable="true" >
                </el-table-column>
                <el-table-column prop="type" label="用户类型" 
                        :show-overflow-tooltip="true"
                        :sortable="true" >
                    <template slot-scope="scope">
                        {{scope.row.type == 'user' ? "普通用户":"管理员"}}
                    </template>
                </el-table-column>
                <el-table-column prop="editAndSave" label="编辑" > 
                    <!-- 固定用法，读取表格元素时使用scope -->
                    <template slot-scope="scope">
                        <el-button @click.native.prevent="editAndSaveUser(scope.$index,filtedUsers)">编辑</el-button>
                    </template>
                </el-table-column>
                <el-table-column prop="delete" label="删除" > 
                    <!-- 固定用法，读取表格元素时使用scope -->
                    <template slot-scope="scope">
                        <el-button @click.native.prevent="deleteUser(filtedUsers[scope.$index].user_id)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>
        <div>
            <el-button @click="insertUser">添加用户</el-button>
        </div>
        <div>
            {{ Response }}
        </div>
        <div>
            <users-dialog ref='usersDialog'></users-dialog>
        </div>
    </div>
</template>
<script>
import request from '@/utils/request';
import usersDialog from '@/components/admin/dialogs/usersDialog.vue';
export default{
    data(){
        return{
            users:[],
            Response:null,
            queryData:{
                user_id:'',
                user_name:'',
                balance:'',
                type:''
            },
            userType:{
                all:'',
                user:'user',
                admin:'admin'
            }
        }
    },
    methods:{
        findAllUsers(){
            request({
                method:'get',
                url:'/users/findAll',
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
                    this.users=res.data.data
                }
            })  
        },
        editAndSaveUser(index,users){
            this.$refs.usersDialog.dialogVisible=true;
            this.$refs.usersDialog.insertOrEdit = "edit";
            this.$refs.usersDialog.oldUser_id=users[index].user_id;
            this.$refs.usersDialog.dialogData=users[index];
        },
        insertUser(){
            this.$refs.usersDialog.dialogVisible=true;
            this.$refs.usersDialog.dialogData={};
            this.$refs.usersDialog.insertOrEdit = "insert";
        },
        deleteUser(user_id){

            this.$confirm('确定要删除此用户吗？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            })
            .then(() => {
                request({
                    method:'delete',
                    url:'/users/deleteUser?user_id='+user_id,
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
        this.findAllUsers();
    },
    watch:{
        
    },
    components:{
        usersDialog
    },
    computed: {
        //表格数据过滤器
        filtedUsers () {
            return this.users
            .filter((item) => {
                return this.queryData.user_id == '' 
                        || this.queryData.user_id == null 
                        || item.user_id.toLowerCase().indexOf(this.queryData.user_id.toLowerCase()) != -1
            })
            .filter((item) => {
                return this.queryData.user_name == '' 
                        || this.queryData.user_name == null 
                        || item.user_name.indexOf(this.queryData.user_name) != -1
            })
            .filter((item) => {
                return this.queryData.balance == '' 
                        || this.queryData.balance == null 
                        || item.balance == this.queryData.balance
            })
            .filter((item) => {
                return this.queryData.type == '' 
                        || this.queryData.type == null 
                        || item.type == this.queryData.type
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

