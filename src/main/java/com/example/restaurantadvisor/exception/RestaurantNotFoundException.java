package com.example.restaurantadvisor.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "ресторан не найден")

public class RestaurantNotFoundException extends Exception{

}