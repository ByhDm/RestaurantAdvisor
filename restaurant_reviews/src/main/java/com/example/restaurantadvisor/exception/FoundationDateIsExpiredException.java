package com.example.restaurantadvisor.exception;

import java.time.LocalDate;

public class FoundationDateIsExpiredException extends Exception {

    private final String restaurantName;
    private final LocalDate creationDate;

    public FoundationDateIsExpiredException(String restaurantName, LocalDate creationDate) {
        this.restaurantName = restaurantName;
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "Restaurant with name \"" + restaurantName + "\"" +
                "has foundation date " + creationDate;
    }
}