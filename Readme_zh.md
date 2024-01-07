**其他语言版本: [English](README.md), [中文](README_zh.md).**

# 物联网平台 By Felix

这是浙江大学 B/S 软件设计课程的项目。框架如下：
- 后端：Spring Boot 3
- 前端：Vue 3
- 数据库：MySQL 8.2.0
- 缓存：Redis 3.2.100
- MQTT 服务器：EMQ X 5.3.2

## 目录

- [安装](#安装)
- [使用](#使用)
- [演示](#演示)
- [贡献者](#贡献者)
- [许可证](#许可证)

## 安装

### Windows

#### 直接运行（推荐）

1. 克隆该repository。
2. 启动 `mysql` 服务。
3. 在 `iot-tools` 文件夹中解压缩 `emqx` 和 `redis` 压缩文件。
4. 进入 `emqx` 文件夹，在终端中运行：

    ```bash
    .\bin\emqx start
    ```

5. 进入 `redis` 文件夹，运行 `redis-server.exe`。
6. 进入 `iotclient` 文件夹，如果需要，请更改 `iot.properties` 中的 `server`，然后运行：

    ```bash
    java -jar iotclient.jar
    ```

7. 在 IDEA 中打开 `iot-backend` 目录，在 `poe.xml` 中安装依赖项，如有必要在 `src\main\resources\application.yml` 中更改端口和用户密码。然后运行 `IotBackendApplication.java`。
8. 在 `iot-frontend\vue-project` 中运行终端：

    ```
    npm run dev
    ```

9. 打开浏览器，输入以下地址：

    ```
    localhost:5173
    ```



####  打包运行

1. 克隆此存储库。
2. 启动 `mysql` 服务。
3. 在 `iot-tools` 文件夹中解压缩 `emqx`、`nginx` 和 `redis` 压缩文件。
4. 进入 `emqx` 文件夹，在终端中运行：

    ```bash
    .\bin\emqx start
    ```

5. 进入 `redis` 文件夹，运行 `redis-server.exe`。
6. 进入 `iotclient` 文件夹，如果需要，请更改 `iot.properties` 中的 `server`，然后运行：

    ```bash
    java -jar iotclient.jar
    ```

7. 启动后端：

    ```bash
    java -jar .\docker\backend\iot-backend-0.0.1-SNAPSHOT.jar
    ```

8. 运行命令：

    ```bash
    cp .\docker\frontend\dist\* .\iot-tools\nginx-1.24.0\html\
    start .\iot-tools\nginx-1.24.0\nginx.exe
    ```

9. 打开浏览器，输入以下地址：

    ```
    localhost:80
    ```



#### Docker

1. 下载 [docker](https://github.com/FelixChristian011226/iot-website/tree/main/docker) 文件夹。
2. 参阅 [docker 部署说明](https://github.com/FelixChristian011226/iot-website/blob/main/docker/Docker%E9%83%A8%E7%BD%B2%E8%AF%B4%E6%98%8E.md) 获取更多帮助。



## 使用

如安装部分所述。



## 演示

#### 登录页面

![登录页面](https://github.com/FelixChristian011226/iot-website/blob/main/report/images/loginpage.png?raw=true)

#### 主页

![主页](https://github.com/FelixChristian011226/iot-website/blob/main/report/images/homepage.png?raw=true)

#### 设备配置页面

![设备配置页面](https://github.com/FelixChristian011226/iot-website/blob/main/report/images/deviceconfiguration.png?raw=true)

#### 设备统计页面

![设备统计页面](https://github.com/FelixChristian011226/iot-website/blob/main/report/images/devicestatistics.png?raw=true)

#### 设备地图页面

![设备地图页面](https://github.com/FelixChristian011226/iot-website/blob/main/report/images/devicemap.png?raw=true)

#### 用户信息页面

![用户信息页面](https://github.com/FelixChristian011226/iot-website/blob/main/report/images/basicinformation.png?raw=true)

#### 头像更新页面

![头像更新页面](https://github.com/FelixChristian011226/iot-website/blob/main/report/images/updateavatar.png?raw=true)

#### 密码更新页面

![密码更新页面](https://github.com/FelixChristian011226/iot-website/blob/main/report/images/updatepassword.png?raw=true)

## 贡献者

- [FelixChristian](https://github.com/FelixChristian011226)

## 许可证

[IoT Platform ©2023 Created by Felix](https://github.com/FelixChristian011226/iot-website)