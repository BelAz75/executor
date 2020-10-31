package com.virtuallab.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserEntityRepository extends JpaRepository<UserEntity, String> {

    UserEntity findByEmail(String email);

    @Query(value = "select u.* from users u join user_authority ua on u.uuid=ua.user_uuid join authority a on ua.authority_uuid=a.uuid where a.authority='ROLE_USER'", nativeQuery = true)
    Page<UserEntity> findUsers(Pageable pageable);
}
