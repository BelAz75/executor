package com.virtuallab.task.dataprovider;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface TaskRepository extends JpaRepository<TaskEntity, String> {

    Page<TaskEntity> findAllByCreatedBy(String createdBy, Pageable pageable);

    @Query(
        value = "select t.* from task t inner join task_assignment ta on t.uuid = ta.task_uuid where ta.user_uuid = :userId",
        countQuery = "select count(1) from task t inner join task_assignment ta on t.uuid = ta.task_uuid where ta.user_uuid = :userId",
        nativeQuery = true
    )
    Page<TaskEntity> findAssignedTasksForUser(@Param("userId") String userId, Pageable pageable);

    @Query(
        value = "select distinct t.* from task t inner join task_assignment ta on t.uuid = ta.task_uuid where ta.user_uuid = :userId or ta.group_uuid in (:groupIds)",
        countQuery = "select count(distinct t.uuid) from task t inner join task_assignment ta on t.uuid = ta.task_uuid where ta.user_uuid = :userId or ta.group_uuid in (:groupIds)",
        nativeQuery = true
    )
    Page<TaskEntity> findAssignedTasksForUserAndGroups(@Param("userId") String userId, @Param("groupIds") Set<String> groupIds, Pageable pageable);

}
