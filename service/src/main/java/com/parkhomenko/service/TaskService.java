package com.parkhomenko.service;

import com.parkhomenko.common.domain.Task;
import com.parkhomenko.service.exception.TaskIdNotFoundException;
import com.parkhomenko.service.exception.TaskIsAlreadyMarkedAsCompletedException;

import java.util.List;

/**
 * @author Dmytro Parkhomenko
 *         Created on 26.10.16.
 */

public interface TaskService {
    List<Task> getAllCompleted();
    List<Task> getAllUnCompleted();
    void complete(long id) throws TaskIdNotFoundException, TaskIsAlreadyMarkedAsCompletedException;
    void add(Task task);
}
