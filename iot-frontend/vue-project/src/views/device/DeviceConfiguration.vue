<script setup>
import {
    Edit,
    Delete
} from '@element-plus/icons-vue'
import { ref } from 'vue'

//DEVICE LIST
const devices = ref();
import { deviceListService,deviceAddService } from '@/api/device.js' 
import swal from 'sweetalert';
import { ElMessage } from 'element-plus';
const deviceList = async()=>{
    let result = await deviceListService();
    devices.value = result.data;
}
deviceList();

//ADD DEVICE DIALOG
const dialogVisible = ref(false)
const deviceModel = ref({
    title: '',
    category: ''
})
const rules = {
    title: [
        { required: true, message: '请输入设备名称', trigger: 'blur' },
    ],
    category: [
        { required: true, message: '请输入设备类型', trigger: 'blur' },
    ]
}

//DEVICE CATEGORY LIST
const deviceTypes = [
  { label: '摄像头', value: 'camera' },
  { label: '传感器', value: 'sensor' },
  { label: '控制器', value: 'controller' },
];

//ADD DEVICE CANCEL
const cancel = () => {
    dialogVisible.value = false;
    deviceModel.value = { title: '', category: '' };
};

//ADD DEVICE CONFIRM
const addDevice = async()=>{
    let result = await deviceAddService(deviceModel.value);
    ElMessage.success("添加成功");
    deviceList();
    cancel();
}
</script>
<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span>设备配置</span>
                <div class="extra">
                    <el-button type="primary" @click="dialogVisible=true">添加设备</el-button>
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
        <el-dialog v-model="dialogVisible" title="添加设备" width="30%" :before-close="cancel">
            <el-form :model="deviceModel" :rules="rules" label-width="100px" style="padding-right: 30px">
                <el-form-item label="设备名称" prop="title">
                    <el-input v-model="deviceModel.title" minlength="1" maxlength="10"></el-input>
                </el-form-item>
                <el-form-item label="设备类型" prop="category">
                    <el-select v-model="deviceModel.category" placeholder="请选择设备类型">
                    <el-option v-for="type in deviceTypes" :key="type.value" :label="type.label" :value="type.value"></el-option>
                    </el-select>
                </el-form-item>
            </el-form>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="cancel">取消</el-button>
                    <el-button type="primary" @click="addDevice"> 确认 </el-button>
                </span>
            </template>
        </el-dialog>
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

    ::v-deep(.el-dialog) {
        border-radius: 10px;
    }

}
</style>