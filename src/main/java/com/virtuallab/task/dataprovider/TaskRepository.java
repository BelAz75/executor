package com.virtuallab.task.dataprovider;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<TaskEntity, String> {

    Page<TaskEntity> findAllByCreatedBy(String createdBy, Pageable pageable);

}
