package nje.hu.quickshop;

import org.junit.Test;
import static org.junit.Assert.*;

import nje.hu.quickshop.utils.Validator;

public class ValidatorTest {

    @Test
    public void validEmail_returnsTrue() {
        assertTrue(Validator.isValidEmail("test@example.com"));
    }

    @Test
    public void invalidEmail_returnsFalse() {
        assertFalse(Validator.isValidEmail("invalidemail"));
    }

    @Test
    public void shortPassword_returnsFalse() {
        assertFalse(Validator.isValidPassword("123"));
    }

    @Test
    public void strongPassword_returnsTrue() {
        assertTrue(Validator.isValidPassword("secure123"));
    }
}
