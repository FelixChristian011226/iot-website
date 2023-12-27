export default {
    getTimeState(){
        // 获取当前时间
        let timeNow = new Date();
        // 获取当前小时
        let hours = timeNow.getHours();
        // 设置默认文字
        let state= ``;
        // 判断当前时间段
        if (hours >= 0 && hours <= 10) {
            state = `早上好!`;
        } else if (hours > 10 && hours <= 14) {
            state= `中午好!`;
        } else if (hours > 14 && hours <= 18) {
            state= `下午好!`;
        } else if (hours > 18 && hours <= 24) {
            state= `晚上好!`;
        }
        return state;
    },
    
}