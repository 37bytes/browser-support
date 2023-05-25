package dev.b37.libs.browsersupport;

import dev.b37.libs.browsersupport.exception.BrowserSupportException;
import dev.b37.libs.browsersupport.model.WebBrowserInfo;
import dev.b37.libs.browsersupport.model.WebBrowserName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.Map;

public class BrowserSupportService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final UserAgentService userAgentService;

    public BrowserSupportService() {
        this.userAgentService = new UserAgentService();
    }

    public BrowserSupport getBrowserSupport(String userAgent, Map<WebBrowserName, Integer> supportedBrowsersMap) {
        if (userAgent == null || supportedBrowsersMap == null) {
            throw new BrowserSupportException("Invalid parameters");
        }
        WebBrowserInfo browserInfo = userAgentService.getBrowserInfo(userAgent);

        if (browserInfo == null) {
            log.warn("getBrowserSupport - probably invalid useragent (browserInfo is null), considering the browser as unsupported. UserAgent=[{}]", userAgent);
            return BrowserSupport.unsupported();
        }

        WebBrowserName browserName = browserInfo.getBrowserName();
        Integer minSupportedVersion = supportedBrowsersMap.get(browserName);

        if (minSupportedVersion == null) {
            log.debug("getBrowserSupport - unknown browser '{}' (minSupportedVersion is null), considering the browser as unsupported. UserAgent=[{}]", browserInfo, userAgent);
            return BrowserSupport.unsupported();
        }

        Integer browserVersion = browserInfo.getVersion();
        if (browserVersion == null) {
            log.debug("getBrowserSupport - unknown browser version (browserVersion is null), considering the browser as unsupported. BrowserInfo=[{}], UserAgent=[{}]", browserInfo, userAgent);
            return BrowserSupport.unsupported();
        }

        if (browserVersion < minSupportedVersion) {
            return BrowserSupport.unsupported();
        }

        return BrowserSupport.supported();
    }
}
