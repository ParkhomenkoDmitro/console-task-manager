package com.parkhomenko.rout;

import com.parkhomenko.persistence.TaskDao;
import com.parkhomenko.service.TaskService;
import com.parkhomenko.service.TaskServiceImpl;

/**
 * @author Dmytro Parkhomenko
 *         Created on 26.10.16.
 */

public class ConsoleTaskManagerBuilder {

    public static Analyzer build(TaskDao dao) {
        TaskService service = new TaskServiceImpl(dao);
        Analyzer analyzer = new Analyzer();
        analyzer.setService(service);
        return analyzer;
    }
}
