# 🐰 Spring Boot + RabbitMQ (Direct Exchange) — Step-by-Step

A working demo using:

- 2 Queues: `emailQueue`, `smsQueue`
- 1 Direct Exchange: `myDirectExchange`
- Routing keys: `send.email`, `send.sms`

---

## 📦 1. Add Dependency (`pom.xml`)

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-amqp</artifactId>
</dependency>
```

---

## ⚙️ 2. Configuration (`application.properties`)

```properties
spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest
```

---

## 🛠️ 3. RabbitMQ Config Class (`RabbitMqConfig.java`)

```java
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    public static final String EMAIL_QUEUE = "emailQueue";
    public static final String SMS_QUEUE = "smsQueue";

    public static final String DIRECT_EXCHANGE = "myDirectExchange";

    public static final String EMAIL_ROUTING_KEY = "send.email";
    public static final String SMS_ROUTING_KEY = "send.sms";

    public Queue emailQueue = new Queue(EMAIL_QUEUE, false);

    @Bean
    public Queue emailQueue() {
        return new Queue(EMAIL_QUEUE, false);
    }

    @Bean
    public Queue smsQueue() {
        return new Queue(SMS_QUEUE, false);
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(DIRECT_EXCHANGE);
    }

    @Bean
    public Binding emailBinding(Queue emailQueue, DirectExchange directExchange) {
        return BindingBuilder.bind(emailQueue).to(directExchange).with(EMAIL_ROUTING_KEY);
    }

    @Bean
    public Binding smsBinding(Queue smsQueue, DirectExchange directExchange) {
        return BindingBuilder.bind(smsQueue).to(directExchange).with(SMS_ROUTING_KEY);
    }
}
```

---

## ✉️ 4. Message Sender (`MyMessageSender.java`)

```java
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class MyMessageSender {

    private final RabbitTemplate rabbitTemplate;

    public MyMessageSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendEmailMessage(String message) {
        rabbitTemplate.convertAndSend(
            RabbitMqConfig.DIRECT_EXCHANGE,
            RabbitMqConfig.EMAIL_ROUTING_KEY,
            message
        );
    }

    public void sendSmsMessage(String message) {
        rabbitTemplate.convertAndSend(
            RabbitMqConfig.DIRECT_EXCHANGE,
            RabbitMqConfig.SMS_ROUTING_KEY,
            message
        );
    }
}
```

---

## 📬 5. Message Listener (`MyMessageListener.java`)

```java
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MyMessageListener {

    @RabbitListener(queues = RabbitMqConfig.EMAIL_QUEUE)
    public void handleEmail(String message) {
        System.out.println("📧 Email received: " + message);
    }

    @RabbitListener(queues = RabbitMqConfig.SMS_QUEUE)
    public void handleSms(String message) {
        System.out.println("📱 SMS received: " + message);
    }
}
```

---

## 🚀 6. Run it from Main (`MyRabbitApp.java`)

```java
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MyRabbitApp implements CommandLineRunner {

    private final MyMessageSender sender;

    public MyRabbitApp(MyMessageSender sender) {
        this.sender = sender;
    }

    public static void main(String[] args) {
        SpringApplication.run(MyRabbitApp.class, args);
    }

    @Override
    public void run(String... args) {
        sender.sendEmailMessage("Hello via Email!");
        sender.sendSmsMessage("Yo, SMS is working!");
    }
}
```

---

## 🧠 Flow Recap

| Action              | What Happens                                  |
|---------------------|-----------------------------------------------|
| `convertAndSend()`  | Sends message to the exchange                 |
| Routing Key         | Exchange uses it to match the correct queue  |
| Queue               | Message is stored in matching queue           |
| `@RabbitListener`   | Listener picks up message from the queue      |
| Console Output      | You see the messages being printed            |

---

🎉 You’re now sending + receiving RabbitMQ messages in Spring Boot using direct exchange and routing keys!
