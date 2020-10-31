package com.virtuallab.executor;

import com.virtuallab.common.Language;
import com.virtuallab.task.dataprovider.TaskTestRunnerEntity;
import com.virtuallab.task.dataprovider.TaskTestRunnerRepository;
import com.virtuallab.util.UUIDUtil;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.virtuallab.executor.ContainerStarter.executeProcess;

@Service
public class DockerImageGenerator {
    private final TaskTestRunnerRepository testRunnerRepository;

    public DockerImageGenerator(TaskTestRunnerRepository testRunnerRepository) {
        this.testRunnerRepository = testRunnerRepository;
    }

    public String generateDockerImageForLanguage(Language language, String execFolder, String taskId, String fileContent) throws IOException, InterruptedException {
        String dockerImageName;
        switch (language) {
            case JAVA:
                createJavaExecScript(language, execFolder);
                System.out.println(taskId);
                TaskTestRunnerEntity testRunnerJava = testRunnerRepository.findByTaskIdAndLanguage(taskId, Language.JAVA.name()).get(0);
                Path testCaseJavaFile = createJavaTestCaseFile(language, execFolder, testRunnerJava.getTaskTestCode());
                Path submissionJavaFile = createJavaSubmissionFile(language, execFolder, fileContent);
                dockerImageName = generateJavaDockerImage(submissionJavaFile, testCaseJavaFile, execFolder, language);
                break;
            case PYTHON:
                TaskTestRunnerEntity testRunnerPython = testRunnerRepository.findByTaskIdAndLanguage(taskId, Language.PYTHON.name()).get(0);
                Path submissionPythonFile = createPythonSubmissionFile(language, execFolder, fileContent);
                Path testCasePythonFile = createPythonTestCaseFile(language, execFolder, testRunnerPython.getTaskTestCode());
                dockerImageName = generatePythonDockerImage(submissionPythonFile, testCasePythonFile, execFolder, language);
                break;
            case C:
                dockerImageName = generateCDockerImage(createFile(language, execFolder, fileContent), execFolder, language);
                break;
            case PASCAL:
                dockerImageName = generatePascalDockerImage();
                break;
            default:
                throw new RuntimeException("Programming language " + language + " not supported");
        }
        return dockerImageName;
    }

    private Path createPythonTestCaseFile(Language language, String execFolder, String fileContent) throws IOException {
        String fileName = "test_case" + language.getExtension();
        Path filePath = Paths.get("./images/" + language.name().toLowerCase() + "/" + execFolder + "/" + fileName);
        Files.write(filePath, fileContent.getBytes());
        return filePath;
    }

    private Path createJavaSubmissionFile(Language language, String execFolder, String fileContent) throws IOException {
        String fileName = "Submission" + language.getExtension();
        Path filePath = Paths.get("./images/" + language.name().toLowerCase() + "/" + execFolder + "/" + fileName);
        Files.write(filePath, fileContent.getBytes());
        return filePath;
    }

    private Path createJavaTestCaseFile(Language language, String execFolder, String fileContent) throws IOException {
        String fileName = "TestCase" + language.getExtension();
        Path filePath = Paths.get("./images/" + language.name().toLowerCase() + "/" + execFolder + "/" + fileName);
        Files.write(filePath, fileContent.getBytes());
        return filePath;
    }

    private Path createJavaExecScript(Language language, String execFolder) throws IOException {
        String fileName = "process_execution.sh";
        String scriptContent = "#!/bin/bash\n" +
                               "cd /usr/local/bin/\n" +
                               "javac Submission.java TestCase.java\n" +
                               "java TestCase\n" +
                               "exit 1";
        Path filePath = Paths.get("./images/" + language.name().toLowerCase() + "/" + execFolder + "/" + fileName);
        Files.write(filePath, scriptContent.getBytes());
        return filePath;
    }

