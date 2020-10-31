package com.virtuallab.task.dataprovider;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskTestRunnerRepository extends JpaRepository<TaskTestRunnerEntity, String> {
    List<TaskTestRunnerEntity> findByTaskId(String taskId);
}
