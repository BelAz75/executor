package com.virtuallab.submission.usecase;

import com.virtuallab.submission.dataprovider.SubmissionEntity;
import com.virtuallab.submission.dataprovider.SubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Component
public class FindSubmission {

    private final SubmissionRepository submissionRepository;

    @Autowired
    public FindSubmission(SubmissionRepository submissionRepository) {
        this.submissionRepository = submissionRepository;
    }

    public SubmissionEntity byId(String submissionId) {
        return submissionRepository
                .findById(submissionId)
                .orElseThrow(() -> new RuntimeException("SubmissionTemplate not found"));
    }

    public Page<SubmissionEntity> byTaskId(String userId, String taskId, int page, int pageSize) {
        return submissionRepository.findByTaskIdAndUserIdOrderBySubmittedAtDesc(taskId, userId, PageRequest.of(page - 1, pageSize));
    }

}
