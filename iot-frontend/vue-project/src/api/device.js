import request from '@/utils/request.js'

export const deviceListService = ()=>{
    return request.get('/device');
}