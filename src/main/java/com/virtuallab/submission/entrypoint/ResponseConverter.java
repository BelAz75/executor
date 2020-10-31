package com.virtuallab.submission.entrypoint;

import com.virtuallab.submission.dataprovider.SubmissionEntity;
import com.virtuallab.submission.dataprovider.SubmissionTemplateEntity;

public class ResponseConverter {

    public static SubmissionTemplateResponse toResponse(SubmissionTemplateEntity submissionTemplateEntity) {
        return new SubmissionTemplateResponse(submissionTemplateEntity.getTaskId(), submissionTemplateEntity.getLanguage(), submissionTemplateEntity.getCode());
    }

    public static SubmissionResponse toResponse(SubmissionEntity submissionEntity) {
        return new SubmissionResponse(submissionEntity.getId(), submissionEntity.getSubmittedAt(), submissionEntity.getLanguage(), submissionEntity.getStatus(), submissionEntity.getMessage(), submissionEntity.getTestsPassed(), submissionEntity.getTestsFailed());
    }

}
