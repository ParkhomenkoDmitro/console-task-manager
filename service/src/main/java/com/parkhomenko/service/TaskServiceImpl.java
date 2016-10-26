package com.parkhomenko.service;

import com.parkhomenko.common.domain.Task;
import com.parkhomenko.persistence.TaskDao;

import java.util.List;

/**
 * @author Dmytro Parkhomenko
 *         Created on 26.10.16.
 */

public class TaskServiceImpl implements TaskService {

    private TaskDao dao;

    public TaskServiceImpl(TaskDao dao) {
        this.dao = dao;
    }

    @Override
    public List<Task> getAllCompleted() {
        return dao.getAllCompleted();
    }

    @Override
    public List<Task> getAllUnCompleted() {
        return dao.getAllUnCompleted();
    }

    @Override
    public void complete(Long id) {
        dao.complete(id);
    }

    @Override
    public void add(Task task) {
        dao.add(task);
    }
}
