package com.felix.iotbackend.mqtt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.felix.iotbackend.pojo.DeviceData;

public class MessageParser {
    public static void main(String[] args) {
        String jsonMessage = "{\"alert\":1,\"clientId\":\"device0001\",\"info\":\"Device Data 2024/01/04 17:16:52\",\"lat\":30.353905391693118,\"lng\":120.49380749464035,\"timestamp\":1704359812028,\"value\":92}";

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // 解析JSON消息
            DeviceData deviceData = objectMapper.readValue(jsonMessage, DeviceData.class);

            // 打印解析后的数据
            System.out.println("Alert: " + deviceData.getAlert());
            System.out.println("Client ID: " + deviceData.getClientId());
            System.out.println("Info: " + deviceData.getInfo());
            System.out.println("Latitude: " + deviceData.getLat());
            System.out.println("Longitude: " + deviceData.getLng());
            System.out.println("Timestamp: " + deviceData.getTimestamp());
            System.out.println("Value: " + deviceData.getValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

