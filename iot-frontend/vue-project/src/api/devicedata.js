import request from '@/utils/request.js'

export const deviceDataListService = (deviceId)=>{
    return request.get('/devicedata?deviceId='+deviceId);
}

export const deviceDataCountService = ()=>{
    return request.get('/devicedata/count');
}

export const deviceDataListAllService = ()=>{
    return request.get('/devicedata/listall');
}