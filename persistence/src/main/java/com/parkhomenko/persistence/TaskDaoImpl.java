package com.parkhomenko.persistence;

import com.parkhomenko.common.domain.Task;
import com.parkhomenko.common.domain.TaskPriority;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
        return null;
    }

    @Override
    public List<Task> getAllUnCompleted() {
        Connection connection = null;
        Statement statement = null;
        List<Task> tasks = new ArrayList<>();

        try {
            connection = DriverManager.getConnection(dbUrl, user, pwd);
            statement = connection.createStatement();
            String sqlQuery = "SELECT id, name, expiration, priority, iscompleted FROM task WHERE iscompleted IS FALSE";
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
            } catch(SQLException se) {}
            try {
                if(Objects.nonNull(connection))
                    connection.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }

        return tasks;
    }

    @Override
    public void complete(Long id) {

    }

    @Override
    public void add(Task task) {

    }
}
