**English | [中文](Readme_zh.md)**

# IoT Platform By Felix

```
This is a project of B/S-software-design course of ZJU.
Framework:
    - Backend: Springboot 3
    - Frontend: vue 3
    - Database: Mysql 8.2.0
    - Cache: Redis 3.2.100
    - Mqtt Server: emqx 5.3.2
```

## Table of Contents

- [Installation](#installation)
- [Usage](#usage)
- [Presentation](#presentation)
- [Contributors](#contributors)
- [License](#license)

## Installation

### Windows

#### Direct Run (recommended)

 1. git clone this repository.

 2. start `mysql` service.

 3. unzip the `emqx` and `redis` zip file in `iot-tools`.

 4. enter `emqx` folder, run in terminal:

    ```bash
    .\bin\emqx start
    ```

 5. enter `redis` folder, run `redis-server.exe`.

 6. enter `iotclient` folder, change `server` in `iot.properties` if necessary, then run:

    ```bash
    java -jar iotclient.jar
    ```

 7. open `iot-backend` directory in IDEA, install dependencies in `poe.xml`, change ports and user password in `src\main\resources\application.yml` if necessary. Then run `IotBackendApplication.java`.

 8. open `iot-frontend\vue-project`, run in terminal:

    ```
    npm run dev
    ```

 9.  Open browser, enter the following address:

   ```
   localhost:5173
   ```
    
    

#### Package run

1. git clone this repository.

2. start `mysql` service.

3. unzip the `emqx` `nginx` and `redis` zip file in `iot-tools`.

4. enter `emqx` folder, run in terminal:

```bash
.\bin\emqx start
```

5. enter `redis` folder, run `redis-server.exe`.

6. enter `iotclient` folder, change `server` in `iot.properties` if necessary, then run:

```bash
java -jar iotclient.jar
```

7. start backend:

```bash
java -jar .\docker\backend\iot-backend-0.0.1-SNAPSHOT.jar
```

8. run command:

```bash
cp .\docker\frontend\dist\* .\iot-tools\nginx-1.24.0\html\
start .\iot-tools\nginx-1.24.0\nginx.exe
```

9.  Open browser, enter the following address:

```
localhost:80
```


​    

#### Docker

1. Download [docker](https://github.com/FelixChristian011226/iot-website/tree/main/docker) folder.
2. See [docker employment instructions](https://github.com/FelixChristian011226/iot-website/blob/main/docker/Docker%E9%83%A8%E7%BD%B2%E8%AF%B4%E6%98%8E.md) for more help.



## Usage

​	As told in installation part.



## Presentation

#### Login Page

![loginpage](https://github.com/FelixChristian011226/iot-website/blob/main/report/images/loginpage.png?raw=true)

#### Home Page

![homepage](https://github.com/FelixChristian011226/iot-website/blob/main/report/images/homepage.png?raw=true)

#### Device Configuration Page

![deviceconfigurationpage](https://github.com/FelixChristian011226/iot-website/blob/main/report/images/deviceconfiguration.png?raw=true)

#### Device Statistics Page

![devicestatisticspage](https://github.com/FelixChristian011226/iot-website/blob/main/report/images/devicestatistics.png?raw=true)

#### Device Map Page

![devicemappage](https://github.com/FelixChristian011226/iot-website/blob/main/report/images/devicemap.png?raw=true)

#### User Information Page

![userinformationpage](https://github.com/FelixChristian011226/iot-website/blob/main/report/images/basicinformation.png?raw=true)

####  Avatar Update Page

![avatarupdatepage](https://github.com/FelixChristian011226/iot-website/blob/main/report/images/updateavatar.png?raw=true)

#### Password Update Page

![passwordupdatepage](https://github.com/FelixChristian011226/iot-website/blob/main/report/images/updatepassword.png?raw=true)

## Contributors

- [FelixChristian](https://github.com/FelixChristian011226)

## License

[IoT Platform ©2023 Created by Felix](https://github.com/FelixChristian011226/iot-website)
