package com.felix.iotbackend.service.impl;

import com.felix.iotbackend.mqtt.MqttSubscriber;
import com.felix.iotbackend.service.MqttService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MqttServiceImpl implements MqttService {

    private final MqttSubscriber mqttSubscriber;

    @Autowired
    public MqttServiceImpl(MqttSubscriber mqttSubscriber) {
        this.mqttSubscriber = mqttSubscriber;
    }

    public void startMqttSubscription() {
        mqttSubscriber.subscribeToMqtt();
    }

}
