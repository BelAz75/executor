package com.virtuallab.task.dataprovider;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TaskRepository extends JpaRepository<TaskEntity, String> {

    Page<TaskEntity> findAllByCreatedBy(String createdBy, Pageable pageable);

    @Query(
        value = "select t.* from task t inner join task_assignment ta on t.uuid = ta.task_uuid where ta.user_uuid = :userId",
        countQuery = "select count(1) from task t inner join task_assignment ta on t.uuid = ta.task_uuid where ta.user_uuid = :userId",
        nativeQuery = true
    )
    Page<TaskEntity> findAssignedTasks(@Param("userId") String userId, Pageable pageable);

}
