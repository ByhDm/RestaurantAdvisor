package com.example.restaurantadvisor.util;

import com.google.i18n.phonenumbers.NumberParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UtilForPhoneTest {

    @Test
    void reformatRuTelephoneRemoveWhiteSpaces() throws NumberParseException {
        String removeWhiteSpaces = PhoneUtil.reformatRuTelephone("+7(999)222-33-11");
        assertEquals("+79992223311", removeWhiteSpaces);
        removeWhiteSpaces = PhoneUtil.reformatRuTelephone("+7-999-222-33-11");
        assertEquals("+79992223311", removeWhiteSpaces);
        removeWhiteSpaces = PhoneUtil.reformatRuTelephone("+7(999)222-33-11");
        assertEquals("+79992223311", removeWhiteSpaces);
        removeWhiteSpaces = PhoneUtil.reformatRuTelephone("+7(999)    222 - 33  -  11");
        assertEquals("+79992223311", removeWhiteSpaces);
        removeWhiteSpaces = PhoneUtil.reformatRuTelephone("+78008888888");
        assertEquals("+78008888888", removeWhiteSpaces);
    }

    @Test
    void reformatRuTelephoneRemoveNotRu() {
        Assertions.assertThrows(Exception.class, () -> PhoneUtil.reformatRuTelephone("+9(999)222-33-11"));
        Assertions.assertThrows(Exception.class, () -> PhoneUtil.reformatRuTelephone("+7(899)222-33-11"));
    }

    @Test
    void reformatRuTelephoneRemoveNotTelephone() {
        Assertions.assertThrows(Exception.class, () -> PhoneUtil.reformatRuTelephone("+cdvbgfcdxdsaxd"));
        Assertions.assertThrows(Exception.class, () -> PhoneUtil.reformatRuTelephone("+7(ddddd899)222-33-11"));
        Assertions.assertThrows(Exception.class, () -> PhoneUtil.reformatRuTelephone("+7000000899)2-0-----0999922-33-11"));
        Assertions.assertThrows(Exception.class, () -> PhoneUtil.reformatRuTelephone("+70000008900000000000000000000"));
    }
}