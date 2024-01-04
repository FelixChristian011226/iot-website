<script setup>
import { ref, computed, onMounted, onUnmounted } from "vue";
import { Connection, Link, MessageBox } from "@element-plus/icons-vue";
import * as echarts from "echarts";
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
onMounted(async () => {
  await deviceList();
  await dataList();
  const formattedData = prepareChartData(datas.value);
  const interval = setInterval(refreshData, 1000);

  const myChart = echarts.init(chartContainer.value); // Get the reference correctly

  const option = {
    title: {
      text: "设备数据折线图",
    },
    tooltip: {
      trigger: "axis",
    },
    legend: {
      data: Object.keys(formattedData),
    },
    xAxis: {
      type: "time",
    },
    yAxis: {
      type: "value",
    },
    series: Object.keys(formattedData).map((clientId) => ({
      name: clientId,
      type: "line",
      data: formattedData[clientId],
    })),
  };

  myChart.setOption(option);
  // Remember to dispose the chart on unmount
  onUnmounted(() => {
    clearInterval(interval);
    myChart.dispose();
  });
});

// Function to prepare data for ECharts
const prepareChartData = (data) => {
  const chartData = {};
  for (let i = 0; i < data.length; i++) {
    const item = data[i];
    const { clientId, timestamp, value } = item;
    if (!chartData[clientId]) {
      chartData[clientId] = [];
    }
    chartData[clientId].push([timestamp, value]);
  }
  return chartData;
};

// Ref for the chart container
const chartContainer = ref(null);

const refreshData = async () => {
  await dataList(); // 获取更新的数据
  const formattedData = prepareChartData(datas.value);

  const myChart = echarts.getInstanceByDom(chartContainer.value); // 获取现有的实例

  if (myChart) {
    // 更新现有实例的数据
    myChart.setOption({
      legend: {
        data: Object.keys(formattedData),
      },
      series: Object.keys(formattedData).map((clientId) => ({
        name: clientId,
        type: "line",
        data: formattedData[clientId],
      })),
    });
  }
};
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
    <div>
      <div ref="chartContainer" style="width: 100%; height: 400px"></div>
    </div>
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
