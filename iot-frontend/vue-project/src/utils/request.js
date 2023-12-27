import axios from 'axios';

const baseURL = '/api';
const instance = axios.create({baseURL})

import { useTokenStore } from '@/stores/token.js';
import { ElMessage } from 'element-plus';
instance.interceptors.request.use(
    (config)=>{
        const tokenStore = useTokenStore();
        if(tokenStore.token){
            config.headers.Authorization = tokenStore.token
        }
        return config;
    },
    (err)=>{
        Promise.reject(err);
    }
)

// instance.interceptors.response.use(
//     result=>{
//         if(result.data.code===0){
//             return result.data;
//         }
//         swal({
//             title: "操作失败",
//             text: result.data.message,
//             icon: "error",
//         });
//         return Promise.reject(result.data)
//     },
//     err=>{
//         swal({
//             title: "服务异常",
//             icon: "error",
//         });
//         return Promise.reject(err);//异步的状态转化成失败的状态
//     }
// )

import router from '@/router'
instance.interceptors.response.use(
    result=>{
        return result.data;
    },
    err=>{
        if(err.response.status===401){
            ElMessage.error('请先登录')
            router.push('/login')
        }else{
            ElMessage.error('服务异常')
        }
        return Promise.reject(err);//异步的状态转化成失败的状态
    }
)

export default instance;