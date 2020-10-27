package com.virtuallab.submission.usecase;

import com.virtuallab.submission.dataprovider.SubmissionTemplateEntity;
import com.virtuallab.submission.dataprovider.SubmissionTemplateRepository;
import com.virtuallab.submission.entrypoint.SubmissionTemplateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateSubmissionTemplate {

    private final SubmissionTemplateRepository submissionTemplateRepository;

    @Autowired
    public CreateSubmissionTemplate(SubmissionTemplateRepository submissionTemplateRepository) {
        this.submissionTemplateRepository = submissionTemplateRepository;
    }

    public SubmissionTemplateEntity execute(SubmissionTemplateRequest submissionTemplateRequest) {
        final SubmissionTemplateEntity entity = new SubmissionTemplateEntity();
        entity.setCode(submissionTemplateRequest.getTemplateCode());
        entity.setLanguage(submissionTemplateRequest.getLanguage());
        entity.setTaskId(submissionTemplateRequest.getTaskId());
        return submissionTemplateRepository.save(entity);
    }
}
