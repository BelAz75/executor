package com.virtuallab.submission.usecase;

import com.virtuallab.submission.dataprovider.SubmissionEntity;
import com.virtuallab.submission.dataprovider.SubmissionRepository;
import com.virtuallab.submission.entrypoint.SubmissionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CreateSubmission {

    private final SubmissionRepository submissionRepository;

    @Autowired
    public CreateSubmission(SubmissionRepository submissionRepository) {
        this.submissionRepository = submissionRepository;
    }

    public SubmissionEntity create(String userId, SubmissionRequest submissionRequest) {
        final SubmissionEntity entity = new SubmissionEntity();
        entity.setCode(submissionRequest.getCode());
        entity.setTaskId(submissionRequest.getTaskId());
        entity.setUserId(userId);
        entity.setLanguage(submissionRequest.getLanguage());
        entity.setSubmittedAt(LocalDateTime.now());
        entity.setStatus("Submitted");

        return submissionRepository.save(entity);
    }
}
