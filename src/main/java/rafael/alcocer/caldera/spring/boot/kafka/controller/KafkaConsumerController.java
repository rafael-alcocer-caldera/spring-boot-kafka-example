/**
 * Copyright [2021] [RAFAEL ALCOCER CALDERA]
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package rafael.alcocer.caldera.spring.boot.kafka.controller;

import java.util.List;
import java.util.Map;

import org.apache.kafka.common.PartitionInfo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rafael.alcocer.caldera.spring.boot.kafka.service.KafkaConsumerService;

@RestController
public class KafkaConsumerController {
    
    private KafkaConsumerService service;
    
    public KafkaConsumerController(KafkaConsumerService service) {
        this.service = service;
    }

    @RequestMapping("/listTopics")
    public Map<String, List<PartitionInfo>> listTopics() {
        System.out.println("##### KafkaController...");
        System.out.println("##### KafkaController..." + service.listTopics());
        
        return service.listTopics();
    }
}
