package com.example.restaurantadvisor;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = {
        RestaurantAdvisorApplication.class,
        H2TestProfileJPAConfig.class})
@ActiveProfiles("test")
public class RestaurantAdvisorApplicationTests {

    @Test
    void contextLoads() {
    }

}
