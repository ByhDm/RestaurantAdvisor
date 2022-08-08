package com.example.restaurantadvisor.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UtilEmailAddressTest {

    @Test
    void checkNotNullEmail() {
        boolean check  = EmailUtil.checkValid("");
        boolean checkWithSpaces  = EmailUtil.checkValid("     ");
        assertFalse(check);
        assertFalse(checkWithSpaces);
    }

    @Test
    void checkNotAddress() {
        boolean address1 = EmailUtil.checkValid("asdasdasd");
        assertFalse(address1);
        boolean address2 = EmailUtil.checkValid("-012222222222222222282-39-1203-0");
        assertFalse(address2);
        boolean address3 = EmailUtil.checkValid("+1212=20312-@amnsodaos.rtas");
        assertFalse(address3);
        boolean address4 = EmailUtil.checkValid("Asdas@asdas@asdas@.ru@asd?.ras@mail.ru@.ru");
        assertFalse(address4);
    }

    @Test
    void checkAddressIsCorrect() {
        boolean address1 = EmailUtil.checkValid("bfgooorch@mail.ru");
        assertTrue(address1);
        boolean address3 = EmailUtil.checkValid("bfgooorch@mail.raewas.ru");
        assertTrue(address3);
    }
}