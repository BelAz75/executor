package com.virtuallab.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserEntityRepository extends JpaRepository<UserEntity, String> {

    UserEntity findByEmail(String email);

}
