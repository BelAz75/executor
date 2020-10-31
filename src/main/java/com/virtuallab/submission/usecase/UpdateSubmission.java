package com.virtuallab.submission.usecase;

import com.virtuallab.submission.dataprovider.SubmissionEntity;
import com.virtuallab.submission.dataprovider.SubmissionRepository;
import com.virtuallab.submission.entrypoint.SubmissionResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UpdateSubmission {

    private final SubmissionRepository submissionRepository;

    @Autowired
    public UpdateSubmission(SubmissionRepository submissionRepository) {
        this.submissionRepository = submissionRepository;
    }

    public SubmissionEntity setResult(String id, SubmissionResult result) {

        final SubmissionEntity entity = submissionRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Submission not found"));

        entity.setStatus(result.getStatus());
        entity.setMessage(result.getMessage());
        entity.setTestsPassed(result.getTestsPassed());
        entity.setTestsFailed(result.getTestsFailed());

        return submissionRepository.save(entity);
    }
}
