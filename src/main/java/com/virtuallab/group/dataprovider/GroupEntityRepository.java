package com.virtuallab.group.dataprovider;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GroupEntityRepository extends JpaRepository<GroupEntity, String> {

    @Query(value = "select g.* from groups g inner join user_group ug on g.uuid = ug.group_uuid where ug.user_uuid = :userId", nativeQuery = true)
    List<GroupEntity> findGroupsForUser(String userId);

}
