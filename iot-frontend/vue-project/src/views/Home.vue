<script setup>
import {
  HomeFilled,
  Monitor,
  PieChart,
  Location,
  UserFilled,
  Postcard,
  PictureRounded,
  EditPen,
  SwitchButton,
  CaretBottom,
} from "@element-plus/icons-vue";
import swal from "sweetalert";
//UserInfo
import { userInfoService } from "@/api/user.js";
import useUserInfoStore from "@/stores/userInfo.js";
import { useTokenStore } from "@/stores/token.js";
const userInfoStore = useUserInfoStore();
const getUserInfo = async () => {
  let result = await userInfoService();
  userInfoStore.setInfo(result.data);
};
getUserInfo();
const currentTime = new Date().getHours();
//Greetings
let greeting = "";
if (currentTime >= 6 && currentTime < 12) {
  greeting = "上午好";
} else if (currentTime >= 12 && currentTime < 14) {
  greeting = "中午好";
} else if (currentTime >= 14 && currentTime < 18) {
  greeting = "下午好";
} else {
  greeting = "晚上好";
}
const greetings = () => {
  return userInfoStore.info.nickname ? `${greeting}，` : `${greeting}`;
};
//Avatar
import avatar from "@/assets/default.png";
import { useRouter } from "vue-router";
const getAvatar = () => {
  return userInfoStore.info.userPic ? userInfoStore.info.userPic : avatar;
};
//DROPDOWN
const router = useRouter();
const tokenStore = useTokenStore();
const handleCommand = (command) => {
  if (command === "information") {
    router.push("/user/information");
  } else if (command === "avatar") {
    router.push("/user/avatar");
  } else if (command === "resetPassword") {
    router.push("/user/resetPassword");
  } else if (command === "logout") {
    swal({
      title: "退出登录",
      text: "退出之后，再次进入网页需要重新登录！",
      icon: "warning",
      buttons: true,
      dangerMode: true,
    }).then((confirm) => {
      if (confirm) {
        swal("退出登录成功", {
          icon: "success",
        });
        tokenStore.removeToken();
        userInfoStore.removeInfo();
        router.push("/login");
      } else {
        swal("用户取消退出登录", {
          icon: "info",
        });
      }
    });
  }
};
</script>

<template>
  <el-container class="layout-container">
    <!-- LEFT MENU -->
    <el-aside width="200px">
      <div class="el-aside__logo"></div>
      <el-menu
        active-text-color="#ffd04b"
        background-color="#232323"
        text-color="#fff"
        router
      >
        <el-menu-item index="/homepage">
          <el-icon>
            <HomeFilled />
          </el-icon>
          <span>首页</span>
        </el-menu-item>
        <el-menu-item index="/device/configuration">
          <el-icon>
            <Monitor />
          </el-icon>
          <span>设备配置</span>
        </el-menu-item>
        <el-menu-item index="/device/statistics">
          <el-icon>
            <PieChart />
          </el-icon>
          <span>数据查询</span>
        </el-menu-item>
        <el-menu-item index="/device/map">
          <el-icon>
            <Location />
          </el-icon>
          <span>设备地图</span>
        </el-menu-item>
        <el-sub-menu>
          <template #title>
            <el-icon>
              <UserFilled />
            </el-icon>
            <span>个人中心</span>
          </template>
          <el-menu-item index="/user/information">
            <el-icon>
              <Postcard />
            </el-icon>
            <span>基本资料</span>
          </el-menu-item>
          <el-menu-item index="/user/avatar">
            <el-icon>
              <PictureRounded />
            </el-icon>
            <span>更换头像</span>
          </el-menu-item>
          <el-menu-item index="/user/resetPassword">
            <el-icon>
              <EditPen />
            </el-icon>
            <span>重置密码</span>
          </el-menu-item>
        </el-sub-menu>
      </el-menu>
    </el-aside>
    <!-- RIGHT AREA -->
    <el-container>
      <!-- 头部区域 -->
      <el-header>
        <div>
          {{ greetings() }}<strong>{{ userInfoStore.info.nickname }}</strong>
        </div>
        <el-dropdown placement="bottom-end" @command="handleCommand">
          <span class="el-dropdown__box">
            <el-image style="height: 50px; border-radius: 50%" :src="getAvatar()">
            </el-image>
            <el-icon>
              <CaretBottom />
            </el-icon>
          </span>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item command="information" :icon="Postcard"
                >基本资料</el-dropdown-item
              >
              <el-dropdown-item command="avatar" :icon="PictureRounded"
                >更换头像</el-dropdown-item
              >
              <el-dropdown-item command="resetPassword" :icon="EditPen"
                >重置密码</el-dropdown-item
              >
              <el-dropdown-item command="logout" :icon="SwitchButton"
                >退出登录</el-dropdown-item
              >
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </el-header>
      <!-- 中间区域 -->
      <el-main>
        <router-view></router-view>
      </el-main>
      <!-- 底部区域 -->
      <el-footer>
        <a
          href="https://github.com/FelixChristian011226/iot-website"
          target="_blank"
          class="footer-link"
        >
          IoT Platform ©2023 Created by Felix
        </a>
      </el-footer>
    </el-container>
  </el-container>
</template>

<style lang="scss" scoped>
.layout-container {
  height: 100vh;

  .el-aside {
    background-color: #232323;

    &__logo {
      height: 120px;
      background: url("@/assets/logo.png") no-repeat center / 200px auto;
    }

    .el-menu {
      border-right: none;
    }
  }

  .el-header {
    background-color: #fff;
    display: flex;
    align-items: center;
    justify-content: space-between;

    .el-dropdown__box {
      display: flex;
      align-items: center;

      .el-icon {
        color: #999;
        margin-left: 10px;
      }

      &:active,
      &:focus {
        outline: none;
      }
    }
  }

  .el-footer {
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 14px;
    color: #666;
  }
  .footer-link {
    color: #66a3ff; /* Light blue color */
    text-decoration: none; /* Remove default underline */
    transition: color 0.3s; /* Smooth color transition */

    &:hover {
      color: #004080; /* Darker blue color on hover */
    }
  }
}
</style>
