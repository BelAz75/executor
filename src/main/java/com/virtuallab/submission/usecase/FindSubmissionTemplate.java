package com.virtuallab.submission.usecase;

import com.virtuallab.submission.dataprovider.SubmissionTemplateEntity;
import com.virtuallab.submission.dataprovider.SubmissionTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FindSubmissionTemplate {

    private final SubmissionTemplateRepository submissionTemplateRepository;

    @Autowired
    public FindSubmissionTemplate(SubmissionTemplateRepository submissionTemplateRepository) {
        this.submissionTemplateRepository = submissionTemplateRepository;
    }

    public SubmissionTemplateEntity execute(String taskId, String language) {
        return submissionTemplateRepository
                .findByTaskIdAndLanguage(taskId, language)
                .orElseThrow(() -> new RuntimeException("SubmissionTemplate not found"));
    }
}
