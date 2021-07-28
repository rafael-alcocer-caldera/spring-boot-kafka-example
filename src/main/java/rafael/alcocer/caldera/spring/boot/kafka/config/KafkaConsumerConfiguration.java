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
package rafael.alcocer.caldera.spring.boot.kafka.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import lombok.Getter;
import lombok.Setter;

/**
 * https://www.baeldung.com/configuration-properties-in-spring-boot
 * 
 * The official documentation advises that we isolate configuration properties
 * into separate POJOs.
 * 
 * @Configuration
   @ConfigurationProperties(prefix = "kafka")
   public class KafkaConfigProperties {...}
 * 
 * 
 * We use @Configuration so that Spring creates a Spring bean in the application context.
 * 
 * @ConfigurationProperties works best with hierarchical properties that all have the same prefix; 
 * therefore, we add a prefix of "kafka".
 * 
 * Note: If we don't use @Configuration in the POJO, then we need to add 
 * @EnableConfigurationProperties(ConfigProperties.class) in the main Spring application 
 * class to bind the properties into the POJO:

    @SpringBootApplication
    @EnableConfigurationProperties(ConfigProperties.class)
    public class EnableConfigurationDemoApplication {
        public static void main(String[] args) {
            SpringApplication.run(EnableConfigurationDemoApplication.class, args);
        }
    }
 * 
 * That's it! 
 * Spring will automatically bind any property defined in our property file that has the prefix "kafka"
 *  and the same name as one of the fields in the ConfigProperties class.
 *  
 * As of Spring Boot 2.2, Spring finds and registers @ConfigurationProperties classes via classpath scanning. 
 * Therefore, there is no need to annotate such classes with @Component (and other meta-annotations like @Configuration), 
 * or even use the @EnableConfigurationProperties:
 * 
 * @author Rafael Alcocer Caldera
 *
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties("spring.kafka.consumer")
public class KafkaConsumerConfiguration {

    private String bootstrapServers;
    private String groupId;
    private String keyDeserializer;
    private String valueDeserializer;
    
    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
       Map<String, Object> props = new HashMap<>();
       props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
       props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
       props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, keyDeserializer);
       props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, valueDeserializer);
       
       return new DefaultKafkaConsumerFactory<>(props);
    }
    
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
       ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
       factory.setConsumerFactory(consumerFactory());
       
       return factory;
    }
}
