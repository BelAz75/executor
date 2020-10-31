package com.virtuallab.submission.usecase;

import com.virtuallab.submission.dataprovider.SubmissionTemplateRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DeleteSubmissionTemplate {

    private final SubmissionTemplateRepository submissionTemplateRepository;

    public DeleteSubmissionTemplate(SubmissionTemplateRepository submissionTemplateRepository) {
        this.submissionTemplateRepository = submissionTemplateRepository;
    }

    @Transactional
    public long execute(String taskId) {
        return submissionTemplateRepository.deleteByTaskId(taskId);
    }

}
