package com.virtuallab.submission.entrypoint;

import com.virtuallab.submission.usecase.CreateSubmissionTemplate;
import com.virtuallab.submission.usecase.FindSubmissionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.virtuallab.submission.entrypoint.ResponseConverter.toResponse;

@Service
public class SubmissionTemplateService {

    private final CreateSubmissionTemplate createSubmissionTemplate;
    private final FindSubmissionTemplate findSubmissionTemplate;

    @Autowired
    public SubmissionTemplateService(CreateSubmissionTemplate createSubmissionTemplate, FindSubmissionTemplate findSubmissionTemplate) {
        this.createSubmissionTemplate = createSubmissionTemplate;
        this.findSubmissionTemplate = findSubmissionTemplate;
    }

    public SubmissionTemplateResponse create(SubmissionTemplateRequest submissionTemplateRequest) {
        return toResponse(createSubmissionTemplate.execute(submissionTemplateRequest));
    }

    public SubmissionTemplateResponse find(String taskId, String language) {
        return toResponse(findSubmissionTemplate.execute(taskId, language));
    }
}
