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

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@TestPropertySource(
        properties = "register=PrometheusMeterRegistry"
)
class DemoApplicationPrometheusMeterRegistryTests {

    @Test
    void fetchPrometheusData(@Autowired TestRestTemplate restTemplate) throws InterruptedException {

        Thread.sleep(1000);

        final String body = restTemplate.getForObject("/actuator/prometheus", String.class);
        assertThat(body).contains("spring_integration_send_seconds_max{exception=\"none\",name=\"randomUUIDs\",result=\"success\",type=\"channel\",} ");

    }

}
