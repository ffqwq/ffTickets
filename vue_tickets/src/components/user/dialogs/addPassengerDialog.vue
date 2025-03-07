<template>
    <el-dialog title="请输入" 
                :visible.sync="dialogVisible" 
                @close="closeDialog" 
                width="35%"
                >
        <el-form :label-position="'left'" label-width="20%" :model="passengerData">
            <el-form-item label="乘客名" >
                <el-input v-model="passengerData.passenger_name" ></el-input>
            </el-form-item>
            <el-form-item label="身份证号" >
                <el-input v-model="passengerData.passenger_id" ></el-input>
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
            dialogVisible:false,
            passengerData:{
                passenger_name:'',
                passenger_id:''
            },
        }
    },
    methods:{
        closeDialog(){
            this.dialogVisible=false;
        },
        saveDialog(){
            request({
                method:'post',
                url:'/passengers/insertPassenger',
                headers:{
                    'token':localStorage.getItem('tickets_token'),
                    'user_type':localStorage.getItem('tickets_user_type')
                },
                data:{
                    passenger_id:this.passengerData.passenger_id,
                    passenger_name:this.passengerData.passenger_name,
                    user_id:localStorage.getItem("tickets_user_id")
                }
            })
            .then(res=>{
                if(res.data==null){
                    this.$message.error('出错了！')
                }else if(res.data.success==false){
                    this.$message.error(res.data.errorMsg)
                }else{
                    this.$message({
                        message:'添加成功',
                        type:'success'
                    })
                }
            })

            this.dialogVisible=false;
        }
    }
}
</script>
<style>
</style>