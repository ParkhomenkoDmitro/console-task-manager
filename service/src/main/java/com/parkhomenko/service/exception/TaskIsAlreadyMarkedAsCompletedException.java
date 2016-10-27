package com.parkhomenko.service.exception;

/**
 * @author Dmytro Parkhomenko
 *         Created on 27.10.16.
 */

public class TaskIsAlreadyMarkedAsCompletedException extends Exception {

    public TaskIsAlreadyMarkedAsCompletedException(String message) {
        super(message);
    }
}
