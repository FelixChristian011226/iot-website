<script setup>
import { ref, onMounted, onUnmounted, watch } from "vue";
import { deviceDataListService } from "@/api/devicedata.js";
import { deviceListService } from "@/api/device.js";
import * as echarts from "echarts";
import { LineChart } from "echarts/charts";
import { TooltipComponent, GridComponent } from "echarts/components";
import { CanvasRenderer } from "echarts/renderers";

echarts.use([TooltipComponent, GridComponent, LineChart, CanvasRenderer]);

const devicedata = ref([]);
const myDevice = ref([]);
const selectDevice = ref();
const deviceList = async () => {
  let result = await deviceListService();
  myDevice.value = result.data.map((item) => item.deviceId);
  selectDevice.value = myDevice.value[0];
};
deviceList();

const chartData = ref([]);
const fetchData = async (deviceId) => {
  let result = await deviceDataListService(deviceId); // Pass deviceId as a parameter
  devicedata.value = result.data;
  chartData.value = result.data.map((item) => ({
    time: new Date(item.timestamp).toLocaleTimeString(),
    value: item.value,
  }));
};
onMounted(() => {
  fetchData(selectDevice.value);
  const chart = echarts.init(document.getElementById("chart-container"));
  let options = {
    title: {
      text: "Value",
      x: "center",
    },
    xAxis: {
      type: "category",
      data: chartData.value.map((item) => item.time),
    },
    yAxis: {
      type: "value",
    },
    series: [
      {
        data: chartData.value.map((item) => item.value),
        type: "line",
      },
    ],
  };
  chart.setOption(options);

  //REFRESH DATA
  const intervalId = setInterval(() => {
    fetchData(selectDevice.value);
  }, 1000);

  watch(chartData, () => {
    options = {
      title: {
        text: "Value",
        x: "center",
      },
      xAxis: {
        type: "category",
        data: chartData.value.map((item) => item.time),
      },
      yAxis: {
        type: "value",
      },
      series: [
        {
          data: chartData.value.map((item) => item.value),
          type: "line",
        },
      ],
    };
    chart.setOption(options);
  });

  onUnmounted(() => {
    clearInterval(intervalId);
  });
});
</script>

<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <span>设备数据</span>
        <el-select v-model="selectDevice" placeholder="请选择设备" class="custom-select">
          <el-option
            v-for="device in myDevice"
            :key="device"
            :label="device"
            :value="device"
          ></el-option>
        </el-select>
      </div>
    </template>
      <div id="chart-container" style="width: 50%; height: 300px"></div>
  </el-card>
</template>

<style scoped>
.custom-select {
  margin-left: 30px;
  width: 150px;
}

</style>
