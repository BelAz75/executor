package com.virtuallab.task.dataprovider;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskTestRunnerRepository extends JpaRepository<TaskTestRunnerEntity, String> {
    TaskTestRunnerEntity findByTaskId(String taskId);
}
