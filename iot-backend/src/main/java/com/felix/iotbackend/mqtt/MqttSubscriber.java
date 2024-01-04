package com.felix.iotbackend.mqtt;

import com.felix.iotbackend.pojo.DeviceData;
import com.felix.iotbackend.service.DeviceDataService;
import com.felix.iotbackend.service.DeviceService;
import com.felix.iotbackend.service.impl.MqttMessageParseServiceImpl;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MqttSubscriber {

    private final MqttMessageParseServiceImpl mqttMessageParseServiceImpl;
    private final DeviceDataService deviceDataService;
    private final DeviceService deviceService;

    private final String broker;
    private final String topic;
    private final String username;
    private final String password;
    private final String clientId;

    public MqttSubscriber(
            @Value("${mqtt.broker}") String broker,
            @Value("${mqtt.topic}") String topic,
            @Value("${mqtt.username}") String username,
            @Value("${mqtt.password}") String password,
            @Value("${mqtt.clientid}") String clientId,
            MqttMessageParseServiceImpl mqttMessageParseServiceImpl,
            DeviceDataService deviceDataService,
            DeviceService deviceService
    ) {
        this.broker = broker;
        this.topic = topic;
        this.username = username;
        this.password = password;
        this.clientId = clientId;
        this.mqttMessageParseServiceImpl = mqttMessageParseServiceImpl;
        this.deviceDataService = deviceDataService;
        this.deviceService = deviceService;
    }

    public void subscribeToMqtt() {
        int qos = 0;

        try {
            MqttClient client = new MqttClient(broker, clientId, new MemoryPersistence());
            // 连接参数
            MqttConnectOptions options = new MqttConnectOptions();
            options.setUserName(username);
            options.setPassword(password.toCharArray());
            options.setConnectionTimeout(60);
            options.setKeepAliveInterval(60);
            // 设置回调
            client.setCallback(new MqttCallback() {
                public void connectionLost(Throwable cause) {
                    System.out.println("connectionLost: " + cause.getMessage());
                }

                public void messageArrived(String topic, MqttMessage message) {
                    DeviceData deviceData = mqttMessageParseServiceImpl.parseMqttMessage(message);
                    if(deviceService.checkConnect(deviceData.getClientId())) {
                        try {
                            deviceDataService.saveToDB(deviceData);
                        } catch (Exception e) {
                            e.printStackTrace();
                            throw new RuntimeException(e);
                        }
                    }

//                    System.out.println(deviceData);
//                    System.out.println("topic: " + topic);
//                    System.out.println("Qos: " + message.getQos());
//                    System.out.println("message content: " + new String(message.getPayload()));
//                    System.out.println("deviceId: " + new String(message.getPayload()).split(",")[0]);


                }

                public void deliveryComplete(IMqttDeliveryToken token) {
                    System.out.println("deliveryComplete---------" + token.isComplete());
                }
            });
            client.connect(options);
            client.subscribe(topic, qos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
