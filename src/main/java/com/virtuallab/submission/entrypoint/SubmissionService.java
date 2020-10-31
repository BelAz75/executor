package com.virtuallab.submission.entrypoint;

import com.virtuallab.events.RunSubmissionEvent;
import com.virtuallab.submission.dataprovider.SubmissionEntity;
import com.virtuallab.submission.usecase.CreateSubmission;
import com.virtuallab.submission.usecase.FindSubmission;
import com.virtuallab.submission.usecase.UpdateSubmission;
import com.virtuallab.util.rest.PageResponse;
import com.virtuallab.util.security.SecurityUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.virtuallab.submission.entrypoint.ResponseConverter.toResponse;

@Service
public class SubmissionService {

    private final FindSubmission findSubmission;
    private final CreateSubmission createSubmission;
    private final UpdateSubmission updateSubmission;
    private final ApplicationEventPublisher eventPublisher;

    public SubmissionService(FindSubmission findSubmission, CreateSubmission createSubmission, UpdateSubmission updateSubmission, ApplicationEventPublisher eventPublisher) {
        this.findSubmission = findSubmission;
        this.createSubmission = createSubmission;
        this.updateSubmission = updateSubmission;
        this.eventPublisher = eventPublisher;
    }

    public SubmissionResponse create(SubmissionRequest request) {
        String userId = SecurityUtils.getCurrentUserId();
        final SubmissionEntity submission = createSubmission.create(userId, request);
        eventPublisher.publishEvent(new RunSubmissionEvent(submission.getId(), request.getTaskId(),request.getLanguage(), request.getCode()));
        return toResponse(submission);
    }

    public SubmissionResponse update(String id, SubmissionResult result) {
        return toResponse(updateSubmission.setResult(id, result));
    }

    public SubmissionResponse find(String submissionId) {
        return toResponse(findSubmission.byId(submissionId));
    }

    public PageResponse<SubmissionResponse> findByTaskId(String taskId, int page, int pageSize) {
        String userId = SecurityUtils.getCurrentUserId();
        final Page<SubmissionEntity> entities = findSubmission.byTaskId(userId, taskId, page, pageSize);

        List<SubmissionResponse> searchResponse = entities.getContent().stream()
                .map(ResponseConverter::toResponse)
                .collect(Collectors.toList());

        return new PageResponse<>(page, pageSize, entities.getTotalPages(), entities.getTotalElements(), searchResponse);
    }
}
