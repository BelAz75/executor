package com.virtuallab.submission.dataprovider;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubmissionTemplateRepository extends JpaRepository<SubmissionTemplateEntity, String> {
    Optional<SubmissionTemplateEntity> findByTaskIdAndLanguage(String taskId, String language);

    long deleteByTaskId(String taskId);
}
