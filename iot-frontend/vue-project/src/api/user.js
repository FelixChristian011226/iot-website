import request from '@/utils/request.js'

export const userRegisterService = (registerData)=>{
    const params = new URLSearchParams()
    for(let key in registerData){
        params.append(key,registerData[key]);
    }
    return request.post('/user/register',params);
}

export const userLoginService = (loginData)=>{
    const params = new URLSearchParams()
    for(let key in loginData){
        params.append(key,loginData[key]);
    }
    return request.post('/user/login',params);
}

export const userInfoService = ()=>{
    return request.get('/user/information');
}

export const userInfoUpdateService = (userInfoData)=>{
    return request.put('/user/update',userInfoData);
}

export const userAvarterUpdateService = (avatarUrl)=>{
    const params = new URLSearchParams();
    params.append('avatarUrl',avatarUrl);
    return request.patch('/user/updateAvatar',params);
}

export const userPasswordUpdateService = (passwordData)=>{
    return request.patch('/user/updatePassword',{
        old_password: passwordData.oldPassword,
        new_password: passwordData.newPassword,
        cfm_password: passwordData.cfmPassword
    });
}