package com.parkhomenko.persistence;

import com.parkhomenko.common.domain.Task;

import java.util.List;

/**
 * @author Dmytro Parkhomenko
 *         Created on 26.10.16.
 */

public interface TaskDao {
    List<Task> getAllCompleted();
    List<Task> getAllUnCompleted();
    void complete(Long id);
    void add(Task task);
}
