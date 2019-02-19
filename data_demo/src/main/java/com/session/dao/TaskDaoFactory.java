package com.session.dao;

/**
 * dao简单工厂
 */
public class TaskDaoFactory {

    public static TaskDao getTaskDao(){
        return new TaskDaoJdbcImpl();
    }
}
