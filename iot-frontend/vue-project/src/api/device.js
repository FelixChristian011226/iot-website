import request from '@/utils/request.js'

export const deviceListService = ()=>{
    return request.get('/device');
}

export const deviceAddService = (deviceData)=>{
    return request.post('/device',deviceData);
}

export const deviceUpdateService = (deviceData)=>{
    return request.put('/device',deviceData);
}

export const deviceDeleteService = (deviceId)=>{
    return request.delete('/device?deviceId='+deviceId);
}

export const deviceUpdateConnectService = (deviceId,connect)=>{
    return request.put('/device/connect?deviceId='+deviceId+'&connect='+connect);
}