package com.example.restaurantadvisor.listeners;

import com.example.restaurantadvisor.dto.in.ChangeOwnerInDTO;
import com.example.restaurantadvisor.exception.OwnerNotFoundException;
import com.example.restaurantadvisor.exception.RestaurantNotFoundException;
import com.example.restaurantadvisor.service.RestaurantService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class RabbitChangeOwner {
    private final RestaurantService restaurantService;

    public RabbitChangeOwner(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @RabbitListener(queues = "myQueueChangeOwner")
    private void rabbit(@Payload ChangeOwnerInDTO changeOwnerInDTO) throws RestaurantNotFoundException, OwnerNotFoundException {
        System.out.println("change user " + "deleteOwnerDTO");
        restaurantService.changeOwner(changeOwnerInDTO);
    }
}
