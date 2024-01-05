<script setup>
import { ref, onMounted, onUnmounted, watch, onUpdated, nextTick } from "vue";
import AMapLoader from "@amap/amap-jsapi-loader";
import { deviceListService } from "@/api/device.js";
import { deviceDataListService } from "@/api/devicedata.js";

//LOAD DEVICE LIST
const myDevice = ref([]);
const selectDevice = ref();
const deviceList = async () => {
  let result = await deviceListService();
  myDevice.value = result.data.map((item) => item.deviceId);
  selectDevice.value = myDevice.value[0];
};
deviceList();
//LOAD DEVICEDATA
const devicedata = ref([]);
const formattedDeviceData = ref("");
const mapPushpinData = ref([]);
const fetchData = async (deviceId) => {
  let result = await deviceDataListService(deviceId);
  devicedata.value = result.data;
  mapPushpinData.value = result.data.map((item) => ({
    time: new Date(item.timestamp).toLocaleTimeString(),
    info: item.info,
    alert: item.alert,
    lat: item.lat,
    lng: item.lng,
  }));
};

//MARKER UPDATE
let map = null;
let markers = {};
const updateMarkers = () => {
  // 遍历新的数据并更新已有标记或添加新的标记
  mapPushpinData.value.forEach((point) => {
    // 检查标记是否已存在
    if (markers[point.info]) {
      // 如果标记已存在，则更新其位置和其他信息
      markers[point.info].setPosition([point.lng, point.lat]);
      markers[point.info].setTitle(point.info);
      // 可根据需要更新其他信息或图标
    } else {
      // 如果标记不存在，则创建新的标记对象并将其添加到地图上
      const marker = new AMap.Marker({
        position: [point.lng, point.lat],
        title: point.info,
        icon: point.alert ? "/PinRed.png" : "/PinGreen.png",
        offset: new AMap.Pixel(-16, -45),
      });

      marker.setMap(map);

      // 将新标记存储在markers对象中
      markers[point.info] = marker;
    }
  });
};
//TRAJECTORY UPDATE
const showTrajectory = ref(false);
let polyline = null;
const updateTrajectory = () => {
  const trajectoryPoints = mapPushpinData.value
    .filter((point) => point.lat && point.lng) // 过滤掉无效的坐标点
    .map((point) => [point.lng, point.lat]); // 创建 [lng, lat] 形式的坐标点数组

  if (polyline) {
    polyline.setMap(null); // 清除地图上已有的轨迹线
  }

  if (showTrajectory.value && trajectoryPoints.length > 1) {
    // 如果复选框被选中并且有超过一个有效坐标点，连接这些坐标点绘制折线
    polyline = new AMap.Polyline({
      path: trajectoryPoints,
      borderWeight: 3,
      strokeColor: "#3366FF",
      lineJoin: "round",
    });
    polyline.setMap(map);
  }
};

onMounted(() => {
  fetchData(selectDevice.value);
  AMapLoader.load({
    key: "67d1946b556ed8eb9bb03449f99b91f3", // 申请好的Web端开发者Key，首次调用 load 时必填
    version: "2.0", // 指定要加载的 JSAPI 的版本，缺省时默认为 1.4.15
    plugins: [
      "AMap.ToolBar",
      "AMap.Scale",
      "AMap.HawkEye",
      "AMap.MapType",
      "AMap.Geolocation",
    ], // 需要使用的的插件列表，如比例尺'AMap.Scale'等
  })
    .then((AMap) => {
      map = new AMap.Map("container", {
        // 设置地图容器id
        viewMode: "3D", // 是否为3D地图模式
        zoom: 10, // 初始化地图级别
        center: [119.9, 30.1], // 初始化地图中心点位置
      });
      map.addControl(new AMap.Scale()); //比例尺
      map.addControl(new AMap.ToolBar()); //工具条
      map.addControl(new AMap.HawkEye()); //鹰眼
      map.addControl(new AMap.MapType()); //地图类型
      map.addControl(new AMap.Geolocation()); //定位
      mapPushpinData.value.forEach((point) => {
        const marker = new AMap.Marker({
          position: [point.lng, point.lat], // 先经度，然后纬度
          title: point.info, // 标记标题/信息
          icon: point.alert ? "/PinRed.png" : "/PinGreen.png", // 根据警报状态自定义图标
          offset: new AMap.Pixel(-16, -45),
        });

        // 将标记添加到地图上
        marker.setMap(map);

        // 将标记存储在markers对象中，以便稍后访问和更新
        markers[point.info] = marker;
      });
    })
    .catch((e) => {
      console.log(e);
    });
  const intervalId = setInterval(async () => {
    await fetchData(selectDevice.value);
    updateMarkers();
  }, 1000);
});

onUnmounted(() => {
  map?.clearMap(); // 清除地图上的标记
  map?.destroy(); // 销毁地图实例
});

watch(selectDevice, async (newDeviceId) => {
  // Clear existing markers on the map
  Object.values(markers).forEach((marker) => {
    marker.setMap(null); // Remove marker from the map
  });
  markers = {}; // Reset markers object

  // Fetch data for the newly selected device
  await fetchData(newDeviceId);
  showTrajectory.value = false;
});
watch(showTrajectory, () => {
  updateTrajectory();
});
</script>

<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <span>设备地图</span>
        <el-select v-model="selectDevice" placeholder="请选择设备" class="custom-select">
          <el-option
            v-for="device in myDevice"
            :key="device"
            :label="device"
            :value="device"
          ></el-option>
        </el-select>
        <el-checkbox v-model="showTrajectory" class="trajectory-checkbox">
          <span class="custom-label">显示历史轨迹</span>
        </el-checkbox>
      </div>
    </template>

    <div id="container"></div>
  </el-card>
</template>

<style scoped>
#container {
  width: 100%;
  height: 580px;
}
.custom-select {
  margin-left: 30px;
  width: 150px;
}
.header {
  display: flex;
  align-items: center;
  justify-content: space-between;
}
.trajectory-checkbox {
  margin-left: 30px;
  display: flex;
  justify-content: flex-end; /* 将内容右对齐 */
}
.trajectory-checkbox .custom-label {
  font-size: 16px !important; /* Increase font size */
  margin-right: 20px; /* Right margin */
  margin-top: 5px; /* Adjust vertical alignment */
}
</style>
