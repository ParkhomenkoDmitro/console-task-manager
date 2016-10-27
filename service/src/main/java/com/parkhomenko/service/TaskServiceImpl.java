package com.parkhomenko.service;

import com.parkhomenko.common.domain.Task;
import com.parkhomenko.persistence.TaskDao;
import com.parkhomenko.service.exception.TaskIdNotFoundException;
import com.parkhomenko.service.exception.TaskIsAlreadyMarkedAsCompletedException;

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
    public void complete(long id) throws TaskIdNotFoundException, TaskIsAlreadyMarkedAsCompletedException {
        if(!dao.isExist(id)) {
            throw new TaskIdNotFoundException("ERROR: Task with id = " + id + " does not exist!!!");
        }

        if(dao.isCompleted(id)) {
            throw new TaskIsAlreadyMarkedAsCompletedException("ERROR: Task with id = " + id + " is already marked as completed!!!");
        }

        dao.complete(id);
    }

    @Override
    public void add(Task task) {
        task.setCompleted(false);
        dao.add(task);
    }
}
