package com.virtuallab.executor;

import com.virtuallab.task.dataprovider.TaskTestRunnerRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DeleteTestRunner {

    private final TaskTestRunnerRepository testRunnerRepository;

    public DeleteTestRunner(TaskTestRunnerRepository testRunnerRepository) {
        this.testRunnerRepository = testRunnerRepository;
    }

    @Transactional
    public long delete(String taskId) {
        return testRunnerRepository.deleteByTaskId(taskId);
    }
}
