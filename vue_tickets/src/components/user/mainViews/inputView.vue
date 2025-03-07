<template>
    <div style="width: 80%;margin: auto;">
        <div>
            <el-card class="cursorHand" shadow="always" style="float:left;width: 20%;height: 60px;background-color: #F5F5F5;">
                航班搜索
            </el-card>
            <el-card class="cursorHand" @click.native="clickFandS" shadow="hover" style="float:left;width: 20%;height: 60px;">
                航班动态
            </el-card>
            <el-card class="cursorHand" @click.native="refundAndChange" shadow="hover" style="float:left;width: 20%;height: 60px;">
                退票与改签
            </el-card>
            <el-card class="cursorHand" @click.native="clickOrder" shadow="hover" style="float:left;width: 20%;height: 60px;">
                我的订单
            </el-card>
            <el-card class="cursorHand" shadow="hover" style="float:left;width: 19%;height: 60px;">
                更多服务
            </el-card>
        </div>
        <div>
            <el-card shadow="never" style="width: 100%;height: 300px;">
                <el-row :gutter="20">
                    <el-col :span="8" style="height: 50px;">
                        <div style="margin-top: 5%;">
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
                    <el-col :span="14">
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
                    
                </el-row>
                <el-row type="flex" justify="center">
                    <el-col :span="3">
                        <el-button 
                            type="primary" plain
                            style="font-size: 20px;width: 100%;height: 60%;margin-top: 30%;"
                            @click="clickSearchButton">
                            搜索
                        </el-button>
                    </el-col>
                </el-row>
            </el-card>
        </div>
    </div>
</template>
<script>
import router from '@/router';
export default{
    data(){
        return{
            start_date:'',
            back_date:'',
            start_city:'上海',
            end_city:'北京',
            isSingal:true,
            isDirect:true,
            onlyTransit:false,
        }
    },
    methods:{
        clickSingal(){
            if(this.isSingal=='true')this.isSingal='false'
            else this.isSingal='true'
        },
        clickSearchButton(){
            if(this.start_date==null||this.start_city==null||this.end_city==null
                ||this.start_date==''||this.start_city==''||this.end_city=='')
            {
                this.$message.error('请正确输入')
                return
            }

            router.push({
                name:'userFlightsAndSchedulesView',
                params:{
                    start_date:this.start_date,
                    back_date:this.back_date,
                    start_city:this.start_city,
                    end_city:this.end_city,
                    isSingal:this.isSingal,
                    isDirect:this.isDirect,
                    onlyTransit:this.onlyTransit
                }
            })
        },
        clickFandS(){
            router.push({
                name:'userSchedulesView'
            })
        },
        clickOrder(){
            router.push({
                name:'userOrdersView'
            })
        },
        refundAndChange(){
            router.push({
                name:'userChangeView'
            })
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
    components:{

    },
    created(){
        
    },
    mounted(){
        this.start_date=this.dateToYMD(new Date());
        document.body.style.overflow = "hidden";//阻止整个页面滚动
    },
    beforeDestroy() {
        document.body.style.overflow = "visible";//阻止整个页面滚动
    },
}
</script>
<style>
.el-input .el-input__inner {
  font-size: 1.3em;
  font-weight: bolder;
  padding: 0 4px;
  text-align: center;
}
</style>