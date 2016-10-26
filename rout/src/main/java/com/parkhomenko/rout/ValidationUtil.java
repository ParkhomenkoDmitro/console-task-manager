package com.parkhomenko.rout;

/**
 * @author Dmytro Parkhomenko
 *         Created on 26.10.16.
 */

public class ValidationUtil {

    public interface Validator {
        boolean isValid(String value);
    }

    public enum ValidatorEnum implements Validator {
        NAME() {
            @Override
            public boolean isValid(String value) {
                return true;
            }
        },
        EXPIRATION() {
            @Override
            public boolean isValid(String value) {
                return true;
            }
        },
        PRIORITY() {
            @Override
            public boolean isValid(String value) {
                return true;
            }
        },
        ID() {
            @Override
            public boolean isValid(String value) {
                return true;
            }
        }
    }
}
