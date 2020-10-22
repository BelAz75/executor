package com.virtuallab.executor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class ContainerStarterController {
    private final ContainerStarter starter;
    public ContainerStarterController(ContainerStarter starter) {
        this.starter = starter;
    }

    @GetMapping("/execute")
    public String execute() throws IOException, InterruptedException {
        starter.main();
        return "Lool";
    }
}
