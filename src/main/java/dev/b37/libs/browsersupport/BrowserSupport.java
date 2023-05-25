package dev.b37.libs.browsersupport;

public interface BrowserSupport {
    boolean isSupported();
    String getJavascript();

    static BrowserSupport supported() {
        return SupportedBrowserSupport.getInstance();
    }

    static BrowserSupport unsupported() {
        return UnsupportedBrowserSupport.getInstance();
    }
}
