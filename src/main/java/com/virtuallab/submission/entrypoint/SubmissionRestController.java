package com.virtuallab.submission.entrypoint;

import com.virtuallab.util.rest.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/submission")
public class SubmissionRestController {

    private SubmissionService submissionService;

    @Autowired
    public SubmissionRestController(SubmissionService submissionService) {
        this.submissionService = submissionService;
    }

    @PostMapping
    public SubmissionResponse create(@RequestBody SubmissionRequest request) {
        return submissionService.create(request);
    }

    @PutMapping("/{id}")
    public SubmissionResponse update(@PathVariable("id") String id, @RequestBody SubmissionResult result) {
        return submissionService.update(id, result);
    }

    @GetMapping("/{submissionId}")
    public SubmissionResponse getById(@PathVariable("submissionId") String submissionId) {
        return submissionService.find(submissionId);
    }

    @GetMapping
    public PageResponse<SubmissionResponse> findByTaskId(@RequestParam(value = "taskId") String taskId,
                                                         @RequestParam(value = "pageNumber", defaultValue = "1") int page,
                                                         @RequestParam(defaultValue = "10") int pageSize) {
        return submissionService.findByTaskId(taskId, page, pageSize);
    }
}
