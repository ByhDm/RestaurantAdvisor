package com.example.restaurantadvisor;

import com.example.restaurantadvisor.dto.in.UserDeleteIdDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class TestRabbit {

    @RabbitListener(queues = "myQueue")
    private void testRabbit(@Payload UserDeleteIdDTO userDeleteIdDTO) {
        System.out.println("restaurantDeleteIdDTO");
    }
}
