package com.virtuallab.task.dataprovider;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskAssignmentRepository extends JpaRepository<TaskAssignmentEntity, String> {

    List<TaskAssignmentEntity> findByTaskId(String taskId);

}
