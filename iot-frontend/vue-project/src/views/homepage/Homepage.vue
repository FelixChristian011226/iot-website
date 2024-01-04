<script setup>
import { ref, computed } from "vue";
import { Connection, Link, MessageBox } from "@element-plus/icons-vue";
import { deviceListService } from "@/api/device.js";
import { deviceDataCountService, deviceDataListAllService } from "@/api/devicedata.js";
const devices = ref([]);
const datas = ref([]);
const deviceList = async () => {
  let result = await deviceListService();
  devices.value = result.data;
};
const dataList = async () => {
  let result = await deviceDataListAllService();
  datas.value = result.data;
};
deviceList();
dataList();
const totalDevices = computed(() => devices.value.length);
const onlineDevices = computed(
  () => devices.value.filter((device) => device.connect).length
);
const totalData = ref();
const dataCount = async () => {
  let result = await deviceDataCountService();
  totalData.value = result.data;
};
dataCount();
</script>
<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <span>集群概览</span>
      </div>
    </template>
    <el-space wrap>
      <el-card class="box-card" style="width: 250px">
        <template #header>
          <div class="card-header">
            <div class="icon-wrapper">
              <el-icon :size="80" color="#67C23A">
                <Connection />
              </el-icon>
            </div>
            <span class="titles">设备总量</span>
          </div>
        </template>
        <!-- 显示设备总量数据 -->
        <div class="text item">
          {{ totalDevices }}
        </div>
      </el-card>

      <el-card class="box-card" style="width: 275px">
        <template #header>
          <div class="card-header">
            <div class="icon-wrapper">
              <el-icon :size="80" color="#67C23A">
                <Link />
              </el-icon>
            </div>
            <span class="titles">设备在线量</span>
          </div>
        </template>
        <!-- 显示设备在线量数据 -->
        <div class="text item">
          {{ onlineDevices }}
        </div>
      </el-card>

      <el-card class="box-card" style="width: 300px">
        <template #header>
          <div class="card-header">
            <div class="icon-wrapper">
              <el-icon :size="80" color="#67C23A">
                <MessageBox />
              </el-icon>
            </div>
            <span class="titles">接收数据总量</span>
          </div>
        </template>
        <!-- 显示接收数据量数据 -->
        <div class="text item">
          {{ totalData }}
        </div>
      </el-card>
    </el-space>
  </el-card>
</template>

<style>
.box-card {
  margin-bottom: 20px;
  color: #444444;
  box-shadow: none !important;
  margin-right: 30px;
  border-radius: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 0px;
  font-family: "黑体";
  font-size: 24px;
  font-weight: 600;
  border-radius: 5px;
}

.text.item {
  font-family: Arial, sans-serif;
  font-size: 28px;
  color: #333333;
  display: flex;
  margin-left: 20px;
  align-items: center;
  height: 100%;
}
.icon-wrapper {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100px;
  height: 100px;
  border-radius: 50%;
  background-color: #d7e7ffcc;
  margin-bottom: 10px;
}
.titles {
  font-size: 24px;
  margin-bottom: -60px;
  display: block;
  text-align: center;
  color: #555555;
}
</style>
