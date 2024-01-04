<script setup>
import { ref, onMounted, onUnmounted, watch, onUpdated, nextTick } from "vue";
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
const formattedDeviceData = ref("");
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
    alert: item.alert,
  }));
};
onMounted(() => {
  fetchData(selectDevice.value);
  const lineChart = echarts.init(document.getElementById("chart-container"));
  const barChart = echarts.init(document.getElementById("bar-chart-container"));
  outputTextarea.value = document.getElementById("outputid");
  let lineOptions = {
    title: {
      text: "Value",
      x: "center",
    },
    tooltip: {
      trigger: "axis",
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

  let barOptions = {
    title: {
      text: "Alert",
      x: "center",
    },
    tooltip: {
      trigger: "axis",
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
        data: chartData.value.map((item) => item.alert),
        type: "bar",
        itemStyle: {
          color: "red",
        },
      },
    ],
  };

  lineChart.setOption(lineOptions);
  barChart.setOption(barOptions);

  //REFRESH DATA
  const intervalId = setInterval(() => {
    fetchData(selectDevice.value);
  }, 1000);

  watch(chartData, () => {
    lineOptions = {
      title: {
        text: "Value",
        x: "center",
      },
      tooltip: {
        trigger: "axis",
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

    barOptions = {
      title: {
        text: "Alert",
        x: "center",
      },
      tooltip: {
        trigger: "axis",
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
          data: chartData.value.map((item) => item.alert),
          type: "bar",
          itemStyle: {
            color: "red",
          },
        },
      ],
    };

    lineChart.setOption(lineOptions);
    barChart.setOption(barOptions);
  });

  onUnmounted(() => {
    clearInterval(intervalId);
  });
});

watch(devicedata, () => {
  formatDeviceData();
});

const formatDeviceData = () => {
  const formatted = devicedata.value.map((item) => JSON.stringify(item)).join("\n");
  formattedDeviceData.value = formatted;
};

//AUTO SCROLL
const outputTextarea = ref(null);
watch(formattedDeviceData, () => {
  nextTick(() => {
    if (outputTextarea.value) {
      outputTextarea.value.scrollTop = outputTextarea.value.scrollHeight;
    }
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
    <div class="charts-container">
      <div id="chart-container" class="chart" style="width: 50%; height: 300px"></div>
      <div id="bar-chart-container" class="chart" style="width: 50%; height: 300px"></div>
    </div>
    <div class="output-container">
      <textarea
        ref="output"
        id="outputid"
        class="output-box"
        v-model="formattedDeviceData"
        readonly
      ></textarea>
    </div>
  </el-card>
</template>

<style scoped>
.custom-select {
  margin-left: 30px;
  width: 150px;
}
.charts-container {
  display: flex;
  justify-content: space-between;
  margin-top: 20px;
}
.chart {
  box-sizing: border-box;
  border: 1px solid #ccc;
  border-radius: 5px;
  margin-right: 10px;
}
.output-box {
  margin-top: 20px;
  border-radius: 5px;
  width: 100%;
  height: 130px;
  resize: none;
  overflow-y: scroll;
  background-color: #1d2021;
  color: #fff;
  font-size: 14px;
  font-family: "Arial";
}
</style>
