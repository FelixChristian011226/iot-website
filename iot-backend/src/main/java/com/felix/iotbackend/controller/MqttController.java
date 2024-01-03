package com.felix.iotbackend.controller;

import com.felix.iotbackend.mqtt.MqttSubscriber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mqtt")
public class MqttController {

    private final MqttSubscriber mqttSubscriber;

    @Autowired
    public MqttController(MqttSubscriber mqttSubscriber) {
        this.mqttSubscriber = mqttSubscriber;
    }

    @GetMapping("/subscribe")
    public String startMqttSubscription() {
        mqttSubscriber.subscribeToMqtt();
        return "MQTT subscription started!";
    }
}
