package com.parkhomenko.rout;

import com.parkhomenko.common.domain.Task;
import com.parkhomenko.service.TaskService;
import com.parkhomenko.service.exception.TaskIdNotFoundException;
import com.parkhomenko.service.exception.TaskIsAlreadyMarkedAsCompletedException;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

/**
 * @author Dmytro Parkhomenko
 *         Created on 26.10.16.
 */

public class ConsoleAnalyzer {

    private static final String EXIT_CODE = "exit";
    private static final String ADD_CODE = "add";
    private static final String PRINT_CODE = "print";
    private static final String BACK_CODE = "back";
    private static final String COMPLETED_TASKS_TABLE_CODE = "completed";

    private TaskService service;

    public void setService(TaskService service) {
        this.service = service;
    }

    public void run() {
        menuScreen();
    }

    private void menuScreen() {

        Scanner scanner = new Scanner(System.in);

        while (true) {

            System.out.println("1) Add task (Enter 'add')");
            System.out.println("2) Print tasks (Enter 'print')");
            System.out.println("3) Exit (Enter 'exit')");

            String input = scanner.nextLine();

            if(input.trim().isEmpty()) {
                continue;
            }

            if (ADD_CODE.equals(input)) {
                addScreen(scanner);
                continue;
            }

            if (PRINT_CODE.equals(input)) {
                printAllUncompletedTasksScreen(scanner);
                continue;
            }

            if (EXIT_CODE.equals(input)) {
                System.out.println("Exit!");
                break;
            }

            System.out.println("ERROR: Invalid command. Try again!!!");
        }

        scanner.close();
    }

    private void addScreen(Scanner scanner) {

        Task task = new Task();
        String input;

        //NAME
        input = inputField(scanner, ValidationUtil.ValidatorEnum.NAME, "Enter Name (allowed: any characters, any digits, any punctuations and any kind of whitespace):");
        if(Objects.isNull(input)) {
            return;
        }
        task.setName(input);

        //Expiration
        input = inputField(scanner, ValidationUtil.ValidatorEnum.EXPIRATION, "Enter Expiration (format: dd MM yyyy):");
        if(Objects.isNull(input)) {
            return;
        }
        task.setExpiration(Utils.convertToDate(input));

        //PRIORITY
        input = inputField(scanner, ValidationUtil.ValidatorEnum.PRIORITY, "Enter Priority (LOW - 0, MIDDLE - 1, HEIGHT - 2):");
        if(Objects.isNull(input)) {
            return;
        }
        task.setPriority(Utils.convertToPriority(input));

        //save task
        service.add(task);

        System.out.println("Done: new task added!!!");
    }

    private String inputField(Scanner scanner, ValidationUtil.Validator validator, String msg) {
        while (true) {

            System.out.println(msg);

            String input = scanner.nextLine();

            if(input.trim().isEmpty()) {
                continue;
            }

            if(validator.isValid(input)) {
                return input;
            } else {
                System.out.println("ERROR: Invalid field format. Try again!!!");
            }
        }
    }

    private void printAllUncompletedTasksScreen(Scanner scanner) {
        printAllUncomplitedTasksTable();

        while(true) {

            System.out.println("1) Back to main menu (Enter 'back')");
            System.out.println("2) Complete task (Enter task ID from table, for example '1')");
            System.out.println("3) Print completed task table (Enter 'completed')");

            String input = scanner.nextLine();

            if(input.trim().isEmpty()) {
                continue;
            }

            if (BACK_CODE.equals(input)) {
                System.out.println("Back!");
                break;
            }

            if (COMPLETED_TASKS_TABLE_CODE.equals(input)) {
                printAllCompletedTasks();
                continue;
            }

            if(ValidationUtil.ValidatorEnum.ID.isValid(input)) {
                Long id = Long.valueOf(input);
                try {
                    service.complete(id);
                    System.out.println("Done: task with id = " + id + " marked as completed!!!");
                } catch (TaskIdNotFoundException | TaskIsAlreadyMarkedAsCompletedException ex) {
                    System.out.println(ex.getMessage());
                }

                printAllUncomplitedTasksTable();
                continue;
            }

            System.out.println("ERROR: Invalid command. Try again!!!");
        }
    }

    private void printAllCompletedTasks() {
        List<Task> allCompleted = service.getAllCompleted();

        System.out.println("COMPLETED TASK TABLE:");

        System.out.format("%10s%20s%20s%20s%n",
                "ID",
                "NAME",
                "EXPIRATION",
                "PRIORITY");

        allCompleted.forEach(task -> System.out.format("%10s%20s%20s%20s%n",
                task.getId(),
                task.getName(),
                task.getExpiration(),
                task.getPriority()));
    }

    private void printAllUncomplitedTasksTable() {
        List<Task> allUnCompleted = service.getAllUnCompleted();

        System.out.println("UNCOMPLETED TASK TABLE:");

        System.out.format("%10s%20s%20s%20s%n",
                "ID",
                "NAME",
                "EXPIRATION",
                "PRIORITY");

        allUnCompleted.forEach(task -> {

            String idTableField = task.getExpiration().isBefore(LocalDate.now()) ?
                    "EXPIRED: " + task.getId() : task.getId().toString();

            System.out.format("%10s%20s%20s%20s%n",
                    idTableField,
                    task.getName(),
                    task.getExpiration(),
                    task.getPriority());
        });
    }
}
