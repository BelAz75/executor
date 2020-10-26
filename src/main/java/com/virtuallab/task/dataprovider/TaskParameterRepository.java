package com.virtuallab.task.dataprovider;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskParameterRepository extends JpaRepository<TaskParameterEntity, String> {

    List<TaskParameterEntity> findByTaskId(String taskId);

}
