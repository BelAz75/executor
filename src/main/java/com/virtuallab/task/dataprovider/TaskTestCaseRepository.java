package com.virtuallab.task.dataprovider;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskTestCaseRepository extends JpaRepository<TaskTestCaseEntity, String> {

    List<TaskTestCaseEntity> findByTaskId(String taskId);

}
