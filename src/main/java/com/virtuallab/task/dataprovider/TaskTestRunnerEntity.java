package com.virtuallab.task.dataprovider;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "task_test_runner")
public class TaskTestRunnerEntity {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "uuid", unique = true)
    private String id;

    @Column(name = "task_uuid")
    private String taskId;

    @Column(name = "language")
    private String language;

    @Column(name = "task_test_code", columnDefinition = "text")
    private String taskTestCode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getTaskTestCode() {
        return taskTestCode;
    }

    public void setTaskTestCode(String taskTestCode) {
        this.taskTestCode = taskTestCode;
    }
}
