// package todolist.follow.service;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.kafka.annotation.KafkaListener;
// import org.springframework.stereotype.Service;

// @Service
// public class KafkaConsumer {

//     private static final Logger log = LoggerFactory.getLogger(KafkaConsumer.class);

//     @KafkaListener(topics = "follow", groupId = "follow")
//     public void consume(String msg)
//     {
//         log.info("[kafka] : " + msg);
//     }
// }

// 비동기 메시지 큐 방식(카프카, 레디스 사용)에서 RestApi 방식으로 구현방향을 변경함에 따라 기존에 사용하던 의존성들을 주석처리한다.
// 비동기 메시지 큐 방식은 RestApi 방식으로 구현을 마친 후 성능개선을 위해 해당방식으로 변경할 때 다시 주석을 풀고 사용하자.