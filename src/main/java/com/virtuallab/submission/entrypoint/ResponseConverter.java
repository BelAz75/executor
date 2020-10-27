package com.virtuallab.submission.entrypoint;

import com.virtuallab.submission.dataprovider.SubmissionTemplateEntity;

public class ResponseConverter {

    public static SubmissionTemplateResponse toResponse(SubmissionTemplateEntity submissionTemplateEntity) {
        return new SubmissionTemplateResponse(submissionTemplateEntity.getTaskId(), submissionTemplateEntity.getLanguage(), submissionTemplateEntity.getCode());
    }

}
