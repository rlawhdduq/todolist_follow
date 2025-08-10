// package todolist.follow.config;

// import java.util.HashMap;
// import java.util.Map;

// import org.apache.kafka.clients.consumer.ConsumerConfig;
// import org.apache.kafka.clients.producer.ProducerConfig;
// import org.apache.kafka.common.serialization.StringDeserializer;
// import org.apache.kafka.common.serialization.StringSerializer;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.kafka.core.KafkaTemplate;
// import org.springframework.kafka.core.ProducerFactory;
// import org.springframework.kafka.listener.ContainerProperties;
// import org.springframework.kafka.listener.DefaultErrorHandler;
// import org.springframework.kafka.support.serializer.JsonDeserializer;
// import org.springframework.kafka.support.serializer.JsonSerializer;
// import org.springframework.util.backoff.FixedBackOff;

// import todolist.follow.dto.FollowDto;

// import org.springframework.kafka.annotation.EnableKafka;
// import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
// import org.springframework.kafka.core.ConsumerFactory;
// import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
// import org.springframework.kafka.core.DefaultKafkaProducerFactory;


// @Configuration
// @EnableKafka
// public class KafkaConfig {
    
//     @Value("${spring.kafka.bootstrap-servers}")
//     private String bootstrapServers;
//     private final String consumerFollowGroup = "follow";
    
//     @Bean
//     public KafkaTemplate<String, Object> kafkaTemplate() 
//     {
//         Map<String, Object> producerProps = new HashMap<>();
//         producerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
//         producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//         producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

//         ProducerFactory<String, Object> producerFactory = new DefaultKafkaProducerFactory<>(producerProps);
//         return new KafkaTemplate<>(producerFactory);
//     }

//     @Bean
//     public ConsumerFactory<String, FollowDto> followINSDELonsumerFactory()
//     {
//         Map<String, Object> configProps = new HashMap<>();
//         configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
//         configProps.put(ConsumerConfig.GROUP_ID_CONFIG, consumerFollowGroup);
//         configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//         configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

//         // 역직렬화 신뢰성 문제 해결을 위한 신뢰가능한 패키지 등록처리
//         JsonDeserializer<FollowDto> deserializer = new JsonDeserializer<>(FollowDto.class);
//         deserializer.addTrustedPackages("todolist.follow.dto");
//         deserializer.setRemoveTypeHeaders(false);
//         deserializer.setUseTypeMapperForKey(false);

//         return new DefaultKafkaConsumerFactory<>(configProps, new StringDeserializer(), deserializer);
//     }

//     @Bean
//     public ConcurrentKafkaListenerContainerFactory<String, FollowDto> followDtoKafkaListenerContainerFactory() {

//         DefaultErrorHandler errorHandler = new DefaultErrorHandler(new FixedBackOff(1000L, 2L)); // 1초 대기, 2회 재시도

//         ConcurrentKafkaListenerContainerFactory<String, FollowDto> factory =
//                 new ConcurrentKafkaListenerContainerFactory<>();
//         factory.setConsumerFactory(followINSDELonsumerFactory());
//         factory.setCommonErrorHandler(errorHandler);
//         factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);
//         return factory;
//     }
// }

// 비동기 메시지 큐 방식(카프카, 레디스 사용)에서 RestApi 방식으로 구현방향을 변경함에 따라 기존에 사용하던 의존성들을 주석처리한다.
// 비동기 메시지 큐 방식은 RestApi 방식으로 구현을 마친 후 성능개선을 위해 해당방식으로 변경할 때 다시 주석을 풀고 사용하자.