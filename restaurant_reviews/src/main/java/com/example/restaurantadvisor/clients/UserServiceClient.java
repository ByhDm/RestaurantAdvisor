package com.example.restaurantadvisor.clients;

import com.example.restaurantadvisor.dto.out.UserOutDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "userServiceClient", url = "http://localhost:8081", decode404 = true,
        configuration = FeignClientConfig.class)
public interface UserServiceClient {

    @RequestMapping(method = RequestMethod.GET, value = "/user/{id}",
            consumes = "application/json", produces = "application/json")
    ResponseEntity<UserOutDTO> getUser(@PathVariable(value = "id") Long id);
}
