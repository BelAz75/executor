package com.virtuallab.submission.dataprovider;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubmissionRepository extends JpaRepository<SubmissionEntity, String> {

    Page<SubmissionEntity> findByTaskIdAndUserIdOrderBySubmittedAtDesc(String taskId, String userId, Pageable pageRequest);

}
