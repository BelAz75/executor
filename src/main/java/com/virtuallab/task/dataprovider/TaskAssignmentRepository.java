package com.virtuallab.task.dataprovider;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskAssignmentRepository extends JpaRepository<TaskAssignmentEntity, String> {
}
