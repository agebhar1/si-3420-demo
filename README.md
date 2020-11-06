# Sample Project for Spring Integration Issue 3420

[Issue 3420](https://github.com/spring-projects/spring-integration/issues/3420):

Proposed workaround:

> Place into a resources/META-INF the spring-autoconfigure-metadata.properties file with content:
```
org.springframework.boot.actuate.autoconfigure.metrics.CompositeMeterRegistryAutoConfiguration.AutoConfigureBefore=org.springframework.boot.autoconfigure.integration.IntegrationAutoConfiguration
```
