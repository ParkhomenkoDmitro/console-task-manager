package com.parkhomenko.rout;

import com.parkhomenko.persistence.TaskDao;
import com.parkhomenko.persistence.TaskDaoImpl;

/**
 * @author Dmytro Parkhomenko
 *         Created on 27.10.16.
 */

public class MySqlDbTest {
    public static void main(String[] args) {
        TaskDao dao = new TaskDaoImpl();
        //TEST
        ConsoleAnalyzer analyzer = ConsoleTaskManagerBuilder.build(dao);
        analyzer.run();
    }
}
