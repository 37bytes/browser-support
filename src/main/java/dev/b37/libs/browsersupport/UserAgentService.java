package dev.b37.libs.browsersupport;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import dev.b37.libs.browsersupport.exception.BrowserSupportException;
import dev.b37.libs.browsersupport.model.DeviceType;
import dev.b37.libs.browsersupport.model.WebBrowserInfo;
import dev.b37.libs.browsersupport.model.WebBrowserName;
import nl.basjes.parse.useragent.UserAgent;
import nl.basjes.parse.useragent.UserAgentAnalyzer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


class UserAgentService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final UserAgentAnalyzer userAgentAnalyzer = UserAgentAnalyzer.newBuilder()
            .hideMatcherLoadStats()
            .withCache(2000)
            .immediateInitialization()
            .build();

    private final Cache<String, WebBrowserInfo> browserInfoCache = Caffeine.newBuilder()
            .maximumSize(5120)
            .build();

    WebBrowserInfo getBrowserInfo(String userAgent) {
        if (userAgent == null) {
            log.debug("No user agent when getting browser info from it");
            return null;
        }

        return browserInfoCache.get(userAgent, k -> {
            return getBrowserInfoInternal(parseUserAgent(userAgent));
        });
    }

    private WebBrowserInfo getBrowserInfoInternal(UserAgent userAgent) {
        if (userAgent == null) {
            log.debug("No user agent when getting browser info from it");
            return null;
        }

        var deviceType = getDeviceType(userAgent);
        var name = getBrowserName(userAgent);
        var majorVersion = getBrowserVersion(userAgent, name);

        return new WebBrowserInfo(deviceType, name, majorVersion);
    }

    private UserAgent parseUserAgent(String userAgent) {
        if (userAgent == null) {
            log.debug("No user agent when parsing it");
            return null;
        }

        return userAgentAnalyzer.parse(userAgent);
    }

    private DeviceType getDeviceType(UserAgent userAgent) {
        if (userAgent == null) {
            throw new BrowserSupportException("userAgent must not be null");
        }

        var deviceClass = userAgent.getValue(UserAgent.DEVICE_CLASS);

        return switch (deviceClass) {
            case "Desktop" -> DeviceType.DESKTOP;
            case "Phone" -> DeviceType.PHONE;
            case "Tablet" -> DeviceType.TABLET;
            default -> {
                log.warn("Unknown device type is used: deviceClass=[{}], userAgent=[{}]", deviceClass, userAgent.getUserAgentString());
                yield DeviceType.OTHER;
            }
        };
    }

    private WebBrowserName getBrowserName(UserAgent userAgent) {
        if (userAgent == null) {
            throw new BrowserSupportException("userAgent must not be null");
        }

        var layoutEngine = userAgent.getValue(UserAgent.LAYOUT_ENGINE_NAME);

        return switch (layoutEngine) {
            case "Blink" -> WebBrowserName.BLINK;
            case "Gecko" -> WebBrowserName.GECKO;
            case "AppleWebKit" -> WebBrowserName.SAFARI;
            default -> {
                log.warn("Unknown browser is used: layoutEngine=[{}], userAgent=[{}]", layoutEngine, userAgent.getUserAgentString());
                yield WebBrowserName.UNKNOWN;
            }
        };
    }

    private Integer getBrowserVersion(UserAgent userAgent, WebBrowserName browserName) {
        if (userAgent == null || browserName == null) {
            throw new BrowserSupportException("userAgent and browserName must not be null");
        }
        String versionString;

        if (browserName == WebBrowserName.BLINK) {
            versionString = userAgent.getValue(UserAgent.LAYOUT_ENGINE_VERSION_MAJOR);
        } else {
            versionString = userAgent.getValue(UserAgent.AGENT_VERSION_MAJOR);
        }

        if (versionString == null) {
            log.warn("Unknown browser version: browserName=[{}], userAgent=[{}]", browserName, userAgent.getUserAgentString());
            return null;
        }

        return Integer.valueOf(versionString);
    }
}
