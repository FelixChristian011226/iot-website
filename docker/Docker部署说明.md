# Docker部署说明

项目可以通过当前目录下`一键部署.bat`进行(windows)。

## 一、依赖准备

1. 在该目录启用终端。

2. 进入/mysql目录，执行打包:

   ```
   docker build -t mysql:v1.0.0 .
   ```

3. 进入/redis目录，执行打包:

   ```
   docker build -t redis:v1.0.0 .
   ```

4. 进入/emqx目录，执行打包:

   ```
   docker build -t emqx:v1.0.0 .
   ```

5. 进入/iotclient目录，执行打包:

   ```
   docker build -t iotclient:v1.0.0 .
   ```

## 二、后端部署

1. 进入/backend目录，启用终端。

2. 执行打包:

   ```bash
   docker build -t springboot:v1.0.0 .
   ```

## 三、前端部署

1. 进入/frontend目录，启用终端。

2. 执行打包:

   ```
   docker build -t vue:v1.0.0 .
   ```

## 四、容器创建

1. 进入根目录：

2. 执行容器创建：

   ```
   docker-compose up
   ```

## 五、项目启动

1. 容器创建后项目会自动启动。

2. 待容器启动完毕后，使用浏览器进入目标地址即可：

   ```
   localhost/80
   ```

## 六、已知问题

​	鄙人能力实在是不足，解决了几天依旧存在不知如何解决的bug。在此诚挚道歉，对不起！不同设备不知结果是否有影响，若同样出现问题，建议于windows系统上部署。

**问题1 - 模拟数据收发：**

​	`spring boot`后端和`iotclient`对于`emqx`会造成挤占行为，即他们无法同时与`emqx`进行通信，造成这个的原因目前排除下来竟然是`mysql`（鄙人也很疑惑啊，从未想到会是它的问题，但是一个一个排除下来，将docker中的`mysql`替换成windows下的`mysql`竟奇迹般地可以了(・∀・(・∀・(・∀・*)）

解决方案：

 1. 停止docker中的`mysql`容器，启用windows下的`mysql`服务。修改`/backend/application.yml`文件中的`url`为以下值:

    ```
    jdbc:mysql://localhost:3306/iot?useUnicode=true&characterEncoding=UTF-8&allowPublicKeyRetrieval=true&serverTimezone=UTC&useSSL=false
    ```

    随后将下面的`password`修改为自己root用户的密码，然后再重新部署。（记得停用docker中的`mysql`）

 2. 不使用`iotclient`服务，在创建数据库时直接插入现有数据，在`/mysql/init_database.sql`末尾的插入语句取消注释，然后重新部署。

**问题2 - 头像显示:**

​	`vue`前端和`spring boot`后端分别在不同容器，头像上传功能当时是在windows下编写的，文件的存储路径为项目相对路径。但是分在不同容器后，头像会存储在后端，而前端无法访问，所以头像的显示无法实现。

