package dev.b37.libs.browsersupport.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class WebBrowserInfo {
    private final DeviceType deviceType;
    private final WebBrowserName browserName;
    private final Integer version;

    public WebBrowserInfo(DeviceType deviceType, WebBrowserName browserName, Integer version) {
        this.deviceType = deviceType;
        this.browserName = browserName;
        this.version = version;
    }

    public DeviceType getDeviceType() {
        return deviceType;
    }

    public WebBrowserName getBrowserName() {
        return browserName;
    }

    public Integer getVersion() {
        return version;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("deviceType", deviceType)
                .append("name", browserName)
                .append("version", version)
                .toString();
    }
}
