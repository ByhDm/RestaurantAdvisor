package com.example.restaurantadvisor.listeners;

import com.example.restaurantadvisor.dto.in.DeleteOwnerInDTO;
import com.example.restaurantadvisor.exception.RestaurantNotFoundException;
import com.example.restaurantadvisor.service.RestaurantService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class RabbitDeleteOwner {
    private final RestaurantService restaurantService;

    public RabbitDeleteOwner(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @RabbitListener(queues = "myQueueDeleteOwner")
    private void rabbit(@Payload DeleteOwnerInDTO deleteOwnerInDTO) throws RestaurantNotFoundException {
        System.out.println("delete user " + "deleteOwnerInDTO");
        restaurantService.deleteOwner(deleteOwnerInDTO);
    }
}
