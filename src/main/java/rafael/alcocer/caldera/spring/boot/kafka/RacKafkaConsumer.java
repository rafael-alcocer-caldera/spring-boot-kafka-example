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
package rafael.alcocer.caldera.spring.boot.kafka;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.admin.Admin;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;

public class RacKafkaConsumer {

    public static void main(String[] args) {
        RacKafkaConsumer x = new RacKafkaConsumer();
        x.go();
        x.go2();
    }

    public static Properties getProperties() {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "rac-kafka");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        return props;
    }

    /**
     * Using KafkaConsumer API. The Consumer API allows applications to read streams
     * of data from topics in the Kafka cluster.
     */
    public void go() {
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(getProperties());
        Map<String, List<PartitionInfo>> topics = consumer.listTopics();

        System.out.println("##### TOPICS: " + topics);
        System.out.println("##### TOPICS SIZE: " + topics.size());
        System.out.println("##### TOPICS keySet: " + topics.keySet());
        System.out.println("##### TOPICS values: " + topics.values());

        consumer.close();
    }

    /**
     * Using AdminClient API. The Admin API supports managing and inspecting topics,
     * brokers, acls, and other Kafka objects.
     */
    public void go2() {
        Admin admin = Admin.create(getProperties());

        try {
            Set<String> topicNames = admin.listTopics().names().get();
            System.out.println("##### topicNames: " + topicNames);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        admin.close();
    }
}
