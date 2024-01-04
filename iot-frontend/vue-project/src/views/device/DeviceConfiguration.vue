<script setup>
import { Edit, Delete } from "@element-plus/icons-vue";
import { ref } from "vue";

//DEVICE LIST
const devices = ref();
import {
  deviceListService,
  deviceAddService,
  deviceUpdateService,
  deviceDeleteService,
  deviceUpdateConnectService,
} from "@/api/device.js";
import swal from "sweetalert";
import { ElMessage } from "element-plus";
const deviceList = async () => {
  let result = await deviceListService();
  devices.value = result.data;
};
deviceList();

//ADD DEVICE DIALOG
const dialogVisible = ref(false);
const title = ref("添加设备");
const deviceModel = ref({
  deviceId: "",
  title: "",
  category: "",
});
const rules = {
  deviceId: [
    { required: true, message: "请输入设备ID", trigger: "blur" },
    {
      pattern: /^\S{2,10}$/,
      message: "设备ID必须是2-10位的非空字符串",
      trigger: "blur",
    },
  ],
  title: [{ required: true, message: "请输入设备名称", trigger: "blur" }],
  category: [{ required: true, message: "请输入设备类型", trigger: "blur" }],
};

//DEVICE CATEGORY LIST
const deviceTypes = [
  { label: "摄像头", value: "camera" },
  { label: "传感器", value: "sensor" },
  { label: "控制器", value: "controller" },
];

//ADD DEVICE CANCEL
const cancel = () => {
  dialogVisible.value = false;
  deviceModel.value = { deviceId: "", title: "", category: "" };
};

//ADD DEVICE CONFIRM
const addDevice = async () => {
  let result = await deviceAddService(deviceModel.value);
  ElMessage.success("添加成功");
  deviceList();
  cancel();
};

//MODIFY DEVICE DIALOG
const disableIdInput = ref(false);
const modify = (row) => {
  dialogVisible.value = true;
  title.value = "修改设备";
  disableIdInput.value = true;
  deviceModel.value.deviceId = row.deviceId;
  deviceModel.value.title = row.title;
  deviceModel.value.category = row.category;
};

//MODIFY DEVICE CONFIRM
const updateDevice = async () => {
  let result = await deviceUpdateService(deviceModel.value);
  ElMessage.success("修改成功");
  deviceList();
  cancel();
};

//DELETE DEVICE
const deleteDevice = async (row) => {
  swal({
    title: "确定删除该设备吗？",
    text: "删除后将无法恢复该设备！",
    icon: "warning",
    buttons: true,
    dangerMode: true,
  }).then(async (willDelete) => {
    if (willDelete) {
      console.log(row.deviceId);
      let result = await deviceDeleteService(row.deviceId);
      if (result.code === 0) {
        swal({
          title: "删除成功",
          icon: "success",
        }).then(() => {
          deviceList();
        });
      } else {
        swal({
          title: "删除失败",
          text: result.message,
          icon: "error",
        });
      }
    }
  });
};

//UPDATE DEVICE CONNECT
const handleConnectChange = async (row) => {
  let result = await deviceUpdateConnectService(row.deviceId, row.connect);
  if (result.code === 0) {
    ElMessage.success(row.connect ? "设备已连接" : "设备已断开连接");
    deviceList();
  } else {
    ElMessage.error("连接状态更新失败");
    row.connect = !row.connect;
  }
};
</script>
<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <span>设备配置</span>
        <div class="extra">
          <el-button
            type="primary"
            @click="
              dialogVisible = true;
              title = '添加设备';
              disableIdInput = false;
            "
            >添加设备</el-button
          >
        </div>
      </div>
    </template>
    <el-table :data="devices" style="width: 100%">
      <el-table-column label="序号" width="100" type="index"> </el-table-column>
      <el-table-column label="设备ID" prop="deviceId"></el-table-column>
      <el-table-column label="设备名称" prop="title"></el-table-column>
      <el-table-column label="设备类型" prop="category"></el-table-column>
      <el-table-column label="设备状态" prop="connect">
        <template #default="{ row }">
          <el-switch
            v-model="row.connect"
            @change="handleConnectChange(row)"
            :active-text="'已连接'"
            :inactive-text="'未连接'"
            inline-prompt
            style="--el-switch-on-color: #13ce66"
          >
          </el-switch>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="100">
        <template #default="{ row }">
          <el-button
            :icon="Edit"
            circle
            plain
            type="primary"
            @click="() => modify(row)"
          ></el-button>
          <el-button
            :icon="Delete"
            circle
            plain
            type="danger"
            @click="() => deleteDevice(row)"
          ></el-button>
        </template>
      </el-table-column>
      <template #empty>
        <el-empty description="没有数据" />
      </template>
    </el-table>
    <el-dialog v-model="dialogVisible" :title="title" width="30%" :before-close="cancel">
      <el-form
        :model="deviceModel"
        :rules="rules"
        label-width="100px"
        style="padding-right: 30px"
      >
        <el-form-item label="设备ID" prop="deviceId">
          <el-input
            v-model="deviceModel.deviceId"
            minlength="2"
            maxlength="10"
            :disabled="disableIdInput"
          ></el-input>
        </el-form-item>
        <el-form-item label="设备名称" prop="title">
          <el-input v-model="deviceModel.title" minlength="2" maxlength="10"></el-input>
        </el-form-item>
        <el-form-item label="设备类型" prop="category">
          <el-select v-model="deviceModel.category" placeholder="请选择设备类型">
            <el-option
              v-for="type in deviceTypes"
              :key="type.value"
              :label="type.label"
              :value="type.value"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="cancel">取消</el-button>
          <el-button
            type="primary"
            @click="title == '添加设备' ? addDevice() : updateDevice()"
          >
            确认
          </el-button>
        </span>
      </template>
    </el-dialog>
  </el-card>
</template>

<style lang="scss" scoped>
.page-container {
  min-height: 100%;
  box-sizing: border-box;

  .el-input {
    width: 200px;
  }

  .el-select {
    width: 200px;
  }

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
