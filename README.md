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