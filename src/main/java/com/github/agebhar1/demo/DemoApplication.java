/*
 * Copyright 2020 Andreas Gebhardt
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.agebhar1.demo;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;

import java.time.Duration;
import java.util.UUID;

import static org.springframework.integration.dsl.IntegrationFlows.from;
import static org.springframework.integration.dsl.Pollers.fixedRate;

@SpringBootApplication
@EnableIntegration
@Configuration
public class DemoApplication {

    @Bean
    public IntegrationFlow flow() {
        return from(UUID::randomUUID, spec -> spec.poller(fixedRate(Duration.ofMillis(100))))
                .channel("randomUUIDs")
                .log()
                .get();
    }

    @Bean
    public String assertDependency(@Qualifier("prometheusMeterRegistry") PrometheusMeterRegistry registry) {
        return registry.toString();
    }

    @Bean("meterRegistry")
    @ConditionalOnProperty(name = "register", havingValue = "SimpleMeterRegistry")
    public MeterRegistry simpleMeterRegistry() {
        return new SimpleMeterRegistry();
    }

    @Bean("meterRegistry")
    @ConditionalOnProperty(name = "register", havingValue = "PrometheusMeterRegistry")
    public MeterRegistry prometheusMeterRegistry(PrometheusMeterRegistry registry) {
        return registry;
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
