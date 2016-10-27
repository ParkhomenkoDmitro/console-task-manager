# console-task-manager
Test project on Java Core

Реализовать консольное приложения список задач.
Как хранилище используется база данных MySQL.
Приложение должно быть организовать таким образом чтобы можно было на основе него написать другое приложение например веб.
При запуске выбирается одно из действий:
1) Добавить задачу;
2) Вывести список задач;
3) Выйти.

При выборе "Добавить задачу" задачу нужно вводить:
1) Название задачи;
2) Когда должна быть выполнена;
3) Приоритет.
После окончания ввода возвращаемся в основное меню.

При выборе "Вывести список задач" 
1) Выводиться список задач, если задача просрочена она помечается как просроченная. После указания какая задача выполнена возвращаемся на екран "список задач"
2) На екране можно указать какая задача закончена, когда задача заканчивается она переноситься в другую таблицу и видна при выборе всех завершенных.
3) На екране можно  запросить вывод всех завершённых задач
4) На екране можно  вернуться в основе меню/екран.

## DB Setup:
- Create table:
```
create table task (
id BIGINT NOT NULL AUTO_INCREMENT, 
name VARCHAR(200), 
expiration DATE, 
priority TINYINT, 
iscompleted BOOL, 
PRIMARY KEY (id));
```
## Run
- Main class path:
```
/rout/src/main/java/com/parkhomenko/rout/Main.java
```
- Command-line arguments (optional) - allwed 3 arguments or no arguments:
 1.  Database url (Example: 'jdbc:mysql://localhost/progforcejob?useSSL=false&serverTimezone=Europe/Kiev' - default value)
 2.  User login (Example: 'jobuser' - default value)
 3.  User pwd (Exaple: 'jobUser_pwd1' - default value)
