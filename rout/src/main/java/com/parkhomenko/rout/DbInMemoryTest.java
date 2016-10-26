package com.parkhomenko.rout;

import com.parkhomenko.common.domain.Task;
import com.parkhomenko.common.domain.TaskPriority;
import com.parkhomenko.persistence.TaskDao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Dmytro Parkhomenko
 *         Created on 26.10.16.
 */

public class DbInMemoryTest {
    public static void main(String[] args) {

        //LIKE DB IN MEMORY
        TaskDao dao = new TaskDao() {

            private List<Task> db = new ArrayList<>();

            @Override
            public List<Task> getAllCompleted() {
                return db.stream().filter(Task::getCompleted).collect(Collectors.toList());
            }

            @Override
            public List<Task> getAllUnCompleted() {
                return db.stream().filter(task -> !task.getCompleted()).collect(Collectors.toList());
            }

            @Override
            public void complete(Long id) {
                boolean isExist = false;

                for (Task task : db) {
                    if(task.getId() == id) {
                        task.setCompleted(true);
                        isExist = true;
                        break;
                    }
                }

                if(!isExist) {
                    throw new IllegalArgumentException("not exist");
                }
            }

            @Override
            public void add(Task task) {
                task.setId(1L); //TODO remove late
                task.setCompleted(false);
                db.add(task);
            }

        };

        //ADD one test task
        Task one = new Task();
        one.setName("Dima");
        LocalDate localDate = LocalDate.now();
        localDate = localDate.plusDays(1); //tomorrow
        one.setExpiration(localDate);
        one.setPriority(TaskPriority.LOW);
        dao.add(one);

        //TEST
        Analyzer analyzer = ConsoleTaskManagerBuilder.build(dao);
        analyzer.run();
    }
}
