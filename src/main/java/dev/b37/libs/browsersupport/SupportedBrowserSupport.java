package dev.b37.libs.browsersupport;

class SupportedBrowserSupport implements BrowserSupport {
    private static SupportedBrowserSupport instance;

    private SupportedBrowserSupport() { }

    public static SupportedBrowserSupport getInstance() {
        if (instance == null) {
            instance = new SupportedBrowserSupport();
        }

        return instance;
    }

    @Override
    public boolean isSupported() {
        return true;
    }

    @Override
    public String getJavascript() {
        return """
                (() => {
                    window.isBrowserSupported = true;
                })();
                """;
    }
}
