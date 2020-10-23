package com.virtuallab.executor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class ContainerStarterController {
    private final ContainerStarter starter;
    public ContainerStarterController(ContainerStarter starter) {
        this.starter = starter;
    }

    @GetMapping("/execute/{language}")
    public String execute(@PathVariable("language") String languageName) throws IOException, InterruptedException {
        Language language = Language.toEnum(languageName);
        switch (language) {
            case JAVA:
                starter.executeJavaCode();
                break;
            case PYTHON:
                starter.executePythonCode();
                break;
            default:
                throw new RuntimeException("Programming language " + language + " not supported");
        }
        return "Lool";
    }
}
