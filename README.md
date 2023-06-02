# browser-support

### deploy

```shell
deploy.sh dev.b37.libs browser-support {VERSION}
```

### usage dependency

```xml
        <repositories>
            <!--...-->
            <repository>
                <id>github</id>
                <url>https://raw.githubusercontent.com/37bytes/browser-support/repository/</url>
            </repository>
        </repositories>
       <!--...-->
        <dependencies>
            <!--...-->
            <dependency>
                <groupId>dev.b37.libs</groupId>
                <artifactId>browser-support</artifactId>
                <version>{VERSION}</version>
            </dependency>
        </dependencies>
```

### usage

```java

Map<WebBrowserName, Integer> supportedBrowsersMap = Map.ofEntries(
        Map.entry(WebBrowserName.BLINK, blink),
        Map.entry(WebBrowserName.GECKO, gecko),
        Map.entry(WebBrowserName.SAFARI, safari)
        );

BrowserSupport browserSupport = browserSupportService.getBrowserSupport(userAgent, supportedBrowsersMap);

boolean isSupported = BrowserSupport browserSupport.isSupported();

String javaScript = BrowserSupport browserSupport.getJavascript();
```