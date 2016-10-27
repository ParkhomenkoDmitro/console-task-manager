package com.parkhomenko.service.exception;

/**
 * @author Dmytro Parkhomenko
 *         Created on 27.10.16.
 */

public class TaskIdNotFoundException extends Exception {

    public TaskIdNotFoundException(String message) {
        super(message);
    }
}
