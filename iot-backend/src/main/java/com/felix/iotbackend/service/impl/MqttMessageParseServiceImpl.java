package com.felix.iotbackend.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.felix.iotbackend.pojo.DeviceData;
import com.felix.iotbackend.service.MqttMessageParseService;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Service;

@Service
public class MqttMessageParseServiceImpl implements MqttMessageParseService {
    private final ObjectMapper objectMapper;

    public MqttMessageParseServiceImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public DeviceData parseMqttMessage(MqttMessage mqttMessage) {
        try {
            // 将MqttMessage转换为String
            String jsonMessage = new String(mqttMessage.getPayload());

            // 解析JSON消息
            return objectMapper.readValue(jsonMessage, DeviceData.class);
        } catch (Exception e) {
            e.printStackTrace();
            // 可能需要返回null或者抛出自定义异常，根据实际需求来处理解析失败的情况
            return null;
        }
    }
}
