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
package rafael.alcocer.caldera.spring.boot.kafka.service;

import java.util.List;
import java.util.Map;

import org.apache.kafka.common.PartitionInfo;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerServiceImpl implements KafkaConsumerService {

    private final ConsumerFactory<String, String> consumerFactory;

    public KafkaConsumerServiceImpl(ConsumerFactory<String, String> consumerFactory) {
        this.consumerFactory = consumerFactory;
    }

    @Override
    public Map<String, List<PartitionInfo>> listTopics() {
        Map<String, List<PartitionInfo>> topics = consumerFactory.createConsumer().listTopics();

        System.out.println("##### TOPICS: " + topics);
        System.out.println("##### TOPICS SIZE: " + topics.size());
        System.out.println("##### TOPICS keySet: " + topics.keySet());
        System.out.println("##### TOPICS values: " + topics.values());
        
        return topics;
    }
}
