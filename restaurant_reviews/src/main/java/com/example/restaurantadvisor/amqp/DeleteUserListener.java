package com.example.restaurantadvisor.amqp;

import com.example.restaurantadvisor.dto.in.UserDeleteIdDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class DeleteUserListener {
    @RabbitListener(queues = "myQueue")
    private void testRabbit(@Payload UserDeleteIdDTO userDeleteIdDTO) {
        System.out.println("restaurantDeleteIdDTO");
    }

}
