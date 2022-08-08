package com.example.restaurantadvisor.exception;

public class IncorrectEmailAddressException extends Exception {
    public IncorrectEmailAddressException(String message) {
        super(message);
    }
}