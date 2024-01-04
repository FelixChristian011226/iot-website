import request from '@/utils/request.js'

export const deviceDataListService = (deviceId)=>{
    return request.get('/devicedata?deviceId='+deviceId);
}