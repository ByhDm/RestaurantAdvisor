package com.example.restaurantadvisor.listeners;

import com.example.restaurantadvisor.dto.in.DeleteOwnerInDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class DeleteUserListener {
    @RabbitListener(queues = "myQueue")
    private void testRabbit(@Payload DeleteOwnerInDTO deleteOwnerInDTO) {
        System.out.println("restaurantDeleteIdDTO");
    }

}
