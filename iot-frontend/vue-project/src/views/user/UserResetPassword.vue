<script setup>
import { ref } from 'vue'
import swal from 'sweetalert';
import { userPasswordUpdateService } from '@/api/user.js';
import useUserInfoStore from '@/stores/userInfo.js'
import { useTokenStore } from '@/stores/token.js'
const tokenStore = useTokenStore();
const userInfoStore = useUserInfoStore();
const userPassword = ref({
    oldPassword:'',
    newPassword:'',
    cfmPassword:''
})
const checkRePassword = (rule,value,callback)=>{
    if (value==='') {
        callback(new Error('请再次输入密码'))
    }else if(value!==userPassword.value.newPassword){
        callback(new Error('两次输入密码不一致'))
    }else{
        callback()
    }
}
const rules = {
    oldPassword: [
        { required: true, message: '请输入旧密码', trigger: 'blur' },
    ],
    newPassword:[
        {required:true,message:'请输入新密码',trigger:'blur'},
        {min:6,max:16 ,message:'密码必须是6-16位的非空字符',trigger:'blur'}
    ],
    cfmPassword:[
        {required:true,message:'请输入确认密码',trigger:'blur'},
        {validator:checkRePassword,trigger:'blur'}
    ]    
}

import router from '@/router';
const updateUserPassword = async()=>{
    if(!userPassword.value.oldPassword||!userPassword.value.newPassword||!userPassword.value.cfmPassword){
        swal({
            title: "修改失败",
            text: "请填写完整信息",
            icon: "error",
        });
        return
    }
    if(userPassword.value.newPassword.length<6||userPassword.value.newPassword.length>16){
        swal({
            title: "修改失败",
            text: "密码必须是6-16位的非空字符",
            icon: "error",
        });
        return
    }
    let result = await userPasswordUpdateService(userPassword.value);
    if(result.code===0)
    {
        swal({
            title: "修改成功",
            icon: "success",
        }).then(()=>{
            tokenStore.removeToken();
            userInfoStore.removeInfo();
            // router.push('/login')
            window.location.reload();
        });
    }else{
        swal({
            title: "修改失败",
            text: result.message,
            icon: "error",
        });
    
    }

}

</script>
<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span>修改密码</span>
            </div>
        </template>
        <el-row>
            <el-col :span="12">
                <el-form :model="userPassword" :rules="rules" label-width="100px" size="large">
                    <el-form-item label="旧密码" prop="oldPassword">
                        <el-input type="password" placeholder="请输入旧密码" v-model="userPassword.oldPassword" show-password></el-input>
                    </el-form-item>
                    <el-form-item label="新密码" prop="newPassword">
                        <el-input type="password" placeholder="请输入新密码" v-model="userPassword.newPassword" show-password></el-input>
                    </el-form-item>
                    <el-form-item label="确认密码" prop="cfmPassword">
                        <el-input type="password" placeholder="请输入确认密码" v-model="userPassword.cfmPassword" show-password></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="updateUserPassword">提交修改</el-button>
                    </el-form-item>
                </el-form>
            </el-col>
        </el-row>
    </el-card>
</template>