package com.virtuallab.task.dataprovider;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskTestCaseRepository extends JpaRepository<TaskTestCaseEntity, String> {
}
