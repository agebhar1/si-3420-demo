# Sample Project for Spring Integration Issue 3420

[Issue 3420](https://github.com/spring-projects/spring-integration/issues/3420):

See branch for [proposed workaround](https://github.com/agebhar1/si-3420-demo/tree/workaround).

Spring Integration metrics (channel, etc.) are missing after update from Spring Integration 5.3.2 to 5.3.3 by update
of Spring Boot from 2.3.4 to 2.3.5.

The Prometheus data should contain:
```
# HELP spring_integration_send_seconds_max Send processing time
# TYPE spring_integration_send_seconds_max gauge
spring_integration_send_seconds_max{exception="none",name="randomUUIDs",result="success",type="channel",} 0.004564305
spring_integration_send_seconds_max{exception="none",name="nullChannel",result="success",type="channel",} 0.0
spring_integration_send_seconds_max{exception="none",name="flow.channel#1",result="success",type="channel",} 4.16428E-4 
spring_integration_send_seconds_max{exception="none",name="flow.org.springframework.integration.config.ConsumerEndpointFactoryBean#1",result="success",type="handler",} 2.90357E-4
spring_integration_send_seconds_max{exception="none",name="flow.logging-channel-adapter#0",result="success",type="handler",} 0.002936582
spring_integration_send_seconds_max{exception="none",name="flow.org.springframework.integration.config.ConsumerEndpointFactoryBean#0",result="success",type="handler",} 0.004438363
# HELP spring_integration_send_seconds Send processing time                      
# TYPE spring_integration_send_seconds summary
spring_integration_send_seconds_count{exception="none",name="randomUUIDs",result="success",type="channel",} 72.0
spring_integration_send_seconds_sum{exception="none",name="randomUUIDs",result="success",type="channel",} 0.038246764
spring_integration_send_seconds_count{exception="none",name="nullChannel",result="success",type="channel",} 72.0
spring_integration_send_seconds_sum{exception="none",name="nullChannel",result="success",type="channel",} 0.0
spring_integration_send_seconds_count{exception="none",name="flow.channel#1",result="success",type="channel",} 72.0
spring_integration_send_seconds_sum{exception="none",name="flow.channel#1",result="success",type="channel",} 0.004494802                                                                                      
spring_integration_send_seconds_count{exception="none",name="flow.org.springframework.integration.config.ConsumerEndpointFactoryBean#1",result="success",type="handler",} 72.0
spring_integration_send_seconds_sum{exception="none",name="flow.org.springframework.integration.config.ConsumerEndpointFactoryBean#1",result="success",type="handler",} 0.003098176
spring_integration_send_seconds_count{exception="none",name="flow.logging-channel-adapter#0",result="success",type="handler",} 72.0
spring_integration_send_seconds_sum{exception="none",name="flow.logging-channel-adapter#0",result="success",type="handler",} 0.0256615
spring_integration_send_seconds_count{exception="none",name="flow.org.springframework.integration.config.ConsumerEndpointFactoryBean#0",result="success",type="handler",} 72.0
spring_integration_send_seconds_sum{exception="none",name="flow.org.springframework.integration.config.ConsumerEndpointFactoryBean#0",result="success",type="handler",} 0.036827643
```

The tests fetch data from Prometheus endpoint `/actuator/prometheus` and assert if data contains:
```
spring_integration_send_seconds_max{exception="none",name="randomUUIDs",result="success",type="channel",}
```

Run tests:
```
$ ./mvnw clean test
```
One test (DemoApplicationFailingTests), without explicit `MeterRegistry` bean **fails**.

Switch back to Spring Boot 2.3.4
```
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.3.4.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
```

Rerun tests: 
```
$ ./mvnw clean test
```

All tests will **pass**.