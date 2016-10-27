package com.parkhomenko.rout;

import com.parkhomenko.persistence.TaskDao;
import com.parkhomenko.persistence.TaskDaoImpl;

/**
 * @author Dmytro Parkhomenko
 *         Created on 27.10.16.
 */

public class MySqlDbTest {
    public static void main(String[] args) {
        TaskDao dao;

        if (args.length == 3) {

            String dbUrl = args[0];
            String user = args[1];
            String pwd = args[2];

            dao = new TaskDaoImpl(dbUrl, user, pwd);

        } else {
            if (args.length != 0) {
                System.out.println("ERROR: invalid args number!!!");
                return;
            } else {
                dao = new TaskDaoImpl();
            }
        }

        //TEST
        ConsoleAnalyzer analyzer = ConsoleTaskManagerBuilder.build(dao);
        analyzer.run();
    }
}
