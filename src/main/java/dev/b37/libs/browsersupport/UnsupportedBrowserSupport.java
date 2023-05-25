package dev.b37.libs.browsersupport;

class UnsupportedBrowserSupport implements BrowserSupport {
    private static UnsupportedBrowserSupport instance;

    private UnsupportedBrowserSupport() { }

    public static UnsupportedBrowserSupport getInstance() {
        if (instance == null) {
            instance = new UnsupportedBrowserSupport();
        }

        return instance;
    }

    @Override
    public boolean isSupported() {
        return false;
    }

    @Override
    public String getJavascript() {
        return """
                (() => {
                    window.isBrowserSupported = false;
                    if (typeof window.showBrowserUnsupportedMessage === 'function') {
                        window.showBrowserUnsupportedMessage();
                    }
                })();
                """;
    }
}
