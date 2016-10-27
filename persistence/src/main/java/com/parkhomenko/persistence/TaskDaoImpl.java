package com.parkhomenko.persistence;

import com.parkhomenko.common.domain.Task;
import com.parkhomenko.common.domain.TaskPriority;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * @author Dmytro Parkhomenko
 *         Created on 27.10.16.
 */

public class TaskDaoImpl implements TaskDao {

    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DEFAULT_DB_URL = "jdbc:mysql://localhost/progforcejob?useSSL=false&serverTimezone=Europe/Kiev";
    private static final String DEFAULT_USER = "jobuser";
    private static final String DEFAULT_PWD = "jobUser_pwd1";

    private String dbUrl = DEFAULT_DB_URL;
    private String user = DEFAULT_USER;
    private String pwd = DEFAULT_PWD;

    static {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("ERROR: JDBC driver not found!!!");
        }
    }

    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Override
    public List<Task> getAllCompleted() {
        return list("SELECT id, name, expiration, priority, iscompleted FROM task WHERE iscompleted IS TRUE");
    }

    @Override
    public List<Task> getAllUnCompleted() {
        return list("SELECT id, name, expiration, priority, iscompleted FROM task WHERE iscompleted IS FALSE");
    }

    @Override
    public boolean isExist(long id) {
        List<Task> list = list("SELECT id, name, expiration, priority, iscompleted FROM task WHERE id = " + id);
        return !list.isEmpty();
    }

    @Override
    public boolean isCompleted(long id) {
        List<Task> list = list("SELECT id, name, expiration, priority, iscompleted FROM task WHERE iscompleted IS TRUE AND id = " + id);
        return !list.isEmpty();
    }

    @Override
    public void complete(long id) {
        crud(() -> "UPDATE task SET iscompleted = true WHERE id = " + id);
    }

    @Override
    public void add(Task task) {
        crud(() -> {
            String sqlQuery = "INSERT INTO task (name, expiration, priority, iscompleted) " +
                    "VALUES ($NAME, $EXPIRATION, $PRIORITY, $ISCOMPLETED)";

            //NAME
            sqlQuery = sqlQuery.replace("$NAME", "'" + task.getName() + "'");

            //EXPIRATION
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String date = task.getExpiration().format(formatter);
            sqlQuery = sqlQuery.replace("$EXPIRATION", "'" + date + "'");

            //PRIORITY
            sqlQuery = sqlQuery.replace("$PRIORITY", Integer.toString(task.getPriority().ordinal()));

            //ISCOMPLETED
            sqlQuery = sqlQuery.replace("$ISCOMPLETED", task.getCompleted().toString());

            return sqlQuery;
        });
    }

    private void crud(Supplier<String> sqlQuerySupplier) {
        Connection connection = null;
        Statement statement = null;

        try {
            connection = DriverManager.getConnection(dbUrl, user, pwd);
            statement = connection.createStatement();
            statement.executeUpdate(sqlQuerySupplier.get());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(Objects.nonNull(statement))
                    statement.close();
            } catch(SQLException ex) {
                ex.printStackTrace();
            }

            try {
                if(Objects.nonNull(connection))
                    connection.close();
            } catch(SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     *
     * @param sqlQuery to execute: MUST select all table columns,
     *                 if not - you will get java.sql.SQLException that will be caught
     *                 and exception stack trace that will be printed for you
     * @return List of tasks
     */
    private List<Task> list(String sqlQuery) {
        Connection connection = null;
        Statement statement = null;
        List<Task> tasks = new ArrayList<>();

        try {
            connection = DriverManager.getConnection(dbUrl, user, pwd);
            statement = connection.createStatement();

            ResultSet rs = statement.executeQuery(sqlQuery);

            while(rs.next()) {
                long id = rs.getInt("id");
                String name = rs.getString("name");
                LocalDate expiration = rs.getDate("expiration").toLocalDate();
                TaskPriority priority = TaskPriority.values()[rs.getByte("priority")];
                boolean iscompleted = rs.getBoolean("iscompleted");

                Task task = new Task();
                task.setId(id);
                task.setName(name);
                task.setExpiration(expiration);
                task.setPriority(priority);
                task.setCompleted(iscompleted);
                tasks.add(task);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(Objects.nonNull(statement))
                    statement.close();
            } catch(SQLException ex) {
                ex.printStackTrace();
            }

            try {
                if(Objects.nonNull(connection))
                    connection.close();
            } catch(SQLException ex) {
                ex.printStackTrace();
            }
        }

        return tasks;
    }
}
