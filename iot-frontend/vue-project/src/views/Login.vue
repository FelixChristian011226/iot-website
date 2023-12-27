<script setup>
import { User, Lock } from '@element-plus/icons-vue'
import { ref } from 'vue'
import swal from 'sweetalert';
//LOGIN/REGISTER SWITCH
const isRegister = ref(false)
//DATA MODEL
const registerData = ref({
    username:'',
    password:'',
    rePassword:''
})
const loginData = ref({
    username:'',
    password:''
})
//VALIDATE PASSWORD
const checkRePassword = (rule,value,callback)=>{
    if (value==='') {
        callback(new Error('请再次输入密码'))
    }else if(value!==registerData.value.password){
        callback(new Error('两次输入密码不一致'))
    }else{
        callback()
    }
}
//FORM VALIDATION RULES
const registerRules = {
    username:[
        {required:true,message:'请输入用户名',trigger:'blur'},
        {min:6,max:16 ,message:'长度为6-16位非空字符',trigger:'blur'}
    ],
    password:[
        {required:true,message:'请输入密码',trigger:'blur'},
        {min:6,max:16 ,message:'长度为6-16位非空字符',trigger:'blur'}
    ],
    rePassword:[
        {validator:checkRePassword,trigger:'blur'}
    ]    
}
const loginRules = {
    username:[
        {required:true,message:'请输入用户名',trigger:'blur'}
    ],
    password:[
        {required:true,message:'请输入密码',trigger:'blur'}
    ]
}
//CALL BACKEND API FOR REGISTER
import {userRegisterService,userLoginService} from '@/api/user.js'
//REGISTER FUNCTION
const register = async()=>{
    if(!registerData.value.username||!registerData.value.password||!registerData.value.rePassword){
        // alert('请填写完整信息')
        swal({
            title: "注册失败",
            text: "请填写完整信息",
            icon: "error",
        });
        return
    }
    if(registerData.value.password!==registerData.value.rePassword){
        // alert('请输入一致的密码')
        swal({
            title: "注册失败",
            text: "请输入一致的密码",
            icon: "error",
        });
        return
    }
    if (registerData.value.username.length<6||registerData.value.username.length>16) {
        // alert('用户名长度为6-16位')
        swal({
            title: "注册失败",
            text: "用户名长度为6-16位",
            icon: "error",
        });
        return
    }
    if (registerData.value.password.length<6||registerData.value.password.length>16) {
        // alert('密码长度为6-16位')
        swal({
            title: "注册失败",
            text: "密码长度为6-16位",
            icon: "error",
        });
        return
    }
    let result = await userRegisterService(registerData.value)
    console.log(registerData.value)
    console.log(result.code)
    if(result.code===0){
        // alert('注册成功')
        swal({
            title: "注册成功",
            icon: "success",
        }).then(()=>{
            isRegister.value = false
        });
    }else{
        // alert('注册失败:'+result.message)
        swal({
            title: "注册失败",
            text: result.message,
            icon: "error",
        });
    }
}
//LOGIN FUNCTION
import {useTokenStore} from '@/stores/token.js'
import {useRouter} from 'vue-router'
const router = useRouter()
const tokenStore = useTokenStore()
const login = async()=>{
    if(!loginData.value.username||!loginData.value.password){
        return
    }
    let result = await userLoginService(loginData.value);
    if(result.code===0){
        swal({
            title: "登录成功",
            icon: "success",
        }).then(()=>{
            //JUMP TO DASHBOARD
            tokenStore.setToken(result.data)
            router.push('/home')
        });        
    }else{
        swal({
            title: "登录失败",
            text: result.message,
            icon: "error",
        });
    }
}
</script>

<template>
    <el-row class="login-page">
        <el-col :span="12" class="bg"></el-col>
        <el-col :span="6" :offset="3" class="form">
            <!-- REGISTER FORM -->
            <el-form ref="form" size="large" autocomplete="off" v-if="isRegister" :model="registerData" :rules="registerRules">
                <el-form-item>
                    <h1>注册</h1>
                </el-form-item>
                <el-form-item prop="username">
                    <el-input :prefix-icon="User" placeholder="请输入用户名" v-model="registerData.username"></el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input :prefix-icon="Lock" type="password" placeholder="请输入密码" v-model="registerData.password"></el-input>
                </el-form-item>
                <el-form-item prop="rePassword">
                    <el-input :prefix-icon="Lock" type="password" placeholder="请再次输入密码" v-model="registerData.rePassword"></el-input>
                </el-form-item>
                <!-- REGISTER BUTTON -->
                <el-form-item>
                    <el-button class="button" type="primary" auto-insert-space @click="register">
                        注册
                    </el-button>
                </el-form-item>
                <el-form-item class="flex">
                    <el-link type="info" :underline="false" @click="isRegister = false">
                        ← 返回
                    </el-link>
                </el-form-item>
            </el-form>
            <!-- LOGIN FORM -->
            <el-form ref="form" size="large" autocomplete="off" v-else :model="loginData" :rules="loginRules">
                <el-form-item>
                    <h1>登录</h1>
                </el-form-item>
                <el-form-item prop="username">
                    <el-input :prefix-icon="User" placeholder="请输入用户名" v-model="loginData.username"></el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input name="password" :prefix-icon="Lock" type="password" placeholder="请输入密码" v-model="loginData.password"></el-input>
                </el-form-item>
                <el-form-item class="flex">
                    <div class="flex">
                        <el-checkbox>记住我</el-checkbox>
                        <el-link type="primary" :underline="false">忘记密码？</el-link>
                    </div>
                </el-form-item>
                <!-- Login Button -->
                <el-form-item>
                    <el-button class="button" type="primary" auto-insert-space @click="login">登录</el-button>
                </el-form-item>
                <el-form-item class="flex">
                    <el-link type="info" :underline="false" @click="isRegister = true">
                        注册 →
                    </el-link>
                </el-form-item>
            </el-form>
        </el-col>
    </el-row>
</template>

<style lang="scss" scoped>
/* 样式 */
.login-page {
    height: 100vh;
    background-color: #fff;

    .bg {
        background: url('@/assets/logo2.png') no-repeat 60% center / 240px auto,
            url('@/assets/login_bg.jpg') no-repeat center / cover;
        border-radius: 0 20px 20px 0;
    }

    .form {
        display: flex;
        flex-direction: column;
        justify-content: center;
        user-select: none;

        .title {
            margin: 0 auto;
        }

        .button {
            width: 100%;
        }

        .flex {
            width: 100%;
            display: flex;
            justify-content: space-between;
        }
    }
}
</style>