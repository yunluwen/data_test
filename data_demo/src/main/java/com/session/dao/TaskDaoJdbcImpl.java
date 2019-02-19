package com.session.dao;



import com.session.domain.Task;
import com.session.jdbc.JDBCHelper;

import java.sql.ResultSet;

/**
 * 读取mysql中的任务信息
 */
public class TaskDaoJdbcImpl implements TaskDao {



    public Task getTask(final long taskid) {
        final Task task = new Task();

        String sql="SELECT * FROM task WHERE taskid = ?";
        Object[] params = {taskid};

        JDBCHelper jdbcHelper = JDBCHelper.getInstanse();
        jdbcHelper.executeQuery(sql, params, new JDBCHelper.QueryCallback() {
            public void process(ResultSet rs) throws Exception {
                if (rs.next()) {
                    long taskId = rs.getLong(1);
                    String taskName = rs.getString(2);
                    String createTime = rs.getString(3);
                    String startTime = rs.getString(3);
                    String finishTime =rs.getString(4);
                    String taskType =rs.getString(5);
                    String taskStatus = rs.getString(6);
                    String taskParam = rs.getString(7);
                    task.setTaskId(taskId);
                    task.setTaskName(taskName);
                    task.setCreateTime(createTime);
                    task.setStartTime(startTime);
                    task.setFinishTime(finishTime);
                    task.setTaskType(taskType);
                    task.setTaskStatus(taskStatus);
                    task.setTaskParam(taskParam);



                }
            }
        });


        return task;
    }


}