package com.virtuallab.task.dataprovider;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskParameterRepository extends JpaRepository<TaskParameterEntity, String> {
}
