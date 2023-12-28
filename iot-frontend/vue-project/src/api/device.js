import request from '@/utils/request.js'

export const deviceListService = ()=>{
    return request.get('/device');
}

export const deviceAddService = (deviceData)=>{
    return request.post('/device',deviceData);
}