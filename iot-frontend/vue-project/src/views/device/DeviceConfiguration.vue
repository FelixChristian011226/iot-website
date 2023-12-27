<script setup>
import {
    Edit,
    Delete
} from '@element-plus/icons-vue'
import { ref } from 'vue'

const devices = ref();
import {deviceListService} from '@/api/device.js' 
const deviceList = async()=>{
    let result = await deviceListService();
    devices.value = result.data;
}
deviceList();
</script>
<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span>设备配置</span>
                <div class="extra">
                    <el-button type="primary">添加设备</el-button>
                </div>
            </div>
        </template>
        <el-table :data="devices" style="width: 100%">
            <el-table-column label="序号" width="100" type="index"> </el-table-column>
            <el-table-column label="设备名称" prop="title"></el-table-column>
            <el-table-column label="设备类型" prop="category"></el-table-column>
            <el-table-column label="操作" width="100">
                <template #default="{ row }">
                    <el-button :icon="Edit" circle plain type="primary" ></el-button>
                    <el-button :icon="Delete" circle plain type="danger"></el-button>
                </template>
            </el-table-column>
            <template #empty>
                <el-empty description="没有数据" />
            </template>
        </el-table>
    </el-card>
</template>

<style lang="scss" scoped>
.page-container {
    min-height: 100%;
    box-sizing: border-box;

    .header {
        display: flex;
        align-items: center;
        justify-content: space-between;
    }
}
</style>