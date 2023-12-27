import {createRouter,createWebHistory} from 'vue-router'
import LoginVue from '@/views/Login.vue'
import HomeVue from '@/views/Home.vue'
import DeviceConfigurationVue from '@/views/device/DeviceConfiguration.vue'
import DeviceStatisticsVue from '@/views/device/DeviceStatistics.vue'
import UserAvatarVue from '@/views/user/UserAvatar.vue'
import UserInfoVue from '@/views/user/UserInfo.vue'
import UserResetPasswordVue from '@/views/user/UserResetPassword.vue'

const routes = [
    {path:'/login',component:LoginVue},
    {path:'/home',component:HomeVue,redirect:'/user/information',children:[
        {path:'/device/configuration',component:DeviceConfigurationVue},
        {path:'/device/statistics',component:DeviceStatisticsVue},
        {path:'/user/avatar',component:UserAvatarVue},
        {path:'/user/information',component:UserInfoVue},
        {path:'/user/resetPassword',component:UserResetPasswordVue}
    ]}
]

const router = createRouter({
    history:createWebHistory(),
    routes:routes
})

export default router