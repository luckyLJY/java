package com.guigu.apitest.source.beans;

/**
 * @deprecated 传感器温度读书类型
 */
public class Sensor {
    //属性：id,时间戳，温度值
    private String id;
    private Long  timestamp;
    private Double temperature;

    public Sensor() {
    }

    public Sensor(String id, Long timestamp, Double temperature) {
        this.id = id;
        this.timestamp = timestamp;
        this.temperature = temperature;
    }

    public Sensor(String id, Double temperature, double max) {
    }

    @Override
    public String toString() {
        return "SensorReading{" +
                "id='" + id + '\'' +
                ", timestamp=" + timestamp +
                ", temperature=" + temperature +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long gettimestamp() {
        return timestamp;
    }

    public void settimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }
}
