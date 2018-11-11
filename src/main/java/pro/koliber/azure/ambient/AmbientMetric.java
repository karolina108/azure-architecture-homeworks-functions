package pro.koliber.azure.ambient;

import java.io.Serializable;

public class AmbientMetric {

    private Long id = 0L;

    private String deviceId;

    private String metricDatetime;

    private String temperature;

    private String pressure;

    private String humidity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getMetricDatetime() {
        return metricDatetime;
    }

    public void setMetricDatetime(String metricDatetime) {
        this.metricDatetime = metricDatetime;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    @Override
    public String toString() {
        return "AmbientMetric{" +
                "deviceId='" + deviceId + '\'' +
                ", metricDatetime='" + metricDatetime + '\'' +
                ", temperature='" + temperature + '\'' +
                ", pressure='" + pressure + '\'' +
                ", humidity='" + humidity + '\'' +
                '}';
    }
}
