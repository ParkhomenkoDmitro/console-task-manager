package com.parkhomenko.rout;

import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            public boolean isValid(String input) {
                Pattern pattern = Pattern.compile("^[\\p{L}\\p{N}\\p{P}\\p{Z}]*$");
                Matcher matcher = pattern.matcher(input);
                return matcher.matches();
            }
        },
        EXPIRATION() {
            @Override
            public boolean isValid(String input) {
                try {
                    Utils.convertToDate(input);
                } catch (DateTimeParseException ex) {
                    return false;
                }
                return true;
            }
        },
        PRIORITY() {
            @Override
            public boolean isValid(String input) {
                try {
                    Utils.convertToPriority(input);
                } catch (NumberFormatException ex) {
                   return false;
                } catch (IndexOutOfBoundsException ex) {
                    return false;
                }
                return true;
            }
        },
        ID() {
            @Override
            public boolean isValid(String input) {
                try {
                    Long.valueOf(input);
                } catch (NumberFormatException ex) {
                    return false;
                }
                return true;
            }
        }
    }
}