    private Path createPythonSubmissionFile(Language language, String execFolder, String fileContent) throws IOException {
        String fileName = "submission" + language.getExtension();
        Path filePath = Paths.get("./images/" + language.name().toLowerCase() + "/" + execFolder + "/" + fileName);
        Files.write(filePath, fileContent.getBytes());
        return filePath;
    }

    private Path createFile(Language language, String execFolder, String fileContent) throws IOException {
        String fileName = UUIDUtil.generateShortUUID() + language.getExtension();
        Path filePath = Paths.get("./images/" + language.name().toLowerCase() + "/" + execFolder + "/" + fileName);
        Files.write(filePath, fileContent.getBytes());
        return filePath;
    }

    private String generateJavaDockerImage(Path submissionFile, Path testCaseFile, String execFolder, Language language) throws IOException, InterruptedException {
        Path dockerfilePath = Paths.get("./images/" + language.name().toLowerCase() + "/" + execFolder + "/Dockerfile");
        String dockerfile = "FROM openjdk:11.0.1-slim\n" +
                "COPY process_execution.sh /usr/local/bin/process_execution.sh\n" +
                "RUN chmod +x /usr/local/bin/process_execution.sh\n" +
                "COPY " + submissionFile.getFileName() + " /usr/local/bin/" + submissionFile.getFileName() + "\n" +
                "COPY " + testCaseFile.getFileName() + " /usr/local/bin/" + testCaseFile.getFileName() + "\n" +
                "CMD [\"/usr/local/bin/process_execution.sh\"]";
        Files.write(dockerfilePath, dockerfile.getBytes());
        String dockerImageName = language.name().toLowerCase() + "_" + UUIDUtil.generateShortUUID();
        executeProcess("docker build -t " + dockerImageName + " " + dockerfilePath.getParent().toAbsolutePath().toString());
        return dockerImageName;
    }

    private String generatePythonDockerImage(Path submissionFile, Path testCaseFile, String execFolder, Language language) throws IOException, InterruptedException {
        Path dockerfilePath = Paths.get("./images/" + language.name().toLowerCase() + "/" + execFolder +  "/Dockerfile");
        String filenameWithoutExtension = testCaseFile.getFileName().toString().replaceFirst("[.][^.]+$", "");
        String dockerfile = "FROM python:3\n" +
                "WORKDIR /usr/src/app\n" +
                "RUN touch __init__.py\n" +
                "COPY " + submissionFile.getFileName() + " ./" + submissionFile.getFileName() + "\n" +
                "COPY " + testCaseFile.getFileName() + " ./" + testCaseFile.getFileName() + "\n" +
                "WORKDIR /usr/src\n" +
                "CMD [\"python\", \"-m\" ,\"app." + filenameWithoutExtension + "\"]";
        Files.write(dockerfilePath, dockerfile.getBytes());
        String dockerImageName = language.name().toLowerCase() + "_" + UUIDUtil.generateShortUUID();
        executeProcess("docker build -t " + dockerImageName + " " + dockerfilePath.getParent().toAbsolutePath().toString());
        return dockerImageName;
    }

    private String generateCDockerImage(Path filePath, String execFolder, Language language) throws IOException, InterruptedException {
        Path dockerfilePath = Paths.get("./images/" + language.name().toLowerCase() + "/" + execFolder +  "/Dockerfile");
        String dockerfile = "FROM gcc:4.9\n" +
                "COPY " + filePath.getFileName() + " /usr/src/myapp/" + filePath.getFileName() + "\n" +
                "WORKDIR /usr/src/myapp\n" +
                "RUN gcc -o myapp ./" + filePath.getFileName() + "\n" +
                "CMD [\"./myapp\"]";
        Files.write(dockerfilePath, dockerfile.getBytes());
        String dockerImageName = language.name().toLowerCase() + "_" + UUIDUtil.generateShortUUID();
        executeProcess("docker build -t " + dockerImageName + " " + dockerfilePath.getParent().toAbsolutePath().toString());
        return dockerImageName;
    }

    private String generatePascalDockerImage() {
        return "";
    }
}
