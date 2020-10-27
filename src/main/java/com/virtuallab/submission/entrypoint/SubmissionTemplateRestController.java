package com.virtuallab.submission.entrypoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/submission-template")
public class SubmissionTemplateRestController {

    private SubmissionTemplateService submissionTemplateService;

    @Autowired
    public SubmissionTemplateRestController(SubmissionTemplateService submissionTemplateService) {
        this.submissionTemplateService = submissionTemplateService;
    }

    @PostMapping
    public SubmissionTemplateResponse create(@RequestBody SubmissionTemplateRequest submissionTemplateRequest) {
        return submissionTemplateService.create(submissionTemplateRequest);
    }

    @GetMapping
    public SubmissionTemplateResponse find(@RequestParam String taskId, @RequestParam String language) {
        return submissionTemplateService.find(taskId, language);
    }
}
